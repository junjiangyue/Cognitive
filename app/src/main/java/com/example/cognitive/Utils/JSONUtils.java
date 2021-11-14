//package com.example.cognitive.Utils;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import java.util.ArrayList;
//import com.example.cognitive.Bean.Articles;
//
//
//public class JSONUtils {
//    public static ArrayList<Articles> parseJson(String jsonData) {
//        ArrayList<Articles> result = new ArrayList<>();
//        JSONObject jo = null;
//        Articles articles;
//        try {
//            jo = new JSONObject(jsonData);
//            if (jo.getString("reason").equals("成功的返回")) {
//                JSONObject jo1 = jo.getJSONObject("result");
//                JSONArray ja = jo1.getJSONArray("data");
//                for (int i = 0; i < ja.length(); i++) {
//                    articles = new Articles();
//                    JSONObject obj = ja.getJSONObject(i);
//                    articles.setNewsTitle(obj.getString("title"));
//                    articles.setNewsDate(obj.getString("date"));
////                    articles.setAuthor_name(obj.getString("author_name"));
////                    articles.setThumbnail_pic_s(obj.getString("thumbnail_pic_s"));
////                    articles.setUrl(obj.getString("url"));
//                    result.add(articles);
//                }
//            }
//            return  result;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
