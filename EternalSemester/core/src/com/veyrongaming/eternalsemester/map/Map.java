package com.veyrongaming.eternalsemester.map;

public class Map {
    private final int WIDTH;
    private final int HEIGHT;
    private final Tile[][] tiles;

    public Map(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.tiles = new Tile[width][height];
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public Tile getTile(int x, int y) {
        if (isValidTileCoordinate(x, y)) {
            return tiles[x][y];
        }
        else {
            return null;
        }
    }

    public void setTile(int x, int y, Tile tile) {
        if (isValidTileCoordinate(x, y)) {
            tiles[x][y] = tile;
        }
    }

    private boolean isValidTileCoordinate(int x, int y) {
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
    }

    
}



class Tile {

}
