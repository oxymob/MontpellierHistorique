package fr.oxymob.montpellier.historique.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
/**
 * A fragment that displays a WebView.
 * <p>
 * The WebView is automically paused or resumed when the Fragment is paused or resumed.
 */
public class FWebView extends Fragment {
    private static final String KEY_CONTENT = "FWebView:Content";
    private WebView mWebView;
    private String mPage;

    public static FWebView newInstance(String url) {
        FWebView fragment = new FWebView();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_CONTENT, url);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mPage = (String) savedInstanceState.get(KEY_CONTENT);
        } else
            mPage = getArguments().getString(KEY_CONTENT);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_CONTENT, mPage);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mWebView = new WebView(getActivity());
       // mWebView.getSettings( ).setSupportZoom( true );
        mWebView.setInitialScale(1);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        return mWebView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWebView.loadUrl(mPage);
    }
}