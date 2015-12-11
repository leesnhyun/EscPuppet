package cje.escapingpuppet;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;
import cje.escapingpuppet.R;

public class ClearState implements IState{
	GraphicObject clearImage;
	GraphicObject clear;
	
	public void Init(){
		clearImage = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.clear_image));
		clear = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.clear));
		clear.SetPosition(300, 200);
	}
	public void Destroy(){
		
	}
	public void Update(){
		
	}
	public void Render(Canvas canvas){
		clearImage.Draw(canvas);
		clear.Draw(canvas);
	}
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(keyCode==KeyEvent.KEYCODE_BACK){
			AppManager.getInstance().getGameView().gameActivity.finish();
		}
		return true;
	}
	public boolean onTouchEvent(MotionEvent event){
		return true;
	}
}
