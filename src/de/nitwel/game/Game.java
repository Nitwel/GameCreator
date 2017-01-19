package de.nitwel.game;

import GLOOP.GLHimmel;
import GLOOP.GLLicht;
import de.nitwel.blocks.Block;
import de.nitwel.blocks.BlockManager;
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
	    new Player(0,2000,200,50,50,50);
	    Map map = new Map();
	    map.addBlock(new Block(0,0,0,1000,1000,1000,"box_white.png"));
	    map.addBlock(new Block(0,0,1000,1000,1000,1000,"box_white.png"));
	    map.addBlock(new Block(0,0,2000,1000,1000,1000,"box_white.png"));
	    
	    map.addBlock(new Block(1000,1000,1000,1000,1000,1000,"box_white.png"));
	    map.addBlock(new Block(-1000,1000,1000,1000,1000,1000,"box_white.png"));
	    map.addBlock(new Block(1000,1000,2000,1000,1000,1000,"box_white.png"));
	    map.addBlock(new Block(-1000,1000,2000,1000,1000,1000,"box_white.png"));
	    
	    Game.blockManager.loadMap(map);
	    
	}
}
