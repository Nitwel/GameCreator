package de.nitwel.blocks;

import java.util.ArrayList;

import GLOOP.GLVektor;

public class BlockManager {

  //----------------------------- Instanzen -----------------------------
  
  private ArrayList<Block> blocks = new ArrayList<>();

  //----------------------------- Konstruktor -----------------------------
  
  public BlockManager() {
    
  }
  
  //----------------------------- get Methoden -----------------------------
  
  public ArrayList<Block> getBlocks() {
    return this.blocks;
  }

  public ArrayList<Block> getBlocks(GLVektor vektor) {
    ArrayList<Block> hittenBlocks = new ArrayList<>();
    for (Block block : blocks) {
      if(block.hitsThisBlock(vektor))hittenBlocks.add(block);
    }
    return hittenBlocks;
  }

  public boolean hitsBlock(GLVektor vektor) {
    for (Block block : blocks) {
      if(block.hitsThisBlock(vektor))return true;
    }
    return false;
  }
  
  public boolean hitsBlock(GLVektor vektor, Block searchedBlock) {
    for (Block block : blocks) {
      if(block == searchedBlock && block.hitsThisBlock(vektor))return true;
    }
    return false;
  }
  
  public boolean hitsOtherBlock(GLVektor vektor, Block executedBlock) {
    for (Block block : blocks) {
      if(block != executedBlock && block.hitsThisBlock(vektor))return true;
    }
    return false;
  }

  //----------------------------- set Methoden -----------------------------
  
  public void addBlock(Block block) {
    block.load();
    blocks.add(block);
  }

  public void removeBlock(Block block){
    if(this.blocks.contains(block)){
      block.unload();
      blocks.remove(block);
    }
  }
  
  public void removeBlocks() {
    for (Object object : blocks) {
      if (object instanceof Block) {
        ((Block) object).unload();
        object = null;
      }
    }
    blocks.clear();
  }

  public void loadMap(Map map) {
    this.removeBlocks();
    for (Block block : map.getBlocks()) {
      this.addBlock(block);
    }
  }
  

}
