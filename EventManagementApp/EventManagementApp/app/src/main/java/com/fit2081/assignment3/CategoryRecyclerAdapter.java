package com.fit2081.assignment3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fit2081.assignment3.provider.EventCategory;

import java.util.List;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<EventCategory> data;
    private Context context;

    private final int HEADER_TYPE = 0;
    private final int ITEM_TYPE = 1;

    public CategoryRecyclerAdapter(List<EventCategory> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public void setData(List<EventCategory> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_TYPE;
        }
        return ITEM_TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == HEADER_TYPE) {
            View headerView = inflater.inflate(R.layout.card_layout_header, parent, false);
            return new HeaderViewHolder(headerView);
        } else {
            View itemView = inflater.inflate(R.layout.card_layout, parent, false);
            return new ItemViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == HEADER_TYPE) {
        } else {
            int itemPosition = position - 1;
            EventCategory category = data.get(itemPosition);
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.tvCategoryId.setText(category.getCategoryId());
            itemViewHolder.tvCategoryName.setText(category.getName());
            itemViewHolder.tvEventCount.setText(String.valueOf(category.getEventCount()));
            itemViewHolder.tvIsActive.setText(category.isActive() ? "Yes" : "No");
            itemViewHolder.categoryLocation = category.getEventLocation();

            itemViewHolder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, GoogleMapsActivity.class);
                intent.putExtra("location", category.getEventLocation());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryId;
        TextView tvCategoryName;
        TextView tvEventCount;
        TextView tvIsActive;
        String categoryLocation;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryId = itemView.findViewById(R.id.tv_id);
            tvCategoryName = itemView.findViewById(R.id.tv_name);
            tvEventCount = itemView.findViewById(R.id.tv_event_count);
            tvIsActive = itemView.findViewById(R.id.tv_active);
        }
    }
}


