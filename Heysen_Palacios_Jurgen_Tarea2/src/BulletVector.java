/**
 * Clase que provee de una estructura Vector para contener objetos Bullet
 * @author Jurgen
 * @version 0.0.1
 *
 */
public class BulletVector {
	private Bullet[] container; //contenedor real de datos
	private int elements; //contador de espacio usado
	/**
	 * Constructor Vacío, genera un contenedor sin elementos
	 */
	public BulletVector(){
		container=new Bullet[0];
		elements=0;
	}
	/**
	 * Constructor con tamaño inicial, permite establecer un tamaño inicial para el contenedor a modo de mejorar rendimiento en ciertos escenarios
	 * @param size Tamaño inicial
	 */
	public BulletVector(int size){
		container=new Bullet[size];
		elements=0;
	}
	/**
	 * Constructor que recibe un contenedor y lo asocia a la clase
	 * @param v Arreglo de Bullets a asociar
	 */
	public BulletVector(Bullet[] v){
		container = v.clone();
		elements=container.length;
	}
	/**
	 * Constructor copia
	 * @param v Objeto a copiar
	 */
	public BulletVector(BulletVector v){
		container=new Bullet[v.getSize()+1];
		elements=0;
		for(int i=0;i<v.getSize();i++){
			container[i]=v.at(i);
			elements++;
		}
	}
	/**
	 * Método que permite acceder al objeto en la posición i-ésima
	 * Cuenta con protección frente a indices inválidos
	 * @param idx índice a acceder
	 * @return referencia al elemento i-ésimo
	 */
	public Bullet at(int idx){
		if(idx>container.length || idx < 0) return null;
		return container[idx];
	}
	/**
	 * Permite agregar un nuevo elemento al final del vector
	 * Si falta espacio, amplía el contenedor
	 * @param a elemento a agregar
	 */
	public void PushBack(Bullet a){
		if(elements==0){ //Si no hay capacidad hacemos un espacio
			container=new Bullet[1];
			container[0]=a;
			elements++;
			return;
		}
		if(elements<container.length){ //Si sobra, simpemente agregamos
			container[elements]=a;
			elements++;
		}
		else{
			//expandir y añadir
			ampliar();
			PushBack(a);
		}
	}
	/**
	 * Método que permite ampliar el contenedor
	 * De uso interno
	 */
	private void ampliar(){
		Bullet[] a = container.clone();
		container = new Bullet[container.length*2]; //Simplemente soblamos la capacidad
		for(int i=0;i<a.length;i++){
			container[i]=a[i];
		}
	}
	/**
	 * Permite conocer la cantidad actual de objetos que almacena el contenedor, no el tamaño del contenedor
	 * @return
	 */
	public int getSize(){
		return elements;
	}
	/**
	 * Permite agregar un elemento al principio del vector
	 * @param a Elemento a agregar
	 */
	public void PushFront(Bullet a){
		if(elements==0){ //Si está vacío le hacemos espacio
			container=new Bullet[1];
			container[0]=a;
			elements++;
			return;
		}
		if(elements<container.length){ //Si sobra solo lo añadimos
			for(int j=elements;j>0;j--){ //Como es al principio hay que correr todos uno
				Bullet tmp=container[j];
				container[j]=container[j-1];
				container[j+1]=tmp;
			}
			container[0]=a;
			elements++;
		}
		else{ //Si falta espacio, primero agrandamos el contenedor
			ampliar();
			PushFront(a);
		}
	}
	/**
	 * Método que permite eliminar todas las balas de golpe
	 */
	public void WipeAll(){
		for(int i=0;i<container.length;i++){
			container[i]=null;
		}
		container=new Bullet[0];
		elements=0;
	}
	/**
	 * Método que permite eliminar el primer elemento del vector
	 */
	public void PopFront(){
		System.out.println("Popping front");
		if(elements>0){ //Solo será válido si hay a lo menos 1 elemento
			for(int i=1;i<elements;i++){ //corremos todos los demás en 1
				container[i-1]=container[i];
			}
			elements--; //Derementamos la cuenta
		}
	}
	/**
	 * Método que permite eliminar el último elemento del vector
	 */
	public void PopBack(){
		System.out.println("Popping back");
		if(elements>0){ //Solo funciona si hay a lo menos un elemento
			container[elements-1]=null; //desreferneciamos el último
			elements--; //Quitamos la cuenta
		}
	}
	/**
	 * Método que permite eliminar el elemento en la posición deseada
	 * @param idx índice del elemento a eliminar
	 */
	public void PopAt(int idx){
		if(idx==0){
			PopFront();
			return;
		}
		if(idx==elements-1){
			PopBack();
			return;
		}
		if(elements>idx && idx>=0){//solo tiene sentido si el índice es válido
			/*Bullet[] aux=new Bullet[elements];
			int j=0;
			for(int i=0;i<elements-1;i++){ //Corremos todos los que vienen despues del elemento a eliminar
				if(i!=idx){
					aux[j]=container[i];
					j++;
				}
				//container[idx]=container[idx+1];
			}
			container=aux;*/
			container[idx]=container[0];
			elements--; //Decrementamos la cuenta
		}
	}

}
