package hdn.dev.baseproject3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hdn.dev.baseproject3.R;
import hdn.dev.baseproject3.models.Message;

public class ChatGPTAdapter extends RecyclerView.Adapter<ChatGPTAdapter.MyViewHolder> {
    Context context;
    List<Message> messageList;

    public ChatGPTAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Message message = messageList.get(position);
        if (message.getSend_id() == 0){
            holder.layout2.setVisibility(View.VISIBLE);
            holder.layout1.setVisibility(View.GONE);
            holder.tv_right.setText(message.getContent());

        } else {
            holder.layout1.setVisibility(View.VISIBLE);
            holder.layout2.setVisibility(View.GONE);
            holder.tv_left.setText(message.getContent());

        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layout1, layout2;
        TextView tv_left, tv_right;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layout1 = itemView.findViewById(R.id.lay1);
            layout2 = itemView.findViewById(R.id.lay2);
            tv_left = itemView.findViewById(R.id.tv_chat_left);
            tv_right = itemView.findViewById(R.id.tv_chat_right);

        }

    }
}
