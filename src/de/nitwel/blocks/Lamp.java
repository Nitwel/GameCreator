package de.nitwel.blocks;

import GLOOP.GLLicht;

public class Lamp extends Block{
	
	private GLLicht light;

	public Lamp( double x, double y, double z, int width, int height, int depth) {
		super(x, y, z, width, height, depth);
		this.koerper.setzeTextur("lamp.png");
		this.light = new GLLicht(this.x,this.y-this.height/2-1,this.z);
	}
	@Override
	public String getBlockType() {
		return "LAMP";
	}
	@Override
	public void remove() {
		this.koerper.loesche();
		this.light.loesche();
	}
	public void setBrightness(double brightness){
		this.light.setzeAbschwaechung(brightness);
	}

}
