package fr.oxymob.montpellier.historique;

import android.app.Application;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import fr.oxymob.montpellier.historique.utils.BitmapLruCache;

/**
 * Created by dany on 05/09/14.
 */
public class MontpellierHistorique extends Application {
    public static final String FILE_MONUMENTS = "monuments.json";
    public static MontpellierHistorique instance;
    private ImageLoader mVolleyImageLoader;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mRequestQueue = Volley.newRequestQueue(this);
        mVolleyImageLoader = new ImageLoader(mRequestQueue,  new BitmapLruCache());
        mRequestQueue.start();
    }

    public void stop() {
        mRequestQueue.stop();
    }

    public static MontpellierHistorique getInstance() {
        return instance;
    }

    public ImageLoader getVolleyImageLoader() {
        return mVolleyImageLoader;
    }
}
