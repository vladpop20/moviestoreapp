package com.stackroute.CustomerDownloadService.service;

import com.stackroute.CustomerDownloadService.model.CustomerDownload;
import com.stackroute.CustomerDownloadService.model.Download;
import com.stackroute.CustomerDownloadService.repository.DownloadRepository;
import com.stackroute.CustomerDownloadService.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerDownloadServiceImpl implements CustomerDownloadService {

    @Autowired
    private DownloadRepository downloadRepository;

    @Autowired
    private CustomerDownMovieConnect movieConnect;

    @Autowired
    private CustomerDownUserConnect userConnect;

    @Override
    public boolean addCustomerDownload(CustomerDownload customerDownload)
            throws CustomerDownloadDuplicateException, DownloadNotFoundException, UserNotFoundException, MovieNotFoundException {

        Optional<CustomerDownload> customerDownloadFound = downloadRepository.findById(customerDownload.getCustomerDownId());
        if (customerDownloadFound.isPresent()) {
            throw new CustomerDownloadDuplicateException("Customer download already exists!");
        }

        Optional<CustomerDownload> customerDownFoundWithCustId = downloadRepository.findByCustomerId(customerDownload.getCustomerId());
        if (customerDownFoundWithCustId.isPresent()) {
            throw new CustomerDownloadDuplicateException("This customer download for '" + customerDownload.getCustomerId()
                    + "', is a duplicate! - simple make a new download");
        }

        if (userConnect.getUserById(customerDownload.getCustomerId()).equals("none")) {
            throw new UserNotFoundException("Customer with ID: '" + customerDownload.getCustomerId() + "' doesn't exists!");
        }

        List<Download> downloads = customerDownload.getDownloads();

        if (downloads == null || downloads.isEmpty()) {
            throw new DownloadNotFoundException("Customer download list null or empty");
        }

        for (Download d : downloads) {
            if (movieConnect.getMovieById(d.getMovieId()) == -1) {
                throw new MovieNotFoundException("Movie with ID: '" + d.getMovieId() + "' doesn't exists!");
            }
        }

        downloadRepository.save(customerDownload);

        return true;
    }

    @Override
    public Download addDownloadForCustomer(String userId, Download download)
            throws CustomerDownloadNotFoundException, MovieNotFoundException, UserNotFoundException,
            DownloadDuplicateException {

        if (userConnect.getUserById(userId).equals("none")) {
            throw new UserNotFoundException("Customer with ID: '" + userId + "' doesn't exists!");
        }

        if (movieConnect.getMovieById(download.getMovieId()) == -1) {
            throw new MovieNotFoundException("Movie with ID: '" + download.getMovieId() + "' doesn't exists!");
        }

        Optional<CustomerDownload> foundCustomerDown = downloadRepository.findByCustomerId(userId);
        if (foundCustomerDown.isEmpty()) {
            throw new CustomerDownloadNotFoundException("Customer with ID: '" + userId + "' first needs to be created!");
        }

        List<Download> downloads = foundCustomerDown.get().getDownloads();

        boolean duplicateDownload = downloads.stream().anyMatch(d -> d.getDownloadId() == download.getDownloadId());
        if (duplicateDownload) {
            throw new DownloadDuplicateException("This download '" + download.getDownloadId() + "' already exists! Can't be duplicate!");
        }
        downloads.add(download);
        foundCustomerDown.get().setDownloads(downloads);
        downloadRepository.save(foundCustomerDown.get());
        return download;
    }

    @Override
    public Download getDownloadByMovieId(String userId, int movieId)
            throws DownloadNotFoundException, CustomerDownloadNotFoundException, UserNotFoundException,
            MovieNotFoundException {

        if (userConnect.getUserById(userId).equals("none")) {
            throw new UserNotFoundException("Customer with ID: '" + userId + "' doesn't exists!");
        }

        if (movieConnect.getMovieById(movieId) == -1) {
            throw new MovieNotFoundException("Movie with ID: '" + movieId + "' doesn't exists!");
        }

        Optional<CustomerDownload> customerDownload = downloadRepository.findByCustomerId(userId);
        if (customerDownload.isEmpty()) {
            throw new CustomerDownloadNotFoundException("Customer with ID: '" + userId + "' doesn't have any downloads yet!");
        }

        List<Download> downloadList = customerDownload.get().getDownloads();
        Optional<Download> foundDownload = downloadList.stream()
                .filter(download -> download.getMovieId() == movieId)
                .findFirst();

        if (foundDownload.isEmpty()) {
            throw new DownloadNotFoundException("Downloaded movie can't be found!");
        }

        return foundDownload.get();
    }

    @Override
    public boolean deleteDownloadedMovie(String userId, int movieId)
            throws CustomerDownloadNotFoundException, MovieNotFoundException, UserNotFoundException {

        Optional<CustomerDownload> customerDownload = downloadRepository.findByCustomerId(userId);
        if (customerDownload.isEmpty()) {
            throw new CustomerDownloadNotFoundException("Customer with ID: '" + userId + "' doesn't have any downloads yet!");
        }

        if (userConnect.getUserById(userId).equals("none")) {
            throw new UserNotFoundException("Customer with ID: '" + userId + "' doesn't exists!");
        }

        if (movieConnect.getMovieById(movieId) == -1) {
            throw new MovieNotFoundException("Movie with ID: '" + movieId + "' doesn't exists!");
        }

        List<Download> downloadList = customerDownload.get().getDownloads();

        boolean movieStatus = downloadList.stream().anyMatch(obj -> obj.getMovieId() == movieId);
        if (movieStatus == false) {
            throw new MovieNotFoundException("Movie with ID: '" + movieId + "' haven't been yet downloaded!");
        }

        downloadList.removeIf(obj -> obj.getMovieId() == movieId);
        customerDownload.get().setDownloads(downloadList);
        downloadRepository.save(customerDownload.get());
        return true;
    }

    @Override
    public List<Download> getAllDownloadedMovies(String userId)
            throws UserNotFoundException, CustomerDownloadNotFoundException {

        if (userConnect.getUserById(userId).equals("none")) {
            throw new UserNotFoundException("Customer with ID: '" + userId + "' doesn't exists!");
        }

        Optional<CustomerDownload> customerDownload = downloadRepository.findByCustomerId(userId);

        if (customerDownload.isEmpty()) {
            throw new CustomerDownloadNotFoundException("Customer with ID: '" + userId + "' doesn't have any downloads yet!");
        }
        if (customerDownload.get().getDownloads().isEmpty()) {
            throw new CustomerDownloadNotFoundException("Customer with ID: '" + userId + "' doesn't have any downloads!");
        }
        return customerDownload.get().getDownloads();
    }

    @Override
    public Download updateDownload(Download download, String customerId)
            throws DownloadNotFoundException, CustomerDownloadNotFoundException, UserNotFoundException,
            MovieNotFoundException {

        if (userConnect.getUserById(customerId).equals("none")) {
            throw new UserNotFoundException("Customer with ID: '" + customerId + "' doesn't exists!");
        }

        if (movieConnect.getMovieById(download.getMovieId()) == -1) {
            throw new MovieNotFoundException("Movie with ID: '" + download.getMovieId() + "' doesn't exists!");
        }

        Optional<CustomerDownload> downloadsToUpdate = this.downloadRepository.findByCustomerId(customerId);

        if (downloadsToUpdate.isEmpty()) {
            throw new CustomerDownloadNotFoundException("Customer with ID: '" + customerId + "' doesn't have any downloads yet!");
        }

        List<Download> downList = downloadsToUpdate.get().getDownloads();
        //see if any favourites with favId exists in this CustomerFavourite before deleting
        boolean found = downList.stream().anyMatch(fav -> fav.getDownloadId() == download.getDownloadId());
        if (found) {
            downList.removeIf(favourite1 -> favourite1.getDownloadId() == download.getDownloadId());
            downList.add(download);
            downloadsToUpdate.get().setDownloads(downList);
            downloadRepository.save(downloadsToUpdate.get());
            return download;
        }
        throw new DownloadNotFoundException("Downloaded movie can't be found");

    }
}
