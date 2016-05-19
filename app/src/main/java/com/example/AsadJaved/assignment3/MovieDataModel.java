package com.example.AsadJaved.assignment3;

import android.graphics.Bitmap;

import com.orm.SugarRecord;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by apple on 4/21/16.
 */
public class MovieDataModel extends SugarRecord implements Serializable{


    private String Actors;
    private String imdbID;
    private String Plot;
    private String Released;
    private String Title;

    private byte[] image;


    public void setImage(Bitmap image) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, bos);
        this.image = bos.toByteArray();
    }

    public byte[] getImage() {
        return image;
    }

    public String getActors() {
        return Actors;
    }









    public String getImdbID() {
        return imdbID;
    }









    public String getPlot() {
        return Plot;
    }





    public String getReleased() {
        return Released;
    }





    public String getTitle() {
        return Title;
    }







    public void setActors(String actors) {
        Actors = actors;
    }












    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }









    public void setPlot(String plot) {
        Plot = plot;
    }





    public void setReleased(String released) {
        Released = released;
    }





    public void setTitle(String title) {
        Title = title;
    }







}
