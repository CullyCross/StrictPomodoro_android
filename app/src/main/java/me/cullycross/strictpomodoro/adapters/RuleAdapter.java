package me.cullycross.strictpomodoro.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
public class RuleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements OnRuleAddListener {

    private static final int ANIMATION_DURATION = 450;
    private List<Rule> mDataset;

    private final Context mContext;

    private OnItemClickListener mListener;

    private RadioButton mLastChecked = null;
    private int mLastCheckedPos = 0;

    private static final int TYPE_FOOTER = 0;
    private static final int TYPE_ITEM = 1;

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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v;
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recycler_view_rule_item, viewGroup, false);
            return new VHItem(v);
        } else if (viewType == TYPE_FOOTER) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recycler_view_rule_footer, viewGroup, false);
            return new VHFooter(v);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof VHItem) {
            final Rule rule = mDataset.get(position);

            final VHItem holder = (VHItem) viewHolder;

            holder.mTextView.setText(rule.getName());

            holder.mRadioButton.setTag(position);

            if (rule.isRunning()) {
                holder.mRadioButton.setChecked(rule.isRunning());
                mLastChecked = holder.mRadioButton;
                mLastCheckedPos = position;
            }

            holder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onItemClick(rule.getId());
                    }
                }
            });

            holder.mRadioButton.setOnClickListener(new View.OnClickListener() {
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
        } else if (viewHolder instanceof VHFooter) {

            final VHFooter holder = (VHFooter) viewHolder;

            holder.setListener(this);

        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionFooter(position)) {
            return TYPE_FOOTER;
        }

        return TYPE_ITEM;
    }

    private boolean isPositionFooter(int position) {
        return position == getItemCount() - 1;
    }

    @Override
    public void onRuleAdd(final VHFooter footer, Rule rule) {
        mDataset.add(rule);
        notifyItemInserted(mDataset.size() - 1);

        footer.mEditTextAddRule.setText("");

        final ObjectAnimator moverTextView
                = ObjectAnimator.ofFloat(
                footer.mTextViewAdd, "translationY", -30, 0);

        final ObjectAnimator alpherTextView
                = ObjectAnimator.ofFloat(
                footer.mTextViewAdd, "alpha", 0, 1);

        final AnimatorSet layoutSet = new AnimatorSet();
        layoutSet.playTogether(moverTextView, alpherTextView);
        layoutSet.setDuration(ANIMATION_DURATION);
        layoutSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                footer.mTextViewAdd.setVisibility(View.VISIBLE);
            }
        });

        final ObjectAnimator alpherLayout
                = ObjectAnimator.ofFloat(
                footer.mLinearLayout, "alpha", 1, 0);
        alpherLayout.setDuration(ANIMATION_DURATION);
        alpherLayout.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                footer.mLinearLayout.setVisibility(View.GONE);
                layoutSet.start();
            }
        });

        alpherLayout.start();
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

    public static class VHFooter extends RecyclerView.ViewHolder {

        @Bind(R.id.text_view_add)
        TextView mTextViewAdd;
        @Bind(R.id.edit_text_add_rule)
        EditText mEditTextAddRule;
        @Bind(R.id.button_add_rule)
        ImageButton mButtonAddRule;
        @Bind(R.id.layout)
        LinearLayout mLinearLayout;

        private OnRuleAddListener mListener;

        public VHFooter(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mButtonAddRule.setEnabled(false);

            mEditTextAddRule.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // ignored
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // ignored
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if(editable.toString().length() > 0) {
                        mButtonAddRule.setEnabled(true);
                    } else {
                        mButtonAddRule.setEnabled(false);
                    }
                }
            });
        }

        @OnClick(R.id.text_view_add)
        void showForm() {

            final ObjectAnimator moverLayout
                    = ObjectAnimator.ofFloat(
                    mLinearLayout, "translationY", -30, 0);

            final ObjectAnimator alpherLayout
                    = ObjectAnimator.ofFloat(
                    mLinearLayout, "alpha", 0, 1);

            final AnimatorSet layoutSet = new AnimatorSet();
            layoutSet.playTogether(moverLayout, alpherLayout);
            layoutSet.setDuration(ANIMATION_DURATION);
            layoutSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    mLinearLayout.setVisibility(View.VISIBLE);
                }
            });

            final ObjectAnimator alpherAddButton
                    = ObjectAnimator.ofFloat(
                    mTextViewAdd, "alpha", 1, 0);
            alpherAddButton.setDuration(ANIMATION_DURATION);
            alpherAddButton.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mTextViewAdd.setVisibility(View.GONE);
                    layoutSet.start();
                }
            });

            alpherAddButton.start();
        }

        @OnClick(R.id.button_add_rule)
        void addRule() {
            final Rule rule = new Rule().setName(mEditTextAddRule.getText().toString());
            rule.save();
            if (mListener != null) {
                mListener.onRuleAdd(this, rule);
            }
        }

        public void setListener(OnRuleAddListener listener) {
            mListener = listener;
        }


    }

    public interface OnItemClickListener {
        void onItemClick(long id);
    }
}

interface OnRuleAddListener {
    void onRuleAdd(RuleAdapter.VHFooter footer, Rule rule);
}
