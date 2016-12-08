package de.nitwel.blocks;

import GLOOP.GLVektor;
import de.nitwel.game.Game;

public class Box extends Block{
	
	private double gravity = 0.001;
	private double gravitation;
	private Thread thread;

	public Box(double x, double y, double z, int height,int width,int depth) {
		super( x, y, z, height, width, depth);
		
		thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					if(inAir(gravitation)){
                    move(new GLVektor(0.0,gravitation,0.0));
                    gravitation -= gravity;
	                }else{
	                    gravitation = 0;
	                }
					try{Thread.sleep(1);}catch(InterruptedException exception){}
				}
			}
		});
		thread.start();
	}
	
	public boolean inAir(double gravitation){
        if(this.y > 0&&!(hitsWallType(new GLVektor(0, gravitation, 0),"BLOCK"))&&!(hitsWallType(new GLVektor(0, gravitation, 0),"BOX")))return true;
        return false;
    }
	
	public boolean move(GLVektor vektor){
    	if(this.hitsWallType(vektor,"BLOCK")){
    		return false;
    	}
    	this.x += vektor.gibX(); this.y += vektor.gibY(); this.z += vektor.gibZ();
		this.koerper.verschiebe(vektor);
		return true;
    }
	private boolean hitsWallType(GLVektor vektor,String blockType){
    	for(GLVektor glVektor: this.getHitPoints()){
    		glVektor.addiere(vektor);
    		if(Game.blockManager.getBlockTypeFromLocation(glVektor).contains(blockType)){
    			for(Object block: Game.blockManager.getBlockFromLocation(glVektor)){
    				if(block instanceof Block&&!((Block) block).getUUID().equals(this.getUUID())){
    					return true;
    				}
    			}
    		}
    	}
    	return false;
    }
	@Override
	public String getBlockType() {
		return "BOX";
	}
	@Override
	public void remove() {
		thread.interrupt();
		super.remove();
	}
}
