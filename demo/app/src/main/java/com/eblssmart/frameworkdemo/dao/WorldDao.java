package com.eblssmart.frameworkdemo.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.eblssmart.frameworkdemo.entity.World;

import java.util.List;

@Dao
public interface WorldDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(World world);

    @Query("delete from world_table")
    void deleteAll();

    @Query("select * from world_table order by world ASC")
    LiveData<List<World>> getAllWords();
}
