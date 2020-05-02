
public abstract class Song {
    private int serialNumber;
    private String title; 
    private String type;//Song type,For example: S or D
    private Song nextSong;   
        
    //Constructor method
    public Song() {
		super();
	}
	public Song(int serialNumber, String title) {
		super();
		this.serialNumber = serialNumber;
		this.title = title;
	} 
	
	public abstract Lyric[] lyricLink();  
	public abstract void play();
	
	//setter/getter method
	public int getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Song getNextSong() {
		return nextSong;
	}
	public void setNextSong(Song next) {
		this.nextSong = next;
	}
    
}
