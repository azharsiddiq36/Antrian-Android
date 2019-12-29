package digtive.antrian.Rest;

public class CombineApi {

    public static final String BASE_URL = "http://192.168.43.201/antrian/";
//    public static final String img_url = "http://192.168.43.201/ci/madrep/";
    public static QueueInterface getApiService(){
        return ApiClient.getApiClient(BASE_URL).create(QueueInterface.class);
    }
}