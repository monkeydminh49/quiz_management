package com.e01.quiz_management.util;

public class BaseResponse {
     private int code;
     private Object body;
     private String message;

     public BaseResponse(){}

     public BaseResponse(int code, Object body, String message) {
          this.code = code;
          this.body = body;
          this.message = message;
     }

     public int getCode() {
          return code;
     }

     public void setCode(int code) {
          this.code = code;
     }

     public Object getBody() {
          return body;
     }

     public void setBody(Object body) {
          this.body = body;
     }

     public String getMessage() {
          return message;
     }

     public void setMessage(String message) {
          this.message = message;
     }
}
