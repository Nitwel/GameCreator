package de.nitwel.game;

import java.util.ArrayList;

import GLOOP.GLKamera;
import GLOOP.GLLicht;
import GLOOP.GLQuader;
import GLOOP.GLTastatur;
import GLOOP.GLVektor;
import de.nitwel.blocks.Box;
import de.nitwel.blocks.Teleporter;

public class Player{

  double x, y, z, xSize, ySize, zSize;
  String image;
  int viewDirection;
  double gravitation = 0.1;
  double speed = 1;
  int jumpStrenght = 5;
  int kameraRadiusMax = 300;
  int kameraRadiusMin = 50;
  int kameraHight = 200;
  double kameraRotationSpeed = 1;
  double playerGravity;
  GLQuader quader;
  GLKamera kamera;
  Thread living;
  char[] keyInputs = {'a', 'd', 'w', 's', ' ', 'q', 'e'};
  int gameSpeed = 120;
  boolean enterPressed = false;

  public Player(double x, double y, double z, double xSize, double ySize, double zSize, String image) {

    this.x = x;
    this.y = y;
    this.z = z;
    this.xSize = xSize;
    this.ySize = ySize;
    this.zSize = zSize;
    this.image = image;
    this.quader = new GLQuader(x, y, z, xSize, ySize, zSize);
    this.quader.setzeTextur(this.image);
    this.kamera = new GLKamera();
    new GLLicht();
    this.kamera.setzePosition(x - 100, y + 100, z);
    this.kamera.setzeBlickpunkt(x, y, z);

    
    
    living = new Thread(new Runnable() {

      @Override
      public void run() {

        GLTastatur tastatur = new GLTastatur();

        while (true) {

          if (tastatur.istGedrueckt(keyInputs[0])) {

            switch (viewDirection) {
              case 0:
                move(new GLVektor(0, 0, -speed));
                break;
              case 1:
                move(new GLVektor(-speed, 0, 0));
                break;
              case 2:
                move(new GLVektor(0, 0, speed));
                break;
              case 3:
                move(new GLVektor(speed, 0, 0));
                break;
              default:
                break;
            }


          }

          if (tastatur.istGedrueckt(keyInputs[1])) {

            switch (viewDirection) {
              case 0:
                move(new GLVektor(0, 0, speed));
                break;
              case 1:
                move(new GLVektor(speed, 0, 0));
                break;
              case 2:
                move(new GLVektor(0, 0, -speed));
                break;
              case 3:
                move(new GLVektor(-speed, 0, 0));
                break;
              default:
                break;
            }

          }

          if (tastatur.istGedrueckt(keyInputs[2])) {

            switch (viewDirection) {
              case 0:
                move(new GLVektor(speed, 0, 0));
                break;
              case 1:
                move(new GLVektor(0, 0, -speed));
                break;
              case 2:
                move(new GLVektor(-speed, 0, 0));
                break;
              case 3:
                move(new GLVektor(0, 0, speed));
                break;
              default:
                break;
            }

          }

          if (tastatur.istGedrueckt(keyInputs[3])) {

            switch (viewDirection) {
              case 0:
                move(new GLVektor(-speed, 0, 0));
                break;
              case 1:
                move(new GLVektor(0, 0, speed));
                break;
              case 2:
                move(new GLVektor(speed, 0, 0));
                break;
              case 3:
                move(new GLVektor(0, 0, -speed));
                break;
              default:
                break;
            }

          }

          if (tastatur.istGedrueckt(keyInputs[5])){
            
            GLVektor kameraPos = Repository.rotate(kameraRotationSpeed, getPosition(), kamera.gibPosition());
            kamera.setzePosition(kameraPos);
            
          }

          if (tastatur.istGedrueckt(keyInputs[6])){
            GLVektor kameraPos = Repository.rotate(-kameraRotationSpeed, getPosition(), kamera.gibPosition());
            kamera.setzePosition(kameraPos);
            
          }
          
          gravity();

          if (tastatur.istGedrueckt(keyInputs[4])) {
            Jump(jumpStrenght);
          }

          
          if (tastatur.enter() && enterPressed == false ) {
            String message = GLOOP.Sys.erwarteEingabe("Command");
            onCommand(message);
            
            enterPressed = true;
          }else if(!tastatur.enter()){
            enterPressed = false;
          }
          
           //System.out.println("View Direction: "+viewDirection+ " X: "+(int)Player.this.x+" Y:"+(int)Player.this.y+" Z: "+(int)Player.this.z+ " Gravity: "+playerGravity);

           try {
            Thread.sleep(1000/gameSpeed);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }

      
      }
    });
    living.start();


  }


  private void onCommand(String message) {
    if(message.startsWith("/")){
      
      String[] args = message.split(" ");
      
      if(args[0].equalsIgnoreCase("/speed") && args.length == 2){
        speed = Double.parseDouble(args[1]);
        return;
      }
      
      if(args[0].equalsIgnoreCase("/gravity") && args.length == 2){
        this.gravitation = Double.parseDouble(args[1]);
        return;
      }
      
      if(args[0].equalsIgnoreCase("/size")){
        
        if(args.length == 2){
          int size = Integer.parseInt(args[1]);
          this.setSize(size, size, size);
          return;
        }
        
        if(args.length == 4){
          int xSize = Integer.parseInt(args[1]);
          int ySize = Integer.parseInt(args[2]);
          int zSize = Integer.parseInt(args[3]);
          this.setSize(xSize, ySize, zSize);
        }
        return;
      }
      
      if(args[0].equalsIgnoreCase("/tp") && args.length == 4){
        double xPos = Double.parseDouble(args[1]);
        double yPos = Double.parseDouble(args[2]);
        double zPos = Double.parseDouble(args[3]);
        
        this.playerGravity = 0;
        
        setPosition(new GLVektor(xPos, yPos, zPos));
        
        return;
      }
    }
        
  }
  
  public void setSize(double xSize, double ySize, double zSize){
    this.xSize = xSize;
    this.ySize = ySize;
    this.zSize = zSize;
    
    this.quader.loesche();
    
    this.quader = new GLQuader(x, y, z, xSize, ySize, zSize);
    this.quader.setzeTextur(this.image);
  }
  
  public GLVektor getSize(){
    return new GLVektor(this.xSize,this.ySize,this.zSize);
  }
  
  public void setGameSpeed(int speed){
    this.gameSpeed = speed;
  }
  
  public int getGameSpeed(){
    return this.gameSpeed;
  }

  public boolean setKeyInputs(char[] inputs) {
    if (inputs.length == this.keyInputs.length) {
      this.keyInputs = inputs;
      return true;
    }
    return false;
  }

  public void move(GLVektor vektor) {

    if (this.hitsBlockType(vektor, "BLOCK")) {
      return;
    }

    if (this.hitsBlockType(vektor, "TELEPORTER")) {
      for (GLVektor glVektor : this.getHitBox()) {
        glVektor.addiere(vektor);
        if (Game.blockManager.getBlocks(glVektor) != null) {
          for (Object o : Game.blockManager.getBlocks(glVektor)) {
            if (o instanceof Teleporter) {
              this.setPosition(((Teleporter) o).getTeleportLocation());
            }
          }
        }

      }
    }
    
    if (this.hitsBlockType(vektor, "BOX")) {
      for (GLVektor glVektor : this.getHitBox()) {
        glVektor.addiere(vektor);
        if (Game.blockManager.getBlocks(glVektor) != null) {
          for (Object o : Game.blockManager.getBlocks(glVektor)) {
            if (o instanceof Box) {
              if(! ((Box) o).move(vektor) )return;
            }
          }
        }

      }
    }

    this.x += vektor.gibX();
    this.y += vektor.gibY();
    this.z += vektor.gibZ();

    this.quader.verschiebe(vektor);
    this.kamera.setzeBlickpunkt(this.x, this.y, this.z);
    this.updateKamera(vektor);
    this.updateViewDirection();

  }

  private void setPosition(GLVektor location) {
    
    this.x = location.gibX(); this.y = location.gibY(); this.z = location.gibZ();
    this.quader.setzePosition(location);
    this.kamera.setzePosition(location.gibX()+100,location.gibY()+ this.kameraHight, location.gibZ());

  }

  private void gravity() {

    if (!this.hitsBlock(new GLVektor(0, -this.playerGravity , 0))) {
      this.playerGravity += this.gravitation;
      this.move(new GLVektor(0, -this.playerGravity, 0));
    } else {
      this.playerGravity = 0;
    }

  }

  public void Jump(int strenght) {

    if (this.hitsBlock(new GLVektor(0, -1, 0))) {
      this.playerGravity = -strenght;
      this.move(new GLVektor(0, 1, 0));
    }

  }

  public boolean hitsBlock(GLVektor vektor) {

    for (GLVektor hitVecs : this.getHitBox()) {
      hitVecs.addiere(vektor);

      if (Game.blockManager.hitsBlock(hitVecs))
        return true;
    }

    return false;
  }

  public boolean hitsBlockType(GLVektor vektor, String type) {

    for (GLVektor hitVecs : this.getHitBox()) {
      hitVecs.addiere(vektor);
      if (Game.blockManager.getBlockTypes(hitVecs).contains(type))
        return true;


    }

    return false;
  }

  public ArrayList<GLVektor> getHitBox() {

    ArrayList<GLVektor> hitPoints = new ArrayList<>();

    for (double x = (this.x - this.xSize / 2); x <= (this.x + this.xSize / 2); x += this.xSize/10) {

      for (double y = (this.y - this.ySize / 2); y <= (this.y + this.ySize / 2); y += this.ySize/10) {

        for (double z = (this.z - this.zSize / 2); z <= (this.z + this.zSize / 2); z += this.zSize/10) {

          hitPoints.add(new GLVektor(x, y, z));

        }
      }
    }
    return hitPoints;

  }

  // --------Camera--------//

  private void updateKamera(GLVektor vektor) {

    if (!Repository.inRadius(this.kameraRadiusMax,new GLVektor(this.x, this.y, this.z), this.kamera.gibPosition())) {
      this.kamera.verschiebe(vektor);
    }
    if (Repository.inRadius(this.kameraRadiusMin,new GLVektor(this.x, this.y, this.z), this.kamera.gibPosition())) {
      this.kamera.verschiebe(vektor);
    }
    this.kamera.setzePosition(this.kamera.gibX(), this.y + this.kameraHight,
        this.kamera.gibPosition().gibZ());

    // System.out.println("KameraX: " + vektor.gibX() + " KameraY: " + vektor.gibY() + " KameraZ: "
    // + vektor.gibZ());
  }

  private void updateViewDirection() {
    GLVektor vektor = this.kamera.gibPosition();
    vektor.subtrahiere(this.kamera.gibBlickpunkt());
    double x = Math.abs(vektor.gibX());
    double z = Math.abs(vektor.gibZ());

    if (vektor.gibX() >= 0 && x > z) {
      this.viewDirection = 2;
    } else if (vektor.gibX() <= 0 && x > z) {
      this.viewDirection = 0;
    } else if (vektor.gibZ() >= 0 && x < z) {
      this.viewDirection = 1;
    } else if (vektor.gibZ() <= 0 && x < z) {
      this.viewDirection = 3;
    }
  }

  public int getViewDirection() {
    return viewDirection;
  }

  // -------------------------------------------Methoden-------------------------------------------//


  /*
   * public void setSize(int width,int height,int depth){ this.koerper.loesche(); this.koerper = new
   * GLQuader(this.getPosition(), width, height, depth); this.kameraOffSet = this.height*6+1;
   * this.minKameraRange = this.width*this.depth/30; this.minKameraRange += 100; this.maxKameraRange
   * += 10; this.maxKameraRange = this.width*this.depth/10; this.width = width; this.height =
   * height; this.depth = depth; }
   * 
   * //bewegen des Objektes public void move(GLVektor vektor){
   * if(this.hitsWallType(vektor,"BLOCK")){ return; }else
   * if(this.hitsWallType(vektor,"TELEPORTER")){ for(GLVektor glVektor: this.getHitPoints()){
   * glVektor.addiere(vektor);
   * if(Game.blockManager.getBlockFromLocation(glVektor)!=null&&Game.blockManager.
   * getBlockFromLocation(glVektor) instanceof Teleporter){ Teleporter teleporter = (Teleporter)
   * Game.blockManager.getBlockFromLocation(glVektor);
   * this.setPosition(teleporter.getTeleportLocation()); }
   * 
   * } return; }else if(this.hitsWallType(vektor,"BOX")){ for(GLVektor glVektor:
   * this.getHitPoints()){ glVektor.addiere(vektor); if(Game.blockManager.getBlock(glVektor)!=null){
   * if(!Game.blockManager.getBlock(glVektor).move(new
   * GLVektor(vektor.gibX(),0,vektor.gibZ())))return; }
   * 
   * } } this.setzeKamera(vektor); this.x += vektor.gibX(); this.y += vektor.gibY(); this.z +=
   * vektor.gibZ(); this.koerper.verschiebe(vektor); }
   * 
   * //gibt die HitPoints des Objektes public List<GLVektor> getHitPoints(){ List<GLVektor>
   * hitPoints = new ArrayList<>(); hitPoints.add(new
   * GLVektor(this.x-this.width/2,this.y-this.height/2,this.z-this.depth/2)); hitPoints.add(new
   * GLVektor(this.x-this.width/2,this.y-this.height/2,this.z+this.depth/2)); hitPoints.add(new
   * GLVektor(this.x+this.width/2,this.y-this.height/2,this.z-this.depth/2)); hitPoints.add(new
   * GLVektor(this.x+this.width/2,this.y-this.height/2,this.z+this.depth/2)); hitPoints.add(new
   * GLVektor(this.x-this.width/2,this.y+this.height/2,this.z-this.depth/2)); hitPoints.add(new
   * GLVektor(this.x-this.width/2,this.y+this.height/2,this.z+this.depth/2)); hitPoints.add(new
   * GLVektor(this.x+this.width/2,this.y+this.height/2,this.z-this.depth/2)); hitPoints.add(new
   * GLVektor(this.x+this.width/2,this.y+this.height/2,this.z+this.depth/2));
   * 
   * return hitPoints; }
   */

  // gibt die Position des Objektes
  public GLVektor getPosition() {
    return new GLVektor(this.x, this.y, this.z);
  }

  // sorgt dafür, dass das Objekt laufen kann
  /*
   * private void startMoving(){ Thread thread = new Thread("PlayerMovement"){
   * 
   * public void run(){ GLTastatur tastatur = new GLTastatur(); boolean spacePressed = false;
   * 
   * while(true){ setMoveDirektion(); if(tastatur.istGedrueckt('a')){ GLVektor move = new
   * GLVektor(0,0,0); switch (viewDirection) { case 1: move = new GLVektor(-speed,0,0); break; case
   * 3: move = new GLVektor(speed,0,0); break; case 2: move = new GLVektor(0,0,-speed); break; case
   * 0: move = new GLVektor(0,0,speed); break;
   * 
   * default: break; } move(move); } if(tastatur.istGedrueckt('s')){ GLVektor move = new
   * GLVektor(0,0,0); switch (viewDirection) { case 2: move = new GLVektor(-speed,0,0); break; case
   * 0: move = new GLVektor(speed,0,0); break; case 3: move = new GLVektor(0,0,-speed); break; case
   * 1: move = new GLVektor(0,0,speed); break;
   * 
   * default: break; } move(move); } if(tastatur.istGedrueckt('w')){ GLVektor move = new
   * GLVektor(0,0,0); switch (viewDirection) { case 0: move = new GLVektor(-speed,0,0); break; case
   * 2: move = new GLVektor(speed,0,0); break; case 1: move = new GLVektor(0,0,-speed); break; case
   * 3: move = new GLVektor(0,0,speed); break;
   * 
   * default: break; } move(move); } if(tastatur.istGedrueckt('d')){ GLVektor move = new
   * GLVektor(0,0,0); switch (viewDirection) { case 3: move = new GLVektor(-speed,0,0); break; case
   * 1: move = new GLVektor(speed,0,0); break; case 0: move = new GLVektor(0,0,-speed); break; case
   * 2: move = new GLVektor(0,0,speed); break;
   * 
   * default: break; } move(move); }
   * 
   * if(tastatur.istGedrueckt('q')){ GLVektor vektor = getKameraPosition(); vektor.rotiere(1,
   * getPosition()); kamera.setzePosition(vektor.gibX(),getKameraPosition().gibY(),vektor.gibZ());
   * 
   * }
   * 
   * if(tastatur.istGedrueckt('e')){ GLVektor vektor = getKameraPosition(); vektor.rotiere(-1,
   * getPosition()); kamera.setzePosition(vektor.gibX(),getKameraPosition().gibY(),vektor.gibZ()); }
   * 
   * if(tastatur.istGedrueckt('o')){ setSize(width+1, height+1, depth+1); }
   * 
   * if(tastatur.istGedrueckt('l')){ setSize(width-1, height-1, depth-1); }
   * 
   * if(tastatur.istGedrueckt('i'))System.out.println("Location: "+(int)x+":"+(int)y+":"+(int)z);
   * if(tastatur.istGedrueckt(' ')&&spacePressed == false){ move(new GLVektor(0,1,0)); gravitation =
   * jumpStrength; spacePressed = true; } //Gravitation if(inAir(gravitation)){ move(new
   * GLVektor(0.0,gravitation,0.0)); gravitation -= gravity; spacePressed = true; }else{ gravitation
   * = 0; spacePressed = false; }
   * 
   * if(!tastatur.istGedrueckt('
   * ')&&!tastatur.istGedrueckt('a')&&!tastatur.istGedrueckt('s')&&!tastatur.istGedrueckt('w')&&!
   * tastatur.istGedrueckt('d')){ kameraResetSpeed = 0.05*(width*depth/1000); }else{
   * kameraResetSpeed = 0.02*(width*depth/1000); } switch (viewDirection) { case 0:
   * if(kamera.gibZ()>z+2){ kamera.verschiebe(0, 0, -kameraResetSpeed); }else if(kamera.gibZ()<z-2){
   * kamera.verschiebe(0, 0, kameraResetSpeed); } break; case 1: if(kamera.gibX()>x+2){
   * kamera.verschiebe(-kameraResetSpeed, 0, 0); }else if(kamera.gibX()<x-2){
   * kamera.verschiebe(kameraResetSpeed, 0, 0); } break; case 2: if(kamera.gibZ()>z+2){
   * kamera.verschiebe(0, 0, -kameraResetSpeed); }else if(kamera.gibZ()<z-2){ kamera.verschiebe(0,
   * 0, kameraResetSpeed); } break; case 3: if(kamera.gibX()>x+2){
   * kamera.verschiebe(-kameraResetSpeed, 0, 0); }else if(kamera.gibX()<x-2){
   * kamera.verschiebe(kameraResetSpeed, 0, 0); } break; default: break; }
   * 
   * 
   * try{Thread.sleep(5);}catch(InterruptedException exception){} } } }; thread.start(); }
   */
}

