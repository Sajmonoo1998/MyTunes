package mytunes.be;

import mytunes.dal.playlistSongsDAO;

public class Playlist
{
    private final int id;
    private String name;
    playlistSongsDAO playlistSongsDAO;
    private int countOfSongsOnPlaylist;
    private String timeLengthOfPlaylist = "0";

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

    public String getTimeLengthOfPlaylist()
    {
        return timeLengthOfPlaylist;
    }
}
