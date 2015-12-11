package cje.escapingpuppet;

import java.util.ArrayList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import cje.escapingpuppet.unit.Puppet;

public class Stage1State implements IState{
	private BackGround m_backGround;
	private PadUp p_up;
	private PadDown p_down;
	private PadLeft p_left;
	private PadRight p_right;
	private Puppet puppet;
	private DrawLine drawLine;
	private int dir;
	private int p_x;
	private int p_y;
	private int fourth;
	private int numOfEdge;
	private long LastRmTime;
	private long LastMkTime;
	private boolean rmFlag = false;
	private boolean mkFlag = false;
	private ArrayList<Rect> obstacles;
	private ArrayList<Edge> edgelist;
	private RangeUp itemlist[];
	private Rect goal;
	
	public void Init(){
		m_backGround = new BackGround();
		p_up = new PadUp();
		p_down = new PadDown();
		p_right = new PadRight();
		p_left = new PadLeft();
		puppet = new Puppet();
		puppet.SetPosition(1240, 480);
		dir = 0;
		p_x = 0;
		p_y = 0;
		numOfEdge = 0;
		fourth = 0; // 4ºÐÀ§¼ö
		obstacles = new ArrayList<Rect>();
		edgelist = new ArrayList<Edge>();
		RangeUp item1 = new RangeUp();
		item1.SetPosition(800, 0);
		RangeUp item2 = new RangeUp();
		item2.SetPosition(1200, 900);
		RangeUp item3 = new RangeUp();
		item3.SetPosition(400, 1400);
		RangeUp item4 = new RangeUp();
		item4.SetPosition(800, 600);
		RangeUp item5 = new RangeUp();
		item5.SetPosition(1100, -300);
		itemlist = new RangeUp[5];
		itemlist[0] = item1;
		itemlist[1] = item2;
		itemlist[2] = item3;
		itemlist[3] = item4;
		itemlist[4] = item5;
		
		Edge startPnt = new Edge(0,0,0);
		//Edge test = new Edge(400, 500, 4);
		Rect pin = new Rect(-240,-400,0,-150);
		Rect ob1 = new Rect(-430,-810,-220,-580);
		Rect ob2 = new Rect(-10,-1020,210,-770);
		Rect ob3 = new Rect(-220,-770,210,-680);
		Rect ob4 = new Rect(-920,-420,-700,-330);
		Rect ob5 = new Rect(-1200,-370,-920,-240);
		Rect ob6 = new Rect(-210,75,430,330);
		Rect ob7 = new Rect(430,150,560,400);
		Rect ob8 = new Rect(85,310,500,490);
		Rect ob9 = new Rect(-520,300,-120,500);
		Rect ob10 = new Rect(-550,300,-370,560);
		Rect ob11 = new Rect(-630,560,-405,660);
		Rect ob12 = new Rect(-275,155,-215,300);
		Rect ob13 = new Rect(450,-660,880,-430);
		Rect ob14 = new Rect(390,-600,450,-430);
		Rect ob15 = new Rect(620,-1250,1330,-630);
		Rect ob16 = new Rect(750,-630,1150,-580);
		
		edgelist.add(startPnt);
		numOfEdge++;
		//edgelist.add(test);
		//numOfEdge++;
		obstacles.add(pin);
		obstacles.add(ob1);
		obstacles.add(ob2);
		obstacles.add(ob3);
		obstacles.add(ob4);
		obstacles.add(ob5);
		obstacles.add(ob6);
		obstacles.add(ob7);
		obstacles.add(ob8);
		obstacles.add(ob9);
		obstacles.add(ob10);
		obstacles.add(ob11);
		obstacles.add(ob12);
		obstacles.add(ob13);
		obstacles.add(ob14);
		obstacles.add(ob15);
		obstacles.add(ob16);
		drawLine = new DrawLine();
		goal = new Rect(-1500, -200, -1450, -100);
	}
	public void Destroy(){
		
	}
	public void Update() {
		Long GameTime = System.currentTimeMillis();
		if(CanMove(p_x, p_y, dir)==true){
			m_backGround.Update(dir, puppet.getFps());
			Move(dir);
			for(int i = 0; i < 5; i++){
				itemlist[i].Update(GameTime,dir,puppet.getFps());
				if(itemlist[i].getHitBox().contains(puppet.GetX()+50, puppet.GetY()+100) && itemlist[i].eaten == false){
					itemlist[i].ChangeImage(AppManager.getInstance().getBitmap(R.drawable.item_eaten));
					itemlist[i].setEaten();
					puppet.increaseRange(200);
					SoundManager.getInstance().play(2);
				}
			}
		}
		
		if(getDistance(edgelist, p_x, p_y) > puppet.getRange() - 100){
			drawLine.SetPaint(Color.RED);
			puppet.setFps(5);
		}else if(getDistance(edgelist, p_x, p_y) > puppet.getRange() - 20){
			drawLine.SetPaint(Color.BLACK);
		}else if(getDistance(edgelist, p_x, p_y) < puppet.getRange() / 2){
			drawLine.SetPaint(Color.GREEN);
			puppet.setFps(15);
		}else{
			drawLine.SetPaint(Color.rgb(255, 127, 39));
			puppet.setFps(10);
		}
		
		puppet.Update(dir, GameTime);
		
		if(p_x >= 0 && p_y < 0){
			fourth = 4;
		}else if(p_x < 0 && p_y < 0){
			fourth = 3;
		}else if(p_x < 0 && p_y >= 0){
			fourth = 2;
		}else if(p_x >= 0 && p_y >= 0){
			fourth = 1;
		}
		
		if(rmFlag = true){
			if(GameTime - LastRmTime > 30000){
				rmFlag = false;
			}
		}
		if(mkFlag = true){
			if(GameTime - LastMkTime > 30000){
				mkFlag = false;
			}
		}
		
		if(goal.contains(p_x,p_y)){
			SoundManager.getInstance().play(1);
			
			AppManager.getInstance().getGameView().ChangeGameState(new ClearState());
		}
	}
	
