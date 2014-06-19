package regexgolf2.ui.challengesolving;

import java.io.IOException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import regexgolf2.ui.subcomponents.challengetitle.ChallengeTitleUI;
import regexgolf2.ui.subcomponents.scoredisplay.ScoreDisplayUI;
import regexgolf2.ui.subcomponents.solutionediting.SolutionEditingUI;
import regexgolf2.ui.util.JavafxUtil;

public class ChallengeSolvingUI
{
	private final BooleanProperty _editable = new SimpleBooleanProperty(false);
	
    @FXML
    private AnchorPane _titlePane;
    private ChallengeTitleUI _challengeTitleUI = new ChallengeTitleUI();
    
    @FXML
    private Label _scoreLabel;
    private ScoreDisplayUI _scoreDisplayUI;

    @FXML
    private TextField _solutionTextField;
    private SolutionEditingUI _solutionEditingUI;

    @FXML
    private Button _addDontMatchBtn;

    @FXML
    private Button _addDoMatchBtn;

    @FXML
    private AnchorPane _doMatchPane;

    @FXML
    private AnchorPane _dontMatchPane;
    
    private Parent _rootNode;
    
    
    
    public ChallengeSolvingUI(Node doMatchUI, Node dontMatchUI) throws IOException
    {    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("ChallengeSolvingUI.fxml")); 
    	loader.setController(this);
    	_rootNode = loader.load();
    	
    	assert _titlePane != null;
    	assert _scoreLabel != null;
    	assert _solutionTextField != null;
    	assert _doMatchPane != null;
    	assert _dontMatchPane != null;
    	assert _addDontMatchBtn != null;
    	assert _addDoMatchBtn != null;

    	JavafxUtil.setAsContent(_challengeTitleUI.getUINode(), _titlePane);
    	JavafxUtil.setAsContent(doMatchUI, _doMatchPane);
    	JavafxUtil.setAsContent(dontMatchUI, _dontMatchPane);
    	
    	_scoreDisplayUI = new ScoreDisplayUI(_scoreLabel);
    	_solutionEditingUI = new SolutionEditingUI(_solutionTextField);    	
    	
    	_challengeTitleUI.editableProperty().bind(_editable);
    	_addDoMatchBtn.visibleProperty().bind(_editable);
    	_addDontMatchBtn.visibleProperty().bind(_editable);
    }
    
    public ScoreDisplayUI getScoreDisplayUI()
    {
    	return _scoreDisplayUI;
    }
    
    public SolutionEditingUI getSolutionEditingUI()
    {
    	return _solutionEditingUI;
    }
    
    public ChallengeTitleUI getChallengeTitleUI()
    {
    	return _challengeTitleUI;
    }
    
    public Button getAddDoMatchBtn()
    {
    	return _addDoMatchBtn;
    }
    
    public Button getAddDontMatchBtn()
    {
    	return _addDontMatchBtn;
    }
    
    public boolean isEditable()
    {
    	return _editable.get();
    }
    
    public void setEditable(boolean editable)
    {
    	_editable.set(editable);
    }
    
    public BooleanProperty editableProperty()
    {
    	return _editable;
    }
    
    public void setDisabled(boolean disabled)
    {
    	_rootNode.setDisable(disabled);
    }
    
    public Parent getUINode()
    {
    	return _rootNode;
    }
}
