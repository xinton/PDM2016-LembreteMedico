package br.edu.ifpb.si.pdm.lembretemedico.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.si.pdm.lembretemedico.Profissional;

/**
 * Created by admin on 01/07/16.
 */
public class ProfissionalDAO implements DAO<Profissional> {
    private BancoHelper banco;
    private static final String TABELA = "profissional";

    public ProfissionalDAO(Context context) {
        this.banco = new BancoHelper(context);
    }

    @Override
    public void inserir(Profissional novo) {
        ContentValues cv = new ContentValues();
        cv.put("nome", novo.getNome());
        cv.put("especialidade", novo.getEspecialidade());
        this.banco.getWritableDatabase().insert(TABELA, null, cv);
    }

    @Override
    public void atualizar(Profissional obj) {

    }

    @Override
    public void remover(int id) {
        String[] where = {Integer.toString(id)};
        this.banco.getWritableDatabase().delete(TABELA, "id = ?", where);
    }

    @Override
    public void remover(Profissional obj) {
        this.remover(obj.getId());
    }

    @Override
    public Profissional get(int id) {
        return null;
    }

    @Override
    public List<Profissional> get() {
        String[] colunas = {"id", "nome", "especialidade"};
        List<Profissional> lista = new ArrayList<Profissional>();

        Cursor c = this.banco.getReadableDatabase().query(TABELA, colunas, null, null, null, null, "nome");
        if (c.getCount() > 0){
            c.moveToFirst();
            do{
                Profissional p = new Profissional();
                p.setId(c.getInt(c.getColumnIndex(colunas[0])));
                p.setNome(c.getString(1));
                p.setEspecialidade(c.getString(2));
                lista.add(p);
            }while (c.moveToNext());
        }

        return lista;
    }
}
