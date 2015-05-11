package cs499.knowsie.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cs499.knowsie.R;
import cs499.knowsie.data.Update;

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

        Update update = updates.get(position);

        viewHolder.updateSource = (ImageView) convertView.findViewById(R.id.update_src);
        switch (update.getSource()) {
            case Update.INSTAGRAM:
                viewHolder.updateSource.setImageResource(R.drawable.ic_instagram);
                break;
            case Update.TWITTER:
                viewHolder.updateSource.setImageResource(R.drawable.ic_twitter);
                break;
            default:
                break;
        }

        viewHolder.userName = (TextView) convertView.findViewById(R.id.user_name);
        viewHolder.userName.setText(update.getUserName());

        viewHolder.userHandle = (TextView) convertView.findViewById(R.id.user_handle);
        viewHolder.userHandle.setText(update.getUserHandle());

        viewHolder.textContent = (TextView) convertView.findViewById(R.id.text_content);
        viewHolder.textContent.setText(update.getTextContent());

        convertView.setTag(viewHolder);

        return convertView;
    }

    private static class ViewHolder {
        ImageView updateSource;
        TextView userName;
        TextView userHandle;
        TextView textContent;
    }
}
