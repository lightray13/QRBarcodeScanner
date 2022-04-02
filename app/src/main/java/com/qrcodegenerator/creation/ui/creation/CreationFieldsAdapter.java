package com.qrcodegenerator.creation.ui.creation;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qrcodegenerator.creation.R;
import com.qrcodegenerator.creation.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CreationFieldsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<String> list;
    private List<String> texts;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    static final private int TYPE_ET = 0;
    static final private int TYPE_CB = 1;
    static final private int TYPE_RB = 2;

    public CreationFieldsAdapter(List<String> list) {
        this.list = list;
        texts = new ArrayList<>();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        String typeItem = (String) list.get(position);
        if (typeItem.equals("Network encryption:")) {
            return TYPE_RB;
        } else if (typeItem.equals("Hidden:")) {
            return TYPE_CB;
        } else {
            return TYPE_ET;
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ET:
            default:
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.et_item, parent, false);
                return new CreationFieldsViewHolder(v);
            case TYPE_RB:
                View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.rb_item, parent, false);
                return new RadioButtonsHolder(v1);
            case TYPE_CB:
                View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.cb_item, parent, false);
                return new CheckBoxHolder(v2);
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

    public List<String> getEtData() {
        return texts;
    }

    public void addItems(List<String> itemList) {
        list.addAll(itemList);
        notifyDataSetChanged();
    }

    class CreationFieldsViewHolder extends BaseViewHolder implements View.OnClickListener{

        @BindView(R.id.tv)
        TextView tv;

        @BindView(R.id.et)
        EditText et;

        public CreationFieldsViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onBind(final int position) {
            final String typeItem = (String) list.get(position);
            tv.setText(typeItem);
            texts.add(position, "");
            if (typeItem.equals(et.getContext().getResources().getString(R.string.field_phone_number_one))) {
                et.setInputType(InputType.TYPE_CLASS_PHONE);
            }
            if ( typeItem.equals(et.getContext().getResources().getString(R.string.field_latitude)) || typeItem.equals(et.getContext().getResources().getString(R.string.field_longitude ))) {
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
            }
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    texts.set(position, s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onClick(View view) {
            mOnItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());
        }
    }

    class CheckBoxHolder extends BaseViewHolder {

        @BindView(R.id.tv)
        TextView tv;

        @BindView(R.id.cb)
        CheckBox cb;

        public CheckBoxHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            final String typeItem = (String) list.get(position);
            texts.add(position, "");
            tv.setText(typeItem);
            if (cb.isChecked()) {
                texts.set(position, "Yes");
            } else {
                texts.set(position, "No");
            }
        }

        @Override
        protected void clear() {

        }
    }

    class RadioButtonsHolder extends BaseViewHolder {

        @BindView(R.id.tv)
        TextView tv;

        @BindView(R.id.radio_group)
        RadioGroup radioGroup;

        public RadioButtonsHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(final int position) {
            final String typeItem = (String) list.get(position);
            tv.setText(typeItem);
            texts.add(position, "");
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case -1:
                            texts.set(position, "");
                            break;
                        case R.id.rb_one:
                            texts.set(position, "WPA");
                            break;
                        case R.id.rb_two:
                            texts.set(position, "WEP");
                            break;
                        case R.id.rb_three:
                            texts.set(position, "");
                            break;
                        default:
                            texts.set(position, "");
                            break;
                    }
                }
            });
        }

        @Override
        protected void clear() {

        }
    }
}

