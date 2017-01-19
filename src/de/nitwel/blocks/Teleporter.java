package de.nitwel.blocks;

import GLOOP.GLVektor;

public class Teleporter extends Block{
	
	double spawnX,spawnY,spawnZ;

	public Teleporter( double x, double y, double z, int width, int height, int depth,double spawnX,double spawnY,double spawnZ) {
		super( x, y, z, width, height, depth);
		this.spawnX = spawnX;this.spawnY = spawnY;this.spawnZ = spawnZ;
	}
	public Teleporter( double x, double y, double z, int width, int height, int depth,String textur,double spawnX,double spawnY,double spawnZ) {
		super( x, y, z, width, height, depth,textur);
		this.spawnX = spawnX;this.spawnY = spawnY;this.spawnZ = spawnZ;
	}
	@Override
	public String getBlockType() {
		return "TELEPORTER";
	}
	
	public GLVektor getTeleportLocation(){
		return new GLVektor(spawnX,spawnY,spawnZ);
	}

}
