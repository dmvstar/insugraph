package sf.net.dvstar.insugraph.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import sf.net.dvstar.insugraph.R;
import sf.net.dvstar.insugraph.adapters.InsulinItem;
import sf.net.dvstar.insugraph.adapters.InsulinAdapter;
import sf.net.dvstar.insugraph.insulin.InsulinConstants;

/**
 * Created by sdv on 15.10.15.
 */
public class InsulinActivity extends AppCompatActivity {


    private ArrayList<InsulinItem> mInsulins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insulin);

        ListView listView = (ListView)findViewById(R.id.insulin_list);

        mInsulins = getInsulins();

        listView.setAdapter(new InsulinAdapter(this, mInsulins));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddInsulin(view);
            }
        });

    }

    private ArrayList<InsulinItem> getInsulins() {
        ArrayList<InsulinItem> ret = new ArrayList<>();

        ret.add( new InsulinItem("actrapid", "8", "8:00", "Morning", Color.YELLOW) );
        ret.add( new InsulinItem("protafan", "16", "8:00", "Morning", Color.GREEN) );
        ret.add( new InsulinItem("novorapid", "8", "14:00", "Dinner", Color.parseColor("#FF9000")) );
        ret.add( new InsulinItem("levemir",  "10", "18:00", "Mid", Color.parseColor("#33CCCC")) );

        return ret;
    }

    private void showAddInsulin(View view) {

        Intent intent = new Intent(this, InsulinAddActivity.class);
        intent.putExtra(InsulinConstants.KEY_INTENT_EXTRA_INSULINS, mInsulins);
        this.startActivity(intent);

    }

}
