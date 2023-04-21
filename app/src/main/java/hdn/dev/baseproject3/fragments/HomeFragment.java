package hdn.dev.baseproject3.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hdn.dev.baseproject3.R;
import hdn.dev.baseproject3.adapter.FlightsRVAdapter;
import hdn.dev.baseproject3.models.Flight;
import hdn.dev.baseproject3.models.FlightResponse;
import hdn.dev.baseproject3.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private RecyclerView notifyRV;
    private TextInputEditText inputDateDeparture, inputDateReturn;
    AppCompatButton btn_search;
//    int firstVisibleItemPosition = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notifyRV = view.findViewById(R.id.idRVNotify);
        inputDateDeparture = view.findViewById(R.id.idTextInputDateDeparture);
        inputDateReturn = view.findViewById(R.id.idTextInputDateReturn);
        btn_search = view.findViewById(R.id.idBtnSearchFlight);
        // Lấy ngày hiện tại truyền vào param => lấy những chuyến bay sau thời gian hiện tại
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("date",currentDateTime);

        Call<FlightResponse> call = RetrofitClient.getInstance().getMyApi().getFights(queryParams);
        call.enqueue(new Callback<FlightResponse>() {
            @Override
            public void onResponse(Call<FlightResponse> call, Response<FlightResponse> response) {
                FlightResponse flightResponse = response.body();
                if (flightResponse.getStatus().equals("success")) {
                    ProgressDialog dialog = new ProgressDialog(getContext());

                    dialog.setMessage("Loading ....");
                    dialog.setCancelable(false);
                    dialog.show();
                    // Get data ...
                    // Flight RecycleView
                    List<Flight> list = flightResponse.getData();
                    FlightsRVAdapter adapter = new FlightsRVAdapter(getContext(), list);
                    notifyRV.setAdapter(adapter);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    notifyRV.setLayoutManager(layoutManager);
                    adapter.setOnItemClickListener(new FlightsRVAdapter.OnItemClickListener() {
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
                    dialog.dismiss();
//                    notifyRV.setOnTouchListener(new View.OnTouchListener() {
//                        private float dx;
//                        private boolean isScrolling = false;
//
//                        @Override
//                        public boolean onTouch(View v, MotionEvent event) {
//                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                                dx = event.getX();
//                                isScrolling = false;
//                                return true;
//                            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                                float deltaX = event.getX() - dx;
//                                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
//                                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
//                                int visibleItemCount = lastVisibleItemPosition - firstVisibleItemPosition + 1;
//                                int totalItemCount = adapter.getItemCount();
//
//                                if (Math.abs(deltaX) > 10 && !isScrolling) {
//                                    isScrolling = true;
//                                    if (deltaX < 0 && (firstVisibleItemPosition + visibleItemCount) < totalItemCount) {
//                                        notifyRV.smoothScrollToPosition(firstVisibleItemPosition + 1);
//                                    } else if (deltaX > 0 && firstVisibleItemPosition > 0) {
//                                        notifyRV.smoothScrollToPosition(firstVisibleItemPosition - 1);
//                                    }
//                                }
//                                return true;
//                            } else if (event.getAction() == MotionEvent.ACTION_UP) {
//                                isScrolling = false;
//                                return true;
//                            }
//                            return false;
//                        }
//                    });

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

        // Pick day
        inputDateDeparture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy ngày và tháng hiện tại
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // Tạo DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view12, year12, month12, dayOfMonth12) -> {
                    // Xử lý khi người dùng chọn ngày
                    String date = dayOfMonth12 + "/" + (month12 + 1) + "/" + year12;
                    inputDateDeparture.setText(date);
                }, year, month, dayOfMonth);

                // Hiển thị DatePickerDialog
                datePickerDialog.show();
            }
        });
        inputDateReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy ngày và tháng hiện tại
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // Tạo DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view1, year1, month1, dayOfMonth1) -> {
                    // Xử lý khi người dùng chọn ngày
                    String date = dayOfMonth1 + "/" + (month1 + 1) + "/" + year1;
                    inputDateReturn.setText(date);
                }, year, month, dayOfMonth);

                // Hiển thị DatePickerDialog
                datePickerDialog.show();
            }
        });
    }
}