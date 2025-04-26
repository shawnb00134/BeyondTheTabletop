package edu.westga.cs3212.dungeonsAndDragonProject.view;

import edu.westga.cs3212.dungeonsAndDragonProject.model.Campaign;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.CampaignCreationViewModel;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.SystemContextViewModel;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Campaign Creation Codebehind
 * 
 * @author Kelis Gates
 * 
 * @version Spring 2025
 */
public class CampaignCreationCodeBehind {
	@FXML
	private TextArea campaignDescription;

	@FXML
	private Button cancelButton;

	@FXML
	private TextField campaignLimit;
	
	@FXML
	private TextField campaignName;

    @FXML
    private Button createButton;
    
    @FXML
    private Label nameErrorLabel;
    
    @FXML
    private Label descriptionError;

    @FXML
    private Label playerLimitError;
    
    private CampaignCreationViewModel vm;
    private boolean isEditing;
    private Campaign selectedCampaign;
    
    /**
	 * Constructor for the campaign creation 
	 * 
	 * @precondition none
	 * @postcondition viewModel is instantiated.
	 * 
	 * @param sharedViewModel the ViewModel
	 */
    public CampaignCreationCodeBehind(CampaignCreationViewModel sharedViewModel) {
        this.vm = sharedViewModel;
        this.isEditing = false;
    }
    
    /**
	 * Constructor for the campaign editing 
	 * 
	 * @precondition none
	 * @postcondition viewModel is instantiated.
	 * 
	 * @param sharedViewModel the ViewModel
	 * @param campaign the campaign to be edited
	 */
    public CampaignCreationCodeBehind(CampaignCreationViewModel sharedViewModel, Campaign campaign) {
        this.vm = sharedViewModel;
        this.vm.loadCampaignData(campaign); 
        this.isEditing = true;
        this.selectedCampaign = campaign;
    }
    
    @FXML
    void initialize() {
    	this.bindProperties();
    	this.eventHandlers();
    	if (this.isEditing) {
    		this.createButton.setText("Save Edit");
    		this.createButton.setDisable(false);
    	} else {
    		this.createButton.setDisable(true);
    		this.campaignName.textProperty().setValue("");
    		this.campaignDescription.textProperty().setValue("");
    		this.campaignLimit.textProperty().setValue("");
    	}
    	this.setupValidation();
    	
    }
    
	private void bindProperties() {
		this.campaignName.textProperty().bindBidirectional(this.vm.getName());
		this.campaignDescription.textProperty().bindBidirectional(this.vm.getDescription());
		this.campaignLimit.textProperty().bindBidirectional(this.vm.getCampaignLimit());
	}

	private void eventHandlers() {
		this.cancelButton.setOnAction((event) -> {
			((Node) (event.getSource())).getScene().getWindow().hide();
		});

		this.createButton.setOnAction((event) -> {
			if (!this.isEditing) {
				
				this.vm.createCampaign();
			} else if (this.isEditing) {
				this.vm.editCampaign(this.selectedCampaign);
			}
			((Node) (event.getSource())).getScene().getWindow().hide();
		});
	}
	
	private void setupValidation() {
		
		ChangeListener<String> listener = (obs, old, neu) -> this.validateForm();

        this.campaignName.textProperty().addListener(listener);
        this.campaignDescription.textProperty().addListener(listener);
        this.campaignLimit.textProperty().addListener(listener);
		
	}
	
	private void validateForm() {
		String name    = this.campaignName.getText().trim();
        String desc    = this.campaignDescription.getText().trim();
        String limitTx = this.campaignLimit.getText().trim();

        boolean nameNonEmpty = !name.isEmpty();

        boolean nameUnique;
        if (this.isEditing) {
            nameUnique = !this.isNewNameEqualToOldName();
        } else {
            nameUnique = !this.isNameTaken();
        }

        boolean nameValid = nameNonEmpty && nameUnique;

        boolean descValid = !desc.isEmpty();

        boolean limitValid;
        int    limit = 0;
        try {
            limit = Integer.parseInt(limitTx);
            limitValid = (limit > 0);
        } catch (NumberFormatException nfe) {
            limitValid = false;
        }

        this.nameErrorLabel.setVisible(!nameValid);
        this.descriptionError.setVisible(!descValid);
        this.playerLimitError.setVisible(!limitValid);
        
        this.createButton.setDisable(!(nameValid && descValid && limitValid));
	}

	private boolean isNewNameEqualToOldName() {
		
		String OldCampaignName = this.selectedCampaign.getName();
		
		if (OldCampaignName.equals(this.campaignName.getText())) {
			return false;
		}
		
		for (Campaign campaign : SystemContextViewModel.getInstance().getExistingCampaigns()) {
            if (campaign.getName().equals(this.campaignName.getText())) {
                return true; 
            }
        }
        return false;
	}

    /**
     * Checks if the campaign name is already taken.
     * 
     * @return true if the name already exists
     */
    private Boolean isNameTaken() {
        for (Campaign campaign : SystemContextViewModel.getInstance().getExistingCampaigns()) {
            if (campaign.getName().equals(this.campaignName.getText())) {
                return true; 
            }
        }
        return false;
    }
}
