package hdn.dev.baseproject3.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import hdn.dev.baseproject3.R;
import hdn.dev.baseproject3.models.Flight;
import hdn.dev.baseproject3.models.Ticket;
import hdn.dev.baseproject3.models.TicketRequest;
import hdn.dev.baseproject3.models.TicketResponse;
import hdn.dev.baseproject3.models.User;
import hdn.dev.baseproject3.retrofit.RetrofitClient;
import hdn.dev.baseproject3.utils.Alert;
import hdn.dev.baseproject3.utils.FormatDay;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookFlightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookFlightFragment extends Fragment {
    private ImageView image_back;

    private AppCompatButton idBtnPickSeat, idBtnBuyTicket;
    private TextView idTVDepartureFlight, idTVDestinationFlight, idTVTimeDeparture, idTVTimeDestination, idTVDateFlight, idTVTime, idTVMoney, idTVSeatSelected;
    private EditText idETClass, idETPerson;
    private LinearLayout idLayoutSeatSelected;

    Flight flight_current;
    List<Integer> list_seat_id = new ArrayList<Integer>();
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
    public void onResume() {
        super.onResume();


        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.idBottomNavView);
        bottomNavigationView.setVisibility(View.GONE);
        BottomAppBar bottomAppBar = getActivity().findViewById(R.id.idBottomAppBar);
        bottomAppBar.setVisibility(View.GONE);
        FloatingActionButton fab = getActivity().findViewById(R.id.idFABBookFlight);
        fab.setVisibility(View.GONE);
    }

    @Override
    public void onStop() {
        super.onStop();

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.idBottomNavView);
        bottomNavigationView.setVisibility(View.VISIBLE);
        BottomAppBar bottomAppBar = getActivity().findViewById(R.id.idBottomAppBar);
        bottomAppBar.setVisibility(View.VISIBLE);
        FloatingActionButton fab = getActivity().findViewById(R.id.idFABBookFlight);
        fab.setVisibility(View.VISIBLE);
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

        initView(view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String flight_json = bundle.getString("flight_data");
            Gson gson = new Gson();
            flight_current = gson.fromJson(flight_json, Flight.class);
            String seat_map = bundle.getString("seat_map");
            String total_amount = bundle.getString("total_amount");
            if (seat_map != null) {
                seat_map = seat_map.trim();
                String[] map = seat_map.split(";");
                int person = map.length;
                idETPerson.setText(String.valueOf(person));
                idLayoutSeatSelected.setVisibility(View.VISIBLE);
                seat_map = seat_map.replace(";", " ");
                idTVSeatSelected.setText(seat_map);
            }
            if (total_amount != null) {
                idTVMoney.setText("$" + total_amount);
            }
            list_seat_id = bundle.getIntegerArrayList("seat_id_list");

            setInfoTicket(flight_current);

        }


        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        // Pick seat
        idBtnPickSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPickSeatFragment();
            }
        });
        idBtnBuyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog mProgressDialog = ProgressDialog.show(getContext(), "Please wait", "Wait a few seconds", true);
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String user_String = sharedPreferences.getString("user", "");
                Gson gson = new Gson();
                User user_json = gson.fromJson(user_String, User.class);
                System.out.println(user_json.toString());
                Call<TicketResponse> call = RetrofitClient.getInstance().getMyApi().insertTicket(new TicketRequest(Long.valueOf(user_json.getUserId().toString()), flight_current.getFlightCode(), list_seat_id));
                call.enqueue(new Callback<TicketResponse>() {
                    @Override
                    public void onResponse(Call<TicketResponse> call, Response<TicketResponse> response) {
                        TicketResponse ticketResponse = response.body();
                        if (ticketResponse.getStatus().equals("success")) {
                            Ticket ticket = ticketResponse.getData();
                            Toast.makeText(getContext(), ticketResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            System.out.println(ticket);
                            mProgressDialog.dismiss();
                            try{
                                Thread.sleep(2000);
                                HistoryFragment newFragment = new HistoryFragment();
                                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.frame_container, newFragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();

                            }catch(InterruptedException e){
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getContext(), ticketResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            System.out.println(ticketResponse.getMessage());
                            mProgressDialog.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call<TicketResponse> call, Throwable t) {
                        mProgressDialog.dismiss();
                        new Alert(getContext(), "Hmm, some error occurred!\nPlease try again!", 0);
                        System.out.println("Insert ticket failure");
                        System.out.println(t);
                    }
                });
            }
        });

    }


    private void goToPickSeatFragment() {
        Bundle bundle = new Bundle();
        Gson gson = new Gson();
        String flight_Data = gson.toJson(flight_current);
        bundle.putString("flight_data", flight_Data);
        bundle.putInt("seat_limited", Integer.parseInt(idETPerson.getText().toString()));

        PickSeatFragment newFragment = new PickSeatFragment();
        newFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setInfoTicket(Flight data) {
        idTVDepartureFlight.setText(data.getDepartureSort());
        idTVDestinationFlight.setText(data.getDestinationSort());
        idTVDateFlight.setText(FormatDay.formatDateWithoutTime(data.getArrivalTime()));
        idTVTimeDestination.setText(FormatDay.formatTime(data.getArrivalTime()));
        idTVTimeDeparture.setText(FormatDay.formatTime(data.getDepartureTime()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            idTVTime.setText(FormatDay.calculateTimeDifference(data.getDepartureTime(), data.getArrivalTime()));
        }
    }

    private void initView(View view) {

        image_back = view.findViewById(R.id.idIVBack);

        idTVDestinationFlight = view.findViewById(R.id.idTVDestinationFlight);
        idTVTimeDeparture = view.findViewById(R.id.idTVTimeDeparture);
        idTVTimeDestination = view.findViewById(R.id.idTVTimeDestination);
        idTVDateFlight = view.findViewById(R.id.idTVDateFlight);
        idTVTime = view.findViewById(R.id.idTVTime);
        idTVMoney = view.findViewById(R.id.idTVMoney);
        idTVDepartureFlight = view.findViewById(R.id.idTVDepartureFlight);
        idTVSeatSelected = view.findViewById(R.id.idTVSeatSelected);
        idETClass = view.findViewById(R.id.idETClass);
        idETPerson = view.findViewById(R.id.idETPerson);
        idBtnPickSeat = view.findViewById(R.id.idBtnPickSeat);
        idLayoutSeatSelected = view.findViewById(R.id.idLayoutSeatSelected);
        idBtnBuyTicket = view.findViewById(R.id.idBtnBuyTicket);
    }
}