package dra.mobile.todotide;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;

public class AddEventDialog extends Dialog {
    public interface AddEventListener {
        void onEventAdded(String title, String date, String location);
    }

    public AddEventDialog(Context context, AddEventListener listener) {
        super(context);
        setContentView(R.layout.dialog_add_event);

        EditText titleInput = findViewById(R.id.eventTitleInput);
        EditText dateInput = findViewById(R.id.eventDateInput);
        EditText locationInput = findViewById(R.id.eventLocationInput);
        Button saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString();
            String date = dateInput.getText().toString();
            String location = locationInput.getText().toString();
            listener.onEventAdded(title, date, location);
            dismiss();
        });
    }
}
