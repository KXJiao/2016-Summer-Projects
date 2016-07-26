package cn.truthvision.stopsignlib;

import com.google.android.gms.maps.model.LatLng;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by TV_Laptop_01 on 7/25/2016.
 */
public class VideoInfo {

    private String _filename;
    private String _uri;
    private double _lat;
    private double _lng;

    public VideoInfo() {

    }

    public VideoInfo(String filename, String uri, double lat, double lng) {
        this._filename = filename;
        this._uri = uri;
        this._lat = lat;
        this._lng = lng;
    }

    public VideoInfo(String filename, String uri) {
        this._filename = filename;
        this._uri = uri;
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

    public void setLatLng(double lat, double lng) {
        this._lat = lat;
        this._lng = lng;
    }

    public double getLat() {
        return this._lat;
    }

    public double getLng(){
        return this._lng;
    }

    public LatLng getLatLng(){
        return new LatLng(_lat,_lng);
    }

    public URI getRealURI() throws URISyntaxException {
        return new URI(_uri);
    }

    @Override
    public String toString(){
        return _filename + "(" + _uri + "), [" +_lat + ", " + _lng + "]";
    }
}