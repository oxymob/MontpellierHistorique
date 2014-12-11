package fr.oxymob.montpellier.historique.utils;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;

import fr.oxymob.montpellier.historique.utils.Functions;

/**
 * Created by dany on 05/09/14.
 */
public class NetworkCall {
    public static final String PATH = "http://oxymob.fr/server/montpellier/";
    private final String mFileName;
    private final Context mContext;
    private NetworkCallListener mListener;
    private DownloadFilesTask mTask;

    public void setListener(NetworkCallListener mListener) {
        this.mListener = mListener;
    }

    public interface NetworkCallListener {
        public void onFileDownloaded(String fileName);
        public void onError();
    }

    public NetworkCall(Context context, String fileName) {
        mContext = context;
        mFileName = fileName;
    }

    public void fetch() {
        mTask = new DownloadFilesTask();
        mTask.execute(PATH + mFileName);
    }

    public void cancel() {
        if (mTask != null)
            mTask.cancel(true);
    }

    private class DownloadFilesTask extends AsyncTask<String, Void, Long> {
        protected Long doInBackground(String... urls) {
            int count = urls.length;
            long totalSize = 0;
            for (int i = 0; i < count; i++) {
                try {
                    Functions.downloadFile(mContext, urls[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return totalSize;
        }

        protected void onPostExecute(Long result) {
            if (mListener != null)
                mListener.onFileDownloaded(mFileName);
        }
    }
}
