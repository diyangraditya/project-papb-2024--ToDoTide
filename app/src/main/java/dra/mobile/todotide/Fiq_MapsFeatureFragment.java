package dra.mobile.todotide;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fiq_MapsFeatureFragment extends Fragment implements Fiq_SearchFragment.OnSearchListener {
    private List<Fiq_Maps> mapList;
    private Fiq_RecyclerViewFragment recyclerViewFragment;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fiq_halaman_map, container, false);

        mapList = new ArrayList<>();
        recyclerViewFragment = new Fiq_RecyclerViewFragment();

        databaseReference = FirebaseDatabase.getInstance().getReference("mapsData");

        setupFragments(view);
        fetchMapsData();

        return view;
    }

    private void setupFragments(View view) {
        Fiq_NavbarFragment navbarFragment = new Fiq_NavbarFragment();
        Fiq_SearchFragment searchFragment = new Fiq_SearchFragment();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.container_navbar, navbarFragment);
        transaction.replace(R.id.container_search, searchFragment);
        transaction.replace(R.id.container_rv, recyclerViewFragment);
        transaction.commit();

        searchFragment.setOnSearchListener(this);

        recyclerViewFragment.setOnEditClickListener(position -> {
            Fiq_Maps mapToEdit = mapList.get(position);

            Intent intent = new Intent(requireContext(), Fiq_MapsEdit.class);
            intent.putExtra("position", position);
            intent.putExtra("kegiatan", mapToEdit.getKegiatan());
            intent.putExtra("lokasi", mapToEdit.getLokasi());
            intent.putExtra("imageUrl", mapToEdit.getImageUrl());
            startActivityForResult(intent, 1001);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001 && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            int position = data.getIntExtra("position", -1);
            String updatedKegiatan = data.getStringExtra("kegiatan");
            String updatedLokasi = data.getStringExtra("lokasi");
            String updatedImageUrl = data.getStringExtra("imageUrl");

            if (position != -1) {
                Fiq_Maps updatedMap = mapList.get(position);
                updatedMap.setKegiatan(updatedKegiatan);
                updatedMap.setLokasi(updatedLokasi);
                updatedMap.setImageUrl(updatedImageUrl);

                recyclerViewFragment.updateItem(position, updatedMap);

                DatabaseReference mapRef = databaseReference.child(String.valueOf(position));
                mapRef.setValue(updatedMap).addOnSuccessListener(aVoid -> {
                    Toast.makeText(requireContext(), "Item updated successfully", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    Toast.makeText(requireContext(), "Failed to update item", Toast.LENGTH_SHORT).show();
                });
            }
        }
    }


    private void fetchMapsData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mapList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Fiq_Maps map = dataSnapshot.getValue(Fiq_Maps.class);
                    if (map != null) {
                        mapList.add(map);
                    }
                }
                recyclerViewFragment.setData(mapList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSearch(String query) {
        List<Fiq_Maps> filteredList = new ArrayList<>();
        for (Fiq_Maps map : mapList) {
            if (map.getKegiatan().toLowerCase().contains(query.toLowerCase()) ||
                    map.getLokasi().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(map);
            }
        }

        recyclerViewFragment.setData(query.isEmpty() ? mapList : filteredList);
        if (filteredList.isEmpty() && !query.isEmpty()) {
            Toast.makeText(requireContext(), "No results found", Toast.LENGTH_SHORT).show();
        }
    }

    public void openCreateActivity() {
        Intent intent = new Intent(requireContext(), Fiq_CreateActivity.class);
        startActivity(intent);
    }
}

