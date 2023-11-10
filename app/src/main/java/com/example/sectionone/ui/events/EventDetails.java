package com.example.sectionone.ui.events;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sectionone.R;
import com.example.sectionone.models.EventItem;

import java.util.ArrayList;
import java.util.List;

public class EventDetails extends Fragment {
    View view;
    ImageView eventDetailsImage1,eventDetailsImage2,eventDetailsImage3;
    TextView eventDetailsTitle, eventDetailsViewCounts, eventDetailsDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event_details, container, false);

        Intent intent = getActivity().getIntent();
        EventItem eventItem = (EventItem) intent.getSerializableExtra("event");


        eventDetailsTitle = view.findViewById(R.id.eventDetailsTitle);
        eventDetailsViewCounts = view.findViewById(R.id.eventDetailsViewCounts);
        eventDetailsDescription = view.findViewById(R.id.eventDetailsDescription);
        eventDetailsImage1 = view.findViewById(R.id.eventDetailsImage1);
        eventDetailsImage2 = view.findViewById(R.id.eventDetailsImage2);
        eventDetailsImage3 = view.findViewById(R.id.eventDetailsImage3);

        String viewCounts = Integer.toString(eventItem.getViewCounts() + 1);

        List<String> images = new ArrayList<>();

        eventItem.getImages().forEach((e) -> {
            images.add(e.split("\\.")[2]);
        });


        eventDetailsTitle.setText(eventItem.getTitle());
        eventDetailsViewCounts.setText(viewCounts);
        eventDetailsDescription.setText(eventItem.getDescription());
        eventDetailsImage1.setImageResource(getActivity().getResources().getIdentifier(images.get(0), "drawable", getActivity().getPackageName()));
        eventDetailsImage2.setImageResource(getActivity().getResources().getIdentifier(images.get(1), "drawable", getActivity().getPackageName()));
        eventDetailsImage3.setImageResource(getActivity().getResources().getIdentifier(images.get(2), "drawable", getActivity().getPackageName()));


        return view;
    }
}