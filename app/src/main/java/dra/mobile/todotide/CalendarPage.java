package dra.mobile.todotide;

import android.os.Bundle;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CalendarPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_frag);

        loadFragment(new TopBarFragment(), R.id.container_top_bar);
        loadFragment(new BottomBarFragment(), R.id.container_bot_bar);
        loadFragment(new FullCalendarFragment(), R.id.container_main);
    }

    private void loadFragment(Fragment fragment, int containerId) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(containerId, fragment);
        ft.commit();
    }
}
