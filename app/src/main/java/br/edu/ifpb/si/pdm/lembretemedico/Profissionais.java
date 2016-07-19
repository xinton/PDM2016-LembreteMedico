package br.edu.ifpb.si.pdm.lembretemedico;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import br.edu.ifpb.si.pdm.lembretemedico.bd.ProfissionalDAO;

public class Profissionais extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText etNome;
    private EditText etEspecialidade;
    private Button btAdd;
    private Spinner especialidade_spinner;

    private ListView lvProfissionais;
    private ProfissionalDAO pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profissionais);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.instanciaComponentesInterface();
        this.defineListeners();

        this.pd = new ProfissionalDAO(this);

        ProfissionalAdapter adapter = new ProfissionalAdapter(this.pd.get(), this);
        this.lvProfissionais.setAdapter(adapter);

        this.atualizaAdapter();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void atualizaAdapter(){
        this.lvProfissionais.setAdapter(new ProfissionalAdapter(this.pd.get(), this));
    }

    private void instanciaComponentesInterface(){
        //etEspecialidade = (EditText) findViewById(R.id.etEspecialidade);
        etNome = (EditText) findViewById(R.id.etNome);
        btAdd = (Button) findViewById(R.id.btAdd);
        lvProfissionais = (ListView) findViewById(R.id.lvProfissionais);

        especialidade_spinner = (Spinner) findViewById(R.id.especialidade_spinner);
        especialidade_spinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.especialidades_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        especialidade_spinner.setAdapter(adapter);

    }

    private void defineListeners(){
        this.btAdd.setOnClickListener(new OnClickBotao());
        this.lvProfissionais.setOnItemLongClickListener(new OnLongClickList());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        //Log.v("item", (String) parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private class OnClickBotao implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            String nome = Profissionais.this.etNome.getText().toString();
            String especialidade = especialidade_spinner.getSelectedItem().toString();
            Log.v("item", especialidade);
            //String especialidade = Profissionais.this.etEspecialidade.getText().toString();
            Profissionais.this.pd.inserir(new Profissional(nome,especialidade));
            Profissionais.this.atualizaAdapter();
            Log.i("LEMBRETEMEDIC", Profissionais.this.pd.get().toString());
        }
    }

    private class OnLongClickList implements AdapterView.OnItemLongClickListener{

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Profissional p = (Profissional) parent.getAdapter().getItem(position);
            Profissionais.this.pd.remover(p);
            Profissionais.this.atualizaAdapter();
            return true;
        }
    }

}
