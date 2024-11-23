package dra.mobile.todotide;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EventDAO {
    @Insert
    void insertEvent(Event event);

    @Update
    void updateEvent(Event event);

    @Delete
    void deleteEvent(Event event);

    @Query("SELECT * FROM events")
    List<Event> getAllEvents();

    @Query("SELECT * FROM events WHERE id = :id")
    Event getEventById(int id);
}
