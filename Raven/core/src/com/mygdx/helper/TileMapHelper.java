package com.mygdx.helper;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TileMapHelper {
    private TiledMap tiledMap;
    public TileMapHelper(){

    }
    public OrthogonalTiledMapRenderer setuMapRenderer(){
        tiledMap = new TmxMapLoader().load("Map/Map01.tmx");
        return new OrthogonalTiledMapRenderer(tiledMap);
    }
}
