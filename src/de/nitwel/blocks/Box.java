package de.nitwel.blocks;

import GLOOP.GLVektor;
import de.nitwel.game.Game;
import de.nitwel.game.Player;

public class Box extends Block {

  private double gravitation = 0.1;
  private double objectGravity;
  private int gameSpeed = 120;
  private Thread thread;

  public Box(double x, double y, double z, int xSize, int ySize, int zSize, String image) {
    super(x, y, z, xSize, ySize, zSize, image);
    this.create();
  }
  
  public Box(GLVektor position, GLVektor size, String image) {
    super(position,size, image);
    this.create();
  }
  
  public Box(GLVektor position, double size, String image) {
    super(position,size, image);
    this.create();
  }

  public boolean hitsBlock(GLVektor vektor) {

    for (GLVektor hitVecs : this.getHitPoints()) {
      hitVecs.addiere(vektor);

      if (Game.blockManager.hitsOtherBlock(hitVecs,this))
        return true;
    }

    return false;
  }

  public boolean move(GLVektor vektor) {
    if (this.hitsBlock(vektor)) {
      return false;
    }
    this.x += vektor.gibX();
    this.y += vektor.gibY();
    this.z += vektor.gibZ();
    this.koerper.verschiebe(vektor);
    return true;
  }

  @Override
  public boolean onPlayerHitBlock(Player player, GLVektor vektor) {
    this.move(vektor);
    return true;
  }

  @Override
  public void unload() {
    thread.interrupt();
    super.unload();
  }
  
  @Override
  public void load() {
    thread.start();
    super.load();
  }
  
  private void gravity() {

    if (!this.hitsBlock(new GLVektor(0, -this.objectGravity, 0))) {
      this.objectGravity += this.gravitation;
      this.move(new GLVektor(0, -this.objectGravity, 0));
    } else {
      this.objectGravity = 0;
    }

  }
  
  private void create(){
    thread = new Thread(new Runnable() {

      @Override
      public void run() {
        while (true) {
          gravity();
          try {
            Thread.sleep(1000 / gameSpeed);
          } catch (InterruptedException exception) {
          }
        }
      }
    });
  }
}
