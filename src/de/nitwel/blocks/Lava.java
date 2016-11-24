package de.nitwel.blocks;

public class Lava extends Block{

	public Lava( double x, double y, double z, int width, int height, int depth) {
		super( x, y, z, width, height, depth);
		this.koerper.setzeTextur("lava.png");
	}
	@Override
	public String getBlockType() {
		return "LAVA";
	}

}
