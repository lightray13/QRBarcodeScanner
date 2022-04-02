package com.qrcodegenerator.creation.ui.scanning;

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

public class ButtonsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<TypeItem> list;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    public ButtonsAdapter(List<TypeItem> list) {
        this.list = list;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ButtonsViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_item, parent, false);
        vh = new ButtonsViewHolder(v);
        return vh;
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

    public int getButtonTitle(int position) {
        return list.get(position).getTitle();
    }

    class ButtonsViewHolder extends BaseViewHolder implements View.OnClickListener{

        @BindView(R.id.tv)
        TextView tv;

        @BindView(R.id.iv)
        ImageView iv;

        @BindView(R.id.item)
        CardView item;

        public ButtonsViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onBind(int position) {
            final TypeItem typeItem = (TypeItem) list.get(position);
            tv.setText(typeItem.getTitle());
            iv.setImageResource(typeItem.getImage());
            item.setCardBackgroundColor(Color.parseColor(item.getContext().getResources().getString(typeItem.getColor())));
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
