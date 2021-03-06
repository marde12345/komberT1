package com.example.indoorpositioning;

import androidx.annotation.NonNull;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.turf.TurfJoins;

import static com.mapbox.mapboxsdk.style.expressions.Expression.exponential;
import static com.mapbox.mapboxsdk.style.expressions.Expression.interpolate;
import static com.mapbox.mapboxsdk.style.expressions.Expression.stop;
import static com.mapbox.mapboxsdk.style.expressions.Expression.zoom;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillOpacity;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineOpacity;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class IndoorMapsActivity extends BaseActivity {

    private MapView mapView;
    private GeoJsonSource indoorBuildingSource;
    private List<List<Point>> boundingBoxList;
    private View levelButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Maps");

        Mapbox.getInstance(this, getString(R.string.apptoken));
        setContentView(R.layout.activity_indoor_maps);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        levelButtons = findViewById(R.id.floor_level_buttons);
                        
                        indoorBuildingSource = new GeoJsonSource("indoor-building", loadJsonFromAsset("lt3.geojson"));

                        style.addSource(indoorBuildingSource);

                        loadBuildingLayer(style);
                    }
                });
                Button buttonMisLevel = findViewById(R.id.mis);
                buttonMisLevel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        indoorBuildingSource.setGeoJson(loadJsonFromAsset("mis-v1.geojson"));
                        CameraPosition position = new CameraPosition.Builder()
                                .target(new LatLng(-7.2799, 112.7976))
                                .zoom(21)
                                .build();
                        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 3000);
                    }
                });
                Button buttonLt3Level = findViewById(R.id.lt3);
                buttonLt3Level.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        indoorBuildingSource.setGeoJson(loadJsonFromAsset("lt3.geojson"));
                        CameraPosition position = new CameraPosition.Builder()
                                .target(new LatLng(-7.2797, 112.7973))
                                .zoom(18)
                                .build();
                        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 3000);
                    }
                });
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private void hideLevelButton() {
// When the user moves away from our bounding box region or zooms out far enough the floor level
// buttons are faded out and hidden.
        AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(500);
        levelButtons.startAnimation(animation);
        levelButtons.setVisibility(View.GONE);
    }

    private void showLevelButton() {
// When the user moves inside our bounding box region or zooms in to a high enough zoom level,
// the floor level buttons are faded out and hidden.
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(500);
        levelButtons.startAnimation(animation);
        levelButtons.setVisibility(View.VISIBLE);
    }

    private void loadBuildingLayer(@NonNull Style style) {
// Method used to load the indoor layer on the map. First the fill layer is drawn and then the
// line layer is added.

        FillLayer indoorBuildingLayer = new FillLayer("indoor-building-fill", "indoor-building").withProperties(
                fillColor(Color.parseColor("#eeeeee")),
// Function.zoom is used here to fade out the indoor layer if zoom level is beyond 16. Only
// necessary to show the indoor map at high zoom levels.
                fillOpacity(interpolate(exponential(1f), zoom(),
                        stop(16f, 0f),
                        stop(16.5f, 0.5f),
                        stop(17f, 1f))));

        style.addLayer(indoorBuildingLayer);

        LineLayer indoorBuildingLineLayer = new LineLayer("indoor-building-line", "indoor-building").withProperties(
                lineColor(Color.parseColor("#50667f")),
                lineWidth(0.5f),
                lineOpacity(interpolate(exponential(1f), zoom(),
                        stop(16f, 0f),
                        stop(16.5f, 0.5f),
                        stop(17f, 1f))));
        style.addLayer(indoorBuildingLineLayer);
    }

    private String loadJsonFromAsset(String filename) {
// Using this method to load in GeoJSON files from the assets folder.

        try {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, Charset.forName("UTF-8"));

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
