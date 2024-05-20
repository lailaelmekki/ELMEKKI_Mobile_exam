package com.example.elmekki_mobile;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView playerFortuneTextView;
    private TextView casinoFortuneTextView;
    private TextView rollResultTextView;
    private ImageView diceImageView;
    private TextView diceResultTextView;
    private Button rollDiceButton;
    private Button endGameButton;

    private int playerFortune;
    private int casinoFortune;

    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        playerFortuneTextView = findViewById(R.id.playerFortune);
        casinoFortuneTextView = findViewById(R.id.casinoFortune);
        rollResultTextView = findViewById(R.id.rollResult);
        diceImageView = findViewById(R.id.diceImage);
        diceResultTextView = findViewById(R.id.diceResultText);
        rollDiceButton = findViewById(R.id.rollDiceButton);
        endGameButton = findViewById(R.id.endGameButton);

        random = new Random();

        // Initialiser les fortunes de manière aléatoire
        playerFortune = random.nextInt(91) + 10; // Entre 10 et 100
        casinoFortune = random.nextInt(91) + 10; // Entre 10 et 100

        updateFortuneDisplays();

        rollDiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice();
            }
        });

        endGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endGame();
            }
        });
    }

    private void rollDice() {
        int diceRoll = random.nextInt(6) + 1;
        updateDiceImage(diceRoll);

        if (diceRoll == 2 || diceRoll == 3) {
            playerFortune++;
            casinoFortune--;
        } else {
            playerFortune--;
            casinoFortune++;
        }

        updateFortuneDisplays();

        if (playerFortune <= 0) {
            Toast.makeText(this, "Le joueur a fait faillite !", Toast.LENGTH_LONG).show();
            rollDiceButton.setEnabled(false);
        } else if (casinoFortune <= 0) {
            Toast.makeText(this, "Le casino a fait faillite !", Toast.LENGTH_LONG).show();
            rollDiceButton.setEnabled(false);
        }
    }

    private void updateDiceImage(int diceRoll) {
        String imageName = "dice_" + diceRoll;
        int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        diceImageView.setImageResource(resId);
        diceResultTextView.setText(String.valueOf(diceRoll));
        rollResultTextView.setText("Résultat : " + diceRoll);
    }

    private void updateFortuneDisplays() {
        playerFortuneTextView.setText("Fortune du joueur : " + playerFortune);
        casinoFortuneTextView.setText("Fortune du casino : " + casinoFortune);
    }

    private void endGame() {
        finish();
    }
}
