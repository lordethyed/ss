package com.example.sectionone.ui.events;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sectionone.MainActivity;
import com.example.sectionone.R;
import com.example.sectionone.dbUtils.DbUtils;
import com.example.sectionone.models.EventItem;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class EventsList extends Fragment  implements itemClick{

    TextView btnAll;
    TextView btnUnread;
    TextView btnRead;
    RecyclerView listEvents;
    DbUtils dbUtils;

    List<EventItem> eventItemList = new ArrayList<>();
    List<EventItem> eventItemAll = new ArrayList<>();
    List<EventItem> eventItemUnread = new ArrayList<>();
    List<EventItem> eventItemRead = new ArrayList<>();

    EventsAdapter eventsAdapter;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_events_list, container, false);

        dbUtils = new DbUtils(getContext());

        btnAll = view.findViewById(R.id.btnAll);
        btnUnread = view.findViewById(R.id.btnUnread);
        btnRead = view.findViewById(R.id.btnRead);
        listEvents = view.findViewById(R.id.listEvents);


        try{
            eventItemList = dbUtils.getEvents();
            eventItemList.forEach((element) -> {
                eventItemAll.add(element);
                if(element.isRead()){
                    eventItemRead.add(element);
                } else{
                    eventItemUnread.add(element);
                }
                changeAdapter(eventItemAll);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAdapter(eventItemAll);
            }
        });

        btnUnread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAdapter(eventItemUnread);
            }
        });


        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAdapter(eventItemRead);
            }
        });



        return view;
    }

    private void changeAdapter(List<EventItem> newList){
        eventsAdapter = new EventsAdapter(getContext(), this::onItemClick, newList);
        listEvents.setLayoutManager(new LinearLayoutManager(getContext()));
        listEvents.setAdapter(eventsAdapter);
    }

    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("fragmentName", "eventDetails");
        intent.putExtra("event", eventItemAll.get(pos));
        startActivity(intent);
    }
}