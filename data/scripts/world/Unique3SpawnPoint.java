package data.scripts.world;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.FleetAssignment;
import com.fs.starfarer.api.campaign.LocationAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;


public class Unique3SpawnPoint extends BaseSpawnPoint {

	public Unique3SpawnPoint(SectorAPI sector, LocationAPI location, 
									float daysInterval, int maxFleets, SectorEntityToken anchor) {
		super(sector, location, daysInterval, maxFleets, anchor);
	}

	@Override
	protected CampaignFleetAPI spawnFleet() {
		
		String variantId = "Absit Invidia_Default";

		float angle = (float) ((float) Math.random() * Math.PI * 2f);
		float x = (float) (Math.cos(angle) * 18000f);
		float y = (float) (Math.sin(angle) * 18000f);

		CampaignFleetAPI fleet = Global.getFactory().createEmptyFleet("unique3", "Absit Invidia", true);
		fleet.getFleetData().addFleetMember(variantId);

		CargoAPI cargo = fleet.getCargo();
		cargo.addCrew(500);
		cargo.addSupplies(100);
		cargo.addFuel(200);

		getLocation().spawnFleet(getAnchor(), x, y, fleet);

		fleet.addAssignment(FleetAssignment.RAID_SYSTEM, getAnchor(), 300);
		fleet.addAssignment(FleetAssignment.GO_TO_LOCATION_AND_DESPAWN, getAnchor(), 1000);

		return fleet;
	}

}
