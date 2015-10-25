package sf.net.dvstar.insugraph.insulins;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class SetTime implements View.OnFocusChangeListener, TimePickerDialog.OnTimeSetListener {

    private final Context mContext;
    private EditText editText;
    private Calendar myCalendar;

    public SetTime(EditText editText, Context ctx){
        this.mContext = ctx;
        this.editText = editText;
        this.editText.setOnFocusChangeListener(this);
        this.myCalendar = Calendar.getInstance();

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = myCalendar.get(Calendar.MINUTE);
            new TimePickerDialog(mContext, this, hour, minute, true).show();
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.editText.setText( hourOfDay + ":" + minute);
    }


}
