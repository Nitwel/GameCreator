package de.nitwel.game;

import de.nitwel.blocks.BlockManager;

public class Game{
	
	//Werte der Klasse
    public static BlockManager blockManager;
    private String version = "version 0.1-SNAPSHOT";
    
    //erstellen des Objektes
    public Game() {
    	blockManager = new BlockManager();
        //this.licht = new GLLicht(0,1000,0);
        GLOOP.Sys.erstelleAusgabe(version);
    }
    
    public BlockManager getBlockManager(){
    	return blockManager;
    }
    
    /*public static void main(String[] args){
	    Game game = new Game();
	    new Player(new GLVektor(0,200,-200),50);
	    Map map = new Map();
	    map.addBlock(new Block(0,0,-1000,40000,2,16000,"floor.png"));
	    map.addBlock(new Block(200,400,-8000,2,800,16000,"background.png"));
	    map.addBlock(new Block(-200,400,-8000,2,800,16000,"background.png"));
	    
	    //objekte
	    map.addBlock(new Block(0,50,-500,100,100,100,"box 2.png"));
	    map.addBlock(new Block(0,100,-700,100,200,100,"box 2.png"));
	    
	    map.addBlock(new Lava(0,0,-1150,500,100,250));
	    
	    map.addBlock(new Block(0,200,-1000,500,50,200,"box 2.png"));
	    map.addBlock(new Lamp(0,350,-1000,20,20,20));
	    map.addBlock(new Box( 0, 100, -300, 100, 100, 100));
	    game.getBlockManager().loadMap(map);
	    
	}*/
}
