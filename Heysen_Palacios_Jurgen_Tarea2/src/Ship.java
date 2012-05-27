/**
 * Clase que modela la nave del juego
 * Extensión de Entity
 * @author Jurgen
 * @version 0.0.1
 *
 */
public class Ship extends Entity{
	private boolean blink; //Estado de parpadeo de la nave
	private int blinkedFrames; //Número de cuadros que se ha parpadeado
	
	/**
	 * Constructor de la nave. La crea en la ubicación determinada por el enunciado.
	 */
	public Ship(){
		pos=new Position(Config.ShipInitX,Config.ShipInitY);
		setBlink(false);
		HitboxRadius=Config.ShipHitboxRadius;
	}
	
	/**
	 * establece la ubicación de la nave
	 * @param p Clase Posición que contiene la nueva ubicación
	 */
	//Realmente no era necesario el Override, pero ya tenía esto escrito antes de hacer Entity :p
	@Override
	public void setPosition(Position p){
		pos=p;
	}
	/**
	 * Permite establecer la ubicación por coordenadas
	 * @param x Coordenada x
	 * @param y Coordenada y
	 * Este método comprueba que la nave no se salga de sus límites
	 */
	public void setPosition(double x,double y){
		if(x>=16 && x<=(Config.maxX-16)) pos.setX(x);
		if(y>=23 && y<=(Config.maxY-12)) pos.setY(y);
	}
	/**
	 * permite obtener un objeto Position con la posisicón de la nave, exactamente igual a getPosition de Entity.
	 * @return Objeto Position con coordenadas de la nave
	 */
	public Position getPositionPos(){
		return new Position(pos);
	}
	/**
	 * Permite obtener la coordenada Y de la nave
	 * @return Entero que representa la coordenada Y de la nave
	 */
	public double getPositionY(){
		return pos.getY();
	}
	/**
	 * Permite obtener la coordenada X de la nave
	 * @return Entero que representa la coordenada x de la nave
	 */
	public double getPositionX(){
		return pos.getX();
	}
	/**
	 * Método que permite saber si la nave debe parpadear o no
	 * @return True si debe parpadear, False de lo contrario
	 */
	public boolean getBlink() {
		return blink;
	}
	/**
	 * Método que permite establecer el parpadeo de la nave
	 * @param b True para que parpadee, false para que deje de hacerlo
	 */
	public void setBlink(boolean b) {
		this.blink = b;
	}
	/**
	 * Método que nos indica cuantos cuadros ha parpadeado la nave
	 * @return Entero con el número de cuadros en que la nave ha parpadeado
	 */
	public int getBlinkedFrames() {
		return blinkedFrames;
	}
	/**
	 * Método que nos permite establecer el parámetro de cuadros parpadeados
	 * @param blinkedFrames Nuevo valor de cuadros parpadeados.
	 */
	public void setBlinkedFrames(int blinkedFrames) {
		this.blinkedFrames = blinkedFrames;
	}
	/**
	 * Método que hace que la nave parpadee y la devuelve a su posición inicial
	 */
	public void resetAndBlink(){
		setBlink(true);
		this.pos.setX(Config.ShipInitX);
		this.pos.setY(Config.ShipInitY);
	}

}
