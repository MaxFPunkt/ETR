package objects.interfaces;

public interface Interactions {

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
	
	public default void push(){}
	public default void grab(){}
	public default void look(){}
	
}
