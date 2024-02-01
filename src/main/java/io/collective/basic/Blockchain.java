package io.collective.basic;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Blockchain {
    private final ArrayList<Block> blockchain;

    public Blockchain() {
        blockchain = new ArrayList<>();
    }
    public boolean isEmpty() { return blockchain.isEmpty(); }

    public void add(Block block) { blockchain.add(block); }

    public int size() {
        return blockchain.size();
    }

    public boolean isValid() throws NoSuchAlgorithmException {
        if (blockchain.isEmpty()) return true;

        String previousHash = blockchain.getFirst().getPreviousHash();

        for (Block block : blockchain) {
            if (!isMined(block) ||
                !block.getHash().equals(block.calculatedHash()) ||
                !block.getPreviousHash().equals(previousHash)) {
                return false;
            }
            previousHash = block.getHash();
        }
        return true;
    }

    /// Supporting functions that you'll need.

    public static Block mine(Block block) throws NoSuchAlgorithmException {
        Block mined = new Block(block.getPreviousHash(), block.getTimestamp(), block.getNonce());

        while (!isMined(mined)) {
            mined = new Block(mined.getPreviousHash(), mined.getTimestamp(), mined.getNonce() + 1);
        }
        return mined;
    }

    public static boolean isMined(Block minedBlock) {
        return minedBlock.getHash().startsWith("00");
    }
}