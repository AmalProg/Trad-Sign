package com.enib.lesbg.tradsign;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.view.View;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ParamMenu extends Interface {
    private RadioButton Gif = null;
    private RadioButton Anim = null;
    private RadioButton Jour = null;
    private RadioButton Nuit = null;
    private RadioButton Lent = null;
    private RadioButton Normale = null;
    private RadioButton Rapide = null;
    private SharedPreferences sharedPreferences;
    private ImageView sun = null;
    private ImageView moon = null;
    private ImageView ValidationIcon = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initTheme();

        setContentView(R.layout.activity_param_menu);

        Gif = (RadioButton) findViewById(R.id.GifButP);
        Anim = (RadioButton) findViewById(R.id.AnimButP);
        Jour = (RadioButton) findViewById(R.id.JourBut);
        Nuit = (RadioButton) findViewById(R.id.Nuit);
        Lent = (RadioButton) findViewById(R.id.LentBut);
        Normale = (RadioButton) findViewById(R.id.NormaleBut);
        Rapide = (RadioButton) findViewById(R.id.RapideBut);
        sun = (ImageView) findViewById(R.id.sun);
        sun.setImageResource(R.drawable.sun);
        moon = (ImageView) findViewById(R.id.moon);
        moon.setImageResource(R.drawable.moon);
        if(m_Theme == 1){
            sun.setVisibility(View.VISIBLE);
            moon.setVisibility(View.INVISIBLE);
        }
        else if(m_Theme == 0){
            sun.setVisibility(View.INVISIBLE);
            moon.setVisibility(View.VISIBLE);
        }
        ValidationIcon = (ImageView) findViewById(R.id.ValidationIcon);
        ValidationIcon.setImageResource(R.drawable.validation);
        loadRadioButtons();
    }

    public void saveRadioButtons(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("Gif", Gif.isChecked());
        editor.putBoolean("Anim", Anim.isChecked());
        editor.putBoolean("Jour", Jour.isChecked());
        editor.putBoolean("Nuit", Nuit.isChecked());
        editor.putBoolean("Lent", Lent.isChecked());
        editor.putBoolean("Normale", Normale.isChecked());
        editor.putBoolean("Rapide", Rapide.isChecked());

        editor.apply();
    }
    public void loadRadioButtons(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gif.setChecked(sharedPreferences.getBoolean("Gif", true));
        Anim.setChecked(sharedPreferences.getBoolean("Anim", false));
        Jour.setChecked(sharedPreferences.getBoolean("Jour", true));
        Nuit.setChecked(sharedPreferences.getBoolean("Nuit", false));
        Lent.setChecked(sharedPreferences.getBoolean("Lent", false));
        Normale.setChecked(sharedPreferences.getBoolean("Normale", true));
        Rapide.setChecked(sharedPreferences.getBoolean("Rapide", false));
    }

    public void onRadioButtonClicked(View view) {
        switch(view.getId()) {
            case R.id.JourBut:
                if(getMTheme() != 1) {
                    setMTheme(1);
                    recreate();
                }
                break;
            case R.id.Nuit:
                if(getMTheme() != 0) {
                    setMTheme(0);
                    recreate();
                }
                break;
        }
    }

    public void onValiderButtonClicked(View view) {
        saveRadioButtons();

        finish();
    }

    public void onAnimButtonClicked(View view) {
        switch(view.getId()) {
            case R.id.GifButP:
                m_AnimationStyle = 0;
                break;
            case R.id.AnimButP:
                m_AnimationStyle = 1;
                break;
        }
    }

    public void onAnimSpeedButtonClicked(View view) {
        switch(view.getId()) {
            case R.id.LentBut:
                m_AnimationSpeed = 3000;
                break;
            case R.id.NormaleBut:
                m_AnimationSpeed = 2000;
                break;
            case R.id.RapideBut:
                m_AnimationSpeed = 1000;
                break;
        }
    }
}
