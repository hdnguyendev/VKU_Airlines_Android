package hdn.dev.baseproject3.fragments;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hdn.dev.baseproject3.R;
import hdn.dev.baseproject3.adapter.SearchListRVAdapter;
import hdn.dev.baseproject3.models.Flight;
import hdn.dev.baseproject3.models.FlightResponse;
import hdn.dev.baseproject3.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private RecyclerView idRVSearchFlight;
    protected ProgressDialog mProgressDialog;

    ImageView iconBack, iconSearch;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        callApi();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void callApi() {
        Bundle bundle = getArguments();

        mProgressDialog = ProgressDialog.show(getContext(), "Please wait", "Wait a few seconds", true);
        // Lấy ngày hiện tại truyền vào param => lấy những chuyến bay sau thời gian hiện tại
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date", currentDateTime);
        if (bundle != null) {
            if (bundle.getString("departure") != null) {
                queryParams.put("departure", bundle.getString("departure"));

            }
            if (bundle.getString("destination") != null) {
                queryParams.put("destination", bundle.getString("destination"));

            }
            if (bundle.getString("time_departure") != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                LocalDate date = LocalDate.parse(bundle.getString("time_departure"), formatter);
                String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                queryParams.put("time_departure", formattedDate);

            }
            if (bundle.getString("time_destination") != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                LocalDate date = LocalDate.parse(bundle.getString("time_destination"), formatter);
                String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                queryParams.put("time_destination", formattedDate);

            }
            if (bundle.getString("person") != null) {
                queryParams.put("person", bundle.getString("person"));

            }

        }
        System.out.println(queryParams);
        Call<FlightResponse> call = RetrofitClient.getInstance().getMyApi().getFights(queryParams);
        call.enqueue(new Callback<FlightResponse>() {
            @Override
            public void onResponse(Call<FlightResponse> call, Response<FlightResponse> response) {
                FlightResponse flightResponse = response.body();
                if (flightResponse.getStatus().equals("success")) {
                    // Search Flights RecycleView
                    List<Flight> list = flightResponse.getData();
                    SearchListRVAdapter adapter = new SearchListRVAdapter(getContext(), list);
                    idRVSearchFlight.setAdapter(adapter);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    idRVSearchFlight.setLayoutManager(layoutManager);
                    adapter.setOnItemClickListener(data -> {
                        Bundle bundle = new Bundle();
                        Gson gson = new Gson();
                        String json = gson.toJson(data);

                        bundle.putString("flight_data", json);

                        BookFlightFragment bookFlightFragment = new BookFlightFragment();
                        bookFlightFragment.setArguments(bundle);

                        // Thay thế Fragment hiện tại bằng một Fragment mới
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame_container, bookFlightFragment).addToBackStack(null).commit();
                    });
                } else if (flightResponse.getStatus().equals("error")) {
                    Toast.makeText(getContext(), flightResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                mProgressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<FlightResponse> call, Throwable t) {
                mProgressDialog.dismiss();
                System.out.println("Get flights failure");
                System.out.println(t);
            }
        });



        iconBack.setOnClickListener(view -> {
            getFragmentManager().popBackStack();
        });

    }

    private void initView(View view) {
        idRVSearchFlight = view.findViewById(R.id.idRVSearchFlight);
        iconSearch = view.findViewById(R.id.iconSearch);
        iconBack = view.findViewById(R.id.iconBack);
    }
}