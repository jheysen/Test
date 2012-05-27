import iic1103T2.*;
/**
 * CLase que representa los distintos parámetros del jugador, como vidas restantes, puntaje, etc.
 * @author Jurgen
 * @version 0.0.1
 *
 */
public class Player {
	private int vidas;
	private int score;
	private int graze;
	
	//Constructor de la clase, carga los valores por defecto desde la configuración
	public Player(){
		vidas = Config.numInitLifes;
		score = Config.InitScore;
		graze = Config.InitGraze;
	}
	
	//getters
	public int getLife(){
		return vidas;
	}
	public int getScore(){
		return score;
	}
	public int getGraze(){
		return graze;
	}
	
	//setters
	//Las vidas nos interesa establecerlas
	public void setLife(int v){
		vidas=v;
	}
	//Pero también nos interesa quitar de a una
	public void setLifeMinus(){
		vidas--;
	}
	//En el caso del puntaje, solo nos interesa agregar
	public void AddScore(int a){
		score+=a;
	}
	//Lo mismo para el graze
	public void AddGraze(int a){
		graze+=a;
	}
	
	//Si bien tiene más relación con la nave, queda más cómodo implementar esto aquí
	/**
	 * Método que realiza operaciones necesarias para simular la explosión de la nave
	 * @param gui Interfaz Gráfica que se utiliza
	 * @param p Una clase pause
	 * @param nave La nave que se está empleando
	 * @param balitas Vector que contiene las balas actualmente en pantalla
	 */
	public void Die(GraphicInterface gui, Pause p, Ship nave,BulletVector balitas){
		gui.blast((int)nave.getPositionX(), (int)nave.getPositionY()); //Establecemos que hay que explotar
		p.setDelay(Config.MillisWaitOnDie); //esperamos la animación
		nave.resetAndBlink(); //Ponemos la nave en su posición inicial
		this.setLifeMinus(); //quitamos una vida
		balitas.WipeAll(); //Eliminamos todas las balas de golpe
	}

}
