package dra.mobile.todotide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class Fiq_SearchFragment extends Fragment{
    private EditText etSearch;
    private Button btnSearch;
    private boolean isSearchVisible = false;

    private OnSearchListener listener;

    public interface OnSearchListener {
        void onSearch(String query);
    }

    public void setOnSearchListener(OnSearchListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fiq_fragment_search, container, false);
        etSearch = view.findViewById(R.id.et_search);
        btnSearch = view.findViewById(R.id.btn_search);

        btnSearch.setOnClickListener(v -> {
            if (!isSearchVisible) {
                etSearch.setVisibility(View.VISIBLE);
                etSearch.requestFocus();
                btnSearch.setText("Enter");
                isSearchVisible = true;
            } else {
                String query = etSearch.getText().toString().trim();
                if (listener != null) {
                    listener.onSearch(query);
                }
            }
        });

        return view;
    }
}
