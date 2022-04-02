package com.qrcodegenerator.creation.ui.scanning;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.qrcodegenerator.creation.R;
import com.qrcodegenerator.creation.data.db.model.History;
import com.qrcodegenerator.creation.ui.base.BaseActivity;
import com.qrcodegenerator.creation.ui.main.create.TypeItem;
import com.qrcodegenerator.creation.utils.ActionsUtils;
import com.qrcodegenerator.creation.utils.AppConstants;
import com.qrcodegenerator.creation.utils.ButtonsUtils;
import com.qrcodegenerator.creation.utils.CommonUtils;
import com.qrcodegenerator.creation.utils.ConnectionManager;
import com.qrcodegenerator.creation.utils.FieldsUtils;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

public class ScanningActivity extends BaseActivity implements ScanningMvpView, AdapterView.OnItemClickListener, View.OnClickListener {

    @Inject
    ScanningMvpPresenter<ScanningMvpView> mPresenter;

    @Inject
    ResultAdapter mAdapter;

    @Inject
    ButtonsAdapter mButtonsAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    GridLayoutManager mGridLayoutManager;

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.iv_code)
    ImageView ivCode;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.card_view)
    CardView cardView;

    @BindView(R.id.iv_icon)
    ImageView ivIcon;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.recycler_view_buttons)
    RecyclerView recyclerViewButtons;

    private static final int REQUEST_LOCATION = 104;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ScanningActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        mButtonsAdapter.setOnItemClickListener(this);

        setUp();

        ivBack.setOnClickListener(this);
    }

    @Override
    protected void setUp() {
//        Gson gson = new Gson();
//        long result = getIntent().getLongExtra("result", 0);
//        Toast.makeText(this, String.valueOf(result), Toast.LENGTH_LONG).show();
        long id = getIntent().getLongExtra("id", 0);
        mPresenter.onViewInitialized(id);
//        Result rawResult = gson.fromJson(strObj, Result.class);
//        String type = ResultParser.parseResult(rawResult).getType().name();
//        List<ScanResult> items = FieldsUtils.getResultsList(this, rawResult);
//        if (items.size() > 0) {
//            recyclerView.setLayoutManager(mLayoutManager);
//            mAdapter.addItems(items);
//            recyclerView.setAdapter(mAdapter);
//        }
//        List<TypeItem> buttons = ButtonsUtils.getButtonsList(type);
//        if (buttons.size() > 0) {
//            recyclerViewButtons.setLayoutManager(mGridLayoutManager);
//            mButtonsAdapter.addItems(buttons);
//            recyclerViewButtons.setAdapter(mButtonsAdapter);
//        }
        // add item to db
    }

    @Override
    public void setupPermission() {
        if (!hasPermission(ACCESS_COARSE_LOCATION)) {
            requestPermission(new String[]{ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        } else {
            mPresenter.permissionGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPresenter.permissionGranted();
            } else {
                mPresenter.permissionDenied();
            }
        }
    }

    @Override
    public void backToMainActivity() {
        onBackPressed();
        finish();
    }

    @Override
    public void setHeader(String type) {
        switch (type) {
            case AppConstants.TEXT:
                setBackground(R.color.colorHeaderOne, R.drawable.text, R.string.text);
                break;
            case AppConstants.URI:
                setBackground(R.color.colorHeaderSix, R.drawable.website, R.string.website);
                break;
            case AppConstants.TEL:
                setBackground(R.color.colorHeaderSeven, R.drawable.phone, R.string.telephone);
                break;
            case AppConstants.ADDRESSBOOK:
                setBackground(R.color.colorHeaderTwo, R.drawable.contacts, R.string.contacts);
                break;
            case AppConstants.EMAIL_ADDRESS:
                setBackground(R.color.colorHeaderEight, R.drawable.email, R.string.email);
                break;
            case AppConstants.SMS:
                setBackground(R.color.colorHeaderThree, R.drawable.message, R.string.message);
                break;
            case AppConstants.GEO:
                setBackground(R.color.colorHeaderFive, R.drawable.location, R.string.location);
                break;
            case AppConstants.WIFI:
                setBackground(R.color.colorHeaderNine, R.drawable.wifi, R.string.wifi);
                break;
            case AppConstants.PRODUCT:
                setBackground(R.color.optionColorOne, R.drawable.product, R.string.product);
                break;
            case AppConstants.CALENDAR:
                setBackground(R.color.optionColorOne, R.drawable.calendar, R.string.event);
                break;
            case AppConstants.ISBN:
                setBackground(R.color.optionColorOne, R.drawable.ic_book, R.string.isbn);
                break;
        }
    }

    private void setBackground(int color, int drawable, int text) {
        cardView.setCardBackgroundColor(Color.parseColor(getResources().getString(color)));
        ivIcon.setImageResource(drawable);
        tvTitle.setText(text);
    }

    @Override
    public void setButtons(History history) {
        List<TypeItem> buttons = ButtonsUtils.getButtonsList(history);
        if (buttons.size() > 0) {
            recyclerViewButtons.setLayoutManager(mGridLayoutManager);
            mButtonsAdapter.addItems(buttons);
            recyclerViewButtons.setAdapter(mButtonsAdapter);
        }
    }

    @Override
    public void setFields(History history) {
        List<ScanResult> items = FieldsUtils.getResultsList(this, history);
        if (items.size() > 0) {
            recyclerView.setLayoutManager(mLayoutManager);
            mAdapter.addItems(items);
            recyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void setQrImage(History history) {
        if (!TextUtils.isEmpty(history.qrText) && !TextUtils.isEmpty(history.format)) {
            try {
                Bitmap bitmap = CommonUtils.encodeAsBitmap(history, ivCode.getHeight(), ivCode.getWidth());
                ivCode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void openMap(double latitude, double longitude) {
        ActionsUtils.openMap(this, latitude, longitude);
    }

    @Override
    public void addContact(String name, String position, String company, String phone, String phoneTwo, String email, String location, String url) {
        ActionsUtils.addContact(this, name, position, company, phone, phoneTwo, email, location, url);
    }

    @Override
    public void dialPhone(String phone) {
        ActionsUtils.dialNumber(this, phone);
    }

    @Override
    public void sendSms(String phone, String message) {
        ActionsUtils.sendSms(this, phone, message);
    }

    @Override
    public void sendEmail(String email, String title, String message) {
        ActionsUtils.sendEmail(this, email, title, message);
    }

    @Override
    public void openUrl(String url) {
        ActionsUtils.openUrl(this, url);
    }

    @Override
    public void addEvent(String title, String description, String location, long startTime, long endTime) {
        ActionsUtils.addEvent(this, title, description, location, startTime, endTime);
    }

    @Override
    public void search(String text) {
        ActionsUtils.search(this, text);
    }

    @Override
    public void share(String text) {
        ActionsUtils.share(this, text);
    }

    @Override
    public void copy(String text) {
        ActionsUtils.copy(this, text);
    }

    @Override
    public void connectToWifi(String ssid, String password) {
        ConnectionManager connectionManager = new ConnectionManager(this);
        connectionManager.enableWifi();
        connectionManager.requestWIFIConnection(ssid, password);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mButtonsAdapter.getButtonTitle(position) == R.string.btn_title_connect_wifi) {
            setupPermission();
        } else {
            mPresenter.actionButtonPressed(mButtonsAdapter.getButtonTitle(position));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                mPresenter.backButtonPressed();
                break;
        }
    }
}

