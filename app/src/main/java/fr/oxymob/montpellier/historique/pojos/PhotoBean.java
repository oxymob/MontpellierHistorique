package fr.oxymob.montpellier.historique.pojos;

import android.net.Uri;
import fr.oxymob.montpellier.historique.utils.Functions;

public class PhotoBean {

	private String path;
	private MonumentBean monument;
	private String url;
	private String auteur;
	private String source;
	private String url_source;
	private String licence;
	private String taille;

	public String getPath() {
		return path;
	}

	public String getCreditPhoto() {
		StringBuilder sb = new StringBuilder();
		if (auteur != null && !auteur.equals("")) 
			sb.append(auteur).append("\n");
		if (source != null && !source.equals("")) 
			sb.append("Source").append(" : ").append(source).append("\n");
		if (licence != null && !licence.equals("")) 
			sb.append("Licence").append(" : ").append(licence).append("\n");
		if (url_source != null && !url_source.equals("")) 
			sb.append("Url").append(" : ").append(url_source).append("\n");

		return sb.toString();
	}
	public Uri getUri() {
		Uri uri = null;

		//if ((path != null) && (path.length()) > 0) {
		//	String str = AssetsProvider.CONTENT_URI + path;
			uri = Uri.parse(path);

		return uri;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAuteur() {
		return auteur;
	}
	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getUrl_source() {
		return url_source;
	}
	public void setUrl_source(String url_source) {
		this.url_source = url_source;
	}
	public String getLicence() {
		return licence;
	}
	public void setLicence(String licence) {
		this.licence = licence;
	}
	public String getTaille() {
		return taille;
	}
	public void setTaille(String taille) {
		this.taille = taille;
	}
	public static PhotoBean fromTextLine(String line) {
		String tokens[] = Functions.splitTotokens(line, "|");

		PhotoBean newPhoto = new PhotoBean();
		String id_mon = tokens[0];
		//newPhoto.monument = MonumentController.getInstance().queryForId(id_mon);
		//newPhoto.fid = tokens[0];
		newPhoto.path = tokens[1] + ".jpg";
		newPhoto.url = tokens[2];
		newPhoto.auteur = tokens[3];
		newPhoto.source = tokens[4];
		newPhoto.url_source = tokens[5];
		newPhoto.licence = tokens[6];
		if (tokens.length > 7)
			newPhoto.taille = tokens[7];

		/*try {
			if (!Functions.isAssetFileExist(newPhoto.path))
				return null;
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		return newPhoto;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("-PHOTOS-------\n");
		sb.append("fid").append(" : ").append(monument.getFid()).append("\n");
		sb.append("path").append(" : ").append(path).append("\n");
		sb.append("url").append(" : ").append(url).append("\n");
		sb.append("auteur").append(" : ").append(auteur).append("\n");
		sb.append("source").append(" : ").append(source).append("\n");
		sb.append("url_source").append(" : ").append(url_source).append("\n");
		sb.append("licence").append(" : ").append(licence).append("\n");
		sb.append("taille").append(" : ").append(taille).append("\n");

		return sb.toString();
	}
	public void setMonument(MonumentBean monument) {
		this.monument = monument;
	}
	public MonumentBean getMonument() {
		return monument;
	}

}
