package in.co.okservices.nidanhospitaapp3.data_models;

public class day_record_madel {
    String _id, date, patient_count, collected_money, normal_count, emergency_count,
            normal_paper_valid_count, paper_valid_emergency_count, discount_count,
            cancel_count;

    public day_record_madel() { }

    public day_record_madel(String _id, String date, String patient_count,
                            String collected_money, String normal_count,
                            String emergency_count, String normal_paper_valid_count,
                            String paper_valid_emergency_count, String discount_count,
                            String cancel_count) {
        this._id = _id;
        this.date = date;
        this.patient_count = patient_count;
        this.collected_money = collected_money;
        this.normal_count = normal_count;
        this.emergency_count = emergency_count;
        this.normal_paper_valid_count = normal_paper_valid_count;
        this.paper_valid_emergency_count = paper_valid_emergency_count;
        this.discount_count = discount_count;
        this.cancel_count = cancel_count;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPatient_count() {
        return patient_count;
    }

    public void setPatient_count(String patient_count) {
        this.patient_count = patient_count;
    }

    public String getCollected_money() {
        return collected_money;
    }

    public void setCollected_money(String collected_money) {
        this.collected_money = collected_money;
    }

    public String getNormal_count() {
        return normal_count;
    }

    public void setNormal_count(String normal_count) {
        this.normal_count = normal_count;
    }

    public String getEmergency_count() {
        return emergency_count;
    }

    public void setEmergency_count(String emergency_count) {
        this.emergency_count = emergency_count;
    }

    public String getNormal_paper_valid_count() {
        return normal_paper_valid_count;
    }

    public void setNormal_paper_valid_count(String normal_paper_valid_count) {
        this.normal_paper_valid_count = normal_paper_valid_count;
    }

    public String getPaper_valid_emergency_count() {
        return paper_valid_emergency_count;
    }

    public void setPaper_valid_emergency_count(String paper_valid_emergency_count) {
        this.paper_valid_emergency_count = paper_valid_emergency_count;
    }

    public String getDiscount_count() {
        return discount_count;
    }

    public void setDiscount_count(String discount_count) {
        this.discount_count = discount_count;
    }

    public String getCancel_count() {
        return cancel_count;
    }

    public void setCancel_count(String cancel_count) {
        this.cancel_count = cancel_count;
    }
}
