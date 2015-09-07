package cat.nurses.ua.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cat.nurses.ua.MainActivity;
import cat.nurses.ua.R;
import cat.nurses.ua.fragments.DetailItemCatsListFragment;
import cat.nurses.ua.models.ImageSource;

/**
 * Created by antonina on 28.08.15.
 */
public class CatsListAdapter extends BaseAdapter {
    private ArrayList <ImageSource> arr;
    private Context context;
    private LayoutInflater inflater;

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
        View view;
        ViewHolder viewHolder;
        if ( convertView == null ) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_catlist, null);
            viewHolder = new ViewHolder(position);
            viewHolder.findView(view);
            viewHolder.setListener();
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }

        Picasso.with(context).load(arr.get(position).getUrl()).into(viewHolder.ivImage);
        viewHolder.tvSourceUrl.setText(arr.get(position).getSourceUrl());

        return view;
    }

    private class ViewHolder implements View.OnClickListener {
        public ImageView ivImage;
        public TextView tvSourceUrl;
        public RatingBar rbRatingImage;
        private int position;

        public ViewHolder(int position) {
            this.position = position;
        }

        public void findView(View parent) {
            ivImage         = (ImageView) parent.findViewById(R.id.iv_catImage_ICL);
            tvSourceUrl     = (TextView) parent.findViewById(R.id.tv_sourceUrl_ICL);
            rbRatingImage   = (RatingBar) parent.findViewById(R.id.rb_catVote_ICL);
        }

        public void setListener() {
            ivImage.setOnClickListener(this);
            tvSourceUrl.setOnClickListener(this);
            rbRatingImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_catImage_ICL:
                    ((MainActivity) context).configContent(
                            DetailItemCatsListFragment.newInstance(arr.get(position)));
                    break;
                case R.id.tv_sourceUrl_ICL:
                    break;
                case R.id.rb_catVote_ICL:
                    break;
            }
        }
    }

}
