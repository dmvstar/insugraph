package sf.net.dvstar.insugraph.adapters;

import android.graphics.Color;

import java.io.Serializable;

public class InsulinItem implements Serializable {
    public String insulin;
    public String dose;
    public String time;
    public String comment;
    public int color;

    public InsulinItem(String insulin, String dose, String time, String comment, int color){
        this.insulin = insulin;
        this.dose = dose;
        this.time = time;
        this.comment = comment;
        this.color = color;
    }

    public InsulinItem(String insulin, String dose, String time, String comment){
        this.insulin = insulin;
        this.dose = dose;
        this.time = time;
        this.comment = comment;
        this.color = Color.GRAY;
    }


}
