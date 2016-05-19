package com.example.AsadJaved.assignment3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdul Moied Awan on 3/20/2016.
 */
public class DeatilMovieData {
    private static final String TAG =DeatilMovieData.class.getSimpleName() ;
    private List<String> description;
    private List<String> imdbID;

    public DeatilMovieData()
    { description=new ArrayList<String>();
         imdbID=new ArrayList<String>();}
    public DeatilMovieData(List<String> des, List<String> id) {
        description=des;
        this.imdbID=id;

    }
    public void setdata(List des,List id)
    {description.addAll(des);



    imdbID.addAll(id);}

    public void setDescription(List str) {
        for (int i = 0; i < str.size(); i++) {

            description.add((String)str.get(i));

        }

    }


      //  description.addAll(str);}
public  void setImdbID(String str){imdbID.add(str);}
    public List<String> getDescription()
    {return description;}
    public List<String> getImdbID()
    {return imdbID;}

}