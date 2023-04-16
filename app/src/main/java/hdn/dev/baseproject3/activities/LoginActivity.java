package hdn.dev.baseproject3.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hdn.dev.baseproject3.R;
import hdn.dev.baseproject3.models.LoginRequest;
import hdn.dev.baseproject3.models.LoginResponse;
import hdn.dev.baseproject3.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private AppCompatButton btn_login;
    private EditText edt_username, edt_password;
    private TextView tv_register, tv_forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        SharedPreferences sharedPreferences = getSharedPreferences("checkLogin", Context.MODE_PRIVATE);
//        if (sharedPreferences.getString("login", "").equals("true")) {
//            // Người dùng đã đăng nhập
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        } else
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            initView();
            initController();
        }
    }

    private void initController() {
        // Login
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginRequest request = new LoginRequest(edt_username.getText().toString(), edt_password.getText().toString());
                Call<LoginResponse> call = RetrofitClient.getInstance().getMyApi().checkLogin(request);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse loginResponse = response.body();
                        if (loginResponse.getStatus().toString().equals("success")) {
                            finish();
                            SharedPreferences sharedPreferences = getSharedPreferences("checkLogin", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("login", "true");
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else if (loginResponse.getStatus().toString().equals("error")) {
                            edt_username.setError("Wrong username/password");
                            edt_password.setText("");
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        System.out.println("Login failure");
                    }
                });
            }
        });
        // Forget password
        tv_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Forget password", Toast.LENGTH_SHORT).show();
            }
        });
        // Register
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        btn_login = findViewById(R.id.idBtnLogin);
        edt_username = findViewById(R.id.idETUsername);
        edt_password = findViewById(R.id.idETPassword);
        tv_forget = findViewById(R.id.idTVForgetPass);
        tv_register = findViewById(R.id.idTVRegister);
    }
}