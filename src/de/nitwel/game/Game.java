package de.nitwel.game;

import GLOOP.GLHimmel;
import GLOOP.GLLicht;
import de.nitwel.blocks.Block;
import de.nitwel.blocks.BlockManager;
import de.nitwel.blocks.Box;
import de.nitwel.blocks.JumpPad;
import de.nitwel.blocks.Map;
import de.nitwel.blocks.Teleporter;

public class Game{
	
	
    public static BlockManager blockManager;
    public static int gameSpeed = 60;
    
    //erstellen des Objektes
    public Game() {
    	blockManager = new BlockManager();
    	 
    }
    
    public static void main(String[] args){
	    new Game();
	    
	    new GLHimmel("images/sky.png");
        new GLLicht(1000,3000,-3000);
        
	    Map map = new Map();
	    map.addBlock(new Block(0,-25,0, 2000, 50, 2000 ,"images/box_white.png"));
	    
	    Box block1 = new Box(400,400,0, 100, 50,100,"images/box_white.png");
	    map.addBlock(block1);
	    
	    map.addBlock(new JumpPad(-300, 50,- 300, 100, 50, 100 , 7, "images/wall.png"));
	    
	    map.addBlock(new Teleporter(300, 50, -300, 100, 100, 100, "images/wall.png", 300, 500, -300));
	    
	    new Player(0,300,0,50,50,50,"images/lamp.png");
	    
	    Game.blockManager.loadMap(map);
	    
	}
}
