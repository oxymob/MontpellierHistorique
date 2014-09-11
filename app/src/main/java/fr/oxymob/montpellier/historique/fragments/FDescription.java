package fr.oxymob.montpellier.historique.fragments;

import fr.oxymob.montpellier.historique.R;
import fr.oxymob.montpellier.historique.pojos.Monument;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FDescription extends Fragment {
    private static final String KEY_CONTENT = "FDescription:Content";
    private Monument mCurrentMonument;
    public String fid;
    TextView vAddr, vName;
    private TextView vContent;

    public static FDescription newInstance(Monument monument) {
        FDescription fragment = new FDescription();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_CONTENT, monument);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            fid = savedInstanceState.getString(KEY_CONTENT);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, fid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);vAddr = (TextView) view.findViewById(R.id.address);
        vName = (TextView) view.findViewById(R.id.name);
        vContent = (TextView) view.findViewById(R.id.content);
        mCurrentMonument = (Monument) getArguments().get(KEY_CONTENT);
        if (mCurrentMonument != null) {
            vAddr.setText(mCurrentMonument.getAdresses());
            vName.setText(mCurrentMonument.getMonument());
            vContent.setText(Html.fromHtml(mCurrentMonument.getPage()));
        }
    }
}
