package fr.oxymob.montpellier.historique.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import fr.oxymob.montpellier.historique.MontpellierHistorique;
import fr.oxymob.montpellier.historique.R;
import fr.oxymob.montpellier.historique.fragments.FDescription;
import fr.oxymob.montpellier.historique.fragments.FMap;
import fr.oxymob.montpellier.historique.fragments.FPicture;
import fr.oxymob.montpellier.historique.fragments.FWebView;
import fr.oxymob.montpellier.historique.pojos.Monument;
import fr.oxymob.montpellier.historique.pojos.Photo;
import fr.oxymob.montpellier.historique.utils.DatasHelper;

/**
 * Created by dany on 08/09/14.
 */
public class ADetail extends AbsActionBarActivity {
    public static final java.lang.String KEY_EXTRAS = "EXTRA_ID";
    private String mFid;
    private Monument mCurrentMonument;
    private ViewPager mViewPager;
    private TabsAdapter mTabsAdapter;
    private DatasHelper mDatasHelper;
    private List<Photo> mListPictures;
    private class Page {
        String title;
        Fragment fragment;

        Page(String title, Fragment fragment) {
            this.title = title;
            this.fragment = fragment;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public String getTitle() {
            return title;
        }
    }

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
        mListPictures = mDatasHelper.getAllPhotosByFid(mFid);

        mTabsAdapter = new TabsAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mTabsAdapter);
        setTitle(mCurrentMonument.getMonument());
    }

    class TabsAdapter extends FragmentPagerAdapter {

      private List<Page> mListPages;

        public TabsAdapter(FragmentManager fm) {
            super(fm);
            initFragments();
        }

        private void initFragments() {
            mListPages =new ArrayList<Page>();
            mListPages.add(new Page("Description", FDescription.newInstance(mCurrentMonument)));
            mListPages.add(new Page("Position", FMap.newInstance(mDatasHelper.getAllPosition(), mCurrentMonument.getFid())));
            int i = 1;
            for (Photo photo:mListPictures) {
                mListPages.add(new Page("Image " + i++, FPicture.newInstance(photo)));
            }
            //merimeeFrag = FWebView.newInstance(MontpellierHistorique.LIEN_MERIMEE + mCurrentMonument.getNoticemh());

          /*  if (!TextUtils.isEmpty(mCurrentMonument.getUrlwikipedia())) {
                //wikiFrag = FWebView.newInstance(mCurrentMonument.getUrlwikipedia());
                mPagesTab = new String[] { "Description", "Position", "Mérimée", "Wikipedia"};
            } else
                mPagesTab = new String[] { "Description", "Position", "Mérimée"};*/
        }

        @Override
        public Fragment getItem(int position) {
            return mListPages.get(position).fragment;
        }

        @Override
        public int getCount() {
            return mListPages.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mListPages.get(position).getTitle();
        }
    }
}
