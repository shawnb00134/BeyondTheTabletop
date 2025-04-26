package edu.westga.cs3212.dungeonsAndDragonProject.view;

import java.io.IOException;
import java.util.Optional;

import edu.westga.cs3212.dungeonsAndDragonProject.model.Campaign;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Character;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Player;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Request;
import edu.westga.cs3212.dungeonsAndDragonProject.model.RequestType;
import edu.westga.cs3212.dungeonsAndDragonProject.server.ServerCommunicationHandler;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.CampaignCreationViewModel;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.SystemContextViewModel;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**Codebehind for Campaign Manager
 * @author Kelis Gates
 * @version Spring 2025
 */
public class CampaignManagerCodeBehind {

	@FXML
	private Button buttonCampaignManager;

	@FXML
	private Button buttonCharacterManager;

	@FXML
	private Button buttonProfile;

	@FXML
	private TabPane campaignTabPane;

	@FXML
	private Button createButton;

	@FXML
	private ListView<Request> incomingRequest;

	@FXML
	private Tab myCampaignTab;

	@FXML
	private Label nameCampaignLabel;

	@FXML
	private Label playerCountLabel;
	
	@FXML
    private Label numberOfCampaignsLabel;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private Label startDateLabel;
    
    private CampaignCreationViewModel vm;
    private SceneController sceneController;
    
    private AnchorPane anchorPane;
    private double nextYcoord = 0;
    private Pane selectedCampaignPane;
    
    @FXML
    void initialize() {
    	this.vm = new CampaignCreationViewModel(false);
    	this.sceneController = new SceneController();
    	this.bindProperties();
    	this.eventHandlers();
    	
    	this.changeListeners();
    	
    	this.anchorPane = new AnchorPane();
    	
    	this.scrollPane.setContent(this.anchorPane);
    	
    	this.populateMyCampaigns();
    	
    }
    
    private void populateMyCampaigns() {
    	for (Campaign currCampaign : this.vm.getCampaigns()) {
	        if (currCampaign == null || currCampaign.getPlayers() == null) { 
	            continue;
	        }
	        this.addNewPane(currCampaign, this.anchorPane, this.scrollPane);
	    }
    	
    }
    
