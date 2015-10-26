package sf.net.dvstar.insugraph.database;

import android.graphics.Color;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

@Table(name = "InsulinInjection")
public class InsulinInjection extends Model implements Serializable, InsulinCommonItem  {

    @Column(name = "insulin")
    public InsulinItem insulin;

    @Column(name = "dose")
    public String dose;

    @Column(name = "time")
    public String time;

    @Column(name = "date")
    public String date;

    @Column(name = "comment")
    public String comment;

    @Column(name = "color")
    public int color;

    @Column(name = "plan")
    public int plan;

    public static int INJECTION_PLAN_REGULAR = 1;
    public static int INJECTION_PLAN_ADDITIONAL = 2;


    public InsulinInjection() {
        super();
    }

    public InsulinInjection(InsulinItem insulin, String dose, String time, String comment, int plan, int color){
        super();
        this.insulin = insulin;
        this.dose = dose;
        this.time = time;
        this.comment = comment;
        this.plan = plan;
        this.color = color;
    }

    @Override
    public String getListText() {
        return "dose="+dose;
    }

/*
    public InsulinInjection(InsulinItem insulin, String dose, String time, String comment){
        this.insulin = insulin;
        this.dose = dose;
        this.time = time;
        this.comment = comment;
        this.color = Color.GRAY;
    }
*/

}
