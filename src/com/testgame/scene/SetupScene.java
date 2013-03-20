
package com.testgame.scene;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.align.HorizontalAlign;
import org.json.JSONException;
import org.json.JSONObject;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.testgame.scene.SceneManager.SceneType;

public class SetupScene extends BaseScene {
	
	/**
	 * Total units, jocks, nerds, and ditzes.
	 */
	private int tot = 0, jocks = 0, nerds = 0, ditzes = 0;
	
	/**
	 * Text indicating how many of each unit has been selected.
	 */
	private Text jockText, nerdText, ditzText, totText;

	private boolean twice = false;
	
	/**
	* Random text fields we need.
	*/
	private Text setupText, mapText;
	
	private ButtonSprite play, reset, jock, nerd, ditz;
	
	private int MAX_UNITS = 10;
	
	@Override
	public void createScene() {
		
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
		jockText = new Text(32, 80, resourcesManager.font, jocks + "", 25, new TextOptions(HorizontalAlign.LEFT), vbom);
		nerdText = new Text(32, 80, resourcesManager.font, nerds + "", 25, new TextOptions(HorizontalAlign.LEFT), vbom);
		ditzText = new Text(32, 80, resourcesManager.font, ditzes + "", 25, new TextOptions(HorizontalAlign.LEFT), vbom);
		totText = new Text(240, 500, resourcesManager.font, "Total: " + tot + "/10", 25, new TextOptions(HorizontalAlign.LEFT), vbom);
		// what the hell is this?
		setupText = new Text(100, 500, resourcesManager.font, "", 25, new TextOptions(HorizontalAlign.LEFT), vbom);
		// not implemented yet..
		mapText = new Text(100, 400, resourcesManager.font, "Map: ", 25, new TextOptions(HorizontalAlign.LEFT), vbom);
		
		jock.attachChild(jockText);
		nerd.attachChild(nerdText);
		ditz.attachChild(ditzText);
		
		attachChild(totText);
	}

	private void createButtons() {
		play = new ButtonSprite(240, 350, resourcesManager.continue_region, vbom, new OnClickListener() {

			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				
				ArrayList<Integer> unitList = new ArrayList<Integer>();
				unitList.add(jocks);
				unitList.add(nerds);
				unitList.add(ditzes);
				
				if(!resourcesManager.isLocal){
					JSONObject object = new JSONObject();
					
					
					try {
						object.put("Nerds", nerds);
						object.put("Jocks", jocks);
						object.put("Ditzes", ditzes);
					} catch (JSONException e) {
						
						e.printStackTrace();
					}
					
					ParseObject turns = new ParseObject("Turns");
					turns.put("PlayerId", "user_"+ParseUser.getCurrentUser().getObjectId());
					turns.put("Player", "user_"+ParseUser.getCurrentUser().getObjectId()+"_"+0);
					turns.put("GameId", resourcesManager.gameId);
					turns.put("Device", resourcesManager.deviceID);
					turns.put("Init", object);
					turns.saveInBackground();
				}
				if(!twice){
					resourcesManager.unitArray = unitList;
					twice = true;
					tot = 0; jocks = 0; nerds = 0; ditzes = 0;
					updateText();

					
					if(!resourcesManager.isLocal){
						SceneManager.getInstance().loadGameScene(engine);
					}
				}
				else{
					resourcesManager.unitArray2 = unitList;
					SceneManager.getInstance().loadGameScene(engine);
				}
			}
			
		});
		
		reset = new ButtonSprite(240, 250, resourcesManager.reset_region, vbom, new OnClickListener() {

			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				tot = 0; jocks = 0; nerds = 0; ditzes = 0;
				updateText();
			}
		
		});
		
		jock = new ButtonSprite (125, 575, resourcesManager.jock, vbom, new OnClickListener() {

			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (tot < MAX_UNITS) {
					jocks++; tot++;
					updateText();
				}
			}
		
		});
		
		nerd = new ButtonSprite (250, 575, resourcesManager.nerd, vbom, new OnClickListener() {

			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (tot < MAX_UNITS) {
					nerds++; tot++;
					updateText();
				}
			}
			
		});
		
		ditz = new ButtonSprite (375, 575, resourcesManager.ditz, vbom, new OnClickListener() {

			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (tot < MAX_UNITS) {
					ditzes++; tot++;
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
		SceneManager.getInstance().loadMenuScene(engine);		
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
		
	}

}