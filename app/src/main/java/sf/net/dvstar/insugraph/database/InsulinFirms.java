package sf.net.dvstar.insugraph.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

/**
 * Created by sdv on 04.10.15.
 */
public class InsulinFirms extends Model {

    @Column(name = "code", index = true, unique = true)
    public String code;

    @Column(name = "name", notNull = true)
    public String name;

}
