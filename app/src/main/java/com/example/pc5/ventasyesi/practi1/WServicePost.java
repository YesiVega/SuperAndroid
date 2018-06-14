package com.example.pc5.ventasyesi.practi1;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by yesica on 18/07/2016.
 */
public class WServicePost {

    InputStream is=null;
    String result;

    public JSONArray obtenerdato(ArrayList<NameValuePair> param ,String url){
        conectar(param, url);
        if(is!=null){

            Log.d("obtenerdato","obtenerdato");
            responder();
            Log.d("obtenerdato", "responder");
            return getjson();
        }else{

            Log.d("obtenerdato", "null");
            return null;
        }
    }

    public String obtenerrespuesta(ArrayList<NameValuePair>param ,String url){
        conectar(param, url);
        if(is!=null){
            responder();
            return result;
        }else{
            return "";
        }
    }

    private void conectar(ArrayList<NameValuePair> param,String url){
        try {
            HttpClient httpClient=new DefaultHttpClient();
            HttpPost httpPost=new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(param));
            HttpResponse httpResponse= httpClient.execute(httpPost);
            HttpEntity entity=httpResponse.getEntity();
            is=entity.getContent();
        }catch (Exception e){

            Log.d("error", "error al conectar");
        }

    }
    public void responder(){
        try {
            BufferedReader rader=new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb=new StringBuilder();
            String line=null;
            while((line=rader.readLine())!=null){
                sb.append(line+"\n");
            }
            is.close();
            result=sb.toString();
            Log.d("responder","result="+sb.toString());
        }catch (Exception e){
            Log.d("responder","Error:"+e.toString());
        }

    }

    public JSONArray getjson(){
        try {
            JSONArray j =new JSONArray(result);
            return j;
        }catch (JSONException e){
            Log.d("getjson","Error:"+e.toString());
            return  null;
        }
    }

}
