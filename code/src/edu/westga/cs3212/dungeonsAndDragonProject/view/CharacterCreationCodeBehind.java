package edu.westga.cs3212.dungeonsAndDragonProject.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.westga.cs3212.dungeonsAndDragonProject.model.Race;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Role;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.CharacterCreationViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.NumberStringConverter;

/**
 * Character Creation Codebehind
 * 
 * @author Shawn Bretthauer
 * 
 * @version Spring 2025
 */
public class CharacterCreationCodeBehind {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonAddEquipment;

    @FXML
    private Button buttonAddFeat;

    @FXML
    private Button buttonAddSpells;
    
    @FXML
    private Button buttonCancel1;

    @FXML
    private Button buttonCancel2;

    @FXML
    private Button buttonCancel3;
    
    @FXML
    private Button buttonRemoveEquipment;

    @FXML
    private Button buttonRemoveFeat;

    @FXML
    private Button buttonRemoveSpell;

    @FXML
    private Button buttonSave1;

    @FXML
    private Button buttonSave2;

    @FXML
    private Button buttonSave3;

    @FXML
    private ComboBox<Role> comboClass;

    @FXML
    private ComboBox<Race> comboSpecies;

    @FXML
    private ImageView imageCharacterPortrait;

    @FXML
    private Label labelAddEquipment;

    @FXML
    private Label labelAddFeats;

    @FXML
    private Label labelAddSpells;

    @FXML
    private Label labelAge;

    @FXML
    private Label labelAlignment;

    @FXML
    private Label labelArmorClass;

    @FXML
    private Label labelCharacterName;
    
    @FXML
    private Label labelCharisma;

    @FXML
    private Label labelChooseASpecies;

    @FXML
    private Label labelChooseWeaponMastery;
    
    @FXML
    private Label labelConstitution;

    @FXML
    private Label labelDexterity;
    
    @FXML
    private Label labelEyes;

    @FXML
    private Label labelFaith;

    @FXML
    private Label labelGender;

    @FXML
    private Label labelGold;

    @FXML
    private Label labelHair;
    
    @FXML
    private Label labelHeight;
    
    @FXML
    private Label labelIntelligence;

    @FXML
    private Label labelLevel;
    
    @FXML
    private Label labelMaximumHealth;

    @FXML
    private Label labelPickTwo;

    @FXML
    private Label labelSelectClass;

    @FXML
    private Label labelSkin;
    
    @FXML
    private Label labelStrength;

    @FXML
    private Label labelWeight;
    
    @FXML
    private Label labelWisdom;

    @FXML
    private ListView<String> listviewEquipment;

    @FXML
    private ListView<String> listviewFeats;

    @FXML
    private ListView<String> listviewSpells;

    @FXML
    private Tab tabBackground;

    @FXML
    private Tab tabBasics;

    @FXML
    private Tab tabClass;

    @FXML
    private TabPane tabPane;

    @FXML
    private TextField textboxCharacterName;

    @FXML
    private TextField textfieldAge;

    @FXML
    private TextField textfieldAlignment;

    @FXML
    private TextField textfieldArmorClass;

    @FXML
    private TextField textfieldCharisma;

    @FXML
    private TextField textfieldConstitution;

    @FXML
    private TextField textfieldDexterity;

    @FXML
    private TextField textfieldEquipment;

    @FXML
    private TextField textfieldEyes;

    @FXML
    private TextField textfieldFaith;

    @FXML
    private TextField textfieldFeat;

    @FXML
    private TextField textfieldGender;

    @FXML
    private TextField textfieldGold;

    @FXML
    private TextField textfieldHair;

    @FXML
    private TextField textfieldHeight;

    @FXML
    private TextField textfieldIntelligence;

    @FXML
    private TextField textfieldLevel;
    
    @FXML
    private TextField textfieldMaxHP;

    @FXML
    private TextField textfieldProficiency1;

    @FXML
    private TextField textfieldProficiency2;

    @FXML
    private TextField textfieldSkin;

    @FXML
    private TextField textfieldSpells;

