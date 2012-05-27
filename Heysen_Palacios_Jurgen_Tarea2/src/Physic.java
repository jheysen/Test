/**
 * Clase que contiene m�todos de detecci�n de coliciones y otros eventos f�sicos
 * @author Jurgen
 * @version 0.5
 * C�digo de colisiones y Graze reescrito, a�adido c�digo de da�o (0.5)
 */
public class Physic {
	
	/**
	 * M�todo est�tico que perimite verificar si dos entidades han colisonado (sus hitbox)
	 * @param a 
	 * @param b
	 * @param radius Radio de colisi�n
	 * @return true si hay colisi�n, false si no
	 */
	public static boolean collision(Entity a,Entity b){
		if(Config.GodMode) return false; //Si estamos en modo dios, nunca chocamos con nada
		if(b.getHitboxRadius()==0) return false; //Si el radio de hitbox es 0, no se puede chocar con el objeto
		Position p1=a.getPosition(); //guardamos el objeto Position para ahorrar un poco de CPU y RAM
		Position p2=b.getPosition();
		double op1=Math.sqrt(Math.pow(p1.getX()-p2.getX(),2)+Math.pow(p1.getY()-p2.getY(), 2)); //Distancia entre puntos
		if(op1<=Math.max(a.getHitboxRadius(),b.getHitboxRadius())){ //si la distancia es menor al mayor de los radios, hay choque
			return true; //Si est� en el radio, ser� impacto
		}
		return false;
	}
	/**
	 * M�todo est�tico que permite verificar si ocurre roce entre dos entidades
	 * @param radius Radio dentro del que se considerar� roce
	 * @param a Entidad contra la que se verifica
	 * @param b Entitdad a verificar
	 * @return true si se rozan, false si no.
	 */
	public static boolean graze(double radius,Entity a, Entity b){
		Position p1=a.getPosition(); //guardamos el objeto Position para ahorrar un poco de CPU y RAM
		Position p2=b.getPosition();
		if(b.isGrazed()) return false; //Si ya se hab�a contado graze, no contar nuevamente
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
	 * M�todo que permite calcular cuanto da�o recibe una entidad por un ataque de otra
	 * @param a Entidad que ataca
	 * @param b Entidad que recibe da�o
	 * @return Double con la cantidad de da�o realizado
	 */
	public static double Dmg(Entity a,Entity b,double temperature){
		if(temperature<=0.0) temperature = 1.0; 
		if(a.getPosition().x<=b.getPosition().x+b.getHitboxRadius() && a.getPosition().x>=b.getPosition().x-b.getHitboxRadius()&&a.getPosition().y>=b.getPosition().y){ //Si nuestra posici�n est� dentro de su hitbox
			return ((Config.LaserNormalDmgPerSecond/Config.TargetFramerate)*(1.0+(temperature/100.0))); //Da�o que har�
		}
		return 0.0;
	}
	/**
	 * M�todo que permite conocer el �ngulo formado por dos entidades cualquiera con respecto a la horizontal
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
	 * M�todo que permite conocer el �ngulo entre puntos del espacio
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
		if(ax<bx) theta+=Math.PI; //Si est� en los cuadrantes II o III, le sumamos un factor de correcci�n
		
		return theta;
	}

}
