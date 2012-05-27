/**
 * Clase que define a cada bala en particular
 * es una extensi�n de Entity
 * @author Jurgen
 * @version 0.0.1
 *
 */
public class Bullet extends Entity{
	private int color; //realmente se condecir� con el sprite a utilizar, de 1 a 24
	private int parametric; //Modo que rige a esta bala particular
	private int t; //tiempo que lleva esta bala, para sus ecuaciones param�tricas
	private double rad; //angulo de la bala, en radianes
	private double vel; //velocidad de la bala
	private boolean alive; //indica si la bala puede ser desechada
	private boolean additionalaux; //variable de ayuda
	private double rotation; //Angulo de rotaci�n de la bala
	private Ship nave; //Una referencia a la nave, para las balas que cambian de direcci�n
	private Position init; //Referencia a la posici�n inicial
	
	
	/**
	 * Constructor especial de Balas
	 * @param p Posici�n inicial de la bala
	 * @param c Color de la bala
	 * @param pa Modo param�trico de la bala
	 */
	public Bullet(Position p, int c, int pa,double r, double vel){
		pos=p;
		if(c<25 && c>0){
			color=c; //si damos una bala v�lida, la guarda, si no asigna una por defecto.
			if(r>=1 && r<=16) this.HitboxRadius = Config.SmallBulletHitbox; //Si es peque�a le damos un hitbox de bala peque�a, si no, de bala grande
			else this.HitboxRadius = Config.BigBulletHitbox;
		}
		else{
			color=1;
			this.HitboxRadius = Config.SmallBulletHitbox;
		}
		parametric=pa;
		t=0;
		this.vel=vel;
		rad=r;
		alive=true;
		additionalaux=false;
		init=new Position(pos);
	}
	/**
	 * Permite saber el sprite asignado a la bala
	 * @return Id del sprite de la bala
	 */
	public int getColor(){
		return color;
	}
	/**
	 * Permite cambiar el sprite de la bala
	 * @param c Id del sprite, debe ser de una bala, es decir, entre 1 y 24
	 */
	public void setColor(int c){
		if(c<25 && c>0) color=c;
	}
	/**
	 * Permite conocer el modo param�trico de esta bala en particular
	 * @return Id del modo param�trico
	 */
	public int getParametricMode(){
		return parametric;
	}
	/**
	 * Hace que la bala actualize su posici�n y otros comportamientos en funci�n de su modo param�trico
	 */
	public void Update(){
		//TODO: Distinttas ecuaciones param�tricas
		switch(parametric){
		case 1: //trayectoria recta
			this.pos.x+=vel*Math.cos(rad);
			this.pos.y-=vel*Math.sin(rad);
			break;
		case 2: //Trayectoria recta con vuelta en medio de camino
			this.pos.x+=vel*Math.cos(rad);
			this.pos.y-=vel*Math.sin(rad);
			if(t>=45 && !additionalaux){
				rad*=-1;
				additionalaux=true;
			}
			break;
		case 3: //Espiral
			this.pos.x+=vel*Math.cos(rad);
			this.pos.y-=vel*Math.sin(rad);
			rad+=rotation;
			break;
		case 4: //espiral?
			this.pos.x+=rotation*vel*Math.exp(rad);
			this.pos.y-=rotation*vel*Math.exp(rad);
			rad+=rotation;
		case 5: //Trayectoria recta por 150 y luego cambio a recta con direccion a la nave
			this.pos.x+=vel*Math.cos(rad);
			this.pos.y-=vel*Math.sin(rad);
			if(Math.sqrt(Math.pow(pos.x-init.x,2)+Math.pow(pos.y-init.y, 2))>=150){
				rad=Physic.getAngle(this, nave);
				parametric=1;
			}
			break;
		case 6: //Trayectoria recta por 150 y luego gira
			rotation=2*Math.PI/100;
			this.pos.x+=vel*Math.cos(rad);
			this.pos.y-=vel*Math.sin(rad);
			if(Math.sqrt(Math.pow(pos.x-init.x,2)+Math.pow(pos.y-init.y, 2))>=150){
				parametric=3;
			}
			break;
		case 7: //DNA
			this.pos.x+=vel*Math.cos(rad);
			this.pos.y-=vel*Math.sin(rad+t*rotation);
			break;
		case 8: //Bala que rebota una vez
			this.pos.x+=vel*Math.cos(rad);
			this.pos.y-=vel*Math.sin(rad);
			if(this.pos.x<=Config.minX+1 || this.pos.x>=Config.maxX-1 || this.pos.y<=Config.minY+1 || this.pos.y>=Config.maxY-1){
				this.color=5;
				this.parametric=1;
				this.rad=2*(Math.PI-this.rad)*-1.0;
			}
			break;
		case 9: //Bala que rebota dos veces
			this.pos.x+=vel*Math.cos(rad);
			this.pos.y-=vel*Math.sin(rad);
			if(this.pos.x<=Config.minX+1 || this.pos.x>=Config.maxX-1 || this.pos.y<=Config.minY+1 || this.pos.y>=Config.maxY-1){
				this.color=5;
				this.parametric=8;
				this.rad=2*(Math.PI-this.rad)*-1.0;
			}
			break;
		default:
			break;
		}
		t++; //avanzamos el tiempo para esta bala
		//Ahora verificamos si la bala se ha salido de la pantalla o no
		if((int)Math.ceil(this.pos.x)<=(int)Math.ceil(Config.minX) || (int)Math.ceil(this.pos.x)>=(int)Math.ceil(Config.maxX) || (int)Math.ceil(this.pos.y)<=(int)Math.ceil(Config.minY) || (int)Math.ceil(this.pos.y)>=(int)Math.ceil(Config.maxY)) alive=false; //si est� fuera de m�rgenes, bala muerta
	}
	public void setRad(double rad) {
		this.rad = rad;
	}
	public void setVel(double vel) {
		this.vel = vel;
	}
	/**
	 * M�todo que permite saber si una bala debe ser desechada o no por salirse de la pantalla
	 * @return True si est� fuera de la pantalla, false si no
	 */
	public boolean isAlive(){
		return alive;
	}
	/**
	 * M�todo que permite obtener la rotaci�n de la direcci�n de la bala por frame
	 * @return double con el valor que cambia la direcci�n por frame
	 */
	public double getRotation() {
		return rotation;
	}
	/**
	 * M�todo que permite establecer rotaci�n para una bala
	 * @param rotation �ngulo que variar� su direcci�n en el tiempo
	 */
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
	/**
	 * M�todo que permite establecer la referencia a la nave
	 * @param nave referencia a la nave
	 */
	public void setNave(Ship nave) {
		this.nave = nave;
	}

}
