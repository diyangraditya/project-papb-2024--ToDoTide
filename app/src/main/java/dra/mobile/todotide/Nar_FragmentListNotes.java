package dra.mobile.todotide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Nar_FragmentListNotes extends Fragment {

    private ListView listViewNotes;
    private ArrayList<String> noteList;
    private ArrayAdapter<String> noteAdapter;
    private DatabaseReference notesRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nar_fragment_task_notes, container, false);

        listViewNotes = view.findViewById(R.id.listViewNotes);
        noteList = new ArrayList<>();
        noteAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, noteList);
        listViewNotes.setAdapter(noteAdapter);

        notesRef = FirebaseDatabase.getInstance().getReference("notes");

        loadNotesFromFirebase();

        return view;
    }

    private void loadNotesFromFirebase() {
        notesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                noteList.clear();
                for (DataSnapshot noteSnapshot : snapshot.getChildren()) {
                    String note = noteSnapshot.getValue(String.class);
                    if (note != null) {
                        noteList.add(note);
                    }
                }
                noteAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Failed to load notes: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addNewNote(String noteContent) {
        if (notesRef != null && !noteContent.isEmpty()) {
            String noteId = notesRef.push().getKey();
            if (noteId != null) {
                notesRef.child(noteId).setValue(noteContent)
                        .addOnSuccessListener(aVoid -> {
                            noteList.add(noteContent);
                            noteAdapter.notifyDataSetChanged();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(requireContext(), "Failed to add note", Toast.LENGTH_SHORT).show();
                        });
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadNotesFromFirebase();
    }
}
