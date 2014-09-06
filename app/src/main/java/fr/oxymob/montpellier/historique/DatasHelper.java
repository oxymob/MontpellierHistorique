package fr.oxymob.montpellier.historique;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import fr.oxymob.montpellier.historique.pojos.Monument;
import fr.oxymob.montpellier.historique.pojos.Position;
import fr.oxymob.montpellier.historique.utils.Functions;

/**
 * Created by dany on 05/09/14.
 */
public class DatasHelper {
    private Context mContext;
    private List<Monument> mMonumentList;

    public DatasHelper(Context mContext) {
        this.mContext = mContext;
    }

    public List<Monument> getAllMonuments() {
        if (mMonumentList == null) {
            String jsonText = Functions.openFile(mContext, MontpellierHistorique.FILE_MONUMENTS);
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("M/d/yy hh:mm a");
            Gson gson = gsonBuilder.create();
            return gson.fromJson(jsonText, new TypeToken<List<Monument>>() {
            }.getType());
        } else
            return mMonumentList;
    }

    public ArrayList<Position> getAllPosition() {
        ArrayList<Position> listItem = new ArrayList<Position>();
        double lat = MyLocationManager.getInstance().getLat();
        double lg = MyLocationManager.getInstance().getLg();

        for (Monument monument : getAllMonuments()) {
            if (monument != null) {
                Position pos = new Position();
                pos.copyFromMonument(monument);
                pos.init_distance(lat, lg);
                listItem.add(pos);
            }
        }
        return listItem;
    }
}
