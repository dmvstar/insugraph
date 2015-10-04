package sf.net.dvstar.insugraph.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

/**
 * Created by sdv on 04.10.15.
 */
public class InsulinTypes extends Model {

    @Column(name = "code", index = true, unique = true)
    public String code;

    @Column(name = "mtype", notNull = true )
    public String mtype;

    @Column(name = "stype", notNull = true)
    public String stype;

    @Column(name = "description", notNull = true)
    public String description;

    @Column(name = "durations", index = true, unique = true)
    public InsulinDurations durations;

}
