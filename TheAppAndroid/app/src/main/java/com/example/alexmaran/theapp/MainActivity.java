package com.example.alexmaran.theapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = getApplicationContext();
    }
    public static Context getAppContext() {
        return MainActivity.context;
    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.LogInBtn) {
            EditText userLbl = (EditText) findViewById(R.id.Username);
            String user = userLbl.getText().toString();
            Intent i = new Intent(MainActivity.this, PotatoList.class);
            i.putExtra("username", user);
            startActivity(i);
        }
    }

    public void sendEmail(View v) {
        String To = "someone@something.stuff";
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, To);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "You clicked that button");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "I told you not to click the button");

        startActivity(Intent.createChooser(emailIntent, "Pick your weapon"));
        finish();

    }
}
