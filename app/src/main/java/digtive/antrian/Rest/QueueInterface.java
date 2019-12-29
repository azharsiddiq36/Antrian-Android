package digtive.antrian.Rest;

import digtive.antrian.Model.QueueResponse;
import digtive.antrian.Model.Services;
import digtive.antrian.Model.ServicesResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface QueueInterface {
    @GET("api/queue/layanan")
    Call<ServicesResponse> getListServices();

    @FormUrlEncoded
    @POST("api/queue/antrian")
    Call<QueueResponse> getQueueList(@Field("layanan_id") String services_id);
    @FormUrlEncoded
    @POST("api/queue/call")
    Call<QueueResponse> doCall(@Field("layanan_id") String services_id);
    @FormUrlEncoded
    @POST("api/queue/recall")
    Call<QueueResponse> doRecall(@Field("layanan_id") String services_id);
}
