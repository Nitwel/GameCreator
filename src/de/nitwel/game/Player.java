package de.nitwel.game;

import java.util.ArrayList;
import java.util.List;

import GLOOP.GLKamera;
import GLOOP.GLQuader;
import GLOOP.GLTastatur;
import GLOOP.GLVektor;
import de.nitwel.blocks.Teleporter;

public class Player
{
	
	//Werte der Klasse
    private GLQuader koerper;
    private double x,y,z;
    private int height, width, depth;
    private double speed = 1;			//geschwindigkeit des Objektes
    private double jumpStrength = 2;	//sprungstärke des Objektes
    private double gravity = 0.015;		//die Gravitation der Welt
    private double gravitation;			//die gespeicherte und aktuelle Gravitation
    private GLKamera kamera;
    private int kameraOffSet;
    private double kameraResetSpeed = 0;
    private int minKameraRange = 100;
    private int maxKameraRange = 500;
    private int viewDirection = 0;
    private int gameSpeed = 5;
    
    //erstellen eines Objektes
    public Player(double x,double y,double z,int width,int height,int depth){
        this.x = x; this.y = y; this.z = z;this.width = width; this.height = height; this.depth = depth;
        this.koerper = new GLQuader(this.x, this.y, this.z,width , height, depth);
        this.kamera = new GLKamera(1200,1200);
        this.kamera.setzePosition(this.x,this.y,this.z-200);
        this.kameraOffSet = this.height*6;
        this.minKameraRange = this.width*this.depth/25;
        this.maxKameraRange = this.width*this.depth/5;
        this.startMoving();
    }
    
    //-------------------------------------------Methoden-------------------------------------------//
    
    public void setSpeed(double speed){
    	this.speed = speed;
    }
    
    public double getSpeed(){
    	return this.speed;
    }
    
    public void resetSpeed(){
    	this.speed = 0.5;
    }
     
    public void setGravity(double gravity){
    	this.gravity = gravity;
    }
    
    public double getGravity(){
    	return this.gravity;
    }
    
    public void resetGravity(){
    	this.gravity = 0.01;
    }
    
    public void setJumpStrength(double strength){
    	this.jumpStrength = strength;
    }
    
    public double getJumpStrength(){
    	return this.jumpStrength;
    }
    
    public void resetJumpStrength(){
    	this.jumpStrength = 2;
    }
    
    public void setGameSpeed(int speed){
    	this.gameSpeed = speed;
    }
    
    public int getGameSpeed(){
    	return this.gameSpeed;
    }
    
    public void resetGameSpeed(){
    	this.gameSpeed = 5;
    }
    
    public int getQubicSize(){
    	return this.width*this.height*this.depth;
    }
    
    public void setSize(int width,int height,int depth){
    	this.koerper.loesche();
    	this.koerper = new GLQuader(this.getPosition(), width, height, depth);
    	this.kameraOffSet = this.height*6+1;
    	this.minKameraRange = this.width*this.depth/30;
    	this.minKameraRange += 100; this.maxKameraRange += 10;
        this.maxKameraRange = this.width*this.depth/10;
    	this.width = width; this.height = height; this.depth = depth;
    }
    
    //bewegen des Objektes
    public void move(GLVektor vektor){
    	if(this.hitsWallType(vektor,"BLOCK")){
    		return;
    	}else
		if(this.hitsWallType(vektor,"TELEPORTER")){
			for(GLVektor glVektor: this.getHitPoints()){
    			glVektor.addiere(vektor);
    			if(Game.blockManager.getBlockFromLocation(glVektor)!=null&&Game.blockManager.getBlockFromLocation(glVektor) instanceof Teleporter){
    				Teleporter teleporter = (Teleporter) Game.blockManager.getBlockFromLocation(glVektor);
    				this.setPosition(teleporter.getTeleportLocation());
    			}

    		}
    		return;
    	}else
    	if(this.hitsWallType(vektor,"BOX")){
    		for(GLVektor glVektor: this.getHitPoints()){
    			glVektor.addiere(vektor);
    			if(Game.blockManager.getBlock(glVektor)!=null){
    				if(!Game.blockManager.getBlock(glVektor).move(new GLVektor(vektor.gibX(),0,vektor.gibZ())))return;
    			}

    		}
    	}
    	this.setzeKamera(vektor);
    	this.x += vektor.gibX(); this.y += vektor.gibY(); this.z += vektor.gibZ();
		this.koerper.verschiebe(vektor);
    }
    
