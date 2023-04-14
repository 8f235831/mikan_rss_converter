package pers.u8f23.mikan_rss_converter.entity;

import lombok.Getter;

@Getter
public class BaseResponse<T> {
    private T body;
    private int code;
    private String msg;

    private BaseResponse(T body, int code, String msg) {
        this.body = body;
        this.msg = msg;
        this.code = code;
    }

    public static <T> BaseResponse<T> success(T body) {
        return new BaseResponse<>(body, 0, "success!");
    }

    public static <T> BaseResponse<T> success(T body, int code) {
        if (code < 0) {
            throw new IllegalArgumentException();
        }
        return new BaseResponse<>(body, code, "success!");
    }

    public static <T> BaseResponse<T> success(T body, int code, String msg) {
        if (code < 0) {
            throw new IllegalArgumentException();
        }
        return new BaseResponse<>(body, code, msg);
    }

    public static <T> BaseResponse<T> fail() {
        return new BaseResponse<>(null, -1, "fail!");
    }

    public static <T> BaseResponse<T> fail(int code) {
        if (code >= 0) {
            throw new IllegalArgumentException();
        }
        return new BaseResponse<>(null, code, "fail!");
    }

    public static <T> BaseResponse<T> fail(int code, String msg) {
        if (code >= 0) {
            throw new IllegalArgumentException();
        }
        return new BaseResponse<>(null, code, msg);
    }
}
