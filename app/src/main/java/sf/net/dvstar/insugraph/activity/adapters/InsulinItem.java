package sf.net.dvstar.insugraph.activity.adapters;

/**
 * Created by sdv on 16.10.15.
 */
public class InsulinItem {
    public String insulin;
    public String dose;
    public String time;
    public String comment;

    public InsulinItem(String insulin, String dose, String time, String comment){
        this.insulin = insulin;
        this.dose = dose;
        this.time = time;
        this.comment = comment;
    }

}