	public void Move(int dir){
		if(dir == 1){
			if((edgelist.get(numOfEdge-1).getDir()==1) && !rmFlag && ChkRm(p_x + 50, p_y + 100, edgelist, edgelist.get(numOfEdge-1).getFourth(), dir)){
				edgelist.remove(numOfEdge-1);
				numOfEdge--;
				rmFlag = true;
				LastRmTime = System.currentTimeMillis();
			}
			/*if(EdgeDetect(edgelist, p_x + 50, p_y + 100) && !mkFlag){
				Edge tmp = new Edge(p_x, p_y, 3);
				edgelist.add(tmp);
				numOfEdge++;
				mkFlag = true;
				LastMkTime = System.currentTimeMillis();
			}*/
			p_y -= puppet.getFps();

		}else if(dir == 2){
			if((edgelist.get(numOfEdge-1).getDir()==2) && !rmFlag && ChkRm(p_x + 50, p_y + 100, edgelist, edgelist.get(numOfEdge-1).getFourth(), dir)){
				edgelist.remove(numOfEdge-1);
				numOfEdge--;
				rmFlag = true;
				LastRmTime = System.currentTimeMillis();
			}
			/*if(EdgeDetect(edgelist, p_x + 50, p_y + 100) && !mkFlag){
				Edge tmp = new Edge(p_x, p_y, 4);
				edgelist.add(tmp);
				numOfEdge++;
				mkFlag = true;
				LastMkTime = System.currentTimeMillis();
			}*/
			p_x -= puppet.getFps();

		}else if(dir == 3){
			if((edgelist.get(numOfEdge-1).getDir()==3) && !rmFlag && ChkRm(p_x + 50, p_y + 100, edgelist, edgelist.get(numOfEdge-1).getFourth(), dir)){
				edgelist.remove(numOfEdge-1);
				numOfEdge--;
				rmFlag = true;
				LastRmTime = System.currentTimeMillis();
			}
			/*if(EdgeDetect(edgelist, p_x + 50, p_y + 100) && !mkFlag){
				Edge tmp = new Edge(p_x, p_y, 1);
				edgelist.add(tmp);
				numOfEdge++;
				mkFlag = true;
				LastMkTime = System.currentTimeMillis();
			}*/
			p_y += puppet.getFps();

		}else if(dir == 4){
			if((edgelist.get(numOfEdge-1).getDir()==4) && !rmFlag && ChkRm(p_x + 50, p_y + 100, edgelist, edgelist.get(numOfEdge-1).getFourth(), dir)){
				edgelist.remove(numOfEdge-1);
				numOfEdge--;
				rmFlag = true;
				LastRmTime = System.currentTimeMillis();
			}
			/*if(EdgeDetect(edgelist, p_x + 50, p_y + 100) && !mkFlag){
				Edge tmp = new Edge(p_x, p_y, 2);
				edgelist.add(tmp);
				numOfEdge++;
				mkFlag = true;
				LastMkTime = System.currentTimeMillis();
			}*/
			p_x += puppet.getFps();

		}
	}
	
