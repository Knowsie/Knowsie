package cs499.knowsie;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        updateList = new ArrayList<Update>();

        updateListAdapter = new UpdateListAdapter(view.getContext(), updateList);
        setListAdapter(updateListAdapter);

        return view;
    }
}
