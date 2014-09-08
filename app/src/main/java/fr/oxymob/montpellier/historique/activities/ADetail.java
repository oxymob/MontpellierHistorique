package fr.oxymob.montpellier.historique.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import fr.oxymob.montpellier.historique.MontpellierHistorique;
import fr.oxymob.montpellier.historique.R;
import fr.oxymob.montpellier.historique.fragments.FDescription;
import fr.oxymob.montpellier.historique.fragments.FMap;
import fr.oxymob.montpellier.historique.fragments.FWebView;
import fr.oxymob.montpellier.historique.pojos.Monument;
import fr.oxymob.montpellier.historique.utils.DatasHelper;

/**
 * Created by dany on 08/09/14.
 */
public class ADetail extends AbsActionBarActivity {
    public static final java.lang.String KEY_EXTRAS = "EXTRA_ID";
    private String mFid;
    private Monument mCurrentMonument;
    private ViewPager mViewPager;
    private String CONTENT[];
    private TabsAdapter mTabsAdapter;
    private DatasHelper mDatasHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            mFid = extras.getString(KEY_EXTRAS);

        setContentView(R.layout.activity_details);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        mDatasHelper = new DatasHelper(this);
        mCurrentMonument = mDatasHelper.getMonument(mFid);

        mTabsAdapter = new TabsAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mTabsAdapter);
        setTitle(mCurrentMonument.getMonument());
    }

    class TabsAdapter extends FragmentPagerAdapter {

        private Fragment wikiFrag;
        private Fragment descFrag;
        private Fragment mapFrag;
        private Fragment merimeeFrag;

        public TabsAdapter(FragmentManager fm) {
            super(fm);
            initFragments();
        }

        private void initFragments() {
            descFrag = FDescription.newInstance(mCurrentMonument);
          //  descFrag = FWebView.newInstance(MontpellierHistorique.LIEN_MERIMEE + mCurrentMonument.getNoticemh());
            mapFrag = FMap.newInstance(mDatasHelper.getAllPosition(), mCurrentMonument.getFid());

            //mapFrag = FWebView.newInstance(MontpellierHistorique.LIEN_MERIMEE + mCurrentMonument.getNoticemh());

            merimeeFrag = FWebView.newInstance(MontpellierHistorique.LIEN_MERIMEE + mCurrentMonument.getNoticemh());
            if (!TextUtils.isEmpty(mCurrentMonument.getUrlwikipedia())) {
                wikiFrag = FWebView.newInstance(mCurrentMonument.getUrlwikipedia());
                CONTENT = new String[] { "Description", "Position", "Mérimée", "Wikipedia"};
            } else
                CONTENT = new String[] { "Description", "Position", "Mérimée"};
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0 :
                    return descFrag;
                case 1 :
                    return mapFrag;
                case 2 :
                    return merimeeFrag;
                case 3 :
                    return wikiFrag;
            }
            return null;
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position];
        }
    }
}
