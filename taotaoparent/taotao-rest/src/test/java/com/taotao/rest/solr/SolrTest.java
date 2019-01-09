package com.taotao.rest.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.junit.Test;

import java.io.IOException;

public class SolrTest {

    public static final String BASE_URL = "http://192.168.2.129:8983/solr/mycore";

    @Test
    public void addDocument()throws Exception{
        //创建一个连接
        HttpSolrClient solrClient = new HttpSolrClient(BASE_URL);
        //创建一个文档对象
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id","test013");
        document.addField("item_title","测试商品013");
        document.addField("item_price",13);

        //把文档写入索引库
        solrClient.add(document);
        //提交
        solrClient.commit();
    }

    @Test
    public void deleteDocument() throws Exception {
        //创建一连接
        HttpSolrClient solrServer = new HttpSolrClient(BASE_URL);
        solrServer.deleteById("test001");
//        solrServer.deleteByQuery("*:*");
        solrServer.commit();
    }

    @Test
    public void queryDocument(){
        SolrClient solrServer = new HttpSolrClient(BASE_URL);

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("*:*");
        solrQuery.setStart(20);
        solrQuery.setRows(50);
        //执行查询
        QueryResponse response = null;
        try {
            response = solrServer.query(solrQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //取查询结果
        SolrDocumentList solrDocumentList = response.getResults();
        System.out.println("共查询到记录：" + solrDocumentList.getNumFound());
        for (SolrDocument solrDocument : solrDocumentList) {
            System.out.println(solrDocument.get("id"));
            System.out.println(solrDocument.get("item_title"));
            System.out.println(solrDocument.get("item_price"));
            System.out.println(solrDocument.get("item_image"));

        }

    }

}
