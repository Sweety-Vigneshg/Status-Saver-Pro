package com.vickystft.statussaverpro.activity;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;
import static com.vickystft.statussaverpro.util.Utils.createFileFolder;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.vickystft.statussaverpro.R;
import com.vickystft.statussaverpro.databinding.ActivityGalleryBinding;
import com.vickystft.statussaverpro.fragment.AllinOneGalleryFragment;
import com.vickystft.statussaverpro.fragment.FBDownloadedFragment;
import com.vickystft.statussaverpro.fragment.InstaDownloadedFragment;
import com.vickystft.statussaverpro.fragment.LikeeDownloadedFragment;
import com.vickystft.statussaverpro.fragment.RoposoDownloadedFragment;
import com.vickystft.statussaverpro.fragment.SharechatDownloadedFragment;
import com.vickystft.statussaverpro.fragment.SnackVideoDownloadedFragment;
import com.vickystft.statussaverpro.fragment.TikTokDownloadedFragment;
import com.vickystft.statussaverpro.fragment.TwitterDownloadedFragment;
import com.vickystft.statussaverpro.fragment.WhatsAppDowndlededFragment;
import com.vickystft.statussaverpro.util.AdsUtils;
import com.vickystft.statussaverpro.util.AppLangSessionManager;
import com.vickystft.statussaverpro.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GalleryActivity  extends AppCompatActivity {
    GalleryActivity activity;
    ActivityGalleryBinding binding;

    AppLangSessionManager appLangSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gallery);
        activity = this;

        appLangSessionManager = new AppLangSessionManager(activity);
        setLocale(appLangSessionManager.getLanguage());
        AdsUtils.showGoogleBannerAd(activity,binding.adView);

        initViews();
    }

    public void initViews() {
        setupViewPager(binding.viewpager);
        binding.tabs.setupWithViewPager(binding.viewpager);
        binding.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        for (int i = 0; i < binding.tabs.getTabCount(); i++) {
            TextView tv=(TextView) LayoutInflater.from(activity).inflate(R.layout.custom_tab,null);
            binding.tabs.getTabAt(i).setCustomView(tv);
        }

        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        createFileFolder();
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(activity.getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new AllinOneGalleryFragment(Utils.ROOTDIRECTORYJOSHSHOW), "Josh");
        adapter.addFragment(new AllinOneGalleryFragment(Utils.ROOTDIRECTORYCHINGARISHOW), "Chingari");
        adapter.addFragment(new AllinOneGalleryFragment(Utils.ROOTDIRECTORYMITRONSHOW), "Mitron");
        adapter.addFragment(new SnackVideoDownloadedFragment(), "Snack Video");
        adapter.addFragment(new SharechatDownloadedFragment(), "Sharechat");
        adapter.addFragment(new RoposoDownloadedFragment(), "Roposo");
        adapter.addFragment(new InstaDownloadedFragment(), "Instagram");
        adapter.addFragment(new WhatsAppDowndlededFragment(), "Whatsapp");
        adapter.addFragment(new TikTokDownloadedFragment(), "TikTok");
        adapter.addFragment(new FBDownloadedFragment(), "Facebook");
        adapter.addFragment(new TwitterDownloadedFragment(), "Twitter");
        adapter.addFragment(new LikeeDownloadedFragment(), "Likee");
        adapter.addFragment(new AllinOneGalleryFragment(Utils.ROOTDIRECTORYMXSHOW), "MXTakaTak");
        adapter.addFragment(new AllinOneGalleryFragment(Utils.ROOTDIRECTORYMOJSHOW), "Moj");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
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
}
