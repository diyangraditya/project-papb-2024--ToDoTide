package dra.mobile.todotide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class Nar_MainFragment extends Fragment {

    private Nar_FragmentListTask fragmentListTask;
    private Nar_FragmentListNotes fragmentListNotes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nar_main_fragment, container, false);

        fragmentListTask = new Nar_FragmentListTask();
        fragmentListNotes = new Nar_FragmentListNotes();

        // Load List Task Fragment
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragmentListTask)
                .commit();

        // Load Notes Fragment
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerNotes, fragmentListNotes)
                .commit();

        // Add Task Button
        Button btnAddTask = view.findViewById(R.id.btnAddTask);
        btnAddTask.setOnClickListener(v -> showAddTaskDialog());

        // Add Note Button
        Button btnAddNotes = view.findViewById(R.id.btnAddNotes);
        btnAddNotes.setOnClickListener(v -> showAddNoteDialog());

        return view;
    }

    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add New Task");

        final EditText input = new EditText(requireContext());
        input.setHint("Enter task");
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String task = input.getText().toString();
            if (fragmentListTask != null) {
                fragmentListTask.addNewTask(task);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    private void showAddNoteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add New Note");

        final EditText input = new EditText(requireContext());
        input.setHint("Enter note content");
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String note = input.getText().toString();
            if (fragmentListNotes != null) {
                fragmentListNotes.addNewNote(note);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();
    }
}
