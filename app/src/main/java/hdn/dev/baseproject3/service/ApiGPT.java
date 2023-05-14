package hdn.dev.baseproject3.service;


import hdn.dev.baseproject3.models.MessParamPost;
import hdn.dev.baseproject3.models.MessResponse;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiGPT {
    @Headers({"Content-Type: application/json","Authorization: Bearer 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"})
    @POST("completions")
    Observable<MessResponse> postQues(
            @Body MessParamPost messParamPost
    );
}
