package com.example.alexmaran.theapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import static java.security.AccessController.getContext;

/**
 * Created by Alex Maran on 11/20/2016.
 */

public class Display extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        String user = getIntent().getStringExtra("username");
        TextView txtView = (TextView) findViewById(R.id.TextViewUsername);
        txtView.setText(user);

        ListView lv = (ListView) findViewById(R.id.listView);
        String[] potatos = {"potato1", "potato2", "potato3", "potato4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_view_row, R.id.listText, potatos);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new ItemList());
    }

    public class ItemList implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            TextView listText = (TextView) view.findViewById(R.id.listText);
            String text = listText.getText().toString();
            Intent intent = new Intent(Display.this, SecondaryActivity.class);
            intent.putExtra("selected-item", text);
            startActivity(intent);
        }
    }
}
