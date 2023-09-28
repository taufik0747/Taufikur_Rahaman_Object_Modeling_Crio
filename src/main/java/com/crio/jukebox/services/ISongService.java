package com.crio.jukebox.services;

import java.io.File;
import java.io.IOException;

public interface ISongService {
    public String loadSongsFromFile(File file) throws IOException;
}