/**
 * Clase base para las Inteligencias artificiales
 * Propone la estrcutura base que deberán seguir
 * @author Jurgen
 * @version 0.0.1
 *
 */
public class BaseIA {
	protected int[][] field; //Copia del campo visual para ver qué hay
	protected Entity ent; //Entidad asociada a la IA
	
	/**
	 * Constructor de nuestra inteligencia artifical, la  asocia a una entidad particular
	 * @param e Entidad asociada a esta IA
	 */
	public BaseIA(Entity e){
		ent=e;
	}
	/**
	 * Constructor Vacío
	 */
	public BaseIA(){
		
	}
	/**
	 * Método que permitirá actualizar los datos almacenados de la IA
	 * @param a Arreglo bidimiensional que representa el estado visual
	 */
	public void Update(int[][] a){
		field=a.clone(); //guardamos lo que nos entregan como un clon para trabajar con él
	}
	/**
	 * Método que permite que la IA procese sus datos para determinar una acción a realizar
	 * Cada IA desprendida hará un Override de este método para determinar qué hacer
	 * @return Algún código de comando, si aplica
	 */
	public int IAaction(){
		return 0;
	}

}
