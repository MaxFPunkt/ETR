package objects.interfaces;

import java.util.function.Consumer;

import objects.Interface;

public abstract class Interactions {

	
	protected Runnable  pushAction, lookAction, useAction,secondaryAction;
	protected Consumer<Interface> grabAction; 

	protected String grabText, pushText, lookText, useText, secondaryText;
	protected boolean canGrab, canPush, canLook;

	public enum Action{NONE,PUSH,GRAB,LOOK,USE};
	/**
	 * 
	 * @return true if the Object can be pushed.
	 */
	public boolean canPush(){return canPush;}
	
	/**
	 * 
	 * @return true if the Object can be grabbed.
	 */
	public boolean canGrab(){return canGrab;}
	
	/**
	 * 
	 * @return true if the Object can be looked at.
	 */
	public boolean canLook(){return canLook;}
	public boolean can(Action action){
		switch (action) {
		case PUSH:
			return canPush();
		case GRAB:
			return canGrab();
		case LOOK:
			return canLook();
		default:return false;
		}
	}
	
	public void setPushAction(Runnable action) { this.pushAction = action; }
	public void setLookAction(Runnable action) { this.lookAction = action; }
	public void setUseAction(Runnable action) { this.useAction = action; }
	public void setGrabAction(Consumer<Interface> action) { this.grabAction = action; }
	public void setSecondary(Runnable action) {this.secondaryAction=action;}

	public void setGrabText(String text) { this.grabText = text; }
	public void setPushText(String text) { this.pushText = text; }
	public void setLookText(String text) { this.lookText = text; }
	public void setSecondaryText(String string) { this.secondaryText = string;	}

	public void setCanGrab(boolean canGrab) { this.canGrab = canGrab; }
	public void setCanPush(boolean canPush) { this.canPush = canPush; }
	public void setCanLook(boolean canLook) { this.canLook = canLook; }
	
	public String getText(Interactions.Action action) { 
		switch(action){
			case GRAB : return grabText;
			case LOOK : return lookText;
			case PUSH : return pushText;
			case USE : return useText;
			default : return "";
		}
	}
	
	public String getSecondaryText(){ return secondaryText; }
	
	public void push(){if(pushAction!=null)pushAction.run();}
	public void grab(Interface intface){if(grabAction!=null)grabAction.accept(intface);}
	public void look(){if(lookAction!=null)lookAction.run();}
	public void secondaryAction(){if(secondaryAction!=null)secondaryAction.run();}
}