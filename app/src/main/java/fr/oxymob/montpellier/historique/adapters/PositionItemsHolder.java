package fr.oxymob.montpellier.historique.adapters;

import android.content.res.Resources;
import com.android.volley.toolbox.NetworkImageView;
import com.backelite.bkdroid.adapters.AbstractViewHolder;
import fr.oxymob.montpellier.historique.MontpellierHistorique;
import fr.oxymob.montpellier.historique.R;
import fr.oxymob.montpellier.historique.pojos.Position;

public class PositionItemsHolder extends AbstractViewHolder<Position> {

	@Override
	public void setContent(Resources res, int position, Position content) {
		get(R.id.item_title).setText(content.getMonument());
        get(R.id.item_desc).setText(content.getDesc());
        get(R.id.item_distance).setText(content.getStrDistance());

		if (content.getImageURL() != null)
			((NetworkImageView) get(R.id.item_image).getView()).setImageUrl(content.getImageURL(), MontpellierHistorique.getInstance().getVolleyImageLoader());
	}
}