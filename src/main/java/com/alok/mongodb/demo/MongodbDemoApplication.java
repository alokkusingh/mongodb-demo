package com.alok.mongodb.demo;

import com.alok.mongodb.demo.collection.SampleCollection;
import com.alok.mongodb.demo.repository.SampleCollectionRepository;
import lombok.Data;
import lombok.ToString;
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
							.sampleId(12345778L)
							.sampleName("Alok Singh")
							.department("home")
							.abcGroup("appliances")
							.abc("oevn")
							.goal("service")
							.yM(YearMonth.of(2022, 5))
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
						.build(),

					SampleCollection.builder()
							.sampleId(12345679L)
							.sampleName("Saanvi Singh")
							.department("home")
							.abcGroup("toys")
							.abc("soft")
							.goal("service")
							.yM(YearMonth.of(2022, 4))
							.currentDate(new Date())
							.currentTime(ZonedDateTime.now())
							.sum(100.0)
							.build()
				)
			 );
		} else {
			// using template to query
			/*System.out.println("Get all Alok");
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
					.forEach(System.out::println);*/

			// For 2022-04 Aggregate by sampleName and total the sums
			// stage 1 -  prepare match by yearMonth
			MatchOperation yearMonthMatchOperation = Aggregation.match(Criteria.where("yM").is("2022-04"));

			// stage 2/3 - prepare group by sampleName and total the sums
			GroupOperation sampleNameCountGroupOperation = Aggregation.group("sampleName").count().as("count");
			GroupOperation sampleNameSumGroupOperation = Aggregation.group("sampleName").sum("sum").as("totalSum");

			// stage 4 - sort
			SortOperation sortByNameAscOperation = Aggregation.sort(Sort.Direction.ASC, "sampleName");

			// stage 5 - pagination - limit and skip (will be updated during iteration)
			LimitOperation limitOperation = Aggregation.limit(2); // page size
			SkipOperation skipOperation = Aggregation.skip(0L);

			// stage 5 - projection
			ProjectionOperation projectRequiredFiled = null;

			System.out.println("Group by name -> count. Order Desc on name. Get All");
			mongoTemplate.aggregate(
					Aggregation.newAggregation(
							yearMonthMatchOperation,
							sampleNameCountGroupOperation,
							sortByNameAscOperation
					),
					"sampleCollection",
					NameCount.class
			).getMappedResults().stream().forEach(System.out::println);

			System.out.println("Group by name -> sum. Order Desc on name. Get All");
			System.out.println("Page 1");
			mongoTemplate.aggregate(
					Aggregation.newAggregation(
							yearMonthMatchOperation,
							sampleNameSumGroupOperation,
							sortByNameAscOperation,
							skipOperation,
							limitOperation
					),
					"sampleCollection",
					NameSum.class
			).getMappedResults().stream().forEach(System.out::println);

			System.out.println("Page 2");
			skipOperation = Aggregation.skip(2L);
			mongoTemplate.aggregate(
					Aggregation.newAggregation(
							yearMonthMatchOperation,
							sampleNameSumGroupOperation,
							sortByNameAscOperation,
							skipOperation,
							limitOperation
					),
					"sampleCollection",
					NameSum.class
			).getMappedResults().stream().forEach(System.out::println);
		}
	}

	@Data
	@ToString
	static class NameCount {
		private String id;
		private Integer count;
	}

	@Data
	@ToString
	static class NameSum {
		private String id;
		private Double totalSum;
	}
}
