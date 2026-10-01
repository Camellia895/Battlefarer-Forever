package data.scripts.weapons;

import com.fs.starfarer.api.combat.BoundsAPI;
import com.fs.starfarer.api.combat.CollisionClass;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.CombatEntityAPI;
import com.fs.starfarer.api.combat.DamageType;
import com.fs.starfarer.api.combat.EveryFrameWeaponEffectPlugin;
import com.fs.starfarer.api.combat.ShieldAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.WeaponAPI;
import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.lwjgl.util.vector.Vector2f;

public class BluelightEmitter implements EveryFrameWeaponEffectPlugin
{
    private static final String WEAPON_ID = "bluelight";
    private static final float EMITTER_THICKNESS = 10f;
	private static final Color EMITTER_FRINGE = new Color(0, 0, 0, 0);
    private static final Color EMITTER_CORE = Color.CYAN;
    private boolean hasChecked = false, hasEye = false;
    private Map toArcTo = new HashMap();
    private Map wepFollowers = new HashMap();

    public boolean checkForWeps(WeaponAPI emitter)
    {
        ShipAPI ship = emitter.getShip();
        WeaponAPI weapon;
        List weapons = ship.getAllWeapons();
        toArcTo.clear();
        wepFollowers.clear();

        for (int x = 0; x < weapons.size(); x++)
        {
            weapon = (WeaponAPI) weapons.get(x);

            if (emitter == weapon)
            {
                continue;
            }

            if (WEAPON_ID.equals(weapon.getId()))
            {
                toArcTo.put(weapon, null);
                wepFollowers.put(weapon, new FollowWeaponCombatEntity(weapon));
            }
        }

        return (!toArcTo.isEmpty());
    }

    @Override
    public void advance(float amount, CombatEngineAPI engine, WeaponAPI weapon)
    {
        if (!hasChecked)
        {
            hasEye = checkForWeps(weapon);
            hasChecked = true;
        }

        if (!hasEye || engine.isPaused())
        {
            return;
        }

        Map.Entry tmp;
        WeaponAPI wep;
        CombatEntityAPI arc;
        FollowWeaponCombatEntity follower;
        for (Iterator weapons = toArcTo.entrySet().iterator(); weapons.hasNext();)
        {
            tmp = (Map.Entry) weapons.next();
            wep = (WeaponAPI) tmp.getKey();
            arc = (CombatEntityAPI) tmp.getValue();
            follower = (FollowWeaponCombatEntity) wepFollowers.get(wep);

            if (wep.isFiring() && (arc == null || !engine.isEntityInPlay(arc)))
            {
                toArcTo.put(wep, engine.spawnEmpArc(weapon.getShip(),
                        weapon.getLocation(), weapon.getShip(),
                        follower, DamageType.OTHER, 0f, 0f, 5000f,
                        null, EMITTER_THICKNESS, EMITTER_FRINGE, EMITTER_CORE));
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="FollowWeaponCombatEntity">
    public static class FollowWeaponCombatEntity implements CombatEntityAPI
    {
        private WeaponAPI weapon;

        public FollowWeaponCombatEntity(WeaponAPI weapon)
        {
            this.weapon = weapon;
        }

        @Override
        public Vector2f getLocation()
        {
            return weapon.getLocation();
        }

        @Override
        public Vector2f getVelocity()
        {
            return null;
        }

        @Override
        public float getFacing()
        {
            return weapon.getArcFacing();
        }

        @Override
        public void setFacing(float facing)
        {
        }

        @Override
        public float getAngularVelocity()
        {
            return 0f;
        }

        @Override
        public void setAngularVelocity(float angVel)
        {
        }

        @Override
        public int getOwner()
        {
            return weapon.getShip().getOwner();
        }

        @Override
        public void setOwner(int owner)
        {
        }

        @Override
        public float getCollisionRadius()
        {
            return 0f;
        }

        @Override
        public CollisionClass getCollisionClass()
        {
            return null;
        }

        @Override
        public void setCollisionClass(CollisionClass collisionClass)
        {
        }

        @Override
        public float getMass()
        {
            return 0f;
        }

        @Override
        public void setMass(float mass)
        {
        }

        @Override
        public BoundsAPI getExactBounds()
        {
            return null;
        }

        @Override
        public ShieldAPI getShield()
        {
            return null;
        }

        @Override
        public float getHullLevel()
        {
            return 0f;
        }

        @Override
        public float getHitpoints()
        {
            return 0f;
        }

        @Override
        public float getMaxHitpoints()
        {
            return 0f;
        }

        @Override
        public void setCollisionRadius(float radius)
        {
        }

        @Override
        public Object getAI()
        {
            return null;
        }

        @Override
        public boolean isExpired()
        {
            return false;
        }

        @Override
        public void setCustomData(String key, Object data)
        {
        }

        @Override
        public void removeCustomData(String key)
        {
        }

        @Override
        public Map<String, Object> getCustomData()
        {
            return null;
        }

        @Override
        public void setHitpoints(float hitpoints)
        {
        }

        @Override
        public boolean isPointInBounds(Vector2f p)
        {
            return false;
        }

        @Override
        public boolean wasRemoved()
        {
            return false;
        }
    }
    //</editor-fold>
}
