package dra.mobile.todotide;

import org.json.JSONException;
import org.json.JSONObject;

public class Fiq_Maps {
    private String kegiatan;
    private String lokasi;
    private String imageUrl;

    public Fiq_Maps() {
    }

    public Fiq_Maps(JSONObject jsonObject) throws JSONException {
        this.kegiatan = jsonObject.optString("kegiatan", "");
        this.lokasi = jsonObject.optString("lokasi", "");
        this.imageUrl = jsonObject.optString("imageUrl", "");
    }

    public Fiq_Maps(String kegiatan, String lokasi, String imageUrl) {
        this.kegiatan = kegiatan;
        this.lokasi = lokasi;
        this.imageUrl = imageUrl;
    }

    public String getKegiatan() {
        return kegiatan;
    }

    public void setKegiatan(String kegiatan) {
        this.kegiatan = kegiatan;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static Fiq_Maps fromJson(JSONObject jsonObject) {
        Fiq_Maps map = new Fiq_Maps();
        try {
            map.setKegiatan(jsonObject.getString("kegiatan"));
            map.setLokasi(jsonObject.getString("lokasi"));
            map.setImageUrl(jsonObject.getString("imageUrl"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }
}
