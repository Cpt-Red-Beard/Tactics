package com.testgame.scene;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

import com.testgame.scene.SceneManager.SceneType;

public class LoadingScene extends BaseScene {

	@Override
	public void createScene() {
		setBackground(new Background(Color.BLACK));
		attachChild(new Text(camera.getCenterX(), camera.getCenterY(), resourcesManager.cartoon_font_white, "Loading...", vbom));
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
