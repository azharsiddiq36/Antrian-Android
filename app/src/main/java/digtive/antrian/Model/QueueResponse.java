package digtive.antrian.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QueueResponse {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Queue> data = null;
    @SerializedName("sekarang")
    private Queue sekarang;
    @SerializedName("total")
    private Integer total;
    @SerializedName("sisa")
    private Integer sisa;

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

    public List<Queue> getData() {
        return data;
    }

    public void setData(List<Queue> data) {
        this.data = data;
    }

    public Queue getSekarang() {
        return sekarang;
    }

    public void setSekarang(Queue sekarang) {
        this.sekarang = sekarang;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSisa() {
        return sisa;
    }

    public void setSisa(Integer sisa) {
        this.sisa = sisa;
    }
}
