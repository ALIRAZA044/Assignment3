package com.example.AsadJaved.assignment3;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abdul Moied Awan on 4/9/2016.
 */
public class Datawrapper implements Serializable {

    private String Actors;
    private String Awards;
    private String Country;
    private String Director;
    private String Genre;
    private String imdbID;
    private String imdbRating;
    private String imdbVotes;
    private String Language;
    private String Metascore;
    private String Plot;
    private String Poster;
    private String Rated;
    private String Released;
    private String Response;
    private String Runtime;
    private String Title;
    private String Type;
    private String Writer;
    private String Year;
    private Bitmap image;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    private Map<String,Object> additionalProperties=new HashMap<>();

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public String getActors() {
        return Actors;
    }

    public String getAwards() {
        return Awards;
    }

    public String getCountry() {
        return Country;
    }

    public String getDirector() {
        return Director;
    }

    public String getGenre() {
        return Genre;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public String getLanguage() {
        return Language;
    }

    public String getMetascore() {
        return Metascore;
    }

    public String getPlot() {
        return Plot;
    }

    public String getPoster() {
        return Poster;
    }

    public String getRated() {
        return Rated;
    }

    public String getReleased() {
        return Released;
    }

    public String getResponse() {
        return Response;
    }

    public String getRuntime() {
        return Runtime;
    }

    public String getTitle() {
        return Title;
    }

    public String getType() {
        return Type;
    }

    public String getWriter() {
        return Writer;
    }

    public String getYear() {
        return Year;
    }

    public void setActors(String actors) {
        Actors = actors;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public void setAwards(String awards) {
        Awards = awards;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public void setMetascore(String metascore) {
        Metascore = metascore;
    }

    public void setPlot(String plot) {
        Plot = plot;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public void setRated(String rated) {
        Rated = rated;
    }

    public void setReleased(String released) {
        Released = released;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public void setRuntime(String runtime) {
        Runtime = runtime;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setWriter(String writer) {
        Writer = writer;
    }

    public void setYear(String year) {
        Year = year;
    }

}
