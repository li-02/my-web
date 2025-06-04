package com.example.blog.response;

import lombok.Getter;

/**
 * 响应状态码枚举
 */
@Getter
public enum ResponseCode {
    // 成功
    SUCCESS(200, "操作成功"),

    // 客户端错误
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权访问"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),

    // 服务器错误
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),

    // 业务错误码 (自定义)
    VALIDATION_FAILED(4001, "数据校验失败"),
    FILE_UPLOAD_FAILED(4002, "文件上传失败"),
    FILE_SIZE_EXCEEDED(4003, "文件大小超过限制"),
    FILE_TYPE_NOT_SUPPORTED(4004, "不支持的文件类型"),
    LOGIN_FAILED(4005, "登录失败"),
    TOKEN_EXPIRED(4006, "令牌已过期"),
    ACCESS_DENIED(4007, "访问被拒绝");

    private final Integer code;
    private final String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}