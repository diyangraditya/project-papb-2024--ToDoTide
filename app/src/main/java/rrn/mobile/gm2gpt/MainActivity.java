package rrn.mobile.gm2gpt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView rvGoogleMaps;
    private MapsAdapter mapsAdapter;
    private List<Maps> mapList;
    private RequestQueue requestQueue;
    private ActivityResultLauncher<Intent> editActivityLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.halaman_maps);

        // Initialize RecyclerView and RequestQueue
        rvGoogleMaps = findViewById(R.id.rvGoogleMaps);
        rvGoogleMaps.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = Volley.newRequestQueue(this);
        mapList = new ArrayList<>();

        // Initialize ActivityResultLauncher for editing
        editActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Log.d(TAG, "onActivityResult called");  // Debug log
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Intent data = result.getData();
                            int position = data.getIntExtra("position", -1);
                            if (position != -1) {
                                String kegiatan = data.getStringExtra("kegiatan");
                                String lokasi = data.getStringExtra("lokasi");
                                String imageUrlString = data.getStringExtra("imageUrl"); // Updated to imageUrl

                                // Update the item in mapList and notify adapter
                                Maps updatedMap = mapList.get(position);
                                updatedMap.setKegiatan(kegiatan);
                                updatedMap.setLokasi(lokasi);
                                updatedMap.setImageUrl(imageUrlString); // Updated to imageUrl

                                mapsAdapter.notifyItemChanged(position);
                                Toast.makeText(MainActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );

        // Initialize the Adapter and set it to RecyclerView
        mapsAdapter = new MapsAdapter(this, mapList, new MapsAdapter.OnEditClickListener() {
            @Override
            public void onEditClick(int position) {
                Log.d(TAG, "onEditClick triggered for position: " + position);  // Debug log
                Intent intent = new Intent(MainActivity.this, MapsEdit.class);
                intent.putExtra("position", position);
                intent.putExtra("kegiatan", mapList.get(position).getKegiatan());
                intent.putExtra("lokasi", mapList.get(position).getLokasi());
                intent.putExtra("imageUrl", mapList.get(position).getImageUrl()); // Updated to imageUrl
                editActivityLauncher.launch(intent);
            }
        });
        rvGoogleMaps.setAdapter(mapsAdapter);

        // Fetch data from JSON API
        fetchMapsData();

        // Call the background task function
        performBackgroundTask();
    }

    private void fetchMapsData() {
        String url = "https://raw.githubusercontent.com/raihanfiqi/list_agenda/refs/heads/main/api_config.json";

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Maps>>() {}.getType();

                        List<Maps> mapsList = gson.fromJson(response.optJSONArray("images").toString(), listType);
                        mapList.clear();
                        mapList.addAll(mapsList);
                        mapsAdapter.notifyDataSetChanged();
                        Log.d(TAG, "Data fetched successfully");  // Debug log
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error fetching data: " + error.getMessage());  // Error log
                    }
                }
        );
        requestQueue.add(request);
    }

    // Method to perform a background task
    private void performBackgroundTask() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000); // Delay for demonstration
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Post the result back to the main thread
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Background task completed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }
}
