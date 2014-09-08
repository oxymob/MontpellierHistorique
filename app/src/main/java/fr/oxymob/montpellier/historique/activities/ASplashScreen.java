package fr.oxymob.montpellier.historique.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import fr.oxymob.montpellier.historique.utils.DatasHelper;
import fr.oxymob.montpellier.historique.MainActivity;
import fr.oxymob.montpellier.historique.utils.NetworkCall;
import fr.oxymob.montpellier.historique.R;
import fr.oxymob.montpellier.historique.utils.Functions;

/**
 * Created by dany on 05/09/14.
 */
public class ASplashScreen extends Activity implements NetworkCall.NetworkCallListener {
    private static final int SPLASH_SCREEN_DURATION=500;
    private NetworkCall mNetworkCallMonuments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS){
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 1 );
            dialog.show();
       } else {
            if (!Functions.fileExists(getBaseContext(), DatasHelper.FILE_MONUMENTS))
                executeNetworkCall(DatasHelper.FILE_MONUMENTS);
            else
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callMainActivity();
                    }
                }, SPLASH_SCREEN_DURATION);
        }
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
        callMainActivity();
    }

    @Override
    public void onError() {
        Toast.makeText(getBaseContext(), getString(R.string.network_call_ko), Toast.LENGTH_LONG).show();
        finish();
    }
}
