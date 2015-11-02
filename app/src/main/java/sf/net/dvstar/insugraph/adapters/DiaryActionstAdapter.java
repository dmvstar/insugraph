package sf.net.dvstar.insugraph.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import sf.net.dvstar.insugraph.R;
import sf.net.dvstar.insugraph.database.ActionCommonItem;
import sf.net.dvstar.insugraph.database.GlucoseReading;
import sf.net.dvstar.insugraph.database.InsulinInjection;
import sf.net.dvstar.insugraph.insulins.InsulinUtils;


public class DiaryActionstAdapter extends ArrayAdapter<ActionCommonItem> {

    private final Activity mContext;
    private final List<ActionCommonItem> mActionss;

    static class ViewHolderInject {
        public TextView tv_insulin;
        public TextView tv_dose;
        public TextView tv_time;
        public TextView tv_comment;
        public TextView tv_planned;
    }

    static class ViewHolderGlucose {
        public TextView tv_value;
        public TextView tv_time;
        public TextView tv_note;
        public TextView tv_comment;
    }


    public DiaryActionstAdapter(Activity context, List<ActionCommonItem> actions) {
        //super(context, android.R.layout.simple_list_item_1, insulins);
        super(context, R.layout.insulin_inject_item, actions);
        this.mContext   = context;
        this.mActionss  = actions;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        //return super.getDropDownView(position, convertView, parent);
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ActionCommonItem action = mActionss.get(position);
        int key = action.getActionType();
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = mContext.getLayoutInflater();
            if(key == ActionCommonItem.ACTION_TYPE_INJECT) {
                rowView = inflater.inflate(R.layout.insulin_inject_item, null);
                // configure view holder
                ViewHolderInject viewHolder = new ViewHolderInject();
                viewHolder.tv_insulin = (TextView) rowView.findViewById(R.id.tv_insulin);
                viewHolder.tv_dose = (TextView) rowView.findViewById(R.id.tv_dose);
                viewHolder.tv_time = (TextView) rowView.findViewById(R.id.tv_time);
                viewHolder.tv_comment = (TextView) rowView.findViewById(R.id.tv_comment);
                viewHolder.tv_planned = (TextView) rowView.findViewById(R.id.tv_planned);
                rowView.setTag(R.id.ACTION_TYPE_INJECT, viewHolder);
            }
            if(key == ActionCommonItem.ACTION_TYPE_GLUCOSE) {
                rowView = inflater.inflate(R.layout.insulin_glucose_item, null);
                ViewHolderGlucose viewHolder = new ViewHolderGlucose();
                viewHolder.tv_value = (TextView) rowView.findViewById(R.id.tv_value);
                viewHolder.tv_time = (TextView) rowView.findViewById(R.id.tv_time);
                viewHolder.tv_comment = (TextView) rowView.findViewById(R.id.tv_comment);
                rowView.setTag(R.id.ACTION_TYPE_GLUCOSE, viewHolder);
            }

        }

        // fill data
        if(key == ActionCommonItem.ACTION_TYPE_INJECT) {
            ViewHolderInject holder = (ViewHolderInject) rowView.getTag(R.id.ACTION_TYPE_INJECT);
            InsulinInjection item = (InsulinInjection) action;
            holder.tv_insulin.setText(item.insulin.name);
            holder.tv_dose.setText(item.dose);
            holder.tv_time.setText(InsulinUtils.getTimeText(item.time));
            holder.tv_comment.setText(item.comment);
            holder.tv_planned.setText(getPlannedDescription(item.plan));
            rowView.setBackgroundColor(item.color);
        }
        if(key == ActionCommonItem.ACTION_TYPE_GLUCOSE) {
            ViewHolderGlucose holder = (ViewHolderGlucose) rowView.getTag(R.id.ACTION_TYPE_GLUCOSE);
            GlucoseReading item = (GlucoseReading) action;
            holder.tv_value.setText(""+item.value);
            holder.tv_time.setText(InsulinUtils.getDateTimeText(item.created));
            holder.tv_comment.setText(item.comment);
        }


        return rowView;
    }

    // @TODO replace to resource
    private String getPlannedDescription(int planned) {
        String ret = mContext.getResources().getString(R.string.planned_none);
        if (planned == InsulinInjection.INJECTION_PLAN_REGULAR) ret = mContext.getResources().getString(R.string.planned_regular);
        if (planned == InsulinInjection.INJECTION_PLAN_ADDITIONAL) ret = mContext.getResources().getString(R.string.planned_additionals);
        return ret;
    }

}
