package me.cullycross.strictpomodoro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Select;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.cullycross.strictpomodoro.R;
import me.cullycross.strictpomodoro.adapters.RuleAdapter;
import me.cullycross.strictpomodoro.content.Rule;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RuleListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RuleListFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * todo(CullyCross): add floatactionbutton(Run!)
 */
public class RuleListFragment extends Fragment
        implements RuleAdapter.OnItemClickListener{

    private OnFragmentInteractionListener mListener;

    private RuleAdapter mAdapter;

    @Bind(R.id.recycler_view_rules_list)
    protected RecyclerView mRecyclerView;

    public static RuleListFragment newInstance() {
        RuleListFragment fragment = new RuleListFragment();
        Bundle args = new Bundle();

        // set args here

        fragment.setArguments(args);
        return fragment;
    }

    public RuleListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // get args here
        }

        initAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recycler_view_layout, container, false);

        ButterKnife.bind(this, v);

        initRecyclerView();

        return v;
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

    private void initAdapter() {
        List<Rule> rules = new Select().from(Rule.class).execute();
        mAdapter = new RuleAdapter(rules, getActivity());

        mAdapter.setListener(this);
    }

    private void initRecyclerView() {

        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(long id) {
        if (mListener != null) {
            mListener.onItemClick(id);
        }
    }

    public interface OnFragmentInteractionListener {
        void onItemClick(long id);
    }

}
