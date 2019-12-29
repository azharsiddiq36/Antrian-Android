package digtive.antrian.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServicesResponse {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Services> data = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Services> getData() {
        return data;
    }

    public void setData(List<Services> data) {
        this.data = data;
    }
}
