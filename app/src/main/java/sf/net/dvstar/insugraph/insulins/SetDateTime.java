package sf.net.dvstar.insugraph.insulins;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SetDateTime {


    public static class SetDate implements View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener{
        private final Context mContext;
        private EditText editText;
        private Calendar myCalendar;

        public SetDate(EditText editText, Context ctx){
            this.mContext = ctx;
            this.editText = editText;
            this.editText.setOnFocusChangeListener(this);
            this.myCalendar = Calendar.getInstance();
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            this.editText.setText( dayOfMonth + "." + monthOfYear +"." +year);
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus){
                int year = myCalendar.get(Calendar.YEAR);
                int month = myCalendar.get(Calendar.MONTH);
                int day = myCalendar.get(Calendar.DAY_OF_MONTH);

                String time = editText.getText().toString();
                if(time.length()>0) {
                    SimpleDateFormat sdf = new SimpleDateFormat( "dd.MM.yyyy" );
                    Date dt = null;
                    try {
                        dt = sdf.parse(time);
                        myCalendar.setTime(dt);
                        year = myCalendar.get(Calendar.YEAR);
                        month = myCalendar.get(Calendar.MONTH);
                        day = myCalendar.get(Calendar.DAY_OF_MONTH);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }


                new DatePickerDialog(mContext, this, year, month, day).show();
            }
        }
    }

    public static class SetTime implements View.OnFocusChangeListener, TimePickerDialog.OnTimeSetListener {
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
                int hour    = myCalendar.get(Calendar.HOUR_OF_DAY);
                int minute  = myCalendar.get(Calendar.MINUTE);
                String time = editText.getText().toString();
                if(time.length()>0) {
                    SimpleDateFormat sdf = new SimpleDateFormat( "HH:mm" );
                    Date dt = null;
                    try {
                        dt = sdf.parse(time);
                        myCalendar.setTime(dt);
                        hour    = myCalendar.get(Calendar.HOUR_OF_DAY);
                        minute  = myCalendar.get(Calendar.MINUTE);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                new TimePickerDialog(mContext, this, hour, minute, true).show();

            }
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            this.editText.setText( hourOfDay + ":" + minute);
        }
    }






}