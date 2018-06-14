package com.example.pc5.ventasyesi.practi1;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import com.example.pc5.ventasyesi.Mapeo.Listas;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Servidor {

	private static final String TAG = Servidor.class.getSimpleName();
	private static final String NAMESPACE = "http://Servidor/";
	//private static final String URL = "http://192.168.43.178:8080/ServerSuperM/WSSuper?wsdl";
	private static final String URL = "http://192.168.43.25:8080/ServerSuperM/WSSuper?wsdl";

	private static final String M_HELLO = "hello";
	private static final String AS_HELLO = NAMESPACE + M_HELLO;	
	
	private static final String M_obtenerlista = "ObtenerLista";
	private static final String AS_obtenerlista = NAMESPACE + M_obtenerlista;
	
	private static final String M_crearlista = "CrearLista";
	private static final String AS_crearlista = NAMESPACE + M_crearlista;

	private static final String M_verifusu = "verificarusu";
	private static final String AS_verifusu = NAMESPACE + M_verifusu;

	private static final String M_regiusu = "registrar_usu";
	private static final String AS_regiusu = NAMESPACE + M_regiusu;
	private Gson mGson;

	public Servidor() {
		mGson = new Gson();
	}

	public String hello(String v) throws IOException,
			XmlPullParserException {

		//String SG=mGson.toJson(usu);
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("name",v );

		SoapPrimitive result = (SoapPrimitive) consumir(AS_HELLO,
				M_HELLO, parametros);

		if(result.toString().equals("null")) {
			return "";
		}else{
			return result.toString();
		}
	}

	public List<Listas> obtenerlistasS(String u) throws IOException,
	XmlPullParserException {

		HashMap<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("usuario", u);
		//SoapPrimitive result = (SoapPrimitive) consumir(AS_Registrar_Usuario,
		//M_Registrar_Usuario, parametros);
		
		SoapPrimitive result = (SoapPrimitive) consumir(AS_obtenerlista, M_obtenerlista, parametros);
		Log.d("Servicio OMA", result.toString());
		
		if(result.toString().equals("N")) {
			return new ArrayList<Listas>();
		}else{
			 Gson g=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
			 Type type = new TypeToken<List<Listas>>(){}.getType();
		    List<Listas> a=g.fromJson(result.toString(), type);
			return a;
		}
		
	}
	
	public Listas crearlistas(String u) throws IOException,
	XmlPullParserException {

		HashMap<String, Object> parametros = new HashMap<String, Object>();
		Log.d("Servicio U", u.toString());
		parametros.put("lista", u);
		
		
		SoapPrimitive result = (SoapPrimitive) consumir(AS_crearlista, M_crearlista, parametros);
		Log.d("Servicio OMA", result.toString());
		
		if(result.toString().equals("N")) {
			return null;
		}else{
			Gson g=  new Gson();
			Type tipo = new TypeToken<Listas>() {
			}.getType();
			Listas a=g.fromJson(result.toString(),tipo);
			return a;
		}
		
	}
	
	public String verifusu(String u) throws IOException,
	XmlPullParserException {

		HashMap<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("usuario", u);
		
		SoapPrimitive result = (SoapPrimitive) consumir(AS_verifusu, M_verifusu, parametros);
		Log.d("Servicio OMA", result.toString());
		
		return result.toString();
		
		
	}
	
	public String regiusu(String u) throws IOException,
	XmlPullParserException {

		HashMap<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("parameter", u);
		
		SoapPrimitive result = (SoapPrimitive) consumir(AS_regiusu, M_regiusu, parametros);
		Log.d("Servicio OMA", result.toString());
		
		return result.toString();
		
		
	}
	protected Object consumir(String accionSoap, String metodo,
			Map<String, Object> parametros) throws IOException,
			XmlPullParserException {
		Log.d("mostrar","1.1");

		// Request
		SoapObject soapObject = new SoapObject(NAMESPACE, metodo);
		if (parametros != null) {
			for (Map.Entry<String, Object> param : parametros.entrySet()) {
				soapObject.addProperty(param.getKey(), param.getValue());
			}
		}
		Log.d("mostrar","1.2");

		// Sobre
		SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		soapEnvelope.setOutputSoapObject(soapObject);
		Log.d("mostrar", "1.3");
		// Marshal
		MarshalFloat marshalFloat = new MarshalFloat();
		marshalFloat.register(soapEnvelope);
		Log.d("mostrar", "1.4");
		// Transporte
		HttpTransportSE httpTransport = new HttpTransportSE(URL);
		Log.d("mostrar","1.5");
		try {
			httpTransport.call(accionSoap, soapEnvelope);
			Log.d("mostrar", "1.6");
			if(soapEnvelope!=null) {
				return soapEnvelope.getResponse();
			} else{
				Log.d(TAG,"1.7");
				return null ;
			}

		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
			throw new IOException(
					"GenericWebService:consumir:Error de conexion con el servidor. "
							+ e.getMessage());
		} catch (XmlPullParserException e) {
			Log.e(TAG, e.getMessage());
			throw new XmlPullParserException(
					"GeneriWebService::consumir:Error al hacer el parser en la respuesta. "
							+ e.getMessage());
		}

	}
	
	
}
