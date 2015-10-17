package sf.net.dvstar.insugraph.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sf.net.dvstar.insugraph.R;
import sf.net.dvstar.insugraph.activity.InsulinActivity;


/**
 * Created by sdv on 15.10.15.
 */
public class InsulinAdapter extends ArrayAdapter<InsulinItem> {

    private final InsulinActivity mContext;
    private final ArrayList<InsulinItem> mInsulins;

    static class ViewHolder {
        public TextView tv_insulin;
        public TextView tv_dose;
        public TextView tv_time;
        public TextView tv_comment;
    }

    public InsulinAdapter(InsulinActivity insulinActivity, ArrayList<InsulinItem> insulins) {
        super(insulinActivity, R.layout.insulin_item, insulins);
        this.mContext = insulinActivity;
        this.mInsulins = insulins;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = mContext.getLayoutInflater();
            rowView = inflater.inflate(R.layout.insulin_item, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tv_insulin = (TextView) rowView.findViewById(R.id.tv_insulin);
            viewHolder.tv_dose = (TextView) rowView.findViewById(R.id.tv_dose);
            viewHolder.tv_time = (TextView) rowView.findViewById(R.id.tv_time);
            viewHolder.tv_comment = (TextView) rowView.findViewById(R.id.tv_comment);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();

        InsulinItem item = mInsulins.get(position);

        holder.tv_insulin.setText(item.insulin);
        holder.tv_dose.setText(item.dose);
        holder.tv_time.setText(item.time);
        holder.tv_comment.setText(item.comment);
        rowView.setBackgroundColor(item.color);

        return rowView;
    }

}
