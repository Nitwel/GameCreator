package de.nitwel.blocks;

import java.util.ArrayList;

public class Map {

  private ArrayList<Block> blocks = new ArrayList<>();

  public ArrayList<Block> getBlocks() {
    return this.blocks;
  }

  public void addBlock(Block block) {
    block.unload();
    blocks.add(block);
  }
}
