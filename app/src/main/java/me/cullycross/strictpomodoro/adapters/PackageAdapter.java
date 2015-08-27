package me.cullycross.strictpomodoro.adapters;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

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

    private List<ApplicationInfo> mDataset;
    private List<String> mSelected;

    private final PackageHelper mPackageHelper;

    public PackageAdapter(Context ctx, List<ApplicationInfo> dataset, List<String> selected) {
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
    public void onBindViewHolder(VHItem viewHolder, int position) {
        final ApplicationInfo info = mDataset.get(position);

        viewHolder.mAppName.setText(info.name);
        viewHolder.mPackageName.setText(info.packageName);
        viewHolder.mIcon.setImageDrawable(
                mPackageHelper.getApplicationIcon(info)
        );
        viewHolder.mSelected.setChecked(
                mSelected.contains(info.packageName)
        );
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
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
}
