package com.example.hw_02.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.hw_02.R;
import com.example.hw_02.classes.Card;
import com.example.hw_02.classes.Deck;
import com.example.hw_02.classes.Record;
import com.example.hw_02.classes.TopTen;
import com.example.hw_02.utils.MySP;
import com.google.gson.Gson;

public class GameActivity extends AppCompatActivity {

    private final Context context = this;
    private ImageView Img_cards_panda;
    private ImageView Img_cards_lion;
    private TextView Vscore_panda;
    private TextView Vscore_lion;
    private ProgressBar progressBar;
    private int score_lion;
    private int score_panda;
    private String winner;
    private int iterations;
    private TopTen topTenList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_);
        Log.d("hw_02_app_Game:", "onCreate");
        topTenList = new TopTen();
        String str = MySP.getInstance().getString("top10", "");
        if (!str.equals("")) {
            topTenList = new Gson().fromJson(str, TopTen.class);
        }
        startGame();
    }

    private void startGame() {
        Deck myImageList = new Deck(52);
        Deck panda_cards = new Deck(myImageList.getSize() / 2);
        Deck lion_cards = new Deck(myImageList.getSize() / 2);

        initViews();
        initializeGame(myImageList, panda_cards, lion_cards);
        game(panda_cards, lion_cards);
    }

    /**
     * Shuffle and Divide the cards.
     * Initialize the scores.
     *
     * @param myImageList - All card
     * @param panda_cards - Gets the panda cards
     * @param lion_cards  - Gets the lion cards
     */
    private void initializeGame(Deck myImageList, Deck panda_cards, Deck lion_cards) {
        myImageList.addAllCards(this);
        myImageList.shuffle();
        myImageList.divideInto2(panda_cards, lion_cards);

        createPic(this, this.getResources().getIdentifier("poker", "drawable", this.getPackageName()), Img_cards_panda);
        createPic(this, this.getResources().getIdentifier("poker", "drawable", this.getPackageName()), Img_cards_lion);

        iterations = 0;
        score_panda = 0;
        score_lion = 0;
        Vscore_lion.setText("0");
        Vscore_panda.setText("0");
    }

    /**
     * @param panda_cards - list of 26 cards.
     * @param lion_cards  - list of 26 cards.
     *                    Game function make 26 iterations of covering card.
     *                    It uses the handler to creating a delay between each iteration.
     *                    When all 26 iterations is over, goes to second activity.
     */
    private void game(Deck panda_cards, Deck lion_cards) {
        final Handler handler = new Handler();
        int delay = 200;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iterations++;
                oneTurn(panda_cards, lion_cards);
                if (iterations < 26)
                    handler.postDelayed(this, delay);
                else {
                    top10Activity();
                }
            }
        }, delay);
    }

    /**
     * Do one turn, reveals card for each player and increase the score for the higher value.
     *
     * @param panda_cards - Deck of panda player.
     * @param lion_cards  - Deck of lion player.
     */
    private void oneTurn(Deck panda_cards, Deck lion_cards) {
        progressBar.incrementProgressBy(1);
        Card cardP = panda_cards.takeCard(0);
        Card cardL = lion_cards.takeCard(0);
        createPic(context, cardP.getIdentifier(), Img_cards_panda);
        createPic(context, cardL.getIdentifier(), Img_cards_lion);
        if (cardL.getValue() >= cardP.getValue()) {
            score_lion++;
            Vscore_lion.setText(score_lion + "");
            winner = "lion";
        }
        if (cardP.getValue() >= cardL.getValue()) {
            score_panda++;
            Vscore_panda.setText(score_panda + "");
            winner = "panda";
        }
    }

    private void top10Activity() {
        Intent myIntent = new Intent(context, TopTenActivity.class);
        Record r = createRecord();
        if (topTenList.add(r)) ;
        {
            createLocation(r);
            String str = new Gson().toJson(topTenList);
            MySP.getInstance().putString("top10", str);
        }
        startActivity(myIntent);
        finish();
    }

    /*
     * Create record without lat,lng attributes
     * */
    private Record createRecord() {
        int score = Math.max(score_panda, score_lion);
        if (score_panda == score_lion)
            winner = "both";
        String date = DateFormat.format("dd.MM.yy HH:mm", System.currentTimeMillis() + (1000 * 60 * 60 * 24)).toString();
        Record r = new Record(winner, date, score);
        return r;
    }

    private void createLocation(Record record) {
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        try {
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                location = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            }
            if (location == null)
                throw new SecurityException();
            record.setLat(location.getLatitude());
            record.setLng(location.getLongitude());
        } catch (SecurityException se) {
            Toast.makeText(this, "Permission denied. Deafult location", Toast.LENGTH_SHORT).show();
            record.setLat(32.11497879118779);
            record.setLng(34.818146817602894);
        }
    }

    /*
     * Create the pic with Glide library*/
    public void createPic(Context activity, int id, ImageView card) {
        Glide
                .with(activity)
                .load(id)
                .into(card);
    }

    private void initViews() {
        Img_cards_panda = findViewById(R.id.game_IMG_pandaCard);
        Img_cards_lion = findViewById(R.id.game_IMG_lionCard);
        Vscore_panda = findViewById(R.id.game_LBL_scorePanda);
        Vscore_lion = findViewById(R.id.game_LBL_scoreLion);
        progressBar = findViewById(R.id.game_PRG_pBar);
    }

    @Override
    protected void onStart() {
        Log.d("hw_02_app_Game:", "onStart");
        super.onStart();

    }

    @Override
    protected void onResume() {
        Log.d("hw_02_app_Game:", "onResume");
        super.onResume();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null)
            throw new AssertionError();
        else actionBar.hide();
    }

    @Override
    protected void onPause() {
        Log.d("hw_02_app_Game:", "onPause");
        super.onPause();

    }

    @Override
    protected void onStop() {
        Log.d("hw_02_app_Game:", "onStop");
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        Log.d("hw_02_app_Game:", "onDestroy");
        super.onDestroy();
    }
}