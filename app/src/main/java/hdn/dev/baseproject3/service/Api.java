package hdn.dev.baseproject3.service;

import java.util.List;

import hdn.dev.baseproject3.models.LoginRequest;
import hdn.dev.baseproject3.models.LoginResponse;
import hdn.dev.baseproject3.models.FlightResponse;
import hdn.dev.baseproject3.models.RegisterRequest;
import hdn.dev.baseproject3.models.RegisterResponse;
import hdn.dev.baseproject3.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {
    String BASE_URL = "http://192.168.1.6:8080/api/v1/";

    @GET("users")
    Call<List<User>> getUsers();

    @POST("login")
    Call<LoginResponse> checkLogin(@Body LoginRequest request);

    @POST("register")
    Call<RegisterResponse> register(@Body RegisterRequest request);

    @GET("flights")
    Call<FlightResponse> getFights();
}

//
//Connection name: Wi-Fi
//        State: Connected
//        Type: Wireless LAN
//        DNS Suffix:
//        IPv4 address: 192.168.1.6
//        IPv4 subnet mask: 255.255.255.0
//        IPv6 link-local address:
//        fe80::b650:87d5:e877:e4c6%6
//        Default Gateway:
//        192.168.1.1
//        DHCP servers:
//        192.168.1.1
//        DNS servers:
//        123.23.23.23
//        123.26.26.26
//        WINS servers:
//        192.168.1.1
//
//        Adapter name: Intel(R) Wi-Fi 6 AX201 160MHz
//        Physical address (MAC): 9843FA30A903
//        Speed: 162 Mbps