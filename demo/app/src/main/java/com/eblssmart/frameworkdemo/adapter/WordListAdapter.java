package com.eblssmart.frameworkdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eblssmart.frameworkdemo.R;
import com.eblssmart.frameworkdemo.entity.World;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WorldViewHolder> {

    class WorldViewHolder extends RecyclerView.ViewHolder {
        private final TextView WorldItemView;

        private WorldViewHolder(View itemView) {
            super(itemView);
            WorldItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<World> mWorlds; // Cached copy of Worlds

    public WordListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public WorldViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WorldViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WorldViewHolder holder, int position) {
        if (mWorlds != null) {
            World current = mWorlds.get(position);
            holder.WorldItemView.setText(current.getWorld());
        } else {
            // Covers the case of data not being ready yet.
            holder.WorldItemView.setText("No World");
        }
    }

    public void setWorlds(List<World> Worlds){
        mWorlds = Worlds;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWorlds has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mWorlds != null)
            return mWorlds.size();
        else return 0;
    }
}