package com.taotao.rest;

public interface Ikey {
    String INDEX_CONTENT_REDIS_KEY = "INDEX_CONTENT_REDIS_KEY";

    //商品信息在Redis中保存的key
    String REDIS_ITEM_KEY = "REDIS_ITEM_KEY";

    //商品信息在Redis中的过期时间
    int REDIS_ITEM_EXPIRE = 60*60*24;

}
