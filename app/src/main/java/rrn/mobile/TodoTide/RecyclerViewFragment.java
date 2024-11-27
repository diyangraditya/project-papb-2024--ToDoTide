package rrn.mobile.TodoTide;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import rrn.mobile.gm2gptcopy10.R;

public class RecyclerViewFragment extends Fragment {

    private RecyclerView rvGoogleMaps;
    private MapsAdapter mapsAdapter;
    private List<Maps> mapList;
    private MapsAdapter.OnEditClickListener editClickListener;

    public void setData(List<Maps> mapList) {
        this.mapList = mapList;
        if (mapsAdapter == null) {
            mapsAdapter = new MapsAdapter(getContext(), mapList, editClickListener);
            rvGoogleMaps.setAdapter(mapsAdapter);
        } else {
            mapsAdapter.updateData(mapList);
        }
    }

    public void setOnEditClickListener(MapsAdapter.OnEditClickListener listener) {
        this.editClickListener = listener;
        if (mapsAdapter != null) {
            mapsAdapter.setOnEditClickListener(listener);
        }
    }

    public void updateItem(int position, Maps updatedMap) {
        if (mapList != null && position >= 0 && position < mapList.size()) {
            mapList.set(position, updatedMap);
            mapsAdapter.notifyItemChanged(position);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        rvGoogleMaps = view.findViewById(R.id.rvGoogleMaps);
        rvGoogleMaps.setLayoutManager(new LinearLayoutManager(getContext()));
        if (mapsAdapter != null) {
            rvGoogleMaps.setAdapter(mapsAdapter);
        }
        return view;
    }
}
