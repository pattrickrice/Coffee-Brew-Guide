package com.brewguide.android.coffeebrewguide;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FeedbackFragment extends Fragment{
    final String LOGTAG = this.getClass().getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feedback_layout, container, false);
        Context context = getContext();

        //Paypal donation link sends user to web browser.
        Button paypal = (Button) rootView.findViewById(R.id.link_paypal);
        paypal.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String url = "http://www.paypal.me/price104";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        //This line is required for the status bar color to stay consistent. Not sure why.
        getActivity().getWindow().setStatusBarColor(ContextCompat
                .getColor(context, R.color.colorPrimaryDark));
        return rootView;

    }


}
