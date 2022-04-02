package com.qrcodegenerator.creation.ui.splash;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qrcodegenerator.creation.R;
import com.qrcodegenerator.creation.ui.base.BaseActivity;
import com.qrcodegenerator.creation.ui.main.MainActivity;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.CAMERA;

public class SplashActivity extends BaseActivity implements SplashMvpView {

    private static final int REQUEST_CAMERA = 101;

    @Inject
    SplashMvpPresenter<SplashMvpView> mPresenter;

    @BindView(R.id.tv_scanner)
    TextView tvScanner;

    @BindView(R.id.rl_icon)
    RelativeLayout rlIcon;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SplashActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();
    }

    @Override
    public void setupPermission() {
        if (!hasPermission(CAMERA)) {
            requestPermission(new String[]{CAMERA}, REQUEST_CAMERA);
        } else {
            mPresenter.permissionGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPresenter.permissionGranted();
            } else {
                if (shouldShowRational(CAMERA)) {
                    mPresenter.permissionDenied();
                }
            }
        }
    }

    @Override
    public void showNeedAccessDialog() {
        new AlertDialog.Builder(SplashActivity.this,  R.style.Theme_MaterialComponents_Light_Dialog_Alert)
                .setMessage(R.string.asses_camera)
                .setPositiveButton(getResources().getText(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestPermission(new String[]{CAMERA}, REQUEST_CAMERA);
                    }
                })
                .setNegativeButton(getResources().getText(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void openMainActivity() {
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(rlIcon, "scaleX", 0.3f, 1.0f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(rlIcon, "scaleY", 0.3f, 1.0f);
        scaleDownX.setDuration(3000);
        scaleDownY.setDuration(3000);

        AnimatorSet scaleDown = new AnimatorSet();
        scaleDown.play(scaleDownX).with(scaleDownY);

        scaleDown.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                rlIcon.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                tvScanner.setVisibility(View.VISIBLE);
                Intent intent = MainActivity.getStartIntent(SplashActivity.this);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        scaleDown.start();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {
        if (isNetworkConnected()) {
            setupPermission();
        } else {
            showMessage(getResources().getString(R.string.internet_connection_error));
        }
    }
}
