package fr.oxymob.montpellier.historique.fragments;

import fr.oxymob.montpellier.historique.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public final class FAbout extends Fragment {

    private Animation mTranslate;
    private ViewGroup vCredits;

    public static Fragment newInstance() {
        Fragment fragment = new FAbout();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mTranslate = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_credits);
        mTranslate.setRepeatCount(Animation.INFINITE);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_share).setVisible(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vCredits = (ViewGroup) view.findViewById(R.id.layout_credits);

        vCredits.startAnimation(mTranslate);
    }
}
