package sf.net.dvstar.insugraph.insulin;

import java.util.Arrays;

/**
 * Created by dstarzhynskyi on 10.10.2015.
 */
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






}




