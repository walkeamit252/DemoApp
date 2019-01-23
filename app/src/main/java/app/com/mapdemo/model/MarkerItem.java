package app.com.mapdemo.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class MarkerItem {

@JsonProperty("id")
private Integer id;
@JsonProperty("name")
private String name;
@JsonProperty("type")
private String type;
@JsonProperty("subtype")
private String subtype;
@JsonProperty("lat")
private String lat;
@JsonProperty("lon")
private String lon;
@JsonProperty("imageurl")
private String imageurl;
@JsonProperty("locdetails")
private String locdetails;

@JsonProperty("id")
public Integer getId() {
return id;
}

@JsonProperty("id")
public void setId(Integer id) {
this.id = id;
}

@JsonProperty("name")
public String getName() {
return name;
}

@JsonProperty("name")
public void setName(String name) {
this.name = name;
}

@JsonProperty("type")
public String getType() {
return type;
}

@JsonProperty("type")
public void setType(String type) {
this.type = type;
}

@JsonProperty("subtype")
public String getSubtype() {
return subtype;
}

@JsonProperty("subtype")
public void setSubtype(String subtype) {
this.subtype = subtype;
}

@JsonProperty("lat")
public String getLat() {
return lat;
}

@JsonProperty("lat")
public void setLat(String lat) {
this.lat = lat;
}

@JsonProperty("lon")
public String getLon() {
return lon;
}

@JsonProperty("lon")
public void setLon(String lon) {
this.lon = lon;
}

@JsonProperty("imageurl")
public String getImageurl() {
return imageurl;
}

@JsonProperty("imageurl")
public void setImageurl(String imageurl) {
this.imageurl = imageurl;
}

@JsonProperty("locdetails")
public String getLocdetails() {
return locdetails;
}

@JsonProperty("locdetails")
public void setLocdetails(String locdetails) {
this.locdetails = locdetails;
}


}