/*
 * Class Name:  Album
 *
 * Class Description:
 *     The class contains the name of the album and a linked list of Song objects. 
 */

public class Album {
    private String albumName;
    private Song nextSong;
    
    
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	public Song getNextSong() {
		return nextSong;
	}
	
	public void deleteSong(Song song){
		System.out.println("delete song -->" + song.getSerialNumber());
	}
	
	public void addSong(Song song){
		//System.out.println("add song -->" + song.getSerialNumber());
		
	}
	
	public void setNextSong(Song next) {
		Song p = this.getNextSong();
		if(p==null){
			this.nextSong = next;
		}else{
			while(p.getNextSong()!=null){
				p = p.getNextSong();
			}
			p.setNextSong(next);
		}
		
	}
	
	@Override
	public String toString() { 
		return this.albumName;
	}

    
    
}
