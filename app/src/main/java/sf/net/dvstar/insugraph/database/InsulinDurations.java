package sf.net.dvstar.insugraph.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by sdv on 04.10.15.
 */
@Table(name = "InsulinDurations")
public class InsulinDurations extends Model {

    @Column(name = "code", index = true, unique = true)
    public String code;

    @Column(name = "description", notNull = true)
    public String description;

}
