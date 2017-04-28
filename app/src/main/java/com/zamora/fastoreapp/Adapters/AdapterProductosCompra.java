package com.zamora.fastoreapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.zamora.fastoreapp.Clases.Producto;
import com.zamora.fastoreapp.R;

import java.util.ArrayList;

/**
 * Created by Sergio on 13/04/2017.
 */

public class AdapterProductosCompra extends BaseAdapter implements Filterable {

    protected Activity activity;
    private static LayoutInflater inflater = null;

    protected ArrayList<Producto> originalProducts;
    protected ArrayList<Producto> filteredProducts;

    public AdapterProductosCompra(Activity activity, ArrayList<Producto> products) {
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.originalProducts = products;
        this.originalProducts = products;
    }

    @Override
    public int getCount() {
        return filteredProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            v = inflater.inflate(R.layout.item_producto_compra, null);
        }

        Producto producto = filteredProducts.get(position);

        ImageView imagen = (ImageView) v.findViewById(R.id.productImage);
        imagen.setImageResource(Integer.parseInt(producto.getImagen()));

        TextView nombre = (TextView) v.findViewById(R.id.productName);
        nombre.setText(producto.getNombre());

        //v.setBackgroundColor(Color.parseColor("#3f834D"));
        v.setPadding(50,50,50,50);
        return v;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint == null || constraint.length() == 0){
                    results.values = originalProducts;
                    results.count = originalProducts.size();
                }
                else{
                    String filterString = constraint.toString().toLowerCase();

                    ArrayList<Producto> filterResultsData = new ArrayList<>();
                    for (Producto data : originalProducts){
                        if (data.getNombre().toLowerCase().contains(filterString)){
                            filterResultsData.add(data);
                        }
                    }
                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredProducts = (ArrayList<Producto>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
