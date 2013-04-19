package com.testgame.sprite;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.testgame.resource.ResourcesManager;
import com.testgame.scene.GameScene;
import com.testgame.mechanics.unit.AUnit;
import com.testgame.player.APlayer;

import android.view.MotionEvent;

public class CharacterSprite extends AnimatedSprite {
	
	public CharacterSprite(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, (ITiledTextureRegion) pTextureRegion, pVertexBufferObjectManager);
		resourcesManager = ResourcesManager.getInstance();
		inSelectedCharactersAttackRange = false;
	}

	private ResourcesManager resourcesManager;
	private Text energyText;
	
	protected APlayer player;
	
	protected GameScene game;
	
	protected ProgressBar healthBar;
	
	protected ProgressBar energyBar;
	
	public boolean inSelectedCharactersAttackRange;
	
	public void initializeText(int Energy, int Health){
		
		this.energyText = new Text(0, 0, this.resourcesManager.font, Energy+"/"+Health, this.game.vbom);
		this.attachChild(energyText);
	}
	
	public void setText(int Energy, int Health) {
		this.energyText.setText(Energy+"/"+Health);
	}
	
	@Override
    public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		
		
		APlayer p = this.player;
		boolean isTurn = p.isTurn();
		if(!game.click)return true;
		if (game.animating) return true;
		if(game.working) return true;
		
		if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_DOWN) {
			
			// if we're not in sprite mode, switch back to it
			if (game.mode != GameScene.SPRITE_MODE) game.switchMode(GameScene.SPRITE_MODE);
			
			ResourcesManager.getInstance().touch_sound.play();
			
			if (isTurn) {
				game.working = true;
				
				if (this.game.getSelectedCharacter() == this) {
					this.game.deselectCharacter(true);
				} else {
					
					this.game.activateAndSelect(this);
				}
			} else {
				if (inSelectedCharactersAttackRange){
					
					
					if(this.game.getSelectedCharacter() == null){
						
						return true;
					}
					game.working = true;
					
					game.alertForAttack();
					((AUnit) this.game.getSelectedCharacter()).attack((AUnit) this);
				} else {
					
					if (this.game.getSelectedCharacter() == this) {
						this.game.deselectCharacter(true);
					} else {
						this.game.setSelectedCharacter((AUnit) this);
					}
				}
			}
		}
		return true;
	}

	public void animatePoints(int points, String which) {
		
		if (points == 0) return;
		
		Font whichFont;
		if (which.equals("red")) {
			whichFont = resourcesManager.cartoon_font_red;
		} else {
			whichFont = resourcesManager.cartoon_font_blue;
		}
		
		Text message;
		if (points > 0) {
			message = new Text(this.getX() + this.getWidth()/2, this.getY() + this.getHeight() + 10, whichFont, "+"+points, game.vbom);
		} else {
			message = new Text(this.getX() + this.getWidth()/2, this.getY() + this.getHeight() + 10, whichFont, ""+points, game.vbom);
		}
		
		game.attachChild(message);
		
		message.setZIndex(game.TEXT_Z);

		
		
		message.registerEntityModifier(new MoveModifier(1.5f, message.getX(), message.getY(), message.getX(), message.getY() + 25) {
			@Override
			protected void onModifierFinished(final IEntity pItem) {
				super.onModifierFinished(pItem);
				
				game.engine.runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						game.detachChild(pItem);
					}});
			}
		});
		
	}
	
}
