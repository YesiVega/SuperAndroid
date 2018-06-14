package com.example.pc5.ventasyesi.practi1;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;


import com.example.pc5.ventasyesi.DAL.MarcadorMapa;
import com.example.pc5.ventasyesi.Mapeo.Listas;
import com.example.pc5.ventasyesi.Mapeo.Producto;
import com.example.pc5.ventasyesi.Mapeo.Super;
import com.example.pc5.ventasyesi.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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


//import com.add.presentacion.BAMisTemas;
//import com.example.auladeduelos.R;
//import com.example.practi1..CargarlisTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Lista_p extends Activity {

	private ArrayList<Producto> lista;
	private ArrayAdapter<Producto> listaAdapter;
	private ProgressDialog pDialog;
	WServicePost post;
	ListView list;
	ImageButton ibtn;
	Context stx;
	int usuario;
	int codl;
	private String tipo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listap);
		post=new WServicePost();
		stx=this;
		list=(ListView)findViewById(R.id.lisp_lv_listasp);
        ibtn=(ImageButton)findViewById(R.id.lisp_ib_agregar);
		ibtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//onCreateOptionsMenu(menu) ;
				OpcioneProducto();
			}
		});
		Bundle bung=getIntent().getExtras();
		if(bung!=null){
			//usuario=bung.getInt("codusu");
			codl=bung.getInt("codl");
			tipo=bung.getString("tipo");
			//i.putExtra("tipo", "S");
		}
		Log.d("LISTAP","codl:"+codl);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
			/*	Intent intent = new Intent(Lista_p.this, MiMapa.class);
				Gson g=new Gson();
				Type type=new TypeToken<ArrayList<MarcadorMapa>>() {
				}.getType();
				ArrayList<MarcadorMapa> marcadorMapas=new ArrayList<MarcadorMapa>();
				Producto pp=lista.get(i);
				marcadorMapas.add(new MarcadorMapa(pp.getMisuper().getLatitud(), pp.getMisuper().getLongitud(),
						pp.getMisuper().getNombre(), pp.getNombre() + " a " + pp.getPrecio() + " bs."));
				String marcas=g.toJson(marcadorMapas,type);
				intent.putExtra("marcas",marcas);
				startActivity(intent);*/
			}
		});
	}


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences prefs =
				getSharedPreferences("SuperConfig", Context.MODE_PRIVATE);
		usuario = prefs.getInt("codUsu", -1);
		//descargarlistas(usuario,"S");
		AsinListas al=new AsinListas();
		al.execute();
	}

	public void OpcioneProducto() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				Lista_p.this);
		builder.setTitle("Seleccione una Opción");
		//builder.setIcon(R.drawable.ic_cliente_visitado);
		builder.setSingleChoiceItems(R.array.opciones_producto, 0,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {

					}
				});

		builder.setPositiveButton("Aceptar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						int selectedPosition = ((AlertDialog) dialog)
								.getListView().getCheckedItemPosition();
						switch (selectedPosition) {
							case 0:
								buscarprod();


								Toast.makeText(getApplicationContext(), "Buscar",
										Toast.LENGTH_SHORT).show();
								break;
							case 1:
								escanearprod();
								Toast.makeText(getApplicationContext(),
										"scannear", Toast.LENGTH_SHORT).show();
								break;

						}

					}
				});
		builder.setNegativeButton("Cancelar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Toast.makeText(getApplicationContext(),
								"No Selecciono una opcion", Toast.LENGTH_SHORT).show();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}


	class AsinListas extends AsyncTask<String, String, String>{

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
			Log.d("doin","entre");

			if(descargarlistas()==true){

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
			Log.d("buscardatos","lista.size:"+lista.size());
			if(listaAdapter==null){
				listaAdapter= new ListaArrayAdapter(Lista_p.this,
						lista);
				listaAdapter.setNotifyOnChange(true);


			}else{
				listaAdapter.clear();
				listaAdapter= new ListaArrayAdapter(Lista_p.this,
						lista);
				listaAdapter.notifyDataSetChanged();
			}

			list.setAdapter(listaAdapter);
			list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    /*Intent i = new Intent(Listacliente.this, ListaDocumento.class);
                    i.putExtra("cuenta", cuenta);
                    i.putExtra("codcliente", listaCliente.get(position).getCod());
                    startActivity(i);*/
					OpcioneProducto();
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
			textViewNombre.setText("Nombre:" +milista.getNombre());
			textViewPrecio.setText("Precio:" +String.valueOf(milista.getPrecio())+" Cantidad:"+String.valueOf(milista.getCantidad())+" Total:"+String.valueOf(milista.getCantidad()*milista.getPrecio()) );
			textViewSucursal.setText("Tienda:"+milista.getMisuper().getNombre());

			return convertView;
		}

	}

	public void lanzarMapa(){
		/*Intent i=new Intent(this ,MiMapa.class) ;

		SharedPreferences prefs =
				getSharedPreferences("SuperConfig",Context.MODE_PRIVATE);
		int Codusu = prefs.getInt("codUsu", -1);
		i.putExtra("Codusu",Codusu);//codigo team 5967
		i.putExtra("Tipo","S");
		startActivity(i);*/
	}


	private void lanzarlista(int cod){

		Intent i = new Intent(this,Lista_p.class);
		i.putExtra("lista", cod);
		startActivity(i);
	}

	private boolean descargarlistas() {
		int estado=-1;
		ArrayList<NameValuePair> postparam= new ArrayList<NameValuePair>();
		Log.d("buscarlistas","codl:"+codl);
		postparam.add(new BasicNameValuePair("Codl", String.valueOf(codl)));
		//postparam.add(new BasicNameValuePair("Tipo",tipo));
		String string= post.obtenerrespuesta(postparam, Constantes.URL_listproducto);
		ArrayList<Producto> listclient=new ArrayList<Producto>();
		if(!string.equalsIgnoreCase("")){
			JSONObject json;
			if (string.equals("[]")){}
			else {
				try {

					json = new JSONObject(string);
					JSONArray jsonArray = json.optJSONArray("listasp");
					for (int i = 0; i < jsonArray.length(); i++) {
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
						cl.setCantidad(Integer.parseInt(jsonarraychild.optString("Cantidad")));
						listclient.add(cl);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				//Toast.makeText(getApplicationContext(), "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
			}
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





	public void lanzarListSim(){
		Intent i=new Intent(this ,Listas.class) ;
		startActivity(i);
	}
	


	
	public void buscarprod(){
		Intent i=new Intent(this ,Buscar_producto.class) ;
		Log.d("buscarp","lista_p-codl:"+codl);
		i.putExtra("codl",codl);
		startActivity(i);
	}
	
	public void escanearprod(){
		Intent i=new Intent(this ,Escanear.class) ;
		Log.d("buscarp","lista_p-codl:"+codl);
		i.putExtra("codl",codl);

		startActivity(i);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(0,1,0,"Buscar");
		menu.add(0,2,0,"Escanear");

		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case 1:			
			buscarprod();
			break;
		case 2:
			escanearprod();
			break;
		case 3:			
			break;
		default:
			return false;
		}
		return true;
	}

	 

	 private void lanzarlista(String cod){
			
			Intent i = new Intent(this,Lista_p.class);
			i.putExtra("lista", cod);
		    startActivity(i);
		}


	    

	
	
}
