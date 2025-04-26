package edu.westga.cs3212.dungeonsAndDragonProject.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.CharacterViewerViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * The codebehind to the view the character sheet.
 * 
 * @author Shawn Bretthauer
 * 
 * @version Spring 2025
 */
public class CharacterViewerCodeBehind {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Button buttonAddCoin;

    @FXML
    private Button buttonCampaignManager;

    @FXML
    private Button buttonCharacterManager;

    @FXML
    private Button buttonDamageChar;

    @FXML
    private Button buttonHealChar;

    @FXML
    private Button buttonInspiration;

    @FXML
    private Button buttonProfile;
    
    @FXML
    private Button buttonRemoveCoin;
    
    @FXML
    private Button buttonSaveChanges;
    
    @FXML
    private ImageView imageD20;

    @FXML
    private Label labelBonusModifiers;

    @FXML
    private Label labelCharAC;

    @FXML
    private Label labelCharCha;

    @FXML
    private Label labelCharChaBonus;

    @FXML
    private Label labelCharClass;

    @FXML
    private Label labelCharCon;

    @FXML
    private Label labelCharConBonus;

    @FXML
    private Label labelCharDex;

    @FXML
    private Label labelCharDexBonus;

    @FXML
    private Label labelCharHealth;

    @FXML
    private Label labelCharInt;

    @FXML
    private Label labelCharIntBonus;

    @FXML
    private Label labelCharLevel;

    @FXML
    private Label labelCharName;

    @FXML
    private Label labelCharRace;

    @FXML
    private Label labelCharStr;

    @FXML
    private Label labelCharStrBonus;

    @FXML
    private Label labelCharWis;

    @FXML
    private Label labelCharWisBonus;
    
    @FXML
    private Label labelCoin;
    
    @FXML
    private ListView<String> listviewAbilities;
    
    @FXML
    private ListView<String> listviewBackground;
    
    @FXML
    private ListView<String> listviewFeats;

    @FXML
    private ListView<String> listviewInventory;

    @FXML
    private ListView<String> listviewSpells;
    
    @FXML
    private Tab tabAbilities;

    @FXML
    private Tab tabBackground;
    
    @FXML
    private Tab tabFeats;
    
    @FXML
    private Tab tabInventory;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabSpells;
    
    @FXML
    private TextArea textareaCampaignNotes;
    
    @FXML
    private TextField textfieldCoinModifier;

    @FXML
    private TextField textfieldHealthModifier;
    
    private SceneController sceneController;
    private CharacterViewerViewModel cvvm;

    @FXML
    void initialize() {
    	this.sceneController = new SceneController();
    	this.cvvm = new CharacterViewerViewModel();
    	this.eventNavigation();
    	this.eventHandler();
    	
    	this.bindUpper();
    	this.bindLower();
    	
    	this.setInspirationDie();
    }
    
    private void setInspirationDie() {
    	if (this.cvvm.getHasInspirationDie().getValue()) {
			this.buttonInspiration.setStyle("-fx-background-color: white");
			this.imageD20.setStyle("-fx-blend-mode: darken");
		} else {
			this.buttonInspiration.setStyle("-fx-background-color: lightgray");
			this.imageD20.setStyle("-fx-blend-mode: lighten");
		}
    }
    
    private void eventNavigation() {
    	this.buttonCharacterManager.setOnAction((ActionEvent event) -> {
    		try {
				this.sceneController.switchToCharacterManager(event);
			} catch (IOException exception) {
				System.err.println("error switching to Campaign Manager scene");
			}
    	});
    	
    	this.buttonCampaignManager.setOnAction((ActionEvent event) -> {
    		try {
				this.sceneController.switchToCampaignManager(event);
			} catch (IOException exception) {
				System.err.println("error switching to Campaign Manager scene");
			}
    	});
    	
		this.buttonProfile.setOnAction((ActionEvent event)-> {
			try {
				this.sceneController.switchToProfile(event);
			} catch (IOException exception) {
				System.err.println("error switching to Profile scene");
			}
		});
    }
    
