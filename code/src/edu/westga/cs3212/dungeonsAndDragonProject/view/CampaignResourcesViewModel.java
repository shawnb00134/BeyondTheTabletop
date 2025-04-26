package edu.westga.cs3212.dungeonsAndDragonProject.view;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import edu.westga.cs3212.dungeonsAndDragonProject.model.Resource;
import edu.westga.cs3212.dungeonsAndDragonProject.server.ServerCommunicationHandler;
import javafx.beans.property.*;
import javafx.collections.*;

/**
 * MVVM backing for the Campaign Resources pane.
 * 
 * @author MoriaEL
 * @version Spring 2025
 */
public class CampaignResourcesViewModel {

	private final StringProperty campaign = new SimpleStringProperty();
	private final BooleanProperty isDungeonMaster = new SimpleBooleanProperty();
	private final ObservableList<Resource> resources = FXCollections.observableArrayList();

	public ReadOnlyStringProperty campaignProperty() {
		return campaign;
	}

	public void setCampaign(String c) {
		campaign.set(c);
	}

	public ReadOnlyBooleanProperty isDungeonMasterProperty() {
		return isDungeonMaster;
	}

	public void setDungeonMaster(boolean dm) {
		isDungeonMaster.set(dm);
	}

	public ObservableList<Resource> getResources() {
		return resources;
	}

	/** Load from server into the observable list. */
	public void loadResources() {
		List<Resource> list = ServerCommunicationHandler.listResources(campaign.get());
		resources.setAll(list);
	}

	/** Uploads a new file to server and metadata. */
	public void uploadResource(Path filePath, String name) throws IOException {
		ServerCommunicationHandler.uploadResource(campaign.get(), name, filePath);
		loadResources();
	}

	/** Deletes metadata + file (via send remove resource request). */
	public void deleteResource(Resource r) {
		ServerCommunicationHandler.deleteResource(campaign.get(), r.filename());
		this.loadResources();
	}

	/** Toggle visibility & persist. */
	public void toggleVisibility(Resource r) {
		Boolean newVis = ServerCommunicationHandler.toggleVisibility(campaign.get(), r.filename());
		if (newVis != null) {
			r = new Resource(r.name(), r.filename(), newVis);
			this.loadResources();
		}
	}
}
