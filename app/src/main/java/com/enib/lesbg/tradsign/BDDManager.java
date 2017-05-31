package com.enib.lesbg.tradsign;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import static com.enib.lesbg.tradsign.ImageDAO.*;
import static com.enib.lesbg.tradsign.AnimDao.*;

public class BDDManager extends SQLiteOpenHelper {
    public static final String IMAGE_TABLE_CREATE = "CREATE TABLE " + IMAGE_TABLE_NAME + " (" + ImageDAO.MOT + " TEXT, resName TEXT );";
    public static final String IMAGE_TABLE_DROP =  "DROP TABLE IF EXISTS " + IMAGE_TABLE_NAME + ";";

    public static final String ANIM_TABLE_CREATE = "CREATE TABLE " + ANIM_TABLE_NAME
            + " (" + ImageDAO.MOT + " TEXT, id INTEGER," +
            " eDX REAL, eDY REAL," +
            " cDX REAL, cDY REAL, pDX REAL, pDY REAL, pouceDX REAL, pouceDY REAL, indexDX REAL, indexDY REAL," +
            " majeureDX REAL, majeureDY REAL, annulaireDX REAL, annulaireDY REAL, auriculaireDX REAL, auriculaireDY REAL," +
            " eGX REAL, eGY REAL," +
            " cGX REAL, cGY REAL, pGX REAL, pGY REAL, pouceGX REAL, pouceGY REAL, indexGX REAL, indexGY REAL," +
            " majeureGX REAL, majeureGY REAL, annulaireGX REAL, annulaireGY REAL, auriculaireGX REAL, auriculaireGY REAL)";
    public static final String ANIM_TABLE_DROP =  "DROP TABLE IF EXISTS " + ANIM_TABLE_NAME + ";";

    public BDDManager(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(IMAGE_TABLE_CREATE);
        db.execSQL("INSERT INTO " + IMAGE_TABLE_NAME + " VALUES (\"bonjour\", \"bonjour\")");
        db.execSQL("INSERT INTO " + IMAGE_TABLE_NAME + " VALUES (\"au revoir\", \"au_revoir\")");
        db.execSQL("INSERT INTO " + IMAGE_TABLE_NAME + " VALUES (\"bienvenue\", \"bienvenue\")");
        db.execSQL("INSERT INTO " + IMAGE_TABLE_NAME + " VALUES (\"merci\", \"merci\")");
        db.execSQL("INSERT INTO " + IMAGE_TABLE_NAME + " VALUES (\"non\", \"non\")");
        db.execSQL("INSERT INTO " + IMAGE_TABLE_NAME + " VALUES (\"nous\", \"nous\")");
        db.execSQL("INSERT INTO " + IMAGE_TABLE_NAME + " VALUES (\"oui\", \"oui\")");
        db.execSQL("INSERT INTO " + IMAGE_TABLE_NAME + " VALUES (\"peuple\", \"peuple\")");
        db.execSQL("INSERT INTO " + IMAGE_TABLE_NAME + " VALUES (\"projet\", \"projet\")");

        db.execSQL(ANIM_TABLE_CREATE);
        db.execSQL("INSERT INTO " + ANIM_TABLE_NAME + " VALUES " +
                "(\"au revoir\", 0," +
                " 0.4, 0.5," +
                " 0.33, 0.66, 0.33, 0.8, 0.33, 0.8, 0.33, 0.8," +
                " 0.33, 0.8, 0.33, 0.8, 0.33, 0.8," +
                " 0.6, 0.5," +
                " 0.66, 0.66, 0.66, 0.8, 0.66, 0.8, 0.66, 0.8," +
                " 0.66, 0.8, 0.66, 0.8, 0.66, 0.8)");
        db.execSQL("INSERT INTO " + ANIM_TABLE_NAME + " VALUES " +
                "(\"au revoir\", 1," +
                " 0.4, 0.5," +
                " 0.26, 0.66, 0.1, 0.66, 0.1, 0.66, 0.1, 0.66," +
                " 0.1, 0.66, 0.1, 0.66, 0.1, 0.66," +
                " 0.6, 0.5," +
                " 0.66, 0.66, 0.66, 0.8, 0.66, 0.8, 0.66, 0.8," +
                " 0.66, 0.8, 0.66, 0.8, 0.66, 0.8)");
        db.execSQL("INSERT INTO " + ANIM_TABLE_NAME + " VALUES " +
                "(\"au revoir\", 2," +
                " 0.4, 0.5," +
                " 0.2, 0.55, 0.2, 0.33, 0.215, 0.315, 0.2075, 0.30," +
                " 0.2, 0.30, 0.1925, 0.30, 0.185, 0.30," +
                " 0.6, 0.5," +
                " 0.66, 0.66, 0.66, 0.8, 0.66, 0.8, 0.66, 0.8," +
                " 0.66, 0.8, 0.66, 0.8, 0.66, 0.8)");
        db.execSQL("INSERT INTO " + ANIM_TABLE_NAME + " VALUES " +
                "(\"au revoir\", 3," +
                " 0.4, 0.5," +
                " 0.2, 0.55, 0.2, 0.33, 0.23, 0.30, 0.215, 0.27," +
                " 0.2, 0.27, 0.185, 0.27, 0.17, 0.27," +
                " 0.6, 0.5," +
                " 0.66, 0.66, 0.66, 0.8, 0.66, 0.8, 0.66, 0.8," +
                " 0.66, 0.8, 0.66, 0.8, 0.66, 0.8)");
        db.execSQL("INSERT INTO " + ANIM_TABLE_NAME + " VALUES " +
                "(\"au revoir\", 4," +
                " 0.4, 0.5," +
                " 0.2, 0.55, 0.2, 0.33, 0.215, 0.315, 0.2075, 0.30," +
                " 0.2, 0.30, 0.1925, 0.30, 0.185, 0.30," +
                " 0.6, 0.5," +
                " 0.66, 0.66, 0.66, 0.8, 0.66, 0.8, 0.66, 0.8," +
                " 0.66, 0.8, 0.66, 0.8, 0.66, 0.8)");
        db.execSQL("INSERT INTO " + ANIM_TABLE_NAME + " VALUES " +
                "(\"au revoir\", 5," +
                " 0.4, 0.5," +
                " 0.2, 0.55, 0.2, 0.33, 0.23, 0.30, 0.215, 0.27," +
                " 0.2, 0.27, 0.185, 0.27, 0.17, 0.27," +
                " 0.6, 0.5," +
                " 0.66, 0.66, 0.66, 0.8, 0.66, 0.8, 0.66, 0.8," +
                " 0.66, 0.8, 0.66, 0.8, 0.66, 0.8)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(IMAGE_TABLE_DROP);
        db.execSQL(ANIM_TABLE_DROP);
        onCreate(db);
    }
}
