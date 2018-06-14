package com.example.pc5.ventasyesi.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import com.example.pc5.ventasyesi.Mapeo.*;


/**
 * Created by yesica on 17/07/2016.
 */
public class Manager {
    private SQLiteDatabase database;
    private SQLiteHelper helper;

    public Manager(Context paramContext)
    {
        this.helper = new SQLiteHelper(paramContext);
    }

    public void openDB()
    {
        this.database = this.helper.getWritableDatabase();
    }

    private Producto cursorToProducto(Cursor paramCursor)
    {
        Producto pr = new Producto();
        pr.setIdprod(paramCursor.getInt(0));
        pr.setCodp(paramCursor.getString(1));
        pr.setNombre(paramCursor.getString(2));
        pr.setPrecio(paramCursor.getDouble(3));
        pr.setImagen(paramCursor.getString(4));
        pr.setCoduni(paramCursor.getString(5));
        pr.setCodsup(paramCursor.getInt(6));
        return pr;
    }

    private Unidad cursorToUnidad(Cursor paramCursor)
    {
        Unidad un = new Unidad();
        un.setCodu(paramCursor.getString(0));
        un.setNombre(paramCursor.getString(1));

        return un;
    }

    private Super cursorToSuper(Cursor paramCursor)
    {
        Super su = new Super();
        su.setCods(paramCursor.getInt(0));
        su.setNombre(paramCursor.getString(1));
        su.setLongitud(paramCursor.getDouble(2));
        su.setLatitud(paramCursor.getDouble(3));
        su.setCuenta(paramCursor.getString(4));
        su.setClave(paramCursor.getString(5));
        return su;
    }
    private DetalleList cursorToDetalleList(Cursor paramCursor)
    {
        DetalleList dl = new DetalleList();
        dl.setIds(paramCursor.getInt(0));
        dl.setCodp(paramCursor.getInt(1));
        dl.setCodl(paramCursor.getInt(2));
        dl.setCantidad(paramCursor.getInt(3));
        dl.setEstado(paramCursor.getString(4));
        return dl;
    }

    private Listas cursorToListas(Cursor paramCursor)
    {
        Listas li = new Listas();
        li.setCodl(paramCursor.getInt(0));
        li.setNombre(paramCursor.getString(1));
        li.setTipo(paramCursor.getString(2));

        return li;
    }


    private Usuario cursorToUsuario(Cursor paramCursor)
    {

        Usuario us = new Usuario();
        us.setCodusu(paramCursor.getInt(0));
        us.setNick(paramCursor.getString(1));
        us.setTelef(paramCursor.getString(2));

        return us;

    }

    private Listusu cursorToListusu(Cursor paramCursor)
    {

        Listusu lu = new Listusu();
        lu.setIds(paramCursor.getInt(0));
        lu.setCodl(paramCursor.getInt(1));
        lu.setCodusu(paramCursor.getInt(2));
        lu.setCargo(paramCursor.getString(3));



        return lu;

    }

    public void closeDB()
    {
        this.database.close();
    }

    public void deleteDatos(String paramString1, String paramString2)
    {
        try
        {
            this.database.delete(paramString1, paramString2, null);
            return;
        }
        catch (Exception localException)
        {
            Log.i("Error", localException.getMessage());
        }
    }



    public ArrayList<Producto> getListaProducto()
    {
        ArrayList<Producto> prod = new ArrayList<Producto>();
        Cursor localCursor = this.database.rawQuery(" select "+SQLiteHelper.columnas_Producto+
                " from "+ SQLiteHelper.TABLA_Producto, null);
        while (localCursor.moveToNext())
        {
            prod.add(cursorToProducto(localCursor));
        }
        localCursor.close();
        return prod;
    }

    public ArrayList<Unidad> getListaUnidad()
    {
        ArrayList<Unidad> uni = new ArrayList<Unidad>();
        Cursor localCursor = this.database.rawQuery(" select "+SQLiteHelper.columnas_Unidad+
                " from "+ SQLiteHelper.TABLA_Unidad, null);
        while (localCursor.moveToNext())
        {
            uni.add(cursorToUnidad(localCursor));
        }
        localCursor.close();
        return uni;
    }

