package com.enib.lesbg.tradsign;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class ImageDAO extends DAOBase {
    public static final String IMAGE_TABLE_NAME = "image";
    public static final String MOT = "mot";

    public ImageDAO(Context pContext) {
        super(pContext);
    }

    public String getResName(String mot) {
        Cursor c = mDb.rawQuery("select * from " + IMAGE_TABLE_NAME + " where mot = ?", new String[]{mot});
        if(c.getCount() != 0) {
            c.moveToFirst();
            String word = c.getString(1);
            c.close();
            return word;
        }
        else {
            c.close();
            return null;
        }
    }
}
