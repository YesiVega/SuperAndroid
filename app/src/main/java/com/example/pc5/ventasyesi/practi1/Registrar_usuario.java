package com.example.pc5.ventasyesi.practi1;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import com.google.gson.Gson;
import com.example.pc5.ventasyesi.R;
import com.example.pc5.ventasyesi.DAL.Manager;
import com.example.pc5.ventasyesi.Mapeo.Usuario;
import com.example.pc5.ventasyesi.menuprincipal;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registrar_usuario extends Activity {

	Context stx;
	static Manager m;
	EditText etx;//nick cuenta
	EditText etx1;//telef clave
	Button btn;
	private ProgressDialog pDialog;
	WServicePost post;
	int codusu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		codusu=-1;
		m=new Manager(this);
		stx=this;
		m.openDB();
		setContentView(R.layout.registrar_usuario);
		etx=(EditText)findViewById(R.id.ru_et_nick);
		etx1=(EditText)findViewById(R.id.ru_et_tel);
		btn=(Button)findViewById(R.id.ru_bt_registrar);

		post=new WServicePost();
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ingresar();
			}
		});
		
	}

	public void ingresar(){
		String usuario=etx.getText().toString();
		String clave=etx1.getText().toString();
		if(vaciologin(usuario,clave)){
			Usuario u=m.getUsuario(usuario);
			if(u!=null){
				if (u.getTelef().equals(clave)){
					Intent i = new Intent(Registrar_usuario.this, menuprincipal.class);
					i.putExtra("usuario", usuario);
					startActivity(i);

				}else{
					Toast.makeText(getApplicationContext(),"usuario invalido",Toast.LENGTH_SHORT).show();
				}
			}else{
				new Asinlogin().execute(usuario,clave);
			}
		}else{
			Toast.makeText(getApplicationContext(),"usuario invalido",Toast.LENGTH_SHORT).show();
		}
	}

	public boolean vaciologin(String usu,String clave){
		if(usu.equals("")||clave.equals("")){
			Log.d("login", "datos vacios");
			return  false;
		}
		return true;
	}

	class Asinlogin extends AsyncTask<String, String, String>{

		String usuario ,clave;

		@Override
		protected void onPreExecute() {
			pDialog=new ProgressDialog(stx);
			pDialog.setMessage("Autenticando......");
			//pDialog.setIndeterminate(false);
			//pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			Log.d("doin","entre");

			usuario=params[0];
			clave=params[1];
			if(RegistrarUsuario(usuario,clave)==true){

				if (descargarUsuario(usuario)<1){
					return "error";
				}else{
					return "ok";
				}
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

					SharedPreferences prefs =
							getSharedPreferences(Constantes.Configuracion,Context.MODE_PRIVATE);
					//Log.d("RU", varia);
				prefs.edit().putInt(Constantes.ConfigUsuario, codusu);
				Log.d("validar", "codUsu" + codusu);

				prefs.edit().apply();
					Toast.makeText(getApplicationContext(), "usuario registrado exitosamente", Toast.LENGTH_SHORT);
				finish();

			}else{
				Toast.makeText(getApplicationContext(), "Nick ya registrado, por favor escriba otro.", Toast.LENGTH_SHORT);

//				Toast.makeText(getApplicationContext(),"Nombre de usuario incorrecto",Toast.LENGTH_SHORT).show();


			}
		}
	}

	private boolean RegistrarUsuario(String nick, String telf) {
		int estado=-1;
		ArrayList<NameValuePair> postparam= new ArrayList<NameValuePair>();
		postparam.add(new BasicNameValuePair("Nick",nick));
		postparam.add(new BasicNameValuePair("Telef",telf));
		JSONArray jdata= post.obtenerdato(postparam,Constantes.URL_regusuario);

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
	private int descargarUsuario(String usuario) {
		int estado=-1;
		ArrayList<NameValuePair> postparam= new ArrayList<NameValuePair>();
		//aqui cargamos los datos que se enviara al servidor, dentro del postparam
		postparam.add(new BasicNameValuePair("Nick",usuario));
		Log.d("descargarUsuario", "Nick:"+usuario);
		//esta linea realiza la llamada al server, se manda los parametros y la URL de la accion
		String string= post.obtenerrespuesta(postparam, Constantes.URL_getusuario);
		//Se crea la lista q guardara los objetos o la respuesta del servidor.
		Log.d("descargarUsuario", "resp:"+string);
		ArrayList<Usuario> listusuarios=new ArrayList<Usuario>();
		if(!string.equalsIgnoreCase("")){
			//cuando son listas usamos el JSONObject. en otro caso veremos cuando es solo texto.
			JSONObject json;
			try{
				//COnvertimos el texto recibido del server a jsonobject
				json=new JSONObject(string);
				//se usa el Jsonarray pa sacar los datos del arreglo se tiene que especificar el nombre
				JSONArray jsonArray =json.optJSONArray("usuario");
				for(int i=0; i<jsonArray.length();i++){
					//creamos el objeto q recibira los datos
					Usuario cl=new Usuario();
					//llamamos a esta linea pa sacar los datos del array, es como un cursos en BD
					JSONObject jsonarraychild=jsonArray.getJSONObject(i);
					cl.setCodusu(Integer.parseInt(jsonarraychild.optString("Codusu")));
					cl.setNick(jsonarraychild.optString("Nick"));
					cl.setTelef(jsonarraychild.optString("Telef"));
					estado=cl.getCodusu();
					listusuarios.add(cl);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			//Toast.makeText(getApplicationContext(), "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
			Log.d("descargarUsuario", "resp:"+string);

			for (Usuario us: listusuarios){
				Registrar_usuario.m.insertUsuario(us);
				codusu=us.getCodusu();
			}

			return estado;
		}else{
			Log.d("json","error");
			return estado ;
		}

	}


	public void Regist_usu(){
		
		Usuario u=new Usuario(0,etx.getText().toString(),etx1.getText().toString());
		Gson g=new Gson();
		String s=g.toJson(u);
		RegusuTask rg=new RegusuTask();
		rg.execute(s);
	}

	  private class RegusuTask extends AsyncTask<String, Void, Void> {

	        private Servidor Server;
	        private String varia;
	        //private Empresa mEmpresa;

	        public RegusuTask() {
	            Server = new Servidor();
	        }


	        
	        @Override
	        protected Void doInBackground(String... params) {

	            try {


	                varia = Server.regiusu((params[0]));
	                Log.i("hol", varia);

	            } catch (IOException e) {
	                Log.e("hol", "" + e.getMessage());
	            } catch (XmlPullParserException e) {
	                Log.e("hol", "" + e.getMessage());
	            }

	            return null;
	        }

	        @Override
	        protected void onPostExecute(Void result) {

	                if(varia.equals("null")){
	                    Toast.makeText(getBaseContext(), "usuario no registrado", Toast.LENGTH_SHORT);
	                }else{
	                	SharedPreferences prefs =
		   					     getSharedPreferences(Constantes.Configuracion,Context.MODE_PRIVATE);
		   					 Log.d("RU", varia);

		   					prefs.edit().putString(Constantes.ConfigUsuario, varia);
						prefs.edit().apply();
		   					finish();  //este finish sta bien
	                	Toast.makeText(getBaseContext(), "usuario ya registrado", Toast.LENGTH_SHORT);
	                }
	                
	        		
	            
	        }


		
	    }
}
