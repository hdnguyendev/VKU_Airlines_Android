package hdn.dev.baseproject3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hdn.dev.baseproject3.R;
import hdn.dev.baseproject3.models.Flight;
import hdn.dev.baseproject3.utils.CovertCityName;
import hdn.dev.baseproject3.utils.FormatDay;

public class SearchListRVAdapter extends RecyclerView.Adapter<SearchListRVAdapter.SearchListViewHolder> {
    Context context;
    List<Flight> list;
    OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(Flight data);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public SearchListRVAdapter(Context context, List<Flight> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SearchListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_flight_search, parent, false);
        return new SearchListRVAdapter.SearchListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchListViewHolder holder, int position) {
        Flight flight = list.get(position);
        flight.setDeparture(CovertCityName.convertCityName(flight.getDeparture()));
        flight.setDestination(CovertCityName.convertCityName(flight. getDestination()));
        holder.idTVFlightNameSearch.setText(flight.getFlightName());
                holder.idTVFlightCodeSearch.setText(flight.getFlightCode());
        holder.idTVDepartureFlightSearch.setText(flight.getDeparture());
                holder.idTVDestinationFlightSearch.setText(flight.getDestination());
        holder.idTVTimeDepartureSearch.setText(FormatDay.formatTime(flight.getDepartureTime()));
                holder.idTVTimeDestinationSearch.setText(FormatDay.formatTime(flight.getArrivalTime()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SearchListViewHolder extends RecyclerView.ViewHolder {
        private TextView idTVFlightNameSearch, idTVFlightCodeSearch, idTVDepartureFlightSearch, idTVDestinationFlightSearch, idTVTimeDepartureSearch, idTVTimeDestinationSearch;

        public SearchListViewHolder(@NonNull View itemView) {
            super(itemView);
            idTVFlightNameSearch = itemView.findViewById(R.id.idTVFlightNameSearch);
            idTVFlightCodeSearch = itemView.findViewById(R.id.idTVFlightCodeSearch);

            idTVDepartureFlightSearch = itemView.findViewById(R.id.idTVDepartureFlightSearch);
            idTVDestinationFlightSearch = itemView.findViewById(R.id.idTVDestinationFlightSearch);

            idTVTimeDepartureSearch = itemView.findViewById(R.id.idTVTimeDepartureSearch);
            idTVTimeDestinationSearch = itemView.findViewById(R.id.idTVTimeDestinationSearch);


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
