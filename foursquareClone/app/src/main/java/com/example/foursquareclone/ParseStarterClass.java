package com.example.foursquareclone;

import android.app.Application;

import com.parse.Parse;

public class ParseStarterClass extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
        Parse.initialize(new Parse.Configuration.Builder(this).applicationId("qUVW2HqOEJOnblxFJoqY7sVLXZ3ryTyfJ63ym1K2").clientKey("ViJ3cpP7U9QLAKSe4A5E5jaMa6qagvoi4bqZ4E7N").server("https://parseapi.back4app.com/").build());
    }
}
