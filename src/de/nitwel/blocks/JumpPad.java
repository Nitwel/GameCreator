package de.nitwel.blocks;

import GLOOP.GLVektor;
import de.nitwel.game.Player;

public class JumpPad extends Block {

  int jumpStrength;
  
  public JumpPad(double x, double y, double z, int xSize, int ySize, int zSize, int jumpStrength, String image) {
    super(x, y, z, xSize, ySize, zSize, image);
    this.jumpStrength = jumpStrength;
  }
  
  public JumpPad(GLVektor position, GLVektor size, int jumpStrength, String image) {
    super(position,size, image);
    this.jumpStrength = jumpStrength;
  }
  
  public JumpPad(GLVektor position, double size, int jumpStrength, String image) {
    super(position,size, image);
    this.jumpStrength = jumpStrength;
  }

  @Override
  public boolean onPlayerHitBlock(Player player, GLVektor vektor) {
    player.Jump(jumpStrength);
    return true;
  }
  
}
