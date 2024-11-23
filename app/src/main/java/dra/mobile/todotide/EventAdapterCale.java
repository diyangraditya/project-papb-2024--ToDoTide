package dra.mobile.todotide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapterCale extends RecyclerView.Adapter<EventAdapterCale.EventViewHolder>{
    private List<Event> eventList;
    private DeleteEventListener deleteListener;

    public interface DeleteEventListener {
        void onDeleteEvent(Event event);
    }

    public EventAdapterCale(List<Event> eventList, DeleteEventListener deleteListener) {
        this.eventList = eventList;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.eventTitle.setText(event.getTitle());
        holder.eventDate.setText(event.getDate());
        holder.eventLocation.setText(event.getLocation());

        holder.deleteButton.setOnClickListener(v -> deleteListener.onDeleteEvent(event));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void updateEvents(List<Event> newEventList) {
        eventList = newEventList;
        notifyDataSetChanged();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView eventTitle, eventDate, eventLocation;
        Button deleteButton;

        public EventViewHolder(View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.eventTitle);
            eventDate = itemView.findViewById(R.id.eventDate);
            eventLocation = itemView.findViewById(R.id.eventLocation);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
