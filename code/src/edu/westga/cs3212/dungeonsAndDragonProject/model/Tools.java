package edu.westga.cs3212.dungeonsAndDragonProject.model;

/**
 * Tool class that extends Item
 * 
 * @author Shawn Bretthauer
 * 
 * @version Spring 2025
 */
public class Tools extends Item {

	private String toolAbilityCheck;
	private String toolCheckRoll;
	
	/**
	 * Tool constructor
	 * 
	 * @param toolName name of the tool
	 * @param toolAbilityCheck the ability check to roll for 
	 * @param toolCheckRoll the die to roll
	 * @param toolCost the cost of the tool
	 * @param toolNotes the tool flavor text
	 */
	public Tools(String toolName, String toolAbilityCheck, String toolCheckRoll, int toolCost, String toolNotes) {
		super(toolName, toolCost, toolNotes);
		
		if (toolAbilityCheck == null) {
			throw new NullPointerException("The tool ability check cannot be null");
		} else if (toolAbilityCheck.isBlank()) {
			throw new IllegalArgumentException("The tool ability check cannot be blank.");
		} else {
			this.toolAbilityCheck = toolAbilityCheck;
		}
		
		if (toolCheckRoll == null) {
			throw new NullPointerException("The tool roll check cannot be null");
		} else if (toolCheckRoll.isBlank()) {
			throw new IllegalArgumentException("The tool roll check cannot be blank.");
		} else {
			this.toolCheckRoll = toolCheckRoll;
		}
	}
	
	/**
	 * Returns which ability to roll for.
	 * 
	 * @return toolAbilityCheck
	 */
	public String getToolAbilityCheck() {
		return this.toolAbilityCheck;
	}
	
	/**
	 * Returns the die to roll for check.
	 * 
	 * @return toolCheckRoll
	 */
	public String getToolCheckRoll() {
		return this.toolCheckRoll;
	}
}
