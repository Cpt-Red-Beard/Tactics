package com.testgame.sprite;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.util.Log;

import com.testgame.resource.ResourcesManager;

public class GameDialogBox {
	
	private HUD hud;
	private float width;
	private float height;
	
	private Sprite backgroundSprite;
	private Text messageText;
	private ButtonSprite okayButton;
	
	public GameDialogBox(HUD hud, String message, ButtonSprite ... buttons) {
		super();
				
		this.hud = hud;
		
		ResourcesManager resourcesManager = ResourcesManager.getInstance();
		
		ITextureRegion background = resourcesManager.dialog_background;
		
		this.setWidth(background.getWidth());
		this.setHeight(background.getHeight());
		
		// Attach Background
		hud.attachChild(backgroundSprite = new Sprite(240, 400, resourcesManager.dialog_background, resourcesManager.vbom));
		backgroundSprite.setScale(1.75f);
		// TODO position text
		hud.attachChild(messageText = new Text(240, 450, resourcesManager.font, message, resourcesManager.vbom));
		
		/*
		int startY = 0;
		for (ButtonSprite b : buttons) {
			this.attachChild(b);
			this.registerTouchArea(b);
			b.setPosition(0, startY);
			startY += offset;
		}	
		*/	
		
		
		// default attach an okay button which dismisses the window.
		final GameDialogBox box = this;
		
		hud.attachChild(okayButton = new ButtonSprite(240, 350, resourcesManager.continue_region, resourcesManager.vbom, new OnClickListener(){
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				Log.d("AndEngine", "dismissing dialog box");
				ResourcesManager.getInstance().select_sound.play();
				box.dismiss();
			}
		}));
		
		hud.registerTouchArea(okayButton);
	}

	protected void dismiss() {
		ResourcesManager.getInstance().engine.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				hud.detachChild(backgroundSprite);
				hud.unregisterTouchArea(okayButton);
				hud.detachChild(messageText);
				hud.detachChild(okayButton);
			}
		});
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
}
