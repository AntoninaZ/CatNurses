package cat.nurses.ua.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import cat.nurses.ua.R;
import cat.nurses.ua.adapters.CatsListAdapter;
import cat.nurses.ua.api.HandleXml;
import cat.nurses.ua.models.ImageSource;

import static cat.nurses.ua.help.Constants.*;


/**
 * Created by antonina on 28.08.15.
 */
public class AllCatsListFragment extends Fragment {
    private ListView lvCatsList;
    private ArrayList<ImageSource> catsArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catslist, container, false);
        getData();
        configListView(view);

        return view;
    }

    private void getData() {
        HandleXml obj = new HandleXml(CONST_API_URL + CONST_API_PARAMS_GET_LIST);
        obj.fetchXML();
        while (obj.parsingComplete) ;
        catsArrayList = obj.getImages();
    }

    private void configListView(View view) {
        lvCatsList = (ListView) view.findViewById(R.id.lv_catsList_FCL);
        lvCatsList.setAdapter(new CatsListAdapter(getActivity(), catsArrayList));
    }
}
