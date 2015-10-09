package sf.net.dvstar.insugraph.insulin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sdv on 07.10.15.
 */
public class InsuGraphContent {


    private double mTimeInjection;
    private int mInsulinDose;

    public List<InsuGraphItem> mInsuGraphItemList;

    private GraphCoord mStartGC;
    private GraphCoord mMaximGC;
    private GraphCoord mStopsGC;

    private GraphCoordPair plotLineUp;
    private GraphCoordPair plotLineDn;
    /*
    private public double mStart;
    private public double mMaxim;
    private public double mStops;
    */
    private ArrayList<Double> mXValsD;
    private ArrayList<String> mXValsS;
    private ArrayList<Double> mYValsD;

    public InsuGraphContent(int aInsulinDose, double aTimeInjection){
        mInsulinDose = aInsulinDose;
        mTimeInjection = aTimeInjection;
    }

    public void calculateInsuGraphItems(double[] aXValues, double[] aInsulin){
        mInsuGraphItemList = null;
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


}
