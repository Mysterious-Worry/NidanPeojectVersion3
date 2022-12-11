package in.co.okservices.nidanhospitaapp3.data_models;

public class patient_model {
    String id, sr_no, checked, type, date;

    public patient_model() { }

    public patient_model(String id, String sr_no, String checked, String type, String date) {
        this.id = id;
        this.sr_no = sr_no;
        this.checked = checked;
        this.type = type;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getSr_no() {
        return sr_no;
    }

    public String getChecked() {
        return checked;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSr_no(String sr_no) {
        this.sr_no = sr_no;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
