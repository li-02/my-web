package com.example.blog.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.example.blog.response.ResponseCode;

import java.time.LocalDateTime;

/**
 * 通用响应类
 *
 * @param <T> 数据类型
 */
@Data
@NoArgsConstructor
public class ApiResponse<T> {

    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 时间戳
     */
    private LocalDateTime timestamp;

    /**
     * 请求是否成功
     */
    private Boolean success;

    // 私有构造方法
    private ApiResponse(Integer code, String message, T data, Boolean success) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.success = success;
        this.timestamp = LocalDateTime.now();
    }

    // ========== 成功响应静态方法 ==========

    /**
     * 成功响应（仅消息）
     */
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(ResponseCode.SUCCESS.getCode(), message, null, true);
    }

    /**
     * 成功响应（数据 + 默认消息）
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ResponseCode.SUCCESS.getCode(), "操作成功", data, true);
    }

    /**
     * 成功响应（自定义消息 + 数据）
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(ResponseCode.SUCCESS.getCode(), message, data, true);
    }

    /**
     * 成功响应（完全自定义）
     */
    public static <T> ApiResponse<T> success(Integer code, String message, T data) {
        return new ApiResponse<>(code, message, data, true);
    }

    // ========== 失败响应静态方法 ==========

    /**
     * 失败响应（仅消息）
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), message, null, false);
    }

    /**
     * 失败响应（状态码 + 消息）
     */
    public static <T> ApiResponse<T> error(Integer code, String message) {
        return new ApiResponse<>(code, message, null, false);
    }

    /**
     * 失败响应（状态码 + 消息 + 数据）
     */
    public static <T> ApiResponse<T> error(Integer code, String message, T data) {
        return new ApiResponse<>(code, message, data, false);
    }

    /**
     * 失败响应（使用预定义的响应码）
     */
    public static <T> ApiResponse<T> error(ResponseCode responseCode) {
        return new ApiResponse<>(responseCode.getCode(), responseCode.getMessage(), null, false);
    }

    /**
     * 失败响应（使用预定义的响应码 + 自定义消息）
     */
    public static <T> ApiResponse<T> error(ResponseCode responseCode, String message) {
        return new ApiResponse<>(responseCode.getCode(), message, null, false);
    }

    // ========== 常用快捷方法 ==========

    /**
     * 参数错误
     */
    public static <T> ApiResponse<T> badRequest(String message) {
        return error(ResponseCode.BAD_REQUEST.getCode(), message);
    }

    /**
     * 未授权
     */
    public static <T> ApiResponse<T> unauthorized(String message) {
        return error(ResponseCode.UNAUTHORIZED.getCode(), message);
    }

    /**
     * 资源未找到
     */
    public static <T> ApiResponse<T> notFound(String message) {
        return error(ResponseCode.NOT_FOUND.getCode(), message);
    }

    /**
     * 服务器内部错误
     */
    public static <T> ApiResponse<T> serverError(String message) {
        return error(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), message);
    }
}
