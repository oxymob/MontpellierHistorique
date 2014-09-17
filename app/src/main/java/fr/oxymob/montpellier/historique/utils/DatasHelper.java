package fr.oxymob.montpellier.historique.utils;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import fr.oxymob.montpellier.historique.pojos.Monument;
import fr.oxymob.montpellier.historique.pojos.Photo;
import fr.oxymob.montpellier.historique.pojos.Position;

/**
 * Created by dany on 05/09/14.
 */
public class DatasHelper {
    private Context mContext;
    private List<Monument> mMonumentList;
    public static final String FILE_MONUMENTS = "monuments.json";
    public static final String FILE_PHOTOS = "photos.json";
    private  List<Photo> mPhotoList;

    public DatasHelper(Context mContext) {
        this.mContext = mContext;
    }

    public List<Monument> getAllMonuments() {
        if (mMonumentList == null) {
            String jsonText = Functions.openFile(mContext, FILE_MONUMENTS);
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("M/d/yy hh:mm a");
            Gson gson = gsonBuilder.create();
            return gson.fromJson(jsonText, new TypeToken<List<Monument>>() {
            }.getType());
        } else
            return mMonumentList;
    }

    public List<Photo> getAllPhotos() {
        if (mPhotoList == null) {
            String jsonText = Functions.openFile(mContext, FILE_PHOTOS);
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("M/d/yy hh:mm a");
            Gson gson = gsonBuilder.create();
            return gson.fromJson(jsonText, new TypeToken<List<Photo>>() {}.getType());
        } else
            return mPhotoList;
    }

    public List<Photo> getAllPhotosByFid(String fid) {
        ArrayList<Photo> listItem = new ArrayList<Photo>();

        for (Photo photo:getAllPhotos()) {
           if (photo == null) continue;
            if (photo.getFid().equals(fid))
                listItem.add(photo);
        }
        return listItem;
    }

    public ArrayList<Position> getAllPosition() {
        ArrayList<Position> listItem = new ArrayList<Position>();
        double lat = MyLocationManager.getInstance().getLat();
        double lg = MyLocationManager.getInstance().getLg();

        for (Monument monument : getAllMonuments()) {
            if (monument != null) {
                Position pos = new Position();
                try {
                    pos.copyFromMonument(monument, lat, lg);
                    listItem.add(pos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Collections.sort(listItem);
        return listItem;
    }

    public Monument getMonument(String mFid) {
        for (Monument monument : getAllMonuments()) {
            if (monument.getFid().equals(mFid)) {
                return monument;
            }
        }
        return null;
    }
}
