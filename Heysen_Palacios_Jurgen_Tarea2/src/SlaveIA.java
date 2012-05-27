
public class SlaveIA extends BaseIA{
	private Entity boss;
	private BulletVector balas;
	private int phase;
	private Slave ent;
	private int[] movement;
	private int[] tipo1={1,2,3,4,5,6};
	private int[] tipo2={5,4,6,2,1,3};
	private int currentpos;
	private double velx;
	private double vely;
	private Entity nave;
	private int currframes;
	private int t;
	
	public SlaveIA(Slave e,Entity b,Entity n,int tipo){
		this.ent=e;
		boss=b;
		nave=n;
		if(tipo==1) movement = tipo1;
		else movement = tipo2;
		currentpos=movement[0];
		currframes=0;
		t=0;
	}
	
	public void UpdateBullets(BulletVector v){
		this.balas=v;
	}
	
	public void setPhase(int p){
		int oldphase=phase;
		phase=p;
		if(phase!=oldphase){
			ent.pos.x=boss.pos.x;
			ent.pos.y=boss.pos.y;
		}
	}
	
	@Override
	public int IAaction(){
		t++; //Un pequeño contador de ayuda
		//Primero movemos a nuestros esclavos
		boolean onPosition=false;
		int oldpos=currentpos;
		switch(movement[currentpos]){
		case 1:
			if(ent.pos.x > boss.pos.x+30) ent.pos.x-=velx;
			if(ent.pos.x < boss.pos.x+30) ent.pos.x+=velx;
			if(ent.pos.y > boss.pos.y) ent.pos.y+=vely;
			if(ent.pos.y < boss.pos.y) ent.pos.y-=vely;
			if(ent.pos.x==boss.pos.x+30 && ent.pos.y==boss.pos.y){
				currframes++;
				if(currframes>=60){
					currentpos++;
					currframes=0;
				}
				onPosition=true;
			}
			break;
		case 2:
			if(ent.pos.y < boss.pos.y+40) ent.pos.y+=vely;
			if(ent.pos.y > boss.pos.y+40) ent.pos.y-=vely;
			if(ent.pos.x > boss.pos.x-50) ent.pos.x-=velx;
			if(ent.pos.x < boss.pos.x-50) ent.pos.x+=velx;
			if(ent.pos.y==boss.pos.y+40 && ent.pos.x==boss.pos.x-50){
				currframes++;
				if(currframes>=60){
					currentpos++;
					currframes=0;
				}
				onPosition=true;
			}
			break;
		case 3:
			if(ent.pos.y < boss.pos.y-30) ent.pos.y+=vely;
			if(ent.pos.y > boss.pos.y-30) ent.pos.y-=vely;
			if(ent.pos.x > boss.pos.x-40) ent.pos.x-=velx;
			if(ent.pos.x < boss.pos.x-40) ent.pos.x+=velx;
			if(ent.pos.y==boss.pos.y-30 && ent.pos.x==boss.pos.x-40){
				currframes++;
				if(currframes>=60){
					currentpos++;
					currframes=0;
				}
				onPosition=true;
				if(currentpos==movement.length) currentpos=0;
			}
			break;
		case 4:
			if(ent.pos.y < boss.pos.y+40) ent.pos.y+=vely;
			if(ent.pos.y > boss.pos.y+40) ent.pos.y-=vely;
			if(ent.pos.x > boss.pos.x+50) ent.pos.x-=velx;
			if(ent.pos.x < boss.pos.x+50) ent.pos.x+=velx;
			if(ent.pos.y==boss.pos.y+40 && ent.pos.x==boss.pos.x+50){
				currframes++;
				if(currframes>=60){
					currentpos++;
					currframes=0;
				}
				onPosition=true;
			}
			break;
		case 5:
			if(ent.pos.x > boss.pos.x-30) ent.pos.x-=velx;
			if(ent.pos.x < boss.pos.x-30) ent.pos.x+=velx;
			if(ent.pos.y > boss.pos.y) ent.pos.y+=vely;
			if(ent.pos.y < boss.pos.y) ent.pos.y-=vely;
			if(ent.pos.x==boss.pos.x-30 && ent.pos.y==boss.pos.y){
				currframes++;
				if(currframes>=60){
					currentpos++;
					currframes=0;
				}
				onPosition=true;
			}
			break;
		case 6:
			if(ent.pos.y < boss.pos.y-30) ent.pos.y+=vely;
			if(ent.pos.y > boss.pos.y-30) ent.pos.y-=vely;
			if(ent.pos.x > boss.pos.x+40) ent.pos.x-=velx;
			if(ent.pos.x < boss.pos.x+40) ent.pos.x+=velx;
			if(ent.pos.y==boss.pos.y-30 && ent.pos.x==boss.pos.x+40){
				currframes++;
				if(currframes>=60){
					currentpos++;
					currframes=0;
				}
				onPosition=true;
				if(currentpos==movement.length) currentpos=0;
			}
			break;
		}
		//Si hay que moverse, calculamos las velocidades
		if(oldpos==currentpos){
			switch(movement[currentpos]){
			case 1:
				//velx= (Math.abs(ent.pos.x-(boss.pos.x+30))/60);
				//vely= (Math.abs(ent.pos.y-boss.pos.y)/60);
				velx=1.0;
				vely=1.0;
				break;
			case 2:
				velx= (Math.abs(ent.pos.x-(boss.pos.x-50))/60);
				vely= (Math.abs(ent.pos.y-(boss.pos.y+40))/60);
				velx=1.0;
				vely=1.0;
				break;
			case 3:
				velx= (Math.abs(ent.pos.x-(boss.pos.x-40))/60);
				vely= (Math.abs(ent.pos.y-(boss.pos.y-30))/60);
				velx=1.0;
				vely=1.0;
				break;
			case 4:
				velx= (Math.abs(ent.pos.x-(boss.pos.x+50))/60);
				vely= (Math.abs(ent.pos.y-(boss.pos.y+40))/60);
				velx=1.0;
				vely=1.0;
				break;
			case 5:
				velx= (Math.abs(ent.pos.x-(boss.pos.x-30))/60);
				vely= (Math.abs(ent.pos.y-boss.pos.y)/60);
				velx=1.0;
				vely=1.0;
				break;
			case 6:
				velx= (Math.abs(ent.pos.x-(boss.pos.x+40))/60);
				vely= (Math.abs(ent.pos.y-boss.pos.y-30)/60);
				velx=1.0;
				vely=1.0;
				break;
			}
		}
		//Luego hacemos que ataquen
		switch(phase){
		case 15:
			if(!onPosition && t%10==0)balas.PushBack(new Bullet(new Position(ent.pos.x,ent.pos.y),3,1,Physic.getAngle(nave, ent),2.0)); //Le ponemos una limitante para que sea posible esquivar estas balas
			ent.setSprite(27);
			break;
		case 16:
			if(!onPosition && t%3==0) Shot.DNAShot(balas, 2.0, Physic.getAngle(nave, ent),ent.pos.x,ent.pos.y);
			ent.setSprite(27);
			break;
		default:
			//ent.setSprite(0);
			break;
		}
		
		return 0;
	}

}
