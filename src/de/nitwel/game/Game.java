package de.nitwel.game;

import GLOOP.GLHimmel;
import GLOOP.GLLicht;
import de.nitwel.blocks.Block;
import de.nitwel.blocks.BlockManager;
import de.nitwel.blocks.JumpPad;
import de.nitwel.blocks.Map;
import de.nitwel.blocks.Teleporter;

public class Game{
	
	
    public static BlockManager blockManager;
    public static int gameSpeed;
    
    //erstellen des Objektes
    public Game(int gameSpeed) {
    	blockManager = new BlockManager();
    	Game.gameSpeed = gameSpeed;
    }
    
    public static void main(String[] args){
	    new Game(60);
	    
	    new GLHimmel("images/sky.png");
        new GLLicht(1000,5000,-3000);
        
        Map map = new Map();
        Block floor1 = new Block(100, -50, 0, 1000, 100, 1000, "images/box_white.png");
        map.addBlock(floor1);
        
        Teleporter ground = new Teleporter(0, -300, 0, 10000000, 100, 10000000, "images/invisible.png", 0, 200, 0);
        map.addBlock(ground);
        
        Block block1 = new Block(100, 50, -300, 100, 100, 100, "images/box_white.png");
	    map.addBlock(block1);
	    
	    Block block2 = new Block(200, 100, -100, 100, 200, 100, "images/box_white.png");
        map.addBlock(block2);
        
        Block block3 = new Block(400, 150, 200, 100, 100, 100, "images/box_white.png");
        map.addBlock(block3);
        
        JumpPad jumpPad1 = new JumpPad(150, 250, 300, 100, 100, 100, 12, "images/box_green.png");
        map.addBlock(jumpPad1);
        
        Block block4 = new Block(-100, 550, 200, 100, 100, 100, "images/box_white.png");
        map.addBlock(block4);
        
        Block block5 = new Block(-200, 600, 0, 100, 100, 100, "images/box_white.png");
        map.addBlock(block5);
        
        Block floor2 = new Block(-900, 700, 0, 1000, 100, 1000, "images/box_white.png");
        map.addBlock(floor2);
        
        Block block6 = new Block(-1200, 800, 300, 100, 100, 100, "images/box_white.png");
        map.addBlock(block6);
        
        Block block7 = new Block(-1300, 900, 0, 100, 100, 100, "images/box_white.png");
        map.addBlock(block7);
        
        Block block8 = new Block(-1100, 950, -300, 100, 100, 100, "images/box_white.png");
        map.addBlock(block8);
        
        Block block9 = new Block(-800, 1000, -400, 100, 100, 100, "images/box_white.png");
        map.addBlock(block9);
        
        JumpPad jumpPad2 = new JumpPad(-700, 1100, -100, 100, 100, 100, 15,"images/box_green.png");
        map.addBlock(jumpPad2);
        
        Block block10 = new Block(-700, 1500, 100, 100, 100, 100, "images/box_white.png");
        map.addBlock(block10);
        
        Teleporter teleporter = new Teleporter(-700, 1500, 400, 100, 100, 100, "images/box_violet.png", -1000, 2000, 1500);
        map.addBlock(teleporter);
        
        Block floor3 = new Block(-1000, 1800, 1500, 1000, 100, 1000, "images/box_white.png");
        map.addBlock(floor3);
        
	    new Player(0, 150, 0,50,50,50,"images/lamp.png");
	    
	    Game.blockManager.loadMap(map);
	    
	}
}
