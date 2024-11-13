package dra.mobile.todotide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment {
    private RecyclerView recyclerView;
    private EventAdapterCale eventAdapterCale;
    private List<EventCalendar.Event> eventList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(40));
        eventList = new ArrayList<>();

        // Load data in a separate thread
        new Thread(() -> {
            eventList.add(new EventCalendar.Event("Kerja Kelompok PAM", "1 Okt 2024 16.00", "Gedung F FILKOM Universitas Brawijaya"));
            eventList.add(new EventCalendar.Event("Rapat kepanitiaan", "2 Okt 2024 18.00", "Ada Apa Dengan Kopi (AADK) Malang"));
            eventList.add(new EventCalendar.Event("Jogging", "5 Okt 2024 07.00", "Lapangan Rampal"));
            eventList.add(new EventCalendar.Event("Mancing", "6 Okt 2024 16.00", "Perpustakaan UB"));
            eventList.add(new EventCalendar.Event("Live nobar timnas", "7 Okt 2024 08.00", "Stadion Gajayana Malang"));

            getActivity().runOnUiThread(() -> {
                eventAdapterCale = new EventAdapterCale(eventList);
                recyclerView.setAdapter(eventAdapterCale);
            });
        }).start();

        return view;
    }
}
