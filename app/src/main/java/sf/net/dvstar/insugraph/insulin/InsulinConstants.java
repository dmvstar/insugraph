package sf.net.dvstar.insugraph.insulin;

/**
 * Created by dstarzhynskyi on 09.10.2015.
 */
public interface InsulinConstants {

    // 1-start 2-max 3-end
    public static int WMODE_NON = 0;
    public static int WMODE_STT = 1;
    public static int WMODE_STW = 10;
    public static int WMODE_MAX = 2;
    public static int WMODE_MAW = 20;
    public static int WMODE_END = 3;

    public static int I_WORK_MODE_MINUTES = 1;
    public static int I_WORK_MODE_HOURS = 2;

    public static String S_WORK_MODE_MINUTES = "m";
    public static String S_WORK_MODE_HOURS = "h";

    public static int L_DIRECTION_UP = 1;
    public static int L_DIRECTION_DN = 2;

    public static String KEY_INTENT_EXTRA_INSULINS = "insulins";

}
