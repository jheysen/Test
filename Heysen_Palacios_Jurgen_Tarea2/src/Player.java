import iic1103T2.*;
/**
 * CLase que representa los distintos par�metros del jugador, como vidas restantes, puntaje, etc.
 * @author Jurgen
 * @version 0.0.1
 *
 */
public class Player {
	private int vidas;
	private int score;
	private int graze;
	
	//Constructor de la clase, carga los valores por defecto desde la configuraci�n
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
	//Pero tambi�n nos interesa quitar de a una
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
	
	//Si bien tiene m�s relaci�n con la nave, queda m�s c�modo implementar esto aqu�
	/**
	 * M�todo que realiza operaciones necesarias para simular la explosi�n de la nave
	 * @param gui Interfaz Gr�fica que se utiliza
	 * @param p Una clase pause
	 * @param nave La nave que se est� empleando
	 * @param balitas Vector que contiene las balas actualmente en pantalla
	 */
	public void Die(GraphicInterface gui, Pause p, Ship nave,BulletVector balitas){
		gui.blast((int)nave.getPositionX(), (int)nave.getPositionY()); //Establecemos que hay que explotar
		p.setDelay(Config.MillisWaitOnDie); //esperamos la animaci�n
		nave.resetAndBlink(); //Ponemos la nave en su posici�n inicial
		this.setLifeMinus(); //quitamos una vida
		balitas.WipeAll(); //Eliminamos todas las balas de golpe
	}

}
