package sf.net.dvstar.insugraph.insulin;

/**
 * Created by dstarzhynskyi on 09.10.2015.
 */
public interface InsulinConstants {

    // 1-start 2-max 3-end
    int WMODE_NON = 0;
    int WMODE_STT = 1;
    int WMODE_STW = 10;
    int WMODE_MAX = 2;
    int WMODE_MAW = 20;
    int WMODE_END = 3;

    int I_WORK_MODE_MINUTES = 1;
    int I_WORK_MODE_HOURS = 2;

    String S_WORK_MODE_MINUTES = "m";
    String S_WORK_MODE_HOURS = "h";

    int L_DIRECTION_UP = 1;
    int L_DIRECTION_DN = 2;

    String KEY_INTENT_EXTRA_INSULINS = "insulins";

}
