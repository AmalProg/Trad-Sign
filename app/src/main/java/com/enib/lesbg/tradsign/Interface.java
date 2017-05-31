package com.enib.lesbg.tradsign;

import android.app.Activity;
import android.os.Bundle;

public class Interface extends Activity {

    protected static int m_Theme = 1;
    protected static int m_AnimationSpeed = 0;
    protected static int m_AnimationStyle = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    protected void initTheme() {
        if (m_Theme == 1) {
            setTheme(R.style.Jour);
        }
        if (m_Theme == 0) {
            setTheme(R.style.Nuit);
        }
    }

    public static void setMTheme(int theme) {
        m_Theme = theme;
    }
    public static int getMTheme() { return m_Theme; }
}
