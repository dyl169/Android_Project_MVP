package com.yunzao.project.model.api.ssl;

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class TrustAllCerts implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) {
        //不效验客户端证书
    }
  
    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {
        //不效验服户端证书
    }
  
    @Override
    public X509Certificate[] getAcceptedIssuers() {return new X509Certificate[0];}
}  