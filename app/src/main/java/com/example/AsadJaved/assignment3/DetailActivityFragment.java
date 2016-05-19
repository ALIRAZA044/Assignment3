package com.example.AsadJaved.assignment3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abdulmoiedawan.assignment3.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {
    private String TAG=DetailActivityFragment.class.getSimpleName();
    private String Share_HashTag="#IMDB Movie";
    private String URL="http://www.imdb.com/title/";
    private String description;
    // DetailMovieAdapter adtapter;
    View rootView;
    public DetailActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail, menu);
        MenuItem menuItem=menu.findItem(R.id.action_share);
        android.support.v7.widget.ShareActionProvider shareActionProvider=( android.support.v7.widget.ShareActionProvider ) MenuItemCompat.getActionProvider(menuItem);

        if (shareActionProvider!=null)
        {
            shareActionProvider.setShareIntent(CreateMovieIntent());

        }else
        {
            Log.d(TAG, "Share Action Provider is null ");

        }


    }





    private Intent             CreateMovieUrlIntent()
    {Intent shareIntent=new Intent(Intent.ACTION_VIEW, Uri.parse(URL));

        return shareIntent;}
    private Intent CreateMovieIntent()
    {
        Intent shareIntent=new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, description);
        shareIntent.putExtra(Intent.EXTRA_TEXT, URL);
        shareIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME,Share_HashTag);




        return shareIntent; }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //adtapter=new DetailMovieAdapter(this.getContext());
        Intent intent=getActivity().getIntent();
        rootView=inflater.inflate(R.layout.fragment_detail, container, false);
        String str1=intent.getStringExtra("check");
        description=intent.getStringExtra("des");
        getActivity().setTitle(description);
        String imdb=intent.getStringExtra("imbid");
        URL+=imdb;
        ImageButton ib = (ImageButton) rootView.findViewById(R.id.detail_item_img);
        Log.e(TAG, "onCreateView: " + imdb);
        DetailAsyncTask thread=new DetailAsyncTask();
        thread.execute(imdb);
        if(str1.equals("1")){
            byte[] bytes = intent.getByteArrayExtra("bitmapbytes");
            if(bytes.length==0)
                Log.e(TAG, "onCreateView: lenght"+bytes.length )  ;
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);


            if(intent!=null && bytes.length!=0)
            {
                ib.setMaxWidth(bmp.getWidth());
                ib.setMaxHeight(bmp.getHeight());
                // DeatilMovieData mv=(DeatilMovieData)intent.getExtras().getSerializable("hello");//getStringExtra(Intent.EXTRA_TEXT);

                ((ImageView)rootView.findViewById(R.id.detail_item_img)).setImageBitmap(bmp);

                //((TextView)rootView.findViewById(R.id.detail_te)).setText(description);
                // ((TextView)rootView.findViewById(R.id.time)).setText( "Hello");

            }}
        else
        {
            ((ImageView)rootView.findViewById(R.id.detail_item_img)).setImageResource(R.drawable.generic_profile_man2);

            //((TextView)rootView.findViewById(R.id.detail_text)).setText(description);


        }


        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent Webrequest=CreateMovieUrlIntent();
                startActivity(Webrequest);
                //Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();
            }
        });

        return  rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


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

    public class DetailAsyncTask extends AsyncTask<String, Void,Movie> {
        //private Movie mv=new Movie();
        private String LOG_TAG = DetailAsyncTask.class.getSimpleName();
        private Movie  getMovieDataFromJson(String JsonMovie) throws JSONException {
            JSONObject MovieJson = new JSONObject(JsonMovie);
            final     Movie mv=new Movie();

            final String OWN_Title = "Title";
            final String OWN_Year = "Year";
            final String OWN_Realeased="Released";
            final String OWN_Runtime="Runtime";

            final String OWN_Genre="Genre";
            final String OWN_Director="Director";
            final String OWN_Writer="Writer";
            final String OWN_Actors="Actors";
            final String OWN_Plot="Plot";
            final String OWN_Language="Language";
            final String OWN_Country="Country";
            final String OWN_Awards="Awards";

            final String OWN_imdbRating="imdbRating";
            final String OWN_imdbVotes="imdbVotes";
            String Title;
            String Year;
            String Released;
            String Time;
            String Genre;
            String Director;
            String Writer;
            String Actors;
            String plot;
            String Language;
            String Country;
            String Awards;
            String Rating;
            String Votes;

            Title=MovieJson.getString(OWN_Title);
            Year=MovieJson.getString(OWN_Year);
            Released=MovieJson.getString(OWN_Realeased);
            Time=MovieJson.getString(OWN_Runtime);
            Time=ConvertTime(Time);
            Genre=MovieJson.getString(OWN_Genre);
            Director=MovieJson.getString(OWN_Director);
            Writer=MovieJson.getString(OWN_Writer);
            Actors=MovieJson.getString(OWN_Actors);
            plot=MovieJson.getString(OWN_Plot);
            Language=MovieJson.getString(OWN_Language);
            Country = MovieJson.getString(OWN_Country);
            Awards=MovieJson.getString(OWN_Awards);
            Rating =MovieJson.getString(OWN_imdbRating);
            Votes=MovieJson.getString(OWN_imdbVotes);

            mv.setTitle(Title);
            mv.setYear(Year);
            mv.setReleased(Released);
            mv.setTime(Time);
            mv.setGenre(Genre);
            mv.setDirector(Director);
            mv.setWriter(Writer);
            mv.setActors(Actors);
            mv.setPlot(plot);
            mv.setLanguage(Language);
            mv.setCountry(Country);
            mv.setAwards(Awards);
            mv.setRating(Rating);
            mv.setVotes(Votes);

            // Log.d(TAG, "getMovieDataFromJson: "+ Title);

            return mv;
        }

        private String ConvertTime(String t)
        {
            String[] min=t.split("\\s+");
            int minutes=Integer.parseInt(min[0]);
            double hours=minutes/(double)60;
            int hour=(int)hours;
            double mins = hours - (long) hours;
            mins=mins*60;
            long m=Math.round(mins);
            String time=null;

            if(hour==1&&m==0)
            {time=hour+" hr";}
            else if(hour==1 &&m>0)
            {time=hour+" hr "+m+" mins";}
            else if(hours>1&&m==0)
            {  time=hour+" hrs";}
            else if(hour==0)
            {time=m+" mins";}
            else
            {time=hour+" hrs "+m+" mins";}


            // Log.d(TAG, "ConvertTime: "+time);


            return  time;
        }


























        @Override
        protected Movie doInBackground(String... params) {
            String MovieJsonStr = null;
            String format = "json";

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
// Construct the URL for the OpenWeatherMap query
// Possible parameters are avaiable at OWM's forecast API page, at
// http://openweathermap.org/API#forecast
                final String Movie_BASE_URL = "http://www.omdbapi.com/?";
                final String QUERY_PARAM = "i";
                final String json_param="r";

                final String page="page";
                final String full="full";
                final String plot="plot";
                String tt=params[0];
                Uri builtUri = Uri.parse(Movie_BASE_URL).buildUpon().appendQueryParameter(QUERY_PARAM, tt).appendQueryParameter(plot,full).appendQueryParameter(json_param,format).build();
                java.net.URL url = new URL(builtUri.toString());
                Log.v(LOG_TAG, "Query g    :  " + builtUri.toString());
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
                MovieJsonStr  = buffer.toString();
                Log.e(LOG_TAG, " Detail Response from server" + MovieJsonStr );
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
                        Log.d(LOG_TAG, "ResponseFromServer" + MovieJsonStr );
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {
                return getMovieDataFromJson(MovieJsonStr );
            }catch (Exception e){
                Log.e(LOG_TAG,"Error parsing Json");
            }



            return null;
        }





        @Override
        protected void onPostExecute(Movie mv) {
            super.onPostExecute(mv);

            if(mv!=null)
            {
                Log.e(TAG, "a gya ");
//adtapter.addAll(mv);
//adtapter.Notify();
                ((TextView)rootView.findViewById(R.id.time)).setText( mv.getTime());
                ((TextView)rootView.findViewById(R.id.Top_genre)).setText( mv.getGenre().replace(',', '|'));
                ((TextView)rootView.findViewById(R.id.plot)).setText( mv.getPlot());
                ((TextView)rootView.findViewById(R.id.rating)).setText( mv.getRating());
                ((TextView)rootView.findViewById(R.id.votes)).setText( mv.getVotes());
                ((TextView)rootView.findViewById(R.id.genre2)).setText( mv.getGenre());
                ((TextView)rootView.findViewById(R.id.release_date)).setText( mv.getReleased());
                ((TextView)rootView.findViewById(R.id.award)).setText( mv.getAwards());
                ((TextView)rootView.findViewById(R.id.actor)).setText( mv.getActors());
                ((TextView)rootView.findViewById(R.id.writer)).setText( mv.getWriter());
                ((TextView)rootView.findViewById(R.id.director)).setText( mv.getDirector());
                ((TextView)rootView.findViewById(R.id.language)).setText( mv.getLanguage());
                ((TextView)rootView.findViewById(R.id.country)).setText( mv.getCountry());





            }

        }
    }


























































}
