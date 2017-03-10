package de.nitwel.blocks;

import java.util.ArrayList;
import java.util.UUID;

import GLOOP.GLVektor;

public class BlockManager {

  ArrayList<Block> blocks = new ArrayList<>();

  public BlockManager() {

  }

  public void addBlock(Block block) {
    block.load();
    blocks.add(block);
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
  
  public boolean hitsBlock(GLVektor vektor, UUID id) {
    for (Block block : blocks) {
      if(block.getUUID() == id && block.hitsThisBlock(vektor))return true;
    }
    return false;
  }
  
  public boolean hitsOtherBlock(GLVektor vektor, Block executedBlock) {
    for (Block block : blocks) {
      if(block != executedBlock && block.hitsThisBlock(vektor))return true;
    }
    return false;
  }
}
