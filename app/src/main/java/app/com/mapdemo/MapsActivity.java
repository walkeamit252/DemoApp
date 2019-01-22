package app.com.mapdemo;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ClusterManager<StringClusterItem> mClusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mClusterManager = new ClusterManager<>(this, mMap);

        mClusterManager.getMarkerCollection()
                .setOnInfoWindowAdapter(new CustomInfoViewAdapter(LayoutInflater.from(this)));

       // mMap.setInfoWindowAdapter(mClusterManager.getMarkerManager());
        mMap.setOnCameraChangeListener(mClusterManager);



        for (int i = 0; i < 10; i++) {
            final LatLng latLng = new LatLng(18.5700755 + i, 73.7298294 + i);
            mClusterManager.addItem(new StringClusterItem("Hotel #" + (i + 1), latLng));
        }


        final LatLng latLng = new LatLng(18.5701755 , 73.7208294 );
        mClusterManager.addItem(new StringClusterItem("Jagdamb #", latLng));

        final LatLng latLng1 = new LatLng(18.5599707, 73.7663066);
        mClusterManager.addItem(new StringClusterItem("Hybd Biryani #", latLng1));


        final LatLng latLng2 = new LatLng(18.5607418, 73.7857914);
        mClusterManager.addItem(new StringClusterItem("Bottoms Up #", latLng2));


        final LatLng latLng3 = new LatLng(18.5483957, 73.7728376);
        mClusterManager.addItem(new StringClusterItem("BlueRidge #", latLng3));


        final LatLng latLng4 = new LatLng(18.5263219, 73.7621671);
        mClusterManager.addItem(new StringClusterItem("Yawele #", latLng4));


        LatLng pune = new LatLng(18.5263219, 73.7621671);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pune));





    }



    static class StringClusterItem implements ClusterItem {
        final String title;
        final LatLng latLng;
        public StringClusterItem(String title, LatLng latLng) {
            this.title = title;
            this.latLng = latLng;
        }
        @Override public LatLng getPosition() {
            return latLng;
        }
    }


    public class CustomInfoViewAdapter implements GoogleMap.InfoWindowAdapter {

        private final LayoutInflater mInflater;

        public CustomInfoViewAdapter(LayoutInflater inflater) {
            this.mInflater = inflater;
        }

        @Override public View getInfoWindow(Marker marker) {
            final View popup = mInflater.inflate(R.layout.info_window_layout, null);

            ((TextView) popup.findViewById(R.id.title)).setText(marker.getSnippet());

            return popup;
            //return null;
        }

        @Override public View getInfoContents(Marker marker) {
            final View popup = mInflater.inflate(R.layout.info_window_layout, null);

            ((TextView) popup.findViewById(R.id.title)).setText(marker.getSnippet());

            return popup;
        }
    }


}
