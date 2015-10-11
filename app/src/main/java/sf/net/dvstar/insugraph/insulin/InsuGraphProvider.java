package sf.net.dvstar.insugraph.insulin;

import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by dstarzhynskyi on 09.10.2015.
 */
public class InsuGraphProvider {

    public InsuGraphProvider(){
        mInsuGraphContent = new ArrayList<InsuGraphContent>();
    }

    /**
     * List of all insulin graph
     */
    private List<InsuGraphContent> mInsuGraphContent;
    /**
     * Combined data for all insulins
     */
    private InsuGraphContent mInsuGraphCombined;
    /**
     * Summary data for all insulins
     */
    private InsuGraphContent mInsuGraphSummary;

    private double[] mXAxisValues;

    /**
     * normalise the x data for time for all insugraph
      */
    public void normalizeXAxisValues(){

        double[] normalizedXAxisValues = new double[0];
        int counter=0;
        // calculate
        for (ListIterator<InsuGraphContent> it = mInsuGraphContent.listIterator(); it.hasNext(); ) {
            InsuGraphContent insuGraphContent = it.next();
            double[] current  = insuGraphContent.getXAsisValues();
            if(counter==0) {
                normalizedXAxisValues = current;
            } else {
                InsulinUtils.merge(current, normalizedXAxisValues);
            }
            counter++;
        }
        // store to
        for (ListIterator<InsuGraphContent> it = mInsuGraphContent.listIterator(); it.hasNext(); ) {
            InsuGraphContent insuGraphContent = it.next();
            insuGraphContent.setXAsisValues(normalizedXAxisValues);
            insuGraphContent.calculateInsuGraphItems();
        }

    }

    public void addInsulin(InsulinWork aInsulin, int aInsulinDose, double aTimeInjection){
        InsuGraphContent insuGraphContent = new InsuGraphContent(aInsulin, aInsulinDose, aTimeInjection);
        mInsuGraphContent.add(insuGraphContent);
    }

    public void createSummaryInsulin(){

    }

    public LineData getLineDataCombineInsulin() {
        LineData data = null;
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        ArrayList<String> xValsS = null;
        for (ListIterator<InsuGraphContent> it = mInsuGraphContent.listIterator(); it.hasNext(); ) {
            InsuGraphContent insuGraphContent = it.next();
            xValsS = insuGraphContent.getXValsS();
            LineDataSet lineDataSet = insuGraphContent.getDataSetInsulin();
            dataSets.add(lineDataSet); // add the datasets
        }
        // create a data object with the datasets
        data = new LineData(xValsS, dataSets);
        return data;
    }

    public String getLineDataCombineInsulinNames() {
        String data = "";
        for (ListIterator<InsuGraphContent> it = mInsuGraphContent.listIterator(); it.hasNext(); ) {
            data += it.next().getInsulinName()+" ";
        }
        return data;
    }

    public InsuGraphContent getInsulinGraphContent( int index ){
        return mInsuGraphContent.get( index );
    }

}
