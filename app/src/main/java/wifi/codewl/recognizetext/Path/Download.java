package wifi.codewl.recognizetext.Path;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class Download {

    public static void download(Context context, String url,String path,String name,String title, String suffix){
        System.out.println("--------------------------------");
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI|DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Download language "+name);
        //request.setDescription("\nDownload File ...");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,title+"."+suffix);
        //request.setDestinationInExternalFilesDir(context,path,title+"."+suffix);
        request.setDestinationUri(Uri.fromFile(new File(path,title+"."+suffix)));

        DownloadManager manager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        //request.setMimeType("application/pdf");
        manager.enqueue(request);
    }

    public static void copyFile(File source, File target) throws IOException {
        FileChannel inChannel = new FileInputStream(source).getChannel();
        FileChannel outChannel = new FileOutputStream(target).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        }
        finally {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }
}
