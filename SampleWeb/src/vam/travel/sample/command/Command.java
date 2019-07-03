/**
 * 
 */
package vam.travel.sample.command;

import java.util.Map;

/**
 * @author Srinivasan
 *
 */
public interface Command {

	String execute(Map<String, String> requestParams);

}
