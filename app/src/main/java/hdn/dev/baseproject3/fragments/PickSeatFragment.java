package hdn.dev.baseproject3.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import hdn.dev.baseproject3.R;
import hdn.dev.baseproject3.adapter.SeatRVAdapter;
import hdn.dev.baseproject3.models.Flight;
import hdn.dev.baseproject3.models.Seat;
import hdn.dev.baseproject3.models.SeatResponse;
import hdn.dev.baseproject3.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PickSeatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PickSeatFragment extends Fragment {
    private RecyclerView rvA, rvB, rvC, rvD;
    private TextView idTVSubTitle, idTVSeatSelected, idTVTotalAmount;
    private AppCompatButton btn_confirm;
    private ImageView ic_back;

    SeatRVAdapter adapterA, adapterB, adapterC, adapterD;
    List<Seat> seatA, seatB, seatC, seatD;
    int seat_limited;
    Flight flight_data;

    List<String> list_seat_selected = new ArrayList<String>();
    List<Integer> list_seat_id = new ArrayList<Integer>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PickSeatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PickSeatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PickSeatFragment newInstance(String param1, String param2) {
        PickSeatFragment fragment = new PickSeatFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pick_seat, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initController();

    }

    private void initController() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            Gson gson = new Gson();

            flight_data = gson.fromJson(bundle.getString("flight_data"), Flight.class);
            seat_limited = bundle.getInt("seat_limited");
        }
        idTVSeatSelected.setText(seat_limited + "");
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    fragmentManager.popBackStack();


            }
        });
        callApi();
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.getFragments().remove(new BookFlightFragment());;
                goToBookFlight();

            }
        });
    }
    private void goToBookFlight() {
        String list_string = "";
        for (String seat : list_seat_selected) {
            list_string = list_string.concat(seat+ ";");
        }
        Bundle bundle = new Bundle();
        bundle.putString("seat_map", list_string);
        Gson gson   = new Gson();
        String flight_Data = gson.toJson(flight_data);
        bundle.putString("flight_data", flight_Data);
        bundle.putIntegerArrayList("seat_id_list", (ArrayList<Integer>) list_seat_id);
        bundle.putString("total_amount", (idTVTotalAmount.getText().toString()));
        BookFlightFragment newFragment = new BookFlightFragment();
        newFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }


    private void callApi() {
        if (flight_data == null) {
            return;
        }
        Call<SeatResponse> call = RetrofitClient.getInstance().getMyApi().getSeatsByFlightCode(flight_data.getFlightCode());
        call.enqueue(new Callback<SeatResponse>() {
            @Override
            public void onResponse(Call<SeatResponse> call, Response<SeatResponse> response) {
                SeatResponse seats = response.body();
                if (seats.getStatus().equals("success")) {
                    List<Seat> list = seats.getData();
                    Comparator<Seat> seatNameComparator = new Comparator<Seat>() {
                        @Override
                        public int compare(Seat seat1, Seat seat2) {
                            String seatName1 = seat1.getSeatName();
                            String seatName2 = seat2.getSeatName();
                            String[] parts1 = seatName1.split("(?<=\\D)(?=\\d)"); // Tách chuỗi thành phần chữ cái và phần số
                            String[] parts2 = seatName2.split("(?<=\\D)(?=\\d)");
                            int result = parts1[0].compareTo(parts2[0]); // So sánh phần chữ cái đầu tiên
                            if (result == 0) {
                                // Nếu phần chữ cái bằng nhau, so sánh phần số
                                Integer number1 = Integer.parseInt(parts1[1]);
                                Integer number2 = Integer.parseInt(parts2[1]);
                                result = number1.compareTo(number2);
                            }
                            return result;
                        }
                    };
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        seatA = list.stream().filter(seat -> seat.getSeatName().charAt(0) == 'A').collect(Collectors.toList());
                        seatB = list.stream().filter(seat -> seat.getSeatName().charAt(0) == 'B').collect(Collectors.toList());
                        seatC = list.stream().filter(seat -> seat.getSeatName().charAt(0) == 'C').collect(Collectors.toList());
                        seatD = list.stream().filter(seat -> seat.getSeatName().charAt(0) == 'D').collect(Collectors.toList());
                        Collections.sort(seatA, seatNameComparator);
                        Collections.sort(seatB, seatNameComparator);
                        Collections.sort(seatC, seatNameComparator);
                        Collections.sort(seatD, seatNameComparator);
                        setUpRecycleView();
                    }
                } else {
                    System.out.println(seats.getMessage());
                }
            }

            @Override
            public void onFailure(Call<SeatResponse> call, Throwable t) {
                System.out.println("Get seats failure");
                System.out.println(t);
            }
        });

    }

    private void setUpRecycleView() {
        // A
        adapterA = new SeatRVAdapter(getContext(), seatA, this);
        rvA.setAdapter(adapterA);
        LinearLayoutManager layoutManagerA = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvA.setLayoutManager(layoutManagerA);
        // B
        adapterB = new SeatRVAdapter(getContext(), seatB, this);
        rvB.setAdapter(adapterB);
        LinearLayoutManager layoutManagerB = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvB.setLayoutManager(layoutManagerB);
        // C
        adapterC = new SeatRVAdapter(getContext(), seatC, this);
        rvC.setAdapter(adapterC);
        LinearLayoutManager layoutManagerC = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvC.setLayoutManager(layoutManagerC);
        // D
        adapterD = new SeatRVAdapter(getContext(), seatD, this);
        rvD.setAdapter(adapterD);
        LinearLayoutManager layoutManagerD = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvD.setLayoutManager(layoutManagerD);
    }

    public void addSeatSelected(int seatSelected, String seat_name, int seat_id) {
        int old = Integer.parseInt(idTVSubTitle.getText().toString());
        if (old == seat_limited) return;
        list_seat_selected.add(seat_name);
        list_seat_id.add(seat_id);
        idTVSubTitle.setText(String.valueOf(old + seatSelected));
    }

    public void subSeatSelected(int seatSelected, String seat_name, int seat_id) {
        int old = Integer.parseInt(idTVSubTitle.getText().toString());
        if (old <= 0) return;
        list_seat_selected.remove(seat_name);
        idTVSubTitle.setText(String.valueOf(old - seatSelected));
    }

    public void addMoney(double money) {
        double old = Double.parseDouble(idTVTotalAmount.getText().toString());
        idTVTotalAmount.setText(String.valueOf(old + money));
    }

    public void subMoney(double money) {
        double old = Double.parseDouble(idTVTotalAmount.getText().toString());
        idTVTotalAmount.setText(String.valueOf(old - money));
    }

    public boolean seatSelectedEnough() {
        int old = Integer.parseInt(idTVSubTitle.getText().toString());
        if (old == seat_limited) {
            return true;
        }
        return false;
    }
    private void initView(View view) {
        rvA = view.findViewById(R.id.rvA);
        rvB = view.findViewById(R.id.rvB);
        rvC = view.findViewById(R.id.rvC);
        rvD = view.findViewById(R.id.rvD);
        idTVSubTitle = view.findViewById(R.id.idTVSubTitle);
        idTVSeatSelected = view.findViewById(R.id.idTVSeatSelected);
        ic_back = view.findViewById(R.id.idIVBack);
        btn_confirm = view.findViewById(R.id.idBtnConfirm);
        idTVTotalAmount = view.findViewById(R.id.idTVTotalAmount);

    }
}
