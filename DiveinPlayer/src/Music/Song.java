
package Music;

import java.io.Serializable;

/*
    This class represents the all the song that can be found in the pc,
        the class was made serializable so that we can keep track of 
        the playlist files i runtime 

*/
public class Song implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String path;
    private String album;

    public Song() {
        
    }

    public Song(String name, String album) {
        this.name = name;
        this.album = album;
    }

    public Song(String name, String path, String album) {
        this.name = name;
        this.path = path;
        this.album = album;
    }

    public String getName() {
        return name;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Song{" + "name=" + name + ", path=" + path + ", album=" + album + '}';
    }




    
}