    public ArrayList<Producto> getListaProducto(String cuenta)
    {
        ArrayList<Producto> prod = new ArrayList<Producto>();
        Cursor localCursor = this.database.rawQuery(" select "+SQLiteHelper.columnas_Producto+
                " from "+ SQLiteHelper.TABLA_Producto+
                " where cuenta='"+cuenta+"'", null);
        while (localCursor.moveToNext())
        {
            prod.add(cursorToProducto(localCursor));
        }
        localCursor.close();
        return prod;
    }

    public ArrayList<Usuario> getListaUsuario()
    {
        ArrayList<Usuario> detped = new ArrayList<Usuario>();
        Cursor localCursor = this.database.rawQuery(" select "+SQLiteHelper.columnas_Usuario+
                " from "+ SQLiteHelper.TABLA_Usuario, null);
        while (localCursor.moveToNext())
        {
            detped.add(cursorToUsuario(localCursor));
        }
        localCursor.close();
        return detped;

    }

    public ArrayList<Super> getListaSuper()
    {
        ArrayList<Super> detped = new ArrayList<Super>();
        Cursor localCursor = this.database.rawQuery(" select " + SQLiteHelper.columnas_Super +
                " from " + SQLiteHelper.TABLA_Super, null);
        while (localCursor.moveToNext())
        {
            detped.add(cursorToSuper(localCursor));
        }
        localCursor.close();
        return detped;

    }

    public ArrayList<Listas> getListaListas()
    {
        ArrayList<Listas> detped = new ArrayList<Listas>();
        Cursor localCursor = this.database.rawQuery(" select " + SQLiteHelper.columnas_Listas +
                " from " + SQLiteHelper.TABLA_Listas, null);
        while (localCursor.moveToNext())
        {
            detped.add(cursorToListas(localCursor));
        }
        localCursor.close();
        return detped;

    }

    public ArrayList<DetalleList> getListaDetalleList()
    {
        ArrayList<DetalleList> detped = new ArrayList<DetalleList>();
        Cursor localCursor = this.database.rawQuery(" select " + SQLiteHelper.columnas_DetalleList +
                " from " + SQLiteHelper.TABLA_DetalleList, null);
        while (localCursor.moveToNext())
        {
            detped.add(cursorToDetalleList(localCursor));
        }
        localCursor.close();
        return detped;

    }

    public ArrayList<Listusu> getListaListusu()
    {
        ArrayList<Listusu> detped = new ArrayList<Listusu>();
        Cursor localCursor = this.database.rawQuery(" select " + SQLiteHelper.columnas_Listusu +
                " from " + SQLiteHelper.TABLA_Listusu, null);
        while (localCursor.moveToNext())
        {
            detped.add(cursorToListusu(localCursor));
        }
        localCursor.close();
        return detped;

    }

   /* public int getSigProucto() throws Exception {
        try {
            Cursor cursor = this.database.rawQuery(" select Max(cod) " +
                    " from " + SQLiteHelper.TABLA_clientes, null);
            int cantidad = 0;
            if (cursor.moveToNext()) {
                cantidad = cursor.getInt(0);
            }
            cursor.close();
            return cantidad+1;

        } catch (Exception e) {
            throw e;
        }
    }*/

    /*public int getSigDocumentos() throws Exception {
        try {
            Cursor cursor = this.database.rawQuery(" select Max(cod) " +
                    " from " + SQLiteHelper.TABLA_documentos, null);
            int cantidad = 0;
            if (cursor.moveToNext()) {
                cantidad = cursor.getInt(0);
            }
            cursor.close();
            return cantidad+1;

        } catch (Exception e) {
            throw e;
        }
    }*/


    public int getSigListas() throws Exception {
        try {
            Cursor cursor = this.database.rawQuery(" select Max(Codl) " +
                    " from " + SQLiteHelper.TABLA_Listas, null);
            int cantidad = 0;
            if (cursor.moveToNext()) {
                cantidad = cursor.getInt(0);
            }
            cursor.close();
            return cantidad+1;

        } catch (Exception e) {
            throw e;
        }
    }



