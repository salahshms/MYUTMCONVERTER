package salahsh.shams;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class shareApk {
    public static void shareAPK(Activity activity) {
        try {
            // First we should copy apk file from source dir to ur external dir
            ApplicationInfo app = activity.getPackageManager().getApplicationInfo(activity.getPackageName(), 0);

            File apkFile = new File(app.sourceDir);
            File backupFile = new File(Environment.getExternalStorageDirectory(), "مبدل واحد UTM.apk");

            copy(apkFile, backupFile);

            Intent shareIntent = getShareIntent(backupFile);
            activity.startActivity(Intent.createChooser(shareIntent, "فرستادن فایل نصبی برنامه تبدیل واحد برای دوستان"));
        } catch (PackageManager.NameNotFoundException | IOException e) {
            e.printStackTrace();
        }

    }


    public static Intent getShareIntent(File file) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        intent.setType("application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        return intent;
    }


    public static void copy(File src, File dst) throws IOException {
        FileInputStream inStream = new FileInputStream(src);
        FileOutputStream outStream = new FileOutputStream(dst);
        FileChannel inChannel = inStream.getChannel();
        FileChannel outChannel = outStream.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inStream.close();
        outStream.close();
    }
}
