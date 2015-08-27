package me.cullycross.strictpomodoro.adapters;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.cullycross.strictpomodoro.content.Rule;

/**
 * Created by: cullycross
 * Date: 8/27/15
 * For my shining stars!
 */
public class RuleAdapter extends RecyclerView.Adapter<RuleAdapter.VHItem> {

    private List<Rule> mDataset;

    private Context mContext;

    public RuleAdapter(List<Rule> dataset, Context ctx) {
        super();
        mContext = ctx;
        mDataset = dataset;
    }

    @Override
    public VHItem onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(android.R.layout.simple_list_item_1, viewGroup, false);

        return new VHItem(v);

    }

    @Override
    public void onBindViewHolder(VHItem viewHolder, int position) {

        viewHolder.mTextView.setText(mDataset.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class VHItem extends RecyclerView.ViewHolder {

        @Bind(android.R.id.text1)
        public TextView mTextView;

        public VHItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
