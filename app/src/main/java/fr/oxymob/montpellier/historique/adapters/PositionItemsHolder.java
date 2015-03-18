package fr.oxymob.montpellier.historique.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fr.oxymob.montpellier.historique.R;
import fr.oxymob.montpellier.historique.activities.ADetail;
import fr.oxymob.montpellier.historique.pojos.Position;

public class PositionItemsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final TextView mTitle;
    private final TextView mDescr;
    private final TextView mDist;
    private final ImageView mImage;
    public final CardView container;
    private Activity mActivity;
    private Position mPosition;

    public PositionItemsHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        container = (CardView) itemView;
        mTitle = (TextView) itemView.findViewById(R.id.item_title);
        mDescr = (TextView) itemView.findViewById(R.id.item_desc);
        mDist = (TextView) itemView.findViewById(R.id.item_distance);
        mImage = (ImageView) itemView.findViewById(R.id.item_image);
    }

    public void setContent(Activity context, Position content) {
        mTitle.setText(content.getMonument());
        mDescr.setText(content.getDesc());
        mDist.setText(content.getStrDistance());

        mActivity = context;
        mPosition = content;

        Picasso.with(context).load(content.getImageURL()).placeholder(R.drawable.icon_mh).error(R.drawable.icon_mh).into(mImage);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mActivity, ADetail.class);
        intent.putExtra(ADetail.KEY_EXTRAS, mPosition.getFid());
        mActivity.startActivity(intent);
    }
}