package com.qrcodegenerator.creation.data.db.dao;

import com.qrcodegenerator.creation.data.db.model.History;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface HistoryDao {

    @Query("SELECT * FROM history")
    List<History> getAll();

    @Query("SELECT * FROM history WHERE isCreate like :isCreate")
    List<History> getHistoryByCreate(boolean isCreate);

    @Query("SELECT * FROM history WHERE id like :id")
    History getHistoryById(long id);

    @Query("SELECT * FROM history ORDER BY id DESC LIMIT 1")
    History getLastHistory();

    @Insert
    long insert(History history);

    @Delete
    int delete(History history);

    @Update
    void update(History history);
}
