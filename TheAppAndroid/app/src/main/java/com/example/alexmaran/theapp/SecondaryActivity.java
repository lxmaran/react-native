package com.example.alexmaran.theapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexmaran.theapp.db.MyDBHandler;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;
import static android.content.ContentValues.TAG;
import static com.example.alexmaran.theapp.R.id.deleteButton;
import static com.example.alexmaran.theapp.R.layout.secondary_activity;

/**
 * Created by Alex Maran on 11/20/2016.
 */

public class SecondaryActivity extends Activity {
    TextView textView;
    private String potatoName;

    private float[] yData = {25.3f, 10.6f, 66.76f, 44.32f};
    private String[] xData = {"Potato1", "Potato2" , "Potato3" , "Potato3"};
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(secondary_activity);

        textView = (TextView) findViewById(R.id.textView);
        // get the intent from which this activity is called.
        Intent intent = getIntent();

        // fetch value from key-value pair and make it visible on TextView.
        String item = intent.getStringExtra("selected-item");
        textView.setText("you selected " + item);
        potatoName = item;

        pieChart = (PieChart) findViewById(R.id.idPieChart);

        pieChart.setRotationEnabled(true);
        //pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Super Cool Chart");
        pieChart.setCenterTextSize(10);
        //pieChart.setDrawEntryLabels(true);
        //pieChart.setEntryLabelTextSize(20);
        //More options just check out the documentation!

        addDataSet();

    }

    private void addDataSet() {
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i] , i));
        }

        for(int i = 1; i < xData.length; i++){
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

    public void UpdatePotato(View v) {
        if (v.getId() == R.id.Updatebutton) {
            EditText listText = (EditText) findViewById(R.id.updateField);
            String text = listText.getText().toString();
            if(text.isEmpty())
                return;
            MyDBHandler dbContext = new MyDBHandler(this, null,
                    null, 1);
                dbContext.updatePotato(potatoName, text);
        }
        finish();
    }

    public void removePotato(View view) {
        MyDBHandler dbContext = new MyDBHandler(this, null,
                null, 1);

        if (view.getId() == deleteButton) {
            dbContext.deletePotato(potatoName);
        }
        finish();
    }

}
