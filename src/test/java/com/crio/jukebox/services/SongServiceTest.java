package com.crio.jukebox.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.SongRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SongServiceTest {

    @Mock
    ISongRepository songRepository;
    @InjectMocks
    SongService songService;

    @Test
    public void loadSongsFromFile_songRepo_Test() throws IOException{
        // Given
        ISongRepository songRepository = new SongRepository();
        SongService songService = new SongService(songRepository);
        File inputCSVFile = new File("songs.csv");
        String output;

        // When

        // Then
        output = songService.loadSongsFromFile(inputCSVFile);

        Assertions.assertEquals("Songs Loaded successfully", output);
        try {
            Song aSongFromRepo = songRepository.findById("0")
                    .orElseThrow(() -> new NoSuchElementException("Song with ID 0 not found"));
        System.out.println(aSongFromRepo.getId());
        System.out.println(aSongFromRepo.getName());
        System.out.println(aSongFromRepo.getGenerName());
        System.out.println(aSongFromRepo.getAlbumName());
        System.out.println(aSongFromRepo.getOriginalArtist());
        System.out.println(aSongFromRepo.getFeaturedArtists());
    } catch (NoSuchElementException e) {
        // Handle the exception
        System.out.println("Song not found: " + e.getMessage());
    }
    }

    @Test
    @DisplayName("loadSongsFromFile method should correctly map each song from the *.csv file to a Song class")
    public void loadSongsFromFile_Test() throws IOException{
        // Given
        File inputCSVFile = new File("songs.csv");
        String output;
        output = songService.loadSongsFromFile(inputCSVFile);

        // When

        // Then
        Assertions.assertEquals("Songs Loaded successfully", output);
        ArgumentCaptor<Song> songCaptor = ArgumentCaptor.forClass(Song.class);
        verify(songRepository, times(30)).save((songCaptor.capture()));
        List<Song> capturedSongs = songCaptor.getAllValues();
        Assertions.assertEquals("Ed Sheeran", capturedSongs.get(1).getOriginalArtist().toString());
        Assertions.assertEquals("Daft Punk", capturedSongs.get(5).getOriginalArtist().toString());
    }
}