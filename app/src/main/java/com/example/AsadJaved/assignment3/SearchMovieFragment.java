package com.example.AsadJaved.assignment3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdulmoiedawan.assignment3.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchMovieFragment extends Fragment {

    private lazyadapter forecastadapter;
    int pageno=1;
    int counter=0;
    int searchcounter=0;
    ListView listView;
    int count=1;
    MovieTask weatherTask;
    TextView searchtext=null;
    View rootView;
    View loadingview;
    String SearchString;
    public static MovieData movieObject=new MovieData();
    private String TAG=MainActivity.class.getSimpleName();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search_movie, container, false);
        rootView.findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);

        //LinearLayout view = (LinearLayout)LayoutInflater.from(getActivity()).inflate(R.layout.fragment_main, null);



        //loadingview=inflater.inflate(R.layout.loadingbar,container,false);
        final MovieData [] forecastArray = {};
        List<MovieData> weekForecast = new ArrayList<MovieData>(Arrays.asList(forecastArray));

        forecastadapter =new lazyadapter(getActivity(),movieObject);
        //= new ArrayAdapter(
        /// getActivity(),
        // R.layout.list_item_forecast,

        // R.id.list_item_forecast_textview,
        // weekForecast

        // );
        listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(forecastadapter);
        searchtext= (TextView)rootView.findViewById(R.id.inputSearch);;
        Button searchbtn= (Button) rootView.findViewById(R.id.buttonsearch) ;
        addListenerOnButton(searchbtn, searchtext);


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (scrollState == SCROLL_STATE_IDLE) {
                    if (listView.getLastVisiblePosition() == forecastadapter.getCount() - 1) {
                        //Update new data here
                        // and then, notify your adapter
                        pageno++;
                        weatherTask = new MovieTask();
                        weatherTask.execute(SearchString, Integer.toString(pageno));
                        forecastadapter.notifyDataSetChanged();
                        //   forecastadapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }

        });

//************** New Detail Activity Start *****************
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String imdb=movieObject.getImdbid(position);
                String forecast = (String) forecastadapter.getdescription(position);
                Bitmap pic = ((Bitmap) forecastadapter.getImg(position));
                // DeatilMovieData send=new DeatilMovieData(fo);
                Intent intent = new Intent(getActivity(), DetailActivity.class);//.putExtra(Intent.EXTRA_TEXT,send);
                if (pic != null) {
                    intent.putExtra("check", "1");
                    Log.e(TAG, "onCreateView: lenght");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    pic.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] bytes = stream.toByteArray();
                    intent.putExtra("bitmapbytes",bytes);
                    intent.putExtra("des",forecast);
                    intent.putExtra("imbid",imdb);



                    //intent.putExtra("hello",send);
                    Toast.makeText(getActivity(), forecast, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else
                {
                    intent.putExtra("check","0");
                    intent.putExtra("des",forecast);
                    intent.putExtra("imbid",imdb);
                    Toast.makeText(getActivity(),forecast,Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }


            }
        });




        //ForecastWeatherTask weatherTask = new ForecastWeatherTask();
        //weatherTask.execute();
        return rootView;

    }

