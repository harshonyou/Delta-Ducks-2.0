package com.mygdx.game.Managers;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Components.Component;
import com.mygdx.game.Components.ComponentEvent;
import com.mygdx.game.Entitys.Entity;

import java.util.ArrayList;

/**
 * Responsible for Managing the entity and component events. Entity's can be accessed by a String name
 */
public final class EntityManager {
    private static ArrayList<String> entityNames;
    private static ArrayList<Entity> entities;
    private static ArrayList<Component> components;
    private static boolean initialized = false;

    /**
     * Should only be called once although if it isn't called at all it will be called automatically
     */
    public static void Initialize() {
        entityNames = new ArrayList<>();
        entities = new ArrayList<>();
        components = new ArrayList<>();
        initialized = true;
    }

    /**
     * Dont call manually
     *
     * @param c the comp to add
     */
    public static void addComponent(Component c) {
        tryInit();
        components.add(c);
    }

    /**
     * Dont call manually
     *
     * @param e the entity to add
     */
    public static void addEntity(Entity e) {
        tryInit();
        entities.add(e);
        entityNames.add(e.getName());
    }

    /**
     * gets the first entity found with the given name
     *
     * @param name name of the entity
     * @return the found entity
     */
    public static Entity getEntity(String name) {
        return entities.get(entityNames.indexOf(name));
    }

    /**
     * changes the entity's name
     *
     * @param prev old name
     * @param new_ new name
     */
    public static void changeName(String prev, String new_) {
        entityNames.set(entityNames.indexOf(prev), new_);
    }

    /**
     * raises the appropriate events for each entity's component. then renders after all entities have being processed if render is requested
     *
     * @param comps calls the events left to right
     */
    public static void raiseEvents(ComponentEvent... comps) {
        tryInit();
        for (Entity e : entities) {
            e.raiseEvents(comps);
        }
        for (ComponentEvent c : comps) {
            if (c == ComponentEvent.Render) {
                RenderingManager.render();

                break;
            }
        }
        for (Entity e : entities) {
            e.update();
        }
    }

    /**
     * Cleans up all entities and components. Disposes of the primary sprite batch
     */
    public static void cleanUp() {
        tryInit();
        for (Entity e : entities) {
            e.cleanUp();
        }
        for (Component c : components) {
            c.cleanUp();
        }
    }

    /**
     * automatically calls initialised if not done so
     */
    private static void tryInit() {
        if (!initialized) {
            Initialize();
        }
    }

    /**
     * gets the time between the last from and the current
     *
     * @return 1/FPS
     */
    public static float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }

    public static int getFPS() {
        return Gdx.graphics.getFramesPerSecond();
    }
}
