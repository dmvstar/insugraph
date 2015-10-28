package sf.net.dvstar.insugraph.activity;

import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sf.net.dvstar.insugraph.R;
import sf.net.dvstar.insugraph.database.InsulinInjection;
import sf.net.dvstar.insugraph.adapters.InsulinInjectAdapter;
import sf.net.dvstar.insugraph.database.InsulinItem;
import sf.net.dvstar.insugraph.insulins.InsulinConstants;

public class InsulinActionActivity extends AppCompatActivity {


    private static final String TAG = "InsulinActionActivity";
    private ArrayList<InsulinInjection> mInsulinsInjections;

    private TextView currentDate;
    private ListView mLvInjects;


    private FloatingActionMenu menu;
    private FloatingActionButton fab12;
    private FloatingActionButton fab22;
    private FloatingActionButton fab32;
    private FloatingActionMenu mFloatingActionMenu;


    @Override
    protected void onResume() {
        super.onResume();
        setListViewContent();
    }

    /**
     * Set content for injections
     */
    private void setListViewContent() {
        mLvInjects = (ListView) findViewById(R.id.insulin_inject_list);
        mLvInjects.setItemsCanFocus(false);
        mInsulinsInjections = getInsulinsInjections();
        //ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,mInsulinsInjections);
        InsulinInjectAdapter adapter = new InsulinInjectAdapter(this, mInsulinsInjections);
        mLvInjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                Toast.makeText(getBaseContext(), "itemSelect: position = " + position + ", id = "
//                        + id + ", " + parent.getAdapter().getItem(position), Toast.LENGTH_SHORT).show();
                showAddInsulinsInjection(InsulinConstants.MODE_INSULIN_EDIT_ITEM, view, (InsulinInjection) parent.getAdapter().getItem(position));
            }
        });
        mLvInjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
//                Toast.makeText(getBaseContext(),"itemSelect: position = " + position + ", id = "
//                        + id+" "+parent.getSelectedItem(), Toast.LENGTH_SHORT).show();

            }

            public void onNothingSelected(AdapterView<?> parent) {
//                Toast.makeText(getBaseContext(), "itemSelect: nothing", Toast.LENGTH_SHORT).show();
            }
        });
        mLvInjects.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insulin_action);

        currentDate = (TextView) findViewById(R.id.tv_injection_date_text);

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        currentDate.setText(sdf.format(today));

        /*
        FloatingActionButton fab_menu_action = (FloatingActionButton) findViewById(R.id.fab_menu_action);
        fab_menu_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddInsulinsInjection(InsulinConstants.MODE_INSULIN_EDIT_ADD, view, null);
            }
        });
        */

        mFloatingActionMenu = (FloatingActionMenu)findViewById(R.id.menu_actions_add);

        fab12 = (FloatingActionButton) findViewById(R.id.fab12);
        fab22 = (FloatingActionButton) findViewById(R.id.fab22);
        fab32 = (FloatingActionButton) findViewById(R.id.fab32);

        fab12.setOnClickListener(clickListener);
        fab22.setOnClickListener(clickListener);
        fab32.setOnClickListener(clickListener);


        setListViewContent();

    }

    private ArrayList<InsulinInjection> getInsulinsInjections() {
        List<InsulinInjection> ret;// = new ArrayList<>();

        List<InsulinItem> insulins = new Select().from(InsulinItem.class).execute();

        Log.v(TAG, "!!!!!!!!"+ insulins.toString() );

        ret = new Select().from(InsulinInjection.class).execute();

        Log.v(TAG, "!!!!!!!!"+ ret );
        /*
        ret.add(new InsulinInjection(new InsulinItem("actrapid", Color.YELLOW), "8", "8:00", "Morning", InsulinInjection.INJECTION_PLAN_REGULAR, Color.YELLOW));
        ret.add(new InsulinInjection(new InsulinItem("protafan", Color.GREEN), "16", "8:00", "Morning", InsulinInjection.INJECTION_PLAN_REGULAR, Color.GREEN));
        ret.add(new InsulinInjection(new InsulinItem("novorapid", Color.parseColor("#FF9000")), "8", "14:00", "Dinner", InsulinInjection.INJECTION_PLAN_ADDITIONAL, Color.parseColor("#FF9000")));
        ret.add(new InsulinInjection(new InsulinItem("levemir", Color.parseColor("#33CCCC")), "10", "18:00", "Mid", InsulinInjection.INJECTION_PLAN_REGULAR, Color.parseColor("#33CCCC")));
        */

        return (ArrayList<InsulinInjection>) ret;
    }

    /**
     * Show add or edit activity
     * @param mode
     * @param view
     * @param item
     */
    private void showAddInsulinsInjection(int mode, View view, InsulinInjection item) {

        Intent intent = new Intent(this, InsulinInjectAddActivity.class);
        intent.putExtra(InsulinConstants.KEY_INTENT_EXTRA_INJECT_EDIT_MODE, mode);

        if(item != null) {
            long rowId = item.getId();
            intent.putExtra(InsulinConstants.KEY_INTENT_EXTRA_ROW_ID, rowId);
            intent.putExtra(InsulinConstants.KEY_INTENT_EXTRA_INJECT_EDIT_ITEM, item);
        }

        this.startActivity(intent);

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = "";

            switch (v.getId()) {
                case R.id.fab12:
                    text = fab12.getLabelText();
                    mFloatingActionMenu.close(false);
                    showAddInsulinsInjection(InsulinConstants.MODE_INSULIN_EDIT_ADD, v, null);
                    break;
                case R.id.fab22:
                    text = fab22.getLabelText();
                    break;
                case R.id.fab32:
                    text = fab32.getLabelText();
                    break;
            }

            Toast.makeText(InsulinActionActivity.this, text, Toast.LENGTH_SHORT).show();
        }
    };


}
