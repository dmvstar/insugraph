package sf.net.dvstar.insugraph.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.buzzingandroid.ui.HSVColorPickerDialog;

import java.util.List;

import sf.net.dvstar.insugraph.R;
import sf.net.dvstar.insugraph.adapters.InsulinDescAdapter;
import sf.net.dvstar.insugraph.database.InsulinItem;

public class InsulinDescActivity extends AppCompatActivity {


    Button btColor;
    LinearLayout llColor;
    ListView insulinListView;
    private List<InsulinItem> mInsulins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insulin_desc);

        btColor = (Button) findViewById(R.id.bt_color);
        llColor = (LinearLayout) findViewById(R.id.ll_color);

        ListView listView = (ListView) findViewById(R.id.insulin_desc_list);

        mInsulins = getInsulins();

        InsulinDescAdapter iInsulinDescAdapter = new InsulinDescAdapter(this, mInsulins);
        listView.setAdapter(iInsulinDescAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }



    private  int mStoredColor = 0xFF4488AA;

    public void selectColor(View view){
        HSVColorPickerDialog cpd = new HSVColorPickerDialog( this, mStoredColor, new HSVColorPickerDialog.OnColorSelectedListener() {
            @Override
            public void colorSelected(Integer color) {
                mStoredColor = color;
                llColor.setBackgroundColor( color );
            }
        });
        cpd.setTitle("Pick a color");
        cpd.show();
    }

    public void cancel(View view){
        finish();
    }

    public List<InsulinItem> getInsulins() {

        List<InsulinItem> insulinList = new Select().from(InsulinItem.class).execute();

        return insulinList;
    }
}
