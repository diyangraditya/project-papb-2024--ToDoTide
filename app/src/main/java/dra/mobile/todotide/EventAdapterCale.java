package dra.mobile.todotide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapterCale extends RecyclerView.Adapter<EventAdapterCale.EventViewHolder>{
    private List<EventCalendar.Event> eventList;

    public EventAdapterCale (List<EventCalendar.Event> eventList){
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        EventCalendar.Event event  = eventList.get(position);
        holder.eventTitle.setText(event.getTitle());
        holder.eventDate.setText(event.getDate());
        holder.eventLocation.setText(event.getLocation());

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }


    public static class EventViewHolder extends RecyclerView.ViewHolder{
        TextView eventTitle, eventDate, eventLocation;

        public EventViewHolder(View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.eventTitle);
            eventDate = itemView.findViewById(R.id.eventDate);
            eventLocation = itemView.findViewById(R.id.eventLocation);
        }
    }

}
