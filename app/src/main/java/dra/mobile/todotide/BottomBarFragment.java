package dra.mobile.todotide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class BottomBarFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_bottom_bar, container, false);

        view.findViewById(R.id.cal_icon).setOnClickListener(v -> loadFragment(new FullCalendarFragment()));
        view.findViewById(R.id.maps_icon).setOnClickListener(v -> loadFragment(new Fiq_MapsFeatureFragment()));
        view.findViewById(R.id.notes_icon).setOnClickListener(v -> loadFragment(new Nar_MainFragment()));

        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container_main, fragment)
                .commit();
    }
}
