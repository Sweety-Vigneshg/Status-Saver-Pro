package com.vickystft.statussaverpro.util;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.vickystft.statussaverpro.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static Dialog customDialog;
    private static Context context;


    public static String RootDirectoryFacebook = "/StatusSaver/Facebook/";
    public static String RootDirectoryInsta = "/StatusSaver/Insta/";
    public static String RootDirectoryTikTok = "/StatusSaver/TikTok/";
    public static String RootDirectoryTwitter ="/StatusSaver/Twitter/";

    public static String RootDirectoryLikee ="/StatusSaver/Likee/";
    public static String RootDirectoryShareChat ="/StatusSaver/ShareChat/";
    public static String RootDirectoryRoposo ="/StatusSaver/Roposo/";
    public static String RootDirectorySnackVideo ="/StatusSaver/SnackVideo/";
    public static final String ROOTDIRECTORYJOSH ="/StatusSaver/Josh/";
    public static final String ROOTDIRECTORYCHINGARI ="/StatusSaver/Chingari/";
    public static final String ROOTDIRECTORYMITRON ="/StatusSaver/Mitron/";
    public static final String ROOTDIRECTORYMX ="/StatusSaver/Mxtakatak/";
    public static final String ROOTDIRECTORYMOJ ="/StatusSaver/Moj/";

    public static File RootDirectoryFacebookShow = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/Facebook");
    public static File RootDirectoryInstaShow = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/Insta");
    public static File RootDirectoryTikTokShow = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/TikTok");
    public static File RootDirectoryTwitterShow = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/Twitter");
    public static File RootDirectoryWhatsappShow = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/Whatsapp");
    public static File RootDirectoryLikeeShow = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/Likee");
    public static File RootDirectoryShareChatShow = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/ShareChat");
    public static File RootDirectoryRoposoShow = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/Roposo");
    public static File RootDirectorySnackVideoShow = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/SnackVideo");
    public static final File ROOTDIRECTORYJOSHSHOW = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/Josh");
    public static final File ROOTDIRECTORYCHINGARISHOW = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/Chingari");
    public static final File ROOTDIRECTORYMITRONSHOW = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/Mitron");
    public static final File ROOTDIRECTORYMXSHOW = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/Mxtakatak");
    public static final File ROOTDIRECTORYMOJSHOW = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/Moj");

    public static String PrivacyPolicyUrl = "https://graduate-hands-tamil.blogspot.com/p/vmatee-app-privacy-policy.html";
    public static String TikTokUrl = "http://androidqueue.com/tiktokapi/api.php";

    public Utils(Context _mContext) {
        context = _mContext;
    }

    public static void setToast(Context _mContext, String str) {
        Toast toast = Toast.makeText(_mContext, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void createFileFolder() {
        if (!RootDirectoryFacebookShow.exists()) {
            RootDirectoryFacebookShow.mkdirs();
        }
        if (!RootDirectoryInstaShow.exists()) {
            RootDirectoryInstaShow.mkdirs();
        }
        if (!RootDirectoryTikTokShow.exists()) {
            RootDirectoryTikTokShow.mkdirs();
        }
        if (!RootDirectoryTwitterShow.exists()) {
            RootDirectoryTwitterShow.mkdirs();
        }
        if (!RootDirectoryWhatsappShow.exists()) {
            RootDirectoryWhatsappShow.mkdirs();
        }
        if (!RootDirectoryLikeeShow.exists()){
            RootDirectoryLikeeShow.mkdirs();
        }
        if (!RootDirectoryLikeeShow.exists()){
            RootDirectoryLikeeShow.mkdirs();
        }
        if (!RootDirectoryShareChatShow.exists()){
            RootDirectoryShareChatShow.mkdirs();
        }
        if (!RootDirectoryRoposoShow.exists()){
            RootDirectoryRoposoShow.mkdirs();
        }
        if (!RootDirectorySnackVideoShow.exists()){
            RootDirectorySnackVideoShow.mkdirs();
        }
        if (!ROOTDIRECTORYJOSHSHOW.exists()){
            ROOTDIRECTORYJOSHSHOW.mkdirs();
        }
        if (!ROOTDIRECTORYCHINGARISHOW.exists()){
            ROOTDIRECTORYCHINGARISHOW.mkdirs();
        }
        if (!ROOTDIRECTORYMITRONSHOW.exists()){
            ROOTDIRECTORYMITRONSHOW.mkdirs();
        }

        if (!ROOTDIRECTORYMXSHOW.exists()){
            ROOTDIRECTORYMXSHOW.mkdirs();
        }
        if (!ROOTDIRECTORYMOJSHOW.exists()){
            ROOTDIRECTORYMOJSHOW.mkdirs();
        }

    }

    public static void showProgressDialog(Activity activity) {
        System.out.println("Show");
        if (customDialog != null) {
            customDialog.dismiss();
            customDialog = null;
        }
        customDialog = new Dialog(activity);
        LayoutInflater inflater = LayoutInflater.from(activity);
        View mView = inflater.inflate(R.layout.progress_dialog, null);
        customDialog.setCancelable(false);
        customDialog.setContentView(mView);
        if (!customDialog.isShowing() && !activity.isFinishing()) {
            customDialog.show();
        }
    }

    public static void hideProgressDialog(Activity activity) {
        System.out.println("Hide");
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @SuppressLint("ObsoleteSdkInt")
    public static void startDownload(String downloadPath, String destinationPath, Context context, String FileName) {
        setToast(context, context.getResources().getString(R.string.download_started));
        Uri uri = Uri.parse(downloadPath); // Path where you want to download file.
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);  // Tell on which network you want to download file.
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);  // This will show notification on top when downloading the file.
        request.setTitle(FileName+""); // Title for notification.
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS,destinationPath+FileName);  // Storage directory path
        ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request); // This will start downloading

        try {
            if (Build.VERSION.SDK_INT >= 21) {
                MediaScannerConnection.scanFile(context, new String[]{new File(DIRECTORY_DOWNLOADS + "/" + destinationPath + FileName).getAbsolutePath()},
                        null, new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
                            }
                        });
            } else {
                context.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.fromFile(new File(DIRECTORY_DOWNLOADS + "/" + destinationPath + FileName))));
            }



        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void shareImage(Context context, String filePath) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, context.getResources().getString(R.string.share_txt));
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), filePath, "", null);
            Uri screenshotUri = Uri.parse(path);
            intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
            intent.setType("image/*");
            context.startActivity(Intent.createChooser(intent, context.getResources().getString(R.string.share_image_via)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void shareImageVideoOnWhatsapp(Context context, String filePath, boolean isVideo) {
        Uri imageUri = Uri.parse(filePath);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setPackage("com.whatsapp");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "");
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        if (isVideo) {
            shareIntent.setType("video/*");
        }else {
            shareIntent.setType("image/*");
        }
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            context.startActivity(shareIntent);
        } catch (Exception e) {
            Utils.setToast(context,context.getResources().getString(R.string.whatsapp_not_installed));
        }
    }

    public static void shareVideo(Context context, String filePath) {
        Uri mainUri = Uri.parse(filePath);
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("video/mp4");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, mainUri);
        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            context.startActivity(Intent.createChooser(sharingIntent, "Share Video using"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, context.getResources().getString(R.string.no_app_installed), Toast.LENGTH_LONG).show();
        }
    }

    public static void RateApp(Context context) {
        final String appName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appName)));
        }
    }

    public static void MoreApp(Context context) {
        final String appName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appName)));
        }
    }

    public static void ShareApp(Context context) {
        final String appLink = "\nhttps://play.google.com/store/apps/details?id=" + context.getPackageName();
        Intent sendInt = new Intent(Intent.ACTION_SEND);
        sendInt.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
        sendInt.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.share_app_message) + appLink);
        sendInt.setType("text/plain");
        context.startActivity(Intent.createChooser(sendInt, "Share"));
    }

    public static void OpenApp(Context context,String Package) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(Package);
        if (launchIntent != null) {
            context.startActivity(launchIntent);
        } else {
            setToast(context,context.getResources().getString(R.string.app_not_available));
        }
    }

    public static boolean isNullOrEmpty(String s) {
        return (s == null) || (s.length() == 0) || (s.equalsIgnoreCase("null"))||(s.equalsIgnoreCase("0"));
    }

    public static List<String> extractUrls(String text) {
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find()) {
            containedUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }

        return containedUrls;
    }

}