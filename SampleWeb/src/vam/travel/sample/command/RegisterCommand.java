/**
 * 
 */
package vam.travel.sample.command;

import java.util.Map;

/**
 * @author Srinivasan
 *
 */
public class RegisterCommand implements Command {

	/*
	 * (non-Javadoc)
	 * 
	 * @see vam.travel.sample.command.Command#execute(java.util.Map)
	 */
	@Override
	public String execute(Map<String, String> requestParams) {
		String successURL = "/error";
		// write the entire logic for login

		return successURL;

	}

}
