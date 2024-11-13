package com.example.project3;

import android.os.Bundle;
import android.widget.Button;
import android.app.AlertDialog;
import android.text.InputType;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private FragmentListTask fragmentListTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi Fragment
        fragmentListTask = new FragmentListTask();
        loadFragment(fragmentListTask);

        // Tombol kecil untuk menambahkan list task
        Button btnAddTask = findViewById(R.id.btnAddTask);
        btnAddTask.setOnClickListener(v -> showAddTaskDialog());
    }

    // Method untuk memuat fragment
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    // Method untuk menampilkan dialog input task
    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Task");

        // Membuat input field
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Tombol Save
        builder.setPositiveButton("Save", (dialog, which) -> {
            String task = input.getText().toString();
            if (!task.isEmpty() && fragmentListTask != null) {
                fragmentListTask.addNewTask(task); // Tambahkan task baru
            }
        });

        // Tombol Cancel
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
