package com.qrcodegenerator.creation.ui.main.created;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.qrcodegenerator.creation.R;
import com.qrcodegenerator.creation.di.component.ActivityComponent;
import com.qrcodegenerator.creation.ui.base.BaseFragment;
import com.qrcodegenerator.creation.ui.creation.CreationActivity;
import com.qrcodegenerator.creation.ui.main.create.TypeItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CreatedFragment extends BaseFragment implements CreatedMvpView, AdapterView.OnItemClickListener {

    @Inject
    CreatedMvpPresenter<CreatedMvpView> mPresenter;

    @Inject
    TypeCreatedAdapter mAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public static com.qrcodegenerator.creation.ui.main.created.CreatedFragment newInstance() {
        return new com.qrcodegenerator.creation.ui.main.created.CreatedFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_created, container, false);

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
    protected void setUp(View view) {
        List<TypeItem> items = new ArrayList<>();
        TypedArray images = getResources().obtainTypedArray(R.array.create_button_images);
        TypedArray titles = getResources().obtainTypedArray(R.array.create_button_titles);
        TypedArray colors = getResources().obtainTypedArray(R.array.create_button_colors);
        for (int i = 0; i < images.length(); i++) {
            items.add(new TypeItem(images.getResourceId(i, R.drawable.qrcode), titles.getResourceId(i, R.string.option_generate),
                    colors.getResourceId(i, R.color.optionColorOne)));
        }
        images.recycle();
        titles.recycle();
        colors.recycle();

        if (items.size() > 0) {
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setHasFixedSize(true);
            mAdapter.addItems(items);
            recyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = CreationActivity.getStartIntent(getBaseActivity());
        intent.putExtra("position", position);
        startActivity(intent);
    }
}