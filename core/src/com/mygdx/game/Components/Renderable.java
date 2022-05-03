package com.mygdx.game.Components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Managers.RenderLayer;
import com.mygdx.game.Managers.RenderingManager;
import com.mygdx.game.Managers.ResourceManager;

/**
 * Add the ability for the object to be shown
 */
public class Renderable extends Component {
    protected Sprite sprite;
    private boolean isVisible;
    private float displacementX; // Added for assessment 2
    private float displacementY; // Added for assessment 2

    /**
     * Called in other constructors, loads no textures by itself.
     */
    public Renderable() {
        super();
        isVisible = true;
        type = ComponentType.Renderable;
        sprite = new Sprite();
        RenderingManager.addItem(this, RenderLayer.Transparent);

        displacementX = displacementY = 0f;
    }

    /**
     * Associates Renderable with the given texture sprite and layer.
     *
     * @param texId the id of the texture the sprite will take on
     * @param layer the rendering layer
     */
    public Renderable(int texId, RenderLayer layer) {
        this();
        sprite = new Sprite(ResourceManager.getTexture(texId));
        RenderingManager.addItem(this, layer);
    }

    /**
     * Associates Renderable with the given sprite from a texture atlas and a layer.
     *
     * @param atlasId the id of the texture atlas containing the sprite
     * @param texName the name of the texture the sprite will take on
     * @param layer   the rendering layer
     */
    public Renderable(int atlasId, String texName, RenderLayer layer) {
        this();
        sprite = new Sprite(ResourceManager.getSprite(atlasId, texName));
        RenderingManager.addItem(this, layer);
    }


    /**
     * Locates the sprite at the position of the parent's Transform component.
     */
    @Override
    public void update() {
        super.update();
        if (sprite == null) {
            return;
        }
        Transform c = parent.getComponent(Transform.class);
        if (c == null) {
            return;
        }
        Vector2 p = c.getPosition();
        Vector2 s = c.getScale();

        sprite.setPosition(p.x + displacementX, p.y + displacementY); // Added displacement to the location for assessment 2
        sprite.setRotation(MathUtils.radiansToDegrees * c.getRotation());
        sprite.setScale(s.x, s.y);
    }

    @Override
    public void render() {
        super.render();
        if (sprite == null || !isVisible) {
            return;
        }
        sprite.draw(RenderingManager.getBatch());
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
    }

    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Asignes a new texture compatible with textures sourced from atlas
     *
     * @param s the sprite contain the texture
     */
    public void setTexture(Sprite s) {
        Sprite a = getSprite();
        a.setTexture(s.getTexture());
        a.setU(s.getU());
        a.setV(s.getV());
        a.setU2(s.getU2());
        a.setV2(s.getV2());
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void show() {
        isVisible = true;
    }

    public void hide() {
        isVisible = false;
    }

    public void dispose() {

    }

    public void toggleVisibility() {
        isVisible = !isVisible;
    }

    /**
     * Added for assessment 2
     * @param c to set the color of the sprite
     */
    public void setColor(Color c) {
        sprite.setColor(c);
    }

    /**
     * Added for assessment 2
     * to set the size of the sprite
     * @param width of the sprite
     * @param height of the sprite
     */
    public void setSize(float width, float height) {
        sprite.setSize(width, height);
    }

    /**
     * Added for assessment 2
     * to set the displacement or offset from the initial location
     * @param x loc
     * @param y loc
     */
    public void setDisplacement(float x, float y) {
        displacementX = x;
        displacementY = y;
    }
}
