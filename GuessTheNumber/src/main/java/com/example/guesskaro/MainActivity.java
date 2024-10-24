package com.example.guesskaro;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button correctButton, wrongButton;
    private ImageView boomImageView, wastedImageView;
    private MediaPlayer boomSound, wastedSound;
    private int correctNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        correctButton = findViewById(R.id.correctButton);
        wrongButton = findViewById(R.id.wrongButton);
        boomImageView = findViewById(R.id.boomImageView);
        wastedImageView = findViewById(R.id.wastedImageView);

        boomSound = MediaPlayer.create(this, R.raw.wee);
        wastedSound = MediaPlayer.create(this, R.raw.negative);
        generateRandomNumber();
        setButtonTexts();
        correctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGuess(true);
            }
        });

        wrongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGuess(false);
            }
        });
    }
    private void generateRandomNumber() {
        Random random = new Random();
        correctNumber = random.nextInt(10) + 1; // Generate random number between 1 and 10
    }

    private void setButtonTexts() {
        Random random = new Random();
        boolean isCorrectButton = random.nextBoolean();

        if (isCorrectButton) {
            correctButton.setText(String.valueOf(correctNumber));
            int falseNumber = random.nextInt(10) + 1; // Generate another random number between 1 and 10
            while (falseNumber == correctNumber) {
                falseNumber = random.nextInt(10) + 1; // Ensure false number is different from correct number
            }
            wrongButton.setText(String.valueOf(falseNumber));
        } else {
            wrongButton.setText(String.valueOf(correctNumber));
            int falseNumber = random.nextInt(10) + 1; // Generate another random number between 1 and 10
            while (falseNumber == correctNumber) {
                falseNumber = random.nextInt(10) + 1; // Ensure false number is different from correct number
            }
            correctButton.setText(String.valueOf(falseNumber));
        }
    }

    private void checkGuess(boolean isCorrect) {
        if (isCorrect) {
            if (correctNumber % 2 == 0) {
                boomImageView.setVisibility(View.VISIBLE);
                boomSound.start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boomImageView.setVisibility(View.GONE);
                    }
                }, 2000);
            } else {
                wastedImageView.setVisibility(View.VISIBLE);
                wastedSound.start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        wastedImageView.setVisibility(View.GONE);
                    }
                }, 2000);
            }
            generateRandomNumber();
            setButtonTexts();
        } else {
            if (correctNumber % 2 != 0) {
                boomImageView.setVisibility(View.VISIBLE);
                boomSound.start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boomImageView.setVisibility(View.GONE);
                    }
                }, 2000);
            } else {
                wastedImageView.setVisibility(View.VISIBLE);
                wastedSound.start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        wastedImageView.setVisibility(View.GONE);
                    }
                }, 2000);
            }
        }
    }
//for playing the sounds
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (boomSound != null) {
            boomSound.release();
            boomSound = null;
        }
        if (wastedSound != null) {
            wastedSound.release();
            wastedSound = null;
        }
    }
}