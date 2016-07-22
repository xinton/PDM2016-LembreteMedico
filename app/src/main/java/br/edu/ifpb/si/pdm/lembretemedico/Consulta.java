package br.edu.ifpb.si.pdm.lembretemedico;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by home on 17/07/2016.
 */
public class Consulta {
    private int id;
    private String medico;
    private String obs;
    private Date data;

    public Consulta(String medico, String obs, Date data) {
        this.medico = medico;
        this.obs = obs;
        this.data = data;
    }

    public Consulta(String medico) {
        this.medico = medico;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {

        this.medico = medico;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String toString(){
        return "Medico: "+this.getMedico()+"\nObs: "+getObs()+"\nData: "+getData()+"\n";
    }
}
