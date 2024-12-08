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

public class Nar_FragmentListTask extends Fragment {

    private ListView listViewTasks;
    private ArrayList<String> taskList;
    private ArrayAdapter<String> taskAdapter;
    private DatabaseReference tasksRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nar_fragment_list_task, container, false);

        // Initialize ListView and Firebase Reference
        listViewTasks = view.findViewById(R.id.listViewTasks);
        taskList = new ArrayList<>();
        taskAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, taskList);
        listViewTasks.setAdapter(taskAdapter);

        tasksRef = FirebaseDatabase.getInstance().getReference("tasks");

        // Load tasks from Firebase
        loadTasksFromFirebase();

        return view;
    }

    private void loadTasksFromFirebase() {
        tasksRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                taskList.clear();
                for (DataSnapshot taskSnapshot : snapshot.getChildren()) {
                    String task = taskSnapshot.getValue(String.class);
                    if (task != null) {
                        taskList.add(task);
                    }
                }
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Failed to load tasks: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addNewTask(String taskContent) {
        if (tasksRef != null && !taskContent.isEmpty()) {
            String taskId = tasksRef.push().getKey();
            if (taskId != null) {
                tasksRef.child(taskId).setValue(taskContent)
                        .addOnSuccessListener(aVoid -> {
                            taskList.add(taskContent);
                            taskAdapter.notifyDataSetChanged();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(requireContext(), "Failed to add task", Toast.LENGTH_SHORT).show();
                        });
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reload tasks when the fragment is resumed
        loadTasksFromFirebase();
    }
}
