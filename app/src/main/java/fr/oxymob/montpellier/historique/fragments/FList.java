package fr.oxymob.montpellier.historique.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import fr.oxymob.montpellier.historique.R;
import fr.oxymob.montpellier.historique.adapters.PositionItemsHolder;
import fr.oxymob.montpellier.historique.pojos.Position;

/**
 * Created by dany on 05/09/14.
 */
public class FList extends Fragment {
    private static final String KEY_LIST = "list";
    private List<Position> mList;
    private RecyclerView mRecyclerView;
    private int lastPosition;

    public static Fragment newInstance(ArrayList<Position> list) {
        Fragment fragment = new FList();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_LIST, list);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_recyclerview, container, false);

            mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mList = (List<Position>) getArguments().getSerializable(KEY_LIST);
            mRecyclerView.setAdapter(new RecycleAdapter());

            return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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

    class RecycleAdapter
            extends RecyclerView.Adapter<PositionItemsHolder> {
        @Override
        public PositionItemsHolder onCreateViewHolder(ViewGroup parent, int pos) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list, parent, false);
            return new PositionItemsHolder(view);
        }

        @Override
        public void onBindViewHolder(PositionItemsHolder holder, int pos) {
            Position position = mList.get(pos);
            holder.setContent(getActivity(), position);
            setAnimation(holder.container, pos);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }



        private void setAnimation(CardView viewToAnimate, int position)
        {
            if (position > lastPosition)
            {
                Animation animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }
        }
    }
}
