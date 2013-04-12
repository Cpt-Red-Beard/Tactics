package com.testgame.sprite;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.ButtonSprite;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.align.HorizontalAlign;
import com.testgame.resource.ResourcesManager;

public class GameDialogBox {
	
	private HUD hud;
	private float width;
	private float height;

	private ButtonSprite[] buttons;

	private Sprite backgroundSprite;
	private Text messageText;
	private ButtonSprite okayButton;
	
	public GameDialogBox(HUD hud, String message, ButtonSprite ... buttons) {
		super();
		this.buttons = buttons;	

		this.hud = hud;
		
		ResourcesManager resourcesManager = ResourcesManager.getInstance();
		
		ITextureRegion background = resourcesManager.dialog_background;
		
		this.setWidth(background.getWidth());
		this.setHeight(background.getHeight());
		
		// Attach Background		
		hud.attachChild(backgroundSprite = new Sprite(240, 400, resourcesManager.dialog_background, resourcesManager.vbom));
		hud.attachChild(messageText = new Text(240, 450, resourcesManager.font, message, new TextOptions(AutoWrap.WORDS, backgroundSprite.getWidth(), HorizontalAlign.CENTER, Text.LEADING_DEFAULT), resourcesManager.vbom));
		
		
		int i = 0;
		for(ButtonSprite button : buttons){
			hud.attachChild(button);
			button.setPosition(240, 340-(100*i));
			hud.registerTouchArea(button);
			i++;
		}
		
		
		
		//hud.registerTouchArea(okayButton);
	}

	public void dismiss() {

		ResourcesManager.getInstance().engine.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				hud.detachChild(backgroundSprite);
				hud.unregisterTouchArea(okayButton);
				hud.detachChild(messageText);

				for(ButtonSprite button: buttons){
					hud.detachChild(button);
					hud.unregisterTouchArea(button);
				}
				

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
