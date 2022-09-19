package com.stackroute.CustomerDownloadService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class CustomerDownload {

    @Id
    private String customerDownId;

    private String customerId;

    private List<Download> downloads;

    public CustomerDownload(String customerId, String customerDownId, List<Download> downloads) {
        this.customerId = customerId;
        this.customerDownId = customerDownId;
        this.downloads = downloads;
    }
    public CustomerDownload(){}

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerDownId() {
        return customerDownId;
    }

    public void setCustomerDownId(String customerDownId) {
        this.customerDownId = customerDownId;
    }

    public List<Download> getDownloads() {
        return downloads;
    }

    public void setDownloads(List<Download> downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return "CustomerDownload{" +
                "customerId=" + customerId +
                ", customerDownId='" + customerDownId + '\'' +
                ", downloads=" + downloads +
                '}';
    }
}
