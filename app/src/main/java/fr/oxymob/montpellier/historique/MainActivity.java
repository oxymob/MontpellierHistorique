package fr.oxymob.montpellier.historique;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.maps.MapsInitializer;
import fr.oxymob.montpellier.historique.abstracts.AbsNavigationActivity;
import fr.oxymob.montpellier.historique.fragments.FAbout;
import fr.oxymob.montpellier.historique.fragments.FList;
import fr.oxymob.montpellier.historique.fragments.FMap;
import fr.oxymob.montpellier.historique.utils.DatasHelper;

public class MainActivity extends AbsNavigationActivity {

    private static final String KEY_CONTENT = "mContent";
    private DatasHelper datasHelper;
    private Fragment mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapsInitializer.initialize(getApplicationContext());
        if (savedInstanceState != null) {
            //Restore the fragment's instance
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, KEY_CONTENT);
        }
        setContentView(R.layout.activity_main);
        datasHelper = new DatasHelper(this);
    }

    @Override
    public void selectNavItem(int position) {
        switch (position) {
            case 0:
                mContent = FMap.newInstance(datasHelper.getAllPosition());
                break;
            case 1:
                mContent = FList.newInstance(datasHelper.getAllPosition());
                break;
            case 2:
                mContent = FAbout.newInstance();
                break;
        }
        if (mContent != null)
            openNavFragment(mContent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, KEY_CONTENT, mContent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_title));
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text));
                startActivity(intent);
                break;
            case R.id.menu_ar:
                intent = new Intent(this, ASimpleARBrowser.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
