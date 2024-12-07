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
import java.util.List;

public class Nar_FragmentListTask extends Fragment {

    private ArrayList<String> taskList;
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private DatabaseReference tasksRef;

    public Nar_FragmentListTask() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nar_fragment_list_task, container, false);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://to-do-tide-default-rtdb.asia-southeast1.firebasedatabase.app/");
        tasksRef = database.getReference("tasks");

        listView = view.findViewById(R.id.listViewTasks);

        taskList = new ArrayList<>();
        adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, taskList);
        listView.setAdapter(adapter);

        loadTasksFromFirebase();

        return view;
    }

    public void addNewTask(String task) {
        String taskId = tasksRef.push().getKey();
        Nar_TaskFirebase taskFirebase = new Nar_TaskFirebase(taskId, task);

        if (taskId != null) {
            tasksRef.child(taskId).setValue(taskFirebase)
                    .addOnSuccessListener(aVoid -> Toast.makeText(requireActivity(), "Task Saved: " + task, Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(requireActivity(), "Error saving task: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void loadTasksFromFirebase() {
        tasksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Nar_TaskFirebase task = snapshot.getValue(Nar_TaskFirebase.class);
                    if (task != null) {
                        taskList.add(task.getTask());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireActivity(), "Error loading tasks: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
