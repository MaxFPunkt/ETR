package objects.interfaces;

public interface Timer {
	public default void update(){}
	public default void updateThreadSave(){}
	public default void eachSekend(){}
	public default void eachSekendThreadSave(){}
	
	/**
	 * 
	 * @param pastTime in Milliseconds
	 */
	public static void call(double pastTime) {
		
	}
}
