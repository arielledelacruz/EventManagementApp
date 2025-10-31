package com.fit2081.assignment3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fit2081.assignment3.provider.Event;

import java.util.List;

public class EventRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Event> data;
    private Context context;

    private final int HEADER_TYPE = 0;
    private final int ITEM_TYPE = 1;

    public EventRecyclerAdapter(List<Event> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public void setData(List<Event> data) {
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
        if (viewType == HEADER_TYPE) {
            View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card_layout_header, parent, false);
            return new HeaderViewHolder(headerView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card_layout, parent, false);
            return new ItemViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == HEADER_TYPE) {
        } else {
            int itemPosition = position - 1;
            Event event = data.get(itemPosition);
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.tvEventId.setText(event.getEventId());
            itemViewHolder.tvEventName.setText(event.getEventName());
            itemViewHolder.tvCategoryId.setText(event.getCategoryId());
            itemViewHolder.tvTicketsAvailable.setText(String.valueOf(event.getTicketsAvailable()));
            itemViewHolder.tvIsActive.setText(event.isActive() ? "Yes" : "No");

            itemViewHolder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, EventGoogleResult.class);
                intent.putExtra("eventName", event.getEventName());
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
        TextView tvEventId;
        TextView tvEventName;
        TextView tvCategoryId;
        TextView tvTicketsAvailable;
        TextView tvIsActive;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEventId = itemView.findViewById(R.id.tv_event_id);
            tvEventName = itemView.findViewById(R.id.tv_event_name);
            tvCategoryId = itemView.findViewById(R.id.tv_category_id);
            tvTicketsAvailable = itemView.findViewById(R.id.tv_tickets_available);
            tvIsActive = itemView.findViewById(R.id.tv_is_active);
        }
    }
}


