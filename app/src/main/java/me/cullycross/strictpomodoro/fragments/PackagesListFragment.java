package me.cullycross.strictpomodoro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.cullycross.strictpomodoro.R;
import me.cullycross.strictpomodoro.adapters.PackageAdapter;
import me.cullycross.strictpomodoro.content.Rule;
import me.cullycross.strictpomodoro.utils.PackageHelper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PackagesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PackagesListFragment extends Fragment {

    private static final String RULE_ID = "RULE_ID";

    @Bind(R.id.recycler_view_rules_list)
    protected RecyclerView mRecyclerView;

    private OnFragmentInteractionListener mListener;
    private PackageAdapter mAdapter;
    private PackageHelper mPackageHelper;

    public static PackagesListFragment newInstance(int ruleId) {
        PackagesListFragment fragment = new PackagesListFragment();
        Bundle args = new Bundle();

        args.putInt(RULE_ID, ruleId);

        fragment.setArguments(args);
        return fragment;
    }

    public PackagesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int id = getArguments().getInt(RULE_ID);

            initAdapter(id);
        }
    }

    private void initAdapter(int id) {
        mPackageHelper = new PackageHelper(getActivity());
        Rule rule = Rule.load(Rule.class, id);
        mAdapter = new PackageAdapter(
                getActivity(),
                mPackageHelper.getInstalledPackages(),
                rule.getPackages()
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler_view_layout, container, false);
        ButterKnife.bind(this, view);

        initRecyclerView();

        return view;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initRecyclerView() {

        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);

        mRecyclerView.setAdapter(mAdapter);
    }

    public interface OnFragmentInteractionListener {

    }

}
