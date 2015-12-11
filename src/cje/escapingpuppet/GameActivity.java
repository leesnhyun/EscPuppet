package cje.escapingpuppet;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class GameActivity extends FragmentActivity {
	public static Activity gameActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		SoundManager.getInstance().Init(this);
		SoundManager.getInstance().addSound(1, R.raw.sound_clear);
		SoundManager.getInstance().addSound(2, R.raw.item);
		SoundManager.getInstance().addSound(3, R.raw.intro);
		SoundManager.getInstance().addSound(4, R.raw.fail);
		SoundManager.getInstance().addSound(5, R.raw.fail_2);
		
		super.onCreate(savedInstanceState);
		View gameView = new GameView(this);
		setContentView(gameView);
		
		gameActivity = GameActivity.this;
	}
	
	
}
