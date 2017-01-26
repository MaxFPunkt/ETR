package objects.interfaces;

public interface Interactions {

	public enum Action{NONE,PUSH,GRAP,LOOK};
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
	
	public default boolean can(Action action){
		switch (action) {
		case PUSH:
			return canPush();
		case GRAP:
			return canGrab();
		case LOOK:
			return canLook();
		default:return false;
		}
	}
	
	public default void push(){}
	public default void grab(){}
	public default void look(){}
	
}
