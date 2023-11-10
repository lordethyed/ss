package com.example.sectionone.ui.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sectionone.R;
import com.example.sectionone.models.EventItem;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    Context context;

    public EventsAdapter(Context context, com.example.sectionone.ui.events.itemClick itemClick, List<EventItem> eventItemList) {
        this.context = context;
        this.itemClick = itemClick;
        this.eventItemList = eventItemList;
    }

    itemClick itemClick;

    List<EventItem> eventItemList;

    @NonNull
    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_tile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsAdapter.ViewHolder holder, int position) {
        holder.eventTitle.setText(eventItemList.get(position).getTitle());
        holder.eventDescription.setText(eventItemList.get(position).getDescription());
        holder.evenStatus.setText(eventItemList.get(position).isRead() ? "Read" : "Unread");
        String resName = eventItemList.get(position).getImages().get(0).split("\\.")[2];
        holder.eventImage.setImageResource(context.getResources().getIdentifier(resName, "drawable", context.getPackageName()));
    }

    @Override
    public int getItemCount() {
        return eventItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventTitle, eventDescription, evenStatus;
        ImageView eventImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.eventTitle);
            eventDescription = itemView.findViewById(R.id.eventDescription);
            evenStatus = itemView.findViewById(R.id.evenStatus);
            eventImage = itemView.findViewById(R.id.eventImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        itemClick.onItemClick(pos);
                    }
                }
            });
        }
    }
}
