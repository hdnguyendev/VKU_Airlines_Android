package hdn.dev.baseproject3.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;

import hdn.dev.baseproject3.R;
import hdn.dev.baseproject3.models.Flight;
import hdn.dev.baseproject3.utils.FormatDay;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookFlightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookFlightFragment extends Fragment {
    private ImageView image_back;
    private TextView idTVDepartureFlight, idTVDestinationFlight, idTVTimeDeparture, idTVTimeDestination,idTVDateFlight,idTVTime,idTVMoney;
    private EditText idETClass,idETPerson,idETSeat;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookFlightFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookFlightFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookFlightFragment newInstance(String param1, String param2) {
        BookFlightFragment fragment = new BookFlightFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_flight, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        image_back = view.findViewById(R.id.idIVBack);

        idTVDestinationFlight = view.findViewById(R.id.idTVDestinationFlight);
        idTVTimeDeparture = view.findViewById(R.id.idTVTimeDeparture);
        idTVTimeDestination = view.findViewById(R.id.idTVTimeDestination);
        idTVDateFlight = view.findViewById(R.id.idTVDateFlight);
        idTVTime = view.findViewById(R.id.idTVTime);
        idTVMoney= view.findViewById(R.id.idTVMoney);
        idTVDepartureFlight = view.findViewById(R.id.idTVDepartureFlight);

        idETClass = view.findViewById(R.id.idETClass);
        idETPerson = view.findViewById(R.id.idETPerson);
        idETSeat= view.findViewById(R.id.idETSeat);


        Bundle bundle = getArguments();
        if (bundle != null) {
            String flight_json = bundle.getString("flight_data");
            Gson gson = new Gson();
            Flight flight = gson.fromJson(flight_json, Flight.class);
            setInfoTicket(flight);

        }


        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        idETSeat.setFocusableInTouchMode(true);
        idETSeat.setFocusable(true);
        idETSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPickSeatFragment();
            }
        });


    }

    private void goToPickSeatFragment() {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        PickSeatFragment newFragment = new PickSeatFragment();
        fragmentTransaction.replace(R.id.frame_container, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setInfoTicket(Flight data) {
        idTVDepartureFlight.setText(data.getDeparture());
        idTVDestinationFlight.setText(data.getDestination());
        idTVDateFlight.setText(FormatDay.formatDateWithoutTime(data.getArrivalTime()));
        idTVTimeDestination.setText(FormatDay.formatTime(data.getArrivalTime()));
        idTVTimeDeparture.setText(FormatDay.formatTime(data.getDepartureTime()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            idTVTime.setText(FormatDay.calculateTimeDifference(data.getDepartureTime(), data.getArrivalTime()));
        }
    }
}