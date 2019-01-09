package com.taotao.rest.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CatNode {

    @JsonProperty("n")
    public String name;
    @JsonProperty("u")
    public String url;
    @JsonProperty("i")
    public List<?> item;
}
