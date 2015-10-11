package sf.net.dvstar.insugraph.insulin;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sdv on 07.10.15.
 */
public class InsuGraphContent {


    private static final String TAG = "InsuGraphContent";

    //private double[] mInsulin;
    private String mInsulinName;
    private InsulinWork mInsulinWork;
    private double mTimeInjection;
    private int mInsulinDose;

    public List<InsuGraphItem> mInsuGraphItemList;

    private GraphCoord mStartGC;
    private GraphCoord mMaximGC;
    private GraphCoord mStopsGC;

    private GraphCoordPair plotLineUp;
    private GraphCoordPair plotLineDn;

    private ArrayList<Double> mXValsD;
    private ArrayList<String> mXValsS;
    private ArrayList<Double> mYValsD;


    public InsuGraphContent(InsulinWork aInsulinWork, int aInsulinDose, double aTimeInjection) {
        mInsulinDose = aInsulinDose;
        mTimeInjection = aTimeInjection;
        mInsulinWork = aInsulinWork;
        calculateInsuGraphItems(getXAsis());
    }

    /*
        public InsuGraphContent(double[] aInsulin, int aInsulinDose, double aTimeInjection){
            this("", aInsulin, aInsulinDose, aTimeInjection);
        }
    */
    public void calculateInsuGraphItems(double[] aXValues){
        mInsuGraphItemList = null;
        double[] aInsulin = mInsulinWork.getDoubleArray();

        if (aXValues != null && aInsulin != null && aXValues.length>0){
            mInsuGraphItemList = new ArrayList<InsuGraphItem>();
            int working = 0;
            for (int i = 0; i < aXValues.length; i++) {
                InsuGraphItem item = new InsuGraphItem();
                item.xValue = aXValues[i];
                item.yValue = 0;
                if(working>0) item.wMode = working;

                for (int j = 0; j < aInsulin.length; j++) {
                    double val = aInsulin[j];

                    if(aXValues[i] == val) {
                        if (j == InsuGraphItem.WMODE_STT-1) {
                            item.wMode = InsuGraphItem.WMODE_STT;
                            working = InsuGraphItem.WMODE_STW;

                            mStartGC = new GraphCoord( aXValues[i], 0.0, 0.0 );
                        }
                        if (j == InsuGraphItem.WMODE_MAX-1) {
                            item.wMode = InsuGraphItem.WMODE_MAX;
                            item.yValue = 1;
                            mMaximGC = new GraphCoord( aXValues[i], 1.0, 1.0);

                            working = InsuGraphItem.WMODE_MAW;
                        }
                        if (j == InsuGraphItem.WMODE_END-1) {
                            item.wMode = InsuGraphItem.WMODE_END;
                            working = InsuGraphItem.WMODE_NON;
                            mStopsGC = new GraphCoord( aXValues[i], 0.0, 0.0 );

                        }
                    }
                }
                mInsuGraphItemList.add(item);
            }

            plotLineUp = new GraphCoordPair(mStartGC, mMaximGC);
            plotLineDn = new GraphCoordPair(mMaximGC, mStopsGC);

            calculateInsuGraphAdditons(plotLineUp);
            calculateInsuGraphAdditons(plotLineDn);

            mXValsD = new ArrayList<>();
            mXValsS = new ArrayList<>();
            mYValsD = new ArrayList<>();

            for( InsuGraphItem item : mInsuGraphItemList ) {
                mXValsD.add(item.xValue);
                mXValsS.add(""+item.xValue);
                mYValsD.add(item.yValue);
            }
        }
    }

    private void calculateInsuGraphAdditons(GraphCoordPair plotLine) {
        double x,y,x_1,y_1,x_2,y_2;

        x_1 = plotLine.first.mX;
        x_2 = plotLine.second.mX;

        y_1 = plotLine.first.mY;
        y_2 = plotLine.second.mY;

        for ( int i=0; i<mInsuGraphItemList.size();i++) {
            InsuGraphItem item = mInsuGraphItemList.get(i);
            if (item.xValue > x_1 && item.xValue <x_2){
                x=item.xValue;
                y = (y_1*(x_2-x_1) + (x-x_1)*(y_2-y_1))
                        /
                        (x_2-x_1);
                y = addDistortion(y,plotLine);
                item.yValue = y;

                mInsuGraphItemList.set(i,item);
            }
        }
    }


    /**
     * Add distortion to y value
     * @param y
     * @param plotLine
     * @return
     */
    private double addDistortion(double y, GraphCoordPair plotLine) {

        return y;

    }

    public String toString(){
        return "Start=["+ mStartGC.mX +"] Max=["+ mMaximGC.mX +"] End=["+ mStopsGC.mX +"] Values="+mInsuGraphItemList;
    }

    public ArrayList<Double> getXValsD() {
        return mXValsD;
    }

    public ArrayList<String> getXValsS() {
        return mXValsS;
    }

    public ArrayList<Double> getYValsD() {
        return mYValsD;
    }


    private LineDataSet getDataSetInsulin() {
        ArrayList<Double> xValsD = getXValsD();
        ArrayList<String> xValsS = getXValsS();
        ArrayList<Double> yValsD = getYValsD();
        ArrayList<Entry> yValsE = new ArrayList<Entry>();
        for (int i=0; i<yValsD.size(); i++){
            double val = yValsD.get(i);
            yValsE.add(new Entry((float)val, i));
        }
        Log.v(TAG, "!!!!" + xValsD.size() + "-" + xValsS.size() + "-" + yValsE.size());
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yValsE, "Insulin "+mInsulinName);
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);
        set1.setLineWidth(1.75f);
        set1.setCircleSize(3f);
        set1.setColor(Color.WHITE);
        set1.setCircleColor(Color.WHITE);
        set1.setHighLightColor(Color.WHITE);
        set1.setDrawValues(false);
        set1.setDrawCubic(true);
        set1.setDrawFilled(true);
        return set1;
    }



    public LineData getLineDataInsulin() {
        LineData data = null;
        ArrayList<String> xValsS = getXValsS();
        LineDataSet set1 = getDataSetInsulin();
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets
        // create a data object with the datasets
        data = new LineData(xValsS, dataSets);
        return data;
    }

    public String getInsulinName() {
        return mInsulinName;
    }

    public static class GraphCoord {
        public double mX;
        public double mY;
        public double mV;

        public GraphCoord(double aX,double aY,double aV){
            mX=aX;
            mY=aY;
            mV=aV;
        }
    }

    public static class GraphCoordPair {

        public final GraphCoord first;
        public final GraphCoord second;

        public GraphCoordPair(GraphCoord first, GraphCoord second){
            this.first = first;
            this.second = second;
        }
    }

    public static class InsuGraphItem implements InsulinConstants {
        public int wMode;
        public double xValue;
        public double yValue;

        public String toString(){
            return "["+wMode+"]["+xValue+"]["+yValue+"]";
        }
    }

    private double[] getXAsis (){
        double[] xAxisHours = new double[24];
        for (int i = 0; i < 24; i++) xAxisHours[i]=i;

        double[] bInsulin = mInsulinWork.getDoubleArray();

        double[] ret = InsulinUtils.merge(xAxisHours, bInsulin);
        return ret;
    }


}
