package com.example.twitter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity  {

    public void redirectUser() {

        if (ParseUser.getCurrentUser() != null) {

            Intent intent = new Intent(getApplicationContext(), MainActivity2.class);

            startActivity(intent);
            finish();

        }

    }

    public void signupLogin(View view) {

        final EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);

        final EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {

                if (e == null) {

                    Log.i("Info", "Logged in");

                    redirectUser();
                    finish();

                } else {

                    ParseUser parseUser = new ParseUser();

                    parseUser.setUsername(usernameEditText.getText().toString());

                    parseUser.setPassword(passwordEditText.getText().toString());

                    parseUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {

                            if (e == null) {

                                Log.i("Info", "Signed up");

                                redirectUser();
                                finish();

                            } else {

                                Toast.makeText(MainActivity.this, e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

                }

            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Twitter: Login");

        redirectUser();

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }


}
