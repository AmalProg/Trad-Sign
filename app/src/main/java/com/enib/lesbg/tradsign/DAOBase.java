package com.enib.lesbg.tradsign;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class DAOBase {
    // Si je décide de la mettre à jour, il faudra changer cet attribut
    public final static int VERSION = 9;
    // Le nom du fichier qui représente ma base
    protected static final String DB_NAME = "db_TradSign";
    protected static final String USER = "projp3";
    protected static final String PASSWORD = "TradSign2017";

    protected SQLiteDatabase mDb = null;
    protected BDDManager mHandler = null;

    public DAOBase(Context pContext) {
        this.mHandler = new BDDManager(pContext, DB_NAME, null, VERSION);
    }

    public SQLiteDatabase open() {
        // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
        mDb = mHandler.getWritableDatabase();
        return mDb;
    }

    public void close() {
        mDb.close();
    }

    public SQLiteDatabase getDb() {
        return mDb;
    }
}
