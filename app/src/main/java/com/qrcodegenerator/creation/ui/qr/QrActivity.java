package com.qrcodegenerator.creation.ui.qr;

import android.content.Context;
import android.content.Intent;
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
import com.qrcodegenerator.creation.ui.scanning.ButtonsAdapter;
import com.qrcodegenerator.creation.utils.AppConstants;
import com.qrcodegenerator.creation.utils.ButtonsUtils;
import com.qrcodegenerator.creation.utils.CommonUtils;

import java.util.List;

import javax.inject.Inject;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class QrActivity extends BaseActivity implements QrMvpView, AdapterView.OnItemClickListener, View.OnClickListener {

    @Inject
    QrMvpPresenter<QrMvpView> mPresenter;

    @Inject
    ButtonsAdapter mButtonsAdapter;

    @Inject
    GridLayoutManager mGridLayoutManager;

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.iv_code)
    ImageView ivCode;

    @BindView(R.id.iv_icon)
    ImageView ivIcon;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.card_view)
    CardView cardView;

    @BindView(R.id.recycler_view_buttons)
    RecyclerView recyclerViewButtons;

    public static Intent getQrIntent(Context context) {
        return new Intent(context, QrActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        mButtonsAdapter.setOnItemClickListener(this);

        setUp();

        ivBack.setOnClickListener(this);
    }

    @Override
    protected void setUp() {
        long id = getIntent().getLongExtra("id", 0);
        mPresenter.onViewInitialized(id);
        setButtons();
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, QrActivity.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                mPresenter.backButtonPressed();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                mPresenter.saveButtonPressed();
                break;
        }
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
    public void setButtons() {
        List<TypeItem> buttons = ButtonsUtils.getCreatedButtons();
        if (buttons.size() > 0) {
            recyclerViewButtons.setLayoutManager(mGridLayoutManager);
            mButtonsAdapter.addItems(buttons);
            recyclerViewButtons.setAdapter(mButtonsAdapter);
        }
    }

    @Override
    public void setQrImage(History history) {
        String str = history.qrText;
        if (!TextUtils.isEmpty(str)) {
            try {
                Bitmap bitmap = CommonUtils.encodeAsBitmap(history, ivCode.getHeight(), ivCode.getWidth());
                ivCode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void backToCreationActivity() {
        onBackPressed();
        finish();
    }

    @Override
    public void saveQrImage() {

    }
}
