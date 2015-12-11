package cje.escapingpuppet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
	GameActivity gameActivity = (GameActivity)GameActivity.gameActivity;
	private GameViewThread m_thread;
	private IState m_state;
	
	public GameView(Context context){
		super(context);
		getHolder().addCallback(this);
		m_thread = new GameViewThread(getHolder(),this);
		
		AppManager.getInstance().setGameView(this);
		AppManager.getInstance().setResources(getResources());
		this.setClickable(true);
		ChangeGameState(new IntroState());
	}
	
	
	public void onDraw(Canvas canvas){
		if(canvas!=null){
			canvas.drawColor(Color.BLACK);
			m_state.Render(canvas);
		}
	}
	
	public void Update(){
		m_state.Update();
	}

	public void ChangeGameState(IState _state){
		if(m_state!=null){
			m_state.Destroy();
		}
		_state.Init();
		m_state = _state;
	}
	
	public IState getIState(){
		return m_state;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		m_state.onKeyDown(keyCode, event);
		return super.onKeyDown(keyCode, event);
	}
	
	public boolean onTouchEvent(MotionEvent event){
		m_state.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		m_thread.SetRunning(true);
		m_thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		m_thread.SetRunning(false);
		while (retry){
			try{
				m_thread.join();
				retry=false;
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}
