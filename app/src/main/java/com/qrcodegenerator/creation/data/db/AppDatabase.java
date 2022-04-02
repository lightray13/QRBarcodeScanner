package com.qrcodegenerator.creation.data.db;

import com.qrcodegenerator.creation.data.db.dao.HistoryDao;
import com.qrcodegenerator.creation.data.db.model.History;

import javax.inject.Singleton;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Singleton
@Database(entities = {History.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HistoryDao historyDao();
}
