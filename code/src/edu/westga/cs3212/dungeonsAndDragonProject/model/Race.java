package edu.westga.cs3212.dungeonsAndDragonProject.model;

import java.util.ArrayList;

/**Defines Race Class
 * @author Kelis
 * @version Spring 2025
 */
public class Race {
	
	private String name;
	private Creature creature;
	private Size size;
	private int speed;
	private String flavorText;
	private String[] traits;
	
	/**Creates a Race
	 * @precondition !(name.isEmpty || flavorText.isEmpty), !((creature == null || size == null), !(traits.length = 0)
	 * @postcondition creates race object with parameters as value
	 * 
	 * @param name the name of the race
	 * @param creature the type of creature
	 * @param size the size of race
	 * @param speed how fast the race is
	 * @param flavorText describes race
	 * @param traits abilities that comes with race
	 */
	public Race(String name, Creature creature, Size size, int speed, String flavorText, String[] traits) {
		
		if (name.isEmpty()) {
			throw new IllegalArgumentException("name can not be empty or null");
		}
		
		if (creature == null || size == null) {
			throw new IllegalArgumentException("creature or size can not be null");
		}
		
		if (flavorText.isEmpty()) {
			throw new IllegalArgumentException("flavor text can not be empty or null");
		}
		
		if (traits.length == 0) {
			throw new IllegalArgumentException("traits can not be empty or null");
		}
		
		this.name = name;
		this.creature = creature;
		this.size = size;
		this.speed = speed;
		this.flavorText = flavorText;
		this.traits = traits;
		
	}
	
	/**Gets the name of the race
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return name of the race
	 */
	public String getName() {
		return this.name;
	}

	/**sets the name of the race
	 * 
	 * @precondition none
	 * @postcondition this.name == name
	 * 
	 * @param name name of the race
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/** Gets the Creature Type
	 * @precondition none
	 * @postcondition none
	 * @return the creature type
	 */
	public Creature getCreature() {
		return this.creature;
	}

	/**Sets the creature type 
	 * @precondition none
	 * @postcondition this.creature = creature
	 * @param creature the creature type
	 */
	public void setCreature(Creature creature) {
		this.creature = creature;
	}

	/**Gets the size of the Race
	 * @precondition none
	 * @postcondition none
	 * @return the size type of the race
	 */
	public Size getSize() {
		return this.size;
	}

	/**Sets the size of the race
	 * @precondition none
	 * @postcondition this.size = size
	 * @param size the size of the race
	 */
	public void setSize(Size size) {
		this.size = size;
	}

	/**Gets the speed of the Race
	 * @precondition none
	 * @postcondition none
	 * @return the speed for the race
	 */
	public int getSpeed() {
		return this.speed;
	}

	/**Sets the speed of the Race
	 * @precondition none
	 * @postcondition this.speed = speed
	 * @param speed how fast the race is
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**Gets the flavor text of race
	 * @precondition none
	 * @postcondition none
	 * @return a string of text that describes race
	 */
	public String getFlavorText() {
		return this.flavorText;
	}

	/**Sets the flavor text
	 * @precondition none
	 * @postcondition this.flavorText = flavorText
	 * @param flavorText text that describes race
	 */
	public void setFlavorText(String flavorText) {
		this.flavorText = flavorText;
	}
	
	/**Gets Array of Traits
	 * @precondition none
	 * @postcondition none
	 * @return array of traits for race
	 */
	public String[] getTraits() {
		return this.traits;
	}
	
	/**Converts and Returns Traits As An ArrayList
	 * @precondition none
	 * @postcondition none
	 * @return an array list that has the same contents as this.traits
	 */
	public ArrayList<String> getTraitsAsArrayList() {
		ArrayList<String> list = new ArrayList<String>();
		
		for (String currentTrait: this.traits) {
			list.add(currentTrait);
		}
		
		return list;
	}
	
	/**Sets Traits list
	 * @precondition none
	 * @postcondition this.traits = traits
	 * @param traits the abilities of the race
	 */
	public void setTraits(String[] traits) {
		this.traits = traits;
	}
	
	@Override
	public String toString() {
		return  this.name;
	}
	
	/**
	 * Returns the details of the race
	 * 
	 * @return builder
	 */
	public String raceDetails() {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(this.getName() + "\n");
		builder.append("Species: " + this.getClass().getSimpleName() + "\n");
		builder.append("Type: " + this.getCreature() + "   Speed: " + this.getSpeed() + " ");
		builder.append(" Size: " + this.getSize() + "\n");
		builder.append(this.getFlavorText() + "\n");
		builder.append("Traits: ");
		
		for (int trait = 0; trait < this.getTraits().length; trait++) {
			if (trait == this.getTraits().length - 1) {
				builder.append(this.getTraitsAsArrayList().get(trait));
			} else {
				builder.append(this.getTraitsAsArrayList().get(trait) + ", ");
			}
		}
		
		return builder.toString();
	}

}
