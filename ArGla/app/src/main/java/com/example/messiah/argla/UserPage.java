package com.example.messiah.argla;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class UserPage extends AppCompatActivity {

    String FIRE_URL = "https://jo-android-chat.firebaseio.com/users/";
    String FIRE_URL_ME = "https://jo-android-chat.firebaseio.com/users/" + MainActivity.userName;
    String FIRE_URL_WORLD = "https://jo-android-chat.firebaseio.com/worldchat/";

    int messageNumber = 0;
    int messageMe = 0;

    private FrameLayout mainLayout;
    private FrameLayout mainLayout_1;
    private FrameLayout mainLayout_2;
    private LineChart mChart;
    private LineChart mChart1;
    private LineChart mChart2;

    long neutralMeter;
    long happyMeter;
    long upsetMeter;

    String toUser = MainActivity.userName;

    Firebase worldMessage = new Firebase(FIRE_URL_WORLD);
    Firebase worldMessage2 = new Firebase(FIRE_URL_WORLD + messageNumber);
    Firebase meMessage = new Firebase (FIRE_URL_ME + "/message");

    Firebase href = new Firebase(FIRE_URL + toUser + "/happy");
    Firebase uref = new Firebase(FIRE_URL + toUser + "/upset");
    Firebase nref = new Firebase(FIRE_URL + toUser + "/neutral");
    Firebase mref = new Firebase(FIRE_URL + toUser + "/message/");

    ListView listView;
    ListView listViewMe;

    List<String> values = new ArrayList<String>();
    List<String> valuesMe = new ArrayList<String>();

    private FrameLayout mainLayout2;
    private PieChart pChart;
    // going to display pie chart for smart phones  market shares
    private float[] yData = {0, 0, 0};
    private String[] xData = {"Happy", "Neutral", "Upset"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        Firebase.setAndroidContext(this);

        System.out.println(this);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("To Me");
        tabSpec.setContent(R.id.toMe);
        tabSpec.setIndicator("To Me");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("To Person");
        tabSpec.setContent(R.id.toPerson);
        tabSpec.setIndicator("To Person");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("To World");
        tabSpec.setContent(R.id.toWorld);
        tabSpec.setIndicator("To World");
        tabHost.addTab(tabSpec);

        listView = (ListView) findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);


        listViewMe = (ListView) findViewById(R.id.list2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, valuesMe);
        listViewMe.setAdapter(adapter2);

        mainLayout = (FrameLayout) findViewById(R.id.mainLayout);
        // Create Line Chart
        mChart = new LineChart(this);
        // add to main layout
        mainLayout.addView(mChart);
        // cusomize line chart
        mChart.setDescription("");
        mChart.setNoDataTextDescription("No data for the moment");
        // enable value highlighting
        mChart.setHighlightPerTapEnabled(true);
        // enable touch gestures
        mChart.setTouchEnabled(true);
        // scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        // enable pinch zoom to avoid scaling x and y axis separately
        mChart.setPinchZoom(true);
        // alternative background color
        mChart.setBackgroundColor(Color.argb(00, 153, 229, 255));
        // data
        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);
        // add data to line chart
        mChart.setData(data);
        // get legend object
        Legend one = mChart.getLegend();
        // cutomize legend
        one.setForm(Legend.LegendForm.LINE);
        one.setTextColor(Color.WHITE);
        XAxis x1 = mChart.getXAxis();
        x1.setTextColor(Color.WHITE);
        x1.setDrawGridLines(false);
        x1.setAvoidFirstLastClipping(true);
        YAxis y1 = mChart.getAxisLeft();
        y1.setTextColor(Color.WHITE);
        y1.setAxisMaxValue(20f);
        y1.setDrawGridLines(true);
        YAxis y12 = mChart.getAxisRight();
        y12.setEnabled(false);

        mainLayout_1 = (FrameLayout) findViewById(R.id.mainLayout_1);
        // Create Line Chart
        mChart1 = new LineChart(this);
        // add to main layout
        mainLayout_1.addView(mChart1);
        // cusomize line chart
        mChart1.setDescription("");
        mChart1.setNoDataTextDescription("No data for the moment");
        // enable value highlighting
        mChart1.setHighlightPerTapEnabled(true);
        // enable touch gestures
        mChart1.setTouchEnabled(true);
        // scaling and dragging
        mChart1.setDragEnabled(true);
        mChart1.setScaleEnabled(true);
        mChart1.setDrawGridBackground(false);
        // enable pinch zoom to avoid scaling x and y axis separately
        mChart1.setPinchZoom(true);
        // alternative background color
        mChart1.setBackgroundColor(Color.argb(00, 153, 229, 255));
        // data
        LineData data1 = new LineData();
        data1.setValueTextColor(Color.WHITE);
        // add data to line chart
        mChart1.setData(data1);
        // get legend object
        Legend one1 = mChart1.getLegend();
        // cutomize legend
        one1.setForm(Legend.LegendForm.LINE);
        one1.setTextColor(Color.WHITE);
        XAxis x11 = mChart1.getXAxis();
        x11.setTextColor(Color.WHITE);
        x11.setDrawGridLines(false);
        x11.setAvoidFirstLastClipping(true);
        YAxis y1211 = mChart1.getAxisLeft();
        y1211.setTextColor(Color.WHITE);
        y1211.setAxisMaxValue(20);
        y1211.setDrawGridLines(true);
        YAxis y1221 = mChart1.getAxisRight();
        y1221.setEnabled(false);

        mainLayout_2 = (FrameLayout) findViewById(R.id.mainLayout_2);
        // Create Line Chart
        mChart2 = new LineChart(this);
        // add to main layout
        mainLayout_2.addView(mChart2);
        // cusomize line chart
        mChart2.setDescription("");
        mChart2.setNoDataTextDescription("No data for the moment");
        // enable value highlighting
        mChart2.setHighlightPerTapEnabled(true);
        // enable touch gestures
        mChart2.setTouchEnabled(true);
        // scaling and dragging
        mChart2.setDragEnabled(true);
        mChart2.setScaleEnabled(true);
        mChart2.setDrawGridBackground(false);
        // enable pinch zoom to avoid scaling x and y axis separately
        mChart2.setPinchZoom(true);
        // alternative background color
        mChart2.setBackgroundColor(Color.argb(00, 153, 229, 255));
        // data
        LineData data2 = new LineData();
        data2.setValueTextColor(Color.WHITE);
        // add data to line chart
        mChart2.setData(data2);
        // get legend object
        Legend one2 = mChart2.getLegend();
        // cutomize legend
        one2.setForm(Legend.LegendForm.LINE);
        one2.setTextColor(Color.WHITE);
        XAxis x12 = mChart2.getXAxis();
        x12.setTextColor(Color.WHITE);
        x12.setDrawGridLines(false);
        x12.setAvoidFirstLastClipping(true);
        YAxis y121 = mChart2.getAxisLeft();
        y121.setTextColor(Color.WHITE);
        y121.setAxisMaxValue(20);
        y121.setDrawGridLines(true);
        YAxis y122 = mChart2.getAxisRight();
        y122.setEnabled(false);


        // pie chart
        mainLayout2 = (FrameLayout) findViewById(R.id.mainLayout2);
        pChart = new PieChart(this);
        //add pie chart to main layout
        mainLayout2.addView(pChart);

        // enable hole and configure
        pChart.setDrawHoleEnabled(true);
        pChart.setHoleColorTransparent(true);
        pChart.setHoleRadius(7);
        pChart.setTransparentCircleRadius(10);
        pChart.setDescription("");

        //Enable rotation of chart by touch
        pChart.setRotationAngle(0);
        pChart.setRotationEnabled(true);

        //set a chart value selected listener
        pChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // display msg when value selected
                if (e == null) {
                    return;
                }
            }

            @Override
            public void onNothingSelected() {

            }

        });

        // add data
        addData();

        // legends
        Legend l = pChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // simulate real time data addition
        new Thread(new Runnable() {
            @Override
            public void run() {
                // add 100 entries
                for(int i = 0; i < 100; ++i) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addEntry(); // chart is notified of update in addEntry method
                            addEntry1();
                            addEntry2(); // chart is notified of update in addEntry method
                        }
                    });

                    // pause between each add
                    try {
                        Thread.sleep(1200);
                    } catch (InterruptedException e) {
                        // manage error ...
                    }

                }
            }

        }).start();
    }

    @Override
    protected void onStart() {

        super.onStart();

        Button happyB = (Button) findViewById(R.id.happyButton);
        Button upsetB = (Button) findViewById(R.id.upsetButton);
        Button neutralB = (Button) findViewById(R.id.neutralButton);

        final TextView title = (TextView) findViewById(R.id.userTitle);

        Firebase ref = new Firebase(FIRE_URL + MainActivity.userName + "/userName");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String newUser = (String) dataSnapshot.getValue();
                title.setText(newUser);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        href.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                happyMeter = (long) dataSnapshot.getValue();
                System.out.println("HAPPY: " + happyMeter);
                yData[0] = happyMeter;
                addData();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        uref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                upsetMeter = (long) dataSnapshot.getValue();
                System.out.println("HAPPY: " + upsetMeter);
                yData[2] = upsetMeter;
                addData();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        nref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                neutralMeter = (long) dataSnapshot.getValue();
                System.out.println("NEUTRAL: " + neutralMeter);
                yData[1] = neutralMeter;
                addData();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        happyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userText = (EditText) findViewById(R.id.userTo);
                toUser = userText.getText().toString();

                href = new Firebase(FIRE_URL + toUser + "/happy");
                href.setValue(++happyMeter);

                EditText userMessage = (EditText) findViewById(R.id.userFeeling);
                String message = userMessage.getText().toString();

                mref = new Firebase(FIRE_URL + toUser + "/message/" + messageMe++);
                mref.setValue(message);
            }
        });

        neutralB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userText = (EditText) findViewById(R.id.userTo);
                toUser = userText.getText().toString();

                nref = new Firebase(FIRE_URL + toUser + "/neutral");
                nref.setValue(++neutralMeter);

                EditText userMessage = (EditText) findViewById(R.id.userFeeling);
                String message = userMessage.getText().toString();

                mref = new Firebase(FIRE_URL + toUser + "/message/" + messageMe++);
                mref.setValue(message);
            }
        });

        upsetB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userText = (EditText) findViewById(R.id.userTo);
                toUser = userText.getText().toString();

                uref = new Firebase(FIRE_URL + toUser + "/upset");
                uref.setValue(++upsetMeter);

                EditText userMessage = (EditText) findViewById(R.id.userFeeling);
                String message = userMessage.getText().toString();

                mref = new Firebase(FIRE_URL + toUser + "/message/" + messageMe++);
                mref.setValue(message);
            }
        });

        Button toWorld = (Button) findViewById(R.id.sendToWorld);

        toWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userText = (EditText) findViewById(R.id.userFeelingWorld);
                String toWorldMessage = userText.getText().toString();

                worldMessage2 = new Firebase(FIRE_URL_WORLD + ++messageNumber);
                worldMessage2.setValue(toWorldMessage);
            }
        });

        worldMessage.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                values.add((String) dataSnapshot.getValue());
                ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        meMessage.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                valuesMe.add((String) dataSnapshot.getValue());
                ((BaseAdapter) listViewMe.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void showP(View v) {
        FrameLayout p = (FrameLayout) findViewById(R.id.mainLayout2);
        FrameLayout g2 = (FrameLayout) findViewById(R.id.mainLayout_2);
        FrameLayout g1 = (FrameLayout) findViewById(R.id.mainLayout_1);
        FrameLayout g = (FrameLayout) findViewById(R.id.mainLayout);

        p.setVisibility(p.VISIBLE);
        g2.setVisibility(g2.INVISIBLE);
        g1.setVisibility(g1.INVISIBLE);
        g.setVisibility(g.INVISIBLE);
    }

    public void showG(View v) {
        FrameLayout p = (FrameLayout) findViewById(R.id.mainLayout2);
        FrameLayout g2 = (FrameLayout) findViewById(R.id.mainLayout_2);
        FrameLayout g1 = (FrameLayout) findViewById(R.id.mainLayout_1);
        FrameLayout g = (FrameLayout) findViewById(R.id.mainLayout);

        p.setVisibility(p.INVISIBLE);
        g2.setVisibility(g2.VISIBLE);
        g1.setVisibility(g1.VISIBLE);
        g.setVisibility(g.VISIBLE);
    }

    // create method to add entry to the line chart
    private void addEntry() {
        LineData data = mChart.getData();
        if (data!= null) {
            LineDataSet set = (LineDataSet) data.getDataSetByIndex(0);

            if(set == null) {
                // creation if null
                set = createSet();
                data.addDataSet(set);
            }

            // add a new random value
            data.addXValue("");
            data.addEntry(new Entry((float) (Math.random() * 15) + 5f, set.getEntryCount()), 0);

            // enable the way chart know when its data changes
            mChart.notifyDataSetChanged();

            // limit number of visible entries
            mChart.setVisibleXRange(0f, 6f);

            // scroll to the last entry
            mChart.moveViewToX(data.getXValCount() - 7);
        }
    }

    // method to create set
    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "Upset");
        set.setDrawCubic(true);
        set.setCubicIntensity(0.2f);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(ColorTemplate.getHoloBlue());
        set.setLineWidth(2f);
        set.setCircleSize(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 177));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(10f);

        return set;
    }

    // create method to add entry to the line chart
    private void addEntry1() {
        LineData data = mChart1.getData();
        if (data!= null) {
            LineDataSet set = (LineDataSet) data.getDataSetByIndex(0);

            if(set == null) {
                // creation if null
                set = createSet1();
                data.addDataSet(set);
            }

            // add a new random value
            data.addXValue("");
            data.addEntry(new Entry((float) (Math.random() * 15) + 1f, set.getEntryCount()), 0);

            // enable the way chart know when its data changes
            mChart1.notifyDataSetChanged();

            // limit number of visible entries
            mChart1.setVisibleXRange(0f, 6f);

            // scroll to the last entry
            mChart1.moveViewToX(data.getXValCount() - 7);
        }
    }

    // method to create set
    private LineDataSet createSet1() {
        LineDataSet set = new LineDataSet(null, "Neutral");
        set.setDrawCubic(true);
        set.setCubicIntensity(0.2f);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.rgb("#F0E68C"));
        set.setCircleColor(ColorTemplate.rgb("#F0E68C"));
        set.setLineWidth(2f);
        set.setCircleSize(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.rgb("#F0E68C"));
        set.setHighLightColor(Color.rgb(240,230,140));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(10f);

        return set;
    }

    // create method to add entry to the line chart
    private void addEntry2() {
        LineData data = mChart2.getData();
        if (data!= null) {
            LineDataSet set = (LineDataSet) data.getDataSetByIndex(0);

            if(set == null) {
                // creation if null
                set = createSet2();
                data.addDataSet(set);
            }

            // add a new random value
            data.addXValue("");
            data.addEntry(new Entry((float) (Math.random() * 15) + 1f, set.getEntryCount()), 0);

            // enable the way chart know when its data changes
            mChart2.notifyDataSetChanged();

            // limit number of visible entries
            mChart2.setVisibleXRange(0f, 6f);

            // scroll to the last entry
            mChart2.moveViewToX(data.getXValCount() - 7);
        }
    }

    // method to create set
    private LineDataSet createSet2() {
        LineDataSet set = new LineDataSet(null, "Happy");
        set.setDrawCubic(true);
        set.setCubicIntensity(0.2f);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.rgb("#7FFF00"));
        set.setCircleColor(ColorTemplate.rgb("#7FFF00"));
        set.setLineWidth(2f);
        set.setCircleSize(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.rgb("#7FFF00"));
        set.setHighLightColor(Color.rgb(127,255,0));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(10f);

        return set;
    }


    // pie chart data adding
    private void addData() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < yData.length; ++i) yVals1.add(new Entry(yData[i], 1));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.length; i++) xVals.add(xData[i]);

        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS) colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS) colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS) colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        //data.setValueFormatter(new PercentFormatter());
        data. setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        pChart.setData(data);

        pChart.highlightValues(null);

        pChart.invalidate();
    }
}
