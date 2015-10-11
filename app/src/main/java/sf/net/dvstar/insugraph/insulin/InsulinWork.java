package sf.net.dvstar.insugraph.insulin;


/**
 * Created by sdv on 11.10.15.
 */

public class InsulinWork implements InsulinConstants{
    private InsulinTime[] mInsulinTimes;
    private String mName;
    private String mFirm;
    private int    mWorkKind;

    public InsulinWork(String aName, InsulinTime[] aInsulinTimes){
        this.setName(aName);
        this.setInsulinTime(aInsulinTimes);
        this.mWorkKind = I_WORK_MODE_HOURS;
    }

    public InsulinTime[] getInsulinTime() {
        return mInsulinTimes;
    }

    public void setInsulinTime(InsulinTime[] mInsulinTimes) {
        this.mInsulinTimes = mInsulinTimes;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getFirm() {
        return mFirm;
    }

    public void setFirm(String mFirm) {
        this.mFirm = mFirm;
    }


    public static class InsulinTime {

        double mWorkTime;
        int mWorkKind;

        public InsulinTime(double aWorkTime, String aWorkKind){
            this.mWorkTime=aWorkTime;
            this.mWorkKind = I_WORK_MODE_HOURS;
            if(aWorkKind.equals("m")) this.mWorkKind = I_WORK_MODE_MINUTES;
        }

    }
}
