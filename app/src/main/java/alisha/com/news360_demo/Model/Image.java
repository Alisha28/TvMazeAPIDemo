package alisha.com.news360_demo.Model;

import java.io.Serializable;

public class Image implements Serializable {
    private String medium;
    private String original;
    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }
}
