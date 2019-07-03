/**
 * 
 */
package vam.examples;

/**
 * @author Srinivasan
 *
 */
public class Bike implements Vehicle {

	/*
	 * (non-Javadoc)
	 * 
	 * @see vam.examples.Vehicle#start()
	 */
	@Override
	public void start() {
		System.out.println("starting a bike");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vam.examples.Vehicle#stop()
	 */
	@Override
	public void stop() {
		System.out.println("stopping this bike");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vam.examples.Vehicle#drive()
	 */
	@Override
	public void drive() {
		System.out.println("drive this bike");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vam.examples.Vehicle#brake()
	 */
	@Override
	public void brake() {
		System.out.println("brake this bike");

	}

}
