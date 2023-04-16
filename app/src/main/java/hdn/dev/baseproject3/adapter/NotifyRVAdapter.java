package hdn.dev.baseproject3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hdn.dev.baseproject3.R;

public class NotifyRVAdapter extends RecyclerView.Adapter<NotifyRVAdapter.NotifyViewHolder> {
    private List<String> list;

    public NotifyRVAdapter(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public NotifyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_notify, parent, false);
        return new NotifyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifyViewHolder holder, int position) {
        String title = list.get(position);
        holder.title.setText(title);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotifyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        public NotifyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.idTVTitle);
        }
    }
}
