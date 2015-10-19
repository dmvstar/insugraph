package sf.net.dvstar.insugraph.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by sdv on 04.10.15.
 */
@Table(name = "InsulinType")
public class InsulinType extends Model {

    /*
    id	code	duration	mtype	stype	description
    1	UHN	1	H	H	Human
     */

    public InsulinType(){
        super();
    }

    public InsulinType(String code, InsulinDuration iInsulinDuration, String mtype, String stype, String desc) {
        super();
        this.code = code;
        this.durations = iInsulinDuration;
        this.mtype = mtype;
        this.stype = stype;
        this.description = desc;
    }

    @Column(name = "code", index = true, unique = true)
    public String code;

    @Column(name = "mtype", notNull = true )
    public String mtype;

    @Column(name = "stype", notNull = true)
    public String stype;

    @Column(name = "description", notNull = true)
    public String description;

    @Column(name = "durations", notNull = true)
    public InsulinDuration durations;

}
