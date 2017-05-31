package com.enib.lesbg.tradsign;

import android.content.Context;
import android.database.Cursor;
import android.graphics.PointF;

public class AnimDao extends DAOBase {
    public static final String ANIM_TABLE_NAME = "anim";
    public static final String MOT = "mot";

    public AnimDao(Context pContext) {
        super(pContext);
    }

    public PointF[] getPoints(String mot, int id) {
        Cursor c = mDb.rawQuery("select * from " + ANIM_TABLE_NAME + " where mot = ? AND id = ?"
                , new String[]{mot, String.valueOf(id)});
        if(c.getCount() != 0) {
            PointF points[] = new PointF[AnimatedCharacter.NBR_POINTS];
            c.moveToFirst();
            for(int i = 0; i < AnimatedCharacter.NBR_POINTS; i++) {
                points[i] = new PointF(c.getFloat(i*2+2), c.getFloat(i*2+1+2));
            }
            c.close();
            return points;
        }
        else {
            c.close();
            return null;
        }
    }

    public int getPositionsNumber(String mot) {
        Cursor c = mDb.rawQuery("select * from " + ANIM_TABLE_NAME + " where mot = ?", new String[]{mot});
        int nbr = c.getCount();
        c.close();
        return nbr;
    }
}
