package hdn.dev.baseproject3.fragments;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.Calendar;

import hdn.dev.baseproject3.R;
import hdn.dev.baseproject3.models.User;
import hdn.dev.baseproject3.models.UserResponse;
import hdn.dev.baseproject3.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {
    private ImageView image_back;
    private EditText edt_year, edt_phone, edt_address, edt_email, edt_oldPassword, edt_newPassword, edt_fullname, edt_username;
    private AppCompatButton btn_update;
    protected ProgressDialog mProgressDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(String param1, String param2) {
        InfoFragment fragment = new InfoFragment();
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
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        image_back = view.findViewById(R.id.idIVBack);

        edt_fullname = view.findViewById(R.id.idETFullNameInfo);
        edt_username = view.findViewById(R.id.idETUsernameInfo);
        edt_phone = view.findViewById(R.id.idETPhoneRegister);
        edt_email = view.findViewById(R.id.idETEmail);
        edt_address = view.findViewById(R.id.idETAddress);
        edt_year = view.findViewById(R.id.idETYear);
        edt_oldPassword = view.findViewById(R.id.idETPasswordOld);
        edt_newPassword = view.findViewById(R.id.idETPasswordNew);

        btn_update = view.findViewById(R.id.idBtnUpdate);


        // Get data ...
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("user", "");
        // Chuyển đổi chuỗi JSON thành đối tượng User
        Gson gson = new Gson();
        User user = gson.fromJson(json, User.class);

        edt_fullname.setText(user.getFullname());
        edt_username.setText(user.getUsername());
        edt_phone.setText(user.getPhone());
        edt_address.setText(user.getAddress());
        edt_email.setText(user.getEmail());
        edt_year.setText(String.valueOf(user.getYear()));


        // Back to Before Fragment
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        // Pick year
        edt_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        (view1, year1, monthOfYear, dayOfMonth) -> edt_year.setText(String.valueOf(year1)),
                        year, 0, 1); // Lưu ý: tháng và ngày được thiết lập là 0 và 1 tương ứng để hiển thị một năm duy nhất

                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                datePickerDialog.getDatePicker().setSpinnersShown(true);
                datePickerDialog.show();
            }
        });
        // Update info user
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edt_oldPassword.getText().toString().equals(user.getPassword())) {
                    edt_oldPassword.setError("Wrong current password");
                    return;
                }
                user.setPhone(edt_phone.getText().toString());
                user.setEmail(edt_email.getText().toString());
                user.setAddress(edt_address.getText().toString());
                user.setYear((long) Integer.parseInt(edt_year.getText().toString()));
                user.setFullname(edt_fullname.getText().toString());
                user.setUsername(edt_username.getText().toString());
                if (!edt_newPassword.getText().toString().equals("")) {
                    user.setPassword(edt_newPassword.getText().toString());
                }
                System.out.println(user);
                mProgressDialog = ProgressDialog.show(getContext(), "Please wait", "Wait a few seconds", true);
                Call<UserResponse> call = RetrofitClient.getInstance().getMyApi().updateUser(user.getUserId(), user);
                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        UserResponse userResponse = response.body();
                        if (userResponse.getStatus().equals("success")) {
                            Log.i(TAG, "onResponse: " + userResponse.getMessage());
                            User user = userResponse.getData();
                            Gson gson = new Gson();
                            String json = gson.toJson(user);
                            System.out.println(json);
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("user", json);
                            editor.apply();
                            getFragmentManager().popBackStack();

                        } else {
                            Toast.makeText(getContext(), userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                        mProgressDialog.dismiss();
                    }
                });
            }
        });
    }
}