package com.example.brad_mikeburzon.game.v2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Brad_Mike Burzon on 7/19/2017.
 */
public class PlayerTest {
    private long expectedScreenWidth = 555;
    private long expectedScreenHeight = 90;

    @Test
    public void testMakePlayerShouldReturnNewInstance() {
        try {
            Player player = Player.makePlayer(expectedScreenWidth, expectedScreenHeight);
            assertNotNull(player);
            assertEquals(expectedScreenWidth, player.getScreenWidth());
            assertEquals(expectedScreenHeight, player.getScreenHeight());
        } catch (Exception e) {
            fail();
        }
    }
}