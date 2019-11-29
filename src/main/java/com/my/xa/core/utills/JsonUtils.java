package com.my.xa.core.utills;

import java.util.ArrayList;
import java.util.List;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class JsonUtils {


    public static String serialize(Object result) {
        return JSON.toJSONString(result);
    }

    @SuppressWarnings("unchecked")
    public static Object deserialize(String json, Class clazz, Class modelType) {

        if (clazz.isAssignableFrom(List.class)) {
            return JSON.parseArray(json, modelType);
        }

        return JSON.parseObject(json, clazz);
    }
    
    public static List<JSONObject> jsonToList(String s){
        List<JSONObject> list =new ArrayList<>();
        try {
            JSONArray jsonArray = JSONArray.fromObject(s);
            for (int i=0;i<jsonArray.size();i++){
                list.add(JSONObject.fromObject(jsonArray.get(i)));
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return list;
    }

    public static JSONObject jsonToObj(String s){
        JSONObject obj =new JSONObject();
        try {
           return  JSONObject.fromObject(s);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return obj;
    }
}
