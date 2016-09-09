package br.com.mxczpiscioneri.acheibrasil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Matheus on 04/05/2016.
 */
public class CustomListAdapterSorteio extends ArrayAdapter {

    private Context context;
    private ArrayList<Sorteio> itens;

    public CustomListAdapterSorteio(Context context, ArrayList<Sorteio> itens) {
        super(context, R.layout.list_item_sorteio, itens);
        this.context = context;
        this.itens = itens;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRow = layoutInflater.inflate(R.layout.list_item_sorteio, null, true);

        TextView txvSorteioNome = (TextView) viewRow.findViewById(R.id.txvSorteioNome);
        TextView txvSorteioPremio = (TextView) viewRow.findViewById(R.id.txvSorteioPremio);
        TextView txvSorteioData = (TextView) viewRow.findViewById(R.id.txvSorteioData);

        Sorteio sorteio = itens.get(i);

        txvSorteioNome.setText(sorteio.getNome());
        txvSorteioPremio.setText(sorteio.getPremio());
        if (sorteio.getTipo() == 1) {
            txvSorteioData.setText("Início: " + sorteio.getInicio());
        } else {
            txvSorteioData.setText("Encerrado: " + sorteio.getTermino());
        }

        return viewRow;
    }
}