package data.scripts.world;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.FleetAssignment;
import com.fs.starfarer.api.campaign.LocationAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;

import data.scripts.world.BaseSpawnPoint;

public class battlefarerSpawnPoint extends BaseSpawnPoint {

	public battlefarerSpawnPoint(SectorAPI sector, LocationAPI location, 
								float daysInterval, int maxFleets, SectorEntityToken anchor) {
		super(sector, location, daysInterval, maxFleets, anchor);
	}

	@Override
	protected CampaignFleetAPI spawnFleet() {
		
		CampaignFleetAPI fleet = Global.getFactory().createEmptyFleet("battlefarer", "Supply Fleet", true);

		// original fleetCompositions: atlas [1,2], mule [1,4], tarsus [2,6]
		fleet.getFleetData().addFleetMember("atlas_Standard");
		if ((float) Math.random() > 0.5f) fleet.getFleetData().addFleetMember("atlas_Standard");
		int mules = 1 + (int) (Math.random() * 4f);
		for (int i = 0; i < mules; i++) fleet.getFleetData().addFleetMember("mule_Standard");
		int tarsii = 2 + (int) (Math.random() * 5f);
		for (int i = 0; i < tarsii; i++) fleet.getFleetData().addFleetMember("tarsus_Standard");

		getLocation().spawnFleet(getAnchor(), 0, 0, fleet);

		CargoAPI cargo = fleet.getCargo();
		cargo.addCrew(500);
		cargo.addSupplies(300);
		cargo.addFuel(600);

		if ((float) Math.random() > 0.95f) {
			fleet.addAssignment(FleetAssignment.RAID_SYSTEM, getAnchor(), 30);
			fleet.addAssignment(FleetAssignment.GO_TO_LOCATION_AND_DESPAWN, getAnchor(), 1000);
		} else {
			fleet.addAssignment(FleetAssignment.DEFEND_LOCATION, getAnchor(), 20);
			fleet.addAssignment(FleetAssignment.GO_TO_LOCATION_AND_DESPAWN, getAnchor(), 1000);
		}

		return fleet;
	}

}
