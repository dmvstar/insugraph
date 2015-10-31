package sf.net.dvstar.insugraph.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import sf.net.dvstar.insugraph.R;

public class DiaryGlucoseAddActivity extends AppCompatActivity {

    private EditText mEtGlucoseValue;
    private EditText mEtGlucoseDate;
    private EditText mEtGlucoseTime;
    private Spinner mSpGlucoseWhen;
    private EditText mEtGlucoseComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_glucose_add);
        mEtGlucoseValue = (EditText) findViewById(R.id.et_glucose_value);
        mEtGlucoseDate = (EditText) findViewById(R.id.et_glucose_date);
        mEtGlucoseTime = (EditText) findViewById(R.id.et_glucose_time);
        mSpGlucoseWhen = (Spinner) findViewById(R.id.sp_glucose_when);
        mEtGlucoseComment = (EditText) findViewById(R.id.et_comment);
    }

    public void cancel(View view){
        finish();
    }

    public void add_update(View view){
        finish();
    }
}
