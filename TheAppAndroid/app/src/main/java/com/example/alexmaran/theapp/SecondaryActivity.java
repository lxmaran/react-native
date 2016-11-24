package com.example.alexmaran.theapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Alex Maran on 11/20/2016.
 */

public class SecondaryActivity extends Activity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondary_activity);

        textView = (TextView) findViewById(R.id.textView);
        // get the intent from which this activity is called.
        Intent intent = getIntent();

        // fetch value from key-value pair and make it visible on TextView.
        String item = intent.getStringExtra("selected-item");
        textView.setText("you selected " + item);
    }

    public void UpdatePotato(View v) {
        if (v.getId() == R.id.Updatebutton) {
            EditText listText = (EditText) findViewById(R.id.updateField);
            String text = listText.getText().toString();
            //Do http call
            TextView row = (TextView) findViewById(R.id.textView);
            row.setText(text);
        }
    }
}
