package com.mygdx.game.Entitys;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.Components.Pirate;
import com.mygdx.game.Components.Transform;
import com.mygdx.game.Faction;
import com.mygdx.game.Managers.CaptureManager;
import com.mygdx.game.Managers.GameManager;
import com.mygdx.utils.Utilities;

import java.util.ArrayList;

/**
 * Defines a college and its associated buildings.
 */
public class College extends Entity {
    private static ArrayList<String> buildingNames;
    private final ArrayList<Building> buildings;

    private boolean aliveToggle; // Added for assessment 2

    Building flag; // Added for assessment 2

    public Faction f; // Added for assessment 2
    public College() {
        super();
        buildings = new ArrayList<>();
        buildingNames = new ArrayList<>();
        buildingNames.add("big");
        buildingNames.add("small");
        buildingNames.add("clock");
        Transform t = new Transform();
        Pirate p = new Pirate();
        addComponents(t, p);
        aliveToggle = true; // Updated for assessment 2
    }

    /**
     * Creates a college at the location associated with the given faction id.
     *
     * @param factionId numerical id of the faction
     */
    public College(int factionId) {
        this();
        f = GameManager.getFaction(factionId); // Updated for assessment 2
        Transform t = getComponent(Transform.class);
        t.setPosition(f.getPosition());
        Pirate p = getComponent(Pirate.class);
        p.setFactionId(factionId);
        spawn(f.getColour());
    }

    /**
     * Randomly populates the college radius with buildings.
     *
     * @param colour used to pull the appropriate flag sprite
     */
    private void spawn(String colour) {
        JsonValue collegeSettings = GameManager.getSettings().get("college");
        float radius = collegeSettings.getFloat("spawnRadius");
        // radius = Utilities.tilesToDistance(radius) * BUILDING_SCALE;
        final Vector2 origin = getComponent(Transform.class).getPosition();
        ArrayList<Vector2> posList = new ArrayList<>();
        posList.add(new Vector2(0, 0));

        for (int i = 0; i < collegeSettings.getInt("numBuildings"); i++) {
            Vector2 pos = Utilities.randomPos(-radius, radius);
            pos = Utilities.floor(pos);

            if (!posList.contains(pos)) {
                posList.add(pos);

                pos = Utilities.tilesToDistance(pos).add(origin);

                Building b = new Building();
                buildings.add(b);

                String b_name = Utilities.randomChoice(buildingNames, 0);

                b.create(pos, b_name, f); // Updated for assessment 2
            }


        }
        flag = new Building(true); // Updated for assessment 2
        buildings.add(flag);
        flag.create(origin, colour, f); // Updated for assessment 2
    }

    /**
     * True as long as unharmed buildings remain, false otherwise.
     */
    public void isAlive() {
        boolean res = false;
        for (int i = 0; i < buildings.size() - 1; i++) {
            Building b = buildings.get(i);
            if (b.isAlive()) {
                res = true;
            }
        }
        /*
        Added for assessment 2
        Fixes the bug of using extra memory on every update after the college is dead
         */
        if (!res && aliveToggle) {
            aliveToggle = false;
            getComponent(Pirate.class).kill();
            CaptureManager.handler(flag);

//            flag.setFaction();
//            flag.updateFlag();
//            CaptureManager.pause();
//            CaptureManager.changeScreen();
        }
    }

    /**
     * Added for assessment 2
     * @return all the buildings within the college
     */
    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    /**
     * Added for assessment 2
     * @return true if all the buildings of the college is alive
     */
    public boolean aliveTest() {
        boolean res = false;
        for (int i = 0; i < buildings.size() - 1; i++) {
            Building b = buildings.get(i);
            if (b.isAlive()) {
                res = true;
            }
        }
        if (!res) {
            return false;
        }
        return true;
    }

    /**
     * Added for assessment 2
     * Destroys all the buildings within the college
     */
    public void destroy() {
        for (int i = 0; i < buildings.size() - 1; i++) {
            Building b = buildings.get(i);
            if (b.isAlive()) {
                b.destroy();
            }
        }
        getComponent(Pirate.class).kill();
    }

    @Override
    public void update() {
        super.update();
        isAlive();
    }
}
