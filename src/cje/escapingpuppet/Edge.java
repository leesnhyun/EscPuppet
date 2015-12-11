package cje.escapingpuppet;

public class Edge {
	private int x;
	private int y;
	private int fourth;
	private int dir;

	public Edge(int x, int y, int dir){
		this.x = x;
		this.y = y;
		if(x >= 0){
			if(y >= 0){
				fourth = 1;
			}else{
				fourth = 4;
			}
		}else if(x < 0){
			if(y >= 0){
				fourth = 2;
			}else{
				fourth = 3;
			}
		}
		
		this.dir = dir;
	}

	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void setFourth(int fourth){
		this.fourth = fourth;
	}
	
	public void setDir(int dir){
		this.dir = dir;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public int getFourth(){
		return this.fourth;
	}
	
	public int getDir(){
		return this.dir;
	}
}
