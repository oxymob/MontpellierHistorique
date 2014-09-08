package fr.oxymob.montpellier.historique.fragments;

import fr.oxymob.montpellier.historique.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public final class FAbout extends Fragment {

    public static FAbout newInstance() {
       FAbout fragment = new FAbout();
       return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_share).setVisible(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	WebView m_webview = (WebView) inflater.inflate(R.layout.infos, null);
		m_webview.loadUrl("file:///android_asset/infos.html");
        return m_webview;
    }
}
