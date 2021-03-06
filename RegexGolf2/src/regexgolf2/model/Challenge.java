package regexgolf2.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;


public class Challenge extends ObservableObject
{
	private final Set<Requirement> _requirements = new HashSet<>();
	private Solution _sampleSolution;
	private String _name = "";
	private int _id;
	
	private ObjectChangedListener _requirementListener;
	
	
	
	public Challenge()
	{
		_sampleSolution = new Solution();
		initRequirementListener();
		initSampleSolutionListener();
	}
	
	
	
	private void initRequirementListener()
	{
		_requirementListener = new ObjectChangedListener()
		{
			@Override
			public void objectChanged(EventObject event)
			{
				Object source = event.getSource();
				
				if (!_requirements.contains(source))
					throw new IllegalStateException(
							"The eventsource is not a Requirement that is owned " +
							"by this challenge, this listener should not be called!");
				
				fireObjectChangedEvent();
			}
		};
	}
	
	private void initSampleSolutionListener()
	{
		_sampleSolution.addObjectChangedListener(new ObjectChangedListener()
		{
			@Override
			public void objectChanged(EventObject event)
			{
				fireObjectChangedEvent();
			}
		});
	}
	
	public int getId()
	{
		return _id;
	}
	
	public void setId(int id)
	{
		_id = id;
	}
	
	@Ensures("result != null")
	public String getName()
	{
		return _name;
	}
	
	@Requires("name != null")
	public void setName(String name)
	{
		if (_name.equals(name))
			return;
		_name = name;
		fireObjectChangedEvent();
	}
	
	@Ensures("result != null")
	public Solution getSampleSolution()
	{
		return _sampleSolution;
	}
	
	@Requires("requirement != null")
	public void addRequirement(Requirement requirement)
	{
		boolean elementWasAdded = _requirements.add(requirement);
		if (!elementWasAdded)
			return;
		requirement.addObjectChangedListener(_requirementListener);
		fireObjectChangedEvent();
	}
	
	@Requires("requirement != null")
	public void removeRequirement(Requirement requirement)
	{
		boolean wasRemoved = _requirements.remove(requirement);
		if (wasRemoved)
		{
			requirement.removeObjectChangedListener(_requirementListener);
			fireObjectChangedEvent();
		}
	}

	/**
	 * Returns an unmodifiable View of the internal Set
	 */
	@Ensures("result != null")
	public Set<Requirement> getRequirements()
	{
		return Collections.unmodifiableSet(_requirements);
	}

	
	/**
	 * Helper method to get filtered requirements
	 * @param expectedMatchresult The value that should be filtered with
	 */
	@Ensures("result != null")
	public List<Requirement> getRequirements(boolean expectedMatchResult)
	{
		List<Requirement> requirements = new ArrayList<>();
		
		for (Requirement r : getRequirements())
		{
			if (r.getExpectedMatchResult() == expectedMatchResult)
			{
				requirements.add(r);
			}
		}
		
		return requirements;
	}
	
	public int getAmountRequirements()
	{
		return _requirements.size();
	}
}
