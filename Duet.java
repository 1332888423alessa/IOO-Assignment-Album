

public class Duet extends Song {
	
	private Lyric[] lyrics1; 
	private Lyric[] lyrics2; 
	
	//constructor
	public Duet(int lineNum1, int lineNum2){
		lyrics1 = new Lyric[lineNum1];
		lyrics2 = new Lyric[lineNum2];
	}
	public Duet(int lineNum){
		lyrics1 = new Lyric[lineNum];
		lyrics2 = new Lyric[lineNum];
	}
	//Add lyrics for 2 singers
	public void setLyrics1(int index,Lyric lyric){
		lyrics1[index] = lyric;
	}
	
	public void setLyrics2(int index,Lyric lyric){
		lyrics2[index] = lyric;
	}
	

	public Lyric[] getLyrics1() {
		return lyrics1;
	}

	public void setLyrics1(Lyric[] lyrics1) {
		this.lyrics1 = lyrics1;
	}

	public Lyric[] getLyrics2() {
		return lyrics2;
	}

	public void setLyrics2(Lyric[] lyrics2) {
		this.lyrics2 = lyrics2;
	}

	@Override
	public Lyric[] lyricLink() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void play() {
		if(lyrics1!=null && lyrics1.length>0 && lyrics2!=null && lyrics2.length>0){
			//create new array
			Lyric[] singer1 = new Lyric[lyrics1.length];
			//copy array element to new array
			System.arraycopy(lyrics1, 0, singer1, 0, singer1.length);
			Lyric[] singer2 = new Lyric[lyrics2.length];
			System.arraycopy(lyrics2, 0, singer2, 0, singer2.length);
			
			int i =0 ,j = 0;
			while(i<singer1.length || j<singer2.length){
				double interval = 0;
				if(singer1[i].getInterval()==singer2[j].getInterval()){					
					interval = singer1[i].getInterval();
					System.out.println(singer1[i].getLyric() + "  \t\t(Display for " + singer1[i].getInterval()+" seconds)");
					System.out.println(singer2[j].getLyric() + "  \t\t(Display for " + singer2[j].getInterval()+" seconds)");
					singer1[i].setInterval(0);
					singer2[j].setInterval(0);
					i++;
					j++;
				}else if(singer1[i].getInterval()>singer2[j].getInterval()){
					interval = singer2[j].getInterval();
					System.out.println(singer1[i].getLyric() + "  \t\t(Display for " + singer2[j].getInterval()+" seconds)");
					System.out.println(singer2[j].getLyric() + "  \t\t(Display for " + singer2[j].getInterval()+" seconds)");
					singer1[i].setInterval(singer1[i].getInterval()-singer2[j].getInterval());
					singer2[j].setInterval(0);
					j++;
				}else if(singer1[i].getInterval()<singer2[j].getInterval()){
					interval = singer1[i].getInterval();
					System.out.println(singer1[i].getLyric() + "  \t\t(Display for " + singer1[i].getInterval()+" seconds)");
					System.out.println(singer2[j].getLyric() + "  \t\t(Display for " + singer1[i].getInterval()+" seconds)");
					singer2[j].setInterval(singer2[j].getInterval()-singer1[i].getInterval());
					singer1[i].setInterval(0);
					i++;
				}
				//Add time pause
				try {
					Thread.sleep((int)(interval*1000));
				} catch (InterruptedException e) { 
					e.printStackTrace();
				}
			}
			
		}
		
	}

}
