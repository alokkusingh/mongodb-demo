package com.alok.mongodb.demo.repository;

import com.alok.mongodb.demo.collection.SampleCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SampleCollectionRepository extends MongoRepository<SampleCollection, String> {
}
