package com.qrcodegenerator.creation.ui.creation;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.qrcodegenerator.creation.R;
import com.qrcodegenerator.creation.data.db.model.History;
import com.qrcodegenerator.creation.ui.base.BaseActivity;
import com.qrcodegenerator.creation.ui.qr.QrActivity;
import com.qrcodegenerator.creation.utils.CreationUtils;
import com.qrcodegenerator.creation.utils.FieldsUtils;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CreationActivity extends BaseActivity implements CreationMvpView, AdapterView.OnItemClickListener, View.OnClickListener {

    @Inject
    CreationMvpPresenter<CreationMvpView> mPresenter;

    @Inject
    CreationFieldsAdapter mAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.card_view)
    CardView cardView;

    @BindView(R.id.iv_icon)
    ImageView ivIcon;

    @BindView(R.id.cv_open)
    CardView cvOpen;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    int position = 0;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, CreationActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position");
        }

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        mAdapter.setOnItemClickListener(this);

        setUp();

        ivBack.setOnClickListener(this);
        cvOpen.setOnClickListener(this);
    }

    @Override
    protected void setUp() {
        position = getIntent().getIntExtra("position", 0);
        setHeader(position);
        setFields(position);
    }

    private void setFields(int position) {
        List<String> items = FieldsUtils.getEtFieldsList(this, position);
        if (items.size() > 0) {
            recyclerView.setLayoutManager(mLayoutManager);
            mAdapter.addItems(items);
            recyclerView.setAdapter(mAdapter);
        }
    }

    private void setHeader(int position) {
        TypedArray images = getResources().obtainTypedArray(R.array.create_button_images);
        TypedArray titles = getResources().obtainTypedArray(R.array.create_button_titles);
        TypedArray colors = getResources().obtainTypedArray(R.array.create_button_colors);
        int color = colors.getResourceId(position, R.color.optionColorOne);
        int image = images.getResourceId(position, R.drawable.qrcode);
        int title = titles.getResourceId(position, R.string.option_generate);
        setBackground(color, image, title);
        images.recycle();
        titles.recycle();
        colors.recycle();
    }

    private void setBackground(int color, int drawable, int text) {
        cardView.setCardBackgroundColor(Color.parseColor(getResources().getString(color)));
        ivIcon.setImageResource(drawable);
        tvTitle.setText(text);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                mPresenter.backButtonPressed();
                break;
            case R.id.cv_open:
                hideKeyboard();
                History history = CreationUtils.createCodeHistory(mAdapter.getEtData(), position);
                if (history.number != 0) {
                    showMessage(history.number);
                } else {
                    mPresenter.qrCodeCreated(history);
                }
                break;
        }
    }

    @Override
    public void backToMainActivity() {
        onBackPressed();
        finish();
    }

    @Override
    public void openCreatedActivity(long id) {
        Intent intent = QrActivity.getStartIntent(this);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", position);
    }
}