	public boolean ChkRm(int p_x, int p_y, ArrayList<Edge> edgelist, int fourth, int dir){
		float deltaC = 1;
		float deltaP = 1;
		float cnt = 0;
		if(p_y != edgelist.get(numOfEdge-1).getY() && p_x != edgelist.get(numOfEdge-1).getX()){
			deltaC = (float)(p_x - edgelist.get(numOfEdge-1).getX()) / (float)(p_y - edgelist.get(numOfEdge-1).getY());
		}else{
			deltaC = 10000;
		}
		if(edgelist.get(numOfEdge-1).getY() != edgelist.get(numOfEdge-2).getY() && edgelist.get(numOfEdge-1).getX() != edgelist.get(numOfEdge-2).getX()){
			deltaP = (float)(edgelist.get(numOfEdge-1).getX() - edgelist.get(numOfEdge-2).getX()) / (float)(edgelist.get(numOfEdge-1).getY() - edgelist.get(numOfEdge-2).getY());
		}else{
			deltaP = 10000;
		}
		
		cnt = deltaC - deltaP;
		
		if(dir == 1){
			if(fourth == 1){
				if(p_x > edgelist.get(numOfEdge-1).getX() && p_y > edgelist.get(numOfEdge-1).getY()){
					if(cnt > 0){
						
						return true;
					}
				}
			}else if(fourth == 2){
				if(p_x < edgelist.get(numOfEdge-1).getX() && p_y > edgelist.get(numOfEdge-1).getY()){
					if(cnt < 0){
						
						return true;
					}
				}
			}else if(fourth == 3){
				if(p_x < edgelist.get(numOfEdge-1).getX() && p_y < edgelist.get(numOfEdge-1).getY()){
					if(cnt < 0){
						
						return true;
					}
				}
			}else if(fourth == 4){
				if(p_x > edgelist.get(numOfEdge-1).getX() && p_y < edgelist.get(numOfEdge-1).getY()){
					if(cnt > 0){
						
						return true;
					}
				}
			}
		}else if(dir == 2){
			if(fourth == 1){
				if(p_x > edgelist.get(numOfEdge-1).getX() && p_y > edgelist.get(numOfEdge-1).getY()){
					if(cnt > 0){
						
						return true;
					}
				}
			}else if(fourth == 2){
				if(p_x < edgelist.get(numOfEdge-1).getX() && p_y > edgelist.get(numOfEdge-1).getY()){
					if(cnt < 0){
						
						return true;
					}
				}
			}else if(fourth == 3){
				if(p_x < edgelist.get(numOfEdge-1).getX() && p_y < edgelist.get(numOfEdge-1).getY()){
					if(cnt < 0){
						
						return true;
					}
				}
			}else if(fourth == 4){
				if(p_x > edgelist.get(numOfEdge-1).getX() && p_y < edgelist.get(numOfEdge-1).getY()){
					if(cnt > 0){
						
						return true;
					}
				}
			}
		}else if(dir == 3){
			if(fourth == 1){
				if(p_x > edgelist.get(numOfEdge-1).getX() && p_y > edgelist.get(numOfEdge-1).getY()){
					if(cnt < 0){
						
						return true;
					}
				}
			}else if(fourth == 2){
				if(p_x < edgelist.get(numOfEdge-1).getX() && p_y > edgelist.get(numOfEdge-1).getY()){
					if(cnt > 0){
						
						return true;
					}
				}
			}else if(fourth == 3){
				if(p_x < edgelist.get(numOfEdge-1).getX() && p_y < edgelist.get(numOfEdge-1).getY()){
					if(cnt > 0){
						
						return true;
					}
				}
			}else if(fourth == 4){
				if(p_x > edgelist.get(numOfEdge-1).getX() && p_y < edgelist.get(numOfEdge-1).getY()){
					if(cnt < 0){
						
						return true;
					}
				}
			}
		}else if(dir == 4){
			if(fourth == 1){
				if(p_x > edgelist.get(numOfEdge-1).getX() && p_y > edgelist.get(numOfEdge-1).getY()){
					if(cnt > 0){
						
						return true;
					}
				}
			}else if(fourth == 2){
				if(p_x < edgelist.get(numOfEdge-1).getX() && p_y > edgelist.get(numOfEdge-1).getY()){
					if(cnt < 0){
						
						return true;
					}
				}
			}else if(fourth == 3){
				if(p_x < edgelist.get(numOfEdge-1).getX() && p_y < edgelist.get(numOfEdge-1).getY()){
					if(cnt < 0){
						
						return true;
					}
				}
			}else if(fourth == 4){
				if(p_x > edgelist.get(numOfEdge-1).getX() && p_y < edgelist.get(numOfEdge-1).getY()){
					if(cnt > 0){
						
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean EdgeDetect(ArrayList<Edge> edgelist, int p_x, int p_y){
		int d_x = p_x - edgelist.get(numOfEdge-1).getX(); 
		int d_y = p_y - edgelist.get(numOfEdge-1).getY();
		
		int dl = 0;
		
		if(Math.abs(d_x) > Math.abs(d_y)){
			if(d_y != 0){
				dl = d_x / d_y;
			}else{
				dl = 0;
			}
			if(d_y >= 0){
				for(int i = edgelist.get(numOfEdge-1).getY(); i < p_y; i++){
					if(!CanMove(i * dl, i, dir)){
						Log.d("p1", Integer.toString(i * dl) + "," + Integer.toString(i));
						return true;
					}
				}
			}else if(d_y < 0){
				for(int i = p_y; i < edgelist.get(numOfEdge-1).getY(); i++){
					if(!CanMove(i * dl, i, dir)){
						Log.d("p2", Integer.toString(i * dl) + "," + Integer.toString(i));
						return true;
					}
				}
			} 
			
		}else{
			if(d_x != 0){
				dl = d_y / d_x;
			}else{
				dl = 0;
			}
			
			if(d_x >= 0){
				for(int i = edgelist.get(numOfEdge-1).getX(); i < p_x; i++){
					if(!CanMove(i, i * dl, dir)){
						Log.d("p3", Integer.toString(i) + "," + Integer.toString(i * dl));
						return true;
					}
				}
			}else if(d_x < 0){
				for(int i = p_x; i < edgelist.get(numOfEdge-1).getX(); i++){
					if(!CanMove(i, i * dl, dir)){
						Log.d("p4", Integer.toString(i) + "," + Integer.toString(i * dl));
						return true;
					}
				}
			}
		}
		
		
		
		return false;
	}
	
	public boolean CanMove(int p_x, int p_y, int dir){
		for(Rect ob : obstacles){
			if(dir == 1 && ob.contains(p_x, (p_y - puppet.getFps()))){
				SoundManager.getInstance().play(5);
				return false;
			}else if(dir == 2 && ob.contains((p_x - puppet.getFps()), p_y)){
				SoundManager.getInstance().play(5);
				return false;
			}else if(dir == 3 && ob.contains(p_x, (p_y + puppet.getFps()))){
				SoundManager.getInstance().play(5);
				return false;
			}else if(dir == 4 && ob.contains((p_x + puppet.getFps()), p_y)){
				SoundManager.getInstance().play(5);
				return false;
			}
		}
		
		if(dir == 1 && (getDistance(edgelist, p_x, (p_y - puppet.getFps())) > puppet.getRange())){
			SoundManager.getInstance().play(4);
			return false;
		}else if(dir == 2 && (getDistance(edgelist, (p_x - puppet.getFps()), p_y) > puppet.getRange())){
			SoundManager.getInstance().play(4);
			return false;
		}else if(dir == 3 && (getDistance(edgelist, p_x, (p_y + puppet.getFps())) > puppet.getRange())){
			SoundManager.getInstance().play(4);
			return false;
		}else if(dir == 4 && (getDistance(edgelist, (p_x + puppet.getFps()), p_y) > puppet.getRange())){
			SoundManager.getInstance().play(4);
			return false;
		}
		
		return true;
	}
	
	public double getDistance(ArrayList<Edge> edgelist, int p_x, int p_y){
		double sum = 0;
		double tmp = 0;
		
		for(int i = 0; i < numOfEdge; i++){
			if(i == numOfEdge - 1){
				tmp = (Math.pow(edgelist.get(i).getX() - p_x, 2) + Math.pow(edgelist.get(i).getY() - p_y, 2));
			}else{
				tmp = (Math.pow(edgelist.get(i).getX() - edgelist.get(i+1).getX(), 2) + Math.pow(edgelist.get(i).getY() - edgelist.get(i+1).getY(), 2));
			}
			sum += Math.sqrt(tmp);
		}
		
		return sum;
	}
	
	public void Render(Canvas canvas){
		m_backGround.Draw(canvas);
		for(int i = 0; i < 5; i++){
			itemlist[i].Draw(canvas);
		}
		drawLine.Draw(canvas, p_x, p_y, edgelist, numOfEdge);
		puppet.Draw(canvas);
		p_up.Draw(canvas);
		p_down.Draw(canvas);
		p_right.Draw(canvas);
		p_left.Draw(canvas);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(keyCode==KeyEvent.KEYCODE_BACK){
			AppManager.getInstance().getGameView().gameActivity.finish();
		}
		return true;
	}
	public boolean onTouchEvent(MotionEvent event){
		int px = (int) event.getX();
		int py = (int) event.getY();
		Rect r_up = new Rect(128, 940, 372, 1068);
		Rect r_down = new Rect(128, 1308, 372, 1440);
		Rect r_left = new Rect(0, 1068, 128, 1308);
		Rect r_right = new Rect(372, 1068, 500, 1308);
		switch(event.getAction()){
			case MotionEvent.ACTION_UP:
				dir = 0;
				return true;
			case MotionEvent.ACTION_DOWN:
				if(r_up.contains(px,py)){
					dir = 1;
				}
				if(r_left.contains(px,py)){
					dir = 2;
				}
				if(r_down.contains(px,py)){
					dir = 3;
				}
				if(r_right.contains(px,py)){
					dir = 4;
				}
				return true;
		}
		
		return true;
	}
}
