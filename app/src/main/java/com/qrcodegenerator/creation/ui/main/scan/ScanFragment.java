package com.qrcodegenerator.creation.ui.main.scan;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.Result;
import com.qrcodegenerator.creation.R;
import com.qrcodegenerator.creation.data.db.model.History;
import com.qrcodegenerator.creation.di.component.ActivityComponent;
import com.qrcodegenerator.creation.ui.base.BaseFragment;
import com.qrcodegenerator.creation.ui.scanning.ScanningActivity;
import com.qrcodegenerator.creation.utils.ParsedResultUtils;

import javax.inject.Inject;

import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class ScanFragment extends BaseFragment implements ScanMvpView, ZXingScannerView.ResultHandler, View.OnClickListener {

    @Inject
    ScanMvpPresenter<ScanMvpView> mPresenter;

    @BindView(R.id.card_view)
    CardView cardView;

    @BindView(R.id.btn_flashlight)
    Button btnFlashlight;

    @BindView(R.id.btn_gallery)
    Button btnGallery;

    private ZXingScannerView scanner;

    private static final int SELECT_PHOTO = 102;
    private static final int REQUEST_EXTERNAL_STORAGE = 103;

    public static ScanFragment newInstance() {
        return new ScanFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        return view;
    }

    @Override
    protected void setUp(View view) {
        if (scanner == null) {
            scanner = new ZXingScannerView(view.getContext());
            scanner.setBorderColor(R.color.colorHeaderOne);
            scanner.setLaserColor(R.color.colorHeaderTwo);
            scanner.setAspectTolerance(0.5f);
            cardView.addView(scanner);
        }

        btnFlashlight.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
    }

    @Override
    public void toggleFlashlight() {
        scanner.setFlash(!scanner.getFlash());
        if (scanner.getFlash()) {
            btnFlashlight.setBackground(getResources().getDrawable(R.drawable.light_on));
        } else {
            btnFlashlight.setBackground(getResources().getDrawable(R.drawable.light_off));
        }
    }

    @Override
    public void showFileChooser() {
        Intent intentImage = new Intent();
        intentImage.setType("image/*");
        intentImage.setAction(Intent.ACTION_PICK);
        startActivityForResult(intentImage, SELECT_PHOTO);
    }

    @Override
    public void setupPermission() {
        if (!hasPermission(READ_EXTERNAL_STORAGE)) {
            requestPermission(new String[]{READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
        } else {
            mPresenter.permissionGranted();
        }
    }

    @Override
    public void openScanningActivity(long id) {
        Intent intent = ScanningActivity.getStartIntent(getContext());
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void resumeCamera() {
        scanner.resumeCameraPreview(this);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 &&  grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showFileChooser();
                showMessage(R.string.permission_granted_external_storage);
                mPresenter.permissionGranted();
            } else {
                mPresenter.permissionDenied();
            }
        }
    }

    @Override
    public void onResume() {
        startCamera();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopCamera();
    }

    private void startCamera() {
        scanner.setResultHandler(this);
        scanner.startCamera();
    }

    private void stopCamera() {
        if (scanner != null) {
            scanner.stopCamera();
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_flashlight:
                mPresenter.toggleFlashlightButtonPressed();
                break;
            case R.id.btn_gallery:
                mPresenter.decodeQrCodeButtonPressed();
                break;
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        History history = ParsedResultUtils.getResultsHistory(rawResult);
        Toast.makeText(getBaseActivity(), rawResult.getBarcodeFormat().name(), Toast.LENGTH_SHORT).show();
        mPresenter.qrCodeScanned(history);
    }
}
