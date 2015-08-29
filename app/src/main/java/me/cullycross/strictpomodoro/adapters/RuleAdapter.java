package me.cullycross.strictpomodoro.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.cullycross.strictpomodoro.R;
import me.cullycross.strictpomodoro.content.Rule;

/**
 * Created by: cullycross
 * Date: 8/27/15
 * For my shining stars!
 * <p/>
 * todo(CullyCross): add footer with action +1
 */
public class RuleAdapter extends RecyclerView.Adapter<RuleAdapter.VHItem> {

    private List<Rule> mDataset;

    private final Context mContext;

    private OnItemClickListener mListener;

    private RadioButton mLastChecked = null;
    private int mLastCheckedPos = 0;

    public RuleAdapter setListener(OnItemClickListener listener) {
        mListener = listener;
        return this;
    }

    public RuleAdapter(List<Rule> dataset, Context ctx) {
        super();
        mContext = ctx;
        mDataset = dataset;
    }

    @Override
    public VHItem onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_rule_item, viewGroup, false);

        return new VHItem(v);
    }

    @Override
    public void onBindViewHolder(VHItem viewHolder, int position) {

        final Rule rule = mDataset.get(position);

        viewHolder.mTextView.setText(rule.getName());

        viewHolder.mRadioButton.setTag(position);

        if (rule.isRunning()) {
            viewHolder.mRadioButton.setChecked(rule.isRunning());
            mLastChecked = viewHolder.mRadioButton;
            mLastCheckedPos = position;
        }

        viewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(rule.getId());
                }
            }
        });

        viewHolder.mRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RadioButton radioButton = (RadioButton) v;
                final int clickedPos = (Integer) radioButton.getTag();

                if (radioButton.isChecked()) {
                    if (mLastChecked != null) {
                        mLastChecked.setChecked(false);
                        mDataset.get(mLastCheckedPos).setRunning(false);
                        mDataset.get(mLastCheckedPos).save();
                    }

                    mLastChecked = radioButton;
                    mLastCheckedPos = clickedPos;
                } else
                    mLastChecked = null;

                mDataset.get(clickedPos).setRunning(radioButton.isChecked());
                mDataset.get(clickedPos).save();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class VHItem extends RecyclerView.ViewHolder {

        @Bind(R.id.rule_title)
        TextView mTextView;
        @Bind(R.id.rule_radio_button)
        RadioButton mRadioButton;

        public VHItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(long id);
    }
}