    @FXML
    private TextField textfieldStrength;

    @FXML
    private TextField textfieldWeaponMastery1;

    @FXML
    private TextField textfieldWeaponMastery2;

    @FXML
    private TextField textfieldWeight;

    @FXML
    private TextField textfieldWisdom;

    private SceneController sceneController;
    private CharacterCreationViewModel characterCreationViewModel;
    
    @FXML
    void initialize() {
    	this.sceneController = new SceneController();
    	this.characterCreationViewModel = new CharacterCreationViewModel();
    	this.bindBasicTab();
    	this.bindClassTab();
    	this.bindBackgroundTab();
    	
    	this.buttonDisableProperty();
    	this.numberValidationListener();
    	
    	this.addRemoveButtons();
    	this.eventHandler();
    	this.listener();
    }
    
    private void buttonDisableProperty() {
    	this.buttonSave1.disableProperty().bind(this.textboxCharacterName.textProperty().isEmpty()
    			.or(this.comboClass.getSelectionModel().selectedItemProperty().isNull())
    			.or(this.comboSpecies.getSelectionModel().selectedItemProperty().isNull()));
    	
    	this.buttonSave2.disableProperty().bind(this.textboxCharacterName.textProperty().isEmpty()
    			.or(this.comboClass.getSelectionModel().selectedItemProperty().isNull())
    			.or(this.comboSpecies.getSelectionModel().selectedItemProperty().isNull()));
    	
    	this.buttonSave3.disableProperty().bind(this.textboxCharacterName.textProperty().isEmpty()
    			.or(this.comboClass.getSelectionModel().selectedItemProperty().isNull())
    			.or(this.comboSpecies.getSelectionModel().selectedItemProperty().isNull()));
    }
    