	@FXML
    void joinCampaignLobby(MouseEvent event) {
    	
    	Campaign selectedCampaign = (Campaign) this.selectedCampaignPane.getUserData();
    	
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Campaign.fxml"));
            CampaignCodeBehind controller = new CampaignCodeBehind(this.vm, selectedCampaign); 
            loader.setController(controller);
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Create Campaign");
            stage.show();
        } catch (IOException message) {
            message.printStackTrace();
        }
    }

	private void bindProperties() {
		this.incomingRequest.setItems(this.vm.getIncomingReq());
	}

	private void eventHandlers() {
		this.createButton.setOnAction((event)-> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("CampaignCreation.fxml"));
				CampaignCreationCodeBehind controller = new CampaignCreationCodeBehind(this.vm);
                loader.setController(controller);
				Parent root = loader.load();

				Stage stage = new Stage();
				stage.setScene(new Scene(root));
				stage.setTitle("Create Campaign");
				stage.show();
			} catch (IOException message) { 
				
			}

		});
		
		this.eventHeaderButtonsHandler();
		
	}

	private void eventHeaderButtonsHandler() {
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
		
		this.incomingRequest.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
            	Request selectedRequest = this.incomingRequest.getSelectionModel().getSelectedItem();
            	
            	if (selectedRequest != null) {

            		this.handleItemClick(selectedRequest);
            		Platform.runLater(() -> {
		                this.incomingRequest.getSelectionModel().clearSelection();
		            });

            	} 
            }
        });
	}

	private void changeListeners() {
		this.vm.getCampaigns().addListener((observable, oldValue, newValue)-> {
			
			this.updateCampaignDisplay(newValue, this.anchorPane, this.scrollPane);
		});
		
	}

	private void updateCampaignDisplay(ObservableList<Campaign> newValue, AnchorPane anchor, ScrollPane scrollPane) {
		anchor.getChildren().clear();
		this.nextYcoord = 0;
		
		for (Campaign campaign : newValue) {
			this.addNewPane(campaign, anchor, scrollPane);
		}
	}

    private void addNewPane(Campaign campaign, AnchorPane anchor, ScrollPane scrollPane) {
        Pane newPane = new Pane();
        newPane.setUserData(campaign);
        newPane.setPrefSize(775, 150);
        newPane.setStyle("-fx-background-color: wheat;" + "-fx-border-color: darkgray;");
        
        String owner = "OWNED BY ME";
        Label ownerLabel = new Label(owner);
        ownerLabel.setLayoutX(20);
        ownerLabel.setLayoutY(40);
        ownerLabel.setStyle("-fx-text-fill: red; -fx-font-size: 20px;");

        String name = campaign.getName();
        Label campaignNameLabel = new Label(name);
        campaignNameLabel.setLayoutX(51);
        campaignNameLabel.setLayoutY(67);
        campaignNameLabel.setStyle("-fx-text-fill: black; -fx-font-size: 20px;");

        Label startDateLabel = new Label("Campaign Started: 2025-02-26");
        startDateLabel.setLayoutX(325);
        startDateLabel.setLayoutY(67);
        startDateLabel.setStyle("-fx-text-fill: black; -fx-font-size: 20px;");

        int amount = campaign.getCampaignLimit() - campaign.getPlayers().size();
        String count = String.valueOf("Available Slots: " + amount);
        Label playerCountLabel = new Label(count);
        playerCountLabel.setLayoutX(325);
        playerCountLabel.setLayoutY(100);
        playerCountLabel.setStyle("-fx-text-fill: black; -fx-font-size: 20px;");
        
        this.createButtons(campaign, newPane);
        this.createPane(campaign, newPane, ownerLabel, campaignNameLabel, startDateLabel, playerCountLabel);
     
        this.createContextMenu(campaign, newPane);
        this.placeNewPane(newPane, anchor, scrollPane);
    }
    
    private void createButtons(Campaign campaign, Pane pane) {
    	
    	Button editButton = new Button("Edit");
    	Button deleteButton = new Button("Delete");
    	Button joinButton = new Button("Join");
    	
    	editButton.setLayoutX(650);
    	editButton.setLayoutY(30);
    	editButton.setPrefSize(60, 30);
    	editButton.setFont(new Font(14));
    	
    	joinButton.setLayoutX(650);
    	joinButton.setLayoutY(70);
    	joinButton.setPrefSize(60, 30);
    	joinButton.setFont(new Font(14));
    	
    	deleteButton.setLayoutX(650);
    	deleteButton.setLayoutY(110);
    	deleteButton.setPrefSize(60, 30);
    	deleteButton.setFont(new Font(14));
    	
    	if (SystemContextViewModel.getInstance().getCurrentAccount().getUsername().equals(campaign.getDungeonMaster().getAccountInfo().getUsername())) {
        	pane.getChildren().addAll(joinButton, editButton, deleteButton);
        } else {
        	pane.getChildren().addAll(joinButton);
        }
    	this.campaignButtonActions(campaign, pane, editButton, deleteButton, joinButton);
    }

	private void createPane(Campaign campaign, Pane newPane, Label ownerLabel, Label campaignNameLabel,
			Label startDateLabel, Label playerCountLabel) {
		if (SystemContextViewModel.getInstance().getCurrentAccount().getUsername().equals(campaign.getDungeonMaster().getAccountInfo().getUsername())) {
        	newPane.getChildren().addAll(ownerLabel, campaignNameLabel, startDateLabel, playerCountLabel);
        } else {
        	newPane.getChildren().addAll(campaignNameLabel, startDateLabel, playerCountLabel);
        }

	}

	private void placeNewPane(Pane newPane, AnchorPane anchor, ScrollPane scrollPane) {
		newPane.setLayoutY(this.nextYcoord);
        this.nextYcoord += 185;
        
        if (scrollPane.getContent() instanceof AnchorPane) {
            anchor = (AnchorPane) scrollPane.getContent();  
            anchor.getChildren().add(newPane);
            Platform.runLater(() -> scrollPane.requestLayout());
        } 
        
	}

	private void campaignButtonActions(Campaign campaign, Pane newPane, Button editButton, Button deleteButton, 
			Button joinButton) {
		editButton.setOnAction((event)-> {
			Campaign selectedCampaign = (Campaign) newPane.getUserData(); 

			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("CampaignCreation.fxml"));
				CampaignCreationCodeBehind controller = new CampaignCreationCodeBehind(this.vm, selectedCampaign);
				loader.setController(controller);
				Parent root = loader.load();

				Stage stage = new Stage();
				stage.setScene(new Scene(root));
				stage.setTitle("Edit Campaign");
				stage.show();
			} catch (IOException message) { 
				message.printStackTrace();
			}
		});
		
		deleteButton.setOnAction((event)-> {
			this.vm.getCampaigns().remove(campaign);
        	ServerCommunicationHandler.removeCampaign(campaign);
//        	SystemContextViewModel.getInstance().removeCampaign(campaign);
            if (newPane.getParent() instanceof AnchorPane) {
                AnchorPane parentPane = (AnchorPane) newPane.getParent();
                parentPane.getChildren().remove(newPane);
            } 
		});
		
		joinButton.setOnAction((event)-> {
			this.checkContextJoin(newPane);
		});
		
		
	}
	private void createContextMenu(Campaign campaign, Pane newPane) {
		ContextMenu contextMenu = new ContextMenu();
        MenuItem contextEdit = new MenuItem("Edit");
        MenuItem contextDelete = new MenuItem("Delete");
        MenuItem contextJoin = new MenuItem("Join" + " " + campaign.getName());
        
        this.contextMenuActions(campaign, newPane, contextEdit, contextDelete, contextJoin);

        this.addContextItems(campaign, contextMenu, contextEdit, contextDelete, contextJoin);
        
        newPane.setOnMousePressed(event -> {
            if (event.isSecondaryButtonDown()) {  
                contextMenu.show(newPane, event.getScreenX(), event.getScreenY());
            } else if (event.getButton() == MouseButton.PRIMARY) { 
                this.selectedCampaignPane = newPane;  
            }
        });
	}

	private void contextMenuActions(Campaign campaign, Pane newPane, MenuItem contextEdit, MenuItem contextDelete,
			MenuItem contextJoin) {
		contextEdit.setOnAction(event -> {
			Campaign selectedCampaign = (Campaign) newPane.getUserData(); 

			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("CampaignCreation.fxml"));
				CampaignCreationCodeBehind controller = new CampaignCreationCodeBehind(this.vm, selectedCampaign);
				loader.setController(controller);
				Parent root = loader.load();

				Stage stage = new Stage();
				stage.setScene(new Scene(root));
				stage.setTitle("Edit Campaign");
				stage.show();
			} catch (IOException message) { 
				message.printStackTrace();
			}
        });

        contextDelete.setOnAction(event -> {
        	this.vm.getCampaigns().remove(campaign);
//        	SystemContextViewModel.getInstance().removeCampaign(campaign);
        	ServerCommunicationHandler.removeCampaign(campaign);

            if (newPane.getParent() instanceof AnchorPane) {
                AnchorPane parentPane = (AnchorPane) newPane.getParent();
                parentPane.getChildren().remove(newPane);
            } 
        });
        
        contextJoin.setOnAction((event)-> {
        	this.checkContextJoin(newPane);
        });
        
	}

	private void checkContextJoin(Pane newPane) {
		this.selectedCampaignPane = newPane;
		Campaign selectedCampaign = (Campaign) this.selectedCampaignPane.getUserData();
		if (SystemContextViewModel.getInstance().getCurrentAccount().getUsername().equals(selectedCampaign.getDungeonMaster().getAccountInfo().getUsername())) {
			this.openCampaignLobby(selectedCampaign);
			return;
		}
		boolean result = selectedCampaign.checkPlayerAlreadyInCampaign(SystemContextViewModel.getInstance().getCurrentAccount());
		if (!result) {
			selectedCampaign.checkCampaignLimit();
			
		} else if (result) {
			this.openCampaignLobby(selectedCampaign);
		}
	}

	private void loadCharacterSelectionScreen(Campaign selectedCampaign, Request request) {
		Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); 
        popupStage.setTitle("Select Character To Join With:");
        Player player = new Player(SystemContextViewModel.getInstance().getCurrentAccount());
        ListView<Character> selection = new ListView<Character>();
        selection.setItems(this.vm.getCharacters());
        Button submitButton = new Button("Submit");
        
        Button cancelButton = new Button("Cancel");
        
        submitButton.disableProperty().bind(
        		selection.getSelectionModel().selectedItemProperty().isNull()
        		);
        
        cancelButton.setOnAction((event) -> {
        	popupStage.close();
        	this.vm.setRequestCompleted(false);
        	
        	Platform.runLater(() -> this.incomingRequest.getSelectionModel().clearSelection());
        });
        
        submitButton.setOnAction((event)-> {
        	this.joinCampaignRequest(selectedCampaign, request, popupStage, player, selection);
        });

        VBox layout = new VBox(10, selection, submitButton, cancelButton);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        Scene popupScene = new Scene(layout, 250, 150);

        popupStage.setScene(popupScene);
        
        popupStage.setOnHidden((event)-> {
        	Platform.runLater(() -> this.incomingRequest.getSelectionModel().clearSelection());
        });
        
        popupStage.showAndWait(); 
		
	}

	private void joinCampaignRequest(Campaign selectedCampaign, Request request, Stage popupStage, Player player,
			ListView<Character> selection) {
		this.vm.setRequestCompleted(true);
		Character character = selection.getSelectionModel().getSelectedItem();
		selectedCampaign.addCharacterToPlayer(character.getCharacterName(), player.getAccountInfo().getAccountId());
		
		if (selectedCampaign.checkIfCharacterIsAdded(this.vm.getPlayerOfCurrentAccount())) {
			this.vm.getPlayerOfCurrentAccount().getAccountInfo().getIncomingRequests().remove(request);
			this.vm.getCampaigns().add(selectedCampaign);
			selectedCampaign.addPlayer(this.vm.getPlayerOfCurrentAccount());
			selectedCampaign.checkCampaignLimit();
			SystemContextViewModel.getInstance().overWriteCampaign(selectedCampaign); //TODO make connected to server
			popupStage.close();
			this.openCampaignLobby(selectedCampaign);
		}
	}

	private void addContextItems(Campaign campaign, ContextMenu contextMenu, MenuItem contextEdit,
			MenuItem contextDelete, MenuItem contextJoin) {
	    
	    if (SystemContextViewModel.getInstance().getCurrentAccount().getUsername().equals(campaign.getDungeonMaster().getAccountInfo().getUsername())) {
	        contextMenu.getItems().addAll(contextJoin, contextEdit, contextDelete);
	    } else {
	        contextMenu.getItems().addAll(contextJoin);
	    } 
        
	}

	private void openCampaignLobby(Campaign selectedCampaign) {
		this.vm.setCampaign(selectedCampaign);
		
		try {
			System.out.println("TRY ATTEMPT");
			Stage currentStage = (Stage) this.campaignTabPane.getScene().getWindow();
			currentStage.close();
			
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("Campaign.fxml"));
		    CampaignCodeBehind controller = new CampaignCodeBehind(this.vm, selectedCampaign); 
		    loader.setController(controller);
		    Parent root = loader.load();

		    Stage stage = new Stage();
		    stage.setScene(new Scene(root));
		    stage.setTitle(selectedCampaign.getName());
		    stage.show();
		} catch (IOException message) {
		    message.printStackTrace();
		}
	}

	private void handleItemClick(Request selectedItem) {
		
		if (selectedItem != null) {
			this.showPopup(selectedItem);
		}
		
	}
	
	private void showPopup(Request request) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Incoming Request");
		alert.setHeaderText("New request received:");
		alert.setContentText("request from: " + this.vm.findAccountById(request.getFromAccountId()).getUsername() + " to join: " + request.getCampaign());

		ButtonType acceptButton = new ButtonType("Accept");
		ButtonType denyButton = new ButtonType("Deny");
		alert.getButtonTypes().setAll(acceptButton, denyButton);

		Optional<ButtonType> result = alert.showAndWait();
		result.ifPresent(button -> {
			if (button == acceptButton) {
				this.handleAccept(request);
			} else if (button == denyButton) {
				this.handleDeny(request);
			}
		});
	}
	
	/**Handles logic for accepting invitation
	 * 
	 * @precondition none
	 * @postcondition player is now apart of campaign, files overwritten 
	 * 
	 * @param request the request sent to account
	 */
	public void handleAccept(Request request) {
		request.accept();

	    if (request.getRequestType() == RequestType.CAMPAIGN_INVITE) {
	        Campaign result = this.vm.findCampaignByName(request.getCampaign());
	        if (result != null) {
	            
	            this.loadCharacterSelectionScreen(result, request);
	        }
	    }

	    Platform.runLater(() -> {
	    	if (this.vm.isRequestCompleted()) {
	    		this.vm.getIncomingReq().remove(request);
		        this.vm.overWriteCampaignAndRequests(request, true);
		        this.vm.setRequestCompleted(false);
	    	}
	    });
	}

	/**Handles logic for denying invitation
	 * 
	 * @precondition none
	 * @postcondition file overwritten
	 * 
	 * @param request the request sent to account
	 */
	public void handleDeny(Request request) {

		request.reject();
		this.vm.setRequestCompleted(true);

	    Platform.runLater(() -> {
	    	if (this.vm.isRequestCompleted()) {
	    		this.vm.getIncomingReq().remove(request);
		        this.vm.overWriteCampaignAndRequests(request, true);
		        this.vm.setRequestCompleted(false);
	    	}
	    });
	}
	
}
