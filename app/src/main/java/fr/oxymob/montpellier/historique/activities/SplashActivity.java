package fr.oxymob.montpellier.historique.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import fr.oxymob.montpellier.historique.MainActivity;
import fr.oxymob.montpellier.historique.MontpellierHistorique;
import fr.oxymob.montpellier.historique.NetworkCall;
import fr.oxymob.montpellier.historique.R;
import fr.oxymob.montpellier.historique.pojos.Monument;
import fr.oxymob.montpellier.historique.utils.Functions;

/**
 * Created by dany on 05/09/14.
 */
public class SplashActivity extends Activity implements NetworkCall.NetworkCallListener {
    private static final int SPLASH_SCREEN_DURATION=500;
    private NetworkCall mNetworkCallMonuments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        if (!Functions.fileExists(getBaseContext(), MontpellierHistorique.FILE_MONUMENTS))
            executeNetworkCall(MontpellierHistorique.FILE_MONUMENTS);
        else
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    callMainActivity();
                }
            }, SPLASH_SCREEN_DURATION);
    }

    private void callMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void executeNetworkCall(String fileName) {
        mNetworkCallMonuments = new NetworkCall(getBaseContext(), fileName);
        mNetworkCallMonuments.setListener(this);
        mNetworkCallMonuments.fetch();
    }

    @Override
    public void onFileDownloaded(String fileName) {
        String jsonText = Functions.openFile(getBaseContext(), fileName);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("dd MM yyyy"); //Format of our JSON dates
        Gson gson = gsonBuilder.create();
        List<Monument> list = gson.fromJson(jsonText, new TypeToken<List<Monument>>() {}.getType());
        callMainActivity();
    }

    @Override
    public void onError() {
        Toast.makeText(getBaseContext(), getString(R.string.network_call_ko), Toast.LENGTH_LONG).show();
        finish();
    }
}