    private void listener() {
    	this.comboClass.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
    		this.characterCreationViewModel.displayClassInformation(newValue);
    	});
    	
    	this.comboSpecies.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
    		this.characterCreationViewModel.displaySpeciesInformation(newValue);
    	});
    	
    	this.listviewEquipment.getSelectionModel().selectedItemProperty().addListener((observable, oldItem, newItem) -> {
    		if (newItem != null) {
    			this.characterCreationViewModel.getItemSelection().setValue(newItem);
    		}
    	});
    	this.listviewSpells.getSelectionModel().selectedItemProperty().addListener((observable, oldItem, newItem) -> {
    		if (newItem != null) {
    			this.characterCreationViewModel.getSpellSelection().setValue(newItem);
    		}
    	});
    	this.listviewFeats.getSelectionModel().selectedItemProperty().addListener((observable, oldItem, newItem) -> {
    		if (newItem != null) {
    			this.characterCreationViewModel.getFeatSelection().setValue(newItem);
    		}
    	});
    }
    
    private void bindBasicTab() {
    	this.textboxCharacterName.textProperty().bindBidirectional(this.characterCreationViewModel.characterNameProperty());
    	this.textfieldLevel.textProperty().bindBidirectional(this.characterCreationViewModel.characterLevelProperty(), new NumberStringConverter());
    	this.textfieldArmorClass.textProperty().bindBidirectional(this.characterCreationViewModel.characterArmorClassRatingProperty(), new NumberStringConverter());
    	this.textfieldMaxHP.textProperty().bindBidirectional(this.characterCreationViewModel.characterMaxHP(), new NumberStringConverter());
    	this.textfieldStrength.textProperty().bindBidirectional(this.characterCreationViewModel.attributeStrengthProperty(), new NumberStringConverter());
    	this.textfieldDexterity.textProperty().bindBidirectional(this.characterCreationViewModel.attributeDexterityProperty(), new NumberStringConverter());
    	this.textfieldConstitution.textProperty().bindBidirectional(this.characterCreationViewModel.attributeConstitutionProperty(), new NumberStringConverter());
    	this.textfieldIntelligence.textProperty().bindBidirectional(this.characterCreationViewModel.attributeIntelligenceProperty(), new NumberStringConverter());
    	this.textfieldWisdom.textProperty().bindBidirectional(this.characterCreationViewModel.attributeWisdomProperty(), new NumberStringConverter());
    	this.textfieldCharisma.textProperty().bindBidirectional(this.characterCreationViewModel.attributeCharismaProperty(), new NumberStringConverter());
    	this.textfieldGold.textProperty().bindBidirectional(this.characterCreationViewModel.characterGoldProperty(), new NumberStringConverter());
    }
    
    private void bindClassTab() {
    	this.comboClass.setItems(this.characterCreationViewModel.getCharacterClassList());
    	this.comboClass.valueProperty().bindBidirectional(this.characterCreationViewModel.getSelectedCharacterClass());
    	
    	this.textfieldProficiency1.textProperty().bindBidirectional(this.characterCreationViewModel.getSkillProficiency1());
    	this.textfieldProficiency2.textProperty().bindBidirectional(this.characterCreationViewModel.getSkillProficiency2());
    	this.textfieldWeaponMastery1.textProperty().bindBidirectional(this.characterCreationViewModel.getWeaponMastery1());
    	this.textfieldWeaponMastery2.textProperty().bindBidirectional(this.characterCreationViewModel.getWeaponMastery2());
    	this.textfieldEquipment.textProperty().bindBidirectional(this.characterCreationViewModel.equipmentProperty());
    	this.listviewEquipment.itemsProperty().bindBidirectional(this.characterCreationViewModel.getEquipmentList());
    	this.textfieldSpells.textProperty().bindBidirectional(this.characterCreationViewModel.spellProperty());
    	this.listviewSpells.itemsProperty().bindBidirectional(this.characterCreationViewModel.getSpellList());
    	this.textfieldFeat.textProperty().bindBidirectional(this.characterCreationViewModel.featProperty());
    	this.listviewFeats.itemsProperty().bindBidirectional(this.characterCreationViewModel.getFeatsList());
    }
    
    private void bindBackgroundTab() {
    	this.comboSpecies.setItems(this.characterCreationViewModel.getSpeciesList());
    	this.comboSpecies.valueProperty().bindBidirectional(this.characterCreationViewModel.selectedCharacterSpecies());
    	
    	this.textfieldAlignment.textProperty().bindBidirectional(this.characterCreationViewModel.characterAlignmentProperty());
    	this.textfieldFaith.textProperty().bindBidirectional(this.characterCreationViewModel.characterFaithProperty());
    	this.textfieldHair.textProperty().bindBidirectional(this.characterCreationViewModel.characterHairProperty());
    	this.textfieldSkin.textProperty().bindBidirectional(this.characterCreationViewModel.characterSkinProperty());
    	this.textfieldEyes.textProperty().bindBidirectional(this.characterCreationViewModel.characterEyesProperty());
    	this.textfieldHeight.textProperty().bindBidirectional(this.characterCreationViewModel.characterHeightProperty());
    	this.textfieldWeight.textProperty().bindBidirectional(this.characterCreationViewModel.characterWeightProperty());
    	this.textfieldAge.textProperty().bindBidirectional(this.characterCreationViewModel.characterAgeProperty());
    	this.textfieldGender.textProperty().bindBidirectional(this.characterCreationViewModel.characterGenderProperty());
    }
    
    private void addRemoveButtons() {
    	this.buttonAddEquipment.setOnAction((ActionEvent event) -> {
    		this.characterCreationViewModel.updateEquipmentList();
    	});
    	this.buttonRemoveEquipment.setOnAction((ActionEvent event) -> {
    		this.characterCreationViewModel.removeEquipmentFromEquipmentList();
    	});
    	this.buttonRemoveSpell.setOnAction((ActionEvent event) -> {
    		this.characterCreationViewModel.removeSpellFromSpellList();
    	});
    	this.buttonRemoveFeat.setOnAction((ActionEvent event) -> {
    		this.characterCreationViewModel.removeFeatFromFeatList();
    	});
    	this.buttonAddSpells.setOnAction((ActionEvent event) -> {
    		this.characterCreationViewModel.updateSpellList();
    	});
    	this.buttonAddFeat.setOnAction((ActionEvent event) -> {
    		this.characterCreationViewModel.updateFeatList();
    	});
    }
    
    private void eventHandler() {
    	
    	this.buttonCancel1.setOnAction((ActionEvent event) -> {
    		try {
    			this.sceneController.switchToCharacterManager(event);
    		} catch (IOException exception) {
    			System.err.println("Error switching to Character Manager");
    		}
    	});
    	this.buttonCancel2.setOnAction((ActionEvent event) -> {
    		try {
    			this.sceneController.switchToCharacterManager(event);
    		} catch (IOException exception) {
    			System.err.println("Error switching to Character Manager");
    		}
    	});
    	this.buttonCancel3.setOnAction((ActionEvent event) -> {
    		try {
    			this.sceneController.switchToCharacterManager(event);
    		} catch (IOException exception) {
    			System.err.println("Error switching to Character Manager");
    		}
    	});
    	this.buttonSave1.setOnAction((ActionEvent event) -> {
    		this.characterCreationViewModel.saveCharacter();
    		try {
    			this.sceneController.switchToCharacterManager(event);
    		} catch (IOException exception) {
    			System.err.println("Error switching to Character Manager");
    		}
    	});
    	this.buttonSave2.setOnAction((ActionEvent event) -> {
    		this.characterCreationViewModel.saveCharacter();
    		try {
    			this.sceneController.switchToCharacterManager(event);
    		} catch (IOException exception) {
    			System.err.println("Error switching to Character Manager");
    		}
    	});
    	this.buttonSave3.setOnAction((ActionEvent event) -> {
    		this.characterCreationViewModel.saveCharacter();
    		try {
    			this.sceneController.switchToCharacterManagerFromCreator(event);
    		} catch (IOException exception) {
    			System.err.println("Error switching to Character Manager");
    		}
    	});
    }
    
    private void numberValidationListener() {
    	this.textfieldLevel.addEventFilter(KeyEvent.KEY_TYPED, event -> {
    		if (!event.getCharacter().matches("[0-9]")) {
    			event.consume();
    		}
    	});
    	this.textfieldArmorClass.addEventFilter(KeyEvent.KEY_TYPED, event -> {
    		if (!event.getCharacter().matches("[0-9]")) {
    			event.consume();
    		}
    	});
    	this.textfieldMaxHP.addEventFilter(KeyEvent.KEY_TYPED, event -> {
    		if (!event.getCharacter().matches("[0-9]")) {
    			event.consume();
    		}
    	});
    	this.textfieldStrength.addEventFilter(KeyEvent.KEY_TYPED, event -> {
    		if (!event.getCharacter().matches("[0-9]")) {
    			event.consume();
    		}
    	});
    	this.textfieldDexterity.addEventFilter(KeyEvent.KEY_TYPED, event -> {
    		if (!event.getCharacter().matches("[0-9]")) {
    			event.consume();
    		}
    	});
    	this.textfieldConstitution.addEventFilter(KeyEvent.KEY_TYPED, event -> {
    		if (!event.getCharacter().matches("[0-9]")) {
    			event.consume();
    		}
    	});
    	this.textfieldIntelligence.addEventFilter(KeyEvent.KEY_TYPED, event -> {
    		if (!event.getCharacter().matches("[0-9]")) {
    			event.consume();
    		}
    	});
    	this.textfieldWisdom.addEventFilter(KeyEvent.KEY_TYPED, event -> {
    		if (!event.getCharacter().matches("[0-9]")) {
    			event.consume();
    		}
    	});
    	this.textfieldCharisma.addEventFilter(KeyEvent.KEY_TYPED, event -> {
    		if (!event.getCharacter().matches("[0-9]")) {
    			event.consume();
    		}
    	});
    	this.textfieldGold.addEventFilter(KeyEvent.KEY_TYPED, event -> {
    		if (!event.getCharacter().matches("[0-9]")) {
    			event.consume();
    		}
    	});
    }
}
