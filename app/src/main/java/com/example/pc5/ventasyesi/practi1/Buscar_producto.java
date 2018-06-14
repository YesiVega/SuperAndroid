package com.example.pc5.ventasyesi.practi1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc5.ventasyesi.R;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.pc5.ventasyesi.Mapeo.Listas;
import com.example.pc5.ventasyesi.Mapeo.Producto;
import com.example.pc5.ventasyesi.Mapeo.Super;

public class Buscar_producto extends Activity {

	private ArrayList<Producto> lista;
	private ArrayAdapter<Producto> listaAdapter;
	private ProgressDialog pDialog;
	private String m_text="";
	WServicePost post;
	ListView list;
	EditText etx;
	Button btn;
	int usuario;
	Context stx;
	int codp;
	int codl;
	int cantidad;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buscar);
		post=new WServicePost();
		stx=this;
		list=(ListView)findViewById(R.id.bus_lv_listasp);
        etx=(EditText)findViewById(R.id.bus_et_buscar);
		btn=(Button)findViewById(R.id.bus_bt_buscar);
		//codp=getIntent().getExtras().getInt("CodP");






		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//onCreateOptionsMenu(menu) ;

				AsinListas al = new AsinListas();
				al.execute(etx.getText().toString());
			}
		});

		SharedPreferences prefs =
				getSharedPreferences("SuperConfig", Context.MODE_PRIVATE);
		usuario = prefs.getInt("codUsu", -1);
		Bundle bung=getIntent().getExtras();
		if(bung!=null){
			codl=bung.getInt("codl");

		}

		Log.d("buscarp","codl:"+codl);
	}

	//al iniciar es unresume para algo q se quiera
	@Override
	protected void onResume() {
		super.onResume();
		//descargarlistas(usuario,"S");
	}

	/*
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub


	}
	*/

	class Asinregprod extends AsyncTask<String, String, String> {

		////

		@Override
		protected void onPreExecute() {
			pDialog=new ProgressDialog(stx);
			pDialog.setMessage("Registrando Producto......");
			//pDialog.setIndeterminate(false);
			//pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			Log.d("doin", "entre");
			//String.valueOf(codp),String.valueOf(codl),m_text
			if(Registrarprod(Integer.parseInt(params[0]),Integer.parseInt( params[2]),Integer.parseInt(params[1]))==true){
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
				Toast.makeText(getApplicationContext(), "Producto registrado exitosamente", Toast.LENGTH_SHORT).show();
				finish();
			}else{
				Toast.makeText(getApplicationContext(), "Producto no registrado", Toast.LENGTH_SHORT).show();

			}
		}
	}//

	private boolean Registrarprod(int codp,int cantidad,int codl) {
		int estado=-1;//vamos al list.onitem
		ArrayList<NameValuePair> postparam= new ArrayList<NameValuePair>();
		postparam.add(new BasicNameValuePair("Codp",String.valueOf(codp)));
		postparam.add(new BasicNameValuePair("Cantidad",String.valueOf(cantidad)));
		postparam.add(new BasicNameValuePair("Codl", String.valueOf(codl)));
		JSONArray jdata= post.obtenerdato(postparam,Constantes.URL_rprodlista);
//me dio m dejas revisar.?.ok.

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




	class AsinListas extends AsyncTask<String, String, String> {

		//String usuario ,clave;

		String nombre;
		int codusu;
		@Override
		protected void onPreExecute() {
			pDialog=new ProgressDialog(stx);
			pDialog.setMessage("Cargando lista......");
			//pDialog.setIndeterminate(false);
			//pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			Log.d("doin", "entre");

			if(buscarprod(params[0])==true){

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
				buscarDatos();
				Log.d("Listas", "post.ok:" + s);
			}else{
				Log.d("Listas", "post:" + s);

			}
		}
	}


	private void buscarDatos() {
		//
		try {

			//lista = Iniciarsesion.m.getListaCliente();
			Log.d("buscardatos", "lista.size:" + lista.size());
			if(listaAdapter==null){
				listaAdapter= new ListaArrayAdapter(Buscar_producto.this,
						lista);
				listaAdapter.setNotifyOnChange(true);


			}else {
				listaAdapter.clear();
				listaAdapter = new ListaArrayAdapter(Buscar_producto.this,
						lista);
				listaAdapter.notifyDataSetChanged();
			}//dio error nel. vamos al create xfa.

			list.setAdapter(listaAdapter);
			list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					//barra de cargar
					AlertDialog.Builder builder = new AlertDialog.Builder(stx);
					builder.setTitle("Cantidad");
					codp=lista.get(position).getIdprod(); // nos manejaremos x id ok.ahora solo falta q corrija el texto y probar
					//inicializas un edittext
					//listo amor a probarlo
					//m avisas como va.
					final EditText input = new EditText(stx);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
					////inicializa el edittext como un txt de texto y con contraseña o sea los puntitos .
					input.setInputType(InputType.TYPE_CLASS_NUMBER );
				//agrega el edittext al mensajr de dialogo
						builder.setView(input);
					//aceptar

                     builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							m_text = input.getText().toString();
							if(m_text.equals("")){
								Toast.makeText(stx,"hola yesi",Toast.LENGTH_SHORT).show();
							}else{
								Asinregprod al=new Asinregprod();
								al.execute(String.valueOf(codp),String.valueOf(codl),m_text);
							}

						}
					});
					//cancelar
					builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});

					builder.show();


                   //amor cuanto vale cantidad al hacer click. no veo q este inicializado.
				//Asinregprod al = new Asinregprod();// si. ok usaremos otra cosa podes entear a internet y buscar: input
				//	al.execute(String.valueOf(codp),String.valueOf(codl),String.valueOf(cantidad));


				}
			});
			Log.d("listacliente", "Adaptado");

		} catch (Exception e) {
			// TODO: handle exception
			Log.e("lista::Buscar", e.toString());
		}

	}

	private static class ListaViewHolder {
		private TextView textViewTitulo;
		private TextView textViewPrecio;
		private TextView textViewSucursal;

		@SuppressWarnings("unused")
		public ListaViewHolder() {
		}

		public ListaViewHolder(TextView textViewTitulo,TextView textViewPrecio,TextView textViewSucursal) {
			this.textViewTitulo = textViewTitulo;
			this.textViewPrecio=textViewPrecio;
			this.textViewSucursal=textViewSucursal;

		}

		public TextView getTextViewPrecio() {
			return textViewPrecio;
		}

		public TextView getTextViewSucursal() {
			return textViewSucursal;
		}

		public TextView getTextViewTitulo() {
			return textViewTitulo;
		}


	}

	private static class ListaArrayAdapter extends ArrayAdapter<Producto> {

		private LayoutInflater inflater;
		SimpleDateFormat dateFormat;

		public ListaArrayAdapter(Context context, List<Producto> clienteLista) {
			super(context, R.layout.ba_producto, clienteLista);
			// Cache the LayoutInflate to avoid asking for a new one each time.
			inflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(final int position, View convertView,
							ViewGroup parent) {
			// Planet to display
			final Producto milista = (Producto) this.getItem(position);

			// The child views in each row.
			TextView textViewNombre;
			TextView textViewPrecio;
			TextView textViewSucursal;

			// Create a new row view
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.ba_producto,
						null);

				// Find the child views.
				textViewNombre = (TextView) convertView
						.findViewById(R.id.bapo_tv_nombre);
				textViewPrecio = (TextView) convertView
						.findViewById(R.id.bapo_tv_precio);
				textViewSucursal = (TextView) convertView
						.findViewById(R.id.bapo_tv_sucursal);


				// Optimization: Tag the row with it's child views, so we don't
				// have to
				// call findViewById() later when we reuse the row.
				convertView
						.setTag(new ListaViewHolder(textViewNombre,textViewPrecio,textViewSucursal));


				// If CheckBox is toggled, update the planet it is tagged with.

			}
			// Reuse existing row view
			else {
				// Because we use a ViewHolder, we avoid having to call
				// findViewById().
				ListaViewHolder viewHolder = (ListaViewHolder) convertView
						.getTag();
				textViewNombre = viewHolder.getTextViewTitulo();
				textViewPrecio=viewHolder.getTextViewPrecio();
				textViewSucursal=viewHolder.getTextViewSucursal();


			}

			// Tag the CheckBox with the Planet it is displaying, so that we can
			// access the planet in onClick() when the CheckBox is toggled.
			textViewNombre.setText(milista.getNombre());
			textViewPrecio.setText(String.valueOf(milista.getPrecio()));
			textViewSucursal.setText(milista.getMisuper().getNombre());

			return convertView;
		}

	}

	public void lanzarreglis(){
		Intent i=new Intent(this ,Registrar_lis.class) ;

		SharedPreferences prefs =
				getSharedPreferences("SuperConfig",Context.MODE_PRIVATE);
		int Codusu = prefs.getInt("codUsu", -1);
		i.putExtra("Codusu",Codusu);//codigo team 5967
		i.putExtra("Tipo","S");
		startActivity(i);
	}


	private void lanzarlista(int cod){

		Intent i = new Intent(this,Lista_p.class);
		i.putExtra("lista", cod);
		startActivity(i);
	}

	private boolean buscarprod(String Nombre) {
		int estado=-1;
		ArrayList<NameValuePair> postparam= new ArrayList<NameValuePair>();
		//postparam.add(new BasicNameValuePair("Codusu", String.valueOf(codusu)));
		postparam.add(new BasicNameValuePair("Nombre", Nombre));
		String string= post.obtenerrespuesta(postparam, Constantes.URL_buscarp);
		ArrayList<Producto> listclient=new ArrayList<Producto>();
		if(!string.equalsIgnoreCase("")){
			JSONObject json;
			if (string.equals("[]")){}
			else{
			try{
				json=new JSONObject(string);
				JSONArray jsonArray =json.optJSONArray("buscarp");
				for(int i=0; i<jsonArray.length();i++){
					Producto cl=new Producto();
					Super sp=new Super();
					JSONObject jsonarraychild=jsonArray.getJSONObject(i);
					cl.setIdprod(Integer.parseInt(jsonarraychild.optString("Idprod")));
					cl.setCodp(jsonarraychild.optString("Codp"));
					cl.setNombre(jsonarraychild.optString("Nombre"));
					cl.setImagen(jsonarraychild.optString("Imagen"));
					cl.setCodsup(Integer.parseInt(jsonarraychild.optString("Codsup")));
					cl.setCoduni(jsonarraychild.optString("Coduni"));
					cl.setPrecio(Double.parseDouble(jsonarraychild.optString("Precio")));
					sp.setCods(cl.getCodsup());
					sp.setCuenta("");
					sp.setClave("");
					sp.setNombre(jsonarraychild.optString("nombreS"));
					sp.setLatitud(Double.parseDouble(jsonarraychild.optString("Latitud")));
					sp.setLongitud(Double.parseDouble(jsonarraychild.optString("Longitud")));
					cl.setMisuper(sp);//insertar super aqui XD

					listclient.add(cl);

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			}
			//Toast.makeText(getApplicationContext(), "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();

			lista=listclient;

            /*listaClienteAdapter.clear();
            listaClienteAdapter= new ClienteArrayAdapter(Listacliente.this,
                    listclient);
            listaClienteAdapter.notifyDataSetChanged();
            ltcliente.setAdapter(listaClienteAdapter);
*/
			return true;
		}else{
			Log.d("json","error");
			return false;
		}

	}


}
