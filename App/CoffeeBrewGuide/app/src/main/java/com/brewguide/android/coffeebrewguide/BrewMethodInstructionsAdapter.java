package com.brewguide.android.coffeebrewguide;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.brewguide.android.coffeebrewguide.InstructionsFragment.OnListFragmentInteractionListener;

import com.brewguide.android.coffeebrewguide.dummy.DummyContent.DummyItem;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class BrewMethodInstructionsAdapter extends RecyclerView.Adapter<BrewMethodInstructionsAdapter.ViewHolder> {

    private final ArrayList<String> mValues;
    private final OnListFragmentInteractionListener mListener;
    final String LOGTAG = this.getClass().getSimpleName();


    public BrewMethodInstructionsAdapter(ArrayList<String> instructions, OnListFragmentInteractionListener listener) {
        mValues = instructions;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_instructions_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.stepNumber.setText(position);
        holder.instructions.setText(mValues.get(position));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView stepNumber;
        public final TextView instructions;
        public String mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            stepNumber = (TextView) view.findViewById(R.id.id);
            instructions = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + instructions.getText() + "'";
        }
    }
}
