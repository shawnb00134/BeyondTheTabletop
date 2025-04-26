package edu.westga.cs3212.dungeonsAndDragonProject.view;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Resource;
import edu.westga.cs3212.dungeonsAndDragonProject.server.ServerCommunicationHandler;

/**
 * Image Controller for image resource
 * 
 * @author MoriaEl
 * @version Spring 2025
 */
public class ImageCardController {

	@FXML
	private StackPane rootPane;
	
	@FXML
	private VBox rootCard;

	@FXML
	private Label resourceNameLabel;

	@FXML
	private ImageView thumbView;

	@FXML
	private Button visibilityBtn;

	@FXML
	private Button downloadBtn;

	@FXML
	private Button deleteBtn;

	@FXML
	private CheckBox selectCheckBox;

	private Resource resource;
	private String campaignId;
	private CampaignResourcesController parent;

	/**
	 * Initializes this ImageCardController with the given context and resource
	 * data.
	 * 
	 * @param parent          the parent controller
	 * @param campaignId      the campaign ID
	 * @param resource        the resource
	 * @param isDungeonMaster if player is dungeon master
	 */
	public void init(CampaignResourcesController parent, String campaignId, Resource resource,
			boolean isDungeonMaster) {
		this.parent = parent;
		this.campaignId = campaignId;
		this.resource = resource;
		this.resourceNameLabel.setText(resource.name());
		this.visibilityBtn.setVisible(isDungeonMaster);
		this.deleteBtn.setVisible(isDungeonMaster);

		byte[] data = ServerCommunicationHandler.downloadResource(campaignId, resource.filename());
		Image img = new Image(new ByteArrayInputStream(data), 140, 100, true, true);
		this.thumbView.setImage(img);

		this.selectCheckBox.setSelected(false);

		this.updateVisibilityIcon(resource.visible());

		this.rootCard.setOnMouseClicked(e -> parent.onCardSelected(this));
	}

	/**
	 * Used by parent to highlight/un‐highlight this card.
	 * 
	 * @param selected the selected resource
	 */
	public void setSelected(boolean selected) {
		if (selected) {
			if (!this.rootPane.getStyleClass().contains("selected")) {
				this.rootPane.getStyleClass().add("selected");
			}
		} else {
			this.rootPane.getStyleClass().remove("selected");
		}
		this.selectCheckBox.setSelected(selected);
	}

	@FXML
	private void handleToggleVisibility() {
		Boolean newVis = ServerCommunicationHandler.toggleVisibility(this.campaignId, this.resource.filename());
		if (newVis != null) {
			this.updateVisibilityIcon(newVis);
		}
	}

	@FXML
	private void handleDownload() {
		Stage stage = (Stage) this.rootCard.getScene().getWindow();
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters()
				.add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
		fc.setInitialFileName(this.resource.filename());
		File dest = fc.showSaveDialog(stage);
		if (dest == null) {
			return;
		}
		try {
			byte[] data = ServerCommunicationHandler.downloadResource(this.campaignId, this.resource.filename());
			Files.write(dest.toPath(), data);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	private void handleDelete() {
		ServerCommunicationHandler.deleteResource(this.campaignId, this.resource.filename());
		this.parent.reloadResources();
	}

	@FXML
	private void onCheckBoxToggled() {
		boolean now = this.selectCheckBox.isSelected();
		this.setSelected(now);
		this.parent.onCardSelected(this);
	}

	private void updateVisibilityIcon(boolean visible) {
		if (visible) {
			this.visibilityBtn.setText("Invisible 👁̶");
			this.visibilityBtn.getStyleClass().remove("toggle-invisible");
		} else {
			this.visibilityBtn.setText("Visible 👁");
			if (!this.visibilityBtn.getStyleClass().contains("toggle-invisible")) {
				this.visibilityBtn.getStyleClass().add("toggle-invisible");
			}
		}
	}

	/**
	 * Gets the resource
	 * 
	 * @return the resource
	 */
	public Resource getResource() {
		return this.resource;
	}
}
