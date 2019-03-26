package com.eblssmart.frameworkdemo.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.eblssmart.frameworkdemo.entity.World;
import com.eblssmart.frameworkdemo.repository.WorldRepository;

import java.util.List;

public class WorldViewModel extends AndroidViewModel {
    private WorldRepository mRepository;
    private LiveData<List<World>> mAllWords;

    public WorldViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WorldRepository(application);
        mAllWords = mRepository.getAllworlds();
    }

    public LiveData<List<World>> getAllWords() {
        return mAllWords;
    }

    public void insert(World world) {
        mRepository.insert(world);
    }
}
