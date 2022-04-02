package com.qrcodegenerator.creation.ui.scanning;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qrcodegenerator.creation.R;
import com.qrcodegenerator.creation.ui.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<ScanResult> list;

    public ResultAdapter(List<ScanResult> list) {
        this.list = list;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ResultViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item, parent, false);
        vh = new ResultViewHolder(v);
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

    public void addItems(List<ScanResult> itemList) {
        list.addAll(itemList);
        notifyDataSetChanged();
    }

    class ResultViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_content)
        TextView tvContent;

        public ResultViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            final ScanResult scanResult = (ScanResult) list.get(position);
            tvTitle.setText(scanResult.getTitle());
            tvContent.setText(scanResult.getContent());
        }

        @Override
        protected void clear() {

        }
    }
}
