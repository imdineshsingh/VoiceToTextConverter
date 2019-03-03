package com.ghughutibasuti.texttospeech;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private TextView tv;
    private Button share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Android has an inbuilt feature speech to text through which you can provide speech input to your app
        //with this feature you can add some oif the cool features to your app like adding voice navigation


        btn= (Button) findViewById(R.id.btn);
        tv= (TextView) findViewById(R.id.tv);
        share= (Button) findViewById(R.id.share);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                promptSpeechInput();


            }
        });

  share.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View v) {

        Toast.makeText(MainActivity.this, "Share Text", Toast.LENGTH_SHORT).show();
        return true;
    }
});

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");

                String shareBody=tv.getText().toString();

                String shareSub="Your subject is here"; //in case of email
                intent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                intent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivityForResult(Intent.createChooser(intent,"Share Via"),123);

            }
        });

    }


   public void promptSpeechInput()
   {
       Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

       i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
       i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
       i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Something");

       try {
           startActivityForResult(i, 100);

       }
       catch(Exception e)
       {
         //  e.printStackTrace();
           Toast.makeText(this, "Galat Baat", Toast.LENGTH_SHORT).show();
       }
   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 100:

                if (resultCode==RESULT_OK && data!=null)
                {
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    tv.setText(result.get(0));
                }
                break;

            case 123:
                if(resultCode==RESULT_OK)
                {
                    Toast.makeText(MainActivity.this,"sent",Toast.LENGTH_LONG).show();

                }
        }
    }
}
