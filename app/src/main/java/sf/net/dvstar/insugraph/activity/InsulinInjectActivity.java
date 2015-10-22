package sf.net.dvstar.insugraph.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sf.net.dvstar.insugraph.R;
import sf.net.dvstar.insugraph.database.InsulinInjection;
import sf.net.dvstar.insugraph.adapters.InsulinInjectAdapter;
import sf.net.dvstar.insugraph.database.InsulinItem;
import sf.net.dvstar.insugraph.insulin.InsulinConstants;

/**
 * Created by sdv on 15.10.15.
 */
public class InsulinInjectActivity extends AppCompatActivity {


    private static final String TAG = "InsulinInjectActivity";
    private ArrayList<InsulinInjection> mInsulinsInjections;

    private TextView currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insulin_inject);

        ListView listView = (ListView) findViewById(R.id.insulin_inject_list);
        currentDate = (TextView) findViewById(R.id.tv_injection_date_text);

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        currentDate.setText( sdf.format(today));

        mInsulinsInjections = getInsulinsInjections();

        listView.setAdapter(new InsulinInjectAdapter(this, mInsulinsInjections));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddInsulinsInjection(view);
            }
        });

    }

    private ArrayList<InsulinInjection> getInsulinsInjections() {
        ArrayList<InsulinInjection> ret = new ArrayList<>();

        List<InsulinItem> insulins = new Select().from(InsulinItem.class).execute();

        Log.v(TAG, "!!!!!!!!"+insulins.toString() );

        ret.add(new InsulinInjection(new InsulinItem("actrapid", Color.YELLOW), "8", "8:00", "Morning", InsulinInjection.INJECTION_PLAN_REGULAR, Color.YELLOW));
        ret.add(new InsulinInjection(new InsulinItem("protafan", Color.GREEN), "16", "8:00", "Morning", InsulinInjection.INJECTION_PLAN_REGULAR, Color.GREEN));
        ret.add(new InsulinInjection(new InsulinItem("novorapid", Color.parseColor("#FF9000")), "8", "14:00", "Dinner", InsulinInjection.INJECTION_PLAN_ADDITIONAL, Color.parseColor("#FF9000")));
        ret.add(new InsulinInjection(new InsulinItem("levemir", Color.parseColor("#33CCCC")), "10", "18:00", "Mid", InsulinInjection.INJECTION_PLAN_REGULAR, Color.parseColor("#33CCCC")));

        return ret;
    }

    private void showAddInsulinsInjection(View view) {

        Intent intent = new Intent(this, InsulinAddActivity.class);
        intent.putExtra(InsulinConstants.KEY_INTENT_EXTRA_INSULINS, mInsulinsInjections);
        this.startActivity(intent);

    }

}
