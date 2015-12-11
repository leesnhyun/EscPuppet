package cje.escapingpuppet;

import android.graphics.Canvas;

public class BackGround extends GraphicObject {
	public BackGround(){
		super(AppManager.getInstance().getBitmap(R.drawable.game_background_01));
		SetPosition(-1280, -660);
	}
	
	public void Update(int dir, int delta){
		if(dir == 1){
			this.m_y += delta;
		}else if(dir == 2){
			this.m_x += delta;
		}else if(dir == 3){
			this.m_y -= delta;
		}else if(dir == 4){
			this.m_x -= delta;
		}else if(dir == 0){
			
		}
	}
	
	public void Draw(Canvas canvas){
		canvas.drawBitmap(m_bitmap, m_x, m_y, null);
	}
}
