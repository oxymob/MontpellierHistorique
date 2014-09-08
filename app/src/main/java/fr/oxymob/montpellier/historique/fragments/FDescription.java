package fr.oxymob.montpellier.historique.fragments;

import fr.oxymob.montpellier.historique.R;
import fr.oxymob.montpellier.historique.pojos.Monument;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FDescription extends Fragment {
	private static final String KEY_CONTENT = "FDescription:Content";
	private Monument mCurrentMonument;
	public String fid;
	TextView vAddr, vName;
	private WebView webView;
	private HorizontalScrollView gallery;
	private LinearLayout containerPics;

    public static FDescription newInstance(Monument monument) {
        FDescription fragment = new FDescription();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_CONTENT, monument);
        fragment.setArguments(bundle);
        return fragment;
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
			fid = savedInstanceState.getString(KEY_CONTENT);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, fid);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_detail, null);

		gallery = (HorizontalScrollView) view.findViewById(R.id.galery);
		vAddr = (TextView) view.findViewById(R.id.address);
		vName = (TextView) view.findViewById(R.id.name);
		webView = (WebView) view.findViewById(R.id.webview);
		containerPics = (LinearLayout) view.findViewById(R.id.container);
		mCurrentMonument = (Monument) getArguments().get(KEY_CONTENT);
        //webView.getSettings().setTextZoom(200);

  		initDatasScreen();
		initPhotos();

		//webView.getSettings().setJavaScriptEnabled(true);

		return view;
	}

	View.OnClickListener onButtonPics = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			//GANTTracker.GANTTrackPageview(R.string.GANTVenueWebsite);
			/*Intent intent = new Intent(MontpellierHistorique.getContext(), ZoomGalleryActivity.class);
			intent.putExtra("EXTRA_ID", fid);
			startActivity(intent);*/
		}
	};

	void initDatasScreen() {
		vAddr.setText(mCurrentMonument.getAdresses());
		vName.setText(mCurrentMonument.getMonument());
		webView.loadUrl("file:///android_asset/" + mCurrentMonument.getPageHtml());
	}

	void initPhotos() {
		boolean hasPicture = false;
		
		LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		/*for (PhotoBean photo : mCurrentMonument.getList_photos()) {
			hasPicture = true;
			ImageView image = (ImageView) inflater.inflate(R.layout.galery_item, null);
			image.setAdjustViewBounds(true);
			image.setOnClickListener(onButtonPics);
			//image.setLayoutParams(layoutParams);
			//LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			//layoutParams.gravity = LinearLayout.
			containerPics.addView(image);
			image.setImageURI(photo.getUri());
		}*/
		
		if (hasPicture) {
			gallery.setOnClickListener(onButtonPics);
			gallery.setClickable(true);
			//gallery.setVisibility(View.VISIBLE);
		} else {
			//gallery.setVisibility(View.GONE);
			gallery.setClickable(false);
			gallery.setOnClickListener(null);
		}
	}
}
