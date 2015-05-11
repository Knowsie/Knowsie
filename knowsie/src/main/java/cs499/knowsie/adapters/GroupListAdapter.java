package cs499.knowsie.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cs499.knowsie.R;
import cs499.knowsie.data.Group;

public class GroupListAdapter extends ArrayAdapter<Group> {

    private Context context;
    private ArrayList<Group> groups;

    public GroupListAdapter(Context context, ArrayList<Group> groups) {
        super(context, 0, groups);
        this.context = context;
        this.groups = groups;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.drawer_list_item, parent, false);

        viewHolder.groupName = (TextView) convertView.findViewById(R.id.drawer_groups_list_item);
        viewHolder.groupName.setText(groups.get(position).getGroupName());

        convertView.setTag(viewHolder);

        return convertView;
    }

    private static class ViewHolder {
        TextView groupName;
    }
}
