package edu.westga.cs3212.dungeonsAndDragonProject.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.westga.cs3212.dungeonsAndDragonProject.model.Character;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.CharacterManagerViewModel;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.SystemContextViewModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

/**
 * Character Manager Codebehind
 * 
 * @author Shawn Bretthauer
 * 
 * @version Spring 2025
 */

public class CharacterManagerCodeBehind {
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonCampaignManager;

    @FXML
    private Button buttonCharacterManager;

    @FXML
    private Button buttonCreateCharacter;

    @FXML
    private Button buttonProfile;

    @FXML
    private AnchorPane characterManagerPane;

    @FXML
    private Label labelCharacterCount;

    @FXML
    private ScrollPane scrollPane;
    
    private CharacterManagerViewModel characterManagerViewModel;
    private SceneController sceneController;
    private AnchorPane anchorPane;
    private double nextYcoord = 0;

    @FXML
    void initialize() {
    	this.characterManagerViewModel = new CharacterManagerViewModel();
    	this.sceneController = new SceneController();
    	
    	assert this.buttonCampaignManager != null : "fx:id=\"buttonCampaignManager\" was not injected: check your FXML file 'CharacterManager.fxml'.";
        assert this.buttonCharacterManager != null : "fx:id=\"buttonCharacterManager\" was not injected: check your FXML file 'CharacterManager.fxml'.";
        assert this.buttonCreateCharacter != null : "fx:id=\"buttonCreateCharacter\" was not injected: check your FXML file 'CharacterManager.fxml'.";
        assert this.buttonProfile != null : "fx:id=\"buttonProfile\" was not injected: check your FXML file 'CharacterManager.fxml'.";
        assert this.characterManagerPane != null : "fx:id=\"characterManagerPane\" was not injected: check your FXML file 'CharacterManager.fxml'.";
        assert this.labelCharacterCount != null : "fx:id=\"labelCharacterCount\" was not injected: check your FXML file 'CharacterManager.fxml'.";
        assert this.scrollPane != null : "fx:id=\"scrollPane\" was not injected: check your FXML file 'CharacterManager.fxml'.";
        
        this.anchorPane = new AnchorPane();
        this.scrollPane.setContent(this.anchorPane);
        
        this.eventHandler();
        this.changeListener();
        this.loadCharacters();
        this.newcharacterButtonProperty();
    }
    
    private void newcharacterButtonProperty() {
    	this.buttonCreateCharacter.disableProperty().bind(this.characterManagerViewModel.getCreateNewCharacterButton());
    }
    
