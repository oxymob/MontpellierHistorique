package fr.oxymob.montpellier.historique.adapters;

import android.content.res.Resources;
import android.widget.ImageView;

import com.backelite.bkdroid.adapters.AbstractViewHolder;
import com.squareup.picasso.Picasso;

import fr.oxymob.montpellier.historique.R;
import fr.oxymob.montpellier.historique.pojos.Position;

public class PositionItemsHolder extends AbstractViewHolder<Position> {

    @Override
    public void setContent(Resources res, int position, Position content) {
        get(R.id.item_title).setText(content.getMonument());
        get(R.id.item_desc).setText(content.getDesc());
        get(R.id.item_distance).setText(content.getStrDistance());

        Picasso.with(getContext()).load(content.getImageURL()).placeholder(R.drawable.icon_mh).error(R.drawable.icon_mh).into((ImageView) get(R.id.item_image).getView());
    }
}