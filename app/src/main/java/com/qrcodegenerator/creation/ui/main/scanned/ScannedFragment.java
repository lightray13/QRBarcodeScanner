package com.qrcodegenerator.creation.ui.main.scanned;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.qrcodegenerator.creation.R;
import com.qrcodegenerator.creation.data.db.model.History;
import com.qrcodegenerator.creation.di.component.ActivityComponent;
import com.qrcodegenerator.creation.ui.base.BaseFragment;
import com.qrcodegenerator.creation.ui.scanning.ScanningActivity;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;

import java.util.List;

import javax.inject.Inject;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ScannedFragment extends BaseFragment implements ScannedMvpView, AdapterView.OnItemClickListener {

    @Inject
    ScannedMvpPresenter<ScannedMvpView> mPresenter;

    @Inject
    HistoryCardsAdapter mAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private PowerMenu powerMenu;

    public static ScannedFragment newInstance() {
        return new ScannedFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scanned, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            mAdapter.setOnItemClickListener(this);
        }

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showChooseDialog(view, position);
    }

    @Override
    protected void setUp(View view) {
        mPresenter.onViewInitialized();
        mPresenter.getScannedEvent();
    }

    @Override
    public void refreshHistoryCards(List<History> histories) {
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter.addItems(histories);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void reloadHistoryCards(List<History> histories) {
        mAdapter.updateItems(histories);
    }

    @Override
    public void showLoadingDialog() {
        showLoading();
    }

    @Override
    public void hideLoadingDialog() {
        hideLoading();
    }

    @Override
    public void showChooseDialog(View view, final int position) {
//        Toast.makeText(getBaseActivity(), "" + position, Toast.LENGTH_SHORT).show();
        powerMenu = new PowerMenu.Builder(getBaseActivity())
                .addItem(new PowerMenuItem(getResources().getString(R.string.open), false))
                .addItem(new PowerMenuItem(getResources().getString(R.string.delete), false))
                .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT)
                .setMenuRadius(10f)
                .setMenuShadow(10f)
                .setTextColor(ContextCompat.getColor(getActivity(), R.color.colorHeaderTwo))
                .setTextGravity(Gravity.CENTER)
                .setTextTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/MuliRegular.ttf"))
                .setSelectedTextColor(Color.WHITE)
                .setTextSize(16)
                .setMenuColor(Color.WHITE)
                .setSelectedMenuColor(ContextCompat.getColor(getActivity(), R.color.colorHeaderFour))
                .setOnMenuItemClickListener(new OnMenuItemClickListener<PowerMenuItem>() {
                    @Override
                    public void onItemClick(int pos, PowerMenuItem item) {
                        if (mAdapter != null) {
                            switch (pos) {
                                case 0:
                                    Intent intent = ScanningActivity.getStartIntent(getContext());
                                    intent.putExtra("id", mAdapter.getHistory(position).id);
                                    startActivity(intent);
                                    break;
                                case 1:
                                    mPresenter.deleteHistoryCard(mAdapter.getHistory(position));
                                    mAdapter.removeItem(position);
                                    break;
                            }
                            powerMenu.setSelectedPosition(pos); // change selected item
                            powerMenu.dismiss();
                        }
                    }
                })
                .build();
        powerMenu.showAsAnchorCenter(view);
    }

    @Override
    public void openHistoryCardActivity() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}