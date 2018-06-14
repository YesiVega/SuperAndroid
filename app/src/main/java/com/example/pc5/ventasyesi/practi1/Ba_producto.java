package com.example.pc5.ventasyesi.practi1;

import java.util.List;

import com.example.pc5.ventasyesi.Mapeo.Listas;
import com.example.pc5.ventasyesi.R;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Ba_producto extends BaseAdapter {
	 private final Activity actividad;
	  private final List<Listas> lista;

	  public Ba_producto(Activity paramActivity, List<Listas> paramList)
	  {
	    this.actividad = paramActivity;
	    this.lista = paramList;
	  }

	  @Override
	  public int getCount()
	  {
	    return this.lista.size();
	  }

	  @Override
	  public Object getItem(int paramInt)
	  {
	    return this.lista.get(paramInt);
	  }

	  @Override
	  public long getItemId(int paramInt)
	  {
	    return paramInt;
	  }

	  @Override
	  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
	  {
	    View localView = this.actividad.getLayoutInflater().inflate(R.layout.ba_producto, null, true);
	    ((TextView)localView.findViewById(R.id.bapo_tv_nombre)).setText(((Listas)this.lista.get(paramInt)).getNombre());
	   return localView;
	  }

}
