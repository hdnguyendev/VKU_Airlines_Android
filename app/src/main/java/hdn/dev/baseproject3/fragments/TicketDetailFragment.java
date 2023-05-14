package hdn.dev.baseproject3.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.FileOutputStream;

import hdn.dev.baseproject3.R;
import hdn.dev.baseproject3.models.TicketDetail;
import hdn.dev.baseproject3.models.TicketDetailResponse;
import hdn.dev.baseproject3.retrofit.RetrofitClient;
import hdn.dev.baseproject3.utils.FormatDay;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TicketDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TicketDetailFragment extends Fragment {

    ImageView idIVBarcode,idIVCancelButton;
    TextView idTVFlightCodeTicket, idTVSeatTicket, idTVDateTicket, idTVTimeTicket, idIVDepartureSort, idTVDestinationSort, idTVDeparture, idTVDestination, idTVNamePassenger, idTVTotalAmountTicket;

    AppCompatButton idBtnDownload;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TicketDetailFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TicketDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TicketDetailFragment newInstance(String param1, String param2) {
        TicketDetailFragment fragment = new TicketDetailFragment();
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ticket_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initController();

    }

    private void initController() {
        Bundle bundle = getArguments();
        assert bundle != null;
        Long ticketId = bundle.getLong("ticket_id");

        Call<TicketDetailResponse> ticketResponse = RetrofitClient.getInstance().getMyApi().getTicketDetail(ticketId);
        ticketResponse.enqueue(new Callback<TicketDetailResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<TicketDetailResponse> call, Response<TicketDetailResponse> response) {
                TicketDetailResponse ticketDetailResponse = response.body();
                if (ticketDetailResponse.getStatus().equals("success")) {
                    TicketDetail ticketDetail = ticketDetailResponse.getData();
                    idTVFlightCodeTicket.setText(ticketDetail.getFlight().getFlightCode());
                    generateBarcode();
                    idTVSeatTicket.setText(ticketDetail.getTicket().getList_seat().replace(';',' '));
                    idTVDateTicket.setText(FormatDay.formatDateTicketDetail(ticketDetail.getFlight().getArrivalTime()));
                    idTVTimeTicket.setText(FormatDay.formatTimeTicketDetail(ticketDetail.getFlight().getDepartureTime(),ticketDetail.getFlight().getArrivalTime()));
                    idIVDepartureSort.setText(ticketDetail.getFlight().getDepartureSort());
                    idTVDestinationSort.setText(ticketDetail.getFlight().getDestinationSort());
                    idTVDeparture.setText(ticketDetail.getFlight().getDeparture());
                    idTVDestination.setText(ticketDetail.getFlight().getDestination());
                    idTVNamePassenger.setText(ticketDetail.getUser().getFullname());
                    idTVTotalAmountTicket.setText("$"+ ticketDetail.getTicket().getTotalAmount());
                } else {
                    Toast.makeText(getContext(), ticketDetailResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TicketDetailResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        idBtnDownload.setOnClickListener(view -> {
//            captureAndSaveScreenshot();
        });
        idIVCancelButton.setOnClickListener(view -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.popBackStack();

        });
    }

    private void captureAndSaveScreenshot() {
        // Tạo một đối tượng Bitmap từ nội dung của Fragment hiện tại
        View fragmentView = getView();
        fragmentView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(fragmentView.getDrawingCache());
        fragmentView.setDrawingCacheEnabled(false);

        // Tạo đường dẫn cho tệp tin hình ảnh
        String fileName = "fragment_image.png";
        File fileDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(fileDir, fileName);

// Lưu Bitmap vào tệp tin
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateBarcode() {
        String content = "";
        content = content.concat(idTVFlightCodeTicket.getText().toString());
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.CODE_128, 300, 70);
            Bitmap bitmap = Bitmap.createBitmap(300, 70, Bitmap.Config.RGB_565);
            for (int i = 0; i < 300; i++) {
                for (int j = 0; j < 70; j++) {
                    bitmap.setPixel(i, j, bitMatrix.get(i, j) ? Color.BLACK : Color.WHITE);
                }
            }
            idIVBarcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void initView(View view) {
        idTVFlightCodeTicket = view.findViewById(R.id.idTVFlightCodeTicket);
        idTVSeatTicket = view.findViewById(R.id.idTVSeatTicket);
        idTVDateTicket = view.findViewById(R.id.idTVDateTicket);
        idTVTimeTicket = view.findViewById(R.id.idTVTimeTicket);
        idIVDepartureSort = view.findViewById(R.id.idIVDepartureSort);
        idTVDestinationSort = view.findViewById(R.id.idTVDestinationSort);
        idTVDeparture = view.findViewById(R.id.idTVDeparture);
        idTVDestination = view.findViewById(R.id.idTVDestination);
        idTVNamePassenger = view.findViewById(R.id.idTVNamePassenger);
        idIVBarcode = view.findViewById(R.id.idIVBarcode);
        idTVTotalAmountTicket = view.findViewById(R.id.idTVTotalAmountTicket);
        idBtnDownload = view.findViewById(R.id.idBtnDownload);
        idIVCancelButton = view.findViewById(R.id.idIVCancelButton);
    }
}