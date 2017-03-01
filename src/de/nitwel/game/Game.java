package de.nitwel.game;

import GLOOP.GLHimmel;
import GLOOP.GLLicht;
import de.nitwel.blocks.Block;
import de.nitwel.blocks.BlockManager;
import de.nitwel.blocks.Map;

public class Game{
	
	
    public static BlockManager blockManager;
    public static int gameSpeed = 60;
    
    //erstellen des Objektes
    public Game() {
    	blockManager = new BlockManager();
    	 
    }
    
    public static void main(String[] args){
	    Game game = new Game();
	    
	    new GLHimmel("sky.png");
        new GLLicht(1000,3000,-3000);
	    //Player player2 = new Player(0,200,200,50,50,50);
	    //char[] player2inputs = {'4','6','8','2','0'};
	    //player2.setKeyInputs(player2inputs);
	    Map map = new Map();
	    map.addBlock(new Block(0,-25,0, 2000, 50, 2000 ,"box_white.png"));
	    
	    map.addBlock(new Block(0, 100,300,100,100,100,"box_white.png"));
	    map.addBlock(new Block(400,100,0, 200,200,200,"box_white.png"));
	    
	    Player player = new Player(0,200,0,50,50,50,"lamp.png");
	    
	    Game.blockManager.loadMap(map);
	    
	}
}
