CREATE TABLE "PRODUCERENTITY" (
    "ACTIVITYID" VARCHAR(255) NOT NULL ,
    "ACTIVITYNAME" VARCHAR(255) ,
    "PRODUCERTYPE" VARCHAR(255) NOT NULL,
    "CONTROLSTATE" INTEGER ,
    "EXECUTIONSTATUS" INTEGER ,
    "ACTIVITYCONTROLPARAMS" BLOB(10K) ,
    "ACTIVITYEXECPARAMS" BLOB(10K) ,
    "ACTIVITYREPORTPARAMS" BLOB(10K) ,
    "SUBSCRIPTIONPARAMS" BLOB(10K) ,
    "SCHEDULE" BLOB(10K) ,
    "MEDIATIONCAPABILITYVALUES" BLOB(10K) ,
    CONSTRAINT PRODUCERENTITY_PK Primary Key (
      "ACTIVITYID" ,
      "PRODUCERTYPE"   
    )
);
COMMIT;