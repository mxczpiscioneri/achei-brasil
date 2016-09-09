package br.com.mxczpiscioneri.acheibrasil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AutoCompleteTextView autoCompleteCidades;
    private AutoCompleteTextView autoCompleteCategorias;
    private Button btnEstabelecimentos;
    private ProgressDialog pDialog;
    private ArrayList<Cidade> listaCidades = new ArrayList<Cidade>();
    private List<String> listaCategorias = new ArrayList<>();
    private CidadeListAdapter adapterCidades;
    private Intent intent;
    //private ArrayAdapter<String> adapterCidades;
    private ArrayAdapter<String> adapterCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        intent = new Intent(MainActivity.this, EstabelecimentosActivity.class);

        btnEstabelecimentos = (Button) findViewById(R.id.btnEstabelecimentos);
        btnEstabelecimentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (autoCompleteCidades.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.erroCidade), Toast.LENGTH_LONG).show();
                } else if (autoCompleteCategorias.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.erroCategoria), Toast.LENGTH_LONG).show();
                } else {
                    startActivity(intent);
                }
            }
        });

        autoCompleteCidades = (AutoCompleteTextView) findViewById(R.id.autoCompleteCidades);
        autoCompleteCategorias = (AutoCompleteTextView) findViewById(R.id.autoCompleteCategorias);

        carregarCidades();
    }

    private void carregarCidades() {
        String url = "http://acheibrasil.net/api/cidades.php";
        Log.d("logmxcz url cidade", url);
        showPDialog();

        autoCompleteCidades.setText("");

        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("logmxcz response", response.toString());
                        hidePDialog();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Cidade cidade = new Cidade(obj.getInt("id_cidade"), obj.getString("nome"), obj.getString("uf"));
                                listaCidades.add(cidade);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("logmxcz", "Error carregarCidades: " + error.getMessage());
                hidePDialog();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.erro), Toast.LENGTH_LONG).show();
            }
        });
        AppVolleyController.getInstance().addToRequestQueue(movieReq);

        adapterCidades = new CidadeListAdapter(this, listaCidades);

        autoCompleteCidades.setAdapter(adapterCidades);
        autoCompleteCidades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("cidade", view.getTag().toString());
                autoCompleteCategorias.setText("");
                carregarCategorias(view.getTag().toString());
                autoCompleteCategorias.requestFocus();
            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void carregarCategorias(String cidade) {
        String url = "http://acheibrasil.net/api/categorias.php?cidade=" + cidade;
        Log.d("logmxcz url categoria", url);
        showPDialog();

        JsonArrayRequest jsonReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("logmxcz", response.toString());
                        hidePDialog();
                        listaCategorias.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                listaCategorias.add(obj.getString("categoria"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("logmxcz", "Error carregarCategorias: " + error.getMessage());
                hidePDialog();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.erro), Toast.LENGTH_LONG).show();
            }
        });
        AppVolleyController.getInstance().addToRequestQueue(jsonReq);

        adapterCategorias = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listaCategorias);

        autoCompleteCategorias.setAdapter(adapterCategorias);
        autoCompleteCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("categoria", parent.getItemAtPosition(position).toString());
            }
        });
    }

    private void showPDialog() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getResources().getString(R.string.carregando));
        pDialog.show();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_sorteio) {
            Intent intent = new Intent(MainActivity.this, SorteiosActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_parceiro) {
            Intent intent = new Intent(MainActivity.this, ParceiroActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_somos) {
            Intent intent = new Intent(MainActivity.this, QuemSomosActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_contato) {
            Intent intent = new Intent(MainActivity.this, ContatoActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        autoCompleteCidades.setText("");
        autoCompleteCategorias.setText("");
        autoCompleteCidades.requestFocus();

        Tracker t = ((AnalyticsApplication) getApplication()).getTracker(AnalyticsApplication.TrackerName.APP_TRACKER);
        t.setScreenName(getTitle().toString());
        t.enableAdvertisingIdCollection(true);
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    @Override
    protected void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }
}
