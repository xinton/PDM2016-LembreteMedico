package br.edu.ifpb.si.pdm.lembretemedico;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


public class ConsultaListFragment extends ListFragment {

    private CadastroLocal cadastroLocal;
    private ConsultaAdapter adapter;

    public ConsultaListFragment(){
        this.cadastroLocal = new CadastroLocal();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(v.getContext(), text, duration);
        toast.show();

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                Uri.parse("http://maps.google.com/maps?"
//                        + "saddr=-7.137926, -34.886738"));
        Uri.parse("http://maps.google.com/maps?saddr=-7.137926, -34.886738&daddr=-7.134571, -34.873334"));

//        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                Uri.parse("google.navigation:q=an+address+city"));

//        Uri gmmIntentUri = Uri.parse("google.navigation:q=Taronga+Zoo,+Sydney+Australia");
//        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//        mapIntent.setPackage("com.google.android.apps.maps");
//        startActivity(mapIntent);

        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//
        this.adapter = new ConsultaAdapter(MainActivity.cadastroLocal.get(), inflater.getContext());
        setListAdapter(adapter);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                inflater.getContext(), android.R.layout.simple_list_item_1,
//                numbers_text);
//        setListAdapter(adapter);
//        return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_consultalist, container, false);



        return view;

    }

    public void refreshData() {
        adapter.notifyDataSetChanged();
    }

}

