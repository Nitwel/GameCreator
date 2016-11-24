package de.nitwel.blocks;

import java.util.ArrayList;

public class Map {
	
	private ArrayList<Object> blocks = new ArrayList<>();
	
	public Map() {
		
	}
	
	public ArrayList<Object> getBlocks(){
		return this.blocks;
	}
	
	public void addBlock(Object object) {
    	if(object instanceof Block){
    		this.blocks.add(object);
    	}else{
    		throw new noBlockException(object.getClass().toString()+" must be a instance of Block");
    	}
    }
}
