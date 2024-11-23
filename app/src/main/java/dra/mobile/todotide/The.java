package dra.mobile.todotide;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class The {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "nama_kegiatan")
    public String nama_kegiatan;

    @ColumnInfo(name = "waktu_kegiatan")
    public String waktu_kegiatan;

    @ColumnInfo(name = "tempat_kegiatan")
    public String tempat_kegiatan;
}
