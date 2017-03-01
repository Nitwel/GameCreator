package de.nitwel.blocks;

import GLOOP.GLVektor;
import de.nitwel.game.Game;

public class Box extends Block {

  private double gravity = 0.1;
  private double gravitation;
  private int gameSpeed = 60;
  private Thread thread;

  public Box(double x, double y, double z, int height, int width, int depth, String image) {
    super(x, y, z, height, width, depth, image);

    thread = new Thread(new Runnable() {

      @Override
      public void run() {
        while (true) {

          if (!hitsBlock(new GLVektor(0, -gravitation - 1, 0))) {
            gravitation += gravity;
            move(new GLVektor(0, -gravitation, 0));
          } else {
            gravitation = 0;
          }

          try {
            Thread.sleep(1000 / gameSpeed);
          } catch (InterruptedException exception) {
          }
        }
      }
    });
    thread.start();
  }


  boolean hitsBlock(GLVektor vektor) {

    for (GLVektor hitVecs : this.getHitPoints()) {
      hitVecs.addiere(vektor);

      if (Game.blockManager.hitsBlock(hitVecs, this.getUUID()))
        return true;
    }

    return false;
  }

  private void gravity() {



  }

  public boolean move(GLVektor vektor) {
    if (this.hitsWallType(vektor, "BLOCK")) {
      return false;
    }
    this.x += vektor.gibX();
    this.y += vektor.gibY();
    this.z += vektor.gibZ();
    this.koerper.verschiebe(vektor);
    return true;
  }

  private boolean hitsWallType(GLVektor vektor, String blockType) {

    for (GLVektor glVektor : this.getHitPoints()) {

      glVektor.addiere(vektor);

      if (Game.blockManager.getBlockTypes(glVektor).contains(blockType)) {

        for (Object block : Game.blockManager.getBlocks(glVektor)) {
          if (block instanceof Block && !((Block) block).getUUID().equals(this.getUUID())) {
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
  public void unload() {
    thread.interrupt();
    super.unload();
  }
}
