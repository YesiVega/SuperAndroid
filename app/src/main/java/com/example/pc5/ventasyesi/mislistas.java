package com.example.pc5.ventasyesi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pc5.ventasyesi.Mapeo.Listas;
import com.example.pc5.ventasyesi.practi1.Ba_listas;
import com.example.pc5.ventasyesi.practi1.Constantes;
import com.example.pc5.ventasyesi.practi1.Lista_p;
import com.example.pc5.ventasyesi.practi1.MiListas;
import com.example.pc5.ventasyesi.practi1.Registrar_lis;
import com.example.pc5.ventasyesi.practi1.WServicePost;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link mislistas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link mislistas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mislistas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private ArrayList<Listas> lista;
    private ArrayAdapter<Listas> listaAdapter;
    private ProgressDialog pDialog;
    WServicePost post;
    ListView list;
    ImageButton ibtn;
    List<Listas> listan;
    int usuario;
    Context stx;


    public mislistas() {
        // Required empty public constructor

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        post=new WServicePost();
        stx=getActivity() ;
        View V = getView();
        super.onActivityCreated(savedInstanceState);
        list=(ListView)V.findViewById(R.id.lis_liv_productos);
        ibtn=(ImageButton)V.findViewById(R.id.lis_ib_agregar);
        ibtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                lanzarreglis();
            }
        });
        SharedPreferences prefs =
                getActivity().getApplicationContext() .getSharedPreferences(Constantes.Configuracion, Context.MODE_PRIVATE);
        usuario = prefs.getInt(Constantes.ConfigUsuario, -1);

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mislistas.
     */
    // TODO: Rename and change types and number of parameters
    public static mislistas newInstance(String param1, String param2) {
        mislistas fragment = new mislistas();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mislistas, container, false);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
           // throw new RuntimeException(context.toString()
           //         + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void cargarmislistas(){
        list=(ListView)getView(). findViewById(R.id.lis_liv_productos);
        if(listan!=null){
            list.setAdapter(new Ba_listas(getActivity(), listan));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int posicion, long id) {
                    // TODO Auto-generated method stub
                    if(posicion>=0){
                        int a=listan.get(posicion).getCodl();
                        lanzarlista(a);

                    }
                }
            });
        }
        // btnmisaulas.setBackgroundColor(R.color.clr_verdeoscuro);
        //  btnyoenseno.setBackgroundColor(R.color.clr_grisclaro);

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
            Log.d("doin","entre");

            if(descargarlistas(Integer.parseInt(params[0]),params[1])==true){

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
                listaAdapter= new ListaArrayAdapter(getActivity(),
                        lista);
                listaAdapter.setNotifyOnChange(true);


            }else{
                listaAdapter.clear();
                listaAdapter= new ListaArrayAdapter(getActivity(),
                        lista);
                listaAdapter.notifyDataSetChanged();
            }

            list.setAdapter(listaAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent i = new Intent(getActivity(), Lista_p.class);
                    i.putExtra("codl", lista.get(position).getCodl());

                    i.putExtra("codusu", usuario);
                    i.putExtra("tipo", "S");
                    startActivity(i);
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


        @SuppressWarnings("unused")
        public ListaViewHolder() {
        }

        public ListaViewHolder(TextView textViewTitulo) {
            this.textViewTitulo = textViewTitulo;


        }


        public TextView getTextViewTitulo() {
            return textViewTitulo;
        }


    }

    private static class ListaArrayAdapter extends ArrayAdapter<Listas> {

        private LayoutInflater inflater;
        SimpleDateFormat dateFormat;

        public ListaArrayAdapter(Context context, List<Listas> clienteLista) {
            super(context, R.layout.ba_listas, clienteLista);
            // Cache the LayoutInflate to avoid asking for a new one each time.
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            // Planet to display
            final Listas milista = (Listas) this.getItem(position);

            // The child views in each row.
            TextView textViewNombre;

            // Create a new row view
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.ba_listas,
                        null);

                // Find the child views.
                textViewNombre = (TextView) convertView
                        .findViewById(R.id.balis_tv_nombre);

                // Optimization: Tag the row with it's child views, so we don't
                // have to
                // call findViewById() later when we reuse the row.
                convertView
                        .setTag(new ListaViewHolder(textViewNombre));

                // If CheckBox is toggled, update the planet it is tagged with.

            }
            // Reuse existing row view
            else {
                // Because we use a ViewHolder, we avoid having to call
                // findViewById().
                ListaViewHolder viewHolder = (ListaViewHolder) convertView
                        .getTag();
                textViewNombre = viewHolder.getTextViewTitulo();


            }

            // Tag the CheckBox with the Planet it is displaying, so that we can
            // access the planet in onClick() when the CheckBox is toggled.
            textViewNombre.setText(milista.getNombre());

            return convertView;
        }

    }

    public void lanzarreglis(){
        Intent i=new Intent(getActivity() ,Registrar_lis.class) ;

        SharedPreferences prefs =getActivity().getApplicationContext().getSharedPreferences(Constantes.Configuracion,Context.MODE_PRIVATE);
        int Codusu = prefs.getInt(Constantes.ConfigUsuario, -1);
        i.putExtra("Codusu",Codusu);//codigo team 5967
        i.putExtra("Tipo","S");
        startActivity(i);
    }


    private void lanzarlista(int cod){

        Intent i = new Intent(getActivity(),Lista_p.class);
        i.putExtra("lista", cod);
        startActivity(i);
    }

    private boolean descargarlistas(int codusu,String tipo) {
        int estado=-1;
        ArrayList<NameValuePair> postparam= new ArrayList<NameValuePair>();
        postparam.add(new BasicNameValuePair("Codusu",String.valueOf(codusu)));
        postparam.add(new BasicNameValuePair("Tipo",tipo));
        String string= post.obtenerrespuesta(postparam, Constantes.URL_listlista);
        ArrayList<Listas> listclient=new ArrayList<Listas>();
        if(!string.equalsIgnoreCase("")){
            JSONObject json;
            try{
                json=new JSONObject(string);
                JSONArray jsonArray =json.optJSONArray("listas");
                for(int i=0; i<jsonArray.length();i++){
                    Listas cl=new Listas();
                    JSONObject jsonarraychild=jsonArray.getJSONObject(i);
                    cl.setCodl(Integer.parseInt(jsonarraychild.optString("Codl")));
                    cl.setNombre(jsonarraychild.optString("Nombre"));
                    cl.setTipo(jsonarraychild.optString("Tipo"));


                    listclient.add(cl);
                }
            } catch (JSONException e) {
                e.printStackTrace();
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
