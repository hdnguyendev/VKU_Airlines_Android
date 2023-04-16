package hdn.dev.baseproject3.service;

import java.util.List;

import hdn.dev.baseproject3.models.LoginRequest;
import hdn.dev.baseproject3.models.LoginResponse;
import hdn.dev.baseproject3.models.RegisterRequest;
import hdn.dev.baseproject3.models.RegisterResponse;
import hdn.dev.baseproject3.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {
    String BASE_URL = "http://192.168.1.7:8099/api/v1/";

    @GET("users")
    Call<List<User>> getUsers();

    @POST("login")
    Call<LoginResponse> checkLogin(@Body LoginRequest request);

    @POST("register")
    Call<RegisterResponse> register(@Body RegisterRequest request);

}
