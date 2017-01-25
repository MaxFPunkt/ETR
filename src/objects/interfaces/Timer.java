package objects.interfaces;

public interface Timer {
	public default void update(){}
	/**
	 * 
	 * @param pastTime in nanoseconds
	 */
	public default void update(double pastTime){}
	public default void updateThreadSave(){}
	public default void eachSekend(){}
	public default void eachSekendThreadSave(){}
	
	/**
	 * 
	 * @param pastTime in nanoseconds
	 */
	public default void call(double pastTime) {
		update(pastTime);
		update();
	}
	
}
