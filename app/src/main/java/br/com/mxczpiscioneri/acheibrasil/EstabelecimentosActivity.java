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
import android.widget.AdapterView;
import android.widget.ListView;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class EstabelecimentosActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Estabelecimento> estabelecimentos = new ArrayList<Estabelecimento>();
    private ListView listaEstabelecimentos;
    private ProgressDialog pDialog;
    private CustomListAdapter adapter;
    private String cidade;
    private String categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estabelecimentos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        cidade = intent.getStringExtra("cidade");
        categoria = intent.getStringExtra("categoria");

        try {
            categoria = URLEncoder.encode(categoria, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = "http://acheibrasil.net/api/estabelecimentos.php?cidade=" + cidade + "&categoria=" + categoria;
        Log.d("logmxcz url estabeleci", url);
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
                                estabelecimentos.add(new Estabelecimento(obj.getInt("id_estabelecimento"), obj.getString("nome"), obj.getString("endereco_completo")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
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

        adapter = new CustomListAdapter(this, estabelecimentos);
        listaEstabelecimentos = (ListView) findViewById(R.id.listaEstabelecimentos);
        listaEstabelecimentos.setAdapter(adapter);

        listaEstabelecimentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EstabelecimentosActivity.this, DetalhesActivity.class);
                intent.putExtra("id_estabelecimento", estabelecimentos.get(position).toString());
                intent.putExtra("id_cidade", cidade);
                startActivity(intent);
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
            Intent intent = new Intent(EstabelecimentosActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_sorteio) {
            Intent intent = new Intent(EstabelecimentosActivity.this, SorteiosActivity.class);
            intent.putExtra("id_cidade", cidade);
            startActivity(intent);
        } else if (id == R.id.nav_parceiro) {
            Intent intent = new Intent(EstabelecimentosActivity.this, ParceiroActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_somos) {
            Intent intent = new Intent(EstabelecimentosActivity.this, QuemSomosActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_contato) {
            Intent intent = new Intent(EstabelecimentosActivity.this, ContatoActivity.class);
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
