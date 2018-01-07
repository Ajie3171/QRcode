package ajie.com.qrcode;

/**
 * Created by 阿杰 on 2017/9/25.
 */

public class HistoryText {
    public String getText() {
        return his;
    }


    public int getImageId() {
        return imageId;
    }


    public HistoryText(String text, int imageId) {
        this.his = text;
        this.imageId = imageId;
    }

    private String his;

    private int imageId;
}
