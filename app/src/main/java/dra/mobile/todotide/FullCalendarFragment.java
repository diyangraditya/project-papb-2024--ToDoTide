package dra.mobile.todotide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FullCalendarFragment extends Fragment{
    private RecyclerView recyclerView;
    private EventAdapterCale eventAdapterCale;
    private List<Event> eventList;
    private Button addButton;
    private DatabaseReference eventsRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.full_fragment_calendar, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(40));
        addButton = view.findViewById(R.id.btAdd);

        eventsRef = FirebaseDatabase.getInstance().getReference("events");

        eventList = new ArrayList<>();
        eventAdapterCale = new EventAdapterCale(eventList, event -> deleteEvent(event));
        recyclerView.setAdapter(eventAdapterCale);

        eventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Event event = snapshot.getValue(Event.class);
                    if (event != null) {
                        eventList.add(event);
                    }
                }
                eventAdapterCale.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load events: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        addButton.setOnClickListener(v -> {
            AddEventDialog dialog = new AddEventDialog(getContext(), (title, date, location) -> {
                String key = eventsRef.push().getKey();
                if (key != null) {
                    Event newEvent = new Event(key, title, date, location);
                    eventsRef.child(key).setValue(newEvent).addOnSuccessListener(aVoid ->
                                    Toast.makeText(getContext(), "Event added!", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e ->
                                    Toast.makeText(getContext(), "Failed to add event: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            });
            dialog.show();
        });

        return view;
    }

    private void deleteEvent(Event event) {
        String key = event.getId();
        if (key != null) {
            eventsRef.child(key).removeValue().addOnSuccessListener(aVoid -> {
                Toast.makeText(getContext(), "Event deleted!", Toast.LENGTH_SHORT).show();
                eventList.remove(event);
                eventAdapterCale.notifyDataSetChanged();
            }).addOnFailureListener(e ->
                    Toast.makeText(getContext(), "Failed to delete event: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(getContext(), "Failed to delete event: Invalid key", Toast.LENGTH_SHORT).show();
        }
    }
}
