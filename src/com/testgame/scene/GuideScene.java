package com.testgame.scene;

import java.util.ArrayList;

import com.testgame.scene.SceneManager.SceneType;

public class GuideScene extends GameScene {

	public enum GuidePhase {
		SELECT_CHARACTER,
		MOVE_UNIT,
		ATTACK_UNIT,
		END_TURN,
		WIN_GAME;
	}
	
	private GuidePhase currentPhase;
	
	@Override
	public void createScene() {
		
		resourcesManager.setMap("guide.tmx");
		
		resourcesManager.unitArray = new ArrayList<Integer>();
		resourcesManager.unitArray.add(1);
		resourcesManager.unitArray.add(1);
		resourcesManager.unitArray.add(1);
		
		resourcesManager.unitArray2 = new ArrayList<Integer>();
		resourcesManager.unitArray2.add(1);
		resourcesManager.unitArray2.add(1);
		resourcesManager.unitArray2.add(1);
		
		resourcesManager.isLocal = true;
		
		super.createScene();
		
		currentPhase = GuidePhase.SELECT_CHARACTER;
		switchToCurrentPhase();
	}

	private void switchToCurrentPhase() {
		
		switch(currentPhase) {
		case SELECT_CHARACTER:
			// disable moves/attacks
			
			break;
		case MOVE_UNIT:
			// restore moves... ? 
			
			break;
		case ATTACK_UNIT:
			// restore attacks... ? 
			
			break;
		case END_TURN:
			
			break;
		case WIN_GAME:
			break;
		default:
			break;
		}
		
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().loadTutorialScene(engine);
	}

	@Override
	public void onHomeKeyPressed() {
		super.onHomeKeyPressed();
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_GUIDE;
	}

	@Override
	public void disposeScene() {
		super.disposeScene();
	}

}
