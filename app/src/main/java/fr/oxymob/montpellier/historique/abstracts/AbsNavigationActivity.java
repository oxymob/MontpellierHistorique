package fr.oxymob.montpellier.historique.abstracts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import fr.oxymob.montpellier.historique.R;

public abstract class AbsNavigationActivity extends AbsActionBarActivity  {
	protected static final String LOG_TAG = "AbsNavigationActivity";
	private ActionBarDrawerToggle mDrawerToggle;
	public DrawerLayout vDrawerLayout;
	private ListView vDrawerList;
	private CharSequence mTitle;
    private String[] mTitles;
    public abstract void selectNavItem(int position);

    @Override
	public void setContentView(int layoutId) {
		vDrawerLayout = new android.support.v4.widget.DrawerLayout(this);
		vDrawerList =  (ListView) getLayoutInflater().inflate(R.layout.flyin_menu_list, null);
		DrawerLayout.LayoutParams lpLeft = new DrawerLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT , FrameLayout.LayoutParams.MATCH_PARENT);
		lpLeft.gravity = Gravity.START;
		vDrawerList.setLayoutParams(lpLeft);

		View content = getLayoutInflater().inflate(layoutId, null);

		vDrawerLayout.addView(content, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
		vDrawerLayout.addView(vDrawerList);
		super.setContentView(vDrawerLayout);	
		initNavigationDrawer();
	}

	public void initNavigationDrawer() {

		mDrawerToggle = new ActionBarDrawerToggle(
				this,                  /* host Activity */
				vDrawerLayout,         /* DrawerLayout object */
				R.drawable.bt_menu_w,  /* nav drawer image to replace 'Up' caret */
				R.string.drawer_open,  /* "open drawer" description for accessibility */
				R.string.drawer_close  /* "close drawer" description for accessibility */
				) {
			public void onDrawerClosed(View view) {
				//getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				//getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
		};
		vDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerToggle.setDrawerIndicatorEnabled(true);

		vDrawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View v, int pos, long id) {
                selectNavItem(pos);
                vDrawerLayout.closeDrawer(vDrawerList);
            }
		} );

		mDrawerToggle.syncState();

        mTitles = getResources().getStringArray(R.array.flyin_menu_titles);

        vDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.item_fyin, mTitles));
	}

    protected void openNavFragment(android.support.v4.app.Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

	@Override
	public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            super.onBackPressed();
        else
            quitAlertDialog();
	}

	private void quitAlertDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				finish();
		        System.exit(0);
			}
		})
		.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// Nothing to do
			}
		})
		.setMessage(getString(R.string.do_you_want_to_quit))
		.setTitle(getString(android.R.string.dialog_alert_title));
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//	MenuInflater inflater = getMenuInflater();
		//	inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content view
		boolean drawerOpen = vDrawerLayout.isDrawerOpen(vDrawerList);
		//menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		switch(item.getItemId()) {
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
		if (savedInstanceState == null) {
            selectNavItem(1);
        }
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
}
