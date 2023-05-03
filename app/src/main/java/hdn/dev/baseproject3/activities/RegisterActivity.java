package hdn.dev.baseproject3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import hdn.dev.baseproject3.R;
import hdn.dev.baseproject3.models.RegisterRequest;
import hdn.dev.baseproject3.models.RegisterResponse;
import hdn.dev.baseproject3.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private AppCompatButton btn_register;
    private EditText edt_fullName, edt_username, edt_phone, edt_password, edt_password_confirm;
    private TextView tv_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initController();
    }

    private void initView() {
        btn_register = findViewById(R.id.idBtnRegister);
        edt_fullName = findViewById(R.id.idETFullNameRegister);
        edt_username = findViewById(R.id.idETUsernameRegister);
        edt_phone = findViewById(R.id.idETPhoneRegister);
        edt_password = findViewById(R.id.idETPasswordRegister);
        edt_password_confirm = findViewById(R.id.idETPasswordConfirmRegister);
        tv_login = findViewById(R.id.idTVLogin);
    }

    private void initController() {
        edt_password_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
// Kiểm tra xem giá trị nhập vào có khớp với giá trị của trường "password" không
                if (!s.toString().equals(edt_password.getText().toString())) {
                    // Hiển thị thông báo lỗi
                    edt_password_confirm.setError("Confirmation password does not match");
                } else {
                    // Xóa thông báo lỗi (nếu có)
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = edt_fullName.getText().toString();
                String username = edt_username.getText().toString();
                String phone = edt_phone.getText().toString();
                String password = edt_password.getText().toString();
                RegisterRequest request = new RegisterRequest(fullName, username, phone, password);
                Call<RegisterResponse> call = RetrofitClient.getInstance().getMyApi().register(request);
                call.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        RegisterResponse registerResponse = response.body();
                        if (registerResponse.getStatus().equals("success")) {
                            finish();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            edt_username.setError(registerResponse.getMessage());
                            edt_password.setText("");
                            edt_password_confirm.setText("");
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        System.out.println("Register failure");
                    }
                });
            }
        });
    }
}