package digtive.antrian.Model;

import com.google.gson.annotations.SerializedName;

public class AntrianResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private Antrian data;
    @SerializedName("nextQueue")
    private Antrian nextQueue;
    @SerializedName("message")
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Antrian getData() {
        return data;
    }

    public void setData(Antrian data) {
        this.data = data;
    }

    public Antrian getNextQueue() {
        return nextQueue;
    }

    public void setNextQueue(Antrian nextQueue) {
        this.nextQueue = nextQueue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
