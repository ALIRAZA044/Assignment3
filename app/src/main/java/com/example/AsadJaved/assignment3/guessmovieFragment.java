package com.example.AsadJaved.assignment3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdulmoiedawan.assignment3.R;
import com.orm.SugarContext;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * A placeholder fragment containing a simple view.
 */
public class guessmovieFragment extends Fragment  implements  OnNetworkCallHandled{

private ImageView movieposter;
    private View rootView;
   // private Datawrapper moviedata;
    List<MovieDataModel> moviedata;
    HashMap keys=new HashMap();
    private String TAG = guessmovie.class.getSimpleName();
    private EditText moviename;
    private Button guessbutton;
    private TextView currentscore;
    private TextView highscore;
    int hscore=0;
    int score=20;
    private Bitmap pic;
    int moviecount=-1;
    private DataBaseModel dataBaseModel;
    boolean flag=true;
     Handler handler;
    public guessmovieFragment() {
    }

    @Override
    public void OnNetworkCallFailure(Object object) {

    }

    @Override
    public void OnNetworkCallSuccess(Object object) {
        //List<Datawrapper> moviedata=(List<Datawrapper>)object;
        //Log.d(TAG, "OnNetworkCallSuccess: "+"ho gya waiii"+moviedata.size());
            dataBaseModel=new DataBaseModel();
        moviecount=0;
        moviedata=dataBaseModel.getmoviedata();
        updateUi();

       // Log.d(TAG, "OnNetworkCallSuccess: " + "ho gya waiii" + moviedata.get(1).getActors());
        //movieposter.setImageBitmap(moviedata.get(0).getImage());




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



                rootView= inflater.inflate(R.layout.fragment_guessmovie, container, false);
        movieposter=(ImageView)rootView.findViewById(R.id.movieimage);
        rootView.findViewById(R.id.movie_name).setVisibility(View.INVISIBLE);
        rootView.findViewById(R.id.guess_button).setVisibility(View.INVISIBLE);
        moviename=(EditText)rootView.findViewById(R.id.movie_name);
        guessbutton=(Button)rootView.findViewById(R.id.guess_button);
        highscore=(TextView)rootView.findViewById(R.id.highscore);
currentscore=(TextView)rootView.findViewById(R.id.score);
        addListenerOnGuessbutton(guessbutton, moviename,getActivity());
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
updateafterdownload(getActivity());
                Toast.makeText(getActivity(), "Start Working", Toast.LENGTH_SHORT).show();
            }
        };
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_guess_movie, menu);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SugarContext.init(getActivity());
        setHasOptionsMenu(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_refresh:  // it is going to refer the search id name in main.xml

               // DetailAsyncTask detailAsyncTask=new DetailAsyncTask();
               // detailAsyncTask.execute(rand());

                return true;
            case R.id.action_search_movie:
                Intent intent=new Intent(getActivity(),SearchMovie.class);
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }




    }
    private void updateafterdownload(Activity activity)
    {
        SharedPreferences prefs =activity. getApplicationContext().getSharedPreferences("service", Context.MODE_WORLD_READABLE);

        String Restoremoviecount=prefs.getString("moviecount", null);
        flag=prefs.getBoolean("flag",true);
        if (Restoremoviecount!=null)
        {
            Log.e(TAG, "updated count "+Restoremoviecount );
            moviecount=Integer.parseInt(Restoremoviecount);


        }

        dataBaseModel=new DataBaseModel();
        moviedata=dataBaseModel.getmoviedata();
        MovieDataModel data=new MovieDataModel();
        data=moviedata.get(moviecount);
        rootView.findViewById(R.id.movie_name).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.guess_button).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.movieimage).setVisibility(View.VISIBLE);
        byte[] bytes=data.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes , 0, bytes .length);
        movieposter.setImageBitmap(bitmap);

    }

    private void updateUi()
    {
        Log.e(TAG, "updateUi: " + moviedata.size());
        MovieDataModel data=new MovieDataModel();
        data=moviedata.get(moviecount);
        rootView.findViewById(R.id.movie_name).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.guess_button).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.movieimage).setVisibility(View.VISIBLE);
        byte[] bytes=data.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes , 0, bytes .length);
        movieposter.setImageBitmap(bitmap);





    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences prefs =getActivity(). getApplicationContext().getSharedPreferences("service", Context.MODE_WORLD_READABLE);

        String Restoremoviecount=prefs.getString("moviecount", null);
       flag=prefs.getBoolean("flag",true);
        if (Restoremoviecount!=null)
        {
            Log.e(TAG, "m count "+Restoremoviecount );
            moviecount=Integer.parseInt(Restoremoviecount);


        }

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED )
        {
            if(moviecount==-1 || moviecount>=20) {

                Log.e(TAG, "onStart: "+"pahlii condition : " +moviecount);
                if(flag)
                {
                Intent intent=new Intent(getContext(),PosterDownloader.class);
                    final Messenger messenger = new Messenger(handler);

                    intent.putExtra("messenger", messenger);
                getActivity().startService(intent);

                    SharedPreferences pref =getActivity(). getApplicationContext().getSharedPreferences("service", Context.MODE_WORLD_READABLE);
                    SharedPreferences.Editor editor1 = prefs.edit();

                    editor1.putBoolean("flag", false);

                    //editor.putString("moviecount", "" + moviecount);
                    editor1.commit();
                flag=false;}

            }
            else if (moviecount>15 && moviecount<=20)
            {
                Log.e(TAG, "onStart: " + "dosri condition");

                if(flag)
                {
                    Log.e(TAG, "andar a gya " + "dosri condition");

                    Intent intent=new Intent(getContext(),PosterDownloader.class);
                    final Messenger messenger = new Messenger(handler);

                    intent.putExtra("messenger", messenger);
                    getActivity().startService(intent);
                    SharedPreferences pref =getActivity(). getApplicationContext().getSharedPreferences("service", Context.MODE_WORLD_READABLE);
                    SharedPreferences.Editor editor1 = prefs.edit();

                    editor1.putBoolean("flag", false);

                    //editor.putString("moviecount", "" + moviecount);
                    editor1.commit();



                }
                dataBaseModel=new DataBaseModel();
                moviedata=dataBaseModel.getmoviedata();

                updateUi();
            }

            //we are connected to a network

            //DetailAsyncTask detailAsyncTask=new DetailAsyncTask();
            //detailAsyncTask.execute(rand());
       // GuessMovieTask guessMovieTask=new GuessMovieTask(this,getActivity());
         //   guessMovieTask.execute();

            else {
                Log.e(TAG, "onStart: "+"tesri condition" );
                dataBaseModel=new DataBaseModel();
                moviedata=dataBaseModel.getmoviedata();
                //updateafterdownload(getActivity());
                updateUi();
                 }
            connected = true;
            }

        else
        {
            if(moviecount == -1)
            {Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_SHORT).show();}

           else  if(moviecount>19){
                Toast.makeText(getActivity(), "No More Movies", Toast.LENGTH_LONG).show();
            }
            else{

            dataBaseModel=new DataBaseModel();
            moviedata=dataBaseModel.getmoviedata();
            updateUi();
               // GuessMovieTask guessMovieTask=new GuessMovieTask(this,getActivity());
                //guessMovieTask.execute();
            Toast.makeText(getActivity(),"Offline mode",Toast.LENGTH_LONG).show();



            connected = false;}


        }

    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences pref =getActivity(). getApplicationContext().getSharedPreferences("guess", Context.MODE_PRIVATE);
        String Restorehscore=pref.getString("hscore", null);
        String Restorecscore=pref.getString("cscore", null);
        if (Restorehscore!=null)
        {
            hscore=Integer.parseInt(Restorehscore);

            highscore.setText(Restorehscore);}
        else
        {
            hscore=0;
            highscore.setText("0");
        }
        if(Restorecscore!=null)
        {
            score= Integer.parseInt(Restorecscore);
            currentscore.setText(""+score);
        }



    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences pref =getActivity(). getApplicationContext().getSharedPreferences("guess", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("cscore", "" + score);
        //editor.putString("moviecount", "" + moviecount);
        editor.commit();

        SharedPreferences prefs =getActivity(). getApplicationContext().getSharedPreferences("service", Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor editor1 = prefs.edit();
        editor1.putString("moviecount", "" + moviecount);


        //editor.putString("moviecount", "" + moviecount);
        editor1.commit();
        //SharedPreferences settings = getActivity().getApplicationContext().getSharedPreferences("service", Context.MODE_WORLD_READABLE);
        //settings.edit().clear().commit();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences pref =getActivity(). getApplicationContext().getSharedPreferences("guess", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        score=20;
        editor.putString("cscore", ""+score);
        editor.commit();
        SharedPreferences prefs =getActivity(). getApplicationContext().getSharedPreferences("service", Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor editor1 = prefs.edit();
        flag=true;
        editor1.putBoolean("flag", flag);

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
                Log.d(TAG, "key present " + imdb);
            continue;}
            else
            {


            Log.d(TAG, "imdb poster " + imdb);
            return imdb;}
        }
    }


    public void addListenerOnGuessbutton(Button btn, final EditText txt, final Context contex) {
        btn.setOnClickListener(new View.OnClickListener() {


            public void onClick(View arg0) {
                Context context=contex;


                Log.e(TAG, "movie count "+moviecount );
    Intent intent=new Intent(getActivity(),guessAnswer.class);
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                View view = getActivity().getCurrentFocus();
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                String moviename=txt.getText().toString();
                MovieDataModel data=new MovieDataModel();
                data=moviedata.get(moviecount);

               // Log.d(TAG, "moviedata: "+moviedata.getTitle());


                   //GuessMovieTask guessMovieTask=new GuessMovieTask(,getActivity());
                    //guessMovieTask.execute();


                if(moviename.equals(""))
                {
                    Toast.makeText(getActivity(),"Enter Movie Name",Toast.LENGTH_LONG).show();
                }
                else if(moviename.equalsIgnoreCase(data.getTitle()))
                {
                    moviecount++;
                    Toast.makeText(getActivity(), "Correct Answere", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "textview: " + moviename);
                     score=Integer.parseInt(currentscore.getText().toString());
                    score+=20;
                    currentscore.setText("" + score);

                    if(score >hscore)
                    {
                        highscore.setText(""+score);
                        SharedPreferences pref =getActivity(). getApplicationContext().getSharedPreferences("guess", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("hscore", ""+score);
                        editor.commit();

                    }
                   // DetailAsyncTask detailAsyncTask=new DetailAsyncTask();
                    //detailAsyncTask.execute(rand());

                    //intent.putExtra("BitmapImage", pic);
                        intent.putExtra("type","correct");
                    intent.putExtra("obj",data);
                    intent.putExtra("hscore"," "+hscore);
                    intent.putExtra("cscore"," "+score);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);



                }
                else
                {
                    moviecount++;


                    Toast.makeText(getActivity(), "Wrong Answer", Toast.LENGTH_SHORT).show();
                     score=Integer.parseInt(currentscore.getText().toString());
                    score-=5;
                    currentscore.setText("" + score);
                    if(score<=0)
                    {
                        score=20;

                        intent.putExtra("hscore"," "+hscore);
                        intent.putExtra("cscore"," "+score);
                        intent.putExtra("type","wrong");
                        intent.putExtra("obj",data);
                        //intent.putExtra("BitmapImage", pic);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }
                    else
                    {
                        intent.putExtra("hscore"," "+hscore);
                        intent.putExtra("cscore"," "+score);
                        intent.putExtra("type","wrong");
                        intent.putExtra("obj",data);
                        //intent.putExtra("BitmapImage", pic);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                   // DetailAsyncTask detailAsyncTask=new DetailAsyncTask();
                    //detailAsyncTask.execute(rand());
                    }



                }

            }

        });



    }





}
