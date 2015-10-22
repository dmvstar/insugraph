package sf.net.dvstar.insugraph.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import sf.net.dvstar.insugraph.database.InsulinFirm;
import sf.net.dvstar.insugraph.database.InsulinInjection;
import sf.net.dvstar.insugraph.database.InsulinItem;
import sf.net.dvstar.insugraph.database.InsulinOrigin;
import sf.net.dvstar.insugraph.database.InsulinType;
import sf.net.dvstar.insugraph.insulin.InsulinConstants;
import sf.net.dvstar.insugraph.insulin.SetTime;

public class InsulinDescAddActivity extends AppCompatActivity {

    Button btColor;
    LinearLayout llColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insulin_desc_add);

        btColor = (Button) findViewById(R.id.bt_color);
        llColor = (LinearLayout) findViewById(R.id.ll_color);


        // адаптер
        List<InsulinFirm> insulinFirmList = new Select().from(InsulinFirm.class).execute();
        ArrayAdapter<InsulinFirm> adapterInsulinFirmAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,insulinFirmList);
        //adapterInsulinFirmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapter.setDropDownViewResource(R.layout.insulin_desc_item);

        Spinner spinner;
        spinner = (Spinner) findViewById(R.id.sp_insulin_desc_firm);
        spinner.setAdapter(adapterInsulinFirmAdapter);
        // заголовок
        spinner.setPrompt("Title");
        // выделяем элемент
        //spinner.setSelection(2);
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

        spinner = (Spinner) findViewById(R.id.sp_insulin_desc_type);
        List<InsulinType> insulinTypeList = new Select().from(InsulinType.class).execute();
        ArrayAdapter<InsulinFirm> adapterInsulinTypeListAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,insulinTypeList);
        spinner.setAdapter(adapterInsulinTypeListAdapter);

        spinner = (Spinner) findViewById(R.id.sp_insulin_desc_origin);
        List<InsulinType> insulinOriginList = new Select().from(InsulinOrigin.class).execute();
        ArrayAdapter<InsulinFirm> adapterInsulinOriginListAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,insulinOriginList);
        spinner.setAdapter(adapterInsulinOriginListAdapter);

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