    private void eventHandler() {
		this.buttonHealChar.setOnAction((ActionEvent event) -> {
			this.cvvm.healCharacter();
		});
		this.buttonDamageChar.setOnAction((ActionEvent event) -> {
			this.cvvm.damageCharacter();
		});
		this.buttonInspiration.setOnAction((ActionEvent event) -> {
			this.cvvm.updateHasInspiration();
			if (this.cvvm.getHasInspirationDie().getValue()) {
				this.buttonInspiration.setStyle("-fx-background-color: white");
				this.imageD20.setStyle("-fx-blend-mode: darken");
			} else {
				this.buttonInspiration.setStyle("-fx-background-color: lightgray");
				this.imageD20.setStyle("-fx-blend-mode: lighten");
			}
		});
		this.buttonAddCoin.setOnAction((ActionEvent event) -> {
			this.cvvm.addCoinToPurse();
		});
		this.buttonRemoveCoin.setOnAction((ActionEvent event) -> {
			this.cvvm.removeCoinFromPurse();
		});
		this.buttonSaveChanges.setOnAction((ActionEvent event) -> {
			this.cvvm.saveCharacterCurrentStatus();
		});
    }
    
    private void bindUpper() {
    	this.labelCharName.textProperty().bindBidirectional(this.cvvm.getCharName());
    	this.labelCharLevel.textProperty().bindBidirectional(this.cvvm.getCharLevel());
    	this.labelCharRace.textProperty().bindBidirectional(this.cvvm.getCharRace());
    	this.labelCharClass.textProperty().bindBidirectional(this.cvvm.getCharClass());
    	this.labelCharStr.textProperty().bindBidirectional(this.cvvm.getCharStr());
    	this.labelCharStrBonus.textProperty().bindBidirectional(this.cvvm.getCharStrBonus());
    	this.labelCharDex.textProperty().bindBidirectional(this.cvvm.getCharDex());
    	this.labelCharDexBonus.textProperty().bindBidirectional(this.cvvm.getCharDexBonus());
    	this.labelCharCon.textProperty().bindBidirectional(this.cvvm.getCharCon());
    	this.labelCharConBonus.textProperty().bindBidirectional(this.cvvm.getCharConBonus());
    	this.labelCharInt.textProperty().bindBidirectional(this.cvvm.getCharInt());
    	this.labelCharIntBonus.textProperty().bindBidirectional(this.cvvm.getCharIntBonus());
    	this.labelCharWis.textProperty().bindBidirectional(this.cvvm.getCharWis());
    	this.labelCharWisBonus.textProperty().bindBidirectional(this.cvvm.getCharWisBonus());
    	this.labelCharCha.textProperty().bindBidirectional(this.cvvm.getCharCha());
    	this.labelCharChaBonus.textProperty().bindBidirectional(this.cvvm.getCharChaBonus());
    	
    	this.labelCharAC.textProperty().bindBidirectional(this.cvvm.getCharAC());
    	
    	this.labelCharHealth.textProperty().bindBidirectional(this.cvvm.getCharHealth());
    	this.textfieldHealthModifier.textProperty().bindBidirectional(this.cvvm.getHealthModifier());
    }
    
    private void bindLower() {
    	this.labelBonusModifiers.textProperty().bindBidirectional(this.cvvm.getProficiencyBonus());
    	this.listviewInventory.itemsProperty().bindBidirectional(this.cvvm.getInventoryList());
    	this.listviewSpells.itemsProperty().bindBidirectional(this.cvvm.getSpellList());
    	this.listviewAbilities.itemsProperty().bindBidirectional(this.cvvm.getAbilityList());
    	this.listviewFeats.itemsProperty().bindBidirectional(this.cvvm.getFeatList());
    	this.listviewBackground.itemsProperty().bindBidirectional(this.cvvm.getBackgroundList());
    	this.labelCoin.textProperty().bindBidirectional(this.cvvm.getCoinPurse());
    	this.textfieldCoinModifier.textProperty().bindBidirectional(this.cvvm.getCoinModifier());
    	this.textareaCampaignNotes.textProperty().bindBidirectional(this.cvvm.getCharacterCampaignNotes());
    }

}
