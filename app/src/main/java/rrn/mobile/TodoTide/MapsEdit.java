package rrn.mobile.TodoTide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import rrn.mobile.gm2gptcopy10.R;

public class MapsEdit extends AppCompatActivity {

    private ImageView imageView;
    private FloatingActionButton button;
    private Uri currentImageUri;
    private int position;
    private EditText etKegiatan, etLokasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_maps);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        String kegiatan = intent.getStringExtra("kegiatan");
        String lokasi = intent.getStringExtra("lokasi");
        String imageUrl = intent.getStringExtra("imageUrl");

        etKegiatan = findViewById(R.id.etKegiatan);
        etLokasi = findViewById(R.id.etLokasi);
        imageView = findViewById(R.id.imageView3);
        button = findViewById(R.id.fAction);
        CardView cvSimpan = findViewById(R.id.saveCardView);
        ImageView imageView4 = findViewById(R.id.backButton);

        etKegiatan.setText(kegiatan);
        etLokasi.setText(lokasi);

        if (imageUrl != null) {
            Glide.with(this).load(imageUrl).into(imageView);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(MapsEdit.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });

        cvSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedKegiatan = etKegiatan.getText().toString();
                String updatedLokasi = etLokasi.getText().toString();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("position", position);
                resultIntent.putExtra("kegiatan", updatedKegiatan);
                resultIntent.putExtra("lokasi", updatedLokasi);
                resultIntent.putExtra("imageUrl", currentImageUri != null ? currentImageUri.toString() : imageUrl);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        imageView4.setOnClickListener(view -> finish());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                imageView.setImageURI(uri);
                currentImageUri = uri;
            }
        }
    }
}