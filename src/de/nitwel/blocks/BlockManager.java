package de.nitwel.blocks;

import java.util.ArrayList;
import java.util.List;

import GLOOP.GLVektor;


public class BlockManager {
	
	ArrayList<Object> blocks = new ArrayList<>();
    
    public BlockManager() {
    	
	}
    
    public void addBlock(Object object) {
    	if(object instanceof Block){
    		this.blocks.add(object);
    	}else{
    		throw new noBlockException(object.getClass().toString()+" must be a instance of Block");
    	}
    }
    public void removeBlocks(){
    	for(Object object:blocks){
    		if(object instanceof Block){
    			((Block) object).remove();
    			object=null;
    		}
    	}
    }
    
    public void loadMap(Map map){
		this.removeBlocks();
		for(Object object:map.getBlocks()){
			this.addBlock(object);
		}
	}
    
	//gibt dir an, ob das Objekt / die Position den Körper berührt
    public ArrayList<String> getBlockTypeFromLocation(GLVektor vektor){
    	ArrayList<String> hittenBlocks = new ArrayList<>();
    	for(Object object :blocks){
			if(object instanceof Block){
				if(((Block) object).hitsBlock(vektor))hittenBlocks.add(((Block) object).getBlockType());
			}
    	}
        return hittenBlocks;
    }
    
    public List<Object> getBlockFromLocation(GLVektor vektor){
    	ArrayList<Object> hittenBlocks = new ArrayList<>();
    	for(Object object :blocks){
			if(object instanceof Block){
				if(((Block) object).hitsBlock(vektor))hittenBlocks.add(object);
			}
    	}
        return hittenBlocks;
    }
    
    public boolean hitsBlock(GLVektor vektor){
    	for(Object object:blocks){
    		if(object instanceof Block){
    			if(((Block) object).hitsBlock(vektor))return true;
    		}
    	}
        return false;
    }
    public Box getBlock(GLVektor vektor){
    	for(Object block:blocks){
    		if(block instanceof Box){
    			if(((Box) block).hitsBlock(vektor))return (Box) block;
    		}
        }
    	return null;
    }
}
