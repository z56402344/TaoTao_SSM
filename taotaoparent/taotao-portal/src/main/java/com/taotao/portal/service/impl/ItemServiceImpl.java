package com.taotao.portal.service.impl;

import com.taotao.pojo.*;
import com.taotao.portal.Common;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;
import org.apache.http.util.TextUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService ,Common {


    @Override
    public ItemInfo getItemById(long itemId) {
        String json = HttpClientUtil.doGet(M_REST_ITEM_INFO + itemId);
        try{
            if (!TextUtils.isBlank(json)){
                TaotaoResult result = TaotaoResult.formatToPojo(json,ItemInfo.class);
                if (result.getStatus() == 200){
                    ItemInfo item = (ItemInfo) result.getData();
                    return item;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getItemDescById(long itemId) {

        String json = HttpClientUtil.doGet(M_REST_ITEM_DESC + itemId);
        try{
            if (!TextUtils.isBlank(json)){
                TaotaoResult result = TaotaoResult.formatToPojo(json,TbItemDesc.class);
                if (result.getStatus() == 200){
                    TbItemDesc item = (TbItemDesc) result.getData();
                    return item.getItemDesc();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getItemParam(long itemId) {
        String json = HttpClientUtil.doGet(M_REST_ITEM_PARAM + itemId);
        try{
            if (!TextUtils.isBlank(json)){
                TaotaoResult result = TaotaoResult.formatToPojo(json,TbItemParamItem.class);
                if (result.getStatus() == 200){
                    TbItemParamItem item = (TbItemParamItem) result.getData();
                    //生成html
                    // 把规格参数json数据转换成java对象
                    List<Map> jsonList = JsonUtils.jsonToList(item.getParamData(), Map.class);
                    StringBuffer sb = new StringBuffer();
                    sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
                    sb.append("    <tbody>\n");
                    for(Map m1:jsonList) {
                        sb.append("        <tr>\n");
                        sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
                        sb.append("        </tr>\n");
                        List<Map> list2 = (List<Map>) m1.get("params");
                        for(Map m2:list2) {
                            sb.append("        <tr>\n");
                            sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
                            sb.append("            <td>"+m2.get("v")+"</td>\n");
                            sb.append("        </tr>\n");
                        }
                    }
                    sb.append("    </tbody>\n");
                    sb.append("</table>");
                    //返回html片段
                    return sb.toString();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
