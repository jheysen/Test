import iic1103T2.*;
import iic1103.InputOutput;

public class Principal {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GraphicInterface gui = new GraphicInterface(); //Generamos nuestra ventana
		InputManager im = new InputManager(gui.getKeyboard()); //Inicializamos nuestro administrador de entrada
		Pause p = new Pause(); //Pausa, para regular el framerate
		
		//Arreglos que utiliza el juego
		int[][] board = new int[(int) Config.maxX][(int) Config.maxY]; //nuestro campo de juego
		boolean[] keys; //mapa de teclas
		
		boolean laser; //Verifica si estamos disparando o no
		boolean laserlock=false; //Bloqueo del laser
		double temperatura=0.0; //temperatura del láser
		int focusmultiplier=1; //Multiplicador para el daño por estar enfocado
		double temperaturemultiplier = 1.0; //Multiplicador para la temperatura por estar enfocado
		int frame=0; //contador de cuadro actual, para actualizar el tiempo
		int focusdivisor=1; //Divisor para la velocidad por estar enfocado
		boolean manualgod = false; //Permite saber si godmode viene dado por configuración o no
		boolean gameflow = true; //Usaremos esta variable para saber si debemos salir del juego o no
		boolean bossinvul=false; //Nos permite hacer invulnerable al jefe
		int bossinvultick=0; //Nos permite saber cuantos cuadros de invulnerabilidad lleva el jefe
		boolean hasCrashed = false; //Boolean que usaremos para verificar si se gana bonus o no
		int currentGraze = 0; //contador de grazes que hacemos durante la carta
		boolean activatedAP = false; //Permite saber si se ha usado el piloto automático
		int apcount=0; //contador para el AP
		
		Ship nave = new Ship(); //creamos nuestra nave
		nave.setBlink(false); //Al comienzo no queremos que parpadee
		Boss jefe = new Boss(); //creamos al jefe
		BulletVector balitas = new BulletVector(); //Vector de balas en pantalla
		Player jugador = new Player(); //Parámetros del jugador
		BossIA malvado = new BossIA(jefe,balitas); //Creamos una inteligencia artifical para el jefe y la asociamos a su entidad
		ShipIA piloto = new ShipIA(nave); //Creamos una inteligencia artifical para la nave (el piloto automático) y la asociamos a su entidad
		Slave es1 = new Slave(jefe); //Creamos a nuestros esclavos del jefe
		Slave es2 = new Slave(jefe);
		SlaveIA ies1 = new SlaveIA(es1,jefe,nave,1); //Creamos sus inteligencias artificales para que las operen
		SlaveIA ies2 = new SlaveIA(es2,jefe,nave,2);
		
		piloto.setJefe(jefe); //damos referencia al jefe a la IA
		
		if(Config.GodMode) manualgod=true; //Si está por configuración, usaremos esto para evitar desactivar el modo dios en la mecánica
		
		if(Config.LowQuality) gui.setLowQ(); //Si activamos el modo de baja calidad, lo aplicamos
		