    private void eventHandler() {
    	this.buttonCreateCharacter.setOnAction((ActionEvent event) -> {
    		try {
    			SystemContextViewModel.getInstance().setCharacterSelection(null);
    			event.getSource();	
    			this.scrollPane.getParent();
				this.sceneController.switchToCharacterCreation(event);
			} catch (IOException exception) {
				System.err.println("error switching to Character Creation scene");
			}
    	});
    	
    	this.buttonCampaignManager.setOnAction((ActionEvent event) -> {
    		try {
				this.sceneController.switchToCampaignManager(event);
			} catch (IOException exception) {
				System.err.println("error switching to Campaign Manager scene");
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
    
    private void switchToViewer(MouseEvent event, Character character) {
    	ActionEvent actionEvent = new ActionEvent(event.getSource(), this.anchorPane);
    	SystemContextViewModel.getInstance().setCharacterSelection(character);
    	
    	try {
			
			event.getSource();	
			this.scrollPane.getParent();
			this.sceneController.switchToCharacterViewer(actionEvent);
		} catch (IOException exception) {
			System.err.println("error switching to Character Creation scene");
		}
    }
    
    private void changeListener() {
    	this.characterManagerViewModel.getCharacterList().addListener((obersvable, oldValue, newValue) -> {
    		this.anchorPane.getChildren().clear();
    		this.nextYcoord = 0;
    		
    		this.labelCharacterCount.textProperty().setValue(this.characterManagerViewModel.getCharacterLimitText());
    		
    		for (Character character : newValue) {
    			this.addNewCharacterPane(character);
    		}
    	});
    }
    
    private void loadCharacters() {
    	for (Character currentCharacter : this.characterManagerViewModel.getCharacterList()) {
    		this.labelCharacterCount.textProperty().setValue(this.characterManagerViewModel.getCharacterLimitText());
    		if (currentCharacter != null) {
    			this.addNewCharacterPane(currentCharacter);
    		}
    	}
    	
    	this.labelCharacterCount.textProperty().setValue(this.characterManagerViewModel.getCharacterLimitText());
    }
    
    private void addNewCharacterPane(Character character) {
    	Pane newCharacterPane = new Pane();
    	newCharacterPane.setPrefSize(775, 150);
    	newCharacterPane.setStyle("-fx-background-color: Wheat;" + "-fx-border-color: darkgray;");
    	
    	ImageView characterImage = new ImageView();
    	characterImage.setId("characterProtrait");
    	characterImage.setLayoutX(0);
    	characterImage.setLayoutY(0);
    	
    	Label characterName = new Label(character.getCharacterName());
    	characterName.setId("characterName");
    	characterName.setLayoutX(200);
    	characterName.setLayoutY(20);
    	characterName.setFont(new Font(25));
    	
    	Label characterSpecies = new Label(character.getCharacterSpecies().getName());
    	characterSpecies.setId("characterSpecies");
    	characterSpecies.setLayoutX(200);
    	characterSpecies.setLayoutY(80);
    	characterSpecies.setFont(new Font(20));
    	
    	Label characterLevel = new Label(Integer.toString(character.getCharacterLevel()));
    	characterLevel.setLayoutX(350);
    	characterLevel.setLayoutY(80);
    	characterLevel.setFont(new Font(20));
    	
    	this.createContextMenu(character, newCharacterPane);
    	this.createButtons(character, newCharacterPane);
    	
    	newCharacterPane.getChildren().add(characterImage);
    	newCharacterPane.getChildren().add(characterName);
    	newCharacterPane.getChildren().add(characterSpecies);
    	newCharacterPane.getChildren().add(characterLevel);
    	
    	AnchorPane.setTopAnchor(newCharacterPane, this.nextYcoord);
    	this.nextYcoord += 150;
    	
    	this.placePane(newCharacterPane);
    }
    
    private void createButtons(Character character, Pane pane) {
    	Button editButton = new Button("Edit");
    	Button deleteButton = new Button("Delete");
    	
    	editButton.setLayoutX(650);
    	editButton.setLayoutY(30);
    	editButton.setPrefSize(60, 30);
    	editButton.setFont(new Font(14));
    	
    	deleteButton.setLayoutX(650);
    	deleteButton.setLayoutY(70);
    	deleteButton.setPrefSize(60, 30);
    	deleteButton.setFont(new Font(14));
    	
    	pane.getChildren().add(editButton);
    	pane.getChildren().add(deleteButton);
    	
    	this.buttonActions(character, pane, editButton, deleteButton);
    }
    
    private void buttonActions(Character character, Pane pane, Button editButton, Button deleteButton) {
    	editButton.setOnAction((ActionEvent event) -> {
    		try {
    			SystemContextViewModel.getInstance().setCharacterSelection(character);
    			ActionEvent paneEvent = new ActionEvent(pane, null);
	            this.sceneController.switchToCharacterCreation(paneEvent);
    		} catch (IOException exception) {
    			System.err.println("Error switching to Character Creation scene");
    		}
    	});
    	
    	deleteButton.setOnAction((ActionEvent event) -> {
    		try {
    			Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + character.getCharacterName() + " ?", ButtonType.YES, ButtonType.CANCEL);
    			alert.showAndWait();
    			
    			if (alert.getResult() == ButtonType.YES) {
        			this.characterManagerViewModel.deleteCharacter(character);
            		SystemContextViewModel.getInstance().removeCharacter(character);
            		
            		if (pane.getParent() instanceof AnchorPane) {
            			AnchorPane parentPane = (AnchorPane) pane.getParent();
            			parentPane.getChildren().remove(pane);
            		}
        		}
    		} catch (Exception exception) {
    			exception.printStackTrace();
    		}
    	});
    }
    
    private void createContextMenu(Character character, Pane pane) {
    	ContextMenu optionContextMenu = new ContextMenu();
    	MenuItem contextEdit = new MenuItem("Edit");
    	MenuItem contextDelete = new MenuItem("Delete");
    	
    	this.contextMenuActions(character, pane, contextEdit, contextDelete);
    	this.addContextItems(character, optionContextMenu, contextEdit, contextDelete);
    	
    	//Contextmenu issue around here
    	pane.setOnMousePressed(event -> {
    		if (event.isSecondaryButtonDown()) {
    			optionContextMenu.show(pane, event.getSceneX(), event.getSceneY());
    		} else if (event.getButton() == MouseButton.PRIMARY) {
    			SystemContextViewModel.getInstance().setCharacterSelection(character);
    			this.switchToViewer(event, character);
    		}
    	});
    }
    
    private void contextMenuActions(Character character, Pane pane, MenuItem contextEdit, MenuItem contextDelete) {
    	contextEdit.setOnAction(event -> {
    		try {
    			SystemContextViewModel.getInstance().setCharacterSelection(character);
	            ActionEvent paneEvent = new ActionEvent(pane, null);
	            this.sceneController.switchToCharacterCreation(paneEvent);
			} catch (IOException exception) {
				System.out.println(exception);
				exception.printStackTrace();
				System.err.println("error switching to Character Editor scene");
			}
    	});
    	
    	contextDelete.setOnAction(event -> {
    		this.characterManagerViewModel.deleteCharacter(character);
    		SystemContextViewModel.getInstance().removeCharacter(character);
    		
    		if (pane.getParent() instanceof AnchorPane) {
    			AnchorPane parentPane = (AnchorPane) pane.getParent();
    			parentPane.getChildren().remove(pane);
    		}
    	});
    }
    
    private void addContextItems(Character character, ContextMenu contextMenu, MenuItem contextEdit, MenuItem contextDelete) {
    	contextMenu.getItems().addAll(contextEdit, contextDelete);
    }
    
    private void placePane(Pane pane) {
    	if (this.scrollPane.getContent() instanceof AnchorPane) {
    		this.anchorPane = (AnchorPane) this.scrollPane.getContent();
    		this.anchorPane.getChildren().add(pane);
    		Platform.runLater(() -> this.scrollPane.requestLayout());
    	}
    }
}
