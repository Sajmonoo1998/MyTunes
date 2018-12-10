package mytunes.be;

import mytunes.dal.playlistSongsDAO;

public class Playlist
{
    private final int id;
    private String name;
    playlistSongsDAO playlistSongsDAO;
    private int countOfSongsOnPlaylist;
    private String durationOfPlaylist;
    

    public String getName()
    {
        return name;
    }

    public int getID()
    {
        return id;
    }

    public Playlist(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int getCountOfSongsOnPlaylist()
    {
        return countOfSongsOnPlaylist;
    }

    public void setCountOfSongsOnPlaylist(int countOfSongsOnPlaylist)
    {
        this.countOfSongsOnPlaylist = countOfSongsOnPlaylist;
    }

    public String getDurationOfPlaylist() {
        return durationOfPlaylist;
    }

     public void setDurationOfPlaylist(String durationOfPlaylist) {
        this.durationOfPlaylist = durationOfPlaylist;
    }
}
