package hdn.dev.baseproject3.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hdn.dev.baseproject3.R;
import hdn.dev.baseproject3.adapter.HistoryRVAdapter;
import hdn.dev.baseproject3.models.Ticket;
import hdn.dev.baseproject3.models.User;
import hdn.dev.baseproject3.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    RecyclerView idRVHistory;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initControl();
    }

    private void initControl() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("user", "");
        // Chuyển đổi chuỗi JSON thành đối tượng User
        Gson gson = new Gson();
        User user = gson.fromJson(json, User.class);

        ProgressDialog mProgressDialog = ProgressDialog.show(getContext(), "Please wait", "Wait a few seconds", true);
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("user_id", String.valueOf(user.getUserId()));
        Call<List<Ticket>> call = RetrofitClient.getInstance().getMyApi().getTickets(queryParams);
        call.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                List<Ticket> list = response.body();
                HistoryRVAdapter adapter = new HistoryRVAdapter(getContext(), list);
                idRVHistory.setAdapter(adapter);
                adapter.setOnItemClickListener(data -> {
                    Bundle bundle = new Bundle();
                    Gson gson = new Gson();
                    String json = gson.toJson(data);

                    bundle.putString("flight_data", json);

                    TicketDetailFragment ticketDetailFragment = new TicketDetailFragment();
                    ticketDetailFragment


                            .setArguments(bundle);

                    // Thay thế Fragment hiện tại bằng một Fragment mới
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_container, ticketDetailFragment
                    ).addToBackStack(null).commit();
                });

                mProgressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
                mProgressDialog.dismiss();
                System.out.println("Get flights failure");
                System.out.println(t);
            }
        });

    }

    private void initView(View view) {
        idRVHistory = view.findViewById(R.id.idRVHistoryBooking);
    }
}