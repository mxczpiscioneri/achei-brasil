package br.com.mxczpiscioneri.acheibrasil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SorteioActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressDialog pDialog;
    private Sorteio sorteio = new Sorteio();
    private String idSorteio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorteio);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPageSorteio);
        SorteioImageAdapter adapterView = new SorteioImageAdapter(this);
        mViewPager.setAdapter(adapterView);

        Intent intent = getIntent();
        idSorteio = intent.getStringExtra("id_sorteio");

        String url = "http://acheibrasil.net/api/sorteio.php?id=" + idSorteio;
        Log.d("logmxcz url sorteio", url);
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

                                sorteio.setId(obj.getInt("id_sorteio"));
                                sorteio.setNome(obj.getString("nome"));
                                sorteio.setPremio(obj.getString("premio"));
                                sorteio.setFoto1(obj.getString("foto1"));
                                sorteio.setFoto2(obj.getString("foto2"));
                                sorteio.setInicio(obj.getString("inicio"));
                                sorteio.setTermino(obj.getString("termino"));
                                sorteio.setGanhador1(obj.getString("ganhador1"));
                                sorteio.setGanhador2(obj.getString("ganhador2"));
                                sorteio.setGanhador3(obj.getString("ganhador3"));
                                sorteio.setGanhador4(obj.getString("ganhador4"));
                                sorteio.setGanhador5(obj.getString("ganhador5"));
                                sorteio.setGanhador6(obj.getString("ganhador6"));
                                sorteio.setGanhador7(obj.getString("ganhador7"));
                                sorteio.setGanhador8(obj.getString("ganhador8"));
                                sorteio.setGanhador9(obj.getString("ganhador9"));
                                sorteio.setGanhador10(obj.getString("ganhador10"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        loadData();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("logmxcz", "Error carregarDetalhes: " + error.getMessage());
                hidePDialog();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.erro), Toast.LENGTH_LONG).show();
            }
        });
        AppVolleyController.getInstance().addToRequestQueue(jsonReq);

    }

    private void loadData() {
        ImageLoader imageLoader = AppVolleyController.getInstance().getImageLoader();

        NetworkImageView foto1 = (NetworkImageView) findViewById(R.id.foto1);
        NetworkImageView foto2 = (NetworkImageView) findViewById(R.id.foto2);
        TextView nome = (TextView) findViewById(R.id.nome);
        TextView premio = (TextView) findViewById(R.id.premio);
        TextView inicio = (TextView) findViewById(R.id.inicio);
        TextView termino = (TextView) findViewById(R.id.termino);
        TextView ganhador1 = (TextView) findViewById(R.id.ganhador1);
        TextView ganhador2 = (TextView) findViewById(R.id.ganhador2);
        TextView ganhador3 = (TextView) findViewById(R.id.ganhador3);
        TextView ganhador4 = (TextView) findViewById(R.id.ganhador4);
        TextView ganhador5 = (TextView) findViewById(R.id.ganhador5);
        TextView ganhador6 = (TextView) findViewById(R.id.ganhador6);
        TextView ganhador7 = (TextView) findViewById(R.id.ganhador7);
        TextView ganhador8 = (TextView) findViewById(R.id.ganhador8);
        TextView ganhador9 = (TextView) findViewById(R.id.ganhador9);
        TextView ganhador10 = (TextView) findViewById(R.id.ganhador10);
        Button btnInscrever = (Button) findViewById(R.id.inscrever);

        foto1.setImageUrl(sorteio.getFoto1(), imageLoader);
        foto2.setImageUrl(sorteio.getFoto2(), imageLoader);
        nome.setText(sorteio.getNome());
        premio.setText(sorteio.getPremio());
        inicio.setText("Início: " + sorteio.getInicio());
        termino.setText("Termino: " + sorteio.getTermino());

        if (!sorteio.getGanhador1().isEmpty()) {
            ganhador1.setVisibility(View.VISIBLE);
            ganhador1.setText("Ganhador 1: " + sorteio.getGanhador1());
        }

        if (!sorteio.getGanhador2().isEmpty()) {
            ganhador2.setVisibility(View.VISIBLE);
            ganhador2.setText("Ganhador 2: " + sorteio.getGanhador1());
        }

        if (!sorteio.getGanhador3().isEmpty()) {
            ganhador3.setVisibility(View.VISIBLE);
            ganhador3.setText("Ganhador 3: " + sorteio.getGanhador1());
        }

        if (!sorteio.getGanhador4().isEmpty()) {
            ganhador4.setVisibility(View.VISIBLE);
            ganhador4.setText("Ganhador 4: " + sorteio.getGanhador1());
        }

        if (!sorteio.getGanhador5().isEmpty()) {
            ganhador5.setVisibility(View.VISIBLE);
            ganhador5.setText("Ganhador 5: " + sorteio.getGanhador1());
        }

        if (!sorteio.getGanhador6().isEmpty()) {
            ganhador6.setVisibility(View.VISIBLE);
            ganhador6.setText("Ganhador 6: " + sorteio.getGanhador1());
        }

        if (!sorteio.getGanhador7().isEmpty()) {
            ganhador7.setVisibility(View.VISIBLE);
            ganhador7.setText("Ganhador 7: " + sorteio.getGanhador1());
        }

        if (!sorteio.getGanhador8().isEmpty()) {
            ganhador8.setVisibility(View.VISIBLE);
            ganhador8.setText("Ganhador 8: " + sorteio.getGanhador1());
        }

        if (!sorteio.getGanhador9().isEmpty()) {
            ganhador9.setVisibility(View.VISIBLE);
            ganhador9.setText("Ganhador 9: " + sorteio.getGanhador1());
        }

        if (!sorteio.getGanhador10().isEmpty()) {
            ganhador10.setVisibility(View.VISIBLE);
            ganhador10.setText("Ganhador 10: " + sorteio.getGanhador1());
        }

        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date dtTermino = format.parse(sorteio.getTermino());
            if (dtTermino.after(new Date())) {
                btnInscrever.setVisibility(View.VISIBLE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        btnInscrever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SorteioActivity.this, InscreverActivity.class);
                intent.putExtra("id_sorteio", idSorteio);
                intent.putExtra("param_nome", sorteio.getNome());
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
            Intent intent = new Intent(SorteioActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_sorteio) {
            Intent intent = new Intent(SorteioActivity.this, SorteiosActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_parceiro) {
            Intent intent = new Intent(SorteioActivity.this, ParceiroActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_somos) {
            Intent intent = new Intent(SorteioActivity.this, QuemSomosActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_contato) {
            Intent intent = new Intent(SorteioActivity.this, ContatoActivity.class);
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
