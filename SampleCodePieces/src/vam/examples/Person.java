/**
 * 
 */
package vam.examples;

import java.util.Calendar;
import java.util.Random;

import vam.examples.excepion.VehicleException;

/**
 * @author Srinivasan
 *
 */
public class Person {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int a = Integer.parseInt(args[0]);
		// Vehicle vv = VehicleFactory.getVehicle(a);

		Random r = new Random();
		System.out.println(r.nextInt(3));

		Calendar cal = Calendar.getInstance();
		int sec = cal.get(Calendar.SECOND);
		System.out.println(sec);

		int remainder = sec % 3;

		try {
			Vehicle vv = VehicleFactory.getVehicle(String.valueOf(remainder));
			vv.start();
			vv.stop();
		} catch (VehicleException ve) {
			System.out.println(ve.getMessage());
		}

	}

}
