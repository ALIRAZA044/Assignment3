package com.example.AsadJaved.assignment3;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by apple on 4/22/16.
 */
public class GuessMovieTask extends AsyncTask< Void, Void,List<Datawrapper>> {

private Context context;
    HashMap keys=new HashMap();
    private String LOG_TAG = GuessMovieTask.class.getSimpleName();
    private OnNetworkCallHandled onNetworkCallHandled;
public GuessMovieTask(Context context,OnNetworkCallHandled onNetworkCallHandled)

{
    this.context=context;
    this.onNetworkCallHandled=onNetworkCallHandled;

}

    public GuessMovieTask()
    {



    }
    private Bitmap downloadimage(String url)
    {
        Bitmap bitmap=downloadImage(url);
        return bitmap;
    }


    public String rand() {

        String imdb = new String("tt00");
        while (true) {
            int min = 11111;
            int max = 99999;

            Random r = new Random();
            int rand = r.nextInt(max - min + 1) + min;
            imdb = imdb + rand;
            if(keys.containsKey(imdb))
            {
                Log.d(LOG_TAG, "key present " + imdb);
                continue;}
            else
            {
keys.put(imdb,"");

                Log.d(LOG_TAG, "imdb poster " + imdb);
                return imdb;}
        }
    }


    //private GuessMovieTask(Context context,Datawrapper datawrapper)
    //{


    //}

    private Datawrapper parseJsonToGson(String json)
    {
        Gson gson=new Gson();
        Datawrapper datawrapper=gson.fromJson(json,Datawrapper.class);
        if(datawrapper.getPoster().equals("N/A"))
        {

return null;

        }
        Bitmap bitmap=downloadimage(datawrapper.getPoster());
        datawrapper.setImage(bitmap);

        return datawrapper;


    }


//**************** do in background ****************
    @Override
    protected List<Datawrapper> doInBackground(Void... params) {

  int i=0;
        List<Datawrapper> moviedata=new ArrayList<Datawrapper>();


        while(i<20){

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
            String tt=rand();
            Uri builtUri = Uri.parse(Movie_BASE_URL).buildUpon().appendQueryParameter(QUERY_PARAM, tt).appendQueryParameter(plot,full).appendQueryParameter(json_param,format).build();
            java.net.URL url = new URL(builtUri.toString());
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
            MovieJsonStr  = buffer.toString();
            Log.e(LOG_TAG, " Detail Response from server" + MovieJsonStr );
        } catch (IOException e) {
            Log.d(LOG_TAG, "Error ", e);
// If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            //return null;
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
            Datawrapper datawrapper= parseJsonToGson(MovieJsonStr);
            if(datawrapper!=null)
            {i++;
                moviedata.add(datawrapper);
                continue;

            }
        }catch (Exception e){
            ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            Log.e(LOG_TAG,"Error parsing Json");
        continue;}
            else{
                Log.e(LOG_TAG,"internet dissconnected");
                break;}

        }










    }
    return moviedata;
    }

    @Override
    protected void onPostExecute(List <Datawrapper> datawrapper) {
        super.onPostExecute(datawrapper);
        DataBaseModel dataBaseModel=new DataBaseModel();
        dataBaseModel.SavedData(datawrapper);

        this.onNetworkCallHandled.OnNetworkCallSuccess(true);



    }


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
            SharedPreferences prefs =this.context. getApplicationContext().getSharedPreferences("service", Context.MODE_WORLD_READABLE);
            SharedPreferences.Editor editor1 = prefs.edit();

            editor1.putBoolean("flag",true);
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
        Log.d(LOG_TAG, "ho v ja");
        return stream;
    }

}

