package com.logtool.model;

import java.sql.Timestamp;

public class Upload {

    private int id;
    private String fileName;
    private Timestamp uploadTime;
    private int totalLines;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public Timestamp getUploadTime() { return uploadTime; }
    public void setUploadTime(Timestamp uploadTime) { this.uploadTime = uploadTime; }

    public int getTotalLines() { return totalLines; }
    public void setTotalLines(int totalLines) { this.totalLines = totalLines; }
}