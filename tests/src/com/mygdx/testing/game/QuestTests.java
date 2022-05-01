package com.mygdx.testing.game;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Entitys.College;
import com.mygdx.game.Entitys.Player;
import com.mygdx.game.Managers.GameManager;
import com.mygdx.game.Managers.PhysicsManager;
import com.mygdx.game.Managers.QuestManager;
import com.mygdx.game.Managers.ResourceManager;
import com.mygdx.game.PirateGame;
import com.mygdx.game.Quests.KillQuest;
import com.mygdx.game.Quests.LocateQuest;
import com.mygdx.game.Quests.Quest;
import com.mygdx.testing.GdxTestRunner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(GdxTestRunner.class)
public class QuestTests {

    @BeforeClass
    public static void init(){
        PirateGame.loadResources();
        PhysicsManager.Initialise();
        GameManager.Initialise();

    }

    @AfterClass
    public static void dispose(){
        ResourceManager.reset();
    }

    @Test
    public void KillCompleteTest (){
        GameManager.CreatePlayer();
        Player p = GameManager.getPlayer();
        int before = p.getPlunder();
        QuestManager.Initialise();
        College c = new College(1);
        Quest q = new KillQuest(c);
        assertFalse(q.checkCompleted(p));
        QuestManager.addQuest(q);
        c.destroy();
        QuestManager.checkCompleted();
        int after = p.getPlunder();
        assertTrue(after > before);
        assertTrue(q.checkCompleted(p));
    }

    @Test
    public void LocateCompleteTest (){
        GameManager.CreatePlayer();
        Player p = GameManager.getPlayer();
        int before = p.getPlunder();
        QuestManager.Initialise();
        Vector2 loc = new Vector2(p.getPosition());
        Quest q = new LocateQuest(loc, 1);
        assertFalse(q.checkCompleted(p));
        QuestManager.addQuest(q);
        QuestManager.checkCompleted();
        int after = p.getPlunder();
        assertTrue(after > before);
        assertTrue(q.checkCompleted(p));
    }

}
