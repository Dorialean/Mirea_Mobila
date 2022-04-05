package ru.mirea.lukutin.controltask.ui.stories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.lukutin.controltask.R;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<Item> items;

    ItemAdapter(Context context, List<Item> items) {
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.nameStory.setText(item.getName());
        holder.dateStory.setText(item.getDate());
        holder.wordStory.setText(item.getWord());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameStory, dateStory, wordStory;
        ViewHolder(View view){
            super(view);
            nameStory = view.findViewById(R.id.nameStory);
            dateStory = view.findViewById(R.id.dateStory);
            wordStory = view.findViewById(R.id.wordStory);
        }
    }
}
