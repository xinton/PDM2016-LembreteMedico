package br.edu.ifpb.si.pdm.lembretemedico;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by home on 19/07/2016.
 */
public class ConsultaAdapter extends BaseAdapter {

    private List<Consulta> lista;
    private Context context;

//// TODO: 19/07/2016 FILTRO POR ESPECIALIDADE

    public ConsultaAdapter(List<Consulta> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }
    @Override
    public int getCount() {
        return this.lista.size();
    }

    @Override
    public Object getItem(int position) {
        return this.lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        Consulta c = this.lista.get(position);

        if (convertView == null){
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.consulta_layout, null);
        }else view = convertView;

        TextView tvNomeProfissionalLayout = (TextView) view.findViewById(R.id.tvNomeProfissionalLayout);
        TextView tvDataLayout = (TextView) view.findViewById(R.id.tvDataLayout);
        TextView tvDescLayout = (TextView) view.findViewById(R.id.tvDescLayout);

        tvNomeProfissionalLayout.setText(c.getMedico());

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String data = df.format(c.getData());
        tvDataLayout.setText( data );

        tvDescLayout.setText( c.getDesc() );

        return view;
    }
}
