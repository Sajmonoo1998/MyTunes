/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

/**
 *
 * @author Szymon
 */
public class Song {
    private final int id;
    private String artist;
    private String category;
    private String time;
    private String path;

    public Song(int id, String artist, String category, String time, String path) {
        this.id = id;
        this.artist = artist;
        this.category = category;
        this.time = time;
        this.path = path;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
}
