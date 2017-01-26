package objects.interfaces;

import objects.Interface;

public interface Interactions {

	public enum Action{NONE,PUSH,GRAB,LOOK,USE};
	/**
	 * 
	 * @return true if the Object can be pushed.
	 */
	public default boolean canPush(){return false;}
	
	/**
	 * 
	 * @return true if the Object can be grabbed.
	 */
	public default boolean canGrab(){return false;}
	
	/**
	 * 
	 * @return true if the Object can be looked at.
	 */
	public default boolean canLook(){return false;}	
	
	/**
	 * 
	 * @return true if the Object can be looked at.
	 */
	public default boolean canUse(){return false;}
	
	public default boolean can(Action action){
		switch (action) {
		case PUSH:
			return canPush();
		case GRAB:
			return canGrab();
		case LOOK:
			return canLook();
		case USE:
			return canUse();
		default:return false;
		}
	}
	

	public default void push(){}
	public default void grab(Interface intface){}
	public default void look(){}
	
}

