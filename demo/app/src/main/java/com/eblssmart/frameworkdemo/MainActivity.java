package com.eblssmart.frameworkdemo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.eblssmart.frameworkdemo.adapter.WordListAdapter;
import com.eblssmart.frameworkdemo.entity.World;
import com.eblssmart.frameworkdemo.viewModel.WorldViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WorldViewModel mWorldViewModel;
    public static final int NEW_World_ACTIVITY_REQUEST_CODE = 1;
    private WordListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rv = findViewById(R.id.recyclerview);
        adapter = new WordListAdapter(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewWorldActivity.class);
                startActivityForResult(intent, NEW_World_ACTIVITY_REQUEST_CODE);
            }
        });
        mWorldViewModel = ViewModelProviders.of(this).get(WorldViewModel.class);
        mWorldViewModel.getAllWords().observe(this, new Observer<List<World>>() {
            @Override
            public void onChanged(@Nullable List<World> worlds) {
                adapter.setWorlds(worlds);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_World_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            World World = new World(data.getStringExtra(NewWorldActivity.EXTRA_REPLY));
            mWorldViewModel.insert(World);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
