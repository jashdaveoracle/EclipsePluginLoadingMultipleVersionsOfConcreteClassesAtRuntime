package concrete;
public class ConcreteClass {
	private String version = "3";
	
	public void printVerion() {
		System.out.println(getClass().getName() + " Version :"+version);
	}
	
	public String getVersion() {
		return version;
	}
}
