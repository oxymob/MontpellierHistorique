package fr.oxymob.montpellier.historique;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import java.util.List;
import fr.oxymob.montpellier.historique.activities.AbsNavigationActivity;
import fr.oxymob.montpellier.historique.fragments.MHListFragment;
import fr.oxymob.montpellier.historique.pojos.Monument;

public class MainActivity extends AbsNavigationActivity {

    private List<Monument> listMonument;
    private DatasHelper datasHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datasHelper = new DatasHelper(this);
    }

    public void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }

    @Override
    public void selectNavItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0 :
                fragment = MHListFragment.newInstance(datasHelper.getAllPosition());
                break;
            case 1:
                break;
        }
        if (fragment != null)
            openNavFragment(fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
