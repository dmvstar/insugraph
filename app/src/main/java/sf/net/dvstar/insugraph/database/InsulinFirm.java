package sf.net.dvstar.insugraph.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by sdv on 04.10.15.
 */
@Table(name = "InsulinFirm")
public class InsulinFirm extends Model {

    /*
    id	code	name
1	NOVO	Novo Nordisk
    */

    public InsulinFirm(){
        super();
    }

    public InsulinFirm(String code, String name){
        super();
        this.code = code;
        this.name = name;
    }

    @Column(name = "code", index = true, unique = true)
    public String code;

    @Column(name = "name", notNull = true)
    public String name;

    @Override
    public String toString() {
        return "InsulinFirm{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
