package com.eblssmart.frameworkdemo.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "world_table")
public class World {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "world")
    private String mWorld;

    public World(String mWorld) {
        this.mWorld = mWorld;
    }

    public String getWorld() {
        return mWorld;
    }
}
