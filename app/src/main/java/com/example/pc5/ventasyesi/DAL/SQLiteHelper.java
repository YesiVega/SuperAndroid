package com.example.pc5.ventasyesi.DAL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by yesica on 17/07/2016.
 */
public class SQLiteHelper   extends SQLiteOpenHelper {

    private static final String CREATE_TABLA_Producto = "CREATE  TABLE if not exists Producto ("+
            "Idprod int(11) PRIMARY KEY NOT NULL, " +
            "Codp TEXT NOT NULL , " +
            "Nombre TEXT NOT NULL , " +
            "Precio DUBLE NOT NULL , " +
            "Imagen MEDIUMBLOB NOT NULL )";
    private static final String CREATE_TABLA_Unidad = "CREATE  TABLE if not exists Unidad ("+
            "Codu TEXT PRIMARY KEY NOT NULL, " +
            "Nombre TEXT NOT NULL )";
    private static final String CREATE_TABLA_Super = "CREATE  TABLE if not exists Super ("+
            "Cods int(11) PRIMARY KEY NOT NULL, " +
            "Nombre TEXT NOT NULL , " +
            "Longitud DOUBLE NOT NULL , " +
            "Latitud DOUBLE NOT NULL , " +
            "Cuenta TEXT NOT NULL , " +
            "Clave TEXT NOT NULL )";
    private static final String CREATE_TABLA_Listas = "CREATE  TABLE if not exists Listas ("+
            "Codl int(11) PRIMARY KEY NOT NULL, " +
            "Nombre TEXT NOT NULL , " +
            "Tipo TEXT NOT NULL )";

    private static final String CREATE_TABLA_DetalleList = "CREATE  TABLE if not exists DetalleList ("+
            "Id int(11) PRIMARY KEY NOT NULL, " +
            "Codp int(11) NOT NULL, " +
            "Codl int(11) NOT NULL , " +
            "Cantidad int(11) NOT NULL , " +
            "Estado int(11) NOT NULL )";
    private static final String CREATE_TABLA_Usuario = "CREATE  TABLE if not exists Usuario ("+
            "Codusu int(11) PRIMARY KEY NOT NULL, " +
            "Nick TEXT NOT NULL , " +
            "Telef TEXT NOT NULL )";
    private static final String CREATE_TABLA_Listusu = "CREATE  TABLE if not exists Listusu ("+
            "Id int(11) PRIMARY KEY NOT NULL, " +
            "Codl int(11) NOT NULL , " +
            "Codusu int(11) NOT NULL , " +
            "Cargo TEXT NOT NULL )";

    public static final String columnas_Producto = "Idprod,Codp,Nombre,Precio,Imagen ";
    public static final String columnas_Unidad = " Codu,Nombre";
    public static final String columnas_Super = " Cods,Nombre,Longitud,Latitud,Cuenta,Clave ";
    public static final String columnas_Listas = " Codl,Nombre,Tipo ";
    public static final String columnas_DetalleList = " Id,Codp,Codl,Cantidad,Estado";
    public static final String columnas_Usuario = " Codusu,Nick,Telef";
    public static final String columnas_Listusu = " Id,Codl,Codusu,Cargo ";


    private static final String DATABASE_NAME = "BDCobros.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLA_Producto = "Producto";
    public static final String TABLA_Unidad = "Unidad";
    public static final String TABLA_Super = "Super";
    public static final String TABLA_Listas = "Listas";
    public static final String TABLA_DetalleList = "DetalleList";
    public static final String TABLA_Usuario = "Usuario";
    public static final String TABLA_Listusu = "Listausu";



    public SQLiteHelper(Context paramContext)
    {
        super(paramContext, DATABASE_NAME, null, 1);
    }



    @Override
    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
        Log.w(SQLiteHelper.class.getSimpleName(), "Actualizando base de datos de la version " + paramInt1 + " a la " + paramInt2 + ", que eliminara todos los datos");
        paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLA_Producto);
        paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLA_Unidad);
        paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLA_Super);
        paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLA_Listas);
        paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLA_DetalleList);
        paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLA_Usuario);
        paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLA_Listusu);
        onCreate(paramSQLiteDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLA_Producto);
        db.execSQL(CREATE_TABLA_Unidad);
        db.execSQL(CREATE_TABLA_Super);
        db.execSQL(CREATE_TABLA_Listas);
        db.execSQL(CREATE_TABLA_DetalleList);
        db.execSQL(CREATE_TABLA_Usuario);
        db.execSQL(CREATE_TABLA_Listusu);


    }



}
