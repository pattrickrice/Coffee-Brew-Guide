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
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class FeedbackFragment extends Fragment {
    final String LOGTAG = this.getClass().getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feedback_layout, container, false);
        Context context = getContext();

        final EditText nameET = (EditText) rootView.findViewById(R.id.input_name);
        final EditText subjectET = (EditText) rootView.findViewById(R.id.input_subject);
        final EditText bodyET = (EditText) rootView.findViewById(R.id.input_body);

        Button sendEmail = (Button) rootView.findViewById(R.id.button_send_email);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = nameET.getText().toString();
                final String subject = subjectET.getText().toString();
                final String body = bodyET.getText().toString();
                String finalSubject;

                if (Objects.equals(subject, "")) {
                    finalSubject = "Application Feedback";
                } else {
                    finalSubject = subject;
                }
                if (!Objects.equals(name, "")) {
                    finalSubject = finalSubject + " from " + name;
                    if (body.length() > 15) {
                        //let's do this
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("message/rfc822");
                        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"outtersunsetlabs@gmail.com"});
                        i.putExtra(Intent.EXTRA_SUBJECT, finalSubject);
                        i.putExtra(Intent.EXTRA_TEXT, body);
                        try {
                            startActivity(Intent.createChooser(i, "Send mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        // body is empty
                        bodyET.setError("A minimum of 15 characters is required.");
                    }
                } else {
                    //name is empty
                    nameET.setError("Name is required");
                }

            }
        });

        //Paypal donation link sends user to web browser.
        Button paypal = (Button) rootView.findViewById(R.id.link_paypal);
        paypal.setOnClickListener(new View.OnClickListener()

        {

            @Override
            public void onClick(View v) {
                String url = "http://www.paypal.me/price104";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        //This line is required for the status bar color to stay consistent. Not sure why.
        getActivity().

                getWindow().

                setStatusBarColor(ContextCompat
                        .getColor(context, R.color.colorPrimaryDark));
        return rootView;

    }

}


