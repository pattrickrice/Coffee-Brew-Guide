package com.brewguide.android.coffeebrewguide;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter to handle the instructions of how to use the brewmethod.
 * The adapter populates a RecyclerView with each step as a view
 */

public class InstructionListAdapter extends RecyclerView.Adapter<InstructionListAdapter.ViewHolder> {
    // name of activity
    final String LOGTAG = this.getClass().getSimpleName();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        TextView mIDTV;
        TextView mContentTV;
        ViewHolder(View v) {
            super(v);

            mIDTV = (TextView) itemView.findViewById(R.id.id);
            mContentTV = (TextView) itemView.findViewById(R.id.content);
        }
    }

    // Store a member variable for the contacts
    private List<String> mInstructions;

    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public InstructionListAdapter(Context context, List<String> instructions) {

        mInstructions = instructions;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @Override
    public InstructionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View instructionView = inflater.inflate(R.layout.instruction_step, parent, false);

        // Return a new holder instance
        return new ViewHolder(instructionView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // Get the data model based on position
        String instruction = mInstructions.get(position);
        // Set item views based on your views and data model
        TextView textView = holder.mIDTV;
        textView.setText("Step " + Integer.toString(position + 1) + ".");
        TextView content = holder.mContentTV;
        content.setText(instruction);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mInstructions.size();
    }

    // changes the dataset in the adapter
    void swap(ArrayList<String> newInstructions){
        Log.v("called","this function");

        mInstructions.clear();
        mInstructions.addAll(newInstructions);

        for (int j = 0; j < newInstructions.size(); j++) {
            mInstructions.add(newInstructions.get(j));
            Log.v("mInstructions #" + j, "is: " + mInstructions.get(j));
        }
        notifyDataSetChanged();
    }
}
