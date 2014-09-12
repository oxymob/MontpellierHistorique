package fr.oxymob.montpellier.historique.abstracts;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.Window;

public abstract class AbsActionBarActivity  extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setBackgroundDrawable( getResources().getDrawable(R.drawable.ab_background));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if ((getSupportParentActivityIntent() != null) &&
                (item.getItemId() == android.R.id.home))
            finish();
        return true;
    }

    public void setProgressVisibility(boolean b) {
        setProgressBarIndeterminateVisibility(b);
    }
}