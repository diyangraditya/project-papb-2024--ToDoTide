package com.example.project3;

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
import java.util.ArrayList;

public class FragmentListTask extends Fragment {

    private ArrayList<String> taskList;
    private ArrayAdapter<String> adapter;
    private ListView listView;

    public FragmentListTask() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_task, container, false);

        // Inisialisasi komponen UI
        listView = view.findViewById(R.id.listViewTasks);

        // Inisialisasi task list dan adapter
        taskList = new ArrayList<>();
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, taskList);
        listView.setAdapter(adapter);

        return view;
    }

    // Method untuk menambah task baru
    public void addNewTask(String task) {
        taskList.add(task);
        adapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "Task Saved: " + task, Toast.LENGTH_SHORT).show();
    }
}
