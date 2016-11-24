package com.example.alexmaran.theapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.LogInBtn) {
            EditText userLbl = (EditText) findViewById(R.id.Username);
            String user = userLbl.getText().toString();
            Intent i = new Intent(MainActivity.this, Display.class);
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
