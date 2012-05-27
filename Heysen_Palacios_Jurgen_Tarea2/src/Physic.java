/**
 * Clase que contiene métodos de detección de coliciones y otros eventos físicos
 * @author Jurgen
 * @version 0.5
 * Código de colisiones y Graze reescrito, añadido código de daño (0.5)
 */
public class Physic {
	
	/**
	 * Método estático que perimite verificar si dos entidades han colisonado (sus hitbox)
	 * @param a 
	 * @param b
	 * @param radius Radio de colisión
	 * @return true si hay colisión, false si no
	 */
	public static boolean collision(Entity a,Entity b){
		if(Config.GodMode) return false; //Si estamos en modo dios, nunca chocamos con nada
		if(b.getHitboxRadius()==0) return false; //Si el radio de hitbox es 0, no se puede chocar con el objeto
		Position p1=a.getPosition(); //guardamos el objeto Position para ahorrar un poco de CPU y RAM
		Position p2=b.getPosition();
		double op1=Math.sqrt(Math.pow(p1.getX()-p2.getX(),2)+Math.pow(p1.getY()-p2.getY(), 2)); //Distancia entre puntos
		if(op1<=Math.max(a.getHitboxRadius(),b.getHitboxRadius())){ //si la distancia es menor al mayor de los radios, hay choque
			return true; //Si está en el radio, será impacto
		}
		return false;
	}
	/**
	 * Método estático que permite verificar si ocurre roce entre dos entidades
	 * @param radius Radio dentro del que se considerará roce
	 * @param a Entidad contra la que se verifica
	 * @param b Entitdad a verificar
	 * @return true si se rozan, false si no.
	 */
	public static boolean graze(double radius,Entity a, Entity b){
		Position p1=a.getPosition(); //guardamos el objeto Position para ahorrar un poco de CPU y RAM
		Position p2=b.getPosition();
		if(b.isGrazed()) return false; //Si ya se había contado graze, no contar nuevamente
		if(a.getHitboxRadius()==0 || b.getHitboxRadius()==0) return false; //Si hay hitbox 0, es que no se puede chocar con el objeto
		if(collision(a,b)) return false; //Si chocan, no es graze
		double op1=Math.sqrt(Math.pow(p1.getX()-p2.getX(),2)+Math.pow(p1.getY()-p2.getY(), 2)); //Distancia entre puntos
		if(op1<=radius-(b.getHitboxRadius())){ //Contamos la distancia entre los bordes de los hitbox
			b.setGrazed(true); //lo marcamos como cotabilizado
			return true;
		}
		return false;
	}
	/**
	 * Método que permite calcular cuanto daño recibe una entidad por un ataque de otra
	 * @param a Entidad que ataca
	 * @param b Entidad que recibe daño
	 * @return Double con la cantidad de daño realizado
	 */
	public static double Dmg(Entity a,Entity b,double temperature){
		if(temperature<=0.0) temperature = 1.0; 
		if(a.getPosition().x<=b.getPosition().x+b.getHitboxRadius() && a.getPosition().x>=b.getPosition().x-b.getHitboxRadius()&&a.getPosition().y>=b.getPosition().y){ //Si nuestra posición está dentro de su hitbox
			return ((Config.LaserNormalDmgPerSecond/Config.TargetFramerate)*(1.0+(temperature/100.0))); //Daño que hará
		}
		return 0.0;
	}
	/**
	 * Método que permite conocer el ángulo formado por dos entidades cualquiera con respecto a la horizontal
	 * @param a Entidad movil
	 * @param b Entidad centro
	 * @return Valor en radianes del angulo entre ellas
	 */
	public static double getAngle(Entity a,Entity b){
		/*if(a.getPosition().x==b.getPosition().x && a.getPosition().y>b.getPosition().y) return 3*Math.PI/4;
		if(a.getPosition().x==b.getPosition().x && a.getPosition().y<b.getPosition().y) return Math.PI/4;
		if(a.getPosition().y==b.getPosition().y && a.getPosition().x>b.getPosition().x) return 0.0;
		if(a.getPosition().y==b.getPosition().y && a.getPosition().x<b.getPosition().x) return Math.PI;
		if(a.getPosition().x==b.getPosition().x && a.getPosition().y==b.getPosition().y) return 0.0; //Uno nunca sabe
		double theta = Math.atan((a.getPosition().y-b.getPosition().y)/(a.getPosition().x-b.getPosition().x));
		if(a.getPosition().y>b.getPosition().y) theta*=-1.0;
		return theta;*/
		return getAngle(a.pos.x,a.pos.y,b.pos.x,b.pos.y);
	}
	/**
	 * Método que permite conocer el ángulo entre puntos del espacio
	 * @param ax coordenada x del punto movil
	 * @param ay Coordenada y del punto movil
	 * @param bx coordenada x del punto centro
	 * @param by Coordenada y del punto centro
	 * @return Angulo entre los puntos, en radianes
	 */
	public static double getAngle(double ax,double ay,double bx,double by){
		//a define el movil,, b define el centro
		//Primero los casos triviales
		if(ax==bx && ay>by) return 3*Math.PI/4;
		if(ax==bx && ay<by) return Math.PI/4;
		if(ay==by && ax>bx) return Math.PI;
		if(ay==by && ax<bx) return 0.0;
		if(ax==bx && ay==by) return 0.0; //Uno nunca sabe
		
		double theta = -1.0*Math.atan((ay-by)/(ax-bx)); //Si no es tribial, calculamos el angulo.
		if(ax<bx) theta+=Math.PI; //Si está en los cuadrantes II o III, le sumamos un factor de corrección
		
		return theta;
	}

}
