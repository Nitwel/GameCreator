package de.nitwel.game;

import GLOOP.GLHimmel;
import GLOOP.GLLicht;
import GLOOP.GLVektor;
import de.nitwel.blocks.Block;
import de.nitwel.blocks.BlockManager;
import de.nitwel.blocks.Box;
import de.nitwel.blocks.Lamp;
import de.nitwel.blocks.Lava;
import de.nitwel.blocks.Map;

public class Game{
	
	//Werte der Klasse
    public static BlockManager blockManager;
    private String version = "version 0.1-SNAPSHOT";
    private GLHimmel himmel;
    private GLLicht licht;
    
    //erstellen des Objektes
    public Game() {
    	blockManager = new BlockManager();
    	 himmel = new GLHimmel("sky.png");
    	 licht = new GLLicht(1000,3000,-3000);
        GLOOP.Sys.erstelleAusgabe(version);
    }
    
    public BlockManager getBlockManager(){
    	return blockManager;
    }
    
    public static void main(String[] args){
	    Game game = new Game();
	    new Player(new GLVektor(0,200,-200),50,50,50);
	    Map map = new Map();
	    map.addBlock(new Block(0,0,0,1000,10,10000,"floor.png"));
	    
	    map.addBlock(new Block(0, 0, -500, 500, 500, 500,"box 2.png"));
	    map.addBlock(new Block(0, 250, -1000, 1000, 500, 1000,"box 2.png"));
	    Game.blockManager.loadMap(map);
	    
	}
}
