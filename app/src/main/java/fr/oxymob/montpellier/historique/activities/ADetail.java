package fr.oxymob.montpellier.historique.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import java.util.ArrayList;
import java.util.List;
import fr.oxymob.montpellier.historique.R;
import fr.oxymob.montpellier.historique.fragments.FDescription;
import fr.oxymob.montpellier.historique.fragments.FMap;
import fr.oxymob.montpellier.historique.fragments.FPicture;
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
            mListPages.add(new Page(getString(R.string.description), FDescription.newInstance(mCurrentMonument)));
            mListPages.add(new Page(getString(R.string.position), FMap.newInstance(mDatasHelper.getAllPosition(), mCurrentMonument.getFid())));
            int i = 1;
            for (Photo photo:mListPictures) {
                mListPages.add(new Page(getString(R.string.image) + " " + i++, FPicture.newInstance(photo)));
            }
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