		while(gameflow){ //mientras que pueda seguir el juego....
			
			malvado.Update(board); //Le entregamos a la IA el cuadro anterior
			piloto.setBalas(balitas); //Le entregamos las balas al AP
			ies1.UpdateBullets(balitas); //Le entregamos las balas a los esclavos también para que puedan disparar
			ies2.UpdateBullets(balitas);
			ies1.setPhase(malvado.getPhase()); //Le indicamos a los súbditos la fase actual
			ies2.setPhase(malvado.getPhase());
			
			//resetamos el campo
			for(int i=0;i<board.length;i++){
				for(int j=0;j<board[i].length;j++){
					board[i][j]=0;
				}
			}
			
			//TODO: AutoPilot
			if(im.isOverride()) im.setCommand(piloto.IAaction()); //Si está activo el piloto automático, que inyecte el comando a realizar este frame
			im.ProcessInput(); //actualiza el estado de teclado
			//Evaluamos el teclado
			keys=im.getCommand(); //actualizamos el mapa de teclado
			if(keys[4]){ //Evaluamos foco primero, ya que afecta el movimiento
				gui.setFocus(true); //focus
				focusmultiplier=Config.FocusDmgMultiplier;
				temperaturemultiplier=Config.FocusTemperatureMultiplier;
				focusdivisor=Config.FocusSpeedDivisor;
			}
			else{
				gui.setFocus(false);
				focusmultiplier=1;
				temperaturemultiplier=1.0;
				focusdivisor=Config.NoFocusSpeedDivisor;
			}
			if(keys[0]) nave.setPosition(nave.getPositionX(), nave.getPositionY()-(Config.ShipSpeedPerFrame/focusdivisor)); //avanzar
			if(keys[1]) nave.setPosition(nave.getPositionX(), nave.getPositionY()+(Config.ShipSpeedPerFrame/focusdivisor)); //retroceder
			if(keys[2]) nave.setPosition(nave.getPositionX()+(Config.ShipSpeedPerFrame/focusdivisor), nave.getPositionY()); //derecha
			if(keys[3]) nave.setPosition(nave.getPositionX()-(Config.ShipSpeedPerFrame/focusdivisor), nave.getPositionY()); //izquierda
			
			if(keys[6]) laser=true; //Disparamos
			else laser=false;
			if(keys[5]){
				if((!activatedAP && Config.APLimitUses) || !Config.APLimitUses){
					im.setOverride(true); //Hacemos que el piloto automático tome control
					gui.setAutoPilot(true);
					activatedAP=true; //marcamos el AP como usado
				}
			}
			//Nuestro pequeño Cheat para matar al boss
			if(Config.CheatKeyNextPhase){
				if(im.CheatKey()){
					jefe.setHP(0); //Si está activo el cheat y presionamos la tecla, lo matamos
					p.setDelay(20); //Y un pequeño delay para soltar la tecla
				}
			}
			
			//verificamos si termina el AP
			if(apcount>=Config.APMaxTime){
				gui.setAutoPilot(false);
				im.setOverride(false);
				apcount=0;
				nave.setBlink(true); //Hacemos que la nave sea invulnerable por unos segundos tras recuperar el control
			}
			if(im.isOverride()) apcount++; //Si esta activo sumamos al contador
			
			if(laserlock) laser=false; //Si aún está bloqueado, negamos la acción de atacar
			
			//Avanzamos las balas
			for(int i=0;i<balitas.getSize();i++){
				balitas.at(i).setNave(nave); //Les damos una referencia a la nave
				balitas.at(i).Update();
			}
			
			//Detectamos colisiones
			boolean choque=false;
			for(int i=0;i<balitas.getSize();i++){ //verificamos con cada bala si los hitbox coinciden
				if(Physic.collision(nave, balitas.at(i))) choque=true;
				if(Physic.graze(Config.ShipGrazeRadius, nave, balitas.at(i))){
					jugador.AddGraze(1); //Si hay graze, damos puntos
					jugador.AddScore(1);
					if(malvado.getOnCard()) currentGraze++;
				}
			}
			//Detectamos si chocamos al Boss también
			if(Physic.collision(nave, jefe)) choque=true;
			if(choque){ //si chocó con alguna explotamos la nave, quitamos una vida y adiós balas
				jugador.Die(gui,p,nave,balitas);
				balitas = new BulletVector();
				hasCrashed=true;
				temperatura=0.0;
				activatedAP=false;
				im.setOverride(false); //Si estaba activado el AP, lo quitamos
			}
			
			//Si sobrecalentamos el laser, explotar, salvo que hayamos desactivado esta mecánica en la configuración
			if(temperatura>=Config.maxHeat && !Config.NoHeatDeath){
				jugador.Die(gui, p, nave,balitas);
				balitas=new BulletVector();
				hasCrashed=true;
				temperatura=0.0;
				activatedAP=false;
				im.setOverride(false); //Si estaba activado el AP, lo desactivamos
			}
			//Si lo sobrecalentamos, nos aseguramos que se queda a máximo y no se pasa (cuando desactivamos morir por ello)
			if(temperatura>Config.maxHeat) temperatura=Config.maxHeat;

			
			//TODO: Detección de ataque por bandas
			if(laser && !bossinvul){
				jefe.setHP(jefe.getHP()-focusmultiplier*Physic.Dmg(nave, jefe,temperatura)); //Dañamos al boss
				jugador.AddScore((int)focusmultiplier*(int)Physic.Dmg(nave, jefe, temperatura)); //Damos puntos por punto dañado al boss
			}
			
			//Ahora vemos si se muere el jefe
			if(jefe.getHP()<=0.0){
				if((malvado.getPhase())%2==0){
					if(!hasCrashed){ //vemos si ha muerto o no en el desarrolo de la fase
						gui.isBonus(true); //ganamos el bono si estabamos en carta
						gui.setBonus(malvado.getBonusPoints()+(int)Math.pow(currentGraze, 3)); //Y lo establecemos.
						jugador.AddScore(malvado.getBonusPoints()+(int)Math.pow(currentGraze, 3)); //Añadimos el bonus, solo si lo gana
					}else{ //Si murió, pierde bonus
						gui.isBonus(false);
					}
				}
				malvado.setPhase(malvado.getPhase()+1); //cambiamos de fase
				balitas=new BulletVector(); //eliminamos balas
				currentGraze=0; //reiniciamos esta cuenta
				gui.isCard(malvado.getOnCard());
				hasCrashed=false; //reseteamos este marcador para cada fase
				bossinvul=true; //Cambiamos de fase, así que lo hacemos invulnerable
				bossinvultick=0;//Y empezaremos a contar cuantos cuadros de invulnerabilidad lleva
				//a ver si esto ayuda con los subditos que no se mueven...
				es1.pos.x=jefe.pos.x;
				es1.pos.y=jefe.pos.y;
				es2.pos.x=jefe.pos.x;
				es2.pos.y=jefe.pos.y;
			}
			
			//regulamos la invulnerabilidad del boss
			if(bossinvul){
				bossinvultick++;
			}
			if(((malvado.getPhase()+1)%2)==0 && bossinvul && bossinvultick>=Config.BossInvunerabilityFramesperPhase){
				bossinvul=false;
			}
			else if(bossinvul && bossinvultick>=Config.BossInvunerabilityFramesperPhase-100) bossinvul=false;
			
			//Temperatura de disparo
			if(laser && !Config.NoHeat){ //Aumentamos temperatura si disparamos, y activamos esta función
				temperatura += temperaturemultiplier*Config.HeatUpInterval; //Si disparamos, incrementamos temperatura
			}
			if(!laser && temperatura>=Config.HeatLaserLock && !Config.NoLaserLock)laserlock=true; //Si pasamos el umbral, bloqueamos el laser (y si no hemos impedido que se bloquee)
			if(!laser && temperatura>=0.0) temperatura -= Config.HeatDownInterval; //Si no, enfriamos
			if(temperatura<=0.0){
				temperatura = 0.0; //Si se nos pasa la mano enfriando, arreglamos
				laserlock=false; //Desbloqueamos el laser también
			}
			
			//TODO: Patrones de ataque
			malvado.setNave(nave);
			malvado.UpdateBullets(balitas); //Actualizamos la refernecia de balas
			malvado.IAaction(); //Hacemos que el jefe haga algo
			ies1.IAaction(); //hacemos que los subditos hagan algo
			ies2.IAaction();
			
			//Limpiamos balas ,lo hacemos acá por si el jefe puso alguna inválida(así solo quitamos balas una sola vez...)
			BulletVector balitas1= new BulletVector();
			for(int i=0;i<balitas.getSize();i++){
				if(balitas.at(i).isAlive() && balitas.at(i).pos.x<Config.maxX-1 && balitas.at(i).pos.y<Config.maxY-1) balitas1.PushBack(balitas.at(i));
			}
			balitas=balitas1;
			
			//dibujamos balas
			for(int i=0;i<balitas.getSize();i++){
				if(balitas.at(i).getPosition().x<Config.minX || balitas.at(i).getPosition().x>=Config.maxX || balitas.at(i).getPosition().y<Config.minY || balitas.at(i).getPosition().y>=Config.maxY) continue; //Si con esto sigo teniendo fugas de memoria, me pego un tiro
				board[(int) Math.ceil(balitas.at(i).getPosition().getX())][(int) Math.ceil(balitas.at(i).getPosition().getY())]=balitas.at(i).getColor();
			}
			
			//Parpadeo
			if(nave.getBlink()){ //dibujamos la nave dependiendo de si la hacemos parpadear o no
				Config.GodMode = true; //Activamos el modo invulnerable mientras parpadea
				if((nave.getBlinkedFrames()%2)==0){ //Si parpadea, la dibujamos solo los cuadros pares
					board[(int) nave.getPositionX()][(int) nave.getPositionY()]=26;  //dibujamos
				}
				nave.setBlinkedFrames(nave.getBlinkedFrames()+1); //actualizamos la cuenta de cuadros
				if(nave.getBlinkedFrames()>Config.numBlinkFrames){
					nave.setBlink(false); //Si alcanzamos el máximo de cuadros a parpadear, dejamos de hacerlo
					if(!manualgod) Config.GodMode = false; //Salimos de godmode, solo si no estaba por configuración así antes
				}
			} else board[(int) nave.getPositionX()][(int) nave.getPositionY()]=26; //dibujamos la nave en el mapa
			
			board[(int)es1.pos.x][(int)es1.pos.x]=es1.getSprite(); //Dibujamos a los súbditos
			board[(int)es2.pos.x][(int)es2.pos.x]=es2.getSprite();
			
			board[(int) jefe.getPosition().getX()][(int) jefe.getPosition().getY()]=25; //dibujamos al jefe en el mapa
			
			//Actualizamos el tiempo de fase
			if(frame==60){ //se supone que esto va a 60 FPS...
				malvado.setTimeleft(malvado.getTimeleft()-1); //Quitamos un segundo
				if(malvado.getTimeleft()==-1){
					malvado.failPhase(); //si nos pasamos del tiempo, perdemos
					balitas=new BulletVector(); //Quitamos las balas de golpe
					gui.isCard(malvado.getOnCard());
				}
				frame=-1; //luego se hará 0 al incrementar
			}
			frame++;
			
			//Actualización de parámetros de ventana
			gui.setHP(jefe.getModuledHP()); //actualizar HP de la fase
			gui.setHPBars(jefe.getBars()); //actualizar barras de vida
			gui.setLaser(laser); //ver si atacamos o no
			gui.setHeat(temperatura); //Temperatura de disparo
			gui.setScore(jugador.getScore()); //actualizar score
			gui.setLives(jugador.getLife()); //actualizamos las vidas restantes
			gui.setGraze(jugador.getGraze()); //balas esquivadas
			gui.setTimer(malvado.getTimeleft()); //actualizamos el tiempo restante
			gui.update(board); //actualizar campo
			gui.show(); //mostrar cambios
			p.setDelay(Config.MillisWaitOnFrame); //limitar framerate
			
			//Ahora vemos si el juego terminó
			if(jugador.getLife()==-1){ //Si el jugador murió..
				gui.gameOver(false, (int)nave.getPositionX(), (int)nave.getPositionY());
				p.setDelay(Config.MillisWaitOnDie);
				gameflow=false;
				board[(int) nave.pos.x][(int) nave.pos.y]=0; //La quitamos para que no se vea luego de la explosion
				new InputOutput().muestra("Lamenablemente, has perdido");
			}
			if(jefe.getBars()==-1 || malvado.getPhase()>Config.numphases){ //Si murió el jefe...
				gui.gameOver(true, (int)jefe.getPosition().getX(), (int)jefe.getPosition().getY());
				p.setDelay(Config.MillisWaitOnDie);
				gameflow=false;
				gui.blast((int)es1.pos.x, (int)es1.pos.y); //hacemos que exploten los subditos tambien
				gui.blast((int)es2.pos.x, (int)es2.pos.y);
				board[(int) jefe.pos.x][(int) jefe.pos.y]=0; //Los quitamos para que no se ven luego de la explosión
				board[(int) es1.pos.x][(int) es1.pos.y]=0;
				board[(int) es2.pos.x][(int) es2.pos.y]=0;
				new InputOutput().muestra("Felicitaciones, has ganado!");
			}
		}
		//Probablemente termino el juego
		for(int i=0;i<180;i++){ //Esperamos 3 segs para que se vea la animación
			p.setDelay(16);
			gui.update(board); 
			gui.show(); 
		}
		gui.dispose(); //Cerramos la ventana

	}

}
