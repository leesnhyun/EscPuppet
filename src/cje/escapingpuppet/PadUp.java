package cje.escapingpuppet;

import android.graphics.Canvas;

public class PadUp extends GraphicObject {
	public PadUp(){
		super(AppManager.getInstance().getBitmap(R.drawable.pad_up));
		SetPosition(128, 940);
	}
	
	public void Update(float x, float y){
		this.m_x = this.m_x + (int) x;
		this.m_y = this.m_y + (int) y;
	}
	
	public void Draw(Canvas canvas){
		canvas.drawBitmap(m_bitmap, m_x, m_y, null);
	}
}
