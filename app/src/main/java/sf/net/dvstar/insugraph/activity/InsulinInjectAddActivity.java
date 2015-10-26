package sf.net.dvstar.insugraph.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.buzzingandroid.ui.HSVColorPickerDialog;

import java.util.ArrayList;
import java.util.List;

import sf.net.dvstar.insugraph.R;
import sf.net.dvstar.insugraph.adapters.InsulinDescAdapter;
import sf.net.dvstar.insugraph.database.InsulinInjection;
import sf.net.dvstar.insugraph.database.InsulinItem;
import sf.net.dvstar.insugraph.insulins.InsulinConstants;
import sf.net.dvstar.insugraph.insulins.SetDateTime;


public class InsulinInjectAddActivity extends AppCompatActivity {

    Button btColor;
    LinearLayout llColor;
    private int mMode;
    private Spinner mSpInsulins;
    private EditText mEtFromTime;
    private EditText mEtFromDate;
    private EditText mEtDose;
    private EditText mEtComment;
    private Spinner mSpInjectType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insulin_inject_add);

        mMode = getIntent().getExtras().getInt(InsulinConstants.KEY_INTENT_EXTRA_INSULIN_EDIT_MODE);

        btColor = (Button) findViewById(R.id.bt_color);
        llColor = (LinearLayout) findViewById(R.id.ll_color);
        mEtFromTime = (EditText) findViewById(R.id.et_inject_time);
        mEtFromDate = (EditText) findViewById(R.id.et_inject_date);
        mEtDose = (EditText) findViewById(R.id.et_inject_dose);
        mEtComment = (EditText) findViewById(R.id.et_inject_comment);
        mSpInjectType = (Spinner) findViewById(R.id.sp_inject_type);

        InsulinInjection injection = (InsulinInjection) getIntent().getSerializableExtra(InsulinConstants.KEY_INTENT_EXTRA_INJECTIONS);

        SetDateTime.SetTime fromTime = new SetDateTime.SetTime(mEtFromTime, this);
        SetDateTime.SetDate fromDate = new SetDateTime.SetDate(mEtFromDate, this);

        // адаптер
        List<InsulinItem> insulinList = new Select().from(InsulinItem.class).execute();

        InsulinDescAdapter adapter = new InsulinDescAdapter(this, insulinList);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.insulin_desc_item);

        mSpInsulins = (Spinner) findViewById(R.id.sp_inject_insulin);
        mSpInsulins.setAdapter(adapter);
        // заголовок
        mSpInsulins.setPrompt("Title");
        // выделяем элемент
        mSpInsulins.setSelection(2);
        // устанавливаем обработчик нажатия
        mSpInsulins.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                InsulinItem insulinItem = (InsulinItem) mSpInsulins.getSelectedItem();
                llColor.setBackgroundColor( insulinItem.color );
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }

    private  int mStoredColor = 0xFF4488CC;

    public void selectColor(View view){
        HSVColorPickerDialog cpd = new HSVColorPickerDialog( this, mStoredColor, new HSVColorPickerDialog.OnColorSelectedListener() {
            @Override
            public void colorSelected(Integer color) {
                mStoredColor = color;
                llColor.setBackgroundColor( color );
            }
        });
        cpd.setTitle( "Pick a color" );
        cpd.show();
    }

    public void cancel(View view){
        finish();
    }

    public void add_update(View view){

        ColorDrawable viewColor = (ColorDrawable) llColor.getBackground();
        InsulinItem insulinItem = (InsulinItem) mSpInsulins.getSelectedItem();
        InsulinInjection injectItem = new InsulinInjection();
        injectItem.insulin = insulinItem;
        injectItem.plan = mSpInjectType.getSelectedItemPosition();
        injectItem.color = viewColor.getColor();

        injectItem.dose = mEtDose.getText().toString();
        injectItem.time = mEtFromTime.getText().toString();
        injectItem.date = mEtFromDate.getText().toString();
        injectItem.comment = mEtComment.getText().toString();

        if( injectItem.dose.length()>0 && injectItem.time.length()>0 ) {
            injectItem.save();
        } else {
            Toast.makeText(getBaseContext(), "Not Enough data (dose, time ...) for store !", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}
