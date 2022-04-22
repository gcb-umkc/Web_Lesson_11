package com.example.web_lesson_11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextToSpeech tts;
    private EditText text;
    private SeekBar pitch;
    private SeekBar speed;
    private Button speakButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speakButton = findViewById(R.id.speak);


        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i == TextToSpeech.SUCCESS){
                    int result = tts.setLanguage(Locale.US);

                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS", "Language not supported");
                    }
                    else{ speakButton.setEnabled(true); }
                }
                else{ Log.e("TTS", "TTS initialization failed"); }
            }
        });

        text = findViewById(R.id.text);
        pitch = findViewById(R.id.seek_bar_pitch);
        speed = findViewById(R.id.seek_bar_speed);

        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activateTTS();
            }
        });
    }
    private void activateTTS(){
        String input = text.getText().toString();
        float pitchInput = (float) pitch.getProgress() / 50;
        if (pitchInput < 0.1) { pitchInput = 0.1f;}
        float speedInput = (float) speed.getProgress() / 50;
        if (speedInput < 0.1) { speedInput = 0.1f;}

        tts.setPitch(pitchInput);
        tts.setSpeechRate(speedInput);
        Log.e("Message", input);
        tts.speak(input, TextToSpeech.QUEUE_FLUSH, null);

    }
    @Override
    protected void onDestroy(){
        if(tts != null){
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
