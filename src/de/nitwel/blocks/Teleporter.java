package de.nitwel.blocks;

import GLOOP.GLVektor;
import de.nitwel.game.Player;

public class Teleporter extends Block{
	
	double spawnX,spawnY,spawnZ;
	
	public Teleporter( double x, double y, double z, int width, int height, int depth,String textur,double spawnX,double spawnY,double spawnZ) {
		super( x, y, z, width, height, depth,textur);
		this.spawnX = spawnX;this.spawnY = spawnY;this.spawnZ = spawnZ;
	}
	
	public Teleporter(GLVektor position, GLVektor size, String image, GLVektor spawn) {
      super(position,size,image);
      this.spawnX = spawn.gibX();
      this.spawnY = spawn.gibY();
      this.spawnZ = spawn.gibZ();
  }
	
	public GLVektor getTeleportLocation(){
		return new GLVektor(spawnX,spawnY,spawnZ);
	}
	
	public void setTeleportLocation(GLVektor vektor){
	  this.spawnX = vektor.gibX();
      this.spawnY = vektor.gibY();
      this.spawnZ = vektor.gibZ();
	}
	
	@Override
	public boolean onPlayerHitBlock(Player player, GLVektor vektor) {
	player.setPosition(new GLVektor(spawnX,spawnY,spawnZ));
	return true;
	}

}
