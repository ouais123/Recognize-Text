package wifi.codewl.recognizetext.Model;

public class Translation {
    private String name,OCRName,link;
    private boolean exists;
    public Translation(String name,String OCRName,String link,boolean exists) {
        this.name = name;
        this.OCRName = OCRName;
        this.link = link;
        this.exists = exists;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOCRName() {
        return OCRName;
    }

    public void setOCRName(String OCRName) {
        this.OCRName = OCRName;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public String getLink() {
        return link;
    }
}
