package alisha.com.news360_demo.Model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

public class Item {
    @SerializedName("name")
    String name;
    @SerializedName("url")
    String url;
    @SerializedName("airstamp")
    String dt;
    @SerializedName("image")
    Image img;


    public Item(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }


}
