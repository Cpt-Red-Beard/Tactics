package com.testgame.scene;

import org.andengine.entity.scene.background.Background;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

import com.testgame.scene.SceneManager.SceneType;

public class LoadingScene extends BaseScene {

	@Override
	public void createScene() {
		camera.setCenter(240, 400);
		((SmoothCamera)camera).setZoomFactor(1f);
		setBackground(new Background(Color.BLACK));
		
		attachChild(new Text(240, 400, resourcesManager.font, "Loading...", vbom));
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_LOADING;
	}

	@Override
	public void disposeScene() {
		// also doesn't need to do anything
	}

	@Override
	public void onHomeKeyPressed() {
		// shouldn't do anything
	}
	
	@Override
	public void onBackKeyPressed() { 
		// Shouldn't do anything 
	}
}
