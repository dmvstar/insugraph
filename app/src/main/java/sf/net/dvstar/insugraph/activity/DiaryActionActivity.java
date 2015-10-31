package sf.net.dvstar.insugraph.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
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
import java.util.Iterator;
import java.util.List;

import sf.net.dvstar.insugraph.R;
import sf.net.dvstar.insugraph.database.InsulinInjection;
import sf.net.dvstar.insugraph.adapters.InsulinInjectAdapter;
import sf.net.dvstar.insugraph.database.InsulinItem;
import sf.net.dvstar.insugraph.insulins.InsulinConstants;
import sf.net.dvstar.insugraph.insulins.InsulinUtils;


public class DiaryActionActivity extends AppCompatActivity {


    private static final String TAG = "DiaryActionActivity";
    private ArrayList<InsulinInjection> mInsulinsInjections;

    private TextView mDiaryActionsDate;
    private ListView mLvDiaryActions;
    private Context mContext;
    private TextView mTotalInsulunDose;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_action);
        mContext = this;

        mDiaryActionsDate = (TextView) findViewById(R.id.tv_diary_actions_date);
        mTotalInsulunDose = (TextView) findViewById(R.id.tv_injection_total_value);

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        mDiaryActionsDate.setText(sdf.format(today));
        mDiaryActionsDate.setOnClickListener( new ActionsOnDateSetListener() );

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

        fab12 = (FloatingActionButton) findViewById(R.id.fab_inject);
        fab22 = (FloatingActionButton) findViewById(R.id.fab_eating);
        fab32 = (FloatingActionButton) findViewById(R.id.fab_glucose);

        fab12.setOnClickListener(clickListener);
        fab22.setOnClickListener(clickListener);
        fab32.setOnClickListener(clickListener);


        setListViewContent();

    }

    Calendar myCalendar = Calendar.getInstance();
    class ActionsOnDateSetListener implements DatePickerDialog.OnDateSetListener, View.OnClickListener{

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mDiaryActionsDate.setText(InsulinUtils.getDateText(year, monthOfYear, dayOfMonth));
        }

        @Override
        public void onClick(View v) {
            new DatePickerDialog(mContext, this, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }


    /**
     * Set content for injections
     */
    private void setListViewContent() {
        mLvDiaryActions = (ListView) findViewById(R.id.lv_diary_action_list);
        mLvDiaryActions.setItemsCanFocus(false);
        mInsulinsInjections = getInsulinsInjections();
        //ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,mInsulinsInjections);
        InsulinInjectAdapter adapter = new InsulinInjectAdapter(this, mInsulinsInjections);
        mLvDiaryActions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                Toast.makeText(getBaseContext(), "itemSelect: position = " + position + ", id = "
//                        + id + ", " + parent.getAdapter().getItem(position), Toast.LENGTH_SHORT).show();
                showAddInsulinsInjection(InsulinConstants.MODE_INSULIN_EDIT_ITEM, view, (InsulinInjection) parent.getAdapter().getItem(position));
            }
        });
        mLvDiaryActions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
//                Toast.makeText(getBaseContext(),"itemSelect: position = " + position + ", id = "
//                        + id+" "+parent.getSelectedItem(), Toast.LENGTH_SHORT).show();

            }

            public void onNothingSelected(AdapterView<?> parent) {
//                Toast.makeText(getBaseContext(), "itemSelect: nothing", Toast.LENGTH_SHORT).show();
            }
        });
        mLvDiaryActions.setAdapter(adapter);

        calculateTotalInsulinDose();
    }

    private void calculateTotalInsulinDose() {
        int totalDose = 0;
        for (Iterator<InsulinInjection> iter = mInsulinsInjections.iterator(); iter.hasNext(); ) {
            InsulinInjection item = iter.next();
            totalDose += Integer.parseInt(item.dose);
        }
        mTotalInsulunDose.setText(""+totalDose);
    }

    private ArrayList<InsulinInjection> getInsulinsInjections() {
        List<InsulinInjection> ret;// = new ArrayList<>();

        List<InsulinItem> insulins = new Select()
                .from(InsulinItem.class)
                .execute();

        Log.v(TAG, "!!!!!!!!" + insulins.toString());

        ret = new Select()
                .from(InsulinInjection.class)
                .orderBy("time")
                .execute();

        Log.v(TAG, "!!!!!!!!" + ret);

        return (ArrayList<InsulinInjection>) ret;
    }

    /**
     * Show add or edit activity
     * @param mode work mode add or edit
     * @param view parent View
     * @param item data value
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

    private void showAddGlucoseReading(int mode, View view) {

        Intent intent = new Intent(this, DiaryGlucoseAddActivity.class);
        intent.putExtra(InsulinConstants.KEY_INTENT_EXTRA_INJECT_EDIT_MODE, mode);
        this.startActivity(intent);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = "";

            switch (v.getId()) {
                case R.id.fab_inject:
                    text = fab12.getLabelText();
                    mFloatingActionMenu.close(false);
                    showAddInsulinsInjection(InsulinConstants.MODE_INSULIN_EDIT_ADD, v, null);
                    break;
                case R.id.fab_eating:
                    text = fab22.getLabelText();
                    mFloatingActionMenu.close(false);
                    break;
                case R.id.fab_glucose:
                    text = fab32.getLabelText();
                    mFloatingActionMenu.close(false);
                    showAddGlucoseReading(InsulinConstants.MODE_INSULIN_EDIT_ADD, v);
                    break;
            }

            Toast.makeText(DiaryActionActivity.this, text, Toast.LENGTH_SHORT).show();
        }
    };


}
