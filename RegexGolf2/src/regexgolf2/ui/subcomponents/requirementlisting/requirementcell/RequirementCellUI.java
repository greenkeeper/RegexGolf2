package regexgolf2.ui.subcomponents.requirementlisting.requirementcell;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import regexgolf2.ui.subcomponents.editablelabel.EditableLabel;

public class RequirementCellUI extends ListCell<RequirementItem>
{
	/**
	 * ImageView that displays the check-image that indicates,
	 * if the requirement is complied or not.
	 */
    private final ImageView _imageView = new ImageView();
    
    private final EditableLabel _editLabel = new EditableLabel();

    private final AnchorPane _rootNode = new AnchorPane();
    
    private Image _notCompliedImage;
    private Image _compliedImage;

    private final BooleanProperty _complied = new SimpleBooleanProperty();


    
    public RequirementCellUI()
    {
    	initCommitEditCall();
    	initLayout();
    	initImages();
    	initCompliedChangedReaction();
    	_editLabel.editableProperty().bind(this.editableProperty());
    	setComplied(false);
    }
    
    //TODO move all the images into a different directory
    
    private void initLayout()
    {
    	_editLabel.editIconAppearsProperty().bind(this.hoverProperty());
    	Node editLabelNode = _editLabel.getUINode();
    	AnchorPane.setLeftAnchor(editLabelNode, 0.0);
    	AnchorPane.setTopAnchor(editLabelNode, 0.0);
    	AnchorPane.setBottomAnchor(editLabelNode, 0.0);
    	
    	_imageView.setFitHeight(25);
    	_imageView.setFitWidth(25);
    	
    	AnchorPane.setTopAnchor(_imageView, 0.0);
    	AnchorPane.setRightAnchor(_imageView, 0.0);
    	
    	_rootNode.getChildren().add(editLabelNode);
    	_rootNode.getChildren().add(_imageView);
    	
    	//Setup correct scaling
    	this.setPrefWidth(0);
    	_rootNode.prefWidthProperty().bind(this.widthProperty());
    	
    	setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }
    
    private void initCommitEditCall()
    {
    	_editLabel.editModeProperty().addListener(new ChangeListener<Boolean>()
		{
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean arg1, Boolean arg2)
			{
				if (!arg2)
				RequirementCellUI.this.commitEdit(getItem());
			}
		});
    }
    
    private void initImages()
    
    {
    	_compliedImage = new Image(getClass().getResourceAsStream("complied.png"));
    	_notCompliedImage = new Image(getClass().getResourceAsStream("notComplied.png"));
    }
    
    private void initCompliedChangedReaction()
    {
    	_complied.addListener(new ChangeListener<Boolean>()
		{
			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue)
			{
				setComplied(newValue);
			}
		});
    }
    
    private void setComplied(boolean complied)
    {
    	if (complied)
    		_imageView.setImage(_compliedImage);
    	else
    		_imageView.setImage(_notCompliedImage);
    }
    
    @Override
    public void startEdit()
    {
    	super.startEdit();
    	_editLabel.tryEnterEditMode();
    }
    
    /**
     * Called on various events to update the UI accordingly
     */
    @Override
    protected void updateItem(RequirementItem requirement, boolean empty)
    {
    	//Get current item and unsubscribe if item is set
		if (getItem() != null)
		{
			_editLabel.textProperty().unbindBidirectional(getItem().wordProperty());
			_complied.unbindBidirectional(getItem().compliedProperty());
		}
		
    	super.updateItem(requirement, empty);

    	if (getItem() == null)
    	{
    		setGraphic(null);
    	}
    	else
    	{
    		setGraphic(_rootNode);

			_editLabel.textProperty().bindBidirectional(requirement.wordProperty());
			_complied.bind(requirement.compliedProperty());
    	}
    }
}