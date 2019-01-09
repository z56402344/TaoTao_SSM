package com.taotao.portal;

public interface Common {
    String BASE_URL = "http://localhost";
    String SEARCH_PORT = ":8083";
    String REST_PORT = ":8081";
    String SSO_PORT = ":8084";
    String PORTAL_PORT = ":8082";
    String ORDER_PORT = ":8085";

    String BASE_SEARCH_URL = BASE_URL + SEARCH_PORT + "/search/query";
    String BASE_REST_URL = BASE_URL + REST_PORT + "/rest";
    String BASE_SSO_URL = BASE_URL + SSO_PORT + "/user";
    String BASE_PORTAL_URL = BASE_URL + SSO_PORT + "/page";
    String BASE_ORDER_URL = BASE_URL + ORDER_PORT + "/order";

    String M_REST_ITEM_INFO = BASE_REST_URL + "/item/info/";
    String M_REST_ITEM_DESC = BASE_REST_URL + "/item/desc/";
    String M_REST_ITEM_PARAM = BASE_REST_URL + "/item/param/";

    String M_SSO_USER_TOKEN = BASE_SSO_URL + "/token/";


    String M_PORTAL_PAGE_LOGIN = BASE_PORTAL_URL + "/login";



    String M_ORDER_CREATE = BASE_ORDER_URL + "/create";

}
