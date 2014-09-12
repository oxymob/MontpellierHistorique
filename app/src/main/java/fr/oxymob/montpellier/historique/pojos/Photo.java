package fr.oxymob.montpellier.historique.pojos;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import fr.oxymob.montpellier.historique.R;
import fr.oxymob.montpellier.historique.utils.NetworkCall;

public class Photo implements Serializable {
    @SerializedName("FID") private String fid;
    @SerializedName("IDPIC") private String path;
    @SerializedName("URL") private String url;
   @SerializedName("AUTEUR") private String auteur;
    @SerializedName("SOURCE") private String source;
    @SerializedName("URLSOURCE") private String url_source;
    @SerializedName("LICENCE") private String licence;
    @SerializedName("TAILLE") private String taille;

    public String getFid() {
        return fid;
    }
    public String getAuteur() {
        return auteur;
    }

    public String getSource() {
        return source;
    }

    public String getLicence() {
        return licence;
    }

    public String getUri() {
        return NetworkCall.PATH + path + ".jpg";
    }

    public static String getCreditPhoto(Context context, Photo photo) {
		StringBuilder sb = new StringBuilder();
		if (!TextUtils.isEmpty(photo.getAuteur()))
            sb.append(context.getString(R.string.auteur)).append(" : ").append(photo.getAuteur()).append("\n");
        if (!TextUtils.isEmpty(photo.getSource()))
			sb.append(context.getString(R.string.source)).append(" : ").append(photo.getSource()).append("\n");
        if (!TextUtils.isEmpty(photo.getLicence()))
			sb.append(context.getString(R.string.licence)).append(" : ").append(photo.getLicence());
		//if (url_source != null && !url_source.equals(""))
		//	sb.append("Url").append(" : ").append(url_source).append("\n");

		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("-PHOTOS-------\n");
		sb.append("FID").append(" : ").append(getFid()).append("\n");
		sb.append("IDPIC").append(" : ").append(path).append("\n");
		sb.append("URL").append(" : ").append(url).append("\n");
		sb.append("AUTEUR").append(" : ").append(auteur).append("\n");
		sb.append("SOURCE").append(" : ").append(source).append("\n");
		sb.append("URLSOURCE").append(" : ").append(url_source).append("\n");
		sb.append("LICENCE").append(" : ").append(licence).append("\n");
		sb.append("TAILLE").append(" : ").append(taille).append("\n");

		return sb.toString();
	}
}
