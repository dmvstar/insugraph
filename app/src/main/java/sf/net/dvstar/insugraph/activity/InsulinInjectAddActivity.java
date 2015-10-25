package sf.net.dvstar.insugraph.activity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insulin_inject_add);

        btColor = (Button) findViewById(R.id.bt_color);
        llColor = (LinearLayout) findViewById(R.id.ll_color);

        ArrayList<InsulinInjection> insulins = (ArrayList<InsulinInjection>) getIntent().getSerializableExtra(InsulinConstants.KEY_INTENT_EXTRA_INSULINS);

        EditText editTextFromTime = (EditText) findViewById(R.id.et_inject_time);
        EditText editTextFromDate = (EditText) findViewById(R.id.et_inject_date);

        SetDateTime.SetTime fromTime = new SetDateTime.SetTime(editTextFromTime, this);
        SetDateTime.SetDate fromDate = new SetDateTime.SetDate(editTextFromDate, this);

        // адаптер
        List<InsulinItem> insulinList = new Select().from(InsulinItem.class).execute();

        InsulinDescAdapter adapter = new InsulinDescAdapter(this, insulinList);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.insulin_desc_item);

        Spinner spinner = (Spinner) findViewById(R.id.sp_inject_insulin);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Title");
        // выделяем элемент
        spinner.setSelection(2);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
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
}
