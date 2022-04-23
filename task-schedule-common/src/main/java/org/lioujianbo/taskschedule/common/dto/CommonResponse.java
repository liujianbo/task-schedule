package org.lioujianbo.taskschedule.common.dto;

import lombok.Data;

@Data
public class CommonResponse<T> {

    private String code;

    private String message;

    private T data;

    public CommonResponse() {
        super();
    }

    public CommonResponse(CommonResponseBuilder<T> builder) {
        this.code =builder.code;
        this.message = builder.message;
        this.data = builder.data;
    }

    public static <T> CommonResponseBuilder<T> builder(){
        return new CommonResponseBuilder<>();
    }

    public static class CommonResponseBuilder<T> {

        public static String SUCCESS = "200";

        private String code = SUCCESS;

        private String message = "SUCCESS";

        private T data;

        public CommonResponseBuilder() {}

        public CommonResponse build(){
            CommonResponse response = new CommonResponse(this);
            return response;
        }

        public CommonResponseBuilder data(T t){
            this.data = t;
            return this;
        }

        public CommonResponseBuilder success(){
            this.code = SUCCESS;
            return this;
        }

        public CommonResponseBuilder failed(String code, String message){
            this.code = code;
            this.message = message;
            return this;
        }

    }
}
