package com.alok.mongodb.demo;

import com.alok.mongodb.demo.collection.SampleCollection;
import com.alok.mongodb.demo.repository.SampleCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class MongodbDemoApplication implements ApplicationRunner {

	@Autowired
	private SampleCollectionRepository sampleCollectionRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(MongodbDemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		long numOfCollections = sampleCollectionRepository.count();

		if (numOfCollections == 0) {
			// using repository to save record
			sampleCollectionRepository.saveAll(
				Arrays.asList(
					SampleCollection.builder()
						.sampleId(12345678L)
						.sampleName("Alok Singh")
						.department("home")
						.abcGroup("appliances")
						.abc("tv")
						.goal("service")
						.yM("2022-04")
						.currentDate(new Date())
						.currentTime(new Timestamp(new Date().getTime()))
						.sum(2004.001)
						.build(),

					SampleCollection.builder()
						.sampleId(12345678L)
						.sampleName("Alok Singh")
						.department("home")
						.abcGroup("appliances")
						.abc("freez")
						.goal("service")
						.yM("2022-04")
						.currentDate(new Date())
						.currentTime(new Timestamp(new Date().getTime()))
						.sum(34.1)
						.build(),

					SampleCollection.builder()
						.sampleId(12345678L)
						.sampleName("Alok Singh")
						.department("home")
						.abcGroup("appliances")
						.abc("oevn")
						.goal("service")
						.yM("2022-04")
						.currentDate(new Date())
						.currentTime(new Timestamp(new Date().getTime()))
						.sum(5.1)
						.build(),

					SampleCollection.builder()
						.sampleId(12345679L)
						.sampleName("Rachna Singh")
						.department("home")
						.abcGroup("jewelry")
						.abc("bangle")
						.goal("service")
						.yM("2022-04")
						.currentDate(new Date())
						.currentTime(new Timestamp(new Date().getTime()))
						.sum(10000.5)
						.build()
				)
			);
		} else {
			// using template to query
			System.out.println("Get all Alok");
			List<SampleCollection> records = mongoTemplate.find(Query.query(
					Criteria.where("sampleName").is("Alok Singh")
			), SampleCollection.class);
			records.stream().forEach(System.out::println);

			System.out.println("Get all 2022-04");
			mongoTemplate.find(Query.query(
					Criteria.where("yM").is("2022-04")
			), SampleCollection.class).stream()
					.forEach(System.out::println);

			System.out.println("Get all 2022-05");
			mongoTemplate.find(Query.query(
					Criteria.where("yM").is("2022-05")
			), SampleCollection.class).stream()
					.forEach(System.out::println);

			System.out.println("Get all from 2022-04 or 2022-05");
			mongoTemplate.find(Query.query(
					Criteria.where("yM").in("2022-04", "2022-05")
			), SampleCollection.class).stream()
					.forEach(System.out::println);

			System.out.println("Get all from 2022-04 and 2022-05 and Alok Singh and tv");
			mongoTemplate.find(Query.query(
					Criteria.where("sampleName").is("Alok Singh")
							.and("abc").is("tv")
							.and("yM").in("2022-04", "2022-05")
			), SampleCollection.class).stream()
					.forEach(System.out::println);
		}
	}
}
