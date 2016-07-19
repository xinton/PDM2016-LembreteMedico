package br.edu.ifpb.si.pdm.lembretemedico;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Substituir por BD
    private CadastroLocal cadastroLocal;

    private ListView lvListaConsultas;

    private static final int ADD_CONSULTA = 1;

    public MainActivity() {
        this.cadastroLocal = new CadastroLocal();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.carregarComponentes();

//        ConsultaAdapter adapter = new ConsultaAdapter(this.cadastroLocal.get(), this);
//        this.lvListaConsultas.setAdapter(adapter);
//        this.lvListaConsultas.setOnItemClickListener(new OnClickList());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, AdicionarConsulta.class);
                startActivityForResult(it, ADD_CONSULTA);
            }
        });

    }

    private void carregarComponentes(){
        lvListaConsultas = (ListView) findViewById(R.id.lvListaConsultas);
        ArrayAdapter<Consulta> adapter = new ArrayAdapter<Consulta>(this, android.R.layout.simple_list_item_1, this.cadastroLocal.get());
        this.lvListaConsultas.setAdapter(adapter);
        this.lvListaConsultas.setOnItemClickListener(new OnClickList());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == ADD_CONSULTA){
                Consulta consulta = new Consulta(data.getStringExtra("PROFISSIONAL"));
                Date d = new Date();
                d.setTime(data.getLongExtra("DATA", -1));
                consulta.setData(d);

                // Notifica o novo cadastro
                this.cadastroLocal.get().add(consulta);
                ((ArrayAdapter)lvListaConsultas.getAdapter()).notifyDataSetChanged();
            }
        }
    }

    //TODO Adicionar activity que mostra a consulta. <action android:name="CONSULTA_INFO" /> no manifest
    private class OnClickList implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //TODO Adicionar activity que mostra a consulta. <action android:name="CONSULTA_INFO" /> no manifest
            /*
             Consulta consulta =  (Consulta) parent.getAdapter().getItem(position);
            Intent it = new Intent("CONSULTA_INFO");
            it.putExtra("MEDICO", consulta.getMedico());
            setResult(RESULT_OK, it);
            startActivity(it);
            */
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_addMedic) {
            Intent intent = new Intent(this, Profissionais.class);
            startActivity(intent);
        } else if (id == R.id.nav_addHospital) {

        } else if (id == R.id.nav_addAlarm) {

        } else if (id == R.id.nav_planos) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_lembreteMedicamento) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
