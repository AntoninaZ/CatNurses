package cat.nurses.ua.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import cat.nurses.ua.R;
import cat.nurses.ua.api.HttpClient;


/**
 * Created by antonina on 28.08.15.
 */
public class AllCatsListFragment extends Fragment {
    private ListView lvCatsList;
    private ArrayList <String> catsArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_catslist, container, false);
        lvCatsList = (ListView) v.findViewById(R.id.lv_catsList);
        new HttpClient().getCatsList(getActivity(), lvCatsList);
        return v;
    }
}
