package com.example.AsadJaved.assignment3;

import java.util.List;

/**
 * Created by apple on 4/22/16.
 */
public class DataBaseModel {


    public DataBaseModel()
    {}

    public void SavedData(List<Datawrapper> datawrapper)
    {
        MovieDataModel.deleteAll(MovieDataModel.class);

        for (Datawrapper data:datawrapper) {
            MovieDataModel mdm =new MovieDataModel();
            mdm.setImage(data.getImage());
            mdm.setActors(data.getActors());
            mdm.setPlot(data.getPlot());
            mdm.setImdbID(data.getImdbID());
            mdm.setTitle(data.getTitle());
            mdm.setReleased(data.getReleased());
            mdm.save();
        }

    }
    public List<MovieDataModel> getmoviedata()
    {
        return MovieDataModel.listAll(MovieDataModel.class);
    }
}
