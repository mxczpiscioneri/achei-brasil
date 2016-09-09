package br.com.mxczpiscioneri.acheibrasil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Matheus on 18/05/2016.
 */
public class SorteioFechadoFragment extends Fragment {
    private ArrayList<Sorteio> sorteios = new ArrayList<Sorteio>();
    private ListView listaSorteios;
    private ProgressDialog pDialog;
    private CustomListAdapterSorteio adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sorteio_fechado, container, false);

        String url = "http://acheibrasil.net/api/sorteios.php?tipo=2";
        Log.d("logmxcz url sorteio f", url);
        showPDialog();

        JsonArrayRequest jsonReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("logmxcz", response.toString());
                        hidePDialog();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                sorteios.add(new Sorteio(obj.getInt("id_sorteio"), obj.getString("nome"), obj.getString("premio"), obj.getString("inicio"), obj.getString("termino"), 2));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("logmxcz", "Error carregarDetalhes: " + error.getMessage());
                hidePDialog();
                Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.erro), Toast.LENGTH_LONG).show();
            }
        });
        AppVolleyController.getInstance().addToRequestQueue(jsonReq);

        adapter = new CustomListAdapterSorteio(getContext(), sorteios);
        listaSorteios = (ListView) view.findViewById(R.id.listaSorteiosFechado);
        listaSorteios.setAdapter(adapter);

        listaSorteios.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), SorteioActivity.class);
                intent.putExtra("id_sorteio", sorteios.get(position).toString());
                Log.d("logmxcz", "sorteio: " + sorteios.get(position).toString());
                startActivity(intent);
            }
        });

        return view;
    }

    private void showPDialog() {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(getResources().getString(R.string.carregando));
        pDialog.show();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
