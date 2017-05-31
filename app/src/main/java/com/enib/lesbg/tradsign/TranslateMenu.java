package com.enib.lesbg.tradsign;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

public class TranslateMenu extends Interface {
    private RadioButton Gif = null;
    private RadioButton Anim = null;
    private AnimatedCharacter animChara = null;
    private VideoView gifVideoView = null;
    private boolean gifVideoLoaded = false;
    private EditText m_translateText = null;
    private SharedPreferences sharedPreferences;
    private ImageView TraductionIcon2 = null;
    private TextView notFound = null;
    private boolean isNotFound = false;
    private ImageDAO imgDAO = new ImageDAO(this);
    private String lastWord = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imgDAO.open();

        initTheme();

        setContentView(R.layout.activity_translate_menu);

        Gif = (RadioButton) findViewById(R.id.GifBut);
        Anim = (RadioButton) findViewById(R.id.AnimBut);
        notFound = (TextView) findViewById(R.id.notFoundText);
        notFound.setText("Ce mot n'a pas pu être trouvé dans notre base de données");
        TraductionIcon2 = (ImageView) findViewById(R.id.TraduireIcon2);
        if(m_Theme == 1){
            TraductionIcon2.setImageResource(R.drawable.traduire);
        }
        else if(m_Theme == 0){
            TraductionIcon2.setImageResource(R.drawable.traduirenuit);
        }
        loadButtons();

        m_translateText = (EditText) findViewById(R.id.TranslateText);
        animChara = (AnimatedCharacter) findViewById(R.id.AnimatedCharacter);
        gifVideoView = (VideoView) findViewById(R.id.GifVideoView);
        gifVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion (MediaPlayer mp) {
                mp.start();
            }
        });

        actualizeAnimView();

        if(sharedPreferences.getBoolean("Lent", false)) m_AnimationSpeed = 3000;
        else if(sharedPreferences.getBoolean("Normale", true)) m_AnimationSpeed = 2000;
        else if(sharedPreferences.getBoolean("Rapide", false)) m_AnimationSpeed = 1000;
        animChara.setAnimationTime(m_AnimationSpeed);
    }

    public void onDestroy() {
        imgDAO.close();
        animChara.onDestroy();
        super.onDestroy();
    }

    public void saveButtons(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("Gif", Gif.isChecked());
        editor.putBoolean("Anim", Anim.isChecked());

        editor.apply();
    }
    private void loadButtons() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gif.setChecked(sharedPreferences.getBoolean("Gif", true));
        Anim.setChecked(sharedPreferences.getBoolean("Anim", false));
    }

    public void onAnimButtonClicked(View view) {
        switch(view.getId()) {
            case R.id.GifBut:
                m_AnimationStyle = 0;
                break;
            case R.id.AnimBut:
                m_AnimationStyle = 1;
                break;
        }
        saveButtons();
        actualizeAnimView();
    }

    public void onFindButtonClicked(View view) {
        String text = m_translateText.getText().toString();

        String resName = imgDAO.getResName(text);
        if(resName != null && !lastWord.equals(text)) {
            String path = "android.resource://" + getPackageName() +
                    "/" + getResources().getIdentifier(resName, "raw", getPackageName());
            gifVideoView.setVideoURI(Uri.parse(path));

            if(animChara.getVisibility() == View.INVISIBLE) {
                gifVideoView.setVisibility(View.VISIBLE);
                gifVideoView.start();
            }

            animChara.prepareNewAnim(text);

            isNotFound = false;
            gifVideoLoaded = true;
        }
        else if(!lastWord.equals(text)) {
            notFound.setVisibility(View.VISIBLE);

            gifVideoView.setVisibility(View.INVISIBLE);
            animChara.setVisibility(View.INVISIBLE);

            isNotFound = true;
            gifVideoLoaded = false;
        }

        lastWord = new String(text);
        actualizeAnimView();
    }

    private void actualizeAnimView() {
        if(!isNotFound) {
            if (m_AnimationStyle == 0) {
                if(gifVideoLoaded) {
                    gifVideoView.setVisibility(View.VISIBLE);
                    gifVideoView.start();
                }
                animChara.setVisibility(View.INVISIBLE);
            } else if (m_AnimationStyle == 1) {
                if(gifVideoView.isPlaying()) {
                    gifVideoView.suspend();
                }
                gifVideoView.setVisibility(View.INVISIBLE);
                animChara.setVisibility(View.VISIBLE);
            }
            notFound.setVisibility(View.INVISIBLE);
        }
    }
}
