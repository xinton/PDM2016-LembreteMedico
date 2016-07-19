package br.edu.ifpb.si.pdm.lembretemedico;

/**
 * Created by home on 18/07/2016.
 */
public class Profissional {
    private int id;
    private String nome;
    private String especialidade;

    public Profissional() {   }

    public Profissional(String nome, String especialidade) {
        this.nome = nome;
        this.especialidade = especialidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
