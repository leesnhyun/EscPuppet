package cje.escapingpuppet.unit;

import android.graphics.Bitmap;
import cje.escapingpuppet.AppManager;
import cje.escapingpuppet.R;

public class Puppet extends Unit {
	private Bitmap hold;
	
	public Puppet() {
		super(AppManager.getInstance().getBitmap(R.drawable.puppet_hold));
		this.InitSpriteData(240, 288, 3, 4);
		direction = 0;
		range = 500;
		fps = 5;
		hold = AppManager.getInstance().getBitmap(R.drawable.puppet_hold);
		front = AppManager.getInstance().getBitmap(R.drawable.puppet_front);
		back = AppManager.getInstance().getBitmap(R.drawable.puppet_back);
		left = AppManager.getInstance().getBitmap(R.drawable.puppet_left);
		right = AppManager.getInstance().getBitmap(R.drawable.puppet_right);
	}
	
	public void increaseRange(int n){
		this.range += n;
	}
	
	public void setFps(int n){
		this.fps = n;
	}
	
	public void Update(int dir, long GameTime){
		super.Update(GameTime);
		if(dir==0){
			this.ChangeImage(hold);
		}else if(dir==1){
			this.ChangeImage(back);
		}else if(dir==2){
			this.ChangeImage(left);
		}else if(dir==3){
			this.ChangeImage(front);
		}else if(dir==4){
			this.ChangeImage(right);
		}
		
	}
}
