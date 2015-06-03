package cs499.knowsie.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cs499.knowsie.R;
import cs499.knowsie.data.Update;

public class UpdateListAdapter extends ArrayAdapter<Update> {

    private Context context;
    private List<Update> updates;

    public UpdateListAdapter(Context context, List<Update> updates) {
        super(context, R.layout.update_card_item, updates);
        this.context = context;
        this.updates = updates;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Update update = updates.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                                        .inflate(R.layout.update_card_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.updateSource = (ImageView) convertView.findViewById(R.id.update_src);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.user_name);
            viewHolder.userHandle = (TextView) convertView.findViewById(R.id.user_handle);
            viewHolder.dateTime = (TextView) convertView.findViewById(R.id.date_text);
            viewHolder.textContent = (TextView) convertView.findViewById(R.id.text_content);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

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

        viewHolder.userName.setText(update.getPrimaryName());
        viewHolder.userHandle.setText("@" + update.getSecondaryName());
        viewHolder.dateTime.setText(update.getDateString());
        viewHolder.textContent.setText(update.getText());

        int padding = viewHolder.textContent.getPaddingStart();
        boolean hasImage;

        try {
            Picasso.with(context).load(update.getMediaURL()).into(viewHolder.imageView);
            hasImage = true;
        } catch (Exception e) {
            hasImage = false;
        }

        if (hasImage) {
            viewHolder.imageView.setVisibility(View.VISIBLE);
            viewHolder.textContent.setPadding(padding, padding, padding, 0);
        } else {
            viewHolder.imageView.setVisibility(View.GONE);
            viewHolder.textContent.setPadding(padding, 0, padding, 0);
        }

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        Collections.sort(updates, new Comparator<Update>() {
            @Override
            public int compare(Update lhs, Update rhs) {
                return rhs.getDate().compareTo(lhs.getDate());
            }
        });
        super.notifyDataSetChanged();
    }

    private static class ViewHolder {
        ImageView updateSource;
        TextView userName;
        TextView dateTime;
        TextView userHandle;
        TextView textContent;
        ImageView imageView;
    }
}
