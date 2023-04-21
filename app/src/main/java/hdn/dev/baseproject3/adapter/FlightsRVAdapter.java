package hdn.dev.baseproject3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import hdn.dev.baseproject3.R;
import hdn.dev.baseproject3.models.Flight;

public class FlightsRVAdapter extends RecyclerView.Adapter<FlightsRVAdapter.FlightViewHolder> {
    Context context;
    List<Flight> list;
    OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(Flight data);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public FlightsRVAdapter(Context context, List<Flight> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_flight, parent, false);
        return new FlightsRVAdapter.FlightViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightViewHolder holder, int position) {
        Flight flight = list.get(position);
        // Xử lí ngày giờ xuất phát
        {
            // Chuỗi ban đầu
            String dateStr = flight.getDepartureTime();
            // Định dạng ngày giờ ban đầu
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

            // Chuyển đổi chuỗi thành đối tượng Date
            Date date = null;
            try {
                date = originalFormat.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Định dạng lại đối tượng Date thành chuỗi theo định dạng mới
            DateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            String newDateStr = newFormat.format(date);
            holder.tv_timeDeparture.setText(newDateStr);

        }
        // Xử lí, tính toán thời gian bay là bao lâu
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDateTime start = LocalDateTime.parse(flight.getDepartureTime(), formatter);
            LocalDateTime end = LocalDateTime.parse(flight.getArrivalTime(), formatter);

            Duration duration = Duration.between(start, end);
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;
            holder.tv_timeFlight.setText(hours + "h " + minutes + "m");

        }


        holder.tv_departure.setText(flight.getDeparture());
        holder.tv_destination.setText(flight.getDestination());
        holder.tv_flightCode.setText(flight.getFlightCode());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FlightViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_departure;
        private TextView tv_destination;
        private TextView tv_timeFlight;
        private TextView tv_timeDeparture;
        private TextView tv_flightCode;

        public FlightViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_departure = itemView.findViewById(R.id.idTVDepartureFlight);
            tv_destination = itemView.findViewById(R.id.idTVDestinationFlight);
            tv_timeFlight = itemView.findViewById(R.id.idTVTimeFlight);
            tv_timeDeparture = itemView.findViewById(R.id.idTVDepartureTime);
            tv_flightCode = itemView.findViewById(R.id.idTVFlightCode);
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
