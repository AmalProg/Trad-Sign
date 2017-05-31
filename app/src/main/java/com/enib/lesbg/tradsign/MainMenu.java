package com.enib.lesbg.tradsign;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.view.View;
import android.widget.ImageView;

public class MainMenu extends Interface {
    private Button translateButton = null;
    private Button gameButton = null;
    private Button paramButton = null;
    private SharedPreferences sharedPreferences;
    private int m_ActualMenuTheme = 0;
    private ImageView Logo = null;
    private ImageView TraductionIcon = null;
    private ImageView JouerIcon = null;
    private ImageView ParamIcon = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (sharedPreferences.getBoolean("Gif", true)) {
            m_AnimationStyle = 0;
        }
        else if (sharedPreferences.getBoolean("Anim", false)) {
            m_AnimationStyle = 1;
        }

        if (sharedPreferences.getBoolean("Jour", true)) {
            setMTheme(1);
            m_ActualMenuTheme = m_Theme;
            setTheme(R.style.Jour);
        }
        else if (sharedPreferences.getBoolean("Nuit", false)) {
            setMTheme(0);
            m_ActualMenuTheme = m_Theme;
            setTheme(R.style.Nuit);
        }

        setContentView(R.layout.activity_main_menu);

        translateButton = (Button)findViewById(R.id.TranslatButton);
        translateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TranslateMenu.class));
            }
        });

        paramButton = (Button)findViewById(R.id.ParamButton);
        paramButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ParamMenu.class));
            }
        });

        gameButton = (Button)findViewById(R.id.GameButton);
        gameButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), GameMenu.class));
            }
        });
        Logo = (ImageView) findViewById(R.id.logo);
        TraductionIcon = (ImageView) findViewById(R.id.TraductionIcon);
        JouerIcon = (ImageView) findViewById(R.id.JouerIcon);
        ParamIcon = (ImageView) findViewById(R.id.ParamIcon);
        if(m_ActualMenuTheme==1) {
            Logo.setImageResource(R.drawable.logojour);
            TraductionIcon.setImageResource(R.drawable.traduire);
            JouerIcon.setImageResource(R.drawable.testjour);
            ParamIcon.setImageResource(R.drawable.parametres);
        }
        else if(m_ActualMenuTheme==0){
            Logo.setImageResource(R.drawable.logonuit);
            TraductionIcon.setImageResource(R.drawable.traduirenuit);
            JouerIcon.setImageResource(R.drawable.testnuit);
            ParamIcon.setImageResource(R.drawable.parametresnuit);
        }
    }

    protected void onResume() {
        super.onResume();

        if(m_ActualMenuTheme != m_Theme)
            recreate();
    }
}
