package de.nitwel.game;

import java.util.ArrayList;
import java.util.UUID;

import GLOOP.GLKamera;
import GLOOP.GLLicht;
import GLOOP.GLQuader;
import GLOOP.GLTastatur;
import GLOOP.GLVektor;
import de.nitwel.blocks.Block;

public class Player {
  
  //----------------------------- Instanzen -----------------------------

  double x, y, z, xSize, ySize, zSize;
  String image;
  int viewDirection;
  double gravitation = 0.1;
  double speed = 1;
  int jumpStrength = 5;
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
  
  //----------------------------- Konstruktor -----------------------------

  public Player(double x, double y, double z, double xSize, double ySize, double zSize,
      String image) {

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

    this.create();

  }

  public Player(GLVektor position, GLVektor size, String image) {

    this.x = position.gibX();
    this.y = position.gibY();
    this.z = position.gibZ();
    this.xSize = size.gibX();
    this.ySize = size.gibY();
    this.zSize = size.gibZ();
    this.image = image;
    this.quader = new GLQuader(x, y, z, xSize, ySize, zSize);
    this.quader.setzeTextur(this.image);
    this.kamera = new GLKamera();
    new GLLicht();
    this.kamera.setzePosition(x - 100, y + 100, z);
    this.kamera.setzeBlickpunkt(x, y, z);

    this.create();

  }
  
  public Player(GLVektor position, double size, String image) {

    this.x = position.gibX();
    this.y = position.gibY();
    this.z = position.gibZ();
    this.xSize = size;
    this.ySize = size;
    this.zSize = size;
    this.image = image;
    this.quader = new GLQuader(x, y, z, xSize, ySize, zSize);
    this.quader.setzeTextur(this.image);
    this.kamera = new GLKamera();
    new GLLicht();
    this.kamera.setzePosition(x - 100, y + 100, z);
    this.kamera.setzeBlickpunkt(x, y, z);

    this.create();

  }

  //----------------------------- get Methoden -----------------------------
  
  public GLVektor getPosition() {
    return new GLVektor(this.x, this.y, this.z);
  }
  
  public GLVektor getSize() {
    return new GLVektor(this.xSize, this.ySize, this.zSize);
  }
  
  public int getGameSpeed() {
    return this.gameSpeed;
  }
  
  public boolean hitsBlock(GLVektor vektor) {

    for (GLVektor hitVecs : this.getHitBox()) {
      hitVecs.addiere(vektor);

      if (Game.blockManager.hitsBlock(hitVecs))
        return true;
    }

    return false;
  }
  
  public boolean hitsBlock(UUID uuid) {

    for (GLVektor hitVecs : this.getHitBox()) {

      if (Game.blockManager.hitsBlock(hitVecs, uuid));
        return true;
    }

    return false;
  }

  public ArrayList<GLVektor> getHitBox() {

    ArrayList<GLVektor> hitPoints = new ArrayList<>();

    for (double x = (this.x - this.xSize / 2); x <= (this.x + this.xSize / 2); x +=
        this.xSize / 10) {

      for (double y = (this.y - this.ySize / 2); y <= (this.y + this.ySize / 2); y +=
          this.ySize / 10) {

        for (double z = (this.z - this.zSize / 2); z <= (this.z + this.zSize / 2); z +=
            this.zSize / 10) {

          hitPoints.add(new GLVektor(x, y, z));

        }
      }
    }
    return hitPoints;

  }

  public int getViewDirection() {
    return viewDirection;
  }
  
  public double getSpeed(){
    return this.speed;
  }
  
  public double getGravitation() {
    return gravitation;
  }
  
  public int getJumpStrength() {
    return jumpStrength;
  }
  
  //----------------------------- set Methoden -----------------------------
  
  public void setSize(double xSize, double ySize, double zSize) {
    this.xSize = xSize;
    this.ySize = ySize;
    this.zSize = zSize;

    this.quader.loesche();

    this.quader = new GLQuader(x, y, z, xSize, ySize, zSize);
    this.quader.setzeTextur(this.image);
  }

  public void setGameSpeed(int speed) {
    this.gameSpeed = speed;
  }

  public boolean setKeyInputs(char[] inputs) {
    if (inputs.length == this.keyInputs.length) {
      this.keyInputs = inputs;
      return true;
    }
    return false;
  }

