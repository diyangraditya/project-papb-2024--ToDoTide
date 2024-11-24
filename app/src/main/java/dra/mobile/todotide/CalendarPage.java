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
    private static final String FirebaseURL = "https://to-do-tide-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_frag);

        databaseReference = FirebaseDatabase.getInstance(FirebaseURL).getReference();

        loadFragment(new TopBarFragment(), R.id.container_top_bar);
        loadFragment(new CalendarFragment(), R.id.container_calendar);
        loadFragment(new NotesFragment(), R.id.container_notes);
        loadFragment(new BottomBarFragment(), R.id.container_bot_bar);
    }

    private void loadFragment(Fragment fragment, int containerId) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ftranc = fm.beginTransaction();
        ftranc.replace(containerId, fragment);
        ftranc.commit();
    }
}
