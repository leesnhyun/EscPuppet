package cje.escapingpuppet;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;
import cje.escapingpuppet.R;

public class IntroState implements IState{
	GraphicObject ready;
	GraphicObject introImage;
	
	public void Init(){
		ready = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.ready));
		introImage = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.intro_image_1));
		ready.SetPosition(800, 90);
		SoundManager.getInstance().play(3);
	}
	public void Destroy(){
		
	}
	public void Update(){
		
	}
	public void Render(Canvas canvas){
		introImage.Draw(canvas);
		ready.Draw(canvas);
	}
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(keyCode==KeyEvent.KEYCODE_BACK){
			AppManager.getInstance().getGameView().gameActivity.finish();
		}
		return true;
	}
	public boolean onTouchEvent(MotionEvent event){
		AppManager.getInstance().getGameView().ChangeGameState(new Stage1State());
		return true;
	}
}
