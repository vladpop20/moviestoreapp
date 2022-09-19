package com.stackroute.CustomerDownloadService.repository;

import com.stackroute.CustomerDownloadService.model.CustomerDownload;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface DownloadRepository extends MongoRepository<CustomerDownload, String> {

       Optional<CustomerDownload> findByCustomerId(String id);
}
