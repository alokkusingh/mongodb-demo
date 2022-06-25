package com.alok.mongodb.demo;

import com.alok.mongodb.demo.collection.SampleCollection;
import com.alok.mongodb.demo.repository.SampleCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.aggregation.Aggregation;

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
		//sampleCollectionRepository.deleteAll();
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
						.yM(YearMonth.of(2022, 4))
						.currentDate(new Date())
						.currentTime(ZonedDateTime.now())
						.sum(2004.001)
						.build(),

					SampleCollection.builder()
						.sampleId(12345678L)
						.sampleName("Alok Singh")
						.department("home")
						.abcGroup("appliances")
						.abc("freez")
						.goal("service")
						.yM(YearMonth.of(2022, 4))
						.currentDate(new Date())
						.currentTime(ZonedDateTime.now())
						.sum(34.1)
						.build(),

					SampleCollection.builder()
						.sampleId(12345678L)
						.sampleName("Alok Singh")
						.department("home")
						.abcGroup("appliances")
						.abc("oevn")
						.goal("service")
						.yM(YearMonth.of(2022, 4))
						.currentDate(new Date())
						.currentTime(ZonedDateTime.now())
						.sum(5.1)
						.build(),

					SampleCollection.builder()
						.sampleId(12345679L)
						.sampleName("Rachna Singh")
						.department("home")
						.abcGroup("jewelry")
						.abc("bangle")
						.goal("service")
						.yM(YearMonth.of(2022, 4))
						.currentDate(new Date())
						.currentTime(ZonedDateTime.now())
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

			System.out.println("Get all from 2022-04 and 2022-05 and Alok Singh and tv");
			mongoTemplate.find(Query.query(
					Criteria.where("sampleName").is("Alok Singh")
							.and("abc").is("tv")
							.and("yM").in("2022-04", "2022-05")
			), SampleCollection.class).stream()
					.forEach(System.out::println);

			System.out.println("Get all from 2022-04 or 2022-05");
			mongoTemplate.find(Query.query(
					Criteria.where("yM").in("2022-04", "2022-05")
			).with(Sort.by("sampleName","abc").descending()), SampleCollection.class)
					.stream()
					.forEach(System.out::println);


			int offset = 3;
			int page = 1;
			System.out.println("Get all from 2022-04 or 2022-05 - page 1");
			mongoTemplate.find(Query.query(
					Criteria.where("yM").in("2022-04", "2022-05")
			).with(Sort.by("sampleName","abc").descending()).limit(offset).skip(page > 0 ? ( ( page - 1 ) * offset ) : 0),
					SampleCollection.class)
					.stream()
					.forEach(System.out::println);

			page = 2;
			System.out.println("Get all from 2022-04 or 2022-05 - page 2");
			mongoTemplate.find(Query.query(
					Criteria.where("yM").in("2022-04", "2022-05")
					).with(Sort.by("sampleName","abc").descending()).limit(offset).skip(page > 0 ? ( ( page - 1 ) * offset ) : 0),
					SampleCollection.class)
					.stream()
					.forEach(System.out::println);

			page = 3;
			System.out.println("Get all from 2022-04 or 2022-05 - page 3");
			mongoTemplate.find(Query.query(
					Criteria.where("yM").in("2022-04", "2022-05")
					).with(Sort.by("sampleName","abc").descending()).limit(offset).skip(page > 0 ? ( ( page - 1 ) * offset ) : 0),
					SampleCollection.class)
					.stream()
					.forEach(System.out::println);

			page = 4;
			System.out.println("Get all from 2022-04 or 2022-05 - page 4");
			mongoTemplate.find(Query.query(
					Criteria.where("yM").in("2022-04", "2022-05")
					).with(Sort.by("sampleName","abc").descending()).limit(offset).skip(page > 0 ? ( ( page - 1 ) * offset ) : 0),
					SampleCollection.class)
					.stream()
					.forEach(System.out::println);

			// Agreegate by abc
			GroupOperation groupByAbcAndSumSum = Aggregation.group("abc")
					.sum("sum").as("sum");
			MatchOperation matchYears = Aggregation.match(Criteria.where("yM").in("2022-04", "2022-05"));
			//ProjectionOperation projectRequired = Aggregation.project("sampleName", "abc", "sum");
			SortOperation sortByPopDesc = Aggregation.sort(Sort.by(Sort.Direction.DESC, "abc"));

			//Aggregation aggregation = Aggregation.newAggregation(matchYears, projectRequired);

			Aggregation aggregation = Aggregation.newAggregation(
					groupByAbcAndSumSum, matchYears, sortByPopDesc);

			AggregationResults<SampleCollection> aggregationResult = mongoTemplate.aggregate(
					aggregation, "sampleCollection",
					SampleCollection.class
			);

			aggregationResult.getMappedResults().stream().forEach(System.out::println);
		}
	}
}
