package com.example.foursquareclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;

public class CreatePlaceActivity extends AppCompatActivity {
    EditText nameText, typeText, atmosphereText;
    ImageView imageView;
    Bitmap chosenImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_place);
        nameText = findViewById(R.id.name_text_create);
        typeText = findViewById(R.id.type_text_create);
        atmosphereText = findViewById(R.id.atmosphere_text_create);
        imageView = findViewById(R.id.imageView);
    }

    public void next(View view) {
        PlacesClass placesClass = PlacesClass.getInstance();
        String placeName = nameText.getText().toString();
        String placeType = nameText.getText().toString();
        String placeAtmosphere = nameText.getText().toString();

        placesClass.setName(placeName);
        placesClass.setType(placeType);
        placesClass.setAtmosphere(placeAtmosphere);
        placesClass.setImage(chosenImage);
        Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
        startActivity(intent);
    }

    public void selectpicture(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                chosenImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(chosenImage);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}