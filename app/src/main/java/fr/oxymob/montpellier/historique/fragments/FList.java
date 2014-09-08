package fr.oxymob.montpellier.historique.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import com.backelite.bkdroid.adapters.EfficientListAdapter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import fr.oxymob.montpellier.historique.R;
import fr.oxymob.montpellier.historique.activities.ADetail;
import fr.oxymob.montpellier.historique.adapters.PositionItemsHolder;
import fr.oxymob.montpellier.historique.pojos.Position;

/**
 * Created by dany on 05/09/14.
 */
public class FList extends ListFragment {
    private static final String KEY_LIST = "list";
    private List<Position> mList;

    public static Fragment newInstance(ArrayList<Position> list) {
        Fragment fragment = new FList();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_LIST, list);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mList == null)
            mList = (List<Position>) getArguments().getSerializable(KEY_LIST);
        setListAdapter(new EfficientListAdapter<Position>(getActivity(),
                R.layout.item_list,
                PositionItemsHolder.class,
                mList));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), ADetail.class);
        Position item = (Position) getListAdapter().getItem(position);
        intent.putExtra(ADetail.KEY_EXTRAS, item.getFid());
        startActivity(intent);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_ar).setVisible(true);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mList = (List<Position>) getArguments().getSerializable(KEY_LIST);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_LIST, (Serializable) mList);
    }
}
