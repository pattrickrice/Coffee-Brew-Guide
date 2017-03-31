package com.brewguide.android.coffeebrewguide;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static android.support.v7.widget.AppCompatDrawableManager.get;


/**
 * Created by Patrick on 3/25/2017.
 */

public class InstructionListAdapter extends RecyclerView.Adapter<InstructionListAdapter.ViewHolder> {
    final String LOGTAG = this.getClass().getSimpleName();


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView mIDTV;
        public TextView mContentTV;
        public ViewHolder(View v) {
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
        Log.v(LOGTAG, "InstructionsListAdapter:begin");

        mInstructions = instructions;
        mContext = context;
        Log.v(LOGTAG, "InstructionsListAdapter:finished" + mInstructions);
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @Override
    public InstructionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v(LOGTAG, "onCreateViewHolder");

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View instructionView = inflater.inflate(R.layout.instruction_step, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder =  new ViewHolder(instructionView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.v(LOGTAG, "onBindViewHolder");

        // Get the data model based on position
        String instruction = mInstructions.get(position);
        // Set item views based on your views and data model
        TextView textView = holder.mIDTV;
        textView.setText(Integer.toString(position + 1));
        Log.v(LOGTAG, "set text instruction in onBindViewHolder: " + instruction);
        TextView content = holder.mContentTV;
        content.setText(instruction);
    }

    @Override
    public int getItemCount() {
        return mInstructions.size();
    }
}
