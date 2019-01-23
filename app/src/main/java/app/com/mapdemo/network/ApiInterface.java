package app.com.mapdemo.network;


import java.util.List;

import app.com.mapdemo.model.MarkerDetailRequest;
import app.com.mapdemo.model.MarkerItem;
import app.com.mapdemo.model.MarkerItemParent;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("equipments")
    Call<List<MarkerItem>> getMarkerList();

    @POST("equipmentDetail")
    Call<MarkerItemParent> getMarkerDetail(@Body MarkerDetailRequest markerDetailRequest);

}