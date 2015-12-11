package cje.escapingpuppet;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;
import cje.escapingpuppet.R;

public class GameOverState implements IState{
	GraphicObject gameover;
	
	
	public void Init(){
		gameover = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.game_over));
		gameover.SetPosition(150, 300);
	}
	public void Destroy(){
		
	}
	public void Update(){
		
	}
	public void Render(Canvas canvas){
		gameover.Draw(canvas);
	}
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(keyCode==KeyEvent.KEYCODE_BACK){
			AppManager.getInstance().getGameView().gameActivity.finish();
		}
		return true;
	}
	public boolean onTouchEvent(MotionEvent event){
		AppManager.getInstance().getGameView().ChangeGameState(new IntroState());
		return true;
	}
}
