package fr.oxymob.montpellier.historique.fragments;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import fr.oxymob.montpellier.historique.R;
import fr.oxymob.montpellier.historique.pojos.Position;

/**
 * Created by dany on 06/09/14.
 */
public class FMap extends SupportMapFragment {
    private static final String KEY_LIST = "list";
    private static final String KEY_ONEPOINT = "onepoint";
    private List<Position> mList;

    class MyMarker{ String fid; }
    private Map<Marker, MyMarker> markerMap = new HashMap<Marker, MyMarker>();
    private String mOnePoint = null;
    private LatLng posOnePoint;

    public static FMap newInstance(ArrayList<Position> list, String onePoint) {
        FMap fragment = new FMap();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_LIST, list);
        bundle.putString(KEY_ONEPOINT, onePoint);
        fragment.setArguments(bundle);

        return fragment;
    }

    public static FMap newInstance(ArrayList<Position> list) {
        FMap fragment = new FMap();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_LIST, list);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.menu_ar);
        if (item!=null) item.setVisible(true);
    }

    private void initMap() {
        //getMap().animateCamera(CameraUpdateFactory.zoomTo(10));
        //LatLngBounds bounds = new LatLngBounds(new LatLng(42.57, 3.565), new LatLng(44.75, 3.90));
        initMarkers();
        getMap().setOnCameraChangeListener(	new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition arg0) {
                if (mOnePoint == null) {
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    builder.include(new LatLng(43.57, 3.81));
                    builder.include(new LatLng(43.64, 3.93));
                    getMap().moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 5));
                } else {
                    getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(posOnePoint, 13));
                    getMap().getUiSettings().setScrollGesturesEnabled(false);
                }
                getMap().setOnCameraChangeListener(null);
                getMap().getUiSettings().setCompassEnabled(true);
                //getMap().getUiSettings().setTiltGesturesEnabled(false);
            }
        });
        getMap().setMyLocationEnabled(true);
        getMap().setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        if (mOnePoint == null)
            getMap().setOnInfoWindowClickListener(listener);
    }

    private GoogleMap.OnInfoWindowClickListener listener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker arg0) {
           /* Intent intent = new Intent();
            intent.setClass(MHMapFragment.this.getActivity(), Fiche.class);
            MyMarker m = markerMap.get(arg0);
            intent.putExtra("EXTRA_ID", m.fid);
            startActivity(intent);*/
        }
    };

    private void initMarkers() {
        //Drawable defaultMarker = this.getResources().getDrawable(R.drawable.map_marker);
        if (mList == null)
            mList = (List<Position>) getArguments().getSerializable(KEY_LIST);
        if (mOnePoint == null)
            mOnePoint = getArguments().getString(KEY_ONEPOINT);

        for (Position pos: mList) {
            if (mOnePoint!= null) {
                if (!mOnePoint.equals(pos.getFid()))
                    continue;
                posOnePoint = pos.getPoint().toLatLng();
            }

            MarkerOptions opt = new MarkerOptions()
                    .position(pos.getPoint().toLatLng())
                    .title(pos.getMonument())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .snippet(pos.getAdresse());
            Marker marker = getMap().addMarker(opt);
            MyMarker m = new MyMarker();
            m.fid = pos.getFid();
            markerMap.put(marker, m);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            mList = (List<Position>) savedInstanceState.getSerializable(KEY_LIST);
            mOnePoint = (String) savedInstanceState.getSerializable(KEY_ONEPOINT);
        }
        initMap();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_LIST, (Serializable) mList);
        outState.putSerializable(KEY_ONEPOINT, (Serializable) mOnePoint);
    }
}
