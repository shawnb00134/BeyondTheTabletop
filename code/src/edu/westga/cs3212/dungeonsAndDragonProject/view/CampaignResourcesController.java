package edu.westga.cs3212.dungeonsAndDragonProject.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import edu.westga.cs3212.dungeonsAndDragonProject.model.Resource;
import edu.westga.cs3212.dungeonsAndDragonProject.server.ServerCommunicationHandler;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * The CampaignResourcesController
 * 
 * @author MoriaEL
 * @version Spring 2025
 */
public class CampaignResourcesController {

	@FXML
	private Button focusBtn;

	@FXML
	private TilePane tilePane;

	@FXML
	private Button uploadBtn;

	@FXML
	private Button deleteBtn;

	@FXML
	private Button downloadBtn;

	@FXML
	private Button closeBtn;

	private String campaignId;
	private boolean isDungeonMaster;

	private final ObservableList<ImageCardController> selectedCards =
	        FXCollections.observableArrayList();
	
	private final BooleanProperty hasSelection = new SimpleBooleanProperty(false);

	private Consumer<Resource> focusCallback;

	/**
	 * Configures this controller for the given campaign and user role.
	 * 
	 * @param campaignId the campaign id
	 * @param isDM       if player is dungeon master
	 */
	public void setCampaign(String campaignId, boolean isDM) {
		this.campaignId = campaignId;
		this.isDungeonMaster = isDM;
		this.uploadBtn.setVisible(isDM);
		this.deleteBtn.setVisible(isDM);
	    this.reloadResources();

	}

	/**
	 * Register a callback to be invoked when the user clicks the Focus button.
	 * 
	 * @param cb will be passed the single selected Resource
	 */
	public void setOnFocus(Consumer<Resource> cb) {
		this.focusCallback = cb;
	}

	@FXML
	private void initialize() {
		BooleanBinding oneSelected = Bindings.size(this.selectedCards).isEqualTo(1);

	    this.focusBtn.disableProperty().bind(oneSelected.not());
	    this.deleteBtn.disableProperty().bind(Bindings.isEmpty(this.selectedCards));
	    this.downloadBtn.disableProperty().bind(Bindings.isEmpty(this.selectedCards));
	}

	/**
	 * Reloads resources
	 */
	public void reloadResources() {
		this.tilePane.getChildren().clear();
		this.selectedCards.clear();
		this.hasSelection.set(false);

	    List<Resource> list = ServerCommunicationHandler.listResources(this.campaignId);
	    for (Resource rcse : list) {
	        if (!this.isDungeonMaster && !rcse.visible()) {
	            continue;
	        }
	        this.addCard(rcse);
	    }
	}

	private void addCard(Resource rcse) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ImageCard.fxml"));
			Node card = loader.load();
			ImageCardController ic = loader.getController();
			ic.init(this, this.campaignId, rcse, this.isDungeonMaster);
			this.tilePane.getChildren().add(card);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Toggle selection in our list and repaint the card’s highlight
	 * 
	 * @param card the image card resource
	 */
	public void onCardSelected(ImageCardController card) {
		boolean nowSelected;
		if (this.selectedCards.contains(card)) {
			this.selectedCards.remove(card);
			nowSelected = false;
		} else {
			this.selectedCards.add(card);
			nowSelected = true;
		}
		card.setSelected(nowSelected);
		this.hasSelection.set(!this.selectedCards.isEmpty());
		

	}

	@FXML
	private void handleUpload() {
		Stage stage = (Stage) this.uploadBtn.getScene().getWindow();
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
		File file = fc.showOpenDialog(stage);
		if (file == null) {
			return;
		}
		TextInputDialog nameDialog = new TextInputDialog();
		nameDialog.setTitle("Name Your Image");
		nameDialog.setHeaderText("Enter a name for this image:");
		nameDialog.setContentText("Image Name:");
		Optional<String> result = nameDialog.showAndWait();
		result.ifPresent(name -> {
			String imageName = name.trim();
			if (!imageName.isEmpty()) {
				try {
					String original = file.getName();
					String ext = "";
					int dot = original.lastIndexOf('.');
					if (dot >= 0) {
						ext = original.substring(dot);
					}

					String base = imageName;
					String filenameWithExt = base + ext;

					ServerCommunicationHandler.uploadResource(this.campaignId, filenameWithExt, file.toPath());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				this.reloadResources();
			}
		});
	}

	@FXML
	private void handleDeleteSelected() {
		if (this.selectedCards.isEmpty()) {
			return;
		}
		for (ImageCardController card : new ArrayList<>(this.selectedCards)) {
			ServerCommunicationHandler.deleteResource(this.campaignId, card.getResource().filename());
		}
		this.reloadResources();
	}

	@FXML
	private void handleDownloadSelected() {
		if (this.selectedCards.isEmpty()) {
			return;
		}

		Stage stage = (Stage) this.downloadBtn.getScene().getWindow();
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("ZIP Archive", "*.zip"));
		fc.setInitialFileName("resources.zip");
		File zipFile = fc.showSaveDialog(stage);
		if (zipFile == null) {
			return;
		}

		try (FileOutputStream fos = new FileOutputStream(zipFile); ZipOutputStream zos = new ZipOutputStream(fos)) {

			for (ImageCardController card : this.selectedCards) {
				String filename = card.getResource().filename();
				byte[] data = ServerCommunicationHandler.downloadResource(this.campaignId, filename);

				ZipEntry entry = new ZipEntry(filename);
				zos.putNextEntry(entry);
				zos.write(data);
				zos.closeEntry();
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	private void handleFocus() {
		if (this.selectedCards.size() == 1 && this.focusCallback != null) {
			focusCallback.accept(this.selectedCards.get(0).getResource());
		}
	}

	@FXML
	private void handleClose() {
		((Stage) this.closeBtn.getScene().getWindow()).close();
	}
}
