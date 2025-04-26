package edu.westga.cs3212.dungeonsAndDragonProject.model;

import java.util.Set;

/**
 * The Class Role.
 * 
 * @author Liam Worthington
 * @version Spring 2025
 */
public class Role {
	private String name;
	private String flavorText;
	private Set<String> features;
	private Set<String> proficiencies;

	/**
	 * Initiates a new Role
	 * 
	 * @precondition name != null && name not blank && flavorText != null && flavorText not blank && features != null && !features.isEmpty()
	 *               && proficiencies != null && !proficiencies.isEmpty()
	 * @postcondition getFlavorText() == flavorText && getFeatures() == features && getProficiencies() == proficiencies && getName() == name
	 * 
	 * @param name 		 the name of the role.
	 * @param flavorText describes the role.
	 * @param features   the abilities the role gains as it levels up.
	 * @param proficiencies the weapons the class is proficient in.
	 */
	public Role(String name, String flavorText, Set<String> features, Set<String> proficiencies) {
		if (name == null) {
			throw new IllegalArgumentException("name cannot be null");
		}
		if (flavorText == null) {
			throw new IllegalArgumentException("flavor text cannot be null");
		}
		if (features == null) {
			throw new IllegalArgumentException("features cannot be null");
		}
		if (proficiencies == null) {
			throw new IllegalArgumentException("proficiencies cannot be null");
		}

		if (name.isBlank()) {
			throw new IllegalArgumentException("name cannot be empty");
		}
		if (flavorText.isBlank()) {
			throw new IllegalArgumentException("flavor text cannot be empty");
		}
		if (features.isEmpty()) {
			throw new IllegalArgumentException("features cannot be empty");			
		}
		if (proficiencies.isEmpty()) {
			throw new IllegalArgumentException("proficiencies cannot be empty");
		}

		this.name = name;
		this.flavorText = flavorText;
		this.features = features;
		this.proficiencies = proficiencies;
	}

	/**
	 * Gets the name
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the name of the role
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gets the flavor text
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return a description of the role
	 */
	public String getFlavorText() {
		return this.flavorText;
	}

	/**
	 * Gets the features
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the features
	 */
	public Set<String> getFeatures() {
		return this.features;
	}
	
	/**
	 * Sets the features
	 * 
	 * @precondition features != null && !features.isEmpty()
	 * @postcondition getFeatures().equals(features)
	 * 
	 * @param features	the features to set.
	 */
	public void setFeatures(Set<String> features) {
		if (features == null) {
			throw new IllegalArgumentException("features cannot be null");
		}
		if (features.isEmpty()) {
			throw new IllegalArgumentException("features cannot be empty");			
		}
		
		this.features = features;
	}
	
	/**
	 * Gets the proficiencies
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the proficiencies
	 */
	public Set<String> getProficiencies() {
		return this.proficiencies;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	/**
	 * Returns the details of the role
	 * 
	 * @return details
	 */
	public String roleDetails() {
		String details = this.flavorText + "\n" + "Features: ";
		for (String feature : this.features) {
			details += feature + " ";
		}
		details += "\nProficencies: ";
		for (String proficiency : this.proficiencies) {
			details += proficiency + " ";
		}
		
		return details;
	}
}
