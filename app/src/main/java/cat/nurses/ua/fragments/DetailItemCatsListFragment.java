package cat.nurses.ua.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import cat.nurses.ua.R;
import cat.nurses.ua.api.HandleXml;
import cat.nurses.ua.models.ImageSource;

import static cat.nurses.ua.help.Constants.CONST_API_PARAMS_SET_FAVORITE;
import static cat.nurses.ua.help.Constants.CONST_API_URL;

/**
 * Created by antonina on 04.09.15.
 */
public class DetailItemCatsListFragment extends Fragment implements View.OnClickListener {
    private ImageSource mImageSource;

    private ImageView mIVCatImage;
    private ImageView mIVCreateVote;
    private ImageView mIVAddToFavorites;
    private ImageView mIVShare;

    public static DetailItemCatsListFragment newInstance(ImageSource itemSource) {
        DetailItemCatsListFragment fragment = new DetailItemCatsListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", itemSource);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_item, container, false);
        findView(view);
        setListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null) {
            mImageSource = (ImageSource) getArguments().getSerializable("data");
            Picasso.with(getActivity()).load(mImageSource.getUrl()).into(mIVCatImage);
        }
    }

    private void findView(View view) {
        mIVCatImage       = (ImageView) view.findViewById(R.id.iv_catImage_DICL);
        mIVCreateVote     = (ImageView) view.findViewById(R.id.iv_CreateVote_DICL);
        mIVAddToFavorites = (ImageView) view.findViewById(R.id.iv_addToFavorites_DICL);
        mIVShare          = (ImageView) view.findViewById(R.id.iv_Share_DICL);

    }

    private void setListener() {
        mIVCatImage.setOnClickListener(this);
        mIVCreateVote.setOnClickListener(this);
        mIVAddToFavorites.setOnClickListener(this);
        mIVShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_addToFavorites_DICL:
                HandleXml obj = new HandleXml(CONST_API_URL + CONST_API_PARAMS_SET_FAVORITE + mImageSource.getIdImage());
                obj.fetchXML();
        }
    }
}
