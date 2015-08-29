package cat.nurses.ua.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cat.nurses.ua.R;

/**
 * Created by antonina on 28.08.15.
 */
public class CatsListAdapter extends BaseAdapter {
    private ArrayList <String> arr;
    private Context context;
    private LayoutInflater li;

    public CatsListAdapter(Context cnt, ArrayList arr) {
        this.arr = arr;
        this.context = cnt;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return arr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if ( v == null ) {
            li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.item_catlist, null);
        }
        Picasso.with(context).load(arr.get(position)).into((ImageView)v.findViewById(R.id.iv_catImage));
        return v;
    }

}
