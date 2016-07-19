package br.edu.ifpb.si.pdm.lembretemedico.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by admin on 01/07/16.
 */
public class BancoHelper extends SQLiteOpenHelper {
    private static final String BANCO = "lembretemedico.db";
    private static final int VERSAO = 1;

    public BancoHelper(Context context) {
        super(context, BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table profissional(" +
                        "id integer primary key autoincrement not null, " +
                        "nome string," +
                        "especialidade string" +
                     ");";
        db.execSQL(sql);
        //Log.i("BLACKLIST", "Tabela profissional criada.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
