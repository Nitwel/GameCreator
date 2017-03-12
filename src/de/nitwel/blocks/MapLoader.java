package de.nitwel.blocks;

import GLOOP.GLVektor;
import de.nitwel.game.Game;
import de.nitwel.game.Player;

public class MapLoader extends Teleporter {
  
  private Map map;

  public MapLoader(double x, double y, double z, int xSize, int ySize, int zSize,Map map, String image, double spawnX, double spawnY, double spawnZ) {
    super(x, y, z, xSize, ySize, zSize, image, spawnX, spawnY, spawnZ);
    this.map = map;
    
    
  }
  
  public MapLoader(GLVektor position, GLVektor size,Map map ,String image, GLVektor spawn) {
    super(position, size, image, spawn);
    this.map = map;
  }
  
  @Override
  public boolean onPlayerHitBlock(Player player, GLVektor vektor) {
    
      Game.blockManager.loadMap(this.map);
      player.setPosition(new GLVektor(xSize,ySize,zSize));
    
    return true;
  }
}
