package ru.bigint.webapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "kml_jobs")
public class KmlJob {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "completed_at")
    private Timestamp completedAt;

    @Column(name = "params")
    private String params;

    @Column(name = "status")
    private int status;

    @Column(name = "result_kml")
    private byte[] resultKml;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Timestamp completedAt) {
        this.completedAt = completedAt;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public byte[] getResultKml() {
        return resultKml;
    }

    public void setResultKml(byte[] resultKml) {
        this.resultKml = resultKml;
    }
}
