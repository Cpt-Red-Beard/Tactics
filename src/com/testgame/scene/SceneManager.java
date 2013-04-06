package com.testgame.scene;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.testgame.resource.ResourcesManager;

public class SceneManager {

    private BaseScene splashScene;
    private BaseScene menuScene;
    public BaseScene gameScene;
    private BaseScene loadingScene;
    private BaseScene setupScene;
    private BaseScene tutorialScene;
    
    public SceneType previousScene;
    
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    private static final SceneManager INSTANCE = new SceneManager();
    
    private SceneType currentSceneType = SceneType.SCENE_SPLASH;
    
    private BaseScene currentScene;
    
    private Engine engine = ResourcesManager.getInstance().engine;
    
    public enum SceneType
    {
        SCENE_SPLASH,
        SCENE_MENU,
        SCENE_GAME,
        SCENE_LOADING,
        SCENE_SETUP,
        SCENE_TUTORIAL;
    }
    
    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------
    
    public void setScene(BaseScene scene)
    {
        engine.setScene(scene);
        currentScene = scene;
        currentSceneType = scene.getSceneType();
    }
    
    public void setScene(SceneType sceneType)
    {
        switch (sceneType)
        {
            case SCENE_MENU:
                setScene(menuScene);
                break;
            case SCENE_GAME:
            	GameScene g = (GameScene) getGameScene();
            	g.camera.setHUD(g.hud);
            	g.camera.setCenter(g.OldX, g.OldY);
            	((BoundCamera)g.camera).setBoundsEnabled(true);
        	    ((SmoothCamera) g.camera).setZoomFactor(1.0f);
                setScene(getGameScene());
                break;
            case SCENE_SPLASH:
                setScene(splashScene);
                break;
            case SCENE_LOADING:
            	LoadingScene l = (LoadingScene) getLoadingScene();
            	((BoundCamera)l.camera).setBoundsEnabled(false);
        	    ((SmoothCamera) l.camera).setZoomFactor(1.0f);
        	    
            	l.camera.setHUD(null);
            	l.camera.setCenter(240, 400);
            	
                setScene(loadingScene);
                break;
            case SCENE_SETUP:
            	setScene(setupScene);
            case SCENE_TUTORIAL:
            	TutorialScene t = (TutorialScene) getTutorialScene();
            	((BoundCamera)t.camera).setBoundsEnabled(false);
        	    ((SmoothCamera)t.camera).setZoomFactor(1.0f);
            	t.camera.setHUD(null);
            	t.camera.setCenter(240, 400);
            	
            	setScene(tutorialScene);
            default:
                break;
        }
    }
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    
    public static SceneManager getInstance()
    {
        return INSTANCE;
    }
    
    public SceneType getCurrentSceneType()
    {
        return currentSceneType;
    }
    
    public BaseScene getCurrentScene()
    {
        return currentScene;
    }
    
    public void createMenuScene()
    {
    	ResourcesManager.getInstance().loadMenuResources();
        menuScene = new MainMenuScene();
        loadingScene = new LoadingScene();
        SceneManager.getInstance().setScene(menuScene);
        disposeSplashScene();
    }
    
    public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback)
    {
        ResourcesManager.getInstance().loadSplashScreen();
        splashScene = new SplashScene();
        currentScene = splashScene;
        pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
    }
    
    private void disposeSplashScene()
    {
        ResourcesManager.getInstance().unloadSplashScreen();
        splashScene.disposeScene();
        splashScene = null;
    }
    
    public void loadGameScene(final Engine mEngine)
    {
        setScene(loadingScene.getSceneType());
        //ResourcesManager.getInstance().unloadMenuTextures();
        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
        {
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadGameResources();
                gameScene = new GameScene();
                setScene(gameScene);
            }
        }));
    }
    
    public void loadMenuScene(final Engine mEngine)
    {
        setScene(loadingScene.getSceneType());
        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
        {
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadMenuResources();
                setScene(menuScene);
            }
        }));
    }
    
    public void loadSetupScene(final Engine mEngine)
    {
        setScene(loadingScene.getSceneType());
        
       
        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
        {
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadSetupResources();
                setupScene = new SetupScene();
                setScene(setupScene);
            }
        }));
    }
    
    public void loadTutorialScene(final Engine mEngine) {
    	
    	setScene(loadingScene.getSceneType());
    	
    	mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
        {
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadTutorialResources();
                tutorialScene = new TutorialScene();
                setScene(tutorialScene.getSceneType());
            }
        }));
    }

    public BaseScene getMainMenuScene(){
    	return menuScene;
    }
    
    public BaseScene getTutorialScene(){
    	return tutorialScene;
    }

    public BaseScene getLoadingScene(){
    	return loadingScene;
    }
    
	public void restorePrevious() {
		this.setScene(previousScene);
	}

	public BaseScene getGameScene() {
		return gameScene;
	}
}
