package cn.truthvision.stopsignlib;

import com.google.android.gms.maps.model.LatLng;

import org.opencv.video.Video;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by TV_Laptop_01 on 7/25/2016.
 */
public class VideoStats {

    private int _id;
    private String _filename;
    private String _uri;
    private ArrayList<VideoInfo> _clips;
    private VideoInfo _parent;

    // This class will be assigned to a VideoInfo, therefore allowing smaller Video clips to be attached

    public VideoStats() {

    }

    public VideoStats(int id, String filename, String uri, ArrayList<VideoInfo> clips, VideoInfo parent) {
        this._id = id;
        this._filename = filename;
        this._uri = uri;
        this._clips = clips;
        this._parent = parent;
    }

    public VideoStats(String filename, String uri, ArrayList<VideoInfo> clips, VideoInfo parent) {
        this._filename = filename;
        this._uri = uri;
        this._clips = clips;
        this._parent = parent;
    }

    public void setID(int id) {
        this._id = id;
    }

    public int getID() {
        return this._id;
    }

    public String getFileName() {
        return this._filename;
    }

    public void setFileName(String filename){
        _filename = filename;
    }

    public String getURI() {
        return this._uri;
    }

    public void setURI(String uri) {
        _uri = uri;
    }

    public boolean addClip(VideoInfo clip){
        return this._clips.add(clip);
    }
    public boolean removeClip(VideoInfo clip){
        return this._clips.remove(clip);
    }

    public int clipCount(){
        return this._clips.size();
    }




    public URI getRealURI() throws URISyntaxException {
        return new URI(_uri);
    }

    @Override
    public String toString(){
        return _filename + "(" + _uri + ")";
    }

    public VideoInfo getParent() {
        return _parent;
    }

    public void setParent(VideoInfo parent) {
        this._parent = parent;
    }
}