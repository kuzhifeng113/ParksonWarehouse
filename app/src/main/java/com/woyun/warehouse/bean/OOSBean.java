package com.woyun.warehouse.bean;

/**
 * 阿里上传图片 配置实体类
 */
public class OOSBean {
    /**
     * accessKeyId : STS.NKFAcAWumXtcXDjsR9UyvyQNX
     * bucket : dymeet      bucker 地址
     * securityToken : CAISkwJ1q6Ft5B2yfSjIr4jzCtn1uqpM77aIWmLbl1JsWfZalpTlujz2IHFKeHVsBesdvvk1lWhU6PsblqB6T55OSAmcNZIob0/7G7zlMeT7oMWQweEuuv/MQBquaXPS2MvVfJ+OLrf0ceusbFbpjzJ6xaCAGxypQ12iN+/m6/Ngdc9FHHP7D1x8CcxROxFppeIDKHLVLozNCBPxhXfKB0ca3WgZgGhku6Ok2Z/euFiMzn+Ck7NO+d2qecD+M5I0bMojAu3YhrImKvDztwdL8AVP+atMi6hJxCzKpNn1ASMKskvab7aIqYY/cVUoPvRkRvde3/H4lOxlvOvIjJjwyBtLMuxTXj7WWIe62szAFfME5DDn4RpVURqAAVfTIvxyN6F+Zqz8ZXUIWZDMagQhh56nNOw51bXWLcmRStLq6+L78kik8g5dpNIkgM3teiNfrHWQm6+C3BvkoYzxaSxV9yFfM3mzM8n6Ux0RFXVv7PCMmcTVG53ZGCctnBQiNTB4Nahx/nifUs/yirKTEc682elKHhaCpNfJqu2A
     * accessKeySecret : 4A7ALSpdR9Y6mNVJGvuu5hnjYz9pE94yRrXf5vWWzEM2
     * folder : avatar/
     * domain : http://image.dymeet.com/
     * expiration : 2018-08-25T09:01:27Z
     * region : https://oss-cn-shenzhen.aliyuncs.com     服务器地址
     * statusCode : 0
     */

    private String accessKeyId;
    private String bucket;
    private String securityToken;
    private String accessKeySecret;
    private String folder;
    private String domain;
    private String expiration;
    private String region;
    private int statusCode;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
