/**
 * Clase que modela el piloto automático de nuestra nave
 * 
 * @author Jurgen
 * @version 0.0.1
 *
 */
public class ShipIA extends BaseIA{
	private BulletVector balas;
	private Boss jefe;

	public void setBalas(BulletVector balas) {
		this.balas = balas;
	}
	
	public void setJefe(Boss jefe) {
		this.jefe = jefe;
	}
	
	public ShipIA(Entity e){
		this.ent=e;
	}
	
	@Override
	public int IAaction(){
		int accion=0;
		
		//Primero la IA mira si puede atacar
		if(jefe.pos.x==ent.pos.x && jefe.pos.y<ent.pos.y){
			accion=7;
		}
		
		//Si no trata de ponerse donde pueda atacar
		if(jefe.pos.x + Config.ShipSpeedPerFrame < ent.pos.x && jefe.pos.x - Config.ShipSpeedPerFrame < ent.pos.x) accion=4;
		if(jefe.pos.x + Config.ShipSpeedPerFrame > ent.pos.x && jefe.pos.x - Config.ShipSpeedPerFrame > ent.pos.x) accion=3;
		if(((jefe.pos.y + 120 + Config.ShipSpeedPerFrame > ent.pos.y) || (jefe.pos.y+120-Config.ShipSpeedPerFrame > ent.pos.y)) && jefe.pos.x!=ent.pos.x) accion=2;
		if(jefe.pos.y + 120 < ent.pos.y) accion=1;
		
		//Lo prioritario para nuestra IA es esquivar balas, así que lo analiza al final
		for(int i=0;i<balas.getSize();i++){
			//Considera las balas a un radio de 25 de la nave
			if(balas.at(i).pos.x<ent.pos.x+25 && balas.at(i).pos.y<ent.pos.y+25 && balas.at(i).pos.y>ent.pos.x-25 && balas.at(i).pos.x>ent.pos.x-25){
				if(balas.at(i).pos.x+25 >= ent.pos.x && balas.at(i).pos.x < ent.pos.x){
					accion=3;
				}
				if(balas.at(i).pos.x+25 <= ent.pos.x && balas.at(i).pos.x > ent.pos.x){
					accion=4;
				}
				if(balas.at(i).pos.y+25  >ent.pos.y && balas.at(i).pos.y < ent.pos.y){
					accion=2;
				}
				if(balas.at(i).pos.y+25 < ent.pos.y && balas.at(i).pos.y > ent.pos.y){
					accion=1;
				}
			}
		}
		return accion;
	}
}
