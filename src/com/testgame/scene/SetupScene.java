
package com.testgame.scene;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.align.HorizontalAlign;
import org.json.JSONArray;
import android.util.Log;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.testgame.scene.SceneManager.SceneType;

public class SetupScene extends BaseScene {
	
	//Jocks = 0, Nerds = 1, Ditz = 2
	
	JSONArray first;
	
	/**
	 * Total units, jocks, nerds, and ditzes.
	 */
	private int tot = 0, jocks = 0, nerds = 0, ditzes = 0;
	
	/**
	 * Text indicating how many of each unit has been selected.
	 */
	private Text jockText, nerdText, ditzText, totText;

	private ArrayList<Integer> units;
	
	private boolean twice = false;
	
	/**
	* Random text fields we need.
	*/
	@SuppressWarnings("unused")
	private Text setupText, mapText;
	
	// TODO Need some sort of scrollable or drop-downn menu for level selection; hard-coding buttons for now
	private ButtonSprite play, reset, jock, nerd, ditz;
	
	private int MAX_UNITS = resourcesManager.getNumber(resourcesManager.mapString);
	
	@Override
	public void createScene() {
		units = new ArrayList<Integer>();
		MAX_UNITS = resourcesManager.getNumber(resourcesManager.mapString);
		twice = false;
		attachChild(new Sprite(240, 400, resourcesManager.setup_background, vbom)
	    {
	        @Override
	        protected void preDraw(GLState pGLState, Camera pCamera) 
	        {
	            super.preDraw(pGLState, pCamera);
	            pGLState.enableDither();
	        }
	    });
		
		createButtons();
		createText();
	}

	private void createText() {
		jockText = new Text(32, 80, resourcesManager.cartoon_font_white, jocks + "", 25, new TextOptions(HorizontalAlign.LEFT), vbom);
		nerdText = new Text(32, 80, resourcesManager.cartoon_font_white, nerds + "", 25, new TextOptions(HorizontalAlign.LEFT), vbom);
		ditzText = new Text(32, 80, resourcesManager.cartoon_font_white, ditzes + "", 25, new TextOptions(HorizontalAlign.LEFT), vbom);
		totText = new Text(240, 500, resourcesManager.cartoon_font_white, "Total: " + tot + "/"+MAX_UNITS, 25, new TextOptions(HorizontalAlign.LEFT), vbom);
		// what the hell is this?
		setupText = new Text(240, 720, resourcesManager.cartoon_font_white, resourcesManager.getLocalName(), new TextOptions(HorizontalAlign.LEFT), vbom);
		// not implemented yet..
		mapText = new Text(100, 400, resourcesManager.cartoon_font_white, "Map: ", 25, new TextOptions(HorizontalAlign.LEFT), vbom);
		
		jock.attachChild(jockText);
		nerd.attachChild(nerdText);
		ditz.attachChild(ditzText);
		
		attachChild(totText);
		attachChild(setupText);
	}

	private void createButtons() {
		
		play = new ButtonSprite(240, 350, resourcesManager.continue_region, vbom, new OnClickListener() {

			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(tot < MAX_UNITS){
					return;
				}
				
				resourcesManager.select_sound.play();
				
				if(!resourcesManager.isLocal){
					first = new JSONArray();
					for(int i : units){
						first.put(i);
					}
					
					
					
					ParseObject turns = new ParseObject("Turns");
					turns.put("PlayerId", "user_"+ParseUser.getCurrentUser().getObjectId());
					turns.put("Player", "user_"+ParseUser.getCurrentUser().getObjectId()+"_"+0);
					turns.put("GameId", resourcesManager.gameId);
					turns.put("Device", resourcesManager.deviceID);
					turns.put("InitArray", first);
					turns.saveInBackground();
					Log.d("Save", "Saved in background");
				}
				if(!twice){
					resourcesManager.unitArray = new ArrayList<Integer>(units);
					twice = true;
					tot = 0; jocks = 0; nerds = 0; ditzes = 0;
					units.clear();
					setupText.setText(resourcesManager.getLocalName());
					updateText();
					
					if(!resourcesManager.isLocal){
						SceneManager.getInstance().loadGameScene(engine);
					}
				}
				else{
					resourcesManager.unitArray2 = new ArrayList<Integer>(units);
					SceneManager.getInstance().loadGameScene(engine);
				}

			}
			
		});
		
		reset = new ButtonSprite(240, 250, resourcesManager.reset_region, vbom, new OnClickListener() {

			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				resourcesManager.select_sound.play();
				tot = 0; jocks = 0; nerds = 0; ditzes = 0;
				units.clear();
				updateText();
			}
		
		});
		
		jock = new ButtonSprite (125, 575, resourcesManager.jock, vbom, new OnClickListener() {

			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (tot < MAX_UNITS) {
					resourcesManager.touch_sound.play();
					jocks++; tot++; units.add(0);
					updateText();
				}
			}
		
		});
		
		nerd = new ButtonSprite (250, 575, resourcesManager.nerd, vbom, new OnClickListener() {

			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (tot < MAX_UNITS) {
					resourcesManager.touch_sound.play();
					nerds++; tot++; units.add(1);
					updateText();
				}
			}
			
		});
		
		ditz = new ButtonSprite (375, 575, resourcesManager.ditz, vbom, new OnClickListener() {

			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (tot < MAX_UNITS) {
					resourcesManager.touch_sound.play();
					ditzes++; tot++; units.add(2);
					updateText();
				}
			}
			
		});
	
	
		attachChild(play);
		attachChild(reset);
		attachChild(jock);
		attachChild(nerd);
		attachChild(ditz);
		
		registerTouchArea(play);
		registerTouchArea(reset);
		registerTouchArea(jock);
		registerTouchArea(nerd);
		registerTouchArea(ditz);
		
	}

	protected void updateText() {
		totText.setText("Total: " + tot + "/" + MAX_UNITS);
		jockText.setText(jocks + "");
		nerdText.setText(nerds + "");
		ditzText.setText(ditzes + "");
	}

	@Override
	public void onBackKeyPressed() {
		//Do Nothing here unsafe when online.
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_SETUP;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onHomeKeyPressed() {
		// TODO Auto-generated method stub
		resourcesManager.pause_music();
		
	}

}