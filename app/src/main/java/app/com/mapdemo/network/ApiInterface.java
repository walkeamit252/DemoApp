package app.com.mapdemo.network;


import java.util.List;

import app.com.mapdemo.model.MarkerItem;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {


    @GET("equipments")
    Call<List<MarkerItem>> getMarkerList();


}