package cs499.knowsie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.drawer_list_item, parent, false);

        TextView groupName = (TextView) view.findViewById(R.id.drawer_item);
        groupName.setText(groups.get(position).getGroupName());

        return view;
    }
}
