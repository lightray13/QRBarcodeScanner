package com.qrcodegenerator.creation.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.qrcodegenerator.creation.R;
import com.qrcodegenerator.creation.utils.AppUtils;

import javax.inject.Inject;

public class MainActivity extends DrawerActivity implements MainMvpView, View.OnClickListener {

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    @Inject
    FragmentAdapter fragmentAdapter;

    @BindView(R.id.materialViewPager)
    MaterialViewPager mViewPager;

    @BindView(R.id.nav_settings)
    LinearLayout navSettings;

    @BindView(R.id.nav_share)
    LinearLayout navShare;

    @BindView(R.id.nav_rating)
    LinearLayout navRating;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();

        navSettings.setOnClickListener(this);
        navShare.setOnClickListener(this);
        navRating.setOnClickListener(this);
    }

    @Override
    public void shareAppSharingIntent() {
        AppUtils.shareApp(this);
    }

    @Override
    public void rateAppSharingIntent() {
        AppUtils.rateApp(this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {
        setTitle("");
        ButterKnife.bind(this);

        final Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        mViewPager.getViewPager().setAdapter(fragmentAdapter);

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                TypedArray images = getResources().obtainTypedArray(R.array.viewpager_page_images);
                TypedArray colors = getResources().obtainTypedArray(R.array.viewpager_page_colors);
                HeaderDesign design =  HeaderDesign.fromColorResAndDrawable(colors.getResourceId(page, R.color.colorHeaderOne),
                        images.getDrawable(page));
                images.recycle();
                colors.recycle();
                if (page == 0) {
                    mPresenter.setScanScreenPref(true);
                } else if (page == 1) {
                    mPresenter.setScanScreenPref(false);
                }
                return design;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_settings:
//                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.nav_share:
                mPresenter.shareAppButtonPressed();
                break;
            case R.id.nav_rating:
                mPresenter.rateAppButtonPressed();
                break;
        }
    }
}

