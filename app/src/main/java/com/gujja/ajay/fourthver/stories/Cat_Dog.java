package com.gujja.ajay.fourthver.stories;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gujja.ajay.fourthver.R;

import java.util.HashMap;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

public class Cat_Dog extends AppCompatActivity implements TextToSpeech.OnInitListener {


    @BindView(R.id.Cat_Dog_textSent)
    TextView CatDogTextSent;
    @BindView(R.id.Cat_Dog_TextWord)
    TextView CatDogTextWord;
    @BindView(R.id.Cat_Dog_Button_speak)
    Button CatDogButtonSpeak;
    @BindView(R.id.Cat_Dog_button_stop)
    Button CatDogButtonStop;
    @BindView(R.id.Cat_Dog_Sign_Gif)
    GifImageView CatDogSignGif;

    int i = 0;
    int j = 0;
    float speed = 0.7f;
    float pitch = 0.8f;
    private TextToSpeech tts;

    String[] cat = {"Once", "upon", "a", "time", "in", "a", "fairy", "tale", "land", "a", "cat", "and", "a", "dog", "were", "friends"," ",
            "One", "night", "the", "cat", "invited", "the", "dog", "for", "a", "party", "at", "his", "house"," ",
            "The", "cat", "played", "the", "fiddle"," ",
            "The", "dog", "happily", "clapped", "his", "hands"," ",
            "Suddenly", "they", "saw", "a", "cow", "flying", "in", "the", "sky"," ",
            "It", "jumped", "over", "the", "moon"," ",
            "The", "dog", "laughed"," ",
            "Just", "then", "they", "saw", "a", "dish", "and", "a", "spoon", "from", "the", "party", "running", "away", "together", " ",
            "And", "they", "laughed", "even", "louder"," ",
            "After", "that", "they", "became", "the", "best", "friends"};


    String[] dog = {"Once upon a time in a fairy tale land a cat and a dog were friends.",
            "One night, the cat invited the dog for a party at his house.",
            "The cat played the fiddle.",
            "The dog happily clapped his hands.",
            "Suddenly, they saw a cow flying in the sky.",
            "It jumped over the moon.",
            "The dog laughed.",
            "Just then, they saw a dish and a spoon from the party running away together.",
            "And they laughed even louder.",
            "After that they became the best friends."};

    String[] stopwords = {
            "the", "all", "into", "loaf", "but", "for", "and", "at", "found", "of", "in", "squeezed", "hole", "to", "have", "caught", "gave",
            "came", "on", "become", "trick", "with", "carry", "cotton", "that", "felt", "every", "stream", "lesson", "let", "upon",
            "tremble", "fear", "left", "anpther", "other", "by", "hunter", "thus", "afterwards", "used", "cross", "tumbled", "also", "fell", "hence",
            "loaded", "would", "be", "still", "become", "dampened", "wet", "anymore", "an", "feeling", "den", "find", "only", "hesitation", "can", "fill",
            "as", "about", "instead", "went", "letting", "off"
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat__dog);
        ButterKnife.bind(this);

        CatDogTextSent.setText(dog[0]);
        CatDogTextWord.setText(cat[0]);

        // Initialization of Text To Speech
        tts = new TextToSpeech(this, this);

        // Tracking of Words
        tts.setOnUtteranceProgressListener(mProgressListener);


        CatDogButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak(cat,i);
            }
        });

        CatDogButtonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak(dog,i);
            }
        });
    }



    private void stop() {
        tts.stop();
        tts.shutdown();
        CatDogTextWord.setText(cat[0]);
        CatDogTextSent.setText(dog[0]);
        CatDogSignGif.setVisibility(View.INVISIBLE);
    }


    private void speak(String[] text, int i) {
        tts.setSpeechRate(speed);  // 0.7f
        tts.setPitch(pitch);
        HashMap<String, String> map = new HashMap<>();


        for (String stopword : stopwords) {
            if (cat[i].toLowerCase().equals(stopword)) {
                char[] alphabet_array = stopword.toCharArray();

                for (char c : alphabet_array) {
                    map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, String.valueOf(c).toLowerCase());
                    tts.setSpeechRate(0.3f);
                    tts.speak(String.valueOf(c), TextToSpeech.QUEUE_ADD, map);
                }
            }
        }

        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, cat[i]);
        tts.speak(cat[i].toLowerCase(), TextToSpeech.QUEUE_ADD, map);


    }

    UtteranceProgressListener mProgressListener = new UtteranceProgressListener() {
        @Override
        public void onStart(String utteranceId) {

            // For Highlighting Spoken Words
            String Replace = "<span style= 'background-color:green'>" + utteranceId + "</span>";
            CatDogTextWord.setText(Html.fromHtml(Replace));


            if(utteranceId.toLowerCase().equals("try") || utteranceId.toLowerCase().equals("catch")){
                int gif_view = getResources().getIdentifier(utteranceId.toLowerCase() +"1", "raw", getPackageName());
                CatDogSignGif.setImageResource(gif_view);
            } else {
                int gif_view = getResources().getIdentifier(utteranceId.toLowerCase(), "raw", getPackageName());
                CatDogSignGif.setImageResource(gif_view);
            }

        }

        @Override
        public void onDone(String utteranceId) {
            i = i + 1;
            speak(cat, i);


            if(utteranceId.equals(" ")){
                j++;
                CatDogTextSent.setText(dog[j]);

            }
        }

        @Override
        public void onError(String utteranceId) {

        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.speedmeter,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){
            case R.id.Slow :
                speed = 0.4f;
                Toast.makeText(this,"Slow is selected",Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Normal :
                speed = 0.8f;
                Toast.makeText(this,"Normal is selected",Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Fast :
                speed = 1.2f;
                Toast.makeText(this,"Fast is selected",Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setLanguage(Locale.ENGLISH);
        }
    }
}
