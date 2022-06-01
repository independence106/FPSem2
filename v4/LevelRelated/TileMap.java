package LevelRelated;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import Blocks.Block;
import Blocks.Brick;
import Blocks.Coin;
import Blocks.Dirt;
import Blocks.Grass;
import Blocks.Lava;
import Blocks.Powerup;
import Blocks.Wood;
import Entity.Boss;
import Entity.Enemy;
import Entity.Entity;
import Entity.Kooler;
import Handlers.DriverRunner;
import Entity.*;
import Blocks.ButtonFlag;
import Settings.MapSettings;

import java.awt.*;

public class TileMap {
    public String[][] rawMap;
    public Block[][] map;

    public ArrayList<Block> rigidBlocks;
    public ArrayList<Block> nonRigidBlocks;
    public ArrayList<Enemy> enemies;
    public ArrayList<Entity> entities;

    public int startX;
    public int startY;
    // public Location start;
    // public Location end;

    public TileMap() {
        this.map = new Block[0][0];
        this.rawMap = new String[0][0];
        rigidBlocks = new ArrayList<Block>();
        nonRigidBlocks = new ArrayList<Block>();
        enemies = new ArrayList<Enemy>();
        entities = new ArrayList<Entity>();
        startX = 0;
        startY = 0;
        // this.entities = new ArrayList<>();
        // this.start = null;
        // this.end = null;
    }

    public void loadFile(String filename) {
        try {
            Scanner sc = new Scanner(new File(filename));
            ArrayList<String[]> blockMap = new ArrayList<>();
            while (sc.hasNextLine()) {
                ArrayList<String> tiles = new ArrayList<>();
                for (char tileKey : sc.nextLine().toCharArray()) {
                    tiles.add(Character.toString(tileKey));
                }
                blockMap.add(tiles.toArray(new String[0]));
            }
            this.rawMap = blockMap.toArray(new String[0][0]);
        } catch (Exception e) {
            System.out.println(e + "hey error heere");
        }
        this.map = new Block[rawMap.length][rawMap[0].length];
    }

    public boolean load() {
        for (int row = 0; row < this.rawMap.length; row++) {
            for (int col = 0; col < this.rawMap[row].length; col++) {
                Block temp = null;
                Enemy enemy = null;
                switch (rawMap[row][col]) {

                    case "G":
                        temp = new Grass(col * MapSettings.tileSize, row * MapSettings.tileSize);
                        map[row][col] = temp;
                        rigidBlocks.add(temp);

                        break;
                    case "D":
                        temp = new Dirt(col * MapSettings.tileSize, row * MapSettings.tileSize);
                        map[row][col] = temp;
                        rigidBlocks.add(temp);
                        break;
                    case "W":
                        temp = new Brick(col * MapSettings.tileSize, row * MapSettings.tileSize);
                        map[row][col] = temp;
                        rigidBlocks.add(temp);
                        break;
                    case "w":
                        temp = new Wood(col * MapSettings.tileSize, row * MapSettings.tileSize);
                        map[row][col] = temp;
                        rigidBlocks.add(temp);
                        break;

                    case "C":
                        temp = new Coin(col * MapSettings.tileSize, row * MapSettings.tileSize);
                        temp.setCol(col);
                        temp.setRow(row);
                        map[row][col] = temp;
                        nonRigidBlocks.add(temp);
                        break;
                    case "L":
                        temp =  new Lava(col * MapSettings.tileSize, row * MapSettings.tileSize);
                        map[row][col] = temp;
                        rigidBlocks.add(temp);
                        break;
                    case "P":
                        temp =  new Powerup(col * MapSettings.tileSize, row * MapSettings.tileSize);
                        map[row][col] = temp;
                        rigidBlocks.add(temp);
                        break;
                    case "B":
                        temp = new ButtonFlag(col * MapSettings.tileSize, row * MapSettings.tileSize);
                        map[row][col] = temp;
                        rigidBlocks.add(temp);
                        break;
                    case "b":
                        enemy =  new Boss(col * MapSettings.tileSize, row * MapSettings.tileSize);
                        enemies.add(enemy);
                        break;
                    case "K":
                        enemy = new Kooler(col * MapSettings.tileSize, row * MapSettings.tileSize);
                        enemies.add(enemy);
                        break;

                    case "S":
                        startX = col * MapSettings.tileSize;
                        startY = row * MapSettings.tileSize;
                        break;
                    
                    
                    case "F":
                        enemy = new Flyer(col * MapSettings.tileSize, row * MapSettings.tileSize);
                        enemies.add(enemy);

                    default:
                        break;

                }
            }

        }
        return true;
    }

    public Block getBlock(int row, int col) {
        return this.map[row][col];
    }

    public void setBlock(int row, int col, Block block) {
        map[row][col] = block;
    }

    public void draw(Graphics g, DriverRunner driver) {
        for (int row = 0; row < this.map.length; row++) {
            for (int col = 0; col < this.map[row].length; col++) {
                if (map[row][col] == null) {
                    continue;
                }
                map[row][col].draw(g, driver);
            
            }

        }

    }
}