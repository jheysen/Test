/**
 * Clase que representa al jefe del juego 
 * Extensión de Entity
 * @author Jurgen
 * @version 0.0.1
 *
 */
public class Boss extends Entity{
	private double hp; //HP actual del jefe
	private double maxHPphase; //HP máximo actual de la fase
	private int bars; //Barras de vida que le quedan
	
	public Boss(){
		this.hp=1.0;
		this.setBars(5);
		this.pos=new Position(288,150);
		HitboxRadius=Config.BossHitboxRadius;
	}
	/**
	 * Retorna el HP de la fase actual del Boss
	 * @return Hp de la fase actual, en puntos reales
	 */
	public double getHP(){
		return hp;
	}
	/**
	 * Permite conocer el Hp del Boss, de forma modulada entre 0 y 1
	 * @return Real entre 0 y 1 que representa el % de HP restante
	 */
	public double getModuledHP(){
		return (double)(hp/maxHPphase);
	}
	/**
	 * Permite modificar el HP actual de la fase
	 * @param a Nuevo HP actual de la fase, cuidar que debe estar entre 0 y 1
	 */
	public void setHP(double a){
		hp=a;
	}
	/**
	 * Permite establecer el Hp máximo de esta fase, se paso establece el HP en dicho valor
	 * @param m HP de la fase
	 */
	public void setMaxPhaseHP(double m){
		maxHPphase=m;
		hp=m;
	}
	/**
	 * Permite conocer cuantas Barras de vida aún quedan
	 * @return Número de barras de vida, como entero
	 */
	public int getBars() {
		return bars/2;
	}
	/**
	 * Permite establecer las barras de vida restantes
	 * @param bars Número de barras de vida
	 */
	public void setBars(int bars) {
		this.bars = bars;
	}

}
