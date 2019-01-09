package com.taotao.sso.core;

public interface Common {

    //商品信息在Redis中保存的key
    String REDIS_USER_SESSION_KEY = "TAOTAO_REDIS_USER_SESSION";

    //商品信息在Redis中的过期时间
    int REDIS_SESSION_EXPIRE = 60*30;

    String TT_TOKEN = "TT_TOKEN";

}
