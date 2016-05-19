package com.example.AsadJaved.assignment3;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdul Moied Awan on 3/16/2016.
 */
public class MovieData {
    private List<Bitmap> imgurl;
    private List<String> description;
    private List<String> imdbID;
public MovieData(){
    imgurl=new ArrayList<Bitmap>();
    description=new ArrayList<String>();
    imdbID=new ArrayList<String>();





}
    public String getImdbid(int pos)
    {return imdbID.get(pos);



    }
    public  String printtt()
    { String str=null;
        for(int i=0;i<imdbID.size();i++)
              str=str+"\n"+imdbID.get(i);
        return  str;
    }

    public List<String> getDescription(){return description;}
    public List<String> getimdbID(){return imdbID;}
    public List<Bitmap> getImgurl(){return imgurl;}
    public int Length(){return description.size();}
    public void setimdbID(List<String> str)
    {

        this.imdbID=str;
    }

    public void setMovieDescription(List<String> str)
    {

        this.description=str;
    }


    public void setImgurl(List<Bitmap> img)
    {

        this.imgurl=img;
    }
    public void appendImageUrls(List<Bitmap> img)
    {
        this.imgurl.addAll(img);

    }
    public void clearImdbId()
    {

        imdbID.clear();

    }
    public void appendDescription(List<String> str)
    {
        this.description.addAll(str);
    }
    public void appendimbID(List<String> str)
    {
        this.imdbID.addAll(str);
    }




}
