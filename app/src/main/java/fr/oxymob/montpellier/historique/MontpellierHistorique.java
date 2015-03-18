package fr.oxymob.montpellier.historique;

import android.app.Application;

/**
 * Created by dany on 05/09/14.
 */
public class MontpellierHistorique extends Application {
    public static String LIEN_MERIMEE = "http://www.culture.gouv.fr/public/mistral/merimee_fr?ACTION=CHERCHER&FIELD_1=REF&VALUE_1=";
    public static MontpellierHistorique instance;

    @Override
    public void onCreate() {
        super.onCreate();
        LIEN_MERIMEE = getString(R.string.merimee);
        instance = this;
    }


    public static MontpellierHistorique getInstance() {
        return instance;
    }

}
