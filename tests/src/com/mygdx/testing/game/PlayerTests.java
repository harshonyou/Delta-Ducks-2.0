package com.mygdx.testing.game;

import com.mygdx.game.Components.Transform;
import com.mygdx.game.Entitys.Boulder;
import com.mygdx.game.Entitys.Monster;
import com.mygdx.game.Entitys.NPCShip;
import com.mygdx.game.Entitys.Player;
import com.mygdx.game.Managers.CaptureManager;
import com.mygdx.game.Managers.GameManager;
import com.mygdx.game.Managers.PhysicsManager;
import com.mygdx.game.PirateGame;
import com.mygdx.testing.GdxTestRunner;
import org.junit.*;
import org.junit.runner.RunWith;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static com.mygdx.game.Managers.GameManager.CreateNPCShip;
import static com.mygdx.utils.Constants.INIT_CONSTANTS;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;

@RunWith(GdxTestRunner.class)
public class PlayerTests {

    private int randomNum(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    @BeforeClass
    public static void init(){
        INIT_CONSTANTS();
        PirateGame.loadResources();
        PhysicsManager.Initialise();
        GameManager.Initialise();
    }

    @AfterClass
    public static void dispose(){

    }

    @Test
    public void armorDamageOnBoulder() {
        GameManager.CreatePlayer();
        Player p = GameManager.getPlayer();
        p.setArmor(1);
        p.setFaction(1);
        Boulder b = new Boulder();
        b.hurt();
        b.getComponent(Transform.class).setPosition((float) (p.getPosition().x + 10), (float) (p.getPosition().y - Math.random()*100 + 10));
        assertTrue("Equal", p.getArmor()==0);
    }

    @Test
    public void healthDamageOnMonster() {
        GameManager.CreatePlayer();
        Player p = GameManager.getPlayer();
        p.setFaction(1);
        Monster m = new Monster();
        m.hurt();
        m.getComponent(Transform.class).setPosition((float) (p.getPosition().x + 10), (float) (p.getPosition().y - Math.random()*100 + 10));
        assertTrue("Equal", p.getHealth()<100);
    }

    @Test
    public void captureTest() {
        GameManager.CreatePlayer();
        Player p = GameManager.getPlayer();
        int plunder = p.getPlunder();
        int xp = p.getXp();
        CaptureManager.captureHandler("Langwith");
        assertAll("",
                () -> assertTrue("Greater",
                        plunder < p.getPlunder()),
                () -> assertTrue("Greater",
                        xp < p.getXp())
        );
    }

    @Test
    public void destoryTest() {
        GameManager.CreatePlayer();
        Player p = GameManager.getPlayer();
        int plunder = p.getPlunder();
        int xp = p.getXp();
        CaptureManager.destroyHandler("Langwith", 5);
        assertAll("",
                () -> assertTrue("Greater",
                        plunder < p.getPlunder()),
                () -> assertTrue("Greater",
                        xp < p.getXp())
        );
    }

//    @Test
//    public void outOfAmmo() {
//        GameManager.CreatePlayer();
//        Player p = GameManager.getPlayer();
//        int ammo = p.getAmmo();
//        for(int i=0; i<ammo; i++){
//            p.shoot();
//        }
//        System.out.println(p.getAmmo());
//    }

}