    //gibt die HitPoints des Objektes
    public List<GLVektor> getHitPoints(){
    	List<GLVektor> hitPoints = new ArrayList<>();
    	hitPoints.add(new GLVektor(this.x-this.width/2,this.y-this.height/2,this.z-this.depth/2));
    	hitPoints.add(new GLVektor(this.x-this.width/2,this.y-this.height/2,this.z+this.depth/2));
    	hitPoints.add(new GLVektor(this.x+this.width/2,this.y-this.height/2,this.z-this.depth/2));
    	hitPoints.add(new GLVektor(this.x+this.width/2,this.y-this.height/2,this.z+this.depth/2));
    	hitPoints.add(new GLVektor(this.x-this.width/2,this.y+this.height/2,this.z-this.depth/2));
    	hitPoints.add(new GLVektor(this.x-this.width/2,this.y+this.height/2,this.z+this.depth/2));
    	hitPoints.add(new GLVektor(this.x+this.width/2,this.y+this.height/2,this.z-this.depth/2));
    	hitPoints.add(new GLVektor(this.x+this.width/2,this.y+this.height/2,this.z+this.depth/2));
    	
    	return hitPoints;
    }
    
    //gibt die Position des Objektes
    public GLVektor getPosition(){
        return new GLVektor(this.x,this.y,this.z);
    }
    
    public void setPosition(GLVektor vektor){
    	this.x = vektor.gibX();	this.y = vektor.gibY();	this.z = vektor.gibZ();
    	this.koerper.setzePosition(vektor);
    	this.kamera.setzeBlickpunkt(this.getPosition());
    	this.kamera.setzePosition(this.getKameraPosition());
    }
    
    public void setzeKamera(GLVektor vektor){
    	this.kamera.setzeBlickpunkt(this.getPosition().gibX(),this.getPosition().gibY(),this.getPosition().gibZ());
    	GLVektor kameraPos = this.kamera.gibPosition();
    	//System.out.println((kameraPos.gibX()>this.x+this.kameraRange)+"||"+(kameraPos.gibX()<this.x-this.kameraRange)+"||"+(kameraPos.gibZ()>this.z+this.kameraRange)+"||"+(kameraPos.gibZ()<this.z-this.kameraRange));
    	if(kameraPos.gibX()>this.x+this.maxKameraRange){
    		kameraPos.addiere(new GLVektor(-this.speed,0,0));
    	}else
    	if(kameraPos.gibX()<this.x-this.maxKameraRange){
    		kameraPos.addiere(new GLVektor(this.speed,0,0));
    	}else
    	if(kameraPos.gibZ()>this.z+this.maxKameraRange){
    		kameraPos.addiere(new GLVektor(0,0,-this.speed));
    	}else
    	if(kameraPos.gibZ()<this.z-this.maxKameraRange){
    		kameraPos.addiere(new GLVektor(0,0,this.speed));
    	}
    	if(this.viewDirection==1&&this.kamera.gibZ()-this.minKameraRange<this.z&&this.kamera.gibX()+minKameraRange>this.x&&this.kamera.gibX()-minKameraRange<this.x){
    		kameraPos.addiere(new GLVektor(0,0,vektor.gibZ()+1*kameraResetSpeed));
		}else
		if(this.viewDirection==3&&this.kamera.gibZ()+this.minKameraRange>this.z&&this.kamera.gibX()+minKameraRange>this.x&&this.kamera.gibX()-minKameraRange<this.x){
    		kameraPos.addiere(new GLVektor(0,0,vektor.gibZ()-1*kameraResetSpeed));
		}else
		if(this.viewDirection==0&&this.kamera.gibX()-this.minKameraRange<this.x&&this.kamera.gibZ()+minKameraRange>this.z&&this.kamera.gibZ()-minKameraRange<this.z){
    		kameraPos.addiere(new GLVektor(vektor.gibZ()+1*kameraResetSpeed,0,0));
		}else
		if(this.viewDirection==2&&this.kamera.gibX()+this.minKameraRange>this.x&&this.kamera.gibZ()+minKameraRange>this.z&&this.kamera.gibZ()-minKameraRange<this.z){
    		kameraPos.addiere(new GLVektor(vektor.gibZ()-1*kameraResetSpeed,0,0));
		}
    	
    	kameraPos.y=this.y+this.kameraOffSet;
    	this.kamera.setzePosition(kameraPos);
    }
    
