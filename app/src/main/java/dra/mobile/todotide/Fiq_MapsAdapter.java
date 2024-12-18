package dra.mobile.todotide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class Fiq_MapsAdapter extends RecyclerView.Adapter<Fiq_MapsAdapter.MapsViewHolder>{

    private List<Fiq_Maps> mapsList;
    private Context context;
    private OnEditClickListener onEditClickListener;

    public Fiq_MapsAdapter(Context context, List<Fiq_Maps> mapsList, OnEditClickListener onEditClickListener) {
        this.context = context;
        this.mapsList = mapsList;
        this.onEditClickListener = onEditClickListener;
    }

    public interface OnEditClickListener {
        void onEditClick(int position);
    }

    public void updateData(List<Fiq_Maps> newMapsList) {
        this.mapsList = newMapsList;
        notifyDataSetChanged();
    }

    public void setOnEditClickListener(OnEditClickListener listener) {
        this.onEditClickListener = listener;
    }

    @NonNull
    @Override
    public MapsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fiq_tampilan_maps, parent, false);
        return new MapsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MapsViewHolder holder, int position) {
        Fiq_Maps mapItem = mapsList.get(position);
        holder.tvKegiatan.setText(mapItem.getKegiatan());
        holder.tvLokasi.setText(mapItem.getLokasi());

        Glide.with(context)
                .load(mapItem.getImageUrl())
                .into(holder.uploadedImageView);

        holder.ivEdit.setOnClickListener(view -> {
            if (onEditClickListener != null) {
                onEditClickListener.onEditClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mapsList != null ? mapsList.size() : 0;
    }

    public static class MapsViewHolder extends RecyclerView.ViewHolder {
        ImageView uploadedImageView, ivEdit;
        TextView tvKegiatan, tvLokasi;

        public MapsViewHolder(@NonNull View itemView) {
            super(itemView);
            uploadedImageView = itemView.findViewById(R.id.uploaded_image_view);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            tvKegiatan = itemView.findViewById(R.id.tvKegiatan);
            tvLokasi = itemView.findViewById(R.id.tvLokasi);
        }
    }
}
