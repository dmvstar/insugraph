package sf.net.dvstar.insugraph.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Table;

import java.io.Serializable;


@Table(name = "InsulinInjection")
public class GlucoseReading extends Model implements Serializable, ActionCommonItem {


    @Override
    public String getListText() {
        return "";
    }

    @Override
    public int getActionType() {
        return ACTION_TYPE_GLUCOSE;
    }
}