//******************** Search Butoon***************

    public void addListenerOnButton(Button btn, final TextView txt) {



        btn.setOnClickListener(new View.OnClickListener() {


            public void onClick(View arg0) {


                if (!txt.getText().toString().equals("") && txt.getText().toString().length() > 1) {
                    searchcounter++;
                    count = 0;
                    pageno = 1;
                    movieObject.clearImdbId();

                    SearchString=txt.getText().toString();
                    //weatherTask.Cancel();
                    //  Toast.makeText(getActivity(), " Entered", Toast.LENGTH_LONG).show();
                    forecastadapter.Clear();
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    View view = getActivity().getCurrentFocus();
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    weatherTask = new MovieTask();
                    weatherTask.execute(txt.getText().toString(), Integer.toString(pageno));

                    // forecastadapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getActivity(), " Nothing Found ", Toast.LENGTH_LONG).show();

                }

            }

        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        searchcounter++;
        SharedPreferences pref =getActivity(). getApplicationContext().getSharedPreferences("MyPref", Context.BIND_IMPORTANT);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("text", searchtext.getText().toString());
        editor.commit();
//        weatherTask.cancel(true);

    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences pref =getActivity(). getApplicationContext().getSharedPreferences("MyPref", Context.BIND_IMPORTANT);
        String Restore=pref.getString("text",null);
        if (Restore!=null)
        {searchtext.setText(Restore);}
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_forecast_fragment, menu);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        searchcounter++;
        //  weatherTask.cancel(true);
        //SharedPreferences pref =getActivity(). getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        //pref.edit().clear().commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toast.makeText(getActivity(),"meni tiem", Toast.LENGTH_LONG).show();
        switch (item.getItemId())
        {
            case R.id.action_refresh:  // it is going to refer the search id name in main.xml

                Intent intent=new Intent(getActivity(),guessmovie.class);
                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }





















    private class MovieTask extends AsyncTask<String, Void,DeatilMovieData> {
        int count=searchcounter;
        private String LOG_TAG = MovieTask.class.getSimpleName();

//******************* Downloading Movie Description **********************

        private DeatilMovieData  getMovieDataFromJson(String forecastJsonstr,int pageno) throws JSONException {
            final     DeatilMovieData mv=new DeatilMovieData();
            final String OWN_Search = "Search";
            final String OWN_Title = "Title";
            final String OWN_Year = "Year";
            final String OWN_URL = "Poster";
            final String OWN_RESPONSE="Response";
            final String  OWN_IMDBID="imdbID";

            int numMovies;
            JSONObject forecastJson = new JSONObject(forecastJsonstr);

            JSONArray MoviesArray = forecastJson.getJSONArray(OWN_Search);

            numMovies = MoviesArray.length();
            Log.e(LOG_TAG, "Array Length:" + numMovies);

            List<String > description=new ArrayList<String>();
            List<String > imgurl=new ArrayList<String>();
            List<String > imbid=new ArrayList<String>();

            for (int i = 0; i < MoviesArray.length(); i++) {
                String name;
                String Year = null;
                String poster=null;
                String imbID=null;
                JSONObject Movieobject = MoviesArray.getJSONObject(i);
                //JSONObject weatherObject = dayForecast.getJSONArray(OWN_WEATHER).getJSONObject(0);
                name = Movieobject.getString(OWN_Title);
                Year = Movieobject.getString(OWN_Year);
                imbID=Movieobject.getString(OWN_IMDBID);

                poster = Movieobject.getString(OWN_URL);
                name=name+"( "+Year+" )";

                // mv.setDescription(name);
                // Log.e(LOG_TAG, "Errorrr!!!!" + imbID);
                // mv.setImdbID(imbID);//
                description.add(name);
                imbid.add(imbID);
                imgurl.add(poster);
                //Moviedescription[i]=name;
                //imgUrl[i]=poster;
                // mv.setMoviename(name);
                //mv.setyear(Year);
                //mv.setImgurl(poster);
                // resultStrs.add(mv);

            }
            // mv.setdata(description,imbid);
            //mv.setDescription(description);
            ArrayList temp=new ArrayList();
            temp.add(Integer.toString(count));
            temp.add(Integer.toString(pageno));
            Imagedownloader imgdownload=new Imagedownloader();
            imgdownload.execute(imgurl,temp);
            mv.setdata(description,imbid);

            return mv;




        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rootView.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        }

        @Override
        protected DeatilMovieData doInBackground(String... params) {


            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
// Will contain the raw JSON response as a string.
            String forecastJsonStr = null;
            String format = "json";

            int pageno = Integer.parseInt(params[1]);

//place your ai key here




            try {
// Construct the URL for the OpenWeatherMap query
// Possible parameters are avaiable at OWM's forecast API page, at
// http://openweathermap.org/API#forecast
                final String Movie_BASE_URL = "http://www.omdbapi.com/?";
                final String QUERY_PARAM = "s";
                final String json_param="r";

                final String page="page";
                final String full="full";
                final String plot="plot";
                String search_word=params[0];
                Uri builtUri = Uri.parse(Movie_BASE_URL).buildUpon().appendQueryParameter(QUERY_PARAM, search_word).appendQueryParameter(plot,full).appendQueryParameter(json_param,format).appendQueryParameter(page,Integer.toString(pageno)).build();
                URL url = new URL(builtUri.toString());
                Log.v(LOG_TAG, "Query    :  " + builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
// Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
// Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
// Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
// But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
// Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();
                Log.e(LOG_TAG, "Response from server" + forecastJsonStr);
            } catch (IOException e) {
                Log.d(LOG_TAG, "Error ", e);
// If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {

                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        Log.d(LOG_TAG, "ResponseFromServer" + forecastJsonStr);
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {


                return getMovieDataFromJson(forecastJsonStr,pageno);
            }catch (Exception e){
                Log.e(LOG_TAG,"Error parsing Json");
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            // forecastadapter.clearData();
            forecastadapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(DeatilMovieData result) {
            super.onPostExecute(result);


            if(result!=null&& this.count==searchcounter)
            {
                movieObject.appendDescription(result.getDescription());
                movieObject.appendimbID(result.getImdbID());

                forecastadapter.AddAll(movieObject);
               // forecastadapter.notifyDataSetInvalidated();
                //forecastadapter.notifyDataSetChanged();
//             Log.d(LOG_TAG, "ttt "+movieObject.printtt());
            }
            else{
                Toast.makeText(getActivity(),"Movie Not Found",Toast.LENGTH_SHORT).show();
                rootView.findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
            }
            // count=1;
        }
    }


    //*************************** Image Download********************

    public class Imagedownloader extends AsyncTask<List<String> ,Void,List<Bitmap>>
    {
        int count=0;
        int pageno;
        private String LOG_TAG1 = Imagedownloader.class.getSimpleName();
        @Override
        protected List<Bitmap> doInBackground(List<String>... params) {
            List<String> urls=params[0];
            count=Integer.parseInt(params[1].get(0));
            pageno =Integer.parseInt(params[1].get(1));
            List<Bitmap> bitmaps = new ArrayList<Bitmap>();
            for (String url : urls) {
                bitmaps.add(downloadImage(url));
            }
            return bitmaps;







        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            rootView.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);


        }

        @Override
        protected void onPostExecute(List<Bitmap> bitmaps) {
            super.onPostExecute(bitmaps);
            if(bitmaps!=null&& this.count==searchcounter)
            {
                movieObject.appendImageUrls(bitmaps);
                forecastadapter.AddAll(movieObject);
                forecastadapter.notifyDataSetChanged();
                Toast.makeText(getActivity(),"Page No : "+this.pageno,Toast.LENGTH_LONG).show();
                rootView.findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
            }}



        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            return bitmap;
        }
        private InputStream getHttpConnection(String urlString) throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {

                ex.printStackTrace();
            }
            return stream;
        }











    }

}