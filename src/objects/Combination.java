package objects;

import java.util.function.Supplier;

public class Combination {
	public Object a,b;
	public Runnable f;
	public String success,error;
	public Supplier<Boolean> condition;
	public Combination(Object a, Object b, Runnable f,Supplier<Boolean> condition, String success,String error) {
		this.a = a;
		this.b = b;
		this.f = f;
		this.success = success;
		this.error = error;
		this.condition = condition;
	}
	public Combination(Object a, Object b, Runnable f, String success){
		this(a,b,f,()->{return true;},success,"");
	}
}
