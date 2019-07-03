/**
 * 
 */
package vam.examples;

import java.io.FileReader;
import java.util.Properties;

import vam.examples.excepion.VehicleException;

/**
 * @author Srinivasan
 *
 */
public class VehicleFactory {

	public static Vehicle getVehicle(String a) throws VehicleException {

		Properties vehicles = new Properties();
		Vehicle vv = null;
		try {
			vehicles.load(new FileReader("D:/CP-POP/workspace/SampleCodePieces/src/vehicle.properties"));

			String className = vehicles.getProperty(a);
			if (className == null) {
				throw new VehicleException("no vehicle available at this time");
			} else {
				Class cl = Class.forName(className);
				vv = (Vehicle) cl.newInstance();
			}
		} catch (Exception ee) {
			ee.printStackTrace();
			throw new VehicleException(ee.getMessage());
		}
		return vv;
		/*
		 * if (a == 1) { return new Car(); } else { return new Auto(); }
		 */
	}

}
