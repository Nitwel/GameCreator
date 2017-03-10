package de.nitwel.blocks;

import java.util.UUID;

import GLOOP.GLQuader;
import GLOOP.GLVektor;
import de.nitwel.game.Player;

public class Block {
  
  protected GLQuader koerper;
  protected double x, y, z;
  protected double xSize, ySize, zSize;
  protected UUID uuid;
  protected String image;

  public Block(double x, double y, double z, int xSize, int ySize, int zSize, String image) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.xSize = xSize;
    this.ySize = ySize;
    this.zSize = zSize;
    this.koerper = new GLQuader(this.x, this.y, this.z, xSize, ySize, zSize);
    this.koerper.setzeTextur(image);
    this.uuid = UUID.randomUUID();
    this.image = image;
    this.koerper.loesche();
  }
  
  public Block(GLVektor position, GLVektor size, String image) {
    this.x = position.gibX();
    this.y = position.gibY();
    this.z = position.gibY();
    this.xSize = size.gibX();
    this.ySize = size.gibY();
    this.zSize = size.gibZ();
    this.koerper = new GLQuader(this.x, this.y, this.z, xSize, ySize, zSize);
    this.koerper.setzeTextur(image);
    this.uuid = UUID.randomUUID();
    this.image = image;
    this.koerper.loesche();
  }
  
  public Block(GLVektor position, double size, String image) {
    this.x = position.gibX();
    this.y = position.gibY();
    this.z = position.gibY();
    this.xSize = size;
    this.ySize = size;
    this.zSize = size;
    this.koerper = new GLQuader(this.x, this.y, this.z, xSize, ySize, zSize);
    this.koerper.setzeTextur(image);
    this.uuid = UUID.randomUUID();
    this.image = image;
    this.koerper.loesche();
  }

  public UUID getUUID() {
    return this.uuid;
  }

  
  public boolean move(GLVektor vektor) {
    this.x += vektor.gibX();
    this.y += vektor.gibY();
    this.z += vektor.gibZ();
    this.koerper.verschiebe(vektor);
    return true;
  }

  public GLVektor getPosition() {
    return new GLVektor(this.x, this.y, this.z);
  }

  public boolean hitsThisBlock(GLVektor vektor) {
    if (vektor.gibX() >= this.x - (this.xSize / 2) && vektor.gibX() <= this.x + (this.xSize / 2)
        && vektor.gibY() >= this.y - (this.ySize / 2)
        && vektor.gibY() <= this.y + (this.ySize / 2) && vektor.gibZ() >= this.z - (this.zSize / 2)
        && vektor.gibZ() <= this.z + (this.zSize / 2)) {

      return true;
    }
    return false;
  }

  public void setPosition(GLVektor vektor) {
    this.x = vektor.gibX();
    this.y = vektor.gibY();
    this.z = vektor.gibZ();
    this.koerper.setzePosition(vektor);
  }

  public GLVektor[] getHitPoints() {
    int h = (int) (ySize / 2);
    int d = (int) (zSize / 2);
    int w = (int) (xSize / 2);
    GLVektor[] hitPoints = {new GLVektor(x, y, z), new GLVektor(x - w, y - h, z - d),
        new GLVektor(x - w, y - h, z + d), new GLVektor(x + w, y - h, z - d),
        new GLVektor(x + w, y - h, z + d), new GLVektor(x - w, y + h, z - d),
        new GLVektor(x - w, y + h, z + d), new GLVektor(x + w, y + h, z - d),
        new GLVektor(x + w, y + h, z + d)};
    return hitPoints;
  }
  
  public boolean onPlayerHitBlock(Player player, GLVektor vektor) {
    return true;
  }

  public void unload() {
    this.koerper.loesche();
  }

  public void load() {
    this.koerper = new GLQuader(this.x, this.y, this.z, xSize, ySize, zSize);
    this.koerper.setzeTextur(this.image);
  }

}
