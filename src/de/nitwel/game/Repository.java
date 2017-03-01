package de.nitwel.game;

import GLOOP.GLVektor;

public class Repository {

	public static boolean inRadius(double distance, GLVektor pos1, GLVektor pos2){
		
	  double momDistance = Math.sqrt(Math.pow(pos2.gibX() - pos1.gibX(), 2)+Math.pow(pos2.gibZ() - pos1.gibZ(), 2));
		if(momDistance < distance)return true;
		
		return false;
	}
	
	public static GLVektor rotate(double degree, GLVektor pos1, GLVektor pos2){
	  
	  double xDis = pos2.gibX() - pos1.gibX();
	  double zDis = pos2.gibZ() - pos1.gibZ();
	  
	  double distance = Math.sqrt(Math.pow(xDis, 2)+Math.pow(zDis, 2));
	  distance = zDis>0?distance:-distance;
	  
	  double calcDegree = Math.toDegrees(Math.atan(xDis/zDis));
	  
	  double xRot = Math.sin(Math.toRadians(degree + calcDegree)) * distance;
	  double zRot = Math.cos(Math.toRadians(degree + calcDegree)) * distance;
	  
	  GLVektor rotLoc = new GLVektor(pos1.gibX()+xRot, pos2.gibY(), pos1.gibZ() + zRot);
	  //System.out.println("xDis: " + (int) xDis + " zDis: " + (int) zDis + " Winkel: " + (int) calcDegree + " Xadd: " + (int) xRot + " Zadd: " + (int) zRot);
	  return rotLoc;
	}
	
}
