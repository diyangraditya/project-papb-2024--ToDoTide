package rrn.mobile.TodoTide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import rrn.mobile.gm2gptcopy10.R;

public class MainActivity extends AppCompatActivity implements SearchFragment.OnSearchListener {

    private static final String TAG = "MainActivity";
    private static final int CREATE_REQUEST_CODE = 1001;
    private List<Maps> mapList;
    private RecyclerViewFragment recyclerViewFragment;
    private ActivityResultLauncher<Intent> editActivityLauncher;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.halaman_map);

        mapList = new ArrayList<>();
        recyclerViewFragment = new RecyclerViewFragment();

        databaseReference = FirebaseDatabase.getInstance().getReference("mapsData");

        setupFragments();

        initActivityLauncher();

        fetchMapsData();

        performBackgroundTask();
    }

    private void setupFragments() {
        NavbarFragment navbarFragment = new NavbarFragment();
        SearchFragment searchFragment = new SearchFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_navbar, navbarFragment);
        transaction.replace(R.id.container_search, searchFragment);
        transaction.replace(R.id.container_rv, recyclerViewFragment);
        transaction.commit();

        searchFragment.setOnSearchListener(this);

        recyclerViewFragment.setOnEditClickListener(position -> {
            Intent intent = new Intent(MainActivity.this, MapsEdit.class);
            intent.putExtra("position", position);
            intent.putExtra("kegiatan", mapList.get(position).getKegiatan());
            intent.putExtra("lokasi", mapList.get(position).getLokasi());
            intent.putExtra("imageUrl", mapList.get(position).getImageUrl());
            editActivityLauncher.launch(intent);
        });
    }

    public void openCreateActivity() {
        Intent intent = new Intent(MainActivity.this, CreateActivity.class);
        startActivityForResult(intent, CREATE_REQUEST_CODE);
    }

    private void initActivityLauncher() {
        editActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        int position = data.getIntExtra("position", -1);
                        if (position != -1) {
                            String kegiatan = data.getStringExtra("kegiatan");
                            String lokasi = data.getStringExtra("lokasi");
                            String imageUrlString = data.getStringExtra("imageUrl");

                            Maps updatedMap = mapList.get(position);
                            updatedMap.setKegiatan(kegiatan);
                            updatedMap.setLokasi(lokasi);
                            updatedMap.setImageUrl(imageUrlString);

                            databaseReference.child(String.valueOf(position)).setValue(updatedMap)
                                    .addOnSuccessListener(aVoid -> Toast.makeText(MainActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show())
                                    .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Failed to update data", Toast.LENGTH_SHORT).show());

                            recyclerViewFragment.updateItem(position, updatedMap);
                        }
                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String kegiatan = data.getStringExtra("kegiatan");
            String lokasi = data.getStringExtra("lokasi");

            Maps newMap = new Maps();
            newMap.setKegiatan(kegiatan);
            newMap.setLokasi(lokasi);
            newMap.setImageUrl("");

            String newKey = databaseReference.push().getKey();
            if (newKey != null) {
                databaseReference.child(newKey).setValue(newMap)
                        .addOnSuccessListener(aVoid -> Toast.makeText(MainActivity.this, "Item added successfully", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Failed to add item", Toast.LENGTH_SHORT).show());
            }
        }
    }

    private void fetchMapsData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                mapList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Maps map = dataSnapshot.getValue(Maps.class);
                    if (map != null) {
                        mapList.add(map);
                    }
                }
                recyclerViewFragment.setData(mapList);
                Log.d(TAG, "Data fetched successfully from Firebase");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void performBackgroundTask() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Handler(Looper.getMainLooper()).post(() ->
                    Toast.makeText(MainActivity.this, "Background task completed", Toast.LENGTH_SHORT).show()
            );
        }).start();
    }

    @Override
    public void onSearch(String query) {
        List<Maps> filteredList = new ArrayList<>();
        for (Maps map : mapList) {
            if (map.getKegiatan().toLowerCase().contains(query.toLowerCase()) ||
                    map.getLokasi().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(map);
            }
        }

        recyclerViewFragment.setData(query.isEmpty() ? mapList : filteredList);
        if (filteredList.isEmpty() && !query.isEmpty()) {
            Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
        }
    }
}
