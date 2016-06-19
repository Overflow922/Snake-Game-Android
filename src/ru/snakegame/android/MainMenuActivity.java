package ru.snakegame.android;

/**
 * Author: Юрий
 * Creation: 03.06.2016 at 19:47
 * Description:
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import ru.snakegame.settings.GameSettings;
import ru.snakegame.settings.GameStates;

public class MainMenuActivity extends Activity implements OnClickListener {

    private static final String TAG = MainMenuActivity.class.getSimpleName();

    Button btnNewGame;
    Button btnContinue;
    Button btnOptions;

    private static long back_pressed;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        btnNewGame = (Button)findViewById(R.id.new_game);
        btnContinue = (Button)findViewById(R.id.menu_continue);
        btnOptions = (Button)findViewById(R.id.options);
//        final Button btnQuit = (Button)findViewById(R.id.quit);

        btnNewGame.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        btnContinue.setVisibility(View.GONE);
        btnOptions.setOnClickListener(this);
//        btnQuit.setOnClickListener(this);

        GameSettings.getInstance().setGameState(GameStates.GAME_MAIN_MENU);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "MainMenu.onClick");
        switch (v.getId()) {
//            case R.id.quit:
//                Toast.makeText(getApplicationContext(), "Quit pressed", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.new_game:

                GameSettings.getInstance().setGameState(GameStates.GAME_START_MAIN_CYCLE);
                btnContinue.setVisibility(View.VISIBLE);
                Intent intent = new Intent(this, MyActivity.class);
                startActivity(intent);
                break;
            case R.id.options:
                Toast.makeText(getApplicationContext(), "Options pressed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_continue:
                GameSettings.getInstance().setGameState(GameStates.GAME_CONTINUE_MAIN_CYCLE);
                btnContinue.setVisibility(View.VISIBLE);
                Intent intent1 = new Intent(this, MyActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent1);
            default:
                break;
        }
    }

    @Override
    public  void onStart() {
        Log.d(TAG, "MainMenu.onStart");
        super.onStart();
        GameSettings.getInstance().setGameState(GameStates.GAME_MAIN_MENU);
    }
    @Override
    public void onRestart(){
        Log.i(TAG, "MainMenu.onRestart");
        super.onRestart();
        GameSettings.getInstance().setGameState(GameStates.GAME_MAIN_MENU);
    }

    @Override
    public  void onPause() {
        Log.i(TAG, "MainMenu.onPause");
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.i(TAG, "MainMenu.onResume");
        super.onResume();
        GameSettings.getInstance().setGameState(GameStates.GAME_MAIN_MENU);
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis())
            super.onBackPressed();
        else
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }


    @Override
    public void onDestroy() {
        Log.d(TAG, "MainMenu.Destroying...");
        super.onDestroy();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "MainMenu.Stopping...");
        super.onStop();
    }
}