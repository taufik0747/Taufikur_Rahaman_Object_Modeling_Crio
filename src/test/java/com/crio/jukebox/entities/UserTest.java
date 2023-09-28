package com.crio.jukebox.entities;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UserTest")
public class UserTest {

    @Test
    @DisplayName("Test setASongLive with Playlist")
    void testSetASongLiveWithPlaylist() {
        User user = new User("TestUser");
        Playlist playlist = new Playlist("Playlist1", new ArrayList<>());

        // Update: Add playlist to the user
        user.getIdPlaylistMap().put(playlist.getId(), playlist);

        user.setASongLive(playlist.getId(), "song1");

        assertEquals(playlist.getId(), user.getLivePlayListId());
        assertEquals("song1", user.getlivePlaylistSongId());
    }

    @Test
    @DisplayName("Test setASongLive without Playlist")
    void testSetASongLiveWithoutPlaylist() {
        User user = new User("TestUser");

        user.setASongLive("2");

        assertNull(user.getLivePlayListId());
        assertEquals("2", user.getlivePlaylistSongId());
    }
}
