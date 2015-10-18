package sf.net.dvstar.insugraph.database;

import android.graphics.Color;

import com.activeandroid.annotation.Table;

import java.io.Serializable;

@Table(name = "InsulinInjection")
public class InsulinInjection implements Serializable {
    public InsulinItem insulin;
    public String dose;
    public String time;
    public String comment;
    public int color;
    public int planned;

    public static int INJECTION_PLAN_REGULAR = 1;
    public static int INJECTION_PLAN_ADDITIONAL = 2;

    public InsulinInjection(InsulinItem insulin, String dose, String time, String comment, int planned, int color){
        this.insulin = insulin;
        this.dose = dose;
        this.time = time;
        this.comment = comment;
        this.planned = planned;
        this.color = color;
    }

    public InsulinInjection(InsulinItem insulin, String dose, String time, String comment){
        this.insulin = insulin;
        this.dose = dose;
        this.time = time;
        this.comment = comment;
        this.color = Color.GRAY;
    }


}