    /*public ArrayList<Documento> getListaDocumentos(int cliente)
    {
        ArrayList<Documento> detped = new ArrayList<Documento>();
        Cursor localCursor = this.database.rawQuery(" select " + SQLiteHelper.columnas_documentos +
                " from " + SQLiteHelper.TABLA_documentos +
                " where cliente=" + cliente , null);
        while (localCursor.moveToNext())
        {
            detped.add(cursorToDocumento(localCursor));
        }
        localCursor.close();
        return detped;

    }*/


    public Producto getProducto(int id)
    {
        Cursor localCursor = this.database.rawQuery(" select " + SQLiteHelper.columnas_Producto +
                " from " + SQLiteHelper.TABLA_Producto +
                " where Idprod="+String.valueOf(id), null);
        localCursor.moveToFirst();
        Producto localProducto = cursorToProducto(localCursor);
        localCursor.close();
        return localProducto;
    }

    public Unidad getUnidad(String id)
    {
        Cursor localCursor = this.database.rawQuery(" select " + SQLiteHelper.columnas_Unidad +
                " from " + SQLiteHelper.TABLA_Unidad +
                " where Codu="+String.valueOf(id), null);
        localCursor.moveToFirst();
        Unidad localUnidad = cursorToUnidad(localCursor);
        localCursor.close();
        return localUnidad;
    }

    public Super getSuper(int id)
    {
        Cursor localCursor = this.database.rawQuery(" select " + SQLiteHelper.columnas_Super+
                " from " + SQLiteHelper.TABLA_Super +
                " where Cods="+String.valueOf(id), null);
        localCursor.moveToFirst();
        Super localSuper = cursorToSuper(localCursor);
        localCursor.close();
        return localSuper;
    }

    public Listas getListas(int id)
    {
        Cursor localCursor = this.database.rawQuery(" select " + SQLiteHelper.columnas_Listas+
                " from " + SQLiteHelper.TABLA_Listas +
                " where Codl="+String.valueOf(id), null);
        localCursor.moveToFirst();
        Listas localListas = cursorToListas(localCursor);
        localCursor.close();
        return localListas;
    }

    public DetalleList getDetalleList(int id)//aqui como hago es una intermdia
    {
        Cursor localCursor = this.database.rawQuery(" select " + SQLiteHelper.columnas_DetalleList+
                " from " + SQLiteHelper.TABLA_DetalleList +
                " where Id="+String.valueOf(id), null);
        localCursor.moveToFirst();
        DetalleList localDetalleList = cursorToDetalleList(localCursor);
        localCursor.close();
        return localDetalleList;
    }

    public Usuario getUsuario(int id)
    {
        Cursor localCursor = this.database.rawQuery(" select " + SQLiteHelper.columnas_Usuario +
                " from " + SQLiteHelper.TABLA_Usuario +
                " where Codusu="+String.valueOf(id), null);
        localCursor.moveToFirst();
        Usuario localUsuario = cursorToUsuario(localCursor);
        localCursor.close();
        return localUsuario;
    }

    public Listusu getListusu(int id)//aqui como hago es una intermdia
    {
        Cursor localCursor = this.database.rawQuery(" select " + SQLiteHelper.columnas_Listusu+
                " from " + SQLiteHelper.TABLA_Listusu +
                " where Id="+String.valueOf(id), null);
        localCursor.moveToFirst();
        Listusu localListusu = cursorToListusu(localCursor);
        localCursor.close();
        return localListusu;
    }

    public Usuario getUsuario(String Nick)//parametro es la llave
    {
        Cursor localCursor = this.database.rawQuery(" select " + SQLiteHelper.columnas_Usuario +
                " from " + SQLiteHelper.TABLA_Usuario +
                " where Nick='"+Nick+"'", null);
        Usuario localUsuario=null;
        if (localCursor.moveToFirst()){
            localUsuario = cursorToUsuario(localCursor);
        }
        localCursor.close();

        return localUsuario;
    }




