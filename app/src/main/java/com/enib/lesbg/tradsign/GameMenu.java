package com.enib.lesbg.tradsign;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.content.DialogInterface;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;
import android.widget.RadioButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Vector;

import static com.enib.lesbg.tradsign.ImageDAO.IMAGE_TABLE_NAME;

public class GameMenu extends Interface {
    private RadioButton Gif = null;
    private RadioButton Anim = null;
    private SharedPreferences sharedPreferences;
    private AnimatedCharacter animChara = null;
    private VideoView gifVideoView = null;
    private EditText m_AnswerText = null;
    private TextView m_QuestionText = null;
    private int m_ActualQuestion = 0;
    private int m_MaxQuestion = 5;
    private int m_GoodAnswers = 0;
    private String m_QuestionWord = "";
    private Vector< String > m_Questions = new Vector< String >();
    private AlertDialog m_AnswerDialog = null;
    AlertDialog.Builder m_Builder = null;
    AlertDialog.Builder m_BuilderResults = null;
    AlertDialog.Builder m_BuilderNewTest = null;
    private ImageDAO imgDAO = new ImageDAO(this);

    private Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imgDAO.open();

        initTheme();

        setContentView(R.layout.activity_game_menu);

        Gif = (RadioButton) findViewById(R.id.GifButG);
        Anim = (RadioButton) findViewById(R.id.AnimButG);
        loadButtons();

        m_QuestionText = (TextView) findViewById(R.id.QuestionText);
        m_AnswerText = (EditText) findViewById(R.id.AnswerText);
        animChara = (AnimatedCharacter) findViewById(R.id.AnimatedCharacterG);
        gifVideoView = (VideoView) findViewById(R.id.GifVideoViewG);
        gifVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion (MediaPlayer mp) {
                mp.start();
            }
        });
        actualizeAnimView();

        // building dialog
        m_Builder = new AlertDialog.Builder(this);
        m_Builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(m_ActualQuestion != m_MaxQuestion) {
                    nextQuestion();
                }
                else {
                    affichageResultats();
                }
            }
        });

        m_BuilderNewTest = new AlertDialog.Builder(this);
        m_BuilderNewTest.setMessage("Voulez vous relancer un nouveau test ?");
        m_BuilderNewTest.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                m_ActualQuestion = 0;
                m_GoodAnswers = 0;
                newTest();
            }
        });
        m_BuilderNewTest.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });

        final AlertDialog newTestDialog = m_BuilderNewTest.create();

        m_BuilderResults = new AlertDialog.Builder(this);
        m_BuilderResults.setTitle("Résultats");
        m_BuilderResults.setPositiveButton("Ook", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                newTestDialog.show();
            }
        });

        if(sharedPreferences.getBoolean("Lent", false)) m_AnimationSpeed = 3000;
        else if(sharedPreferences.getBoolean("Normale", true)) m_AnimationSpeed = 2000;
        else if(sharedPreferences.getBoolean("Rapide", false)) m_AnimationSpeed = 1000;
        animChara.setAnimationTime(m_AnimationSpeed);

        newTest();
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
            case R.id.GifButG:
                m_AnimationStyle = 0;
                break;
            case R.id.AnimButG:
                m_AnimationStyle = 1;
                break;
        }
        saveButtons();
        actualizeAnimView();
    }

    public void onValidateButtonCLicked(View view) {
        m_ActualQuestion++;

        if(m_AnswerText.getText().toString().equals(m_QuestionWord)) {
            m_Builder.setTitle("Bonne réponse");
            m_Builder.setMessage("Bien joué, vous avez réussi à apprendre un nouveau mot en LDS !");
            m_GoodAnswers += 1;
        }
        else {
            m_Builder.setTitle("Mauvaise réponse");
            m_Builder.setMessage("La bonne signification de ce signe est : " + m_QuestionWord);
        }

        m_AnswerDialog = m_Builder.create();
        m_AnswerDialog.show();
    }

    private void nextQuestion() {
        m_QuestionWord = m_Questions.elementAt(m_ActualQuestion);
        m_QuestionText.setText("Question " + String.valueOf(m_ActualQuestion+1));

        animChara.prepareNewAnim(m_QuestionWord);
        String resName = imgDAO.getResName(m_QuestionWord);
        String path = "android.resource://" + getPackageName() +
                "/" + getResources().getIdentifier(resName, "raw", getPackageName());
        gifVideoView.setVideoURI(Uri.parse(path));

        if(animChara.getVisibility() == View.INVISIBLE) {
            gifVideoView.setVisibility(View.VISIBLE);
            gifVideoView.start();
        }

        m_AnswerText.setText("");
    }

    private void affichageResultats() {
        m_QuestionText.setText("Fin du test");
        m_BuilderResults.setMessage("Votre score est de " + m_GoodAnswers + " / " + m_MaxQuestion);
        AlertDialog resultsDialog = m_BuilderResults.create();
        resultsDialog.show();
    }

    private void newTest() {
        m_Questions.clear();

        SQLiteDatabase db = imgDAO.getDb();
        Cursor c = db.rawQuery("select * from " + IMAGE_TABLE_NAME, null);
        Map<Integer, Boolean> pile = new HashMap<>();
        for(int i = 0; i < m_MaxQuestion; i++) {
            int p;
            while(pile.containsKey((p = rand.nextInt(c.getCount())))) {}
            pile.put(p, true);
            c.moveToPosition(p);
            m_Questions.add(c.getString(0));
        }
        c.close();

        nextQuestion();
    }

    private void actualizeAnimView() {
        if(m_AnimationStyle == 0) {
            gifVideoView.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 3);
            layout.gravity= Gravity.CENTER_HORIZONTAL;
            gifVideoView.setLayoutParams(layout);
            gifVideoView.start();
            animChara.setVisibility(View.INVISIBLE);
            animChara.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0));
        }
        else if(m_AnimationStyle == 1) {
            gifVideoView.setVisibility(View.INVISIBLE);
            gifVideoView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0));
            if(gifVideoView.isPlaying()) {
                gifVideoView.suspend();
            }
            animChara.setVisibility(View.VISIBLE);
            animChara.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 3));
        }
    }
}