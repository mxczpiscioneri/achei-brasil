package br.com.mxczpiscioneri.acheibrasil;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DetalhesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LatLng location;
    private GoogleMap googleMap;
    private List<Address> geocodeMatches;
    private ProgressDialog pDialog;
    private Estabelecimento estabelecimento = new Estabelecimento();
    private LinearLayout llGeral;
    private ViewPager mViewPager;
    private NetworkImageView propaganda1;
    private NetworkImageView propaganda2;
    private boolean zoomOut = false;
    private int heigthPropaganda;
    private int widthPropaganda;
    private String id_estabelecimento;
    private String cidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);
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
        id_estabelecimento = intent.getStringExtra("id_estabelecimento");
        cidade = intent.getStringExtra("id_cidade");

        String url = "http://acheibrasil.net/api/estabelecimento.php?id=" + id_estabelecimento;
        Log.d("logmxcz url destalhes", url);
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

                                estabelecimento.setId(obj.getInt("id_estabelecimento"));
                                estabelecimento.setNome(obj.getString("nome"));
                                estabelecimento.setFoto_principal(obj.getString("foto_principal"));
                                estabelecimento.setFoto_menu(obj.getString("foto_menu"));
                                estabelecimento.setFoto_extra(obj.getString("foto_extra"));
                                estabelecimento.setCategoria1(obj.getString("categoria1"));
                                estabelecimento.setCategoria2(obj.getString("categoria2"));
                                estabelecimento.setCategoria3(obj.getString("categoria3"));
                                estabelecimento.setCategoria4(obj.getString("categoria4"));
                                estabelecimento.setCategoria5(obj.getString("categoria5"));
                                estabelecimento.setTelefone(obj.getString("telefone"));
                                estabelecimento.setCelular(obj.getString("celular"));
                                estabelecimento.setEmail(obj.getString("email"));
                                estabelecimento.setEndereco(obj.getString("endereco"));
                                estabelecimento.setNumero(obj.getString("numero"));
                                estabelecimento.setComplemento(obj.getString("complemento"));
                                estabelecimento.setBairro(obj.getString("bairro"));
                                estabelecimento.setCidade(obj.getString("cidade"));
                                estabelecimento.setEstado(obj.getString("estado"));
                                estabelecimento.setWebsite(obj.getString("website"));
                                estabelecimento.setFacebook(obj.getString("facebook"));
                                estabelecimento.setInstagram(obj.getString("instagram"));
                                estabelecimento.setTwitter(obj.getString("twitter"));
                                estabelecimento.setWhatsapp(obj.getString("whatsapp"));
                                estabelecimento.setObservacao(obj.getString("observacao"));
                                estabelecimento.setPropaganda1(obj.getString("propaganda1"));
                                estabelecimento.setPropaganda2(obj.getString("propaganda2"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        loadData();
                        contabilizaVisita();
                        //loadMap();
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

    public void loadData() {
        ImageLoader imageLoader = AppVolleyController.getInstance().getImageLoader();

        String txtCategoria = "";
        if (estabelecimento.getCategoria1() != null && !estabelecimento.getCategoria1().isEmpty()) {
            txtCategoria = estabelecimento.getCategoria1();
        }
        if (estabelecimento.getCategoria2() != null && !estabelecimento.getCategoria2().isEmpty()) {
            txtCategoria += ", " + estabelecimento.getCategoria2();
        }
        if (estabelecimento.getCategoria3() != null && !estabelecimento.getCategoria3().isEmpty()) {
            txtCategoria += ", " + estabelecimento.getCategoria3();
        }
        if (estabelecimento.getCategoria4() != null && !estabelecimento.getCategoria4().isEmpty()) {
            txtCategoria += ", " + estabelecimento.getCategoria4();
        }
        if (estabelecimento.getCategoria5() != null && !estabelecimento.getCategoria5().isEmpty()) {
            txtCategoria += ", " + estabelecimento.getCategoria5();
        }


        llGeral = (LinearLayout) findViewById(R.id.llGeral);
        LinearLayout llContato = (LinearLayout) findViewById(R.id.llContato);
        LinearLayout llEndereco = (LinearLayout) findViewById(R.id.llEndereco);
        LinearLayout llObservacao = (LinearLayout) findViewById(R.id.llObservacao);
        LinearLayout llPropagandas = (LinearLayout) findViewById(R.id.llPropagandas);

        TextView nome = (TextView) findViewById(R.id.nome);
        TextView categoria = (TextView) findViewById(R.id.categoria);
        TextView telefone = (TextView) findViewById(R.id.telefone);
        TextView celular = (TextView) findViewById(R.id.celular);
        TextView whatsapp = (TextView) findViewById(R.id.whatsapp);
        TextView email = (TextView) findViewById(R.id.email);
        TextView endereco = (TextView) findViewById(R.id.endereco);
        TextView complemento = (TextView) findViewById(R.id.complemento);
        TextView bairro = (TextView) findViewById(R.id.bairro);
        TextView cidade = (TextView) findViewById(R.id.cidade);
        ImageView website = (ImageView) findViewById(R.id.website);
        ImageView facebook = (ImageView) findViewById(R.id.facebook);
        ImageView twitter = (ImageView) findViewById(R.id.twitter);
        ImageView instagram = (ImageView) findViewById(R.id.instagram);
        TextView observacao = (TextView) findViewById(R.id.observacao);

        FloatingActionButton fab_website = (FloatingActionButton) findViewById(R.id.fab_site);
        FloatingActionButton fab_facebook = (FloatingActionButton) findViewById(R.id.fab_facebook);
        FloatingActionButton fab_twitter = (FloatingActionButton) findViewById(R.id.fab_twitter);
        FloatingActionButton fab_instagram = (FloatingActionButton) findViewById(R.id.fab_instagram);


        mViewPager = (ViewPager) findViewById(R.id.viewPageAndroid);
        String[] images = new String[]{estabelecimento.getFoto_principal(), estabelecimento.getFoto_menu(), estabelecimento.getFoto_extra()};
        AndroidImageAdapter adapterView = new AndroidImageAdapter(this, images);
        mViewPager.setAdapter(adapterView);

        propaganda1 = (NetworkImageView) findViewById(R.id.propaganda1);
        propaganda2 = (NetworkImageView) findViewById(R.id.propaganda2);

        nome.setText(estabelecimento.getNome());
        categoria.setText(txtCategoria);
        if (!estabelecimento.getTelefone().isEmpty() || !estabelecimento.getCelular().isEmpty() || !estabelecimento.getWhatsapp().isEmpty() || !estabelecimento.getEmail().isEmpty()) {
            llContato.setVisibility(View.VISIBLE);
        }
        if (!estabelecimento.getTelefone().isEmpty()) {
            telefone.setVisibility(View.VISIBLE);
            telefone.setText(estabelecimento.getTelefone());
        }
        if (!estabelecimento.getCelular().isEmpty()) {
            celular.setVisibility(View.VISIBLE);
            celular.setText(estabelecimento.getCelular());
        }
        if (!estabelecimento.getWhatsapp().isEmpty()) {
            whatsapp.setVisibility(View.VISIBLE);
            whatsapp.setText(estabelecimento.getWhatsapp());
        }
        if (!estabelecimento.getEmail().isEmpty()) {
            email.setVisibility(View.VISIBLE);
            email.setText(estabelecimento.getEmail());
        }
        if (!estabelecimento.getEndereco().isEmpty()) {
            llEndereco.setVisibility(View.VISIBLE);
            endereco.setText(estabelecimento.getEndereco() + (estabelecimento.getNumero().isEmpty() ? "" : ", " + estabelecimento.getNumero()));
        }
        if (!estabelecimento.getComplemento().isEmpty()) {
            complemento.setVisibility(View.VISIBLE);
            complemento.setText(estabelecimento.getComplemento());
        }
        if (!estabelecimento.getBairro().isEmpty()) {
            bairro.setVisibility(View.VISIBLE);
            bairro.setText(estabelecimento.getBairro());
        }
        cidade.setText(estabelecimento.getCidade() + (estabelecimento.getEstado().isEmpty() ? "" : " - " + estabelecimento.getEstado()));
        if (!estabelecimento.getWebsite().isEmpty()) {
            website.setVisibility(View.VISIBLE);
            website.setTag(estabelecimento.getWebsite());
            fab_website.setVisibility(View.VISIBLE);
            fab_website.setTag(estabelecimento.getWebsite());
        }
        if (!estabelecimento.getFacebook().isEmpty()) {
            facebook.setVisibility(View.VISIBLE);
            facebook.setTag(estabelecimento.getFacebook());
            fab_facebook.setVisibility(View.VISIBLE);
            fab_facebook.setTag(estabelecimento.getFacebook());
        }
        if (!estabelecimento.getTwitter().isEmpty()) {
            twitter.setVisibility(View.VISIBLE);
            twitter.setTag(estabelecimento.getTwitter());
            fab_twitter.setVisibility(View.VISIBLE);
            fab_twitter.setTag(estabelecimento.getTwitter());
        }
        if (!estabelecimento.getInstagram().isEmpty()) {
            instagram.setVisibility(View.VISIBLE);
            instagram.setTag(estabelecimento.getInstagram());
            fab_instagram.setVisibility(View.VISIBLE);
            fab_instagram.setTag(estabelecimento.getInstagram());
        }
        if (!estabelecimento.getObservacao().isEmpty()) {
            llObservacao.setVisibility(View.VISIBLE);
            observacao.setText(estabelecimento.getObservacao());
        }
        if (!estabelecimento.getPropaganda1().isEmpty()) {
            llPropagandas.setVisibility(View.VISIBLE);
            propaganda1.setImageUrl(estabelecimento.getPropaganda1(), imageLoader);
        }
        if (!estabelecimento.getPropaganda2().isEmpty()) {
            propaganda2.setImageUrl(estabelecimento.getPropaganda2(), imageLoader);
        }

        propaganda1.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                if (zoomOut) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthPropaganda, heigthPropaganda);
                    params.setMargins(0, 0, 0, 16);
                    propaganda1.setLayoutParams(params);
                    propaganda1.setAdjustViewBounds(true);
                    propaganda1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    zoomOut = false;
                    propaganda2.setVisibility(View.VISIBLE);
                } else {
                    widthPropaganda = propaganda1.getWidth();
                    heigthPropaganda = propaganda1.getHeight();
                    propaganda1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    propaganda1.setScaleType(ImageView.ScaleType.FIT_XY);
                    zoomOut = true;
                    propaganda2.setVisibility(View.GONE);
                }
            }
        });

        propaganda2.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                //llGeral.setBackground(propaganda2.getDrawable());
                if (zoomOut) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthPropaganda, heigthPropaganda);
                    params.setMargins(16, 0, 0, 0);
                    propaganda2.setLayoutParams(params);
                    propaganda2.setAdjustViewBounds(true);
                    propaganda2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    zoomOut = false;
                    propaganda1.setVisibility(View.VISIBLE);
                } else {
                    widthPropaganda = propaganda2.getWidth();
                    heigthPropaganda = propaganda2.getHeight();
                    propaganda2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    propaganda2.setScaleType(ImageView.ScaleType.FIT_XY);
                    zoomOut = true;
                    propaganda1.setVisibility(View.GONE);
                }
            }
        });

    }

    private void contabilizaVisita() {
        final String URL = "http://acheibrasil.net/api/visita.php";

        JSONObject params = new JSONObject();
        try {
            params.put("id_estabelecimento", estabelecimento.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("logmxcz", response.toString());
                        try {
                            if (response.getString("success").equals("true")) {
                                Log.d("logmxcz", "Visita salva - ID:" + estabelecimento.getId());
                            } else {
                                Log.d("logmxcz", "Visita não salva - ID:" + estabelecimento.getId());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("logmxcz", "Error visita: " + error.getMessage());
            }
        });
        AppVolleyController.getInstance().addToRequestQueue(req);
    }

    public void loadMap() {
        try {
            //Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            //int tentatives = 0;
            //while (geocodeMatches.size()==0 && (tentatives < 10)) {
            //    geocodeMatches = geocoder.getFromLocationName("Av Luiz Eduardo Toledo Prado, 900 - Ribeirão Preto", 1);
            //    tentatives ++;
            //}

            //if (geocodeMatches.size() > 0) {
            //LinearLayout llMapa = (LinearLayout) findViewById(R.id.llMapa);
            //llMapa.setVisibility(View.VISIBLE);
            //Log.d("logmxcz", "entrou mapa");

            //Address addr = geocodeMatches.get(0);
            //location = new LatLng(addr.getLatitude(), addr.getLongitude());
            location = new LatLng(-21.225784, -47.835223);

            if (googleMap == null) {
                //googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            }
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16));
            googleMap.addMarker(new MarkerOptions().position(location).title("Starbucks"));
            //}
        } catch (Exception e) {
            //Log.d("logmxcz", "catch mapa");
            e.printStackTrace();
        }
    }

    public void openBrowser(View view) {

        String url = (String) view.getTag();

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);

        intent.setData(Uri.parse(url));

        startActivity(intent);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalhes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_share) {

            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            share.putExtra(Intent.EXTRA_SUBJECT, estabelecimento.getNome());
            share.putExtra(Intent.EXTRA_TEXT, estabelecimento.getNome() + "\n\n" + estabelecimento.getEndereco() + ", " + estabelecimento.getNumero() + " (" + estabelecimento.getCidade() + " - " + estabelecimento.getEstado() + ")");

            startActivity(Intent.createChooser(share, getResources().getString(R.string.compartilhar)));

            return true;
        }

        return super.onOptionsItemSelected(item);
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
            Intent intent = new Intent(DetalhesActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_sorteio) {
            Intent intent = new Intent(DetalhesActivity.this, SorteiosActivity.class);
            intent.putExtra("id_cidade", cidade);
            startActivity(intent);
        } else if (id == R.id.nav_parceiro) {
            Intent intent = new Intent(DetalhesActivity.this, ParceiroActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_somos) {
            Intent intent = new Intent(DetalhesActivity.this, QuemSomosActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_contato) {
            Intent intent = new Intent(DetalhesActivity.this, ContatoActivity.class);
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
