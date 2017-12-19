create table ALARMVALUE
(
    AlarmKey                VARCHAR(128) UNIQUE,
    AckTime                   BIGINT,
    AckUserId               VARCHAR(128),
    AckSystemId             VARCHAR(128),
    AdditionalText          VARCHAR(128),
    AlarmAckState                   INT,
    AlarmChangedTime            BIGINT,
    AlarmClearedTime            BIGINT,
    AlarmRaisedTime         BIGINT,
    AlarmType               VARCHAR(128) NOT NULL,
    AttributeChanges            BLOB(1k),
    BackedUpStatus          INT,
    BackUpObject                    VARCHAR(400),
    CorrelatedNotifications     BLOB(1k),
    Comments                    BLOB(1k),
    ManagedObjectClass          VARCHAR(128),
    ManagedObjectInstance       VARCHAR(400) NOT NULL,
    MonitoredAttributes         BLOB(1k),
    NotificationId          VARCHAR(128),   
    PerceivedSeverity           INT,
    ProbableCause                 INT NOT NULL,   
    ProposedRepairActions       VARCHAR(128),   
    SystemDN                VARCHAR(256),   
    ThresholdInfo                 BLOB(1k),
    TrendIndication             VARCHAR(16)
);

ALTER TABLE ALARMVALUE 
   ADD CONSTRAINT ALARM_PK PRIMARY KEY ( ManagedObjectInstance, AlarmType, ProbableCause );


create table THRESHOLDMONITORENTITYBEAN
(
  THRESHOLDMONITORPRIMARYKEY CHAR(32) NOT NULL,
  THRESHOLDMONITORKEY BLOB(1k),
  THRESHOLDMONITORVALUE BLOB(10k),
  THRESHOLDMONITORSTATUS INT,
  PERFORMANCEMONITORPRIMARYKEY CHAR(32),
  PERFORMANCEMONITORKEY BLOB(1k),
  THRESHOLDALARMPRIMARYKEY CHAR(32), 
  THRESHOLDALARMNOTIFICATIONID BIGINT   
);


ALTER TABLE THRESHOLDMONITORENTITYBEAN
   ADD CONSTRAINT PK Primary Key (
      THRESHOLDMONITORPRIMARYKEY);


COMMIT;

