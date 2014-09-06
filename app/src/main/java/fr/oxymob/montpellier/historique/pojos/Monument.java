package fr.oxymob.montpellier.historique.pojos;

import java.util.Collection;
import android.net.Uri;

import fr.oxymob.montpellier.historique.NetworkCall;
import fr.oxymob.montpellier.historique.utils.Functions;

import com.google.gson.annotations.SerializedName;

public class Monument {
    @SerializedName("id") private String fid; 						// r�f�rence
    @SerializedName("nom") private String monument;					// nom du Monument
    @SerializedName("classement") private String protection;		// type de protection
    @SerializedName("merimee") private String noticemh;				// r�f�rence MH
    @SerializedName("desc") private String descmh;					// quels sont les �l�ments prot�g�s ?
    //private String desc;						// contenu �ditorial - description
    @SerializedName("adr1") private String adresse;					// adresse du monument (ou POI)
    @SerializedName("adr2") private String adresse2;				// adresse du monument (ou POI)
    @SerializedName("adr3") private String adresse3;				// adresse du monument (ou POI)
    @SerializedName("date") private String date;					// date de classement
    @SerializedName("lat") private String lat;						// latitude
    @SerializedName("lg") private String lg;						// longitude
    @SerializedName("epoque") private String epoque;
    @SerializedName("wikipedia") private String urlwikipedia;
    @SerializedName("pic") private String vignette;
	private String datemodif;				// SAISIE SUR PLACE : date de modif de l'enregistrement
	private String notes;					// SAISIE SUR PLACE : notes ajout�es � la fiche
    private String noticeinventaire;			// SAISIE SUR PLACE : note inventaire MH
	private int interet;						// SAISIE SUR PLACE : note int�r�t (sur 10)
	private int multipoi;					// SAISIE SUR PLACE : multiple POI possible ?
	private Collection<Photo> list_photos;

	public static Monument fromTextLine(String line) {
		String tokens[] = Functions.splitTotokens(line, "|");
		Monument mon = new Monument();
		mon.fid = tokens[0];
		mon.monument = tokens[1];
		mon.protection = tokens[2];
		mon.noticemh = tokens[3];
		mon.descmh = tokens[4];
		mon.adresse = tokens[5];
		mon.adresse2 = tokens[6];
		mon.adresse3 = tokens[7];
		mon.epoque = tokens[8];
		mon.date = tokens[9];
		mon.lat = tokens[10];
		mon.lg = tokens[11];
		//mon.desc = mon.fid + ".html";
		mon.urlwikipedia = tokens[12];
		if (mon.urlwikipedia.equals("NC"))
			mon.urlwikipedia = "";
		mon.vignette = tokens[13];
		return mon;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("-MONUMENT-------\n");
		sb.append("fid").append(" : ").append(fid).append("\n");
		sb.append("monument").append(" : ").append(monument).append("\n");
		sb.append("protection").append(" : ").append(protection).append("\n");
		sb.append("noticemh").append(" : ").append(noticemh).append("\n");
		sb.append("descmh").append(" : ").append(descmh).append("\n");
		//sb.append("desc").append(" : ").append(desc).append("\n");
		sb.append("adresse").append(" : ").append(adresse).append("\n");
		sb.append("date").append(" : ").append(date).append("\n");

		return sb.toString();
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getMonument() {
		return monument;
	}

	public void setMonument(String monument) {
		this.monument = monument;
	}

	public String getProtection() {
		return protection;
	}

	public void setProtection(String protection) {
		this.protection = protection;
	}

	public String getNoticemh() {
		return noticemh;
	}

	public void setNoticemh(String noticemh) {
		this.noticemh = noticemh;
	}

	public String getDescmh() {
		return descmh;
	}

	public void setDescmh(String descmh) {
		this.descmh = descmh;
	}

	/*public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}*/

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getAdresse2() {
		return adresse2;
	}

	public void setAdresse2(String adresse2) {
		this.adresse2 = adresse2;
	}

	public String getAdresse3() {
		return adresse3;
	}

	public void setAdresse3(String adresse3) {
		this.adresse3 = adresse3;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLg() {
		return lg;
	}

	public void setLg(String lg) {
		this.lg = lg;
	}

	public String getNoticeinventaire() {
		return noticeinventaire;
	}

	public void setNoticeinventaire(String noticeinventaire) {
		this.noticeinventaire = noticeinventaire;
	}

	public String getEpoque() {
		return epoque;
	}

	public void setEpoque(String epoque) {
		this.epoque = epoque;
	}

	public String getUrlwikipedia() {
		return urlwikipedia;
	}

	public void setUrlwikipedia(String urlwikipedia) {
		this.urlwikipedia = urlwikipedia;
	}

	public String getDatemodif() {
		return datemodif;
	}

	public void setDatemodif(String datemodif) {
		this.datemodif = datemodif;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getInteret() {
		return interet;
	}

	public void setInteret(int interet) {
		this.interet = interet;
	}

	public int getMultipoi() {
		return multipoi;
	}

	public void setMultipoi(int multipoi) {
		this.multipoi = multipoi;
	}

	public String getVignette() {
		return vignette;
	}

	public void setVignette(String vignette) {
		this.vignette = vignette;
	}

	public String getVignetteUri() {
		/*String str = AssetsProvider.CONTENT_URI + "montpellier.jpg";
		
		if ((vignette != null) && (vignette.length()) > 0)
			str = AssetsProvider.CONTENT_URI + vignette;*/

		return NetworkCall.PATH + vignette;
	}

	public void setList_photos(Collection<Photo> list_photos) {
		this.list_photos = list_photos;
	}

	public Collection<Photo> getList_photos() {
		return list_photos;
	}

	public String getAdresses() {
		String ret;
		ret = getAdresse().trim() + "\n" + getAdresse2().trim() + "\n" + getAdresse3().trim() + "\n";
		return ret;
	}
}
