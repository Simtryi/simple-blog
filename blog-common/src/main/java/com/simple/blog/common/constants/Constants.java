package com.simple.blog.common.constants;

public interface Constants {

    /**
     * OSS 域名
     */
    String OSS_ENDPOINT = "https://oss-cn-shanghai.aliyuncs.com";

    /**
     * OSS 用户标识
     */
    String OSS_ACCESS_KEY_ID = "LTAI5tBFpQYtAWaTVCo6t6yY";

    /**
     * OSS 验证签名字符串的密钥
     */
    String OSS_ACCESS_KEY_SECRET = "haTy4X0SSc5tX9LkRabwwW8jKLmIti";

    /**
     * OSS 存储空间名称
     */
    String OSS_BUCKET_NAME = "simple-oss-1";

    /**
     * OSS 上传文件夹路径前缀
     */
    String OSS_DIR_PREFIX = "blog/images/";



    /**
     * JWT 加解密使用的密钥
     */
    String JWT_SECRET = "simple-blog-secret";

    /**
     * JWT 的超期限时间(S) (7*24*60*60)
     */
    Long JWT_EXPIRATION = 604800L;

    /**
     * JWT 请求头
     */
    String JWT_HEADER = "Authorization";

    /**
     * JWT 身份验证模式
     */
    String JWT_AUTHENTICATION_SCHEMA = "Bearer";

}
