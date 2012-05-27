/**
 * Clase general para entidades
 * De acá se desprenden las balas, el jefe y la nave.
 * @author Jurgen
 * @version 0.0.1
 *
 */
public class Entity {
	/**
	 * Toda entidad tiene una posición en el mapa
	 */
	protected Position pos; //Visibilidad protected por sugerencia de eclipse.
	/**
	 * Toda entidad puede necesitar diferenciarse de las demás por más de solo su posición
	 *
	 */
	private int id;
	/**
	 * Además, los graze se deben contar solo una vez
	 */
	private boolean grazed;
	protected int HitboxRadius; //Radio de la hitbox
	
	/**
	 * Costructor general para entidades
	 * Asumimos que parte en 0,0
	 */
	public Entity(){
		pos=new Position(0,0);
		setGrazed(false);
		HitboxRadius=1;
	}
	/**
	 * Es interezante para toda entidad conocer su posición en un momento x
	 * @return Un objeto Position con su posición, cabe descatar que es una copia y no una referencia no que se obtiene.
	 */
	public Position getPosition(){
		return new Position(pos);
	}
	/**
	 * Es interesante poder modificar la ubicación de una entidad a voluntad
	 * @param p
	 * Posición que se le asignará a la entidad
	 */
	public void setPosition(Position p){
		pos=new Position(p);
	}
	/**
	 * Establecemos la posición, esta vez por coordenadas
	 * @param x Coordenada x que se le asigna a la entidad
	 * @param y Coordenada y que se le asigna a la entidad
	 * Importante: Este método no comprueba límites de posición
	 */
	public void setPosition(int x,int y){
		pos.setX(x);
		pos.setY(y);
	}
	/**
	 * Obtenemos la id de esta entidad particular
	 * @return Id de la entidad
	 */
	public int getId() {
		return id;
	}
	/**
	 * Establecemos la id que tendrá esta entidad
	 * @param id
	 * ID asignada a la entidad
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Permite verificar que ya se le hizo graze a este Entity
	 * @return true si ya se contó, false si no
	 */
	public boolean isGrazed() {
		return grazed;
	}
	/**
	 * Permite cambiar el estado de graze
	 * @param grazed estado a guardar
	 */
	public void setGrazed(boolean grazed) {
		this.grazed = grazed;
	}
	/**
	 * Método que permite conocer el tamaño de la hitbox de una entidad específica
	 * @return Entero con el radio de su hitbox
	 */
	public int getHitboxRadius(){
		return HitboxRadius;
	}

}