    private void setMoveDirektion(){
    	GLVektor vektor = this.kamera.gibPosition();
    	vektor.subtrahiere(this.kamera.gibBlickpunkt());
    	double x = Math.abs(vektor.gibX());
    	double z = Math.abs(vektor.gibZ());
    	if(vektor.gibX()>=0&&x>z){
    		this.viewDirection = 0;
    	}else
		if(vektor.gibX()<=0&&x>z){
			this.viewDirection = 2;
    	}else
		if(vektor.gibZ()>=0&&x<z){
    		this.viewDirection = 1;
    	}else
		if(vektor.gibZ()<=0&&x<z){
			this.viewDirection = 3;
    	}
    }
    
    //gibt die Kameraposition des Objektes
    public GLVektor getKameraPosition(){
    	GLVektor vektor = this.kamera.gibPosition();
        return vektor;
    }
    
    //gibt an, ob das Objekt in der Luft ist
    public boolean inAir(double gravitation){
        if(this.y > 0&&!(hitsWallType(new GLVektor(0, gravitation, 0),"BLOCK"))&&!(hitsWallType(new GLVektor(0, gravitation, 0),"BOX")))return true;
        return false;
    }
    
    //sagt dir, ob das Objekt eine Wall berührt
    private boolean hitsWallType(GLVektor vektor,String blockType){
    	for(GLVektor glVektor: this.getHitPoints()){
    		glVektor.addiere(vektor);
    		if(Game.blockManager.getBlockTypeFromLocation(glVektor).contains(blockType))return true;
    	}
    	return false;
    }
    
