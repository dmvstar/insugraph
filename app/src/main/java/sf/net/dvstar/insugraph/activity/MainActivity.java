package sf.net.dvstar.insugraph.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import sf.net.dvstar.insugraph.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private LineChart[] mCharts = new LineChart[4];
    private Typeface mTf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
*/
        //setContentView(R.layout.activity_colored_lines);
        setContentView(R.layout.activity_main);

        mCharts[0] = (LineChart) findViewById(R.id.chart1);
        mCharts[1] = (LineChart) findViewById(R.id.chart2);
        mCharts[2] = (LineChart) findViewById(R.id.chart3);
        mCharts[3] = (LineChart) findViewById(R.id.chart4);

        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");

        LineData data;


        for (int i = 0; i < mCharts.length; i++) {
            // add some transparency to the color with "& 0x90FFFFFF"
            if(i<2){
                data = getDataInsulin(i);
                data.setValueTypeface(mTf);
                setupChart(mCharts[i], data, mColors[i % mColors.length]);
            } else {
                data = getData(36, 100);
                data.setValueTypeface(mTf);
                setupChart(mCharts[i], data, mColors[i % mColors.length]);
            }
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private int[] mColors = new int[] {
            Color.rgb(137, 230, 81),
            Color.rgb(240, 240, 30),
            Color.rgb(89, 199, 250),
            Color.rgb(250, 104, 104
            )
    };

    private void setupChart(LineChart chart, LineData data, int color) {

        // no description text
        chart.setDescription("");
        chart.setNoDataTextDescription("You need to provide data for the chart.");

        // mChart.setDrawHorizontalGrid(false);
        //
        // enable / disable grid background
        chart.setDrawGridBackground(false);
//        chart.getRenderer().getGridPaint().setGridColor(Color.WHITE & 0x70FFFFFF);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setBackgroundColor(color);

        // set custom chart offsets (automatic offset calculation is hereby disabled)
        chart.setViewPortOffsets(10, 0, 10, 0);

        // add data
        chart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();
        l.setEnabled(false);

        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setEnabled(false);

        chart.getXAxis().setEnabled(false);

        // animate calls invalidate()...
        chart.animateX(2500);
    }

    protected String[] mMonths = new String[] {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"
    };

    protected double[] actrapid = new double[]{20.0/60.0,   1.00,   6.0};
    protected double[] protafan = new double[]{1.0,         4.00,   18.0};

    protected double[][] insulins = new double[][]{actrapid, protafan};

    // hours
    private double[] merge(double[] a, double[] b) {
        int N = a.length;
        int M = b.length;
        int size = M+N;
        for (double elementB : b){
            if (Arrays.binarySearch(a, elementB)>=0){
                size--;
            }
        }
        double[] c = new double[size];
        for (int i=0; i<a.length; i++){
            c[i]=a[i];
        }
        for (int i=a.length, j=0; j<b.length; j++){
            if (Arrays.binarySearch(c,b[j])<0){
                c[i]=b[j];
                i++;
            }
        }
        Arrays.sort(c);
        return c;
    }


    private ArrayList<Double> getXVals(int pos){
        ArrayList<Double> xVals = new ArrayList<Double>();
        double[] a = new double[24];
        for (int i = 0; i < 24; i++) a[i]=i;
        double[] b = insulins[pos];
        double[] c = merge(a,b);

        double[] cc = calculateIntervals(c);

        for (int i = 0; i < c.length; i++) xVals.add(c[i]);

        Log.v(TAG, "getXVals [" + pos + "]" + xVals.toString());
        return xVals;
    }

    private  LineData getDataInsulin(int pos) {
        ArrayList<Double> xValsD = getXVals(pos);
        ArrayList<String> xValsS = new ArrayList<String>();
        for (int v = 0;v<xValsD.size();v++)
            xValsS.add(""+xValsD.get(v));

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        if(pos<2){
            for (int v = 0;v<xValsD.size();v++) {

                int e=0;
                e = getExistValue(insulins[pos], xValsD.get(v).doubleValue());

                Log.v(TAG,"yVals["+v+"]["+xValsD.get(v).doubleValue()+"]["+e+"]");
                yVals.add(new Entry(e, v));
            }
        }

        Log.v(TAG, xValsS.size()+"-"+yVals.size());

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        set1.setLineWidth(1.75f);
        set1.setCircleSize(3f);
        set1.setColor(Color.WHITE);
        set1.setCircleColor(Color.WHITE);
        set1.setHighLightColor(Color.WHITE);
        set1.setDrawValues(false);

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xValsS, dataSets);

        return data;
    }

    private double[] calculateIntervals(double[] aSource){
        double[] ret = null;

        return ret;
    }

    private int getExistValue(double[] insulin, double aValue) {
        int ret = 0;

        for (int i = 0; i < insulin.length; i++) {
            double val = insulin[i];

            if(aValue == val) {
                if (i == 0) {
                    ret=1;
                }
                if (i == 1) {
                    ret=15;
                }
                if (i == 2) {
                    ret=1;
                }
            }
        }
        return ret;
    }


    private LineData getData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add(mMonths[i % 12]);
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range) + 3;
            yVals.add(new Entry(val, i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        set1.setLineWidth(1.75f);
        set1.setCircleSize(3f);
        set1.setColor(Color.WHITE);
        set1.setCircleColor(Color.WHITE);
        set1.setHighLightColor(Color.WHITE);
        set1.setDrawValues(false);

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        return data;
    }


}
