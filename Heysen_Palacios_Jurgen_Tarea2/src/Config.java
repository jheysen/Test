/**
 * Esta clase entregará valores estáticos sobre el juego, como un archivo de configuración
 * @author Jurgen
 * @version 0.0.1
 *
 */
public class Config {
	
	//Límites de área de renderizado
	public static double maxX = 576.0; //Coordenada X máxima válida
	public static double maxY = 672.0; //Coordenada Y máxima válida
	public static double minX = 0.0; //Coordenada X mínima válida
	public static double minY = 0.0; //Coordenada Y mínima válida
	
	//Configuración de calidad
	public static boolean LowQuality = false; //Permite activar el modo de baja calidad
	
	//Parámetros del juego
	public static int numphases = 16; //Número de fases máximo a jugarse
	public static int numInitLifes = 3; //Número inicial de vidas
	public static int numslaves = 2; //Número de esclavos
	public static int InitScore = 0; //Score inicial
	public static int InitGraze = 0; //Graze inicial
	public static double HeatUpInterval = 0.25; //Aumento de temperatura por cuadro ( 1 seg = 100 cuadros)
	public static double HeatDownInterval = 0.1; //Disminución de temperatura por cuadro
	public static double HeatLaserLock = 75.0; //Umbral de bloqueo del laser
	public static int MillisWaitOnDie = 10; //Milisegundos a pausar renderizado por muerte
	public static int MillisWaitOnFrame = 16; //Milisegundos a pausar en cada frame
	public static int numBlinkFrames = 180; //Número de cuadros que debe parpadear la nave luego de morir
	public static double maxHeat = 100.0; //Temperatura máxima a la que explota la nave
	public static double LaserNormalDmgPerSecond = 60.0; //Daño que hace el láser normal por segundo
	public static int FocusDmgMultiplier = 2; //Multiplicador del daño por estado focus
	public static double FocusTemperatureMultiplier = 1.5; //Multiplicador de temperatura por estado focus
	public static double TargetFramerate = 60.0; //framerate que queremos mantener teóricamente, útil para cálculos
	public static int ShipSpeedPerFrame = 6; //Velocidad de la nave, por cuadro
	public static int FocusSpeedDivisor = 2; //Divisor para la velocidad si estamos en estado de foco
	public static int NoFocusSpeedDivisor = 1; //Divisor para la velocidad cuando no estamos en foco
	public static int BossInvunerabilityFramesperPhase = 180; //Cuadros de invulnerabilidad en cada fase del jefe
	public static double BossMovementSpeed = 1.0; //Velocidad del jefe al moverse
	public static int APMaxTime = 180; //Tiempo que permanece el AP activado
	
	//Posiciones iniciales
	public static int ShipInitX = 288; //Corrdenada X inicial de la nave
	public static int ShipInitY = 550; //Coordenada Y inicial de la nave
	public static int BossInitX = 288; //Coordenada X donde aparece el jefe
	public static int BossInitY = 100; //Coordenada Y inicial donde aparece el jefe
	public static int BossSteadyX= 288; //Coordenada X donde se queda el jefe
	public static int BossSteadyY = 150; //Coordenada Y donde se queda el jefe
	
	//Radios de Hitbox
	public static int ShipHitboxRadius = 4; //Radio del hitbox de la nave
	public static int BossHitboxRadius = 20; //Radio del hitbox del boss
	public static int SlaveHitboxRadius = 0; //Radio del hitbox de los esclavos
	public static int SmallBulletHitbox = 8; //Radio de hitbox de balas pequeñas
	public static int BigBulletHitbox = 14; //Radio de hitbox de balas grandes
	public static int ShipGrazeRadius = 16; //Radio dentro del que se considera graze
	
	//HP de cada fase
	public static double Phase1HP = 1000.0; //Ataque 1
	public static double Phase2HP = 1400.0; //Carta 1
	public static double Phase3HP = 1200.0; //Ataque 2
	public static double Phase4HP = 1500.0; //Carta 2
	public static double Phase5HP = 1400.0; //Ataque 3
	public static double Phase6HP = 1700.0; //Carta 3
	public static double Phase7HP = 1600.0; //Ataque 4
	public static double Phase8HP = 1800.0; //Carta 4
	public static double Phase9HP = 1700.0; //Ataque 5
	public static double Phase10HP = 1900.0; //Carta 5
	public static double Phase11HP = 1800.0; //Ataque 6
	public static double Phase12HP = 2000.0; //Carta 6
	public static double Phase13HP = 1900.0; //Ataque 7
	public static double Phase14HP = 2200.0; //Carta 7
	public static double Phase15HP = 2100.0; //Ataque 8
	public static double Phase16HP = 99999999999999999999999999999999999.99; //Carta 8
	
	//Tiempo de cada fase
	public static int Phase1TL = 30; //Ataque 1
	public static int Phase2TL = 40; //Carta 1
	public static int Phase3TL = 40; //Ataque 2
	public static int Phase4TL = 30; //carta 2
	public static int Phase5TL = 40; //ataque 3
	public static int Phase6TL = 80; //carta 3
	public static int Phase7TL = 40; //ataque 4
	public static int Phase8TL = 70; //carta 4
	public static int Phase9TL = 60; //ataque 5
	public static int Phase10TL = 60; //carta 5
	public static int Phase11TL = 40; //ataque 6
	public static int Phase12TL = 90; //carta 6
	public static int Phase13TL = 60; //ataque 7
	public static int Phase14TL = 70; //carta 7
	public static int Phase15TL = 70; //ataque 8
	public static int Phase16TL = 50; //carta 8
	
	//Bonus de las fases que lo tienen, es decir, las cartas
	public static int Card1Bonus=1200000;
	public static int Card2Bonus=1400000;
	public static int Card3Bonus=1800000;
	public static int Card4Bonus=2200000;
	public static int Card5Bonus=2400000;
	public static int Card6Bonus=2600000;
	public static int Card7Bonus=2800000;
	public static int Card8Bonus=5000000;
	
	//Para debug
	public static boolean GodMode = true; //Modo dios, desactiva la detección de coliciones
	public static boolean NoHeat = false; //Desactiva temperatura del laser
	public static boolean NoHeatDeath = true; //Desactiva morir por sobrecalentamiento del laser
	public static boolean NoLaserLock = true; //Desactiva el bloqueo de laser
	public static boolean CheatKeyNextPhase = true; //Permite que al presionar cierta tecla, pasemos a la siguiente fase de ataque inmediatamente
	public static boolean APLimitUses = false; //Permite usar el AP más de una vez por vida cuando es false

}
