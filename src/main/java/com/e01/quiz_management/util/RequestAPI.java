package com.e01.quiz_management.util;

import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.model.TestHistory;
import com.e01.quiz_management.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

public class RequestAPI {
    private final static String baseURL = "http://13.212.194.205/api/v1";
//    private final static String baseURL = "http://localhost:8080/api/v1";

    private final List<String> openEndpoints = List.of("/hello", "/login", "/register");
    private static User user;
    private static RequestAPI instance;
    private static ObjectMapper mapper;

    private RequestAPI() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    public static RequestAPI getInstance() {
        if (instance == null) {
            instance = new RequestAPI();
        }
        return instance;
    }

    public static User getUser() {
        return user;
    }

    public Object getHello() {
        HttpURLConnection httpRequest = httpRequest("GET", "/hello");
        try {
            return mappingResponse(httpRequest, Object.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Test getUserTestById(Long id) {
        HttpURLConnection httpRequest = httpRequest("GET", "/test/" + id);
        try {
            return mappingResponse(httpRequest, Test.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Test getTestByCode(String code) {
        HttpURLConnection httpRequest = httpRequest("GET", "/test?code=" + code);
        try {
            return mappingResponse(httpRequest, Test.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Test getTestById(Long id){
        HttpURLConnection httpRequest = httpRequest("GET", "/test/" + id);
        try {
            return mappingResponse(httpRequest, Test.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Test> getAllUserTests() {
        HttpURLConnection httpRequest = httpRequest("GET", "/test/all");
        return mappingResponse(httpRequest, new TypeReference<List<Test>>() {
        });
    }

    public List<TestHistory> getTestHistories() {
        HttpURLConnection httpRequest = httpRequest("GET", "/test-history");
        return mappingResponse(httpRequest, new TypeReference<List<TestHistory>>() {
        });
    }

    public List<TestHistory> getTestHistoriesByTestId(Long id) {
        HttpURLConnection httpRequest = httpRequest("GET", "/test-history/" + id);
        System.out.println(id);
        return mappingResponse(httpRequest, new TypeReference<List<TestHistory>>() {
        });
    }

    public TestHistory postSubmitTestScore(Long testId, int score, LocalDateTime submitTime) {
        String payload = "{\"id\": " + testId + ", \"score\": " + score + ", \"submitTime\": \"" + submitTime + "\"}";
        System.out.println(payload);
        HttpURLConnection httpRequest = httpRequest("POST", "/test-history", payload);
        try {
            return mappingResponse(httpRequest, TestHistory.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BaseResponse postCreateTest(Test test) {
        BaseResponse response = new BaseResponse();
        String payload = "";
        try {
            payload = mapper.writeValueAsString(test);
            System.out.println("Payload: " + payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        HttpURLConnection httpRequest = httpRequest("POST", "/test", payload);
        try {
            return mappingResponse(httpRequest, BaseResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void putUpdateTestById(Long id, Test test) {
        BaseResponse response = new BaseResponse();
        String payload = "";
        try {
            payload = mapper.writeValueAsString(test);
            System.out.println("\n********************\n");
            System.out.println(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        HttpURLConnection httpRequest = httpRequest("PUT", "/test/" + id, payload);
        try {
            mappingResponse(httpRequest, BaseResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public BaseResponse postLogin(String username, String password) {
        BaseResponse response = new BaseResponse();
        String payload = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";
        System.out.println(payload);
        HttpURLConnection httpRequest = httpRequest("POST", "/login", payload);
        try {
            if (httpRequest.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String data;
                try (BufferedReader bf = new BufferedReader(new InputStreamReader(httpRequest.getInputStream()))) {
                    data = bf.readLine();
                    response = mapper.readValue(data, BaseResponse.class);
                    user = getBaseResponseBodyObject(response, User.class);
                } catch (Exception e) {
                    throw (new IOException(e));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public BaseResponse postRegister(String name, String username, String password) {
        String payload = "{\"name\": \"" + name + "\", \"username\": \"" + username + "\", \"password\": \"" + password + "\"}";
        System.out.println(payload);
        HttpURLConnection httpRequest = httpRequest("POST", "/register", payload);

        try {
            return mappingResponse(httpRequest, BaseResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteTest(Long id) {
        HttpURLConnection httpRequest = httpRequest("DELETE", "/test/" + id);
        try {
            mappingResponse(httpRequest, BaseResponse.class);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private HttpURLConnection httpRequest(String method, String endpoint, String payload) {
        HttpURLConnection httpConnection = httpRequest(method, endpoint);

        // this is used for POST and PUT requests to send a request body
        httpConnection.setDoOutput(true);

        // try-with-resources: used to declare resources to be used in a "try" block.
        // Resources will be removed after the block
        try (DataOutputStream dos = new DataOutputStream(httpConnection.getOutputStream())) {
            dos.writeBytes(payload);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return httpConnection;
    }

    private HttpURLConnection httpRequest(String method, String endpoint) {


        // The connection is opened after the following construction
        HttpURLConnection httpConnection;
        try {
            URL url = new URL(baseURL + endpoint);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod(method);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // these are used to set Headers to requests
        httpConnection.setRequestProperty("Content-Type", "application/json");
        httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
        System.out.println();
        System.out.println("Request URL: " + httpConnection.getURL());
        System.out.println("Request Method: " + httpConnection.getRequestMethod());
        System.out.println("Content-Type: " + httpConnection.getRequestProperty("Content-Type"));
        System.out.println("User-Agent: " + httpConnection.getRequestProperty("User-Agent"));
        if (!openEndpoints.contains(endpoint) && user != null) {
            httpConnection.setRequestProperty("Authorization", "Bearer " + user.getToken().getAccessToken());
//            System.out.println("Authorization: " + httpConnection.getRequestProperty("Authorization"));
            System.out.println("Authorization: " + "Bearer " + user.getToken().getAccessToken());

        }
        System.out.println();


        return httpConnection;
    }

    public <T> T getBaseResponseBodyObject(String jsonData, Class<T> clazz) {
        System.out.println(jsonData);
        BaseResponse response;
        try {
            response = mapper.readValue(jsonData, BaseResponse.class);
            String body = mapper.writeValueAsString(response.getBody());
            return mapper.readValue(body, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T getBaseResponseBodyObject(BaseResponse response, Class<T> clazz) {
        String body;
        try {
            body = mapper.writeValueAsString(response.getBody());
            return mapper.readValue(body, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T mappingResponse(HttpURLConnection httpRequest, Class<T> clazz) throws IOException {
        try {
            System.out.println("Status code: " + httpRequest.getResponseCode());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BufferedReader bf = new BufferedReader(new InputStreamReader(httpRequest.getInputStream()));
        String data = bf.readLine();
        System.out.println("Response body: " + data);
        if (httpRequest.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return mapper.readValue(data, clazz);
        }

        return null;
    }

    public <T> T mappingResponse(HttpURLConnection httpRequest, TypeReference<T> valueTypeRef) {
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(httpRequest.getInputStream()))) {
            System.out.println("Status code: " + httpRequest.getResponseCode());
            String data = bf.readLine();
            System.out.println("Response body: " + data);
            if (httpRequest.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return mapper.readValue(data, valueTypeRef);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
