package com.alok.mongodb.demo.collection;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.util.Date;

@ToString
@Builder
@Data
@Document
public class SampleCollection {
    @Id
    private String id;
    private Long sampleId;
    private String sampleName;
    private String department;
    private String subDepartment;
    private String abcGroup;
    private String abc;
    private String status;
    private String goal;
    @Indexed
    private String yM;
    private Date currentDate;
    private Date currentTime;
    private Double sum;
}
