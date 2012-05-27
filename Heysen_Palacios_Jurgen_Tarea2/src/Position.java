/**
 * Clase que representa una posición en el plano
 * @author Jurgen
 * @version 0.0.1
 *
 */
public class Position {
	//TODO borrar getters
	double x; //coordenada x
	double y; //coordenada y
	/**
	 * Constructor vacío, inicializa al 0,0
	 */
	public Position(){
		this.x=0;
		this.y=0;		
	}
	/**
	 * Constructor con coordenadas enteras
	 * @param x Coordenada X
	 * @param y Coordenada Y
	 */
	public Position(double x,double y){
		this.x=x;
		this.y=y;
	}
	/**
	 * Constructor copia
	 * @param p Objeto Position a copiar
	 */
	public Position(Position p){
		this.x=p.getX();
		this.y=p.getY();
	}
	/**
	 * Permite obtener la coordenada X
	 * @return Coordenada X del objeto
	 */
	public double getX() {
		return x;
	}
	/**
	 * Permite establecer la coordenada X
	 * @param x Nuevo valor para la Coordenada X
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * Permite obtener la Coordenada Y
	 * @return Coordenada Y del Objeto
	 */
	public double getY() {
		return y;
	}
	/**
	 * Permite cambiar la coordenada Y
	 * @param y Nueva coordenada Y del Objeto
	 */
	public void setY(double y) {
		this.y = y;
	}
	

}
