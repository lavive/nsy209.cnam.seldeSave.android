package nsy209.cnam.seldesave.background.webService;


import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by lavive on 07/07/2017.
 */

public class RetrofitBuilder {
    private static final String BASE_URL = "http://XX.XXX.XXX.XX:XXXX/selServices/";

    public static WebService getClient(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(MoshiConverterFactory.create()).build();

        WebService webService = retrofit.create(WebService.class);

        return webService;
    }
}