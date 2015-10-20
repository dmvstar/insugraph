package sf.net.dvstar.insugraph.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sf.net.dvstar.insugraph.R;
import sf.net.dvstar.insugraph.activity.InsulinDescActivity;
import sf.net.dvstar.insugraph.database.InsulinInjection;
import sf.net.dvstar.insugraph.database.InsulinItem;


public class InsulinDescAdapter  extends ArrayAdapter<InsulinItem> {

    private final Activity mContext;
    private final List<InsulinItem> mInsulins;

    static class ViewHolder {
        public TextView tv_insulin;
        public TextView tv_firm;
        public TextView tv_type;
        public ListView lv_item;
    }


    public InsulinDescAdapter(Activity context, List<InsulinItem> insulins) {
        super(context, R.layout.insulin_desc_item, insulins);
        this.mContext   = context;
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
            rowView = inflater.inflate(R.layout.insulin_desc_item, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tv_insulin = (TextView) rowView.findViewById(R.id.tv_insulin);
            viewHolder.tv_firm = (TextView) rowView.findViewById(R.id.tv_firm);
            viewHolder.tv_type = (TextView) rowView.findViewById(R.id.tv_type);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();

        InsulinItem item = mInsulins.get(position);

        holder.tv_insulin.setText(item.name);
        holder.tv_firm.setText(item.firm.name);
        holder.tv_type.setText(item.type.description);
        rowView.setBackgroundColor(item.color);

        return rowView;
    }


}