package edu.westga.cs3212.dungeonsAndDragonProject.view;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.scene.Parent;
import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Campaign;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Character;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Player;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Request;
import edu.westga.cs3212.dungeonsAndDragonProject.model.RequestType;
import edu.westga.cs3212.dungeonsAndDragonProject.server.ServerCommunicationHandler;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.CampaignCreationViewModel;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.SystemContextViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**Codebehind for campaign lobby
 * @author kelis gates
 * @version Spring 2025
 */
public class CampaignCodeBehind {
	
	@FXML
    private ContextMenu PlayerMenu;

    @FXML
    private ListView<Player> playerListView;

    @FXML
    private Button buttonCampaignManager;

    @FXML
    private Button buttonCharacterManager;

    @FXML
    private Button buttonInvitePlayer;

    @FXML
    private Button buttonProfile;
    
    @FXML
    private Label labelDungeonMaster;

    @FXML
    private MenuItem removePlayerContext;
    
    @FXML
    private MenuItem promotePlayer;
    
    @FXML
    private ImageView imageView;

    @FXML
    private Label resourcesLabel;

    @FXML
    private Button showResourcesButton;

    @FXML
    private TextArea sharedNotesTextArea;
    
    @FXML
    private Button sharedNotesButton;
    
    @FXML
    private Button dungeonMasterNotesButton;

    @FXML
    private TextArea dungeonMasterNotesTextArea;
    
    @FXML
    private Tab dungeonMasterNotesTab;
    
    @FXML
    private AnchorPane anchorPane;
    

    
    private CampaignCreationViewModel vm;
    private Campaign currentCampaign;
    private SceneController sceneController;
    private FileChooser chooser;
    
    /**Initiates campaign codebehind class
     * 
     * @precondition none
	 * @postcondition none
     * 
     * @param sharedViewModel the campaign manager view model
     * @param campaign the associated campaign
     */
    public CampaignCodeBehind(CampaignCreationViewModel sharedViewModel, Campaign campaign) {
        this.vm = sharedViewModel;
        this.currentCampaign = campaign;
        this.sceneController = new SceneController();
        this.intializeFileChooser();
        
    }

