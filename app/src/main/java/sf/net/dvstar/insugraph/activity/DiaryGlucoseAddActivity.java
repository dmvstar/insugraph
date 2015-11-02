package sf.net.dvstar.insugraph.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Date;

import sf.net.dvstar.insugraph.R;
import sf.net.dvstar.insugraph.database.GlucoseReading;
import sf.net.dvstar.insugraph.insulins.InsulinConstants;
import sf.net.dvstar.insugraph.insulins.InsulinUtils;
import sf.net.dvstar.insugraph.insulins.SetDateTime;

public class DiaryGlucoseAddActivity extends AppCompatActivity {

    private EditText mEtGlucoseValue;
    private EditText mEtGlucoseDate;
    private EditText mEtGlucoseTime;
    private Spinner mSpGlucoseWhen;
    private EditText mEtGlucoseComment;
    private int mMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_glucose_add);
        mEtGlucoseValue = (EditText) findViewById(R.id.et_glucose_value);
        mEtGlucoseDate = (EditText) findViewById(R.id.et_glucose_date);
        mEtGlucoseTime = (EditText) findViewById(R.id.et_glucose_time);
        mSpGlucoseWhen = (Spinner) findViewById(R.id.sp_glucose_when);
        mEtGlucoseComment = (EditText) findViewById(R.id.et_comment);


        SetDateTime.SetTime fromTime = new SetDateTime.SetTime(mEtGlucoseTime, this);
        SetDateTime.SetDate fromDate = new SetDateTime.SetDate(mEtGlucoseDate, this);

    }

    public void cancel(View view){
        finish();
    }

    public void add_update(View view){

        if(mMode == InsulinConstants.MODE_ACTIONS_EDIT_ADD) {

        }

        String value = mEtGlucoseValue.getText().toString();
        float fvalue = Float.parseFloat(value);
        String date = mEtGlucoseDate.getText().toString();
        String time = mEtGlucoseTime.getText().toString();
        String comm = mEtGlucoseComment.getText().toString();
        String note = mSpGlucoseWhen.getSelectedItem().toString();
        Date created = InsulinUtils.parseDateTimeText(time, date);

        if(created != null && fvalue > 0.0) {
            GlucoseReading lGlucoseReading = new GlucoseReading(
                    fvalue, created, note, comm
            );
            lGlucoseReading.save();
        }

        finish();
    }
}
