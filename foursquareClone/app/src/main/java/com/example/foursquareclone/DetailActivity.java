package com.example.foursquareclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    TextView nameText, typeText, atmosphereText;
    ImageView imageView;

    String placeName;
    String latitudeString;
    String longitudeString;
    Double latitude;
    Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        nameText = findViewById(R.id.textView_detail_name);
        typeText = findViewById(R.id.textView_detail_type);
        atmosphereText = findViewById(R.id.textView_detail_atmosphere);
        imageView = findViewById(R.id.imageViewDetail);


        Intent intent = getIntent();
        placeName = intent.getStringExtra("name");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapDetail);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        getData();
    }

    private void getData() {
        ParseQuery<ParseObject> query=ParseQuery.getQuery("Places");
        query.whereEqualTo("name",placeName);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e!=null){
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                else{

                    if(objects.size()>0){
                        for(final ParseObject object:objects){
                            ParseFile parseFile=(ParseFile) object.get("image");
                            parseFile.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if(e==null && data!=null){
                                        Bitmap bitmap= BitmapFactory.decodeByteArray(data,0,data.length);
                                        imageView.setImageBitmap(bitmap);

                                        nameText.setText(placeName);
                                        typeText.setText(object.getString("type"));
                                        atmosphereText.setText(object.getString("atmosphere"));
                                        latitudeString=object.getString("latitude");
                                        longitudeString=object.getString("longitude");
                                        latitude=Double.parseDouble(latitudeString);
                                        longitude=Double.parseDouble(longitudeString);
                                        mMap.clear();

                                        LatLng placeLocation = new LatLng(latitude,longitude);
                                        mMap.addMarker(new MarkerOptions().position(placeLocation).title(placeName));
                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(placeLocation, 15));


                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
    }
}