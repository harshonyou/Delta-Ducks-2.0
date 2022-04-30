package com.mygdx.testing.ai;

import com.mygdx.game.AI.Node;
import com.mygdx.game.AI.NodeHeuristic;
import com.mygdx.testing.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class NodeHeuristicTests {

    @Test
    public void estimate() {
        Node n1 = new Node(10, 20);
        Node n2 = new Node(20, 10);
        NodeHeuristic h = new NodeHeuristic();
        assertEquals(h.estimate(n1, n2), 200, 0);
    }
}
