package com.example.order_service.client;

import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ProviderClient {

    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;
    private Map<String, String> requiredParams;

    public ProviderClient(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
        httpHeaders = new HttpHeaders();
        requiredParams = new HashMap<>();
        addCommonHeader();
        configDefaultHttpHeaders();
        configDefaultParams();
    }

    protected abstract void configDefaultHttpHeaders();

    protected abstract void configDefaultParams();

    protected HttpHeaders getHttpHeaders(){
        return this.httpHeaders;
    }

    protected Map<String, String> getRequiredParams(){
        return this.requiredParams;
    }

    public <T> T sendGet(String url, Map<String, String> params, Class<T> returnClass){
        UriComponentsBuilder builder = buildDefaultUriComponentsBuilder(url);
        if(params != null && params.size() > 0){
            for (Map.Entry<String, String> paramEntry: params.entrySet()) {
                if(!paramEntry.getKey().isEmpty()){
                    builder.queryParam(paramEntry.getKey(), paramEntry.getValue());
                }
            }
        }
        ResponseEntity<T> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
                new HttpEntity<>(this.httpHeaders), returnClass);
        return response.getBody();
    }

    public <T> T sendGet(String url, Class<T> returnClass){
        ResponseEntity<T> response = restTemplate.exchange(buildDefaultUriComponentsBuilder(url).toUriString(), HttpMethod.GET,
                new HttpEntity<>(this.httpHeaders), returnClass);
        return response.getBody();
    }

    public <T> T sendPost(String url, Object body, Class<T> returnClass){
        ResponseEntity<T> response = restTemplate.exchange(buildDefaultUriComponentsBuilder(url).toUriString(),
                HttpMethod.POST, new HttpEntity<>(body, this.getHttpHeaders()), returnClass);
        return response.getBody();
    }

    public <T> T sendPost(String url, MultiValueMap<String, Object> body, Class<T> returnClass){
        ResponseEntity<T> response = restTemplate.exchange(buildDefaultUriComponentsBuilder(url).toUriString(),
                HttpMethod.POST, new HttpEntity<>(body, this.httpHeaders), returnClass);
        return response.getBody();
    }

    public <T> T sendPut(String url, Object body, Class<T> returnClass){
        ResponseEntity<T> response = restTemplate.exchange(buildDefaultUriComponentsBuilder(url).toUriString(),
                HttpMethod.PUT, new HttpEntity<>(body, this.httpHeaders), returnClass);
        return response.getBody();
    }

    private void addCommonHeader(){
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediaTypes);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    private UriComponentsBuilder buildDefaultUriComponentsBuilder(String url){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        if(requiredParams != null && requiredParams.size() > 0){
            for (Map.Entry<String, String> paramEntry: requiredParams.entrySet()) {
                if(!paramEntry.getKey().isEmpty()){
                    builder.queryParam(paramEntry.getKey(), paramEntry.getValue());
                }
            }
        }
        return builder;
    }
}
