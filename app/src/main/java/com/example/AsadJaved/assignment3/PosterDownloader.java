package com.example.AsadJaved.assignment3;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by apple on 4/21/16.
 */
public class PosterDownloader extends IntentService{
    String TAG=IntentService.class.getSimpleName();
    public PosterDownloader() {
        super("my");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
Toast.makeText(this,"That's it",Toast.LENGTH_LONG).show();
        SharedPreferences prefs =getBaseContext(). getApplicationContext().getSharedPreferences("service", Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor editor1 = prefs.edit();

        editor1.putBoolean("flag",true);

        //editor.putString("moviecount", "" + moviecount);
        editor1.commit();

    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"Download movies",Toast.LENGTH_LONG).show();


            final  Messenger messenger = (Messenger) intent.getParcelableExtra("messenger");
           final Message message = Message.obtain(null, 1234);



        GuessMovieTask guessMovieTask=new GuessMovieTask(getBaseContext(), new OnNetworkCallHandled() {
            @Override
            public void OnNetworkCallSuccess(Object object) {


                SharedPreferences prefs =getBaseContext(). getApplicationContext().getSharedPreferences("service", Context.MODE_WORLD_READABLE);
                SharedPreferences.Editor editor1 = prefs.edit();
                editor1.putString("moviecount", ""+0);
                editor1.putBoolean("flag",true);

                //editor.putString("moviecount", "" + moviecount);
                editor1.commit();

                Log.e(TAG, "OnNetworkCallSuccess: "+prefs.getString("moviecount",null)+ "boolean "+prefs.getBoolean("flag",false) );

                Toast.makeText(getBaseContext(),"Process Done",Toast.LENGTH_LONG).show();
                try {
                    messenger.send(message);
                } catch (RemoteException exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void OnNetworkCallFailure(Object object) {

            }
        });
        guessMovieTask.execute();



        return super.onStartCommand(intent, flags, startId);


    }
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

}

