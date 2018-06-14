package com.example.pc5.ventasyesi.practi1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;







import com.google.gson.Gson;
import com.example.pc5.ventasyesi.R;
import com.example.pc5.ventasyesi.Mapeo.Listas;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class Registrar_lis extends Activity {

	EditText txt;
	Button btn;
	Button btn1;
	private ProgressDialog pDialog;
	WServicePost post;
	Context ctx;
	int codusu,codl;
	String tipo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrarlis);
		ctx=this;
		txt=(EditText)findViewById(R.id.re_et_nombre);
        btn=(Button)findViewById(R.id.re_bt_guardar);
        btn1=(Button)findViewById(R.id.re_bt_cancelar);
		codusu=getIntent().getExtras().getInt("Codusu");
		tipo=getIntent().getExtras().getString("Tipo");
	//	codl=getIntent().getExtras().getInt("codl");



		post=new WServicePost();
        btn1.setOnClickListener(new View.OnClickListener() {


			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//registrar();//
		//
				Asinreglista as=new Asinreglista();
				as.execute(String.valueOf(codusu),txt.getText().toString(),tipo);
			}
		});

	}
  //ok.
	private boolean RegistrarLista(String codusu, String Nombre,String Tipo) {
		int estado=-1;
		ArrayList<NameValuePair> postparam= new ArrayList<NameValuePair>();
		postparam.add(new BasicNameValuePair("Nombre",Nombre));
		postparam.add(new BasicNameValuePair("Tipo", Tipo));
		postparam.add(new BasicNameValuePair("Codusu", codusu));
		Log.d("validar", "Nombre:" + Nombre+"  Tipo:" + Tipo+"  codusu:" + codusu);
		JSONArray jdata= post.obtenerdato(postparam,Constantes.URL_reglista);

		if(jdata!=null&&jdata.length()>0){
			JSONObject json_data;
			try {
				json_data=jdata.getJSONObject(0);
				estado=json_data.getInt("estado");
				Log.d("validar","estado"+estado);
			}catch (JSONException e){
				e.printStackTrace();
			}
			if(estado==0){
				Log.d("validar","invalido");
				return false;
			}else{
				Log.d("validar","valido");
				return true;
			}

		}else{
			Log.d("json","error");
			return false;
		}

	}




	class Asinreglista extends AsyncTask<String, String, String> {

		////

		@Override
		protected void onPreExecute() {
			pDialog=new ProgressDialog(ctx);
			pDialog.setMessage("Registrando lista......");
			//pDialog.setIndeterminate(false);
			//pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			Log.d("doin", "entre");

			if(RegistrarLista(params[0], params[1],params[2])==true){
				return "ok";
			}else{
				return "error";
			}
		}

		@Override
		protected void onPostExecute(String s) {
			if (pDialog!=null){
				pDialog.dismiss();
			}
			Log.d("asyn", "onpos" + s);
			if(s.equals("ok")) {
				Toast.makeText(getApplicationContext(), "Lista registrada exitosamente", Toast.LENGTH_SHORT).show();
				finish();
			}else{
				Toast.makeText(getApplicationContext(), "Lista no registrada", Toast.LENGTH_SHORT).show();

			}
		}
	}


}
