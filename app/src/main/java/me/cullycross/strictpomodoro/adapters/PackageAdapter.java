package me.cullycross.strictpomodoro.adapters;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import me.cullycross.strictpomodoro.R;
import me.cullycross.strictpomodoro.utils.PackageHelper;

/**
 * Created by: cullycross
 * Date: 8/27/15
 * For my shining stars!
 */
public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.VHItem> {

    private static final String TAG = PackageAdapter.class.getCanonicalName();
    private List<ApplicationInfo> mDataset;
    private Set<String> mSelected;

    private final PackageHelper mPackageHelper;

    private OnCheckBoxChanged mListener;

    public PackageAdapter(Context ctx, List<ApplicationInfo> dataset, Set<String> selected) {
        super();
        mPackageHelper = new PackageHelper(ctx);
        mDataset = dataset;
        mSelected = selected;
    }

    @Override
    public VHItem onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_package_item, viewGroup, false);

        return new VHItem(v);

    }

    @Override
    public void onBindViewHolder(VHItem viewHolder, final int position) {
        final ApplicationInfo info = mDataset.get(position);

        viewHolder.mAppName.setText(info.loadLabel(mPackageHelper.getPackageManager()));
        viewHolder.mPackageName.setText(info.packageName);
        viewHolder.mIcon.setImageDrawable(
                mPackageHelper.getApplicationIcon(info)
        );

        viewHolder.mSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                if (mListener != null) {
                    mListener.onCheckedChange(b, info.packageName);
                }
            }
        });

        viewHolder.mSelected.setChecked(
                mSelected.contains(info.packageName)
        );
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public PackageAdapter setListener(OnCheckBoxChanged listener) {
        mListener = listener;
        return this;
    }

    public static class VHItem extends RecyclerView.ViewHolder {

        @Bind(R.id.icon)
        CircleImageView mIcon;
        @Bind(R.id.text1)
        TextView mAppName;
        @Bind(R.id.text2)
        TextView mPackageName;
        @Bind(R.id.checkbox_selected)
        CheckBox mSelected;

        public VHItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnCheckBoxChanged {
        void onCheckedChange(boolean flag, String packageName);
    }
}
