package de.nitwel.blocks;

import GLOOP.GLObjekt;
import GLOOP.GLQuader;
import GLOOP.GLVektor;
import de.nitwel.game.Player;

public class Block {
  
  //----------------------------- Instanzen -----------------------------
  
  protected GLObjekt koerper;
  protected double x, y, z;
  protected double xSize, ySize, zSize;
  protected String image;

  //----------------------------- Konstruktor -----------------------------
  
  public Block(double x, double y, double z, int xSize, int ySize, int zSize, String image) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.xSize = xSize;
    this.ySize = ySize;
    this.zSize = zSize;
    this.koerper = new GLQuader(this.x, this.y, this.z, xSize, ySize, zSize);
    this.koerper.setzeTextur(image);
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
    this.image = image;
    this.koerper.loesche();
  }

  //----------------------------- get Methoden -----------------------------

  public GLVektor getSize() {
    return new GLVektor(this.xSize, this.ySize, this.zSize);
  }
  
  public GLVektor getPosition() {
    return new GLVektor(this.x, this.y, this.z);
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

  public boolean hitsThisBlock(GLVektor vektor) {
    if (vektor.gibX() >= this.x - (this.xSize / 2) && vektor.gibX() <= this.x + (this.xSize / 2)
        && vektor.gibY() >= this.y - (this.ySize / 2)
        && vektor.gibY() <= this.y + (this.ySize / 2) && vektor.gibZ() >= this.z - (this.zSize / 2)
        && vektor.gibZ() <= this.z + (this.zSize / 2)) {

      return true;
    }
    return false;
  }

//----------------------------- set Methoden -----------------------------
  
  public void setSize(double xSize, double ySize, double zSize) {
    this.xSize = xSize;
    this.ySize = ySize;
    this.zSize = zSize;

    this.koerper.loesche();

    this.koerper = new GLQuader(x, y, z, xSize, ySize, zSize);
    this.koerper.setzeTextur(this.image);
  }
  
  public void setImage(String image) {
    if(this.koerper != null && this.image != image){
    this.image = image;
    this.koerper.setzeTextur(image);
    }
  }
  
  public boolean move(GLVektor vektor) {
    this.x += vektor.gibX();
    this.y += vektor.gibY();
    this.z += vektor.gibZ();
    this.koerper.verschiebe(vektor);
    return true;
  }

  public void setPosition(GLVektor vektor) {
    this.x = vektor.gibX();
    this.y = vektor.gibY();
    this.z = vektor.gibZ();
    this.koerper.setzePosition(vektor);
  }

  public void unload() {
    this.koerper.loesche();
  }

  public void load() {
    this.koerper = new GLQuader(this.x, this.y, this.z, xSize, ySize, zSize);
    this.koerper.setzeTextur(this.image);
  }

  //----------------------------- event Methoden -----------------------------
    
  public boolean onPlayerHitBlock(Player player, GLVektor vektor) { 
    return true;
  }

  
}
