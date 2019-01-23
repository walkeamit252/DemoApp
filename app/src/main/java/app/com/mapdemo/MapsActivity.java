package app.com.mapdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.util.List;

import app.com.mapdemo.model.MarkerItem;
import app.com.mapdemo.network.ApiClient;
import app.com.mapdemo.network.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivityResult";
    private GoogleMap mMap;
    private ClusterManager<StringClusterItem> mClusterManager;
    private ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mClusterManager = new ClusterManager<>(this, mMap);
        mMap.setOnMarkerClickListener(mClusterManager);

        final CustomClusterRenderer renderer = new CustomClusterRenderer(this, mMap, mClusterManager);

        mClusterManager.setRenderer(renderer);

       /* mClusterManager.getMarkerCollection()
                .setOnInfoWindowAdapter(new CustomInfoViewAdapter(LayoutInflater.from(this)));

        mMap.setInfoWindowAdapter(mClusterManager.getMarkerManager());*/

        mMap.setOnCameraChangeListener(mClusterManager);

        fetMarkerList();

//        LatLng pune = new LatLng(18.5263219, 73.7621671);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(pune));


        mClusterManager.setOnClusterClickListener(
                new ClusterManager.OnClusterClickListener<StringClusterItem>() {
                    @Override
                    public boolean onClusterClick(Cluster<StringClusterItem> cluster) {
                        Toast.makeText(MapsActivity.this, "Cluster click", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });

        mClusterManager.setOnClusterItemClickListener(
                new ClusterManager.OnClusterItemClickListener<StringClusterItem>() {
                    @Override
                    public boolean onClusterItemClick(StringClusterItem clusterItem) {
                        Toast.makeText(MapsActivity.this, "Cluster item click", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
    }


    public class CustomClusterRenderer extends DefaultClusterRenderer<StringClusterItem> {
        public CustomClusterRenderer(Context context, GoogleMap map,
                                     ClusterManager<MapsActivity.StringClusterItem> clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        protected void onBeforeClusterItemRendered(MapsActivity.StringClusterItem item,
                                                   MarkerOptions markerOptions) {
            final BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.map_location_icon);
            markerOptions.icon(markerDescriptor).snippet(item.title);
            markerOptions.icon(markerDescriptor);
        }

        @Override
        protected void onBeforeClusterRendered(Cluster<StringClusterItem> cluster, MarkerOptions markerOptions) {
            final BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.
                    fromResource(R.drawable.map_location_icon);
            markerOptions.icon(markerDescriptor);
        }
    }


    static class StringClusterItem implements ClusterItem {
        final String title;
        final LatLng latLng;

        public StringClusterItem(MarkerItem markerItem) {
            this.title = markerItem.getName();
            this.latLng = new LatLng(Double.parseDouble(markerItem.getLat()), Double.parseDouble(markerItem.getLon()));
        }

        @Override
        public LatLng getPosition() {
            return latLng;
        }
    }


    public class CustomInfoViewAdapter implements GoogleMap.InfoWindowAdapter {

        private final LayoutInflater mInflater;

        public CustomInfoViewAdapter(LayoutInflater inflater) {
            this.mInflater = inflater;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            final View popup = mInflater.inflate(R.layout.info_window_layout, null);
            ((TextView) popup.findViewById(R.id.title)).setText(marker.getSnippet());
            return popup;
        }

        @Override
        public View getInfoContents(Marker marker) {
            final View popup = mInflater.inflate(R.layout.info_window_layout, null);
            ((TextView) popup.findViewById(R.id.title)).setText(marker.getSnippet());
            return popup;
        }
    }


    public void fetMarkerList() {

        Call<List<MarkerItem>> call = apiService.getMarkerList();
        call.enqueue(new Callback<List<MarkerItem>>() {
            @Override
            public void onResponse(Call<List<MarkerItem>> call, Response<List<MarkerItem>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: " + response.body().size());
                    if (response != null && response.body() != null && response.body().size() != 0) {
                        LatLng latLng = new LatLng(Double.parseDouble(response.body().get(0).getLat()),
                                Double.parseDouble(response.body().get(0).getLon()));
                        for (MarkerItem item : response.body()) {
                            mClusterManager.addItem(new StringClusterItem(item));
                        }
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MarkerItem>> call, Throwable t) {
                Log.i(TAG, "onResponse: " + t.getLocalizedMessage());
            }
        });

    }

}











  /*for (int i = 0; i < 10; i++) {
            final LatLng latLng = new LatLng(18.5700755 + i, 73.7298294 + i);
            mClusterManager.addItem(new StringClusterItem("Hotel #" + (i + 1), latLng));
        }


        final LatLng latLng = new LatLng(18.5701755, 73.7208294);
        mClusterManager.addItem(new StringClusterItem("Jagdamb #", latLng));

        final LatLng latLng1 = new LatLng(18.5599707, 73.7663066);
        mClusterManager.addItem(new StringClusterItem("Hybd Biryani #", latLng1));


        final LatLng latLng2 = new LatLng(18.5607418, 73.7857914);
        mClusterManager.addItem(new StringClusterItem("Bottoms Up #", latLng2));


        final LatLng latLng3 = new LatLng(18.5483957, 73.7728376);
        mClusterManager.addItem(new StringClusterItem("BlueRidge #", latLng3));


        final LatLng latLng4 = new LatLng(18.5263219, 73.7621671);
        mClusterManager.addItem(new StringClusterItem("Yawele #", latLng4));*/