package fr.oxymob.montpellier.historique.utils;

import android.content.Context;
import android.os.Environment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Functions {

    public static String formatTimeStamp(long timestamp) {
        String ret = null;

        ret = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(timestamp);

        return ret;
    }

    public static String[] splitTotokens(String line, String delim) {
        int i = 0;

        if (line.charAt(line.length() - 1) != delim.charAt(0))
            line = line + delim;

        String s = line;
        while (s.contains(delim)) {
            s = s.substring(s.indexOf(delim) + delim.length());
            i++;
        }

        //if (!s.equals(delim))
        //	i++;

        String token = null;
        String remainder = null;
        String[] tokens = new String[i];

        for (int j = 0; j < i; j++) {
            token = line.substring(0, line.indexOf(delim));
            //System.out.print("#" + token + "#");
            tokens[j] = token;
            remainder = line.substring(line.indexOf(delim) + delim.length());
            //System.out.println("#" + remainder + "#");

            line = remainder;
        }

        return tokens;
    }

    public static boolean exists(String URLName){
        try {
            HttpURLConnection.setFollowRedirects(false);
            // note : you may also need
            //        HttpURLConnection.setInstanceFollowRedirects(false)
            HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Using HTTP_NOT_MODIFIED
    public static boolean Changed(String url){
        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_NOT_MODIFIED);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // GET THE LAST MODIFIED TIME
    public static long LastModified(String url) throws IOException {
        HttpURLConnection.setFollowRedirects(false);
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        long date = con.getLastModified();

        if (date == 0)
            System.out.println("No last-modified information.");
        else
            System.out.println("Last-Modified: " + new Date(date));

        return date;
    }
    public static String downloadFile(Context context, String urlFile) throws IOException {
        String fileName = urlFile.substring(urlFile.lastIndexOf("/") + 1);
        URL url = new URL(urlFile);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoOutput(true);

        //connect
        urlConnection.connect();

        //set the path where we want to save the file
        //create a new file, to save the downloaded file
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);
        FileOutputStream fileOutput = new FileOutputStream(file);

        //Stream used for reading the data from the internet
        InputStream inputStream = urlConnection.getInputStream();
        //create a buffer...
        byte[] buffer = new byte[1024];
        int bufferLength = 0;

        while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
            fileOutput.write(buffer, 0, bufferLength);
        }
        //close the output stream when complete //
        fileOutput.close();



        return fileName;
    }

    public static String openFile(Context context, String fileName) {
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                //text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }
        return text.toString();
    }

    public static boolean fileExists(Context context, String sFileName) {
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), sFileName);
        return file.exists();
    }
}