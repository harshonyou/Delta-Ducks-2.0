package com.mygdx.game.Managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Components.Component;

import java.util.ArrayList;

import static com.mygdx.utils.Constants.*;

/**
 * Responsible for all rending. Renders in layers render item layers can't be changed
 * holds the primary sprite batch and rendering camera
 */
public final class RenderingManager {
    private static boolean initialised = false; // Added for assessment 2
    private static boolean pred = false; // Added for assessment 2
    private static ArrayList<ArrayList<Component>> layers; // Updated for assessment 2
    private static OrthographicCamera camera;
    private static SpriteBatch batch;

    /**
     * Added for assessment 2
     * This fixes the bug for testing by running it only once for initialization
     */
    public static void Pre() {
        pred = true;

        layers = new ArrayList<>(RenderLayer.values().length);
        for (int i = 0; i < RenderLayer.values().length; i++) {
            layers.add(new ArrayList<>());
        }
    }

    public static void Initialise() {
        tryPre();
        initialised = true;

        batch = new SpriteBatch();
        // batch.enableBlending();
        camera = new OrthographicCamera();
        camera.viewportHeight = VIEWPORT_HEIGHT / ZOOM;
        camera.viewportWidth = VIEWPORT_WIDTH / ZOOM;
        camera.update();

    }

    public static OrthographicCamera getCamera() {
        tryInit();
        return camera;
    }


    public static void setCamera(OrthographicCamera cam) {
        camera = cam;
    }

    /**
     * adds item to the list of renderable and adds to the correct layer
     *
     * @param item  component that utilises render
     * @param layer the layer that it will be rendered in
     */

    /**
     * Updated for assessment 2
     * @param item component to add
     * @param layer layer which to add
     */
    public static void addItem(Component item, RenderLayer layer) {
        tryPre();
        layers.get(layer.ordinal()).add(item);
    }

    /**
     * Added for assessment 2
     * Check if Pre has been initialized or not
     */
    private static void tryPre() {
        if (!pred) {
            Pre();
        }
    }

    private static void tryInit() {
        if (!initialised) {
            Initialise();
        }
    }

    /**
     * Renders all items in accordance with their layers on one sprite batch
     */
    public static void render() {
        tryInit();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        /*
        Updated for assessment 2
         */
        for (ArrayList<Component> layer : layers) {
            for (Component c : layer) {
                c.render();
            }
        }

        /*for(int i = 0; i < renderItems.size(); i++){
            //renderItems.get(renderItems.size() - (1 + i)).render();
            renderItems.get(i).render();
        }*/

        batch.end();
    }

    public static void cleanUp() {
        batch.dispose();
    }

    public static SpriteBatch getBatch() {
        return batch;
    }
}
