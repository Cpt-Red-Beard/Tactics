package com.testgame.scene;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;

import android.util.Log;

import com.testgame.scene.SceneManager.SceneType;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.align.HorizontalAlign;

import com.testgame.scene.SceneManager;

public class TutorialScene extends BaseScene {
	
	private Text basicText, controlText, unitText;
	
	private ButtonSprite basicsButton, controlsButton, unitsButton;
	
	private boolean inMenu; 
	
	private Rectangle whiteLayer;
	
	private final int BASICS = 0;
	private final int CONTROLS = 1;
	private final int UNITS = 2;
	
	private int which;
	
	@Override
	public void createScene() {
		this.setBackgroundEnabled(false);
		
		attachChild(new Sprite(240, 400, resourcesManager.tutorial_background_region, vbom)
	    {
	        @Override
	        protected void preDraw(GLState pGLState, Camera pCamera) 
	        {
	            super.preDraw(pGLState, pCamera);
	            pGLState.enableDither();
	        }
	    });
		
		whiteLayer = new Rectangle(240, 400, 480, 800, vbom);
		whiteLayer.setColor(1f, 1f, 1f, .35f);
		
		createMenuButtons();
		
		// create text messages
		
		TextOptions texOps = new TextOptions(HorizontalAlign.CENTER);
		
		String basicMsg = "Welcome to Tactics with Friends!!\n The object of this game is to\n maneuver " +
		                  "your units around the\n map to attack and defeat your\n friend's units.\n\n" + 
				          "Be careful though! Each of your\n units only has a certain amount\n of energy" + 
		                  "it can use each turn.\n The more energy you use a \nturn, the less restores!";
		basicText = new Text(240, 400, resourcesManager.cartoon_font_white, basicMsg, texOps, vbom);
		
		String controlMsg = "How to Move:\n Click the character you want\n to moveand select the 'Move'.\n" + 
		                    "Double click a blue square to move.\n\nHow to Attack:\n Click the character you want\n" +
				            "to attack with and select 'Attack'.\n Click a red square unit to attack.\n\n " +
		                    "How to End Turn:\n Click the pause button at\n the top and select 'End Turn'";
		controlText = new Text(240, 400, resourcesManager.cartoon_font_white, controlMsg, texOps, vbom);

		String unitMsg = "There are three different types\n of units: Jocks, Nerds, and Ditzes.\n\nThe jock " +
				" is your standard tank. \nHe has a short attack range\n but a large movement range." +
				"\n\n The girl is the midrange unit.\n She has a medium movement\n and attack range." +
			 "\n\n The nerd is the range unit.\n He has a large attack range,\n but a short movement.";
		unitText = new Text(240, 400, resourcesManager.cartoon_font_white, unitMsg, texOps, vbom);	
	}
	
	private void createMenuButtons() {
		
		basicsButton = new ButtonSprite(245, 700, resourcesManager.basics_region, vbom, new OnClickListener() {
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				
				showBasics();
			}
		});
		
		controlsButton = new ButtonSprite(245, 600, resourcesManager.controls_region, vbom, new OnClickListener() {
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				
				showControls();
			}
		});
		
		unitsButton = new ButtonSprite(245, 500, resourcesManager.units_region, vbom, new OnClickListener() {
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				
				showUnits();
			}
		});
		
	    inMenu = true;
	    
	    attachChild(basicsButton); registerTouchArea(basicsButton);
	    attachChild(controlsButton); registerTouchArea(controlsButton);
	    attachChild(unitsButton); registerTouchArea(unitsButton);
	}
	
	

	@Override
	public void onBackKeyPressed() {
		
		Log.d("AndEngine", "Back key pressed in tutorial.");
		
		if (!inMenu) { // menu not up, pop it back
			final TutorialScene tutorial = this;
			tutorial.detachChild(whiteLayer);
			
					tutorial.detachChild(whiteLayer);
					switch(which) {
					case BASICS:
						tutorial.detachChild(basicText);
						
					case CONTROLS:
						tutorial.detachChild(controlText);
						
					case UNITS:
						tutorial.detachChild(unitText);
						
					default:
						break;
					}
				
			restoreMenu();
		} else {
			SceneManager.getInstance().restorePrevious();
		}
	}

	private void restoreMenu() {
		inMenu = true;
		attachChild(basicsButton);
		registerTouchArea(basicsButton);
		attachChild(controlsButton);
		registerTouchArea(controlsButton);
		attachChild(unitsButton);
		registerTouchArea(unitsButton);
	}

	@Override
	public void onHomeKeyPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_TUTORIAL;
	}

	@Override
	public void disposeScene() {
		resourcesManager.unloadTutorialResources();
		
	}
	
	private void showBasics() {
		clearMenu();
		attachChild(whiteLayer);
		attachChild(basicText);
		inMenu = false;
		which = BASICS;
	}
	
	private void clearMenu() {
		final TutorialScene tutorial = this;
		
				tutorial.detachChild(basicsButton);
				tutorial.unregisterTouchArea(basicsButton);
				tutorial.detachChild(controlsButton);
				tutorial.unregisterTouchArea(controlsButton);
				tutorial.detachChild(unitsButton);
				tutorial.unregisterTouchArea(unitsButton);
			}
	

	private void showControls() {
		clearMenu();
		attachChild(whiteLayer);
		attachChild(controlText);
		inMenu = false;
		which = CONTROLS;
	}
	
	private void showUnits() {
		clearMenu();
		attachChild(whiteLayer);
		attachChild(unitText);
		inMenu = false;
		which = UNITS;
	}
}
