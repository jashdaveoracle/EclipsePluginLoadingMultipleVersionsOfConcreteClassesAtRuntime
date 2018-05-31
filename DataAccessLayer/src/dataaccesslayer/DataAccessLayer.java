package dataaccesslayer;

import concrete.ConcreteClass;
import iface.DataAccessIFace;

public class DataAccessLayer implements DataAccessIFace {
	ConcreteClass obj = new ConcreteClass();
	@Override
	public void printImpl() {
		obj.printVerion();
	}
	@Override
	public String getData() {
		return obj.getVersion();
	}
}