    //sorgt dafür, dass das Objekt laufen kann
    private void startMoving(){
        Thread thread = new Thread("PlayerMovement"){
            
            public void run(){
                GLTastatur tastatur = new GLTastatur();
                boolean spacePressed = false;
                
                while(true){
                	setMoveDirektion();
                    if(tastatur.istGedrueckt('a')){
                    	GLVektor move = new GLVektor(0,0,0);
                    	switch (viewDirection) {
						case 1:
							move = new GLVektor(-speed,0,0);
							break;
						case 3:
							move = new GLVektor(speed,0,0);
							break;
						case 2:
							move = new GLVektor(0,0,-speed);
							break;
						case 0:
							move = new GLVektor(0,0,speed);
							break;

						default:
							break;
						}
                    	move(move);
                    }
                    if(tastatur.istGedrueckt('s')){
                    	GLVektor move = new GLVektor(0,0,0);
                    	switch (viewDirection) {
						case 2:
							move = new GLVektor(-speed,0,0);
							break;
						case 0:
							move = new GLVektor(speed,0,0);	
							break;
						case 3:
							move = new GLVektor(0,0,-speed);
							break;
						case 1:
							move = new GLVektor(0,0,speed);
							break;

						default:
							break;
						}
                    	move(move);
                    }
                    if(tastatur.istGedrueckt('w')){
                    	GLVektor move = new GLVektor(0,0,0);
                    	switch (viewDirection) {
						case 0:
							move = new GLVektor(-speed,0,0);
							break;
						case 2:
							move = new GLVektor(speed,0,0);
							break;
						case 1:
							move = new GLVektor(0,0,-speed);
							break;
						case 3:
							move = new GLVektor(0,0,speed);
							break;

						default:
							break;
						}
                    	move(move);
                    }
                    if(tastatur.istGedrueckt('d')){
                    	GLVektor move = new GLVektor(0,0,0);
                    	switch (viewDirection) {
						case 3:
							move = new GLVektor(-speed,0,0);
							break;
						case 1:
							move = new GLVektor(speed,0,0);
							break;
						case 0:
							move = new GLVektor(0,0,-speed);
							break;
						case 2:
							move = new GLVektor(0,0,speed);
							break;

						default:
							break;
						}
                    	move(move);
                    }
                    
                    if(tastatur.istGedrueckt('q')){
                    	GLVektor vektor = getKameraPosition();
                    	vektor.rotiere(1, getPosition());
                    	kamera.setzePosition(vektor.gibX(),getKameraPosition().gibY(),vektor.gibZ());
                    	
                    }
                    
                    if(tastatur.istGedrueckt('e')){
                    	GLVektor vektor = getKameraPosition();
                    	vektor.rotiere(-1, getPosition());
                    	kamera.setzePosition(vektor.gibX(),getKameraPosition().gibY(),vektor.gibZ());
                    }
                    
                    if(tastatur.istGedrueckt('o')){
                    	setSize(width+1, height+1, depth+1);
                    }
                    
                    if(tastatur.istGedrueckt('l')){
                    	setSize(width-1, height-1, depth-1);
                    }
                    
                    if(tastatur.istGedrueckt('i'))System.out.println("Location: "+(int)x+":"+(int)y+":"+(int)z);
                    if(tastatur.istGedrueckt(' ')&&spacePressed == false){
                        move(new GLVektor(0,1,0));
                        gravitation = jumpStrength;
                        spacePressed = true;
                    }
                    //Gravitation
                    if(inAir(gravitation)){
                        move(new GLVektor(0.0,gravitation,0.0));
                        gravitation -= gravity;
                        spacePressed = true;
                    }else{
                        gravitation = 0;
                        spacePressed = false;
                    }
                    
                    if(!tastatur.istGedrueckt(' ')&&!tastatur.istGedrueckt('a')&&!tastatur.istGedrueckt('s')&&!tastatur.istGedrueckt('w')&&!tastatur.istGedrueckt('d')){
                    	kameraResetSpeed = 0.05*(width*depth/1000);
                    }else{
                    	kameraResetSpeed = 0.02*(width*depth/1000);
                    }
                    switch (viewDirection) {
					case 0:
						if(kamera.gibZ()>z+2){
							kamera.verschiebe(0, 0, -kameraResetSpeed);
						}else
						if(kamera.gibZ()<z-2){
							kamera.verschiebe(0, 0, kameraResetSpeed);
						}
						break;
					case 1:
						if(kamera.gibX()>x+2){
							kamera.verschiebe(-kameraResetSpeed, 0, 0);
						}else
						if(kamera.gibX()<x-2){
							kamera.verschiebe(kameraResetSpeed, 0, 0);
						}
						break;
					case 2:
						if(kamera.gibZ()>z+2){
							kamera.verschiebe(0, 0, -kameraResetSpeed);
						}else
						if(kamera.gibZ()<z-2){
							kamera.verschiebe(0, 0, kameraResetSpeed);
						}
						break;
					case 3:
						if(kamera.gibX()>x+2){
							kamera.verschiebe(-kameraResetSpeed, 0, 0);
						}else
						if(kamera.gibX()<x-2){
							kamera.verschiebe(kameraResetSpeed, 0, 0);
						}
						break;
					default:
						break;
					}
                   
                    
                    try{Thread.sleep(5);}catch(InterruptedException exception){}
                }
            }
        };
        thread.start();
    }
}

