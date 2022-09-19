package com.stackroute.CustomerDownloadService.service;

import com.stackroute.CustomerDownloadService.model.CustomerDownload;
import com.stackroute.CustomerDownloadService.model.Download;
import com.stackroute.CustomerDownloadService.util.exception.*;

import java.util.List;

public interface CustomerDownloadService {

    boolean addCustomerDownload(CustomerDownload customerDownload)
			throws CustomerDownloadDuplicateException, DownloadNotFoundException, UserNotFoundException,
            MovieNotFoundException;

	Download addDownloadForCustomer(String userId, Download download)
			throws CustomerDownloadNotFoundException, MovieNotFoundException, UserNotFoundException,
			DownloadDuplicateException;

    Download getDownloadByMovieId(String userId, int movieId)
            throws DownloadNotFoundException, CustomerDownloadNotFoundException, UserNotFoundException,
            MovieNotFoundException;

    boolean deleteDownloadedMovie(String userId, int movieId)
            throws CustomerDownloadNotFoundException, MovieNotFoundException, UserNotFoundException;

    List<Download> getAllDownloadedMovies(String userId) throws UserNotFoundException,
            CustomerDownloadNotFoundException;

    Download updateDownload(Download download, String customerId)
			throws DownloadNotFoundException, CustomerDownloadNotFoundException, UserNotFoundException,
			MovieNotFoundException;
}