    public void insertProducto(Producto param)
    {

        ContentValues localContentValues = new ContentValues();
        localContentValues.put("Idprod", param.getIdprod());
        localContentValues.put("Codp", param.getCodp());
        localContentValues.put("Nombre", param.getNombre());
        localContentValues.put("Precio", param.getPrecio());
        localContentValues.put("Imagen", param.getImagen());
        localContentValues.put("Coduni", param.getCoduni());
        localContentValues.put("Codsup", param.getCodsup());


        try
        {
            // Cliente c=getCliente(param.getCod());
            //if (c==null){
            this.database.insert(SQLiteHelper.TABLA_Producto, null, localContentValues);
            //}
            return;
        }
        catch (Exception localException)
        {
            Log.i("Error", localException.getMessage());
        }
    }

    public void insertUnidad(Unidad param)
    {

        ContentValues localContentValues = new ContentValues();
        localContentValues.put("Codu", param.getCodu());
        localContentValues.put("Nombre", param.getNombre());

        try
        {
            // Cliente c=getCliente(param.getCod());
            //if (c==null){
            this.database.insert(SQLiteHelper.TABLA_Unidad, null, localContentValues);
            //}
            return;
        }
        catch (Exception localException)
        {
            Log.i("Error", localException.getMessage());
        }
    }

    public void insertSuper(Super param)
    {

        ContentValues localContentValues = new ContentValues();
        localContentValues.put("Cods", param.getCods());
        localContentValues.put("Nombre", param.getNombre());
        localContentValues.put("Longitud", param.getLongitud());
        localContentValues.put("Latitud", param.getLatitud());
        localContentValues.put("Cuenta", param.getCuenta());
        localContentValues.put("Clave", param.getClave());

        try
        {
            // Cliente c=getCliente(param.getCod());
            //if (c==null){
            this.database.insert(SQLiteHelper.TABLA_Super, null, localContentValues);
            //}
            return;
        }
        catch (Exception localException)
        {
            Log.i("Error", localException.getMessage());
        }
    }

    public void insertListas(Listas param)
    {

        ContentValues localContentValues = new ContentValues();
        localContentValues.put("Codl", param.getCodl());
        localContentValues.put("Nombre", param.getNombre());
        localContentValues.put("Tipo", param.getTipo());


        try
        {
            // Cliente c=getCliente(param.getCod());
            //if (c==null){
            this.database.insert(SQLiteHelper.TABLA_Listas, null, localContentValues);
            //}
            return;
        }
        catch (Exception localException)
        {
            Log.i("Error", localException.getMessage());
        }
    }

    public void insertUsuario(Usuario param)
    {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("Codusu", param.getCodusu());
        localContentValues.put("Nick", param.getNick());
        localContentValues.put("Telef", param.getTelef());


        try
        {
            this.database.insert(SQLiteHelper.TABLA_Usuario, null, localContentValues);
            return;
        }
        catch (Exception localException)
        {
            Log.i("Error", localException.getMessage());
        }
    }


    public void insertDetalleList(DetalleList param)
    {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("Id", param.getIds());
        localContentValues.put("Codp", param.getCodp());
        localContentValues.put("Codl", param.getCodl());
        localContentValues.put("Cantidad", param.getCantidad());
        localContentValues.put("Estado", param.getEstado());

        try
        {
            this.database.insert(SQLiteHelper.TABLA_DetalleList, null, localContentValues);
            return;
        }
        catch (Exception localException)
        {
            Log.i("Error", localException.getMessage());
        }
    }

    public void insertListusu(Listusu param)
    {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("Id", param.getIds());
        localContentValues.put("Codl", param.getCodl());
        localContentValues.put("Codusu", param.getCodusu());
        localContentValues.put("Cargo", param.getCargo());

        try
        {
            this.database.insert(SQLiteHelper.TABLA_Listusu, null, localContentValues);
            return;
        }
        catch (Exception localException)
        {
            Log.i("Error", localException.getMessage());
        }
    }

    public void updateDatos(String tabla, ContentValues ContentValues, String where)
    {
        try
        {
            this.database.update(tabla, ContentValues, where, null);
            return;
        }
        catch (Exception localException)
        {
            Log.i("Error", localException.getMessage());
        }
    }

}
