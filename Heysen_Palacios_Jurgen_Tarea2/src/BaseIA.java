/**
 * Clase base para las Inteligencias artificiales
 * Propone la estrcutura base que deber�n seguir
 * @author Jurgen
 * @version 0.0.1
 *
 */
public class BaseIA {
	protected int[][] field; //Copia del campo visual para ver qu� hay
	protected Entity ent; //Entidad asociada a la IA
	
	/**
	 * Constructor de nuestra inteligencia artifical, la  asocia a una entidad particular
	 * @param e Entidad asociada a esta IA
	 */
	public BaseIA(Entity e){
		ent=e;
	}
	/**
	 * Constructor Vac�o
	 */
	public BaseIA(){
		
	}
	/**
	 * M�todo que permitir� actualizar los datos almacenados de la IA
	 * @param a Arreglo bidimiensional que representa el estado visual
	 */
	public void Update(int[][] a){
		field=a.clone(); //guardamos lo que nos entregan como un clon para trabajar con �l
	}
	/**
	 * M�todo que permite que la IA procese sus datos para determinar una acci�n a realizar
	 * Cada IA desprendida har� un Override de este m�todo para determinar qu� hacer
	 * @return Alg�n c�digo de comando, si aplica
	 */
	public int IAaction(){
		return 0;
	}

}
