package app.tuxguitar.io.ptb.base;

import java.util.ArrayList;
import java.util.List;

public class PTTrack {

	private List<PTSection> sections;
	private List<PTTrackInfo> infos;

	public PTTrack(){
		this.sections = new ArrayList<PTSection>();
		this.infos = new ArrayList<PTTrackInfo>();
	}

	public List<PTTrackInfo> getInfos(){
		return this.infos;
	}

	public List<PTSection> getSections(){
		return this.sections;
	}

	public PTSection getSection(int index){
		for(int i = getSections().size(); i <= index; i ++){
			getSections().add(new PTSection(i));
		}
		return (PTSection)getSections().get(index);
	}

	public PTTrackInfo getInfo(int number){
		for(int i = 0; i < getInfos().size(); i++){
			PTTrackInfo info = (PTTrackInfo)getInfos().get(i);
			if( (  (1 << info.getNumber()) & number ) != 0 ){
				return info;
			}
		}
		return null;
	}

	public PTTrackInfo getDefaultInfo(){
		PTTrackInfo defaultInfo = null;
		for(int i = 0; i < getInfos().size(); i++){
			PTTrackInfo info = (PTTrackInfo)getInfos().get(i);
			if( defaultInfo == null || info.getNumber() < defaultInfo.getNumber() ){
				defaultInfo = info;
			}
		}
		return defaultInfo;
	}
}
