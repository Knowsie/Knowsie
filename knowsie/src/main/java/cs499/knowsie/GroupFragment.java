package cs499.knowsie;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import cs499.knowsie.adapters.UpdateListAdapter;
import cs499.knowsie.data.Update;

public class GroupFragment extends ListFragment {

    private UpdateListAdapter updateListAdapter;
    private ArrayList<Update> updateList;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        ListView listView = (ListView) view.findViewById(R.id.group);

        String msg = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

        updateList = new ArrayList<Update>();
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                updateList.add(new Update("MonteCristo",
                                          "@ggCMonteCristo",
                                          msg,
                                          Update.TWITTER));
            } else {
                updateList.add(new Update("Google",
                                          "@google",
                                          msg,
                                          Update.INSTAGRAM));
            }
        }

        updateListAdapter = new UpdateListAdapter(view.getContext(), updateList);
        listView.setAdapter(updateListAdapter);

        return view;
    }
}
