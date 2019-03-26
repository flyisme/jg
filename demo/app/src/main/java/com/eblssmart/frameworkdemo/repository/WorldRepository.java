package com.eblssmart.frameworkdemo.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.eblssmart.frameworkdemo.dao.WorldDao;
import com.eblssmart.frameworkdemo.db.WorldRoomDatabase;
import com.eblssmart.frameworkdemo.entity.World;

import java.util.List;

public class WorldRepository {
    private WorldDao mWorldDao;
    private LiveData<List<World>> mAllworlds;

    public WorldRepository(Application application) {
        WorldRoomDatabase db = WorldRoomDatabase.getDatabase(application);
        mWorldDao = db.worldDao();
        mAllworlds = mWorldDao.getAllWords();
    }

    public LiveData<List<World>> getAllworlds() {
        return mAllworlds;
    }

    public void insert(World world) {
        new insertAsyncTask(mWorldDao).execute(world);
    }

    private class insertAsyncTask extends AsyncTask<World, Void, Void> {


        private WorldDao mAsyncTaskDao;

        insertAsyncTask(WorldDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final World... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
