package cs499.knowsie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UpdateListAdapter extends ArrayAdapter<Update> {

    private Context context;
    private ArrayList<Update> updates;

    public UpdateListAdapter(Context context, ArrayList<Update> updates) {
        super(context, R.layout.update_card_item, updates);
        this.context = context;
        this.updates = updates;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.update_card_item, parent, false);

        viewHolder.userName = (TextView) convertView.findViewById(R.id.user_name);
        viewHolder.userName.setText(updates.get(position).getUserName());

        viewHolder.userHandle = (TextView) convertView.findViewById(R.id.user_handle);
        viewHolder.userHandle.setText(updates.get(position).getUserHandle());

        viewHolder.textContent = (TextView) convertView.findViewById(R.id.text_content);
        viewHolder.textContent.setText(updates.get(position).getTextContent());

        convertView.setTag(viewHolder);

        return convertView;
    }

    private static class ViewHolder {
        TextView userName;
        TextView userHandle;
        TextView textContent;
    }
}
