package cje.escapingpuppet;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameViewThread extends Thread{
	private SurfaceHolder m_surfaceHolder;
	private GameView m_gameview;
	
	private boolean m_run = false;
	
	public GameViewThread(SurfaceHolder surfaceHolder, GameView gameView) {
		m_surfaceHolder = surfaceHolder;
		m_gameview = gameView;
	}
	
	public void SetRunning(boolean run){
		this.m_run = run;
	}
	
	public void run(){
		Canvas canvas;
		while(m_run){
			canvas = null;
			try{
				m_gameview.Update();
				
				canvas = m_surfaceHolder.lockCanvas(null);
				synchronized (m_surfaceHolder) {
					m_gameview.onDraw(canvas);
				}
			}finally{
				if(canvas != null){
					m_surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}
	

}
