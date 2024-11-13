package rrn.mobile.gm2gpt;

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

public class MapsAdapter extends RecyclerView.Adapter<MapsAdapter.MapsViewHolder> {

    private List<Maps> mapsList;
    private Context context;
    private OnEditClickListener onEditClickListener;

    public MapsAdapter(Context context, List<Maps> mapsList, OnEditClickListener onEditClickListener) {
        this.context = context;
        this.mapsList = mapsList;
        this.onEditClickListener = onEditClickListener;
    }

    // Interface OnEditClickListener declaration
    public interface OnEditClickListener {
        void onEditClick(int position); // Pass position as a parameter
    }

    @NonNull
    @Override
    public MapsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tampilan_maps, parent, false);
        return new MapsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MapsViewHolder holder, int position) {
        Maps mapItem = mapsList.get(position);
        holder.tvKegiatan.setText(mapItem.getKegiatan());
        holder.tvLokasi.setText(mapItem.getLokasi());

        // Load image using Glide
        Glide.with(context)
                .load(mapItem.getImageUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // Log atau tampilkan pesan error
                        return false; // Return false to let Glide handle the error placeholder if any
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false; // Return false to allow Glide to display the image
                    }
                })
                .into(holder.uploadedImageView);

        // Set click listener for edit action
        holder.itemView.findViewById(R.id.ivEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onEditClickListener != null) {
                    onEditClickListener.onEditClick(holder.getAdapterPosition()); // Use getAdapterPosition() instead of position
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mapsList.size();
    }

    public static class MapsViewHolder extends RecyclerView.ViewHolder {
        ImageView uploadedImageView;
        TextView tvKegiatan, tvLokasi;

        public MapsViewHolder(@NonNull View itemView) {
            super(itemView);
            uploadedImageView = itemView.findViewById(R.id.uploaded_image_view);
            tvKegiatan = itemView.findViewById(R.id.tvKegiatan);
            tvLokasi = itemView.findViewById(R.id.tvLokasi);
        }
    }
}
