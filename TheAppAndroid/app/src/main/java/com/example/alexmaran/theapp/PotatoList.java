package com.example.alexmaran.theapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexmaran.theapp.models.Potato;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import com.example.alexmaran.theapp.db.MyDBHandler;

/**
 * Created by Alex Maran on 11/20/2016.
 */

public class PotatoList extends Activity {

    final  String DB_COLLECTION_POTATOES = "potatoes";
    private DatabaseReference db_context;
    private List<Potato> potatoList = new ArrayList<>();
    ArrayAdapter<Potato> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        db_context = FirebaseDatabase.getInstance().getReference();

        setContentView(R.layout.display);

        String user = getIntent().getStringExtra("username");
        TextView txtView = (TextView) findViewById(R.id.TextViewUsername);
        txtView.setText(user);
        final ListView potatoListView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, R.layout.list_view_row, R.id.listText, potatoList);
        potatoListView.setAdapter(adapter);
        potatoListView.setOnItemClickListener(new ItemList());

        //firebase live
        db_context.child(DB_COLLECTION_POTATOES).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Potato p;
                p = dataSnapshot.getValue(Potato.class);
                potatoList.add(p);
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Something was added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Potato p;
                p = dataSnapshot.getValue(Potato.class);
                int index = potatoList.indexOf(p);
                if(index>=0){
                    potatoList.set(index, p);
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Something was changed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Potato p;
                p = dataSnapshot.getValue(Potato.class);
                int index = potatoList.indexOf(p);
                if(index>=0){
                    potatoList.remove(index);
                }
                adapter.notifyDataSetChanged();
                if(BackgroundCheck.getInstance().isAppIsInBackground(MainActivity.getAppContext())) {
                    raisNotification("Something happened to your potatoes", "One just died");
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(getApplicationContext(), "Something was moved", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Firebase went offline", Toast.LENGTH_SHORT).show();
            }
        });
        //end
    }

    private void raisNotification(String titleMsg ,String bodyMsg){
        android.support.v4.app.NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(titleMsg)
                        .setContentText(bodyMsg);

        Intent notificationIntent = new Intent(this, PotatoList.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    private void addPotato(Potato potato){
        String key = db_context.child(DB_COLLECTION_POTATOES).push().getKey();
        HashMap<String, Object> potatoValues = new HashMap<>();
        potatoValues.put("name", potato.getName());
        potatoValues.put("id", key);
        db_context.child(DB_COLLECTION_POTATOES + "/" + key).updateChildren(potatoValues);
    }

    public void newPotato(View view){
        if (view.getId() == R.id.addButton) {
            EditText listText = (EditText) findViewById(R.id.newPotatoNameText);
            String text = listText.getText().toString();
            if(text.isEmpty()){
                showSimpleDialog();
                return;
            }
            addPotato(new Potato(text));
            TextView row = (TextView) findViewById(R.id.newPotatoNameText);
            row.setText("");

            //close keyboard ... weird bug that does not refresh the list until the keyboard is hidden
            InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public void showSimpleDialog() {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(PotatoList.this);
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
            String key = adapter.getItem(i).getId();
            Intent intent = new Intent(PotatoList.this, SecondaryActivity.class);
            intent.putExtra("selected-item", text);
            intent.putExtra("selected-item-key", key);
            startActivity(intent);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }
}
