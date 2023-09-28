package com.crio.jukebox.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.crio.jukebox.entities.Artist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.repositories.ISongRepository;

public class SongService implements ISongService{

    ISongRepository songRepository;

    public SongService(ISongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public String loadSongsFromFile(File csvFileName) throws IOException{

        try{
            BufferedReader reader = new BufferedReader(new FileReader(csvFileName));

            // Read the file line by line
            String line;
            while ((line=reader.readLine()) != null){
                String[] fields = line.split(",");
                if (fields.length == 5){
                    String name = fields[0].trim();
                    String generName = fields[1].trim();
                    String albumName = fields[2].trim();
                    String originalArtistName = fields[3].trim();
                    String[] featuredArtistsNames = fields[4].split("#");

                    Artist originalArtist = new Artist(originalArtistName);
                    List<Artist> featuredArtists = new ArrayList<>();
                    for (String featuredArtistName : featuredArtistsNames){
                        featuredArtists.add(new Artist(featuredArtistName.trim()));
                    }

                    Song song = new Song(name, generName, albumName, originalArtist, featuredArtists);
                    songRepository.save(song);
                }
            }
            reader.close();
        } catch (IOException e){
            throw new IOException("Error while reading file!");
        }
        return "Songs Loaded successfully";
    }
}