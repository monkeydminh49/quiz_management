package com.e01.quiz_management.util;

public class Response<T> {
    public static final Response<?> Loading = new Loading();

    public static class Loading extends Response<Nothing> {
    }

    public static class Success<T> extends Response<T> {
        private T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }
    }

    public static class Failure extends Response<Nothing> {
        private Exception e;

        public Failure(Exception e) {
            this.e = e;
        }

        public Exception getException() {
            return e;
        }
    }

    public static class Nothing {
    }
}

