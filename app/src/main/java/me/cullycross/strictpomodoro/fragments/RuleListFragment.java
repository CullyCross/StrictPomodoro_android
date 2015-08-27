package me.cullycross.strictpomodoro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.cullycross.strictpomodoro.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RuleListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RuleListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RuleListFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rule_list, container, false);
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

    public interface OnFragmentInteractionListener {

    }

}
