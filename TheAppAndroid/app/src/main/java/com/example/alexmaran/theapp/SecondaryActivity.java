package com.example.alexmaran.theapp;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import static android.content.ContentValues.TAG;
import static com.example.alexmaran.theapp.R.layout.secondary_activity;


public class SecondaryActivity extends Activity {
    TextView textView;
    private String potatoName;
    private String key;
    private float[] yData = {25.3f, 10.6f, 66.76f, 44.32f};
    private String[] xData = {"Potato1", "Potato2", "Potato3", "Potato3"};
    PieChart pieChart;

    final String DB_COLLECTION_POTATOES = "potatoes";
    private DatabaseReference db_context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(secondary_activity);

        db_context = FirebaseDatabase.getInstance().getReference();

        textView = (TextView) findViewById(R.id.textView);
        // get the intent from which this activity is called.
        Intent intent = getIntent();
        // fetch value from key-value pair and make it visible on TextView.
        String item = intent.getStringExtra("selected-item");
        key = intent.getStringExtra("selected-item-key");
        textView.setText("you selected " + item);
        potatoName = item;

        pieChart = (PieChart) findViewById(R.id.idPieChart);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Super Cool Chart");
        pieChart.setCenterTextSize(10);
        addDataSet();

    }

    public void UpdatePotato(View v) {
        if (v.getId() == R.id.Updatebutton) {
            EditText listText = (EditText) findViewById(R.id.updateField);
            String text = listText.getText().toString();
            if (text.isEmpty())
                return;
            HashMap<String, Object> potatoValues = new HashMap<>();
            potatoValues.put("name", text);
            potatoValues.put("id", key);
            db_context.child(DB_COLLECTION_POTATOES + "/" + key).updateChildren(potatoValues);
        }
        finish();
    }

    public void removePotato(View view) {
        db_context.child(DB_COLLECTION_POTATOES + "/" + key).removeValue();
        finish();
    }


    private void addDataSet() {
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for (int i = 0; i < yData.length; i++) {
            yEntrys.add(new PieEntry(yData[i], i));
        }

        for (int i = 1; i < xData.length; i++) {
            xEntrys.add(xData[i]);
        }
        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "potatoes something");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);
        pieDataSet.setColors(colors);
        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}
