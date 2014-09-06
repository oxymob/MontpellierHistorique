package fr.oxymob.montpellier.historique.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.View;
import com.backelite.bkdroid.adapters.EfficientListAdapter;
import java.util.ArrayList;
import java.util.List;
import fr.oxymob.montpellier.historique.R;
import fr.oxymob.montpellier.historique.adapters.PositionItemsHolder;
import fr.oxymob.montpellier.historique.pojos.Position;

/**
 * Created by dany on 05/09/14.
 */
public class MHListFragment extends ListFragment {
    private static final String KEY_LIST = "list";
    private List<Position> mList;

    public static Fragment newInstance(ArrayList<Position> list) {
        Fragment fragment = new MHListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_LIST, list);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mList = (List<Position>) getArguments().getSerializable(KEY_LIST);
        setListAdapter(new EfficientListAdapter<Position>(getActivity(),
                R.layout.item_list,
                PositionItemsHolder.class,
                mList));
    }
}
