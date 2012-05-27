import iic1103T2.Keyboard;

/***
 * Permite administrar la entrada desde el teclado u otro punto del programa
 * @author Jurgen
 * @version 0.0.1
 * 
 *
 */
public class InputManager {
	private Keyboard key;
	private boolean[] command; //cada tecla se corresponderá con un espacio acá
	private boolean override;
	
	
	//códigos de cada tecla, hace más fácil optimizar
	private static int x=88;
	private static int z=90;
	private static int shift=16;
	private static int arriba=38;
	private static int abajo=40;
	private static int derecha=39;
	private static int izquierda=37;
	
	/**
	 * Constructor Vacío
	 * @param Nada
	 * @return Nada
	 * 
	 */
	public InputManager(){
		command = new boolean[7];
		for(int i=0;i<command.length;i++){
			command[i]=false;
		}
		override=false;
	}
	/**
	 * Constructor de la clase
	 * @param a 
	 * Objeto teclado que será evaluado
	 */
	public InputManager(Keyboard a){
		key=a;
		//key.showKeyCodes(true);
		command=new boolean[7];
		override=false;
	}
	/**
	 * Permite cambiar el teclado asociado
	 * @param k
	 * Teclado a asociar al objeto
	 */
	public void UpdateKeyboard(Keyboard k){
		key=k;
	}
	/**
	 * Método de procesamiento de entrada de teclado, permite determinar qué se debe hacer en virtud de las teclas presionadas
	 * Sólo evalúa si no se ha invalidado el teclado
	 */
	public void ProcessInput(){ //procesamos el teclado, solo si la IA no ha activado el override de entrada
		//Si un flujo de datos no ha tomado control del programa, evaluamos el teclado y a cada celda le asignamos su tecla
		if(!override){
			if(key.isKeyPressed(arriba)) command[0]=true; //arriba
			else command[0]=false;
			if(key.isKeyPressed(abajo)) command[1]=true; //abajo
			else command[1]=false;
			if(key.isKeyPressed(derecha)) command[2]=true; //derecha
			else command[2]=false;
			if(key.isKeyPressed(izquierda)) command[3]=true; //izquierda
			else command[3]=false;
			if(key.isKeyPressed(shift)) command[4]=true; //shift
			else command[4]=false;
			if(key.isKeyPressed(x)) command[5]=true; //x
			else command[5]=false;
			if(key.isKeyPressed(z)) command[6]=true; //z
			else command[6]=false;
		}
				
	}
	/**
	 * Permite desactivar el procesamiento del teclado, ideal para un objeto que requiera inyectar comandos a la entrada
	 * @param stat
	 * estado de la entrada por teclado, true para habilitado y false para deshabilitar
	 */
	public void setOverride(boolean stat){ //permite que la IA tome control de la entrada
		override=stat;
	}
	/**
	 * Permite inyectar comandos a la entrada, sólo si el teclado ha sido invalidado
	 * @param c
	 * código del comando a inyectar
	 * Los válidos son 1 para subir, 2 para bajar, 3 para ir a la derecha, 4 a la izquierda, 5 para focus, 6 para AP y 7 para inyectar tecla z
	 */
	public void setCommand(int c){ //permite que la IA ingrese comandos directos a la entrada
		if(override){
			for(int i=0;i<command.length;i++){ //primero reseteamos el mapa de teclas
				command[i]=false;
			}
			switch(c){ //revisa el comando y lo hace pasar como tecla presionada
			case 1:
				command[0]=true;
				break;
			case 2:
				command[1]=true;
				break;
			case 3:
				command[2]=true;
				break;
			case 4:
				command[3]=true;
				break;
			case 5:
				command[4]=true;
				break;
			case 6:
				command[5]=true;
				break;
			case 7:
				command[6]=true;
				break;
			}
		}
	}
	/**
	 * permite recuperar el comando a efectuar según lo indique la entrada actual de datos
	 * @return
	 * Comando a ejecutar
	 */
	public boolean[] getCommand(){ //con esto el juego sabe qué debe hacer en virtud de la tecla presionada
		return command;
	}
	
	/**
	 * Método que permite conocer si el teclado está activo o la entrada está en control de otro elemento
	 * @return true si está otro elemento en control de la entrada, false si es el teclado.
	 */
	public boolean isOverride() {
		return override;
	}
	/**
	 * Método que permite saber si se preciono la tecla asignada a matar al jefe instantaneamente
	 * @return true si está presionada (y el cheat activado), false si no
	 */
	public boolean CheatKey(){
		if(Config.CheatKeyNextPhase && key.isKeyPressed(82)) return true;
		return false;
	}
}
