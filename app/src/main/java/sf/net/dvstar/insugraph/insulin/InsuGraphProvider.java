package sf.net.dvstar.insugraph.insulin;

import java.util.ArrayList;
import java.util.List;

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
     * noralise the x data for time for all insugraph
      */
    public void noralizeXAxisValues(){
    }

    /**
     * Add
     * @param aInsulin
     * @param aInsulinDose
     * @param aTimeInjection
     */
    public void addInsulin(String aInsulinName, double[] aInsulin, int aInsulinDose, double aTimeInjection){

        InsuGraphContent insuGraphContent = new InsuGraphContent(aInsulinName, aInsulin, aInsulinDose, aTimeInjection);
        mInsuGraphContent.add(insuGraphContent);

    }

    public void addInsulin(InsulinWork aInsulin, int aInsulinDose, double aTimeInjection){

        InsuGraphContent insuGraphContent = new InsuGraphContent(aInsulin, aInsulinDose, aTimeInjection);
        mInsuGraphContent.add(insuGraphContent);

    }



    public InsuGraphContent getInsulinGraphContent( int index ){

        return mInsuGraphContent.get( index );

    }


}
