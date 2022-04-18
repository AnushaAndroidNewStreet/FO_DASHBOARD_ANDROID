package com.example.fodashboard.common;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    private static long timeout = 80000;
    //180_000L



    private final static String BASE_URL = "https://dev-api-collection.newstreettech.com/";







    public static ApiClient apiClient;
    private Retrofit retrofit = null;
    private Retrofit retrofit2 = null;
    SharedPreferences sharedPreferences;
    String token_val, token_type;
    Boolean isloggedin = false;
    String val;
    Request request;
    Gson gson;

    public static ApiClient getInstance() {
        if (apiClient == null) {
            apiClient = new ApiClient();
        }
        return apiClient;
    }

    public Retrofit getClient() {
        return getClient(null);
    }



    private Retrofit getClient(final Context context) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(timeout, TimeUnit.MILLISECONDS) // connect timeout
                .writeTimeout(timeout, TimeUnit.MILLISECONDS) // write timeout
                .readTimeout(timeout, TimeUnit.MILLISECONDS);

        httpClient.addInterceptor(logging);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                if (isloggedin) {
                 request = original.newBuilder()
                              .header("Authorization",val)

                           // .header("TokenValue", token_val)
                            .method(original.method(), original.body())
                            .build();
                }else{
                 request = original.newBuilder()
                            //  .header("Authorization",val)
                            .header("Content-Type", "application/json")
                            .method(original.method(), original.body())
                            .build();
                }

                return chain.proceed(request);
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        return retrofit;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void decodeJWT()
    {

        String hosp_id=null;

        // Object obj = new JSONParser().parse(new FileReader("JSONExample.json"));
       // String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJWUFRpZWlsdUtObGlSU3UwdlB1THlPcnljRlU1QmV6OWh1bmlkd3BId1lNIn0.eyJleHAiOjE2MTcwODg3MDksImlhdCI6MTYxNzA4NzgwOCwianRpIjoiNTBlYjQwYzQtMjc2NS00NDAxLWIwMzAtMmUxNDk1MTNlNjEwIiwiaXNzIjoiaHR0cDovLzEzLjIzMi4yNTQuMTE0OjgwODAvYXV0aC9yZWFsbXMvdmVudGlsYXRvciIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI2ZWQyYjkwYy0yZjUwLTQ4YjAtOTA0Zi02MzUyYjdkZmQxNDciLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJzcHJpbmdib290LXNlcnZpY2VzIiwic2Vzc2lvbl9zdGF0ZSI6IjJkZGY1ZDQzLWQ0NGUtNDg0Zi1iYTEzLTBkNTVkN2ZlYmZiNSIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cDovL3ZlbnRpLWFwcC5hbGJvdC5pbzo4MDgwLyIsImh0dHA6Ly8xMjcuMC4wLjE6NTUwMC8iLCJodHRwOi8vdmVudGktYXBwLmFsYm90LmlvLyIsImh0dHA6Ly9sb2NhbGhvc3Q6MzAwMC8iXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbInZlbnRpLXVzciIsIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJ2ZW50aS1hZG0iXX0sInJlc291cmNlX2FjY2VzcyI6eyJzcHJpbmdib290LXNlcnZpY2VzIjp7InJvbGVzIjpbImFkbWluIiwidXNlciJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJob3NwSUQiOiIxIiwibmFtZSI6InZhbXNpIGtva2EiLCJtb2JpbGUiOiI5MDIyNDk0NTkwIiwicHJlZmVycmVkX3VzZXJuYW1lIjoidmFtc2kiLCJnaXZlbl9uYW1lIjoidmFtc2kiLCJmYW1pbHlfbmFtZSI6Imtva2EiLCJlbWFpbCI6InByYXRpa3MzNjA1QGdtYWlsLmNvbSJ9.bbMs3ldkbWd2s3e6Wj88Edd2-iTMlnU2b4Mw-yxNconEAZ7NCJ42mdmAQslM_8AzQNCE9pe9SIgyZcfwMTKK5cKORiJ_Sws983AKzat0zFxc3TfK94GigoXQqXD2pzLEoFEluWlN1U5BtUk4-NH97-n0CRVPfVNfhY0TSPAPNZOE0f77yZ3xEN5DODQKbozhQK-NS0vTiMI__biyNjyxC0CDYf9TFcDhFQ411Yhk-aSzOCFQbhNUzBJGvBrT7Ca67Lbu3P1oVYthLe6U70qnjtaDzH5SWAoM4vvHHhH_hI5E9JBUnUXNQx_bhd7QgSaHDDFtbUYHmT2yOKpGrvz-eA";
        String[] chunks = token_val.split("\\.");

        Base64.Decoder decoder = Base64.getDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        try {
            //JSONObject json = (JSONObject) JSONObject(payload);
            JSONObject jsonObj = new JSONObject(payload);
            hosp_id = jsonObj.getString( "hospID" );
        }
        catch (Exception e)
        {
            Log.e("Error",e.toString());
        }


    }

}
