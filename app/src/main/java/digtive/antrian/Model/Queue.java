package digtive.antrian.Model;

import com.google.gson.annotations.SerializedName;

public class Queue {
    @SerializedName("antrian_id")
    private String antrianId;
    @SerializedName("antrian_nomor")
    private String antrianNomor;
    @SerializedName("antrian_layanan_id")
    private String antrianLayananId;
    @SerializedName("antrian_loket_id")
    private String antrianLoketId;
    @SerializedName("antrian_date_created")
    private String antrianDateCreated;
    @SerializedName("antrian_status")
    private String antrianStatus;

    public String getAntrianId() {
        return antrianId;
    }

    public void setAntrianId(String antrianId) {
        this.antrianId = antrianId;
    }

    public String getAntrianNomor() {
        return antrianNomor;
    }

    public void setAntrianNomor(String antrianNomor) {
        this.antrianNomor = antrianNomor;
    }

    public String getAntrianLayananId() {
        return antrianLayananId;
    }

    public void setAntrianLayananId(String antrianLayananId) {
        this.antrianLayananId = antrianLayananId;
    }

    public String getAntrianLoketId() {
        return antrianLoketId;
    }

    public void setAntrianLoketId(String antrianLoketId) {
        this.antrianLoketId = antrianLoketId;
    }

    public String getAntrianDateCreated() {
        return antrianDateCreated;
    }

    public void setAntrianDateCreated(String antrianDateCreated) {
        this.antrianDateCreated = antrianDateCreated;
    }

    public String getAntrianStatus() {
        return antrianStatus;
    }

    public void setAntrianStatus(String antrianStatus) {
        this.antrianStatus = antrianStatus;
    }
}
