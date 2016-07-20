package br.edu.ifpb.si.pdm.lembretemedico;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by home on 16/06/2016.
 */
public class CadastroLocal {
    private List<Consulta> consultas;

    public CadastroLocal() {
        this.consultas = new ArrayList<Consulta>();
        this.insere();
    }

    private void insere(){
        this.consultas.add(new Consulta("local","desc",new Date()));
    }

    public List<Consulta> get(){
        return this.consultas;
    }

    public int quantidade(){
        return this.consultas.size();
    }

    public void clear(){
        this.consultas.clear();
    }

    public void delete(int pos){
        this.consultas.remove(pos);
    }
}
