package com.example.alexmaran.theapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alexmaran.theapp.db.MyDBHandler;
import com.example.alexmaran.theapp.models.Potato;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex Maran on 11/20/2016.
 */

public class Display extends Activity {

    private MyDBHandler dbContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        String user = getIntent().getStringExtra("username");
        TextView txtView = (TextView) findViewById(R.id.TextViewUsername);
        txtView.setText(user);
        dbContext = new MyDBHandler(this, null, null, 1);
        loadListView();
    }

    private void loadListView(){
        ListView lv = (ListView) findViewById(R.id.listView);
        List<Potato> potatos = dbContext.getAllPotatoes();
        List<String> potatoNames = new ArrayList<>();
        for(Potato p : potatos){
            potatoNames.add(p.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_view_row, R.id.listText, potatoNames);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new ItemList());
    }

    public void newPotato(View view){
        if (view.getId() == R.id.addButton) {
            EditText listText = (EditText) findViewById(R.id.newPotatoNameText);
            String text = listText.getText().toString();
            if(text.isEmpty()){
                showSimpleDialog();
                return;
            }
            dbContext.addPotato(new Potato(text));
            TextView row = (TextView) findViewById(R.id.newPotatoNameText);
            row.setText("");
            loadListView();
        }


    }


    public void showSimpleDialog() {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(Display.this);
        builder.setCancelable(false);
        builder.setTitle("You failed ");
        builder.setMessage("... at saving this potato");
        builder.setPositiveButton("OK!!!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //
            }
        });

        // Create the AlertDialog object and return it
        builder.create().show();
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

    @Override
    public void onResume()
    {
        super.onResume();
        loadListView();
    }
}
