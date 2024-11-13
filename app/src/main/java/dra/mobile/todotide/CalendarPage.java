package dra.mobile.todotide;

import android.os.Bundle;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class CalendarPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_frag);

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
