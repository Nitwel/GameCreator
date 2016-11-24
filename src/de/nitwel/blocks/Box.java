package de.nitwel.blocks;

import GLOOP.GLVektor;
import de.nitwel.game.Game;

public class Box extends Block{

	public Box(double x, double y, double z, int height,int width,int depth) {
		super( x, y, z, height, width, depth);
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
    		if(Game.blockManager.getBlockTypeFromLocation(glVektor).contains(blockType))return true;
    	}
    	return false;
    }
	@Override
	public String getBlockType() {
		return "BOX";
	}
}
