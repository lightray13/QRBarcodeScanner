package com.qrcodegenerator.creation.ui.main.created;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.qrcodegenerator.creation.R;
import com.qrcodegenerator.creation.ui.base.BaseViewHolder;
import com.qrcodegenerator.creation.ui.main.create.TypeItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TypeCreatedAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<TypeItem> list;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    static final private int TYPE_HEADER = 0;
    static final private int TYPE_CELL = 1;

    public TypeCreatedAdapter(List<TypeItem> list) {
        this.list = list;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_CELL;
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case TYPE_CELL:
                View view2 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.small_button_item, parent, false);
                return new com.qrcodegenerator.creation.ui.main.created.TypeCreatedAdapter.TypeCreatedHolder(view2);
            case TYPE_HEADER:
            default: {
                View view1 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.big_button_item_two, parent, false);
                return new com.qrcodegenerator.creation.ui.main.created.TypeCreatedAdapter.TypeHeaderHolder(view1);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItems(List<TypeItem> itemList) {
        list.addAll(itemList);
        notifyDataSetChanged();
    }

    class TypeHeaderHolder extends BaseViewHolder {

        @BindView(R.id.iv_icon)
        ImageView ivIcon;

        @BindView(R.id.tv_title)
        TextView tvTitle;

        public TypeHeaderHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            final TypeItem itemBig = (TypeItem) list.get(position);
            ivIcon.setImageResource(itemBig.getImage());
            tvTitle.setText(itemBig.getTitle());
        }

        @Override
        protected void clear() {

        }
    }

    class TypeCreatedHolder extends BaseViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_icon)
        ImageView ivIcon;

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.item)
        CardView item;

        private TypeCreatedHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onBind(int position) {
            final TypeItem itemBig = (TypeItem) list.get(position);
            ivIcon.setImageResource(itemBig.getImage());
            tvTitle.setText(itemBig.getTitle());
            item.setCardBackgroundColor(Color.parseColor(item.getContext().getResources().getString(itemBig.getColor())));
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onClick(View view) {
            mOnItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());
        }
    }
}

