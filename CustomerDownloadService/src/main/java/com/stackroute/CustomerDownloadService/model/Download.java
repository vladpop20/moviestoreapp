package com.stackroute.CustomerDownloadService.model;

import java.time.LocalDateTime;


public class Download {

    private int downloadId;
    private int movieId;
    private LocalDateTime createdAt;

    public Download(int downloadId, int movieId) {
        this.downloadId = downloadId;
        this.movieId = movieId;
        this.createdAt = LocalDateTime.now();
    }

    public Download() {
    }



    public int getDownloadId() {
        return downloadId;
    }

    public int getMovieId() {
        return movieId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setDownloadId(int downloadId) {
        this.downloadId = downloadId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Download{" +
                "downloadId=" + downloadId +
                ", movieId=" + movieId +
                ", createdAt=" + createdAt +
                '}';
    }
}
