package br.edu.ifpb.si.pdm.lembretemedico;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by home on 17/07/2016.
 */
public class Consulta {
    private String medico;
    private String desc;
    private Date data;

    public Consulta(String medico, String desc, Date data) {
        this.medico = medico;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String toString(){
        return this.getMedico()+"-"+getDesc()+"-"+getData();
    }
}
