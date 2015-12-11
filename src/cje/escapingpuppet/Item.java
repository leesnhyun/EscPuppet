package cje.escapingpuppet;

import android.graphics.Bitmap;
import android.graphics.Rect;
import cje.escapingpuppet.SpriteAnimation;

public class Item extends SpriteAnimation {
	protected Rect hitBox = new Rect();
	protected Boolean eaten = false;
	
	public Item(Bitmap bitmap){
		super(bitmap);
		hitBox = new Rect();
	}
	
	public Rect calcHitBox(){
		Rect hitbox = new Rect();
		
		hitbox.bottom = this.GetY() + 144;
		hitbox.left = this.GetX();
		hitbox.right = this.GetX() + 120;
		hitbox.top = this.GetY();
		
		return hitbox;
	}
	
	public void Update(long GameTime, int dir, int delta){
		this.hitBox = calcHitBox();
		super.Update(GameTime);

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
	
	public Rect getHitBox(){
		return this.hitBox;
	}
	
	public void setEaten(){
		this.eaten = true;
	}
}
