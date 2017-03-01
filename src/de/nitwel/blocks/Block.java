package de.nitwel.blocks;

import java.util.UUID;

import GLOOP.GLQuader;
import GLOOP.GLVektor;

public class Block {
	
	//werte der Klasse
    GLQuader koerper;
    double x,y,z;
    int height, width, depth;
    private UUID uuid;
    private String image;
    
    public Block(double x, double y, double z, int width,int height,int depth,String image) {
        this.x = x; this.y = y; this.z = z;
        this.height = height; this.width = width; this.depth = depth;
        this.koerper = new GLQuader(this.x, this.y, this.z,width , height, depth);
        this.koerper.setzeTextur(image);
        this.uuid = UUID.randomUUID();
        this.image = image;
    }
    
    public UUID getUUID(){
    	return this.uuid;
    }
    
    //bewegen einer wand
    public boolean move(GLVektor vektor) {
		this.x += vektor.gibX(); this.y += vektor.gibY(); this.z += vektor.gibZ();
		this.koerper.verschiebe(vektor);
		return true;
    }
    
    public GLVektor getPosition() {
        return new GLVektor(this.x, this.y, this.z);
    }

    public boolean hitsThisBlock(GLVektor vektor) {
        if (vektor.gibX() >= this.x - (this.width / 2) && vektor.gibX() <= this.x + (this.width / 2) && vektor.gibY() >= this.y - (this.height / 2) && vektor.gibY() <= this.y + (this.height / 2) && vektor.gibZ() >= this.z -(this.depth / 2) && vektor.gibZ() <= this.z + (this.depth / 2)) {
            
        	return true;
        }
        return false;
    }
    
    public void setPosition(GLVektor vektor){
    	this.x = vektor.gibX();	this.y = vektor.gibY();	this.z = vektor.gibZ();
    	this.koerper.setzePosition(vektor);
    }
    
    public GLVektor[] getHitPoints(){
    	int h = height/2;
    	int d = depth/2;
    	int w = width/2;
    	GLVektor[] hitPoints = {new GLVektor(x,y,z),new GLVektor(x-w,y-h,z-d),new GLVektor(x-w,y-h,z+d),new GLVektor(x+w,y-h,z-d),new GLVektor(x+w,y-h,z+d)/*--*/,new GLVektor(x-w,y+h,z-d),new GLVektor(x-w,y+h,z+d),new GLVektor(x+w,y+h,z-d),new GLVektor(x+w,y+h,z+d)};
    	return hitPoints;
    }
    public String getBlockType(){
    	return "BLOCK";
    }
    public void unload(){
    	this.koerper.loesche();
    }
    
    public void load(){
      this.koerper = new GLQuader(this.x, this.y, this.z,width , height, depth);
      this.koerper.setzeTextur(this.image);
    }
    
}