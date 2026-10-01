package data.scripts.world;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.SectorGeneratorPlugin;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.impl.campaign.ids.Submarkets;
import com.fs.starfarer.api.util.Misc;

@SuppressWarnings("unchecked")
public class battlefarerGen implements SectorGeneratorPlugin {

	public void generate(SectorAPI sector) {

		StarSystemAPI system = sector.getStarSystem("Corvus");

		SectorEntityToken center1 = system.getCenter();
		SectorEntityToken token = system.createToken(2000, 15000);

		SectorEntityToken BFstation = system.addCustomEntity("bf_tradestation",
				"Tradestation", "station_midline1", "battlefarer");
		BFstation.setCircularOrbitPointingDown(center1, 170, 900, 100);
		BFstation.setInteractionImage("illustrations", "orbital");

		MarketAPI market = Global.getFactory().createMarket("bf_tradestation_market", BFstation.getName(), 0);
		market.setSize(4);
		market.setFactionId("battlefarer");
		market.setSurveyLevel(MarketAPI.SurveyLevel.FULL);
		market.setPrimaryEntity(BFstation);
		market.addCondition(Conditions.POPULATION_4);
		market.addIndustry(Industries.POPULATION);
		market.addIndustry(Industries.SPACEPORT);
		market.addSubmarket(Submarkets.SUBMARKET_OPEN);
		market.addSubmarket(Submarkets.SUBMARKET_BLACK);
		market.addSubmarket(Submarkets.SUBMARKET_STORAGE);
		market.getTariff().modifyFlat("default_tariff", market.getFaction().getTariffFraction());
		BFstation.setMarket(market);
		market.setEconGroup(market.getId());
		EconomyAPI economy = sector.getEconomy();
		economy.addMarket(market, true);
		Misc.setFullySurveyed(market, null, false);

		CargoAPI BFcargo = market.getSubmarket(Submarkets.SUBMARKET_OPEN).getCargo();

		BFcargo.addCrew(500);
		BFcargo.addCrew(1000);
		BFcargo.addCrew(1000);
		BFcargo.addMarines(500);
		BFcargo.addSupplies(1000);
		BFcargo.addFuel(1000);

		//strike
		BFcargo.addWeapons("bomb", 25);
		BFcargo.addWeapons("reaper", 12);

		//Support
		BFcargo.addWeapons("lightac", 25);
		BFcargo.addWeapons("lightmg", 40);
		BFcargo.addWeapons("annihilator", 10);
		BFcargo.addWeapons("taclaser", 10);

		BFcargo.addWeapons("harpoon_single", 12); //medium

		//assault
		BFcargo.addWeapons("lightmortar", 40);
		BFcargo.addWeapons("miningblaster", 1); //medium

		//PD
		BFcargo.addWeapons("swarmer", 5);
		BFcargo.addWeapons("mininglaser", 25);
		BFcargo.addWeapons("pdlaser", 25);

		BFcargo.addWeapons("flak", 5); //medium
		BFcargo.addWeapons("shredder", 5); //medium
		BFcargo.addWeapons("annihilatorpod", 1); //medium
		BFcargo.addWeapons("pilum", 2); //medium
		BFcargo.addWeapons("mark9", 2); //large

		battlefarerConvoySpawnPoint convoySpawn = new battlefarerConvoySpawnPoint(sector, system, 7, 1, token, BFstation);
		system.addSpawnPoint(convoySpawn);
		convoySpawn.spawnFleet();

		Unique1SpawnPoint U1spawn = new Unique1SpawnPoint(sector, system, 10, 0, center1);
		Unique2SpawnPoint U2spawn = new Unique2SpawnPoint(sector, system, 10, 0, center1);
		Unique3SpawnPoint U3spawn = new Unique3SpawnPoint(sector, system, 10, 0, center1);
		Unique4SpawnPoint U4spawn = new Unique4SpawnPoint(sector, system, 10, 0, center1);
		Unique5SpawnPoint U5spawn = new Unique5SpawnPoint(sector, system, 10, 0, center1);
		Unique6SpawnPoint U6spawn = new Unique6SpawnPoint(sector, system, 10, 0, center1);
		Unique7SpawnPoint U7spawn = new Unique7SpawnPoint(sector, system, 10, 0, center1);
		Unique8SpawnPoint U8spawn = new Unique8SpawnPoint(sector, system, 10, 0, center1);
		Unique9SpawnPoint U9spawn = new Unique9SpawnPoint(sector, system, 10, 0, center1);

		system.addSpawnPoint(U1spawn);
		system.addSpawnPoint(U2spawn);
		system.addSpawnPoint(U3spawn);
		system.addSpawnPoint(U4spawn);
		system.addSpawnPoint(U5spawn);
		system.addSpawnPoint(U6spawn);
		system.addSpawnPoint(U7spawn);
		system.addSpawnPoint(U8spawn);
		system.addSpawnPoint(U9spawn);

		U1spawn.spawnFleet();
		U2spawn.spawnFleet();
		U3spawn.spawnFleet();
		U4spawn.spawnFleet();
		U5spawn.spawnFleet();
		U6spawn.spawnFleet();
		U7spawn.spawnFleet();
		U8spawn.spawnFleet();
		U9spawn.spawnFleet();

		FactionAPI battlefarer = sector.getFaction("battlefarer");

		FactionAPI hegemony = sector.getFaction("hegemony");
		FactionAPI tritachyon = sector.getFaction("tritachyon");
		FactionAPI pirates = sector.getFaction("pirates");
		FactionAPI independent = sector.getFaction("independent");
		FactionAPI player = sector.getFaction("player");

		battlefarer.setRelationship("hegemony", 0);
		battlefarer.setRelationship("tritachyon", 0);
		battlefarer.setRelationship("pirates", 0);
		battlefarer.setRelationship("independent", 0);
		battlefarer.setRelationship("player", 1);

		player.setRelationship(hegemony.getId(), -1);
		player.setRelationship(tritachyon.getId(), -1);
		player.setRelationship(pirates.getId(), -1);
		player.setRelationship(independent.getId(), -1);
	}
}
