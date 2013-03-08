package com.testgame.scene;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.testgame.resource.ResourcesManager;
import com.testgame.scene.SceneManager.SceneType;

import android.app.Activity;

public abstract class BaseScene extends Scene {
	 	public Engine engine;
	    public Activity activity;
	    public ResourcesManager resourcesManager;
	    public VertexBufferObjectManager vbom;
	    public Camera camera;
	    
	    //---------------------------------------------
	    // CONSTRUCTOR
	    //---------------------------------------------
	    
	    public BaseScene()
	    {
	        this.resourcesManager = ResourcesManager.getInstance();
	        this.engine = resourcesManager.engine;
	        this.activity = resourcesManager.activity;
	        this.vbom = resourcesManager.vbom;
	        this.camera = resourcesManager.camera;
	        createScene();
	    }
	    
	    //---------------------------------------------
	    // ABSTRACTION
	    //---------------------------------------------
	    
	    public abstract void createScene();
	    
	    public abstract void onBackKeyPressed();
	    
	    public abstract void onHomeKeyPressed();
	    
	    public abstract SceneType getSceneType();
	    
	    public abstract void disposeScene();
}
