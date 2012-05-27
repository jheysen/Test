import iic1103.GeneradorAleatorio;
/**
 * Clase que lleva a cabo los ataques del jefe y regula su comportamiento
 * @author Jurgen
 * @version 0.0.1
 *
 */
public class BossIA extends BaseIA{
	private int timeleft; //Tiempo que le queda a la fase
	private int tick; //cuadros que lleva esta fase
	private BulletVector balas; //Referencia a las balas vistas en pantalla
	private int phase; //Medimos la fase actual
	private boolean onCard; //Si estamos en la parte carta o la parte ataque
	private Boss ent; //Para esto en particular, necesitamos el tipo específico
	private GeneradorAleatorio rnd; //Necesario para ciertos ataques
	private int auxvar; //Variable de ayuda
	private double auxmult; //multiplicador auxiliar
	private BulletVector auxvect; //Auxiliar cuando necesitamos trazar cosas
	private Ship nave; //referencia a la nave para los ataques que requieren angulo

	public void setNave(Ship nave) {
		this.nave = nave;
	}
	public BossIA(Boss e,BulletVector b){
		ent=e;
		balas=b;
		tick=0;
		setPhase(1);
		onCard=false;
		ent.setBars(16);
		rnd = new GeneradorAleatorio();
		auxvar=0;
		auxmult=1.0;
		auxvect=new BulletVector();
	}
	/**
	 * Método que hace que nuestro jefe haga algo
	 */
	@Override
	public int IAaction(){
		tick++;//avanzamos el contador de la IA
		//Detectamos la posición visible del jugador
		int px=0,py=0;
		for(int i=0;i<field.length;i++){
			for(int j=0;j<field[i].length;j++){
				if(field[i][j]==26){ //si lo vemos, guardamos su ubicación
					px=i;
					py=j;
					System.out.println("X:"+px+" Y:"+py);
				}
			}
		}
		switch(phase){ //se comportará según en qué fase estamos del juego
		case 1: //Ataque 1
			if(tick%120==0){ //Frecuencia 0.5Hz= Cada 2 segs = 120 frames
				for(int i=0;i<26;i++){ //Ponemos nuevas balas y a jugar
					int x=-1;
					int y=-1;
					while(Math.sqrt(Math.pow(x-px, 2)+Math.pow(y-py, 2))<=100){
						x=rnd.entero(0, (int) Config.maxX);
						y=rnd.entero(0, (int) Config.maxY);
					}
					if(balas.getSize()<25) balas.PushBack(new Bullet(new Position(x,y),rnd.entero(1, 14),1,rnd.real(0, 2*Math.PI),rnd.real(0.01 , 1.0)));
				}
				//Shot.RandomShot(balas, 3.0, 26);
				tick=0;
			}
			break;
		case 2: //Carta 1
			if(tick%20==0){ //Frecuencia 3 Hz
				/*for(int i=0;i<26;i++){
					Position pos = ent.getPosition();
					double alpha = 2*Math.PI/26;
					double x = pos.x +Math.cos(i*alpha)*0;
					double y = pos.y - Math.sin(i*alpha)*0;
					balas.PushBack(new Bullet(new Position(x,y),15,1,i*alpha,1.0));
				}*/
				Shot.CircularShot(balas, (int)ent.getPosition().x, (int)ent.getPosition().y, 0.0, 0.5, 26);
				for(int i=balas.getSize()-26;i<balas.getSize();i++){
					balas.at(i).setColor(20);
				}
				tick=0;
			}
			break;
		case 3: //Ataque 2
			if(tick%30==0){ //Frecuencia 2Hz
				Ship aux = new Ship();
				aux.setPosition(new Position(px,py));
				double angulo=Physic.getAngle(nave, ent);
				balas.PushBack(new Bullet(ent.getPosition(),2,1,angulo,3.0));
				tick=0;
			}
			break;
		case 4: //Carta 2
			if(tick%30==0){ //Frecuencia 2Hz
				for(int i=0;i<38;i++){
					if(i%2==0)balas.PushBack(new Bullet(ent.getPosition(),3,2,(2*Math.PI/38)*(i),5.0));
					else balas.PushBack(new Bullet(ent.getPosition(),3,2,(2*Math.PI/38)*(i),4.5));
				}
				tick=0;
			}
			break;
		case 5: //Ataque 3
			switch(auxvar){ //Vemos en qué punto se quiere parar el jefe
			case 0://Posición normal
				if(ent.pos.x==Config.BossSteadyX){ //Su posición normal
					Shot.CircularShot(balas, (int)ent.pos.x, (int)ent.pos.y, 0.0, 0.3, 26);
					auxvar++;
				}
				if(ent.pos.x<Config.BossSteadyX){
					ent.pos.x+=Config.BossMovementSpeed;
				}
				if(ent.pos.x>Config.BossSteadyX){
					ent.pos.x-=Config.BossMovementSpeed;
				}
				break;
			case 1: //Esquina derecha superior
				if(ent.pos.x==Config.maxX-100){
					Shot.CircularShot(balas, (int)ent.pos.x, (int)ent.pos.y, 0.0, 0.3, 26);
					auxvar++;
				}
				if(ent.pos.x<Config.maxX-100){
					ent.pos.x+=Config.BossMovementSpeed;
				}
				if(ent.pos.x>Config.maxX-100){
					ent.pos.x-=Config.BossMovementSpeed;
				}
				break;
			case 2: //Esquina izquierda superior
				if(ent.pos.x==Config.minX+100){
					Shot.CircularShot(balas, (int)ent.pos.x, (int)ent.pos.y, 0.0, 0.3, 26);
					auxvar=0;
				}
				if(ent.pos.x<Config.minX+100){
					ent.pos.x+=Config.BossMovementSpeed;
				}
				if(ent.pos.x>Config.minX+100){
					ent.pos.x-=Config.BossMovementSpeed;
				}
				break;
			}
			/*for(int i=0;i<26;i++){
				Position pos = ent.getPosition();
				double alpha = 2*Math.PI/26;
				double x = pos.x +Math.cos(i*alpha)*0;
				double y = pos.y - Math.sin(i*alpha)*0;
				balas.PushBack(new Bullet(new Position(x,y),15,1,i*alpha,3.0));
			}*/
			tick=0;
			break;
		case 6: //Carta 3
			switch(auxvar){
			case 0: //esquina superior derecha
				if(ent.pos.x==Config.minX+100 && ent.pos.y==Config.minY+100){ //Si etsá en posición disparamos
					Shot.CircularShot(balas, (int)ent.pos.x, (int)ent.pos.y, 100.0, 0.5, 26);
					auxvar=rnd.entero(0, 3);
				}
				if(ent.pos.x<Config.minX+100){
					ent.pos.x+=Config.BossMovementSpeed;
				}
				if(ent.pos.x>Config.minX+100){
					ent.pos.x-=Config.BossMovementSpeed;
				}
				if(ent.pos.y<Config.minY+100){
					ent.pos.y+=Config.BossMovementSpeed;
				}
				if(ent.pos.y>Config.minY+100){
					ent.pos.y-=Config.BossMovementSpeed;
				}
				break;
			case 1: //esquina superior izquierda
				if(ent.pos.x==Config.maxX-100 && ent.pos.y==Config.minY+100){ //Si etsá en posición disparamos
					Shot.CircularShot(balas, (int)ent.pos.x, (int)ent.pos.y, 100.0, 0.5, 26);
					auxvar=rnd.entero(0, 3);
				}
				if(ent.pos.x<Config.maxX-100){
					ent.pos.x+=Config.BossMovementSpeed;
				}
				if(ent.pos.x>Config.maxX-100){
					ent.pos.x-=Config.BossMovementSpeed;
				}
				if(ent.pos.y<Config.minY+100){
					ent.pos.y+=Config.BossMovementSpeed;
				}
				if(ent.pos.y>Config.minY+100){
					ent.pos.y-=Config.BossMovementSpeed;
				}
				break;
			case 2: //esquina inferior derecha
				if(ent.pos.x==Config.maxX-100 && ent.pos.y==Config.maxY-100){ //Si etsá en posición disparamos
					Shot.CircularShot(balas, (int)ent.pos.x, (int)ent.pos.y, 100.0, 0.5, 26);
					auxvar=rnd.entero(0, 3);
				}
				if(ent.pos.x<Config.maxX-100){
					ent.pos.x+=Config.BossMovementSpeed;
				}
				if(ent.pos.x>Config.maxX-100){
					ent.pos.x-=Config.BossMovementSpeed;
				}
				if(ent.pos.y<Config.maxY-100){
					ent.pos.y+=Config.BossMovementSpeed;
				}
				if(ent.pos.y>Config.maxY-100){
					ent.pos.y-=Config.BossMovementSpeed;
				}
				break;
			case 3: //esquina inferior izquierda
				if(ent.pos.x==Config.minX+100 && ent.pos.y==Config.maxY-100){ //Si etsá en posición disparamos
					Shot.CircularShot(balas, (int)ent.pos.x, (int)ent.pos.y, 100.0, 0.5, 26);
					auxvar=rnd.entero(0, 3);
				}
				if(ent.pos.x<Config.minX+100){
					ent.pos.x+=Config.BossMovementSpeed;
				}
				if(ent.pos.x>Config.minX+100){
					ent.pos.x-=Config.BossMovementSpeed;
				}
				if(ent.pos.y<Config.maxY-100){
					ent.pos.y+=Config.BossMovementSpeed;
				}
				if(ent.pos.y>Config.maxY-100){
					ent.pos.y-=Config.BossMovementSpeed;
				}
				break;
			}
			tick=0;
			break;
		case 7: //ataque 4
			boolean ataca=true; //atacaremos solo una vez en la posición
			//Primero volvemos a la posición normal
			if(ent.pos.x<Config.BossSteadyX){
				ent.pos.x+=Config.BossMovementSpeed;
				ataca=false;
			}
			if(ent.pos.x>Config.BossSteadyX){
				ent.pos.x-=Config.BossMovementSpeed;
				ataca=false;
			}
			if(ent.pos.y<Config.BossSteadyY){
				ent.pos.y+=Config.BossMovementSpeed;
				ataca=false;
			}
			if(ent.pos.y>Config.BossSteadyY){
				ent.pos.y-=Config.BossMovementSpeed;
				ataca=false;
			}
			if(ataca && tick%40==0){ //Frecuencia 1.5Hz, y si el jefe dejó de moverse
				Shot.SpiralShot(balas, 1.0, 26, rnd.entero(1, 9), 0.0, ent.pos.x, ent.pos.y, auxmult*0.008);
				auxmult*=-1.0; //Hacemos oscilar el multiplicador
				tick=0;
			}
			break;
		case 8: //carta 4
			//No quería dejar esto acá, pero estoy desesperando que no me sale haciendo el método en la clase Shot
			//balas.PushBack(new Bullet(new Position((int)ent.pos.x+Math.cos(2*tick*Math.PI/8),(int)ent.pos.y+Math.sin(2*tick*Math.PI/8)),3,1,tick*2*Math.PI/100,3.0));
			//balas.PushBack(new Bullet(new Position((int)ent.pos.x+Math.cos(2*tick*Math.PI/8),(int)ent.pos.y+Math.sin(2*tick*Math.PI/8)),4,1,-1.0*tick*2*Math.PI/100,3.0));
			//balas.PushBack(new Bullet(new Position((int)ent.pos.x+Math.cos(2*tick*Math.PI/8),(int)ent.pos.y+Math.sin(2*tick*Math.PI/8)),3,1,(tick+13)*2*Math.PI/100,3.0));
			//balas.PushBack(new Bullet(new Position((int)ent.pos.x+Math.cos(2*tick*Math.PI/8),(int)ent.pos.y+Math.sin(2*tick*Math.PI/8)),4,1,-1.0*(tick+13)*2*Math.PI/100,3.0));
			//balas.PushBack(new Bullet(new Position((int)ent.pos.x+Math.cos(2*tick*Math.PI/8),(int)ent.pos.y+Math.sin(2*tick*Math.PI/8)),3,1,(tick+26)*2*Math.PI/100,3.0));
			//balas.PushBack(new Bullet(new Position((int)ent.pos.x+Math.cos(2*tick*Math.PI/8),(int)ent.pos.y+Math.sin(2*tick*Math.PI/8)),4,1,-1.0*(tick+26)*2*Math.PI/100,3.0));
			if(tick%10==0)
			for(int i=0;i<8;i++){
				balas.PushBack(new Bullet(new Position((int)ent.pos.x+Math.cos(2*tick*Math.PI/8),(int)ent.pos.y+Math.sin(2*tick*Math.PI/8)),3,1,(tick+128*i)*2*Math.PI/1024,2.0));
				balas.PushBack(new Bullet(new Position((int)ent.pos.x+Math.cos(2*tick*Math.PI/8),(int)ent.pos.y+Math.sin(2*tick*Math.PI/8)),4,1,-1.0*(tick+128*i)*2*Math.PI/1024,2.0));
			}
			auxvar=0;
			break;
		case 9: //ataque 5
			//Primero hacemos que se mueva y llegue a una ubicación
			switch(auxvar){
			case 0: //Esquina izquierda
				boolean ataca2=true; //Dice que ataca solo cuando está en la esquina
				if(ent.pos.x>Config.minX+100){
					ent.pos.x-=Config.BossMovementSpeed;
					ataca2=false;
				}
				if(ent.pos.x<Config.minX+100){
					ent.pos.x+=Config.BossMovementSpeed;
					ataca2=false;
				}
				if(ataca2){
					//Shot.CircularShot(balas,(int) ent.pos.x,(int) ent.pos.y, 100, 1.0, 13);
					Entity auxiliar = new Entity();
					auxiliar.setPosition(px, py);
					//Shot.CircularShotAngled(balas, (int)ent.pos.x, (int)ent.pos.y, 100, 1.0, 13, Physic.getAngle(ent, auxiliar));
					for(int i=0;i<26;i++){
						if(i%2==0)balas.PushBack(new Bullet(new Position(ent.pos.x+100*Math.cos(2*i*Math.PI/26),ent.pos.y-100*Math.sin(2*i*Math.PI/26)), 1, 1, 2*i*Math.PI/26, 1.0));
						else{
							balas.PushBack(new Bullet(new Position(ent.pos.x+100*Math.cos(2*i*Math.PI/26),ent.pos.y-100*Math.sin(2*i*Math.PI/26)), 3, 1, Physic.getAngle(ent, auxiliar), 1.0));
							auxvect.PushBack(balas.at(balas.getSize()-1));
						}
					}
					for(int i=0;i<auxvect.getSize();i++){
						auxvect.at(i).setRad(Physic.getAngle(auxvect.at(i), nave));
					}
					auxvect=new BulletVector();
					auxvar=1;
				}
				break;
			case 1:
				boolean ataca3=true; //Dice que ataca solo cuando está en la esquina
				if(ent.pos.x>Config.maxX-100){
					ent.pos.x-=Config.BossMovementSpeed;
					ataca3=false;
				}
				if(ent.pos.x<Config.maxX-100){
					ent.pos.x+=Config.BossMovementSpeed;
					ataca3=false;
				}
				if(ataca3){
					Entity auxiliar = new Entity();
					auxiliar.setPosition(px, py);
					for(int i=0;i<26;i++){
						if(i%2==0)balas.PushBack(new Bullet(new Position(ent.pos.x+100*Math.cos(2*i*Math.PI/26),ent.pos.y-100*Math.sin(2*i*Math.PI/26)), 2, 1, 2*i*Math.PI/26, 1.0));
						else{
							balas.PushBack(new Bullet(new Position(ent.pos.x+100*Math.cos(2*i*Math.PI/26),ent.pos.y-100*Math.sin(2*i*Math.PI/26)), 3, 1, Physic.getAngle(ent, auxiliar), 1.0));
							auxvect.PushBack(balas.at(balas.getSize()-1));
						}
					}
					for(int i=0;i<auxvect.getSize();i++){
						auxvect.at(i).setRad(Physic.getAngle(auxvect.at(i), nave));
					}
					auxvect=new BulletVector();
					auxvar=0;
				}
				break;
			}
			break;
		case 10: //carta 5
			//Primero volvemos a la posición, pero no dice que deba atacar solo cuando esté allí so...
			if(ent.pos.x>Config.BossSteadyX) ent.pos.x-=Config.BossMovementSpeed;
			if(ent.pos.x<Config.BossSteadyX) ent.pos.x+=Config.BossMovementSpeed;
			if(tick%30==0){ //Frecuencia 0.5Hz
				Shot.CircularShotMutant(balas, (int)this.ent.pos.x, (int)this.ent.pos.y, 0.0, 0.1, 26);
			}
			if((tick+6)%30==0){ //Frecuencia 0.5Hz y con desfase 0.1Hz
				Shot.CircularShotMutant(balas, (int)this.ent.pos.x, (int)this.ent.pos.y, 0.0, 0.1, 26);
			}
			if((tick+12)%30==0){
				Shot.CircularShotMutant(balas, (int)this.ent.pos.x, (int)this.ent.pos.y, 0.0, 0.1, 26);
				tick=0;
			}
			break;
		case 11: //Ataque 6
			if(tick%30==0){ //Frecuencia 2Hz
				Shot.RandomBounceShot(balas, 2.0, 16);
				tick=0;
			}
			break;
		case 12: //carta 6
			if(ent.pos.y>Config.minY+100){
				ent.pos.y-=Config.BossMovementSpeed;
			}
			if(ent.pos.y<Config.minY+100){
				ent.pos.y+=Config.BossMovementSpeed;
			}
			int centrox=rnd.entero((int)Config.minX, (int)Config.maxX);
			if(auxvect.getSize()==0){ //cuando no hemos comenzado a trazar un anillo
				auxvect.PushBack(new Bullet(new Position(centrox+70*Math.cos(2*auxvect.getSize()*Math.PI/40),(Math.sqrt(Math.pow(60, 2)-Math.pow(centrox, 2)))-70*Math.sin(2*auxvect.getSize()*Math.PI/40)),8,9,2*auxvect.getSize()*Math.PI/40,0.0));
				balas.PushBack(auxvect.at(auxvect.getSize()-1));
			}
			else{ //si no
				auxvect.PushBack(new Bullet(new Position(centrox+70*Math.cos(2*auxvect.getSize()*Math.PI/40),Math.sqrt(Math.pow(60, 2)-Math.pow(auxvect.at(0).pos.x, 2))-70*Math.sin(2*auxvect.getSize()*Math.PI/40)),8,9,2*auxvect.getSize()*Math.PI/40,0.0));
				balas.PushBack(auxvect.at(auxvect.getSize()-1));
				if(auxvect.getSize()==40){ //cuando termina de trazar, dispara
					for(int i=0;i<auxvect.getSize();i++){
						auxvect.at(i).setRad(Physic.getAngle(nave,auxvect.at(i)));
						auxvect.at(i).setVel(1.0);
					}
					auxvect=new BulletVector();
				}
			}
			break;
		case 13: //Ataque 7
			break;
		case 14: //Carta 7
			break;
		case 15: //Ataque 8
			if(tick%60==0){ //Cada un segundo emite un circulo
				Shot.CircularShot(balas, (int)this.ent.pos.x, (int)this.ent.pos.y, 0.0, 1.0, 16);
				tick=0;
			}
			break;
		case 16: //Carta 8
			auxvect.PushBack(new Bullet(new Position(ent.pos.x+150*Math.cos(2*auxvect.getSize()*Math.PI/40),ent.pos.y-150*Math.sin(2*auxvect.getSize()*Math.PI/40)),rnd.entero(1, 14),1,Physic.getAngle(ent.pos.x,ent.pos.y, px, py),0.0));
			balas.PushBack(auxvect.at(auxvect.getSize()-1));
			if(auxvect.getSize()==40){ //cuando termina de trazar, dispara
				for(int i=0;i<auxvect.getSize();i++){
					auxvect.at(i).setRad(Physic.getAngle(nave,auxvect.at(i)));
					auxvect.at(i).setVel(1.0);
				}
				auxvect=new BulletVector();
			}
			break;
		default:
			break;	
		}
		
		return 0;
	}
	/**
	 * Método que nos permite saber en qué fase del juego vamos
	 * @return Fase del juego actual
	 */
	public int getPhase() {
		return phase;
	}
	/**
	 * Método que nos permite establecer la fase actual
	 * @param phase fase a la que se quiere poner el juego
	 */
	public void setPhase(int phase) {
		this.phase = phase;
		System.out.println("Phase"+phase);
		ent.setBars(16-phase);
		auxvect=new BulletVector(); //Limpiamos el vector auxiliar para evitar problemas con la siguiente fase
		switch(phase){ //Ponemos máximo HP dependiendo de la fase
		case 1:
			ent.setMaxPhaseHP(Config.Phase1HP);
			timeleft=Config.Phase1TL;
			onCard=false;
			balas.WipeAll();
			break;
		case 2:
			ent.setMaxPhaseHP(Config.Phase2HP);
			timeleft=Config.Phase2TL;
			onCard=true;
			balas.WipeAll();
			break;
		case 3:
			ent.setMaxPhaseHP(Config.Phase3HP);
			timeleft=Config.Phase3TL;
			onCard=false;
			balas.WipeAll();
			break;
		case 4:
			ent.setMaxPhaseHP(Config.Phase4HP);
			timeleft=Config.Phase4TL;
			onCard=true;
			balas.WipeAll();
			break;
		case 5:
			ent.setMaxPhaseHP(Config.Phase5HP);
			timeleft=Config.Phase5TL;
			onCard=false;
			balas.WipeAll();
			break;
		case 6:
			ent.setMaxPhaseHP(Config.Phase6HP);
			timeleft=Config.Phase6TL;
			onCard=true;
			balas.WipeAll();
			break;
		case 7:
			ent.setMaxPhaseHP(Config.Phase7HP);
			timeleft=Config.Phase7TL;
			onCard=false;
			balas.WipeAll();
			break;
		case 8:
			ent.setMaxPhaseHP(Config.Phase8HP);
			timeleft=Config.Phase8TL;
			onCard=true;
			balas.WipeAll();
			break;
		case 9:
			ent.setMaxPhaseHP(Config.Phase9HP);
			timeleft=Config.Phase9TL;
			onCard=false;
			balas.WipeAll();
			break;
		case 10:
			ent.setMaxPhaseHP(Config.Phase10HP);
			timeleft=Config.Phase10TL;
			onCard=true;
			balas.WipeAll();
			break;
		case 11:
			ent.setMaxPhaseHP(Config.Phase11HP);
			timeleft=Config.Phase11TL;
			onCard=false;
			balas.WipeAll();
			break;
		case 12:
			ent.setMaxPhaseHP(Config.Phase12HP);
			timeleft=Config.Phase12TL;
			onCard=true;
			balas.WipeAll();
			break;
		case 13:
			ent.setMaxPhaseHP(Config.Phase13HP);
			timeleft=Config.Phase13TL;
			onCard=false;
			balas.WipeAll();
			break;
		case 14:
			ent.setMaxPhaseHP(Config.Phase14HP);
			timeleft=Config.Phase14TL;
			onCard=true;
			balas.WipeAll();
			break;
		case 15:
			ent.setMaxPhaseHP(Config.Phase15HP);
			timeleft=Config.Phase15TL;
			onCard=false;
			balas.WipeAll();
			break;
		case 16:
			ent.setMaxPhaseHP(Config.Phase16HP);
			timeleft=Config.Phase16TL;
			onCard=true;
			balas.WipeAll();
			break;
			
		}
	}
	/**
	 * Método que permite fallar la fase actual de forma prematura
	 */
	public void failPhase(){
		setPhase(phase+1);
	}
	/**
	 * Método que permite ver cuando tiempo queda
	 * @return tiempo que queda
	 */
	public int getTimeleft() {
		return timeleft;
	}
	/**
	 * Permite establecer el tiempo restante
	 * @param timeleft nuevo tiempo restante
	 */
	public void setTimeleft(int timeleft) {
		this.timeleft = timeleft;
	}
	/**
	 * Método que permite saber si estamos en carta o no
	 * @return True si estamos en carta, false si no
	 */
	public boolean getOnCard(){
		return onCard;
	}
	public void UpdateBullets(BulletVector v){
		balas=v;
	}
	/**
	 * Método que indica los bonus points asignados a la fase actual, en el momento que es llamada.
	 * @return Puntos totales que tiene la fase actual
	 */
	public int getBonusPoints(){
		switch(phase){
		case 2:
			return (int) (Config.Card1Bonus-(0.8*Config.Card1Bonus-0.8*Config.Card1Bonus*timeleft/getPhaseTimeLimit()));
		case 4:
			return (int) (Config.Card2Bonus-(0.8*Config.Card2Bonus-0.8*Config.Card2Bonus*timeleft/getPhaseTimeLimit()));
		case 6:
			return (int) (Config.Card3Bonus-(0.8*Config.Card3Bonus-0.8*Config.Card3Bonus*timeleft/getPhaseTimeLimit()));
		case 8:
			return (int) (Config.Card4Bonus-(0.8*Config.Card4Bonus-0.8*Config.Card4Bonus*timeleft/getPhaseTimeLimit()));
		case 10:
			return (int) (Config.Card5Bonus-(0.8*Config.Card5Bonus-0.8*Config.Card5Bonus*timeleft/getPhaseTimeLimit()));
		case 12:
			return (int) (Config.Card6Bonus-(0.8*Config.Card6Bonus-0.8*Config.Card6Bonus*timeleft/getPhaseTimeLimit()));
		case 14:
			return (int) (Config.Card7Bonus-(0.8*Config.Card7Bonus-0.8*Config.Card7Bonus*timeleft/getPhaseTimeLimit()));
		case 16:
			return Config.Card8Bonus;
		}
		
		return 0;
	}
	/**
	 * Método que permite saber el tiempo límite de la fase actual
	 * @return segundos que tiene la fase actual para ser completada
	 */
	public int getPhaseTimeLimit(){
		switch(phase){
		case 1:
			return Config.Phase1TL;
		case 2:
			return Config.Phase2TL;
		case 3:
			return Config.Phase3TL;
		case 4:
			return Config.Phase4TL;
		case 5:
			return Config.Phase5TL;
		case 6:
			return Config.Phase6TL;
		case 7:
			return Config.Phase7TL;
		case 8:
			return Config.Phase8TL;
		case 9:
			return Config.Phase9TL;
		case 10:
			return Config.Phase10TL;
		case 11:
			return Config.Phase11TL;
		case 12:
			return Config.Phase12TL;
		case 13:
			return Config.Phase13TL;
		case 14:
			return Config.Phase14TL;
		case 15:
			return Config.Phase15TL;
		case 16:
			return Config.Phase16TL;
		}
		return 0;
	}
}
