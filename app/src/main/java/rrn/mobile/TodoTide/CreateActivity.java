package rrn.mobile.TodoTide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import rrn.mobile.gm2gptcopy10.R;

public class CreateActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText kegiatanInput, lokasiInput;
    private ImageView imageView;
    private CardView saveButton, chooseImageButton;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        kegiatanInput = findViewById(R.id.input_kegiatan);
        lokasiInput = findViewById(R.id.input_lokasi);
        imageView = findViewById(R.id.imageView3);
        saveButton = findViewById(R.id.saveCardView);
        chooseImageButton = findViewById(R.id.cardViewChooseImage);

        chooseImageButton.setOnClickListener(v -> openImagePicker());

        saveButton.setOnClickListener(v -> {
            String kegiatan = kegiatanInput.getText().toString().trim();
            String lokasi = lokasiInput.getText().toString().trim();

            if (!kegiatan.isEmpty() && !lokasi.isEmpty() && imageUri != null) {
                saveDataToDatabase(kegiatan, lokasi, imageUri.toString());
            } else {
                Toast.makeText(CreateActivity.this, "Please fill all fields and choose an image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openImagePicker() {
        ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            if (imageUri != null) {
                imageView.setImageURI(imageUri);
            }
        }
    }

    private void saveDataToDatabase(String kegiatan, String lokasi, String imageUrl) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("mapsData");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long id = snapshot.getChildrenCount();
                DatabaseReference newItemRef = databaseReference.child(String.valueOf((int) id));

                newItemRef.setValue(new Maps(kegiatan, lokasi, imageUrl))
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(CreateActivity.this, "Item added successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .addOnFailureListener(e -> Toast.makeText(CreateActivity.this, "Failed to save data", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CreateActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
