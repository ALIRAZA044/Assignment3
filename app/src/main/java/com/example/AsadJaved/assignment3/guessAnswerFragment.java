package com.example.AsadJaved.assignment3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abdulmoiedawan.assignment3.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class guessAnswerFragment extends Fragment {
    private static final String TAG =guessAnswer.class.getSimpleName() ;
    private View rootview;


    public guessAnswerFragment() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_guess_answer, menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.playagain:  // it is going to refer the search id name in main.xml

                Intent intent1=new Intent(getActivity(),guessmovie.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview= inflater.inflate(R.layout.fragment_guess_answer, container, false);
        Intent intent=getActivity().getIntent();
        MovieDataModel moviedate=(MovieDataModel)intent.getSerializableExtra("obj");
TextView textView=(TextView) rootview.findViewById(R.id.mscore);
        String cscore=intent.getStringExtra("cscore");
        textView.setText(cscore);
        String hscore=intent.getStringExtra("hscore");
        textView=(TextView) rootview.findViewById(R.id.mhighscore);
        textView.setText(hscore);
        byte[] bytes=moviedate.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);






        //Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
        ImageView moviepic=(ImageView)rootview.findViewById(R.id.answeremovieimage);
        moviepic.setImageBitmap(bitmap);
      String type=intent.getStringExtra("type");
        if(type.equals("correct"))
        {
            rightanswer(moviedate,rootview);
        }
        else
        wronganswer(moviedate,rootview);


        return rootview;}


    private void rightanswer(MovieDataModel data,View v)
    {
      TextView answere= (TextView) v.findViewById(R.id.answer);
        answere.setTextColor(Color.parseColor("#328917"));
        answere.setText("Correct Answere!!");
        answere=(TextView) v.findViewById(R.id.wronganswere);
        answere.setVisibility(View.INVISIBLE);
        answere=(TextView) v.findViewById(R.id.mname);
        answere.setText(data.getTitle());
        answere=(TextView) v.findViewById(R.id.rdate);
        answere.setText(data.getReleased());
        answere=(TextView) v.findViewById(R.id.actorsname);
        answere.setText(data.getActors());
        answere=(TextView) v.findViewById(R.id.mdescription);
        answere.setText(data.getPlot());





    }
    private void wronganswer(MovieDataModel data,View v)
    {

        TextView answere= (TextView) v.findViewById(R.id.answer);
        answere.setTextColor(Color.parseColor("#e92b2b"));
        answere.setText("Wrong Answere!!");
        answere=(TextView) v.findViewById(R.id.wronganswere);
        answere.setTextColor(Color.parseColor("#328917"));
        answere.setText("Correct Info ");
        answere=(TextView) v.findViewById(R.id.mname);
        answere.setText(data.getTitle());
        answere=(TextView) v.findViewById(R.id.rdate);
        answere.setText(data.getReleased());
        answere=(TextView) v.findViewById(R.id.actorsname);
        answere.setText(data.getActors());
        answere=(TextView) v.findViewById(R.id.mdescription);
        answere.setText(data.getPlot());




    }

}


