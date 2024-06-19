package com.emat.aatranscript_opeinai_app.global;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class ImageModelConfig {

    @Bean
    public RestClient.Builder restClientBuilder() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)  // Connection timeout
                .readTimeout(30, TimeUnit.SECONDS)     // Read timeout
                .writeTimeout(30, TimeUnit.SECONDS)    // Write timeout
                .build();

        ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory(okHttpClient);

        return RestClient.builder()
                .requestFactory(factory);
    }


}
