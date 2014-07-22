package regexgolf2.services.persistence;

import regexgolf2.model.ObservableObject;
import regexgolf2.model.containers.ContainerChangedEvent;
import regexgolf2.model.containers.ContainerChangedListener;
import regexgolf2.services.persistence.changetracking.ChangeTrackingService;

import com.google.java.contract.Requires;

public class TrackHandler implements ContainerChangedListener<ObservableObject>
{
	private final ChangeTrackingService _cts;
	
	
	
	@Requires("changeTrackingService != null")
	public TrackHandler(ChangeTrackingService changeTrackingService)
	{
		_cts = changeTrackingService;
	}
	
	
	
	@Override
	public void containerChanged(ContainerChangedEvent<? extends ObservableObject> event)
	{
		event.getRemovedItem().ifPresent(item -> _cts.untrack(item));
		event.getAddedItem().ifPresent(item -> _cts.track(item));
	}

}
