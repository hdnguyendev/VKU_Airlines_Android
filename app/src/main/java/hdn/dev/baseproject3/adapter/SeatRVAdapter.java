package hdn.dev.baseproject3.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hdn.dev.baseproject3.R;
import hdn.dev.baseproject3.fragments.PickSeatFragment;
import hdn.dev.baseproject3.models.Seat;

public class SeatRVAdapter extends RecyclerView.Adapter<SeatRVAdapter.SeatViewHolder> {
    Context context;
    private PickSeatFragment mFragment;
    List<Seat> list;

    int seatSelected = 0;
    SeatRVAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(Seat data);
    }

    public void setOnItemClickListener(SeatRVAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public SeatRVAdapter(Context context, List<Seat> list,PickSeatFragment mFragment) {
        this.context = context;
        this.list = list;
        this.mFragment = mFragment;
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_seat, parent, false);
        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        Seat seat = list.get(position);
        if (seat.getAvailable()) {
            holder.idIVSeat.setImageResource(R.drawable.ic_seat_green_24);
        } else {
            holder.idIVSeat.setImageResource(R.drawable.ic_seat_black_24);
        }
        holder.idIVSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable image_current = holder.idIVSeat.getDrawable();
                if (image_current.getConstantState().equals(context.getResources().getDrawable(R.drawable.ic_seat_blue_24).getConstantState()) ) {
                    holder.idIVSeat.setImageResource(R.drawable.ic_seat_green_24);
                    mFragment.subSeatSelected(1, seat.getSeatName(), seat.getSeatId());
                    mFragment.subMoney(seat.getPrice());
                } else if (image_current.getConstantState().equals(context.getResources().getDrawable(R.drawable.ic_seat_green_24).getConstantState())  && !mFragment.seatSelectedEnough()) {
                    holder.idIVSeat.setImageResource(R.drawable.ic_seat_blue_24);
                    mFragment.addSeatSelected(1, seat.getSeatName(), seat.getSeatId());
                    mFragment.addMoney(seat.getPrice());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SeatViewHolder extends RecyclerView.ViewHolder {
        private ImageView idIVSeat;

        public SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            idIVSeat = itemView.findViewById(R.id.idIVSeat);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && mListener != null) {
                        mListener.onItemClick(list.get(position));
                    }
                }
            });
        }
    }


}
