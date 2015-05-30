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
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                                        .inflate(R.layout.update_card_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.updateSource = (ImageView) convertView.findViewById(R.id.update_src);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.user_name);
            viewHolder.userHandle = (TextView) convertView.findViewById(R.id.user_handle);
            viewHolder.textContent = (TextView) convertView.findViewById(R.id.text_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Update update = updates.get(position);

        switch (update.getSource()) {
            case INSTAGRAM:
                viewHolder.updateSource.setImageResource(R.drawable.ic_instagram);
                break;
            case TWITTER:
                viewHolder.updateSource.setImageResource(R.drawable.ic_twitter_blue);
                break;
            default:
                break;
        }

        viewHolder.userName.setText(update.getUserName());
        viewHolder.userHandle.setText(update.getScreenName());
        viewHolder.textContent.setText(update.getText());

        return convertView;
    }

    private static class ViewHolder {
        ImageView updateSource;
        TextView userName;
        TextView userHandle;
        TextView textContent;
    }
}
