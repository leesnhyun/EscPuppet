package cje.escapingpuppet.unit;

import android.graphics.Bitmap;
import android.graphics.Rect;
import cje.escapingpuppet.SpriteAnimation;

public class Unit extends SpriteAnimation {
	protected int direction;
	protected int fps;
	protected int range;
	protected Rect hitBox = new Rect();
	protected int dst_x;
	protected int dst_y;
	protected Bitmap front;
	protected Bitmap back;
	protected Bitmap left;
	protected Bitmap right;
	
	public Unit(Bitmap bitmap){
		super(bitmap);
		hitBox = new Rect();
	}
	
	void Move(int dst_x, int dst_y){

	}
	
	public Rect calcHitBox(){
		Rect hitbox = new Rect();
		
		hitbox.bottom = this.GetY() + 144;
		hitbox.left = this.GetX();
		hitbox.right = this.GetX() + 120;
		hitbox.top = this.GetY();
		
		return hitbox;
	}
	
	public void Update(long GameTime){
		this.hitBox = calcHitBox();
		super.Update(GameTime);
	}
	
	public int getFps(){
		return this.fps;
	}
	
	public int getRange(){
		return this.range;
	}
	
	public Rect getHitBox(){
		return this.hitBox;
	}
}
