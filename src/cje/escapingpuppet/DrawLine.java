package cje.escapingpuppet;

import java.util.ArrayList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class DrawLine {
	private Paint paint;
	
	public DrawLine(){
		paint = new Paint();
		paint.setTypeface(Typeface.MONOSPACE);
	    paint.setStrokeWidth(20);
	    paint.setColor(Color.rgb(255, 127, 39));
	}
	
	public void Draw(Canvas canvas, int p_x, int p_y, ArrayList<Edge> edgelist, int NoE) {
		for(int i = 0; i < NoE; i++){
			if(i + 1 == NoE){
				canvas.drawLine(1350, 650, edgelist.get(i).getX()-p_x+1250, edgelist.get(i).getY()-p_y+570, paint);
			}else{
				canvas.drawLine(edgelist.get(i).getX()-p_x+1250, edgelist.get(i).getY()-p_y+570,edgelist.get(i+1).getX()-p_x+1250, edgelist.get(i+1).getY()-p_y+570, paint);
			}
		}
	}
	
	public void SetPaint(int color){
		paint.setColor(color);
	}
}
