package com.redhat.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.annotation.PostConstruct;
import com.redhat.model.Song;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class SongService {

    private final List<Song> songs = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public List<Song> listAll() {
        return songs;
    }

    public Song findById(Long id) {
        return songs.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }

    public Song add(Song song) {
        song.setId(counter.incrementAndGet());
        songs.add(song);
        return song;
    }

    public boolean delete(Long id) {
        return songs.removeIf(s -> s.getId().equals(id));
    }

    @PostConstruct
    void initSongs() {
        add(new Song(1L, "Bohemian Rhapsody", "Queen"));
        add(new Song(2L, "Imagine", "John Lennon"));
        add(new Song(3L, "Hotel California", "Eagles"));
        add(new Song(4L, "Smells Like Teen Spirit", "Nirvana"));
        add(new Song(5L, "Billie Jean", "Michael Jackson"));
        add(new Song(6L, "Under the Bridge", "Red Hot Chili Peppers"));
        add(new Song(7L, "Black", "Pearl Jam"));
        add(new Song(8L, "Creep", "Radiohead"));
        add(new Song(9L, "November Rain", "Guns N' Roses"));
        add(new Song(10L, "Losing My Religion", "R.E.M."));
    }
}