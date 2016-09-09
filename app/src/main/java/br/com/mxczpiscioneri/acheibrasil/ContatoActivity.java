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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;
import java.util.Map;

public class ContatoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressDialog pDialog;
    private EditText nome;
    private EditText email;
    private EditText cidade;
    private EditText telefone;
    private EditText assunto;
    private EditText mensagem;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        nome = (EditText) findViewById(R.id.nome);
        email = (EditText) findViewById(R.id.email);
        cidade = (EditText) findViewById(R.id.cidade);
        telefone = (EditText) findViewById(R.id.telefone);
        assunto = (EditText) findViewById(R.id.assunto);
        mensagem = (EditText) findViewById(R.id.mensagem);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String URL = "http://acheibrasil.net/enviar-contato.php";
                showPDialog();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("logmxcz", response.toString());
                                if (response.equals("1")) {
                                    Toast.makeText(getApplicationContext(), "E-mail enviado com sucesso!", Toast.LENGTH_LONG).show();
                                    limparCampos();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Erro ao enviar e-mail!", Toast.LENGTH_LONG).show();
                                }
                                hidePDialog();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("local", "app");
                        params.put("nome", nome.getText().toString());
                        params.put("email", email.getText().toString());
                        params.put("cidade", cidade.getText().toString());
                        params.put("telefone", telefone.getText().toString());
                        params.put("assunto", assunto.getText().toString());
                        params.put("mensagem", mensagem.getText().toString());
                        return params;
                    }

                };

                AppVolleyController.getInstance().addToRequestQueue(stringRequest);

            }
        });

    }

    private void limparCampos(){
        nome.setText("");
        email.setText("");
        cidade.setText("");
        telefone.setText("");
        assunto.setText("");
        mensagem.setText("");

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
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
            Intent intent = new Intent(ContatoActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_sorteio) {
            Intent intent = new Intent(ContatoActivity.this, SorteiosActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_parceiro) {
            Intent intent = new Intent(ContatoActivity.this, ParceiroActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_somos) {
            Intent intent = new Intent(ContatoActivity.this, QuemSomosActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_contato) {
            Intent intent = new Intent(ContatoActivity.this, ContatoActivity.class);
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
