import iic1103.GeneradorAleatorio;
/**
 * Clase que define cada tipo de disparo
 * @author Jurgen
 * @version 0.0.1
 *
 */
public class Shot {
	
	public static void CircularShot(BulletVector v, int centrox, int centroy,double radius,double spd,int cant){
		for(int i=0;i<cant;i++){
			v.PushBack(new Bullet(new Position(centrox+radius*Math.cos(2*i*Math.PI/cant),centroy-radius*Math.sin(2*i*Math.PI/cant)), 1, 1, 2*i*Math.PI/cant, spd));
		}
	}
	public static void CircularShotMutant(BulletVector v, int centrox, int centroy,double radius,double spd,int cant){
		for(int i=0;i<cant;i++){
			if(i%2==0)v.PushBack(new Bullet(new Position(centrox+radius*Math.cos(2*i*Math.PI/cant),centroy-radius*Math.sin(2*i*Math.PI/cant)), 1, 5, 2*i*Math.PI/cant, spd));
			else v.PushBack(new Bullet(new Position(centrox+radius*Math.cos(2*i*Math.PI/cant),centroy-radius*Math.sin(2*i*Math.PI/cant)), 1, 6, 2*i*Math.PI/cant, spd));
		}
	}
	public static void CircularShotAngled(BulletVector v, int centrox, int centroy,double radius,double spd,int cant,double angle){
		for(int i=0;i<cant;i++){
			v.PushBack(new Bullet(new Position(centrox+radius*Math.cos(2*i*Math.PI/cant),centroy-radius*Math.sin(2*i*Math.PI/cant)), 1, 1, angle, spd));
		}
	}
	public static void RandomShot(BulletVector v,double spd,int cant){
		GeneradorAleatorio rnd = new GeneradorAleatorio();
		for(int i=0;i<cant;i++){
			v.PushBack(new Bullet(new Position(rnd.entero(0, (int)Config.maxX-1),rnd.entero(0, (int)Config.maxY-1)), rnd.entero(1, 14), 1, rnd.real(0, 2*Math.PI), spd));
		}
	}
	public static void SpiralShot(BulletVector v,double spd,int cant,int color,double initradius,double centrox,double centroy,double rotation){
		for(int i=0;i<cant;i++){
			v.PushBack(new Bullet(new Position(centrox+initradius*Math.cos(2*i*Math.PI/cant),centroy-initradius*Math.sin(2*i*Math.PI/cant)),color,3,2*i*Math.PI/cant,spd));
			v.at(v.getSize()-1).setRotation(rotation);
		}
	}
	public static void DreamcastShot(BulletVector v, double spd, int cant,double radius, double rotation, Entity ent){
		for(int i=0;i<cant;i++){
			v.PushBack(new Bullet(new Position(ent.pos),1,4,2*i*Math.PI/cant,spd));
			v.at(v.getSize()-1).setRotation(rotation);
		}
	}
	public static void DNAShot(BulletVector v,double spd,double angle,double x,double y){
		v.PushBack(new Bullet(new Position(x,y),2,7,angle,spd));
		v.at(v.getSize()-1).setRotation(Math.PI/6);
		v.PushBack(new Bullet(new Position(x,y),3,7,angle,spd));
		v.at(v.getSize()-1).setRotation(-1.0*Math.PI/6);
	}
	public static void RandomBounceShot(BulletVector v,double spd,int cant){
		GeneradorAleatorio rnd = new GeneradorAleatorio();
		for(int i=0;i<cant;i++){
			v.PushBack(new Bullet(new Position(rnd.entero(0, (int)Config.maxX-1),rnd.entero(0, (int)Config.maxY-1)), 3, 8, rnd.real(0, 2*Math.PI), spd));
		}
	}

}
