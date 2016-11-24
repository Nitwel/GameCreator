package de.nitwel.game;

import GLOOP.GLKamera;
import GLOOP.GLQuader;
import GLOOP.GLTastatur;
import GLOOP.GLVektor;

public class Player
{
	
	//Werte der Klasse
    private GLQuader koerper;
    private double x,y,z;
    private int size;
    private double speed = 0.2;
    private double kameraResetSpeed = 0;
    private double jumpStrength = 0.5;
    private double gravity = 0.001;
    private double gravitation;
    private GLKamera kamera;
    private int minKameraRange = 100;
    private int maxKameraRange = 500;
    private int viewDirection = 0;
    
    //erstellen eines Objektes
    public Player(GLVektor vektor,int size){
        this.x = vektor.gibX(); this.y = vektor.gibY(); this.z = vektor.gibZ(); this.size = size;
        this.koerper = new GLQuader(this.x,this.y,this.z,this.size,this.size,this.size);
        this.kamera = new GLKamera(1200,1200);
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
    	this.speed = 0.2;
    }
     
    public void setGravity(double gravity){
    	this.gravity = gravity;
    }
    
    public double getGravity(){
    	return this.gravity;
    }
    
    public void resetGravity(){
    	this.gravity = 0.001;
    }
    
    public void setJumpStrength(double strength){
    	this.jumpStrength = strength;
    }
    
    public double getJumpStrength(){
    	return this.jumpStrength;
    }
    
    public void resetJumpStrength(){
    	this.jumpStrength = 0.5;
    }
    
    //bewegen des Objektes
    public void move(GLVektor vektor){
    	if(this.hitsWallType(vektor,"BLOCK")){
    		return;
    	}else
		if(this.hitsWallType(vektor,"LAVA")){
			this.setPosition(new GLVektor(0,100,-100));
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
    public GLVektor[] getHitPoints(){
    	int s = this.size/2;
    	GLVektor[] hitPoints = {new GLVektor(x,y,z),new GLVektor(x-s,y-s,z-s),new GLVektor(x-s,y-s,z+s),new GLVektor(x+s,y-s,z-s),new GLVektor(x+s,y-s,z+s)/*--*/,new GLVektor(x-s,y+s,z-s),new GLVektor(x-s,y+s,z+s),new GLVektor(x+s,y+s,z-s),new GLVektor(x+s,y+s,z+s)};
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
    	System.out.println(viewDirection);
    	if(this.viewDirection==1&&this.kamera.gibZ()-this.minKameraRange<this.z&&this.kamera.gibX()+minKameraRange>this.x&&this.kamera.gibX()-minKameraRange<this.x){
    		kameraPos.addiere(new GLVektor(0,0,vektor.gibZ()+1));
		}else
		if(this.viewDirection==3&&this.kamera.gibZ()+this.minKameraRange>this.z&&this.kamera.gibX()+minKameraRange>this.x&&this.kamera.gibX()-minKameraRange<this.x){
    		kameraPos.addiere(new GLVektor(0,0,vektor.gibZ()-1));
		}else
		if(this.viewDirection==0&&this.kamera.gibX()-this.minKameraRange<this.x&&this.kamera.gibZ()+minKameraRange>this.z&&this.kamera.gibZ()-minKameraRange<this.z){
    		kameraPos.addiere(new GLVektor(vektor.gibZ()+1,0,0));
		}else
		if(this.viewDirection==2&&this.kamera.gibX()+this.minKameraRange>this.x&&this.kamera.gibZ()+minKameraRange>this.z&&this.kamera.gibZ()-minKameraRange<this.z){
    		kameraPos.addiere(new GLVektor(vektor.gibZ()-1,0,0));
		}
    	
    	kameraPos.y=this.y+300;
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
                while(!tastatur.esc()){
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
                    	kameraResetSpeed = 0.05;
                    }else{
                    	kameraResetSpeed = 0.02;
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
                   
                    
                    try{Thread.sleep(1);}catch(InterruptedException exception){}
                }
                while (tastatur.esc()) {
                	 try{Thread.sleep(1);}catch(InterruptedException exception){}
				}
            }
        };
        thread.start();
    }
}

