package hdn.dev.baseproject3.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hdn.dev.baseproject3.R;
import hdn.dev.baseproject3.models.Ticket;
import hdn.dev.baseproject3.models.TicketDetail;
import hdn.dev.baseproject3.models.TicketDetailResponse;
import hdn.dev.baseproject3.retrofit.RetrofitClient;
import hdn.dev.baseproject3.utils.FormatDay;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryRVAdapter extends RecyclerView.Adapter<HistoryRVAdapter.HistoryViewHolder> {
    private Context context;
    private List<Ticket> list;
    HistoryRVAdapter.OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(Ticket data);
    }

    public void setOnItemClickListener(HistoryRVAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public HistoryRVAdapter(Context context, List<Ticket> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_history_ticket, parent, false);
        return new HistoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        Ticket ticket = list.get(position);
        Call<TicketDetailResponse> ticketResponse = RetrofitClient.getInstance().getMyApi().getTicketDetail(ticket.getTicketId());
        ticketResponse.enqueue(new Callback<TicketDetailResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<TicketDetailResponse> call, Response<TicketDetailResponse> response) {
                TicketDetailResponse ticketDetailResponse = response.body();
                if (ticketDetailResponse.getStatus().equals("success")) {
                    TicketDetail ticketDetail = ticketDetailResponse.getData();
                    System.out.println(ticketDetail);
                    holder.idTVTimeBookingTicket.setText(FormatDay.formatTimeBooking(ticket.getTime_booking()));
                    holder.idTVDateDepartureTicket.setText(FormatDay.formatMMMMdd(ticketDetail.getFlight().getDepartureTime()));
                    holder.idTVDateDestinationTicket.setText(FormatDay.formatMMMMdd(ticketDetail.getFlight().getArrivalTime()));
                    holder.idTVDepartureFlightHistory.setText(ticketDetail.getFlight().getDepartureSort());
                    holder.idTVDestinationFlightHistory.setText(ticketDetail.getFlight().getDestinationSort());
                    holder.idTVTimeDepartureHistory.setText(FormatDay.formatTime(ticketDetail.getFlight().getDepartureTime()));
                    holder.idTVTimeDestinationHistory.setText(FormatDay.formatTime(ticketDetail.getFlight().getArrivalTime()));
                    holder.idTVDescriptionTicket.setText("Total Amount: $" + ticket.getTotalAmount() + " | Seat: " + ticket.getList_seat().replace(";", " "));
                } else {
                    Toast.makeText(context, ticketDetailResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TicketDetailResponse> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView idTVTimeBookingTicket, idTVDateDepartureTicket, idTVDateDestinationTicket, idTVDepartureFlightHistory, idTVDestinationFlightHistory, idTVTimeDepartureHistory, idTVTimeDestinationHistory, idTVDescriptionTicket;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            idTVTimeBookingTicket = itemView.findViewById(R.id.idTVTimeBookingTicket);
            idTVDateDepartureTicket = itemView.findViewById(R.id.idTVDateDepartureTicket);
            idTVDateDestinationTicket = itemView.findViewById(R.id.idTVDateDestinationTicket);
            idTVDepartureFlightHistory = itemView.findViewById(R.id.idTVDepartureFlightHistory);
            idTVDestinationFlightHistory = itemView.findViewById(R.id.idTVDestinationFlightHistory);
            idTVTimeDepartureHistory = itemView.findViewById(R.id.idTVTimeDepartureHistory);
            idTVTimeDestinationHistory = itemView.findViewById(R.id.idTVTimeDestinationHistory);
            idTVDescriptionTicket = itemView.findViewById(R.id.idTVDescriptionTicket);


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
