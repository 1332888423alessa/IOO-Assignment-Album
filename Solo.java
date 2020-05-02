/*
 * Class Name:  Solo
 *
 * Class Description:
 *     The class inherits the Song class. It has an array of Lyrics objects
 */
public class Solo extends Song {
	 
	private Lyric[] lyrics; 
	
	//constructor
	public Solo(int lineNum){
		lyrics = new Lyric[lineNum];
	}
	
	//set value for each line of lyric
	public void setLyric(int index,Lyric lyric){
		lyrics[index] = lyric;
	}
	
	public void setLyric(Lyric[] lyrics){
		this.lyrics = lyrics;
	}
	
	@Override
	public String toString() { 
		return this.getType() + ":" + this.getTitle();
	} 
	
	@Override
	public Lyric[] lyricLink() {		
		return lyrics;
	} 
	
	@Override
	public void play(){
		 if(lyrics!=null && lyrics.length>0){
			 for(Lyric lyric:lyrics){
				    System.out.println(lyric.getLyric() + "  \t\t(Display for " + lyric.getInterval()+" seconds)");
					try {
						Thread.sleep((int)(lyric.getInterval()*1000));
					} catch (InterruptedException e) { 
						e.printStackTrace();
					}
			 }
		 }
	}
    
    
}
