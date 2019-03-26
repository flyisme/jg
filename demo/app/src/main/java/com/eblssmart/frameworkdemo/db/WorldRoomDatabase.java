package com.eblssmart.frameworkdemo.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.eblssmart.frameworkdemo.dao.WorldDao;
import com.eblssmart.frameworkdemo.entity.World;

@Database(entities = {World.class}, version = 1)
public abstract class WorldRoomDatabase extends RoomDatabase {
    private static volatile WorldRoomDatabase INSTANCE;

    public static WorldRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WorldRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WorldRoomDatabase.class, "world_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract WorldDao worldDao();
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WorldDao mDao;

        PopulateDbAsync(WorldRoomDatabase db) {
            mDao = db.worldDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            World World = new World("Hello");
            mDao.insert(World);
            World = new World("World");
            mDao.insert(World);
            return null;
        }
    }
}
