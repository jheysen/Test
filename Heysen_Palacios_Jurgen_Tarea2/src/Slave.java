/**
 * Clase que modela los subditos del Boss
 * @author Jurgen
 * @version 0.0.1
 * No hacen mucho como entidad, pero su IA es de cuidado
 *
 */
public class Slave extends Entity{
	
	private int sprite;

	public Slave(Entity e){
		this.pos.x=e.pos.x;
		this.pos.y=e.pos.y;
		this.HitboxRadius=Config.SlaveHitboxRadius;
		this.setSprite(0); //Inicialmente, los esclavos son invisibles
	}

	public int getSprite() {
		return sprite;
	}

	public void setSprite(int sprite) {
		this.sprite = sprite;
	}
	
}
