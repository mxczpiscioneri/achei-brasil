package br.com.mxczpiscioneri.acheibrasil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matheus on 04/05/2016.
 */
public class CidadeListAdapter extends ArrayAdapter<Cidade> {

    private Context context;
    private List<Cidade> itens, itensCopy;

    public CidadeListAdapter(Context context, ArrayList<Cidade> itens) {
        super(context, android.R.layout.simple_dropdown_item_1line, itens);
        this.context = context;
        this.itens = itens;
        this.itensCopy = itens;
    }

    @Override
    public int getCount() {
        return itensCopy.size();
    }

    @Override
    public Cidade getItem(int position) {
        return itensCopy.get(position);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(android.R.layout.simple_dropdown_item_1line, viewGroup, false);
        }

        Cidade cidade = itensCopy.get(i);

        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
        text1.setTag(cidade.getId());
        text1.setText(cidade.getNome());

        return view;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                itensCopy = (ArrayList<Cidade>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<Cidade> FilteredArrList = new ArrayList<Cidade>();

                if (itens == null) {
                    itens = new ArrayList<Cidade>(itensCopy); // saves the original data in mOriginalValues
                }

                if (constraint == null || constraint.length() == 0) {
                    // set the Original result to return
                    results.count = itens.size();
                    results.values = itens;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < itens.size(); i++) {
                        String data = itens.get(i).getNome();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new Cidade(itens.get(i).getId(),itens.get(i).getNome(),itens.get(i).getUf()));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }
}