package de.nitwel.blocks;

import java.util.ArrayList;
import java.util.UUID;

import GLOOP.GLVektor;

public class BlockManager {

  ArrayList<Object> blocks = new ArrayList<>();

  public BlockManager() {

  }

  public void addBlock(Object object) {
    if (object instanceof Block) {
      ((Block) object).load();
      this.blocks.add(object);
    } else {
      throw new noBlockException(object.getClass().toString() + " must be a instance of Block");
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
    for (Object object : map.getBlocks()) {
      this.addBlock(object);
    }
  }

  public ArrayList<String> getBlockTypes(GLVektor vektor) {
    ArrayList<String> hittenBlocks = new ArrayList<>();
    for (Object object : blocks) {
      if (object instanceof Block) {
        if (((Block) object).hitsThisBlock(vektor))
          hittenBlocks.add(((Block) object).getBlockType());
      }
    }
    return hittenBlocks;
  }

  public ArrayList<Object> getBlocks(GLVektor vektor) {
    ArrayList<Object> hittenBlocks = new ArrayList<>();
    for (Object object : blocks) {
      if (object instanceof Block) {
        if (((Block) object).hitsThisBlock(vektor))
          hittenBlocks.add(object);
      }
    }
    return hittenBlocks;
  }

  public boolean hitsBlock(GLVektor vektor) {
    for (Object object : blocks) {
      if (object instanceof Block) {
        if (((Block) object).hitsThisBlock(vektor))
          return true;
      }
    }
    return false;
  }
  
  public boolean hitsBlock(GLVektor vektor, UUID id) {
    for (Object object : blocks) {
      if (object instanceof Block && ((Block) object).getUUID() != id) {
        if (((Block) object).hitsThisBlock(vektor))
          return true;
      }
    }
    return false;
  }

  public Box getBlock(GLVektor vektor) {
    for (Object block : blocks) {
      if (block instanceof Box) {
        if (((Box) block).hitsThisBlock(vektor))
          return (Box) block;
      }
    }
    return null;
  }
}
