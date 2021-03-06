package regexgolf2.controllers;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import regexgolf2.model.challengegenerator.GeneratorConfig;
import regexgolf2.model.challengegenerator.GeneratorConfigVisitor;
import regexgolf2.model.challengegenerator.RandomGeneratorConfig;
import regexgolf2.model.challengegenerator.Generator.EmptyConfig;

public class GeneratorConfigUICreator implements GeneratorConfigVisitor
{
	private final Node _emptyConfigUI = new AnchorPane();
	private final RandomGeneratorConfigController _randGenController = new RandomGeneratorConfigController();
	/**
	 * Temporary variable for the selected UI
	 */
	private Node _ui;
	
	
	
	public GeneratorConfigUICreator()
	{
		
	}
	

	
	public Node getUIFor(GeneratorConfig config)
	{
		_ui = null;
		config.accept(this);
		assert _ui != null;
		return _ui;
	}
	
	@Override
	public void visit(EmptyConfig emptyConfig)
	{
		_ui = _emptyConfigUI;
	}

	@Override
	public void visit(RandomGeneratorConfig randomGeneratorConfig)
	{
		_randGenController.setConfig(randomGeneratorConfig);
		_ui = _randGenController.getUINode();
	}
}
