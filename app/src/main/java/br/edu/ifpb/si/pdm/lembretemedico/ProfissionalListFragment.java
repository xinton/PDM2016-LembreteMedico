package br.edu.ifpb.si.pdm.lembretemedico;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import br.edu.ifpb.si.pdm.lembretemedico.bd.ProfissionalDAO;


public class ProfissionalListFragment extends Fragment {

    private EditText etNome;
    private EditText etEspecialidade;
    private Button btAdd;
    private Spinner especialidade_spinner;
    private ListView lvProfissionais;

    private ProfissionalAdapter profAdapter;
    private ProfissionalDAO pd;


//    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(v.getContext(), text, duration);
        toast.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profissionallist, container, false);
        this.instanciaComponentesInterface(view,inflater);
        this.defineListeners();
        return view;

    }

    private void instanciaComponentesInterface(View v, LayoutInflater inflater){

        this.pd = new ProfissionalDAO(inflater.getContext());

        etNome = (EditText) v.findViewById(R.id.etNome);
        btAdd = (Button) v.findViewById(R.id.btAdd);
        lvProfissionais = (ListView) v.findViewById(R.id.lvProfissionais);


        profAdapter = new ProfissionalAdapter(this.pd.get(), inflater.getContext());
        this.lvProfissionais.setAdapter(profAdapter);
//        setListAdapter(profAdapter);

        especialidade_spinner = (Spinner) v.findViewById(R.id.especialidade_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(inflater.getContext(),
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

    private class OnClickBotao implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            String nome = ProfissionalListFragment.this.etNome.getText().toString();
            String especialidade = especialidade_spinner.getSelectedItem().toString();
            ProfissionalListFragment.this.pd.inserir(new Profissional(nome,especialidade));
            profAdapter.notifyDataSetChanged();
        }
    }

    private class OnLongClickList implements AdapterView.OnItemLongClickListener{

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Profissional p = (Profissional) parent.getAdapter().getItem(position);
            ProfissionalListFragment.this.pd.remover(p);
            profAdapter.notifyDataSetChanged();
            return true;
        }
    }

}

