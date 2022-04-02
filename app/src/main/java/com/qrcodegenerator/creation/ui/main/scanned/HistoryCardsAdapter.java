package com.qrcodegenerator.creation.ui.main.scanned;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qrcodegenerator.creation.R;
import com.qrcodegenerator.creation.data.db.model.History;
import com.qrcodegenerator.creation.ui.base.BaseViewHolder;
import com.qrcodegenerator.creation.utils.AppConstants;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryCardsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<History> list;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    static final private int TYPE_HEADER = 0;
    static final private int TYPE_CELL = 1;

    public HistoryCardsAdapter(List<History> list) {
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

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_CELL:
                return new HistoryCardsViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.history_card_button, parent, false));
            case TYPE_HEADER:
            default:
                return new TypeHeaderHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.big_button_item_two, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
//        if (list != null && list.size() > 0) {
//            return list.size() + 1;
//        } else {
//            return 1;
//        }
//        return list.size() + 1;
        return list.size() + 1;
    }

    public void addItems(List<History> itemList) {
        list.addAll(itemList);
        notifyDataSetChanged();
    }

    public void updateItems(List<History> itemList) {
        list.clear();
        list.addAll(itemList);
        notifyDataSetChanged();
    }

    public History getHistory(int position) {
//        Toast.makeText(, "" + position, Toast.LENGTH_SHORT).show();
        return list.get(position);
    }

    public void removeItem(int position)
    {
        list.remove(position);
        notifyItemRemoved(position);
    }

//    public int getButtonTitle(int position) {
//        return list.get(position).getTitle();
//    }

    class HistoryCardsViewHolder extends BaseViewHolder implements View.OnClickListener{

        @BindView(R.id.tv_data)
        TextView tvData;

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_text)
        TextView tvText;

        @BindView(R.id.iv_icon)
        ImageView ivIcon;

        @BindView(R.id.item)
        CardView item;

        @BindView(R.id.rl)
        RelativeLayout rl;

        public HistoryCardsViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onBind(int position) {
            final History history = (History) list.get(position - 1);
            tvData.setText(history.date);
            tvTitle.setText(history.type);
            tvText.setText(history.text);
            switch (history.type) {
                case AppConstants.TEXT:
                    tvTitle.setText(R.string.text);
                    ivIcon.setImageResource(R.drawable.text);
                    rl.setBackgroundResource(R.color.colorHeaderOne);
//                    item.setCardBackgroundColor(item.getContext().getResources().getColor(R.color.colorHeaderOne));
                    break;
                case AppConstants.URI:
                    tvTitle.setText(R.string.website);
                    ivIcon.setImageResource(R.drawable.website);
                    rl.setBackgroundResource(R.color.colorHeaderSix);
                    break;
                case AppConstants.TEL:
                    tvTitle.setText(R.string.telephone_two);
                    ivIcon.setImageResource(R.drawable.phone);
                    rl.setBackgroundResource(R.color.colorHeaderSeven);
                    break;
                case AppConstants.ADDRESSBOOK:
                    tvTitle.setText(R.string.contacts);
                    ivIcon.setImageResource(R.drawable.contacts);
                    rl.setBackgroundResource(R.color.colorHeaderTwo);
                    break;
                case AppConstants.EMAIL_ADDRESS:
                    tvTitle.setText(R.string.email);
                    ivIcon.setImageResource(R.drawable.email);
                    rl.setBackgroundResource(R.color.colorHeaderEight);
                    break;
                case AppConstants.SMS:
                    tvTitle.setText(R.string.message);
                    ivIcon.setImageResource(R.drawable.message);
                    rl.setBackgroundResource(R.color.colorHeaderThree);
                    break;
                case AppConstants.GEO:
                    tvTitle.setText(R.string.location);
                    ivIcon.setImageResource(R.drawable.location);
                    rl.setBackgroundResource(R.color.colorHeaderFive);
                    break;
                case AppConstants.WIFI:
                    tvTitle.setText(R.string.wifi);
                    ivIcon.setImageResource(R.drawable.wifi);
                    rl.setBackgroundResource(R.color.colorHeaderNine);
                    break;
                case AppConstants.PRODUCT:
                    tvTitle.setText(R.string.product);
                    ivIcon.setImageResource(R.drawable.product);
                    rl.setBackgroundResource(R.color.optionColorOne);
                    break;
                case AppConstants.CALENDAR:
                    tvTitle.setText(R.string.event);
                    ivIcon.setImageResource(R.drawable.calendar);
                    rl.setBackgroundResource(R.color.optionColorOne);
                    break;
                case AppConstants.ISBN:
                    tvTitle.setText(R.string.isbn);
                    ivIcon.setImageResource(R.drawable.ic_book);
                    rl.setBackgroundResource(R.color.optionColorOne);
                    break;
            }

//            tv.setText(typeItem.getTitle());
//            iv.setImageResource(typeItem.getImage());
//            item.setCardBackgroundColor(Color.parseColor(item.getContext().getResources().getString(typeItem.getColor())));
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onClick(View view) {
            mOnItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());
        }

//        public void removeItem(int position)
//        {
//            list.remove(position);
//            notifyItemRemoved(position);
//        }
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
//            final TypeItem itemBig = (TypeItem) list.get(position);
            if (list.size() > 0) {
                ivIcon.setImageResource(R.drawable.scanned_qr_code);
                tvTitle.setText(R.string.scanned_qr_code_title);
            } else {
                ivIcon.setImageResource(R.drawable.note_outline);
                tvTitle.setText(R.string.list_empty_title);
            }
        }

        @Override
        protected void clear() {

        }
    }
}
