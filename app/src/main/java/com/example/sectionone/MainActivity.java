package com.example.sectionone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sectionone.ui.events.EventDetails;
import com.example.sectionone.ui.events.EventsList;

public class MainActivity extends AppCompatActivity {

    TextView btn_Events;
    TextView btn_Tickets;
    TextView btn_Records;

    FrameLayout content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        content = findViewById(R.id.view_content);

        btn_Events = findViewById(R.id.btn_Events);
        btn_Tickets = findViewById(R.id.btn_Tickets);
        btn_Records = findViewById(R.id.btn_Records);

        btn_Events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_Events.setTextColor(getColor(R.color.red));
                btn_Tickets.setTextColor(getColor(R.color.black));
                btn_Records.setTextColor(getColor(R.color.black));
                switchFragment(new EventsList());
            }
        });

        btn_Tickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_Events.setTextColor(getColor(R.color.black));
                btn_Tickets.setTextColor(getColor(R.color.red));
                btn_Records.setTextColor(getColor(R.color.black));
            }
        });

        btn_Records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_Events.setTextColor(getColor(R.color.black));
                btn_Tickets.setTextColor(getColor(R.color.black));
                btn_Records.setTextColor(getColor(R.color.red));
            }
        });


        Intent intent = getIntent();

        String fragmentName = intent.getStringExtra("fragmentName");
        if(fragmentName != null){
            switch (fragmentName) {
                case "eventDetails":
                    switchFragment(new EventDetails());
                    btn_Events.setTextColor(getColor(R.color.red));
                    btn_Tickets.setTextColor(getColor(R.color.black));
                    btn_Tickets.setTextColor(getColor(R.color.black));
                    btn_Records.setTextColor(getColor(R.color.black));

                    break;
            }
        }
    }

    private void switchFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.view_content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}


