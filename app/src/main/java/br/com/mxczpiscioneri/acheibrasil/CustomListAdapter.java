package br.com.mxczpiscioneri.acheibrasil;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Matheus on 04/05/2016.
 */
public class CustomListAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<Estabelecimento> itens;

    public CustomListAdapter(Context context, ArrayList<Estabelecimento> itens) {
        super(context, R.layout.list_item_estabelecimento, itens);
        this.context = context;
        this.itens = itens;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRow = layoutInflater.inflate(R.layout.list_item_estabelecimento, null, true);

        TextView txvNome = (TextView) viewRow.findViewById(R.id.txvNome);
        TextView txvEndereco = (TextView) viewRow.findViewById(R.id.txvEndereco);

        try {
            Estabelecimento estabelecimento = itens.get(i);
            txvNome.setText(estabelecimento.getNome());
            txvEndereco.setText(estabelecimento.getEndereco_completo());
        } catch (Exception error) {
            error.printStackTrace();
        }

        return viewRow;
    }
}