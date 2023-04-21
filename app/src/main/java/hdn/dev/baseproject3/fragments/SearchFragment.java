package hdn.dev.baseproject3.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        Call<FlightResponse> call = RetrofitClient.getInstance().getMyApi().getFights(new HashMap<>());
        call.enqueue(new Callback<FlightResponse>() {
            @Override
            public void onResponse(Call<FlightResponse> call, Response<FlightResponse> response) {
                FlightResponse flightResponse = response.body();
                if (flightResponse.getStatus().equals("success")) {
                    ProgressDialog dialog = new ProgressDialog(getContext());
                    // Search Flights RecycleView
                    List<Flight> list = flightResponse.getData();
                    SearchListRVAdapter adapter = new SearchListRVAdapter(getContext(), list);
                    idRVSearchFlight.setAdapter(adapter);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    idRVSearchFlight.setLayoutManager(layoutManager);
                    adapter.setOnItemClickListener(new SearchListRVAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Flight data) {
                            Bundle bundle = new Bundle();
                            Gson gson = new Gson();
                            String json = gson.toJson(data);

                            bundle.putString("flight_data", json);

                            BookFlightFragment bookFlightFragment = new BookFlightFragment();
                            bookFlightFragment.setArguments(bundle);


                            // Thay thế Fragment hiện tại bằng một Fragment mới
                            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.frame_container, bookFlightFragment).addToBackStack(null).commit();
                        }
                    });
                } else if (flightResponse.getStatus().equals("error")) {
                    Toast.makeText(getContext(), "Có lỗi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FlightResponse> call, Throwable t) {
                System.out.println("Get flights failure");
                System.out.println(t);
            }
        });

    }

    private void initView(View view) {
        idRVSearchFlight = view.findViewById(R.id.idRVSearchFlight);

    }
}