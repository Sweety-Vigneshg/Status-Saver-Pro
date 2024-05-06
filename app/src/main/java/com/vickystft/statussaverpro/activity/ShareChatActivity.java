package com.vickystft.statussaverpro.activity;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;
import static android.content.ContentValues.TAG;
import static com.vickystft.statussaverpro.util.Utils.RootDirectoryShareChat;
import static com.vickystft.statussaverpro.util.Utils.createFileFolder;
import static com.vickystft.statussaverpro.util.Utils.startDownload;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.vickystft.statussaverpro.R;
import com.vickystft.statussaverpro.api.CommonClassForAPI;
import com.vickystft.statussaverpro.databinding.LayoutGlobalUiBinding;
import com.vickystft.statussaverpro.util.AppLangSessionManager;
import com.vickystft.statussaverpro.util.SharePrefs;
import com.vickystft.statussaverpro.util.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;

public class ShareChatActivity extends AppCompatActivity {
    private LayoutGlobalUiBinding binding;
    ShareChatActivity activity;
    CommonClassForAPI commonClassForAPI;
    private String VideoUrl;
    private ClipboardManager clipBoard;
    AppLangSessionManager appLangSessionManager;
    InterstitialAd mInterstitialAd;
    UnifiedNativeAd admobNativeAD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.layout_global_ui);
        activity = this;
        commonClassForAPI = CommonClassForAPI.getInstance(activity);
        createFileFolder();
        initViews();

        binding.imAppIcon.setImageDrawable(getResources().getDrawable(R.drawable.sharechat));
        binding.tvAppName.setText(getResources().getString(R.string.sharechat_app_name));


        appLangSessionManager = new AppLangSessionManager(activity);
        setLocale(appLangSessionManager.getLanguage());


        InterstitialAdsINIT();
        LoadNativeAd();
    }

    @Override
    protected void onResume() {
        super.onResume();
        activity = this;
        assert activity != null;
        clipBoard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
        PasteText();
    }

    private void initViews() {
        clipBoard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);

        binding.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.imInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.layoutHowTo.LLHowToLayout.setVisibility(View.VISIBLE);
            }
        });

        Glide.with(activity)
                .load(R.drawable.sc1)
                .into(binding.layoutHowTo.imHowto1);

        Glide.with(activity)
                .load(R.drawable.sc2)
                .into(binding.layoutHowTo.imHowto2);

        Glide.with(activity)
                .load(R.drawable.sc1)
                .into(binding.layoutHowTo.imHowto3);

        Glide.with(activity)
                .load(R.drawable.sc2)
                .into(binding.layoutHowTo.imHowto4);

        binding.layoutHowTo.tvHowToHeadOne.setVisibility(View.GONE);
        binding.layoutHowTo.LLHowToOne.setVisibility(View.GONE);
        binding.layoutHowTo.tvHowToHeadTwo.setText(getResources().getString(R.string.how_to_download));

        binding.layoutHowTo.tvHowTo1.setText(getResources().getString(R.string.open_sharechat));
        binding.layoutHowTo.tvHowTo3.setText(getResources().getString(R.string.cop_link_from_sharechat));
        if (!SharePrefs.getInstance(activity).getBoolean(SharePrefs.ISSHOWHOWTOSHARECHAT)) {
            SharePrefs.getInstance(activity).putBoolean(SharePrefs.ISSHOWHOWTOSHARECHAT, true);
            binding.layoutHowTo.LLHowToLayout.setVisibility(View.VISIBLE);
        } else {
            binding.layoutHowTo.LLHowToLayout.setVisibility(View.GONE);
        }


        binding.loginBtn1.setOnClickListener(v -> {
            String LL = binding.etText.getText().toString();
            if (LL.equals("")) {
                Utils.setToast(activity, getResources().getString(R.string.enter_url));
            } else if (!Patterns.WEB_URL.matcher(LL).matches()) {
                Utils.setToast(activity, getResources().getString(R.string.enter_valid_url));
            } else {
                Utils.showProgressDialog(activity);
                GetSharechatData();
            }
        });

        binding.tvPaste.setOnClickListener(v -> {
            PasteText();
        });

        binding.LLOpenApp.setOnClickListener(v -> {
            Utils.OpenApp(activity, "in.mohalla.sharechat");
        });
    }

    private void GetSharechatData() {
        try {
            createFileFolder();
            URL url = new URL(binding.etText.getText().toString());
            String host = url.getHost();
            if (host.contains("sharechat")) {
                Utils.showProgressDialog(activity);
                new callGetShareChatData().execute(binding.etText.getText().toString());
                showInterstitialAds();
            } else {
                Utils.setToast(activity, getResources().getString(R.string.enter_url));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void PasteText() {
        try {
            binding.etText.setText("");
            String CopyIntent = getIntent().getStringExtra("CopyIntent");
            if (CopyIntent.equals("")) {

                if (!(clipBoard.hasPrimaryClip())) {

                } else if (!(clipBoard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN))) {
                    if (clipBoard.getPrimaryClip().getItemAt(0).getText().toString().contains("sharechat")) {
                        binding.etText.setText(clipBoard.getPrimaryClip().getItemAt(0).getText().toString());
                    }

                } else {
                    ClipData.Item item = clipBoard.getPrimaryClip().getItemAt(0);
                    if (item.getText().toString().contains("sharechat")) {
                        binding.etText.setText(item.getText().toString());
                    }

                }
            } else {
                if (CopyIntent.contains("sharechat")) {
                    binding.etText.setText(CopyIntent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class callGetShareChatData extends AsyncTask<String, Void, Document> {
        Document ShareChatDoc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Document doInBackground(String... urls) {
            try {
                ShareChatDoc = Jsoup.connect(urls[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "doInBackground: Error");
            }
            return ShareChatDoc;
        }

        protected void onPostExecute(Document result) {
            Utils.hideProgressDialog(activity);
            try {

                VideoUrl = result.select("meta[property=\"og:video:secure_url\"]").last().attr("content");
                Log.e("onPostExecute: ", VideoUrl);
                if (!VideoUrl.equals("")) {
                    try {
                        startDownload(VideoUrl, RootDirectoryShareChat, activity, "sharechat_"+System.currentTimeMillis() + ".mp4");
                        VideoUrl = "";
                        binding.etText.setText("");

                        showInterstitialAds();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);


    }


    public void InterstitialAdsINIT() {

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        mInterstitialAd = new com.google.android.gms.ads.InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.admob_interstitial_ad));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }


    private void showInterstitialAds() {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }




    public void LoadNativeAd() {
        {
            binding.myTemplate.setVisibility(View.GONE);

            MobileAds.initialize(activity, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });
            AdLoader adLoader = new AdLoader.Builder(activity, getResources().getString(R.string.admob_native_ad))
                    .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                        @Override
                        public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                            if (admobNativeAD != null) {
                                admobNativeAD.destroy();
                            }
                            admobNativeAD = unifiedNativeAd;

                            NativeTemplateStyle styles = new
                                    NativeTemplateStyle.Builder()
                                    .withCallToActionBackgroundColor
                                            (new ColorDrawable(ContextCompat.getColor(activity, R.color.colorAccent)))
                                    .build();
                            binding.myTemplate.setStyles(styles);
                            binding.myTemplate.setNativeAd(unifiedNativeAd);
                            binding.myTemplate.setBackground(getResources().getDrawable(R.drawable.rectangle_white));
                            binding.myTemplate.setVisibility(View.VISIBLE);
                        }
                    })
                    .build();
            adLoader.loadAd(new AdRequest.Builder().build());
        }
    }


}