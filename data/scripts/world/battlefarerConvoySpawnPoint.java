package data.scripts.world;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.Script;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.FleetAssignment;
import com.fs.starfarer.api.campaign.LocationAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.fleet.FleetMemberType;

@SuppressWarnings("unchecked")
public class battlefarerConvoySpawnPoint extends BaseSpawnPoint {

	private final SectorEntityToken convoyDestination;

	public battlefarerConvoySpawnPoint(SectorAPI sector, LocationAPI location,
							float daysInterval, int maxFleets, SectorEntityToken anchor,
							SectorEntityToken convoyDestination) {
		super(sector, location, daysInterval, maxFleets, anchor);
		this.convoyDestination = convoyDestination;
	}

	@Override
	protected CampaignFleetAPI spawnFleet() {

		CampaignFleetAPI fleet = Global.getFactory().createEmptyFleet("battlefarer", "Supply Fleet", true);

		fleet.getFleetData().addFleetMember("atlas_Standard");
		if ((float) Math.random() > 0.5f) fleet.getFleetData().addFleetMember("mule_Standard");
		if ((float) Math.random() > 0.5f) fleet.getFleetData().addFleetMember("tarsus_Standard");

		getLocation().spawnFleet(getAnchor(), 0, 0, fleet);

		CargoAPI cargo = fleet.getCargo();
		cargo.addCrew(200);
		cargo.addSupplies(200);
		cargo.addFuel(400);
		addRandomWeapons(cargo, 4);
		addRandomShips(fleet.getCargo(), (int) (Math.random() * 3f));

		Script script = createArrivedScript();
		Global.getSector().getCampaignUI().addMessage("An Unknown Fleet has jumped into the system.");

		fleet.addAssignment(FleetAssignment.DELIVER_RESOURCES, convoyDestination, 200, script);
		fleet.addAssignment(FleetAssignment.GO_TO_LOCATION_AND_DESPAWN, getAnchor(), 500);

		return fleet;
	}

	private Script createArrivedScript() {
		return new Script() {
			public void run() {
				Global.getSector().getCampaignUI().addMessage("A supply convoy has reached the Tradestation.");
			}
		};
	}

	private void addRandomWeapons(CargoAPI cargo, int count) {
		for (int i = 0; i < count; i++) {
			String weapon = (String) weapons[(int) (weapons.length * Math.random())];
			int quantity = (int) (Math.random() * 4f + 2f);
			cargo.addWeapons(weapon, quantity);
		}
	}

	private void addRandomShips(CargoAPI cargo, int count) {
		for (int i = 0; i < count; i++) {
			if ((float) Math.random() > 0.4f) {
				String wing = (String) wings[(int) (wings.length * Math.random())];
				cargo.addMothballedShip(FleetMemberType.FIGHTER_WING, wing, null);
			} else {
				String ship = (String) ships[(int) (ships.length * Math.random())];
				cargo.addMothballedShip(FleetMemberType.SHIP, ship, null);
			}
		}
	}

	// 0.98a: addMothballedShip takes variant ids (0.35a took hull ids; shuttle is gone -> hermes)
	private static String[] ships = {
									"hound_Standard",
									"hermes_Standard",
									"lasher_Standard",
									"brawler_Assault",
									"vigilance_Standard",
									"dram_Light",
									"wolf_CS",
									};

	private static String[] wings = {
									"wasp_wing",
									};

	private static String[] weapons = {
									"autopulse",
									"atropos",
									"atropos_single",
									"harpoon",
									"harpoon_single",
									"harpoonpod",
									"hurricane",
									"phasecl",
									"pilum",
									"pdburst",
									"sabot",
									"sabot_single",
									"sabotpod",
									"salamanderpod",
									"swarmer",
									"taclaser",
									"heavymauler",
									"vulcan",
									"lightdualmg",
									"lightmg",
									"chaingun",
									"hveldriver",
									"heavyneedler",
									};
}
