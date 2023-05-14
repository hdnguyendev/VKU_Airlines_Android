package hdn.dev.baseproject3.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hdn.dev.baseproject3.R;
import hdn.dev.baseproject3.adapter.ChatGPTAdapter;
import hdn.dev.baseproject3.models.MessParamPost;
import hdn.dev.baseproject3.models.Message;
import hdn.dev.baseproject3.retrofit.RetrofitGPT;
import hdn.dev.baseproject3.service.ApiGPT;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    RecyclerView recyclerView_chat;
    EditText edt_chat;
    ImageButton btn_send;
    List<Message> messageList;
    ChatGPTAdapter adapter;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiGPT apiGpt;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
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
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiGpt = RetrofitGPT.getInstance().create(ApiGPT.class);
        messageList = new ArrayList<>();
        adapter = new ChatGPTAdapter(getContext(), messageList);

        initView(view);
        initControl();
    }
    public void postData(String ques) {
        messageList.add(new Message("Typing ....", 1));

        MessParamPost messParamPost = new MessParamPost();
        messParamPost.setModel("text-davinci-003");
        messParamPost.setMax_tokens(2048);
        messParamPost.setPrompt(ques);
        messParamPost.setTemperature(0);
        compositeDisposable.add(apiGpt.postQues(messParamPost)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messResponse -> {
                            messageList.remove(messageList.size()-1);
                            String result = messResponse.getChoices().get(0).getText();
                            addQuestionToRecycle(result, 1);

                        },
                        throwable -> {
                            Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void initControl() {
        btn_send.setOnClickListener(view -> {
            String ques = edt_chat.getText().toString().trim();
            addQuestionToRecycle(ques, 0);
            edt_chat.setText("");
            postData(ques);
        });
    }

    private void addQuestionToRecycle(String ques, int send_id) {
        messageList.add(new Message(ques, send_id));
        adapter.notifyDataSetChanged();
        recyclerView_chat.smoothScrollToPosition(adapter.getItemCount());

    }

    private void initView(View view) {
        recyclerView_chat = view.findViewById(R.id.recycleView_chat);
        edt_chat = view.findViewById(R.id.edt_mess);
        btn_send = view.findViewById(R.id.btn_send);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        recyclerView_chat.setLayoutManager(layoutManager);
        recyclerView_chat.setAdapter(adapter);
    }

}