  public void move(GLVektor vektor) {
    
    boolean hitBlock = false;
    
    for (GLVektor hitVecs : this.getHitBox()) {
      
      hitVecs.addiere(vektor);
      for(Block block: Game.blockManager.getBlocks(hitVecs)){
        hitBlock = hitBlock? true : block.onPlayerHitBlock(this, vektor);
      }
      
    }
    
    if(hitBlock)return;
    
    this.x += vektor.gibX();
    this.y += vektor.gibY();
    this.z += vektor.gibZ();

    this.quader.verschiebe(vektor);
    this.kamera.setzeBlickpunkt(this.x, this.y, this.z);
    this.updateKamera(vektor);
    this.updateViewDirection();

  }

  public void Jump(int strenght) {

    if (this.hitsBlock(new GLVektor(0, -1, 0))) {
      this.playerGravity = -strenght;
      this.move(new GLVektor(0, 1, 0));
    }

  }
  
  public void setSpeed(double speed) {
    this.speed = speed;
  }
  
  public void setGravitation(double gravitation) {
    this.gravitation = gravitation;
  }
  
  public void setJumpStrength(int jumpStrength) {
    this.jumpStrength = jumpStrength;
  }
  
  public void setPosition(GLVektor location) {

    this.x = location.gibX();
    this.y = location.gibY();
    this.z = location.gibZ();
    this.quader.setzePosition(location);
    this.kamera.setzePosition(location.gibX() + 100, location.gibY() + this.kameraHight,
        location.gibZ());

  }
  
  //----------------------------- private Methoden -----------------------------

  private void gravity() {

    if (!this.hitsBlock(new GLVektor(0, -this.playerGravity, 0))) {
      this.playerGravity += this.gravitation;
      this.move(new GLVektor(0, -this.playerGravity, 0));
    } else {
      this.playerGravity = 0;
    }

  }

  private void onCommand(String message) {
    if (message.startsWith("/")) {

      String[] args = message.split(" ");

      if (args[0].equalsIgnoreCase("/speed") && args.length == 2) {
        speed = Double.parseDouble(args[1]);
        return;
      }

      if (args[0].equalsIgnoreCase("/gravity") && args.length == 2) {
        this.gravitation = Double.parseDouble(args[1]);
        return;
      }

      if (args[0].equalsIgnoreCase("/size")) {

        if (args.length == 2) {
          int size = Integer.parseInt(args[1]);
          this.setSize(size, size, size);
          return;
        }

        if (args.length == 4) {
          int xSize = Integer.parseInt(args[1]);
          int ySize = Integer.parseInt(args[2]);
          int zSize = Integer.parseInt(args[3]);
          this.setSize(xSize, ySize, zSize);
        }
        return;
      }

      if (args[0].equalsIgnoreCase("/pos")) {

        if (args.length == 1) {
          System.out.println("X: " + (int) this.x + " Y: " + (int) this.y + " Z: " + (int) this.z);
          return;
        }
        return;
      }

      if (args[0].equalsIgnoreCase("/tp") && args.length == 4) {
        double xPos = Double.parseDouble(args[1]);
        double yPos = Double.parseDouble(args[2]);
        double zPos = Double.parseDouble(args[3]);

        this.playerGravity = 0;

        setPosition(new GLVektor(xPos, yPos, zPos));

        return;
      }
    }

  }

  private void create() {
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

          if (tastatur.istGedrueckt(keyInputs[5])) {

            GLVektor kameraPos =
                Repository.rotate(kameraRotationSpeed, getPosition(), kamera.gibPosition());
            kamera.setzePosition(kameraPos);

          }

          if (tastatur.istGedrueckt(keyInputs[6])) {
            GLVektor kameraPos =
                Repository.rotate(-kameraRotationSpeed, getPosition(), kamera.gibPosition());
            kamera.setzePosition(kameraPos);

          }

          gravity();

          if (tastatur.istGedrueckt(keyInputs[4])) {
            Jump(jumpStrength);
          }


          if (tastatur.enter() && enterPressed == false) {
            String message = GLOOP.Sys.erwarteEingabe("Command");
            onCommand(message);

            enterPressed = true;
          } else if (!tastatur.enter()) {
            enterPressed = false;
          }

          try {
            Thread.sleep(1000 / gameSpeed);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }


      }
    });
    living.start();
  }

  // --------Camera--------//

  private void updateKamera(GLVektor vektor) {

    if (!Repository.inRadius(this.kameraRadiusMax, new GLVektor(this.x, this.y, this.z),
        this.kamera.gibPosition())) {
      this.kamera.verschiebe(vektor);
    }
    if (Repository.inRadius(this.kameraRadiusMin, new GLVektor(this.x, this.y, this.z),
        this.kamera.gibPosition())) {
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

}

