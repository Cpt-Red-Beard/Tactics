package com.testgame.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;

import org.andengine.opengl.util.GLState;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

import com.example.testgame.MainActivity;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.LogInCallback;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.PushService;

import com.testgame.resource.ResourcesManager;

import com.testgame.scene.SceneManager.SceneType;
import com.testgame.sprite.GameDialogBox;

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener {

	private static MenuScene menuChildScene;
	private final int MENU_LOGIN = 0;
	private final int MENU_PLAY = 1;
	private final int MENU_HOWTOPLAY = 2;
	private final int MENU_LOGOUT = 3;

	private final int MENU_QUIT = 4;
	private static IMenuItem loginMenuItem;
	private static IMenuItem playMenuItem;
	private static IMenuItem quitMenuItem;
	private static String name;
	private static IMenuItem logoutMenuItem;
	private static List<String> userslist = new ArrayList<String>();
	private AlertDialog dialog;
	private GameDialogBox quitDialog;

	private static AlertDialog loading;
	private static GameDialogBox invitation;
	private static GameDialogBox textDialog;
	private static GameDialogBox acceptDialog;
	private static GameDialogBox welcome;
	private static GameDialogBox gameOptionsDialog;
	private static AlertDialog mapDialog;
	private static Map<String, String> usernames;
	private static String selectedMapName = "Default"; 
	private static ButtonSprite okayButton;
	
	private boolean click = true;
	

	@Override
	public void createScene() {
		camera.setHUD(new HUD());
		createBackground();
		createMenuChildScene();
		usernames = new HashMap<String, String>();
		resourcesManager.menu_background_music.play();
	}

	@Override
	public void onBackKeyPressed() {
		resourcesManager.menu_background_music.pause();
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_MENU;
	}

	@Override
	public void disposeScene() {
		// Nothing to do here since we never want to unload game resources unless quitting the game.
	}
	
	private void createBackground()
	{
	    attachChild(new Sprite(240, 400, resourcesManager.menu_background_region, vbom)
	    {
	        @Override
	        protected void preDraw(GLState pGLState, Camera pCamera) 
	        {
	            super.preDraw(pGLState, pCamera);
	            pGLState.enableDither();
	        }
	    });
	}

	private void createMenuChildScene()
	{
	    menuChildScene = new MenuScene(camera);
	    menuChildScene.setPosition(240, 400);
	    
	    logoutMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_LOGOUT, resourcesManager.logout_region, vbom), 1.2f, 1);
	    loginMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_LOGIN, resourcesManager.login_region, vbom), 1.2f, 1);
	    playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, resourcesManager.newgame_region, vbom), 1.2f, 1);
	    quitMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_QUIT, resourcesManager.quit_region, vbom), 1.2f, 1);

	    final IMenuItem conintueMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_HOWTOPLAY, resourcesManager.howtoplay_region, vbom), 1.2f, 1);
	    
	    menuChildScene.addMenuItem(loginMenuItem);
	    menuChildScene.addMenuItem(playMenuItem);
	    menuChildScene.addMenuItem(logoutMenuItem);
	    menuChildScene.addMenuItem(conintueMenuItem);
	    menuChildScene.addMenuItem(quitMenuItem);
	    menuChildScene.buildAnimations();
	    menuChildScene.setBackgroundEnabled(false);
	    
	    loginMenuItem.setPosition(0, 175); 
	    playMenuItem.setPosition(0, 75);
	    conintueMenuItem.setPosition(0, -25);
	    quitMenuItem.setPosition(0, -125);
	    logoutMenuItem.setPosition(0, -325); // place log out all the way at the bottom.
	    logoutMenuItem.setVisible(false);
	    
	    menuChildScene.setOnMenuItemClickListener(this);
	    setChildScene(menuChildScene, false, false, false);
	}
	
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY)
	{
			resourcesManager.select_sound.play();
		
	        switch(pMenuItem.getID())
	        {
	        case MENU_QUIT:
	        	if(!click)
	        		return true;
	        	activity.runOnUiThread(new Runnable() {
	        	    @Override
	        	    public void run() {
	        	    	 createQuit();
	          			 
	        	    }
	        	});
	        	
	        	return true;
	        
	        case MENU_LOGIN:
	        	if(!click)
	        		return true;
	        	activity.runOnUiThread(new Runnable() {
	        	    @Override
	        	    public void run() {
	        	    	 createLoad();
	          			 
	        	    }
	        	});
	        	
	        	usernames.clear();
	        	userslist.clear();
	        	ParseFacebookUtils.logIn(activity, new LogInCallback() {
	        		  @Override
	        		  public void done(ParseUser user, ParseException err) {
	        			 
	        		    if (user == null) {
	        		      
	        		      loading.dismiss();
		      
	        		    } else if (user.isNew()) {
	        		    	
	        		      resourcesManager.userString = "user_"+ParseUser.getCurrentUser().getObjectId();
	        		      resourcesManager.deviceID = ParseInstallation.getCurrentInstallation().getInstallationId();
	        		      PushService.subscribe(activity, resourcesManager.userString, MainActivity.class);
	        		      getFacebookIdInBackground();
	        		      	        		     
	        		    } else {
	        		     
	        		      resourcesManager.userString = "user_"+ParseUser.getCurrentUser().getObjectId();
	        		      resourcesManager.deviceID = ParseInstallation.getCurrentInstallation().getInstallationId();
	        		      PushService.subscribe(activity, resourcesManager.userString, MainActivity.class);
	        		      getFacebookIdInBackground();
	        		     
	        		    }
	        		  }
	        		});
	        	
	        	
	        	
	            return true;
	        case MENU_PLAY:
	        	if(!click)
	        		return true;
	        	activity.runOnUiThread(new Runnable() {
	        	    @Override
	        	    public void run() {
	        	    	 gameOptions();
	        	    }
	        	});
	       
	        	 
	        	
	            return true;
	        
	        case MENU_HOWTOPLAY:
	        	if(!click)
	        		return true;
	        	SceneManager.getInstance().previousScene = this.getSceneType();
	        	SceneManager.getInstance().loadTutorialScene(engine);
	        	//SceneManager.getInstance().loadSetupScene(engine);
	        	return true;
	        	
	        case MENU_LOGOUT:
	        	if(!logoutMenuItem.isVisible() ){
	        		return true;
	        	}
	        	PushService.unsubscribe(activity, resourcesManager.userString);
	    		Session.getActiveSession().closeAndClearTokenInformation();
	    		logoutMenuItem.setVisible(false);
	        	return true;
	        	
	        	
	        default:
	            return false;
	    }
	}
	
	private void getFacebookIdInBackground() {
		
		  Request.executeMeRequestAsync(ParseFacebookUtils.getSession(), new Request.GraphUserCallback() {
		    @Override
		    public void onCompleted(GraphUser user, Response response) {
		      if (user != null) {
		    	 
		    	ParseUser.getCurrentUser().put("Name", user.getName());
		        ParseUser.getCurrentUser().put("fbId", user.getId());
		        ParseUser.getCurrentUser().saveInBackground();
		        name =  user.getName();
		        welcomeDialog();

		      }
		      Request.executeMyFriendsRequestAsync(ParseFacebookUtils.getSession(), new Request.GraphUserListCallback() {

				  @Override
				  public void onCompleted(final List<GraphUser> users, Response response) {
				    if (users != null) {
				      List<String> friendsList = new ArrayList<String>();
				      for (GraphUser user : users) {
				        friendsList.add(user.getId());
				      }
				     
				      ParseQuery query = ParseUser.getQuery();
				      query.whereContainedIn("fbId", friendsList);
				      query.findInBackground(new FindCallback() {
				          public void done(List<ParseObject> friendUsers, ParseException e) {
				            
				        	  if (e == null) {
				            	  for(ParseObject u : friendUsers){
				            		  userslist.add(u.getString("Name"));
				            		  usernames.put(u.getString("Name"), u.getObjectId());
				            		  
				            	  }
				            	  loading.dismiss();
				                 
				              } else {
				                 
				                  loading.dismiss();
				              }
				          }
				      });
				    }
				  }
				});   
		    }
		  });
		}
	
	private void welcomeDialog() {
		if(!clearDialogs())
			return;
		logoutMenuItem.setVisible(true);
		click = false;
		okayButton = new ButtonSprite(240, 350, resourcesManager.continue_region, resourcesManager.vbom, new OnClickListener(){
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				Log.d("AndEngine", "dismissing dialog box");
				ResourcesManager.getInstance().select_sound.play();
				welcome.dismiss();
				click = true;
			}
		});
		ButtonSprite[] buttons = {okayButton};
		camera.setHUD(new HUD());
		welcome = new GameDialogBox(camera.getHUD(), "Welcome "+name+"!", 2,true,  buttons);


	}
	
	private void showDialog(){
		if(!clearDialogs())
			return;
		final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle("Friends");
		//userslist.add("TestUser");
		final CharSequence[] de = userslist.toArray(new CharSequence[userslist.size()]);	
		 builder.setItems(de, new  DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int pos) {
                resourcesManager.opponent = (String) de[pos];
                resourcesManager.opponentString = usernames.get((String)de[pos]);
                try {
					JSONObject data = new JSONObject("{\"alert\": \"Invitation to Game\", \"action\": \"com.testgame.INVITE\", \"deviceId\": \""+resourcesManager.deviceID+"\", \"name\": \""+ParseUser.getCurrentUser().getString("Name")+"\", \"map\": \""+resourcesManager.mapString+"\", \"userid\": \""+ParseUser.getCurrentUser().getObjectId()+"\"}");
					 ParsePush push = new ParsePush();
		             push.setChannel("user_"+resourcesManager.opponentString);
		             push.setData(data);
		             push.sendInBackground();
                } catch (JSONException e) {
					e.printStackTrace();
				}
         }});
		 
		 builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int whichButton) {
            	 dialog.dismiss();
             }
         });
		dialog = builder.create();
		((AlertDialog) dialog).getListView().setFastScrollEnabled(true);
        
		dialog.setCanceledOnTouchOutside(false);
        
		dialog.show();
	}
	
	private void createLoad(){
		final AlertDialog.Builder load = new AlertDialog.Builder(activity);
		load.setTitle("Please Wait Logging In.");
		loading = load.create();
		loading.setCanceledOnTouchOutside(false);
		loading.show();
	}
	
	public void gameOptions() {
		if(!clearDialogs())
			return;
		click = false;
		ButtonSprite onlineButton = new ButtonSprite(240, 350, resourcesManager.online_region, resourcesManager.vbom, new OnClickListener(){
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				gameOptionsDialog.dismiss();
				activity.runOnUiThread(new Runnable () {
					@Override
					public void run() {
						click = true;
						createMapDialog();
					}
				});
			}
		});
		
		ButtonSprite localButton = new ButtonSprite(240, 350, resourcesManager.local_region, resourcesManager.vbom, new OnClickListener(){
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				resourcesManager.isLocal = true;
				gameOptionsDialog.dismiss();
				activity.runOnUiThread(new Runnable () {
					@Override
					public void run() {
						click = true;
						createMapDialog();
					}
				});
			}
		});
		
		ButtonSprite[] buttons = {onlineButton, localButton};
		camera.setHUD(new HUD());
		gameOptionsDialog = new GameDialogBox(camera.getHUD(), "Play locally or online!", 1, true, buttons);
		
		
	}
	
	public void createInvite(final JSONObject object){
		if(resourcesManager.inGame || !clearDialogs()){
			try {
				JSONObject data = new JSONObject("{\"alert\": \"Invitation Denied\", \"action\": \"com.testgame.CANCEL\", \"name\": \""+ParseUser.getCurrentUser().getString("Name")+"\"}");
				 ParsePush push = new ParsePush();
	             push.setChannel("user_"+object.getString("userid")); 
	             push.setData(data);
	             push.sendInBackground();
            } catch (JSONException e) { 
				e.printStackTrace();
			}	
			return;
		}
		click = false;
		ButtonSprite cancelButton = new ButtonSprite(240, 350, resourcesManager.cancel_region, resourcesManager.vbom, new OnClickListener(){
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				try {
					JSONObject data = new JSONObject("{\"alert\": \"Invitation Denied\", \"action\": \"com.testgame.CANCEL\", \"name\": \""+ParseUser.getCurrentUser().getString("Name")+"\"}");
					 ParsePush push = new ParsePush();
		             push.setChannel("user_"+object.getString("userid")); 
		             push.setData(data);
		             push.sendInBackground();
                } catch (JSONException e) { 
					e.printStackTrace();
				}	
			click = true;
           	 invitation.dismiss();
            }
		});
		
		ButtonSprite continueButton = new ButtonSprite(240, 350, resourcesManager.continue_region, resourcesManager.vbom, new OnClickListener(){
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				try {
					resourcesManager.opponentDeviceID = object.getString("deviceId");
					
					resourcesManager.setMap(object.getString("map"));
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
				resourcesManager.isLocal = false;
				resourcesManager.inGame = true;
	        	resourcesManager.gameId = UUID.randomUUID().toString();
	        	Random rand = new Random();
            	boolean h;
            	if (rand.nextDouble() > 0.5)
        			h = true;
        		else{
        			h = false;
        			resourcesManager.turn = true;
        		}
            	
            	try {
            		resourcesManager.opponent = object.getString("name");
					resourcesManager.opponentString = object.getString("userid");
				} catch (JSONException e1) {
					
					e1.printStackTrace();
				}
            	try {
					JSONObject data = new JSONObject("{\"alert\": \"Invitation Accepted\", \"action\": \"com.testgame.ACCEPT\", \"GameId\": \""+resourcesManager.gameId+"\", \"deviceId\": \""+resourcesManager.deviceID+"\", \"turn\": \""+h+"\", \"name\": \""+ParseUser.getCurrentUser().getString("Name")+"\"}");
					 ParsePush push = new ParsePush();
		             push.setChannel("user_"+object.getString("userid"));
		             push.setData(data);
		             push.sendInBackground();
                } catch (JSONException t) {
					t.printStackTrace();
				}	 
            click = true;	
           	 invitation.dismiss();
           	 SceneManager.getInstance().loadSetupScene(engine);
            }
		});
		
		
		
		
		
		
		
		          

		ButtonSprite[] buttons = {continueButton, cancelButton};
		
		
		
		camera.setHUD(new HUD());
		try {
			invitation = new GameDialogBox(camera.getHUD(), object.getString("name")+" wants to play!", 3, true, buttons);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void createDialog(String text){
		if(!clearDialogs())
			return;
		click = false;
		ButtonSprite confirmButton = new ButtonSprite(240, 350, resourcesManager.continue_region, resourcesManager.vbom, new OnClickListener(){
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {	
			click = true;
           	 textDialog.dismiss();
            }
		});
		
		
		
		
		ButtonSprite[] buttons = {confirmButton};
		
		
		
		camera.setHUD(new HUD());
		textDialog = new GameDialogBox(camera.getHUD(), text, 1, true, buttons);
		
		
		
	}
	
	public void createAcceptDialog(final JSONObject object){
		clearDialogs();
		click = false;
		ButtonSprite confirmButton = new ButtonSprite(240, 350, resourcesManager.continue_region, resourcesManager.vbom, new OnClickListener(){
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				try {
					
        			if(object.getString("turn").equals("true")){
						resourcesManager.turn = true;
					}
					else 
						resourcesManager.turn = false;
			
		
    				resourcesManager.gameId = object.getString("GameId");
    				resourcesManager.opponentDeviceID = object.getString("deviceId");
    				resourcesManager.isLocal = false;
    				resourcesManager.inGame = true;
		        	acceptDialog.dismiss();
		           	SceneManager.getInstance().loadSetupScene(engine);
        		} catch (JSONException e) {
					
					e.printStackTrace();
				}
				click = true;
           	 acceptDialog.dismiss();
            }
		});
		
		
		
		
		ButtonSprite[] buttons = {confirmButton};
		
		
		
		camera.setHUD(new HUD());
		try {
			acceptDialog = new GameDialogBox(camera.getHUD(), object.getString("name")+ " accepted the invitation!", 1, true, buttons);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	
	}
	
	public void createQuit(){
		if(!clearDialogs())
			return;
		click = false;
		
		
		ButtonSprite okay = new ButtonSprite(240, 350, resourcesManager.continue_region, resourcesManager.vbom, new OnClickListener(){
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				click = true;
				if(logoutMenuItem.isVisible()){
        			PushService.unsubscribe(activity, resourcesManager.userString);
    	    		Session.getActiveSession().closeAndClearTokenInformation();
        		}
        		System.exit(0);
			}
		});
		ButtonSprite quit = new ButtonSprite(240, 350, resourcesManager.cancel_region, resourcesManager.vbom, new OnClickListener(){
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				click = true;
				quitDialog.dismiss();
			}
		});
		
		
		
		ButtonSprite[] buttons = {okay, quit};
		
		quitDialog = new GameDialogBox(camera.getHUD(), "Are you sure you wish to quit the game?", 3, true,  buttons);
		
	}
	
	public void createMapDialog(){
		final AlertDialog.Builder dia = new AlertDialog.Builder(activity);
		dia.setTitle("Selected Map:");
		dia.setSingleChoiceItems(resourcesManager.maps(), 0, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) { 
				selectedMapName = (resourcesManager.maps()[whichButton]).toString();
				resourcesManager.setMap(selectedMapName);
				
				mapDialog.dismiss();
				
				if (!resourcesManager.isLocal) {
					activity.runOnUiThread(new Runnable () {
						@Override
						public void run() {
							showDialog();
						}
					});
				}
				else{
					resourcesManager.inGame = true;
					SceneManager.getInstance().loadSetupScene(engine);
				}
			}
		});
		
		mapDialog = dia.create();
		mapDialog.setCanceledOnTouchOutside(false);
		mapDialog.show();
		
	}

	@Override
	public void onHomeKeyPressed() {
		resourcesManager.pause_music();
	}
	private boolean clearDialogs(){
		if(invitation != null && !invitation.dismissed()){
			return false;
		}
		if(textDialog != null){
			textDialog.dismiss();
			return true;
		}
		if(acceptDialog != null && !acceptDialog.dismissed())
			return false;
		if(welcome != null){
			welcome.dismiss();
		}
		if(gameOptionsDialog != null){
			gameOptionsDialog.dismiss();
			return true;
		}
		if(quitDialog != null){
			quitDialog.dismiss();
			return true;		
		}
		return true;
	}

}
