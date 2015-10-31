package sf.net.dvstar.insugraph.insulins;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


public class InsulinUtils {


    // hours
    public static double[] merge(double[] a, double[] b) {
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


    public static String mDateFormatString = "%02d.%02d.%02d";
    public static String mDateFormat = "dd.MM.yyyy";
    public static String mTimeFormat = "HH:mm";

    public static SimpleDateFormat mSimpleDateFormatDate = new SimpleDateFormat(mDateFormat);
    public static SimpleDateFormat mSimpleTimeFormatDate = new SimpleDateFormat(mTimeFormat);

    public static String getDateText(Date date) {
        String ret = "";
        if(date !=null)
            ret = mSimpleDateFormatDate.format(date);
        return ret;
    }

    public static String getTimeText(Date date) {
        String ret = "";
        if(date !=null)
            ret = mSimpleTimeFormatDate.format(date);
        return ret;
    }


    public static String getDateText(int year, int monthOfYear, int dayOfMonth) {
        return String.format(mDateFormatString, dayOfMonth , monthOfYear, year);
    }


    public static Date parseTimeText(String time) {
        Date ret = null;//Calendar.getInstance().getTime();

        try {
            ret = mSimpleTimeFormatDate.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static Date parseDateText(String date) {
        Date ret = null;//Calendar.getInstance().getTime();

        try {
            ret = mSimpleDateFormatDate.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ret;
    }
}




