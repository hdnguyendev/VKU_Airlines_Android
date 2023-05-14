package hdn.dev.baseproject3.retrofit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitGPT {
    private static Retrofit instance;
    public static Retrofit getInstance() {
        if (instance == null)
        {
            instance = new Retrofit.Builder()
                    .baseUrl("https://api.openai.com/v1/")
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }
}
