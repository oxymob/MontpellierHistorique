package fr.oxymob.montpellier.historique.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fr.oxymob.montpellier.historique.R;
import fr.oxymob.montpellier.historique.pojos.Photo;

public class FPicture extends Fragment {
    private static final String KEY_CONTENT = "FPicture:Content";
    private Photo mPicture;
    private View vOverlay;

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
    public void setUserVisibleHint(boolean isVisibleToUser){
        if (isVisibleToUser)
            openPanel(vOverlay);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_CONTENT, mPicture);
    }

    public void openPanel(View view) {
        if (view.getVisibility() == View.VISIBLE)
            return;
        Animation animParams = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        animParams.setDuration(500);
        view.setVisibility(View.VISIBLE);
        view.startAnimation(animParams);
    }

    public void closePanel(View view) {
        Animation animParams = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f);
        animParams.setDuration(500);
        view.setVisibility(View.GONE);
        view.startAnimation(animParams);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_picture, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView vImage = (ImageView) view.findViewById(R.id.image);
        Picasso.with(getActivity()).load(mPicture.getUri()).placeholder(R.drawable.icon_mh).error(R.drawable.icon_mh).into(vImage);
        TextView vDescImage = (TextView) view.findViewById(R.id.desc_image);
        vDescImage.setText(Photo.getCreditPhoto(getActivity(), mPicture));
        view.findViewById(R.id.bt_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePanel(vOverlay);
            }
        });
        vOverlay = view.findViewById(R.id.overlay_container);
    }
}