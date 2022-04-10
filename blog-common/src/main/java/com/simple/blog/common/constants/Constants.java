package com.simple.blog.common.constants;

public interface Constants {

    /**
     * OSS 域名
     */
    String OSS_ENDPOINT = "https://oss-cn-shanghai.aliyuncs.com";

    /**
     * OSS 用户标识
     */
    String OSS_ACCESS_KEY_ID = "access_key_id";

    /**
     * OSS 验证签名字符串的密钥
     */
    String OSS_ACCESS_KEY_SECRET = "access_key_secret";

    /**
     * OSS 存储空间名称
     */
    String OSS_BUCKET_NAME = "simple-oss-1";

    /**
     * OSS 上传文件夹路径前缀
     */
    String OSS_DIR_PREFIX = "blog/images/";



    /**
     * Redis 用户缓存 key 前缀
     */
    String REDIS_USER_PREFIX = "blog_user_";

    /**
     * Redis 资源缓存 key 前缀
     */
    String REDIS_RESOURCE_PREFIX = "blog_resource_";

    /**
     * Redis 缓存时间(s) (24*60*60)
     */
    Long REDIS_EXPIRATION = 86400L;



    /**
     * Elasticsearch URI
     */
    String ELASTICSEARCH_URI = "localhost:9200";

    /**
     * Elasticsearch 用户名
     */
    String ELASTICSEARCH_USERNAME = "elastic";

    /**
     * Elasticsearch 密码
     */
    String ELASTICSEARCH_PASSWORD = "12345678";

    /**
     * Elasticsearch 博客索引
     */
    String ELASTICSEARCH_INDEX_BLOG = "simple-blog-blog";



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
