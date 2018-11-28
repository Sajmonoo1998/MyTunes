/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Szymon
 */
public class Playlist {
    private final int id;
    private String name;
    List<Song> songsList = new ArrayList<>();

    public Playlist(int id,String name) {
        this.id = id;
        this.name=name;
    }

    @Override
    public String toString() {
        return "Playlist{" + "id=" + id + ", name=" + name + ", songsList=" + songsList + '}';
    }
    
    
}
