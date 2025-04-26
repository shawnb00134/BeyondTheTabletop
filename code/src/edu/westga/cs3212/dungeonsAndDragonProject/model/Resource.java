package edu.westga.cs3212.dungeonsAndDragonProject.model;

/**
 * Class for a campaign image resource.
 * 
 * @author MoriaEl
 * @version Spring 2025
 */
public class Resource {
	private String name;
	private String filename;
	private boolean visible;

	/**
	 * The default resource constructor
	 */
	public Resource() {
	}

	/**
	 * Construct a resource
	 * 
	 * @param name     usergiven name
	 * @param filename the actual file on disk under campaigns/{id}/resources/
	 * @param visible  whether non DMs may see it
	 */
	public Resource(String name, String filename, boolean visible) {
		this.name = name;
		this.filename = filename;
		this.visible = visible;
	}

	/**
	 * The user given name
	 * @return the user‐given name of this resource
	 */
	public String name() {
		return this.name;
	}

	/**
	 * The file name
	 * 
	 * @return the stored filename for this resource (including extension)
	 */
	public String filename() {
		return this.filename;
	}

	/**
	 *Checks weather resource is visible to regular dungeon players  
	 *
	 * @return {@code true} if non‐DM players may see this resource; {@code false}
	 *         otherwise
	 */
	public boolean visible() {
		return this.visible;
	}

	/**
	 * Sets or updates the user‐visible name.
	 *
	 * @param name the new name to display for this resource
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Changes the filename under which this resource is stored. Use with caution:
	 * files must be renamed on disk to match.
	 *
	 * @param filename the new filename (including extension)
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * Updates the visibility flag for non‐DM players.
	 *
	 * @param visible {@code true} to make this resource visible to all players;
	 *                {@code false} to hide it from everyone except the DM
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}