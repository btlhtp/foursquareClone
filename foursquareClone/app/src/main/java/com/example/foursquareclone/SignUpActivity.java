package com.example.foursquareclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {
EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        username=findViewById(R.id.username_edt);
        password=findViewById(R.id.pass_edt);

        ParseUser parseUser=ParseUser.getCurrentUser();
        if(parseUser!=null){
            Intent intent=new Intent(getApplicationContext(),LocationsActivity.class);
            startActivity(intent);
        }

    }
    public void signup(View view){
        ParseUser user=new ParseUser();
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e!=null){
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(getApplicationContext(), "User Signed Up!!!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),LocationsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    public void signin(View view){
ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
    @Override
    public void done(ParseUser user, ParseException e) {
        if(e!=null){
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Welcome:"+user.getUsername(), Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(),LocationsActivity.class);
            startActivity(intent);
        }

    }
});
    }
}