	private void intializeFileChooser() {
		
		this.chooser = new FileChooser();
        this.chooser.setTitle("Select File");
        
        this.chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Supported Files", "*.png", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
            );
	}
    
    @FXML
    void initialize() {
    	
    	this.bindProperties();
    	this.eventHandlers();
    	this.navigationEventHandlers();
    	
    	this.changePlayerDisplay(); 
    	
    	if (!(this.currentCampaign.getDungeonMaster().getAccountInfo().getUsername().equals(
        		SystemContextViewModel.getInstance().getCurrentAccount().getUsername()))) {
        	this.buttonInvitePlayer.setDisable(true);
        	this.removePlayerContext.setDisable(true);
        	this.promotePlayer.setDisable(true);
        	this.dungeonMasterNotesTab.setDisable(true);
        	this.dungeonMasterNotesTextArea.setVisible(false);
        	boolean result = this.currentCampaign.checkIfPlayerHasSpecialPermissions(SystemContextViewModel.getInstance().getCurrentAccount().getAccountId());
        	if (!result) {
        		this.sharedNotesButton.setDisable(true);
        		this.sharedNotesTextArea.setDisable(true);
        	} 
        } else if (!this.currentCampaign.isOpen()) {
        	this.buttonInvitePlayer.setDisable(true);
        }
    }

	private void changePlayerDisplay() {
    	
        this.playerListView.itemsProperty().bind(this.vm.getPlayers());
	}

	private void bindProperties() {
		this.playerListView.setItems(this.vm.getPlayersInCampaign(this.currentCampaign));
		String name = this.currentCampaign.getDungeonMaster().getAccountInfo().getUsername();
		this.labelDungeonMaster.textProperty().set("Dungeon Master: " + name);
		
		this.sharedNotesTextArea.setText(this.currentCampaign.getSharedNotesText());
		this.dungeonMasterNotesTextArea.setText(this.currentCampaign.getDungeonMasterNotesText());
		
	}

	private void eventHandlers() {
		this.buttonInvitePlayer.setOnAction((event)-> {
			Stage stage = (Stage) this.buttonInvitePlayer.getScene().getWindow();
			this.showPlayerInvitePopup(stage);
			
		});
		
		this.showResourcesButton.setOnAction(event -> {
		    try {
		        FXMLLoader loader = new FXMLLoader(
		                getClass().getResource("CampaignResourcesView.fxml"));
		        Parent root = loader.load();

		        CampaignResourcesController rc = loader.getController();
		        boolean amIDm = this.currentCampaign.getDungeonMaster()
		                            .getAccountInfo().getUsername()
		                            .equals(SystemContextViewModel.getInstance()
		                                    .getCurrentAccount().getUsername());
		        rc.setCampaign(this.currentCampaign.getName(), amIDm);

		        rc.setOnFocus(res -> {
		            resourcesLabel.setText(res.name());           
		            byte[] data = ServerCommunicationHandler.downloadResource(
		                               currentCampaign.getName(), res.filename());
		            imageView.setImage(new Image(new ByteArrayInputStream(data)));
		        });
		        
		        Stage popup = new Stage(StageStyle.UTILITY);
		        popup.initModality(Modality.APPLICATION_MODAL);
		        popup.setTitle("Resources: " + this.currentCampaign.getName());
		        popup.setScene(new Scene(root));
		        popup.showAndWait();

		    } catch (IOException ex) {
		        ex.printStackTrace();
		    }
		});
		
		this.playerListView.setOnMousePressed(event -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				Player selectedPlayer = this.playerListView.getSelectionModel().getSelectedItem();
				if (selectedPlayer != null) {
					Stage stage = (Stage) this.buttonInvitePlayer.getScene().getWindow();
					this.showPlayerListViewPopUp(stage, selectedPlayer);
				} else {
					System.out.println("No player selected.");
				}
			}
        });
		
		this.sharedNotesButton.setOnAction((event)-> {
			String noteText = this.sharedNotesTextArea.getText();  
			this.currentCampaign.setSharedNotesText(noteText);
			ServerCommunicationHandler.overwriteCampaign(this.currentCampaign);
		});
		
		this.dungeonMasterNotesButton.setOnAction((event)-> {
			String noteText = this.dungeonMasterNotesTextArea.getText();  
			this.currentCampaign.setDungeonMasterNotesText(noteText);
			ServerCommunicationHandler.overwriteCampaign(this.currentCampaign);
		});
	}

	private void navigationEventHandlers() {
		this.buttonCampaignManager.setOnAction((ActionEvent event) -> {
    		try {
				this.sceneController.switchToCampaignManager(event);
			} catch (IOException exception) {
				System.err.println("error switching to Campaign Manager scene");
			}
    	});
		
		this.buttonCharacterManager.setOnAction((event)-> {
			try {
				this.sceneController.switchToCharacterManager(event);
			} catch (IOException exception) {
				System.err.println("error switching to Account Creation scene");
			}
		});
		
		this.buttonProfile.setOnAction((event)-> {
			try {
				this.sceneController.switchToProfile(event);
			} catch (IOException exception) {
				System.err.println("error switching to Profile scene");
			}
		});
	}

	private boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg");
    }

	
	private void showPlayerInvitePopup(Stage parentStage) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); 
        popupStage.setTitle("Enter Username Of Account That You Want To Invite");

        TextField textField = new TextField();
        textField.setPromptText("Enter text...");

        Button sendButton = new Button("Send");
        Button cancelButton = new Button("Cancel");
        
        Label sent = new Label("");
        sent.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");

        sendButton.setOnAction(e -> {
            String inputText = textField.getText();
            int num = this.checkOutGoingInvites(inputText);
            this.determineErrorMessage(parentStage, popupStage, textField, sent, inputText, num);
        });

        cancelButton.setOnAction(e -> popupStage.close());

        VBox layout = new VBox(10, textField, sendButton, cancelButton, sent);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        Scene popupScene = new Scene(layout, 250, 150);

        popupStage.setScene(popupScene);
        popupStage.showAndWait(); 
    }

	private void determineErrorMessage(Stage parentStage, Stage popupStage, TextField textField, Label sent,
			String inputText, int num) {
		if (num == 1) {
			sent.setText("already sent request");
		} else if (num == 0) {
		    AccountInfo result = this.vm.sendInviteToPlayer(inputText, this.currentCampaign, parentStage);
		    if (result != null) {
		    	sent.setStyle("-fx-text-fill: green; -fx-font-size: 12px;");
		    	sent.setText("Campaign Request sent to: " + result.getUsername());
		    	textField.setText("");
		    	popupStage.close();
		    } 
		} else if (num == -1) {
			sent.setText("user not found in system.");
		} else if (num == 2) {
			sent.setText("already in campaign");
		}
	}
	
	private void showPlayerListViewPopUp(Stage parentStage, Player selectedPlayer) {
		
		Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); 
        popupStage.setTitle("Options");

        Button kickPlayerButton = new Button("kick player");
        Button promotePlayerButton = new Button("promote to dungeon master");
        Button givePermissionsButton = new Button("give special permissions");
        Button removePermissionsButton = new Button("remove special permissions");
        Button viewCharacterSheetButton = new Button("view character");
        Button cancelButton = new Button("Cancel");
        
        Label error = new Label("Cannot look at others character sheet");
        error.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        
        this.popUpEventHandlers(popupStage, selectedPlayer, kickPlayerButton, promotePlayerButton, givePermissionsButton,
        		viewCharacterSheetButton, cancelButton, removePermissionsButton);
		boolean hasPermission = this.currentCampaign.checkIfPlayerHasSpecialPermissions(selectedPlayer.getAccountInfo().getAccountId());
        if ((this.currentCampaign.getDungeonMaster().getAccountInfo().getUsername().equals(
        		SystemContextViewModel.getInstance().getCurrentAccount().getUsername()))) {
        	this.displayForDM(popupStage, kickPlayerButton, promotePlayerButton, givePermissionsButton,
					removePermissionsButton, cancelButton, hasPermission);
            
        } else {
        	this.displayForPlayer(selectedPlayer, popupStage, viewCharacterSheetButton, cancelButton, error);
        }

        popupStage.showAndWait(); 
	}

	private void displayForPlayer(Player selectedPlayer, Stage popupStage, Button viewCharacterSheetButton,
			Button cancelButton, Label error) {
		if (SystemContextViewModel.getInstance().getCurrentAccount().getAccountId().equals(selectedPlayer.getAccountInfo().getAccountId())) {
			VBox layout = new VBox(10, viewCharacterSheetButton, cancelButton);
			layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
			Scene popupScene = new Scene(layout, 250, 150);
			popupStage.setScene(popupScene);
		} else {
			VBox layout = new VBox(10, error, cancelButton);
			layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
			Scene popupScene = new Scene(layout, 250, 150);
			popupStage.setScene(popupScene);
		}
	}

	private void displayForDM(Stage popupStage, Button kickPlayerButton, Button promotePlayerButton,
			Button givePermissionsButton, Button removePermissionsButton, Button cancelButton, boolean hasPermission) {
		if (!hasPermission) {
			VBox layout = new VBox(10, kickPlayerButton, promotePlayerButton, givePermissionsButton, cancelButton);
			layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
			Scene popupScene = new Scene(layout, 250, 150);
			popupStage.setScene(popupScene);
		} else {
			VBox layout = new VBox(10, kickPlayerButton, promotePlayerButton, removePermissionsButton, cancelButton);
			layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
			Scene popupScene = new Scene(layout, 250, 150);
			popupStage.setScene(popupScene);
		}
	}

	private void popUpEventHandlers(Stage popupStage, Player selectedPlayer, Button kickPlayerButton, Button promotePlayerButton, Button givePermissionsButton,
			Button viewCharacterSheetButton, Button cancelButton, Button removePermissionsButton) {
		
		kickPlayerButton.setOnAction((event)-> {
	        
	        if (selectedPlayer != null) {
	            this.playerListView.getItems().remove(selectedPlayer); 
	            this.currentCampaign.getPlayers().remove(selectedPlayer);
	            this.currentCampaign.getCampaignPlayers().remove(selectedPlayer.getAccountInfo().getAccountId());
	            this.currentCampaign.checkCampaignLimit();
	            ServerCommunicationHandler.overwriteCampaign(this.currentCampaign);
	        }
		});
		
		promotePlayerButton.setOnAction((event)-> {
			if (selectedPlayer != null) {
				this.currentCampaign.setDungeonMaster(selectedPlayer);
				this.playerListView.getItems().remove(selectedPlayer); 
	            this.currentCampaign.getPlayers().remove(selectedPlayer);
	            this.currentCampaign.getCampaignPlayers().remove(selectedPlayer.getAccountInfo().getAccountId());
	            this.currentCampaign.checkCampaignLimit();
	            
	            ServerCommunicationHandler.overwriteCampaign(this.currentCampaign);
				this.buttonInvitePlayer.setDisable(true);
	        	this.showResourcesButton.setDisable(true);
	        	this.removePlayerContext.setDisable(true);
	        	this.promotePlayer.setDisable(true);
	        	
	        	popupStage.close();
				Stage currentStage = (Stage) this.imageView.getScene().getWindow();
				currentStage.close();
				ActionEvent actionEvent = new ActionEvent(event.getSource(), this.anchorPane);
				
				try {
					this.sceneController.switchToCampaignManager(actionEvent);
				} catch (IOException exception) {
					System.err.println("error switching to Campaign Manager scene");
				}
			}
		});
		
		givePermissionsButton.setOnAction((event)-> {
			if (selectedPlayer != null) {
				String accountID = selectedPlayer.getAccountInfo().getAccountId();
				this.currentCampaign.addPlayerToSpecialPermissions(accountID);
				ServerCommunicationHandler.overwriteCampaign(this.currentCampaign);
				
			}
		});
		
		removePermissionsButton.setOnAction((event)-> {
			if (selectedPlayer != null) {
				String accountID = selectedPlayer.getAccountInfo().getAccountId();
				this.currentCampaign.removeSpecialPermission(accountID);
				ServerCommunicationHandler.overwriteCampaign(this.currentCampaign);
				
			}
		});
		
		viewCharacterSheetButton.setOnAction((event)-> {
			popupStage.close();
			ActionEvent actionEvent = new ActionEvent(event.getSource(), this.anchorPane);
			String characterName = this.currentCampaign.getCampaignPlayers().get(selectedPlayer.getAccountInfo().getAccountId());
			Character result = this.vm.findCharacter(characterName);
			if (result != null) {
				SystemContextViewModel.getInstance().setCharacterSelection(result);

				try {

					this.sceneController.switchToCharacterViewer(actionEvent);
				} catch (IOException exception) {
					System.err.println("error switching to Character View scene");
				}
			}
			
			
		});
		
		cancelButton.setOnAction(e -> popupStage.close());
		
	}
	
	private int checkOutGoingInvites(String inputText) {
		List<AccountInfo> loadedAccounts = ServerCommunicationHandler.loadAccounts(); 
		AccountInfo result = null;
		for (AccountInfo curr: loadedAccounts) {
			if (curr.getUsername().equals(inputText)) {
				result = curr;
			}
		}
		
		if (result == null) {
			return -1;
		}
		
		for (Player curr: this.currentCampaign.getPlayers()) {
			if (curr.getAccountInfo().getUsername().equals(result.getUsername())) {
				return 2;
			}
		}
		
		
		
		for (Request curr: ServerCommunicationHandler.getOutgoingRequest(SystemContextViewModel.getInstance().getCurrentAccount())) {
			if (curr.getRequestType() == RequestType.CAMPAIGN_INVITE) {
				if (curr.getToAccountId().equals(result.getAccountId())) {
					return 1;
				}
			}
		}
		return 0;
	}

}

