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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.update_card_item, parent, false);

        TextView userName = (TextView) view.findViewById(R.id.user_name);
        userName.setText(updates.get(position).getUserName());

        TextView userHandle = (TextView) view.findViewById(R.id.user_handle);
        userHandle.setText(updates.get(position).getUserHandle());

        TextView textContent = (TextView) view.findViewById(R.id.text_content);
        textContent.setText(updates.get(position).getTextContent());

        return view;
    }
}
