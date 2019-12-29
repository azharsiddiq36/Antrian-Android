package digtive.antrian.Model;

import com.google.gson.annotations.SerializedName;

public class Services {
    @SerializedName("layanan_id")
    private String layananId;
    @SerializedName("layanan_nama")
    private String layananNama;
    @SerializedName("layanan_date_created")
    private String layananDateCreated;

    public String getLayananId() {
        return layananId;
    }

    public void setLayananId(String layananId) {
        this.layananId = layananId;
    }

    public String getLayananNama() {
        return layananNama;
    }

    public void setLayananNama(String layananNama) {
        this.layananNama = layananNama;
    }

    public String getLayananDateCreated() {
        return layananDateCreated;
    }

    public void setLayananDateCreated(String layananDateCreated) {
        this.layananDateCreated = layananDateCreated;
    }
}
