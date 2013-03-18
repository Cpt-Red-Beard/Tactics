package com.testgame.sprite;

import java.util.ArrayList;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.primitive.Line;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;
import org.andengine.extension.tmx.TMXTile;

import com.testgame.resource.ResourcesManager;
import com.testgame.scene.GameScene;

import android.util.Log;

public class HighlightedSquare extends Rectangle {

	public HighlightedSquare(float pX, float pY, float pWidth, float pHeight,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
	}
	
	public CharacterSprite unit;
	public TMXTile tile;
	private GameScene game;
	
	boolean touched;
	
	private ArrayList<Line> borderLines;
	
	public HighlightedSquare(TMXTile tile, int x, int y, int tileSize, GameScene gameScene, CharacterSprite unit) {
		super(x, y, tileSize, tileSize, gameScene.vbom);
		this.tile = tile;
		this.game = gameScene;
		this.unit = unit;
		this.touched = false;
		
		this.borderLines = new ArrayList<Line>();
		int borderSize = 3;
		this.borderLines.add(new Line(0, 0, 0, game.tileSize, borderSize, game.vbom));
		this.borderLines.add(new Line(0, 0, game.tileSize, 0, borderSize, game.vbom));
		this.borderLines.add(new Line(0, game.tileSize, game.tileSize, game.tileSize, borderSize, game.vbom));
		this.borderLines.add(new Line(game.tileSize, 0, game.tileSize, game.tileSize, borderSize, game.vbom));
	}
	
	@Override
    public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		Log.d("AndEngine", "Square touched!");
		
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			
			ResourcesManager.getInstance().touch_sound.play();
			
			if (touched) {
				Log.d("AndEngine", "already selected, calling gamescne");
				this.removeBorder();
				this.game.squareTouched(this, pSceneTouchEvent);
				this.touched = false;
				this.game.currentlySelectedMoveTile = null;
				return true;
			} else {
				Log.d("AndEngine", "drawing border");
				// remove border from previously selected square.
				if (game.currentlySelectedMoveTile != null) {
					game.currentlySelectedMoveTile.removeBorder();
				}
				this.touched = true;
				this.game.currentlySelectedMoveTile = this;
				drawBorder();
				return true;
			}
		}
		
		return true;
	}
	
	public void drawBorder() {
		for (Line l : this.borderLines){
			l.setColor(Color.BLACK);
			this.attachChild(l);
		}
	}
	
	public void removeBorder() {
		for (Line l : this.borderLines){
			this.detachChild(l);
		}
	}

}
