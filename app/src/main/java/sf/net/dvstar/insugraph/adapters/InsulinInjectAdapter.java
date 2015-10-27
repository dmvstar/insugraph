package sf.net.dvstar.insugraph.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import sf.net.dvstar.insugraph.R;
import sf.net.dvstar.insugraph.database.InsulinInjection;


/**
 * Created by sdv on 15.10.15.
 */
public class InsulinInjectAdapter extends ArrayAdapter<InsulinInjection> {

    private final Activity mContext;
    private final List<InsulinInjection> mInsulins;

    static class ViewHolder {
        public TextView tv_insulin;
        public TextView tv_dose;
        public TextView tv_time;
        public TextView tv_comment;
        public TextView tv_planned;
    }

    public InsulinInjectAdapter(Activity context, List<InsulinInjection> insulins) {
        super(context, R.layout.insulin_inject_item, insulins);
        this.mContext = context;
        this.mInsulins = insulins;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = mContext.getLayoutInflater();
            rowView = inflater.inflate(R.layout.insulin_inject_item, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tv_insulin = (TextView) rowView.findViewById(R.id.tv_insulin);
            viewHolder.tv_dose = (TextView) rowView.findViewById(R.id.tv_dose);
            viewHolder.tv_time = (TextView) rowView.findViewById(R.id.tv_time);
            viewHolder.tv_comment = (TextView) rowView.findViewById(R.id.tv_comment);
            viewHolder.tv_planned = (TextView) rowView.findViewById(R.id.tv_planned);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();

        InsulinInjection item = mInsulins.get(position);

        holder.tv_insulin.setText(item.insulin.name);
        holder.tv_dose.setText(item.dose);
        holder.tv_time.setText(item.time);
        holder.tv_comment.setText(item.comment);
        holder.tv_planned.setText(getPlannedDescription(item.plan));
        rowView.setBackgroundColor(item.color);

        return rowView;
    }

    // @TODO replace to resource
    private String getPlannedDescription(int planned) {
        String ret = "none";
        if (planned == 0) ret = mContext.getResources().getString(R.string.planned_regular);
        if (planned == 1) ret = mContext.getResources().getString(R.string.planned_additionals);
        return ret;
    }

}
