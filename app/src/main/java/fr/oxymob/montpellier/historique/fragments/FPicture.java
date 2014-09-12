package fr.oxymob.montpellier.historique.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import fr.oxymob.montpellier.historique.MontpellierHistorique;
import fr.oxymob.montpellier.historique.R;
import fr.oxymob.montpellier.historique.pojos.Photo;

public class FPicture extends Fragment {
    private static final String KEY_CONTENT = "FPicture:Content";
    private Photo mPicture;

    public static FPicture newInstance(Photo picture) {
        FPicture fragment = new FPicture();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_CONTENT, picture);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPicture = (Photo) savedInstanceState.get(KEY_CONTENT);
        } else
            mPicture = (Photo) getArguments().getSerializable(KEY_CONTENT);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_CONTENT, mPicture);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_picture, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NetworkImageView vImage = (NetworkImageView) view.findViewById(R.id.image);
        TextView vDescImage = (TextView) view.findViewById(R.id.desc_image);
        vImage.setImageUrl(mPicture.getUri(), MontpellierHistorique.getInstance().getVolleyImageLoader());
        vDescImage.setText(Photo.getCreditPhoto(getActivity(), mPicture));
    }
}