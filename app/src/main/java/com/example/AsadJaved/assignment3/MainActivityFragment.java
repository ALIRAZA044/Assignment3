package com.example.AsadJaved.assignment3;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.abdulmoiedawan.assignment3.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
 private ImageButton guessmovie;
    View rootView;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_main, container, false);
       guessmovie= (ImageButton) rootView.findViewById(R.id.guessmovie) ;
        addguessmovielistner(guessmovie);



return rootView;
    }




    private void addguessmovielistner(ImageButton imageButton)
    {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

Intent intent=new Intent(getActivity(),guessmovie.class);
                startActivity(intent);
                //Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();
            }
        });




    }



}
