-- -------------------------------------------------------------------------------
-- This SQL creates the call_center_quality data warehouse.  The DWH is based off
-- the data files from http://ie.technion.ac.il/serveng/callcenterdata/index.html
-- This schema was created using MySQL.
--
-- This schema was created by Steve O'Shaughnessy
-- Ver: 2016-02-07
-- ------------------------------------------------------------------------------- 
CREATE DATABASE `call_center_quality` /*!40100 DEFAULT CHARACTER SET utf8 */
;
-- -------------------------------------------------------------------------------
-- Every data warehouse uses dates and needs a date dimension.
-- The csv date data set was created using BuildDateDimension.java and
-- DateDimensionRecord.java
CREATE TABLE d_date (
   d_date_key           INT(10)     UNSIGNED NOT NULL AUTO_INCREMENT,
   calendar_date        DATE        NOT NULL,
   full_date_name       VARCHAR(20) DEFAULT NULL,
   day_of_week          VARCHAR(9)  DEFAULT NULL,
   month_name           VARCHAR(10) DEFAULT NULL,
   day_number_in_month  INT(11)     DEFAULT NULL,
   day_number_in_year   INT(11)     DEFAULT NULL,
   is_last_day_in_week  TINYINT(1)  DEFAULT NULL,
   is_last_day_of_month TINYINT(1)  DEFAULT NULL,
   week_ending_date     DATE        DEFAULT NULL,
   week_number_in_year  INT(11)     DEFAULT NULL,
   month_number_in_year INT(11)     DEFAULT NULL,
   month_year           VARCHAR(15) DEFAULT NULL,
   year_month_          VARCHAR(15) DEFAULT NULL,
   quarter_             CHAR(2)     DEFAULT NULL,
   year_quarter         CHAR(7)     DEFAULT NULL,
   year_                INT(11)     DEFAULT NULL,
   is_holiday           TINYINT(1)  DEFAULT NULL,
   is_weekday           TINYINT(1)  DEFAULT NULL,
   major_event          VARCHAR(45) DEFAULT NULL,
   recmod_date          datetime    DEFAULT NULL,
   recmod_by          VARCHAR(45) DEFAULT NULL,
   PRIMARY KEY (d_date_key),
   UNIQUE (calendar_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;
ALTER TABLE d_date ADD INDEX d_date_idx1 (calendar_date)
;
DELIMITER $$
CREATE TRIGGER d_date_INSERT 
BEFORE INSERT ON d_date 
FOR EACH ROW BEGIN
    SET NEW.recmod_date = NOW();
    SET NEW.recmod_by = CURRENT_USER;
END
$$
DELIMITER $$
CREATE TRIGGER d_date_UPDATE 
BEFORE UPDATE ON d_date 
FOR EACH ROW BEGIN
    SET NEW.recmod_date = NOW();
    SET NEW.recmod_by = CURRENT_USER;
END
$$
LOAD DATA local INFILE 'C:\\Users\\steveo250k\\Dropbox\\Python\\CallCenterAnalysis\\date_dimension.csv'
INTO TABLE d_date
FIELDS TERMINATED BY ',' 
OPTIONALLY ENCLOSED BY '"'   
LINES  TERMINATED BY '\n'  
IGNORE 1 LINES 
(calendar_date, full_date_name, day_of_week, month_name, day_number_in_month, day_number_in_year, is_last_day_in_week, is_last_day_of_month, 
 week_ending_date, week_number_in_year, month_number_in_year, month_year, year_month_, quarter_, year_quarter, year_, is_holiday, 
   is_weekday, major_event)
;
-- -------------------------------------------------------------------------------
-- Next we'll create the fact table from the basic data in the data files.  From
-- this table we'll then extract data for other dimensions.  In the real world 
-- we would create the dimensions first using ETL jobs.  Since this is a "quick
-- and dirty" exercise in understanding call center data, and since the data is
-- static we'll use SQL commands to simulate our ETL processes to quickly build a
-- a DWH from which to play with the data.
-- This initial table layout mostly matches the layout of the data files.
CREATE TABLE ft_call_record 
(
   ft_call_record_key INT(10)     UNSIGNED NOT NULL AUTO_INCREMENT,
   vru         VARCHAR(6),
   line        INT,
   call_id     INT,
   customer_id VARCHAR(12),
   priority    SMALLINT,
   type        CHAR(2),
   call_date   DATE,
   vru_entry   TIME,
   vru_exit    TIME,
   vru_time    INT,
   q_start     TIME,
   q_exit      TIME,
   q_time      INT,
   outcome     VARCHAR(7),
   ser_start   TIME,
   ser_exit    TIME,
   ser_time    INT,
   server_text VARCHAR(15),
   recmod_date datetime    DEFAULT NULL,
   recmod_by   VARCHAR(45) DEFAULT NULL,
   PRIMARY KEY (ft_call_record_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;
DELIMITER $$
CREATE TRIGGER ft_call_record_INSERT 
BEFORE INSERT ON ft_call_record
FOR EACH ROW BEGIN
    SET NEW.recmod_date = NOW();
    SET NEW.recmod_by = CURRENT_USER;
END
$$
DELIMITER $$
CREATE TRIGGER ft_call_record_UPDATE 
BEFORE UPDATE ON ft_call_record 
FOR EACH ROW BEGIN
    SET NEW.recmod_date = NOW();
    SET NEW.recmod_by = CURRENT_USER;
END
$$
-- Load table with raw data.  Repeat for each month 01_January.txt - 12_December.txt
-- Later we'll rework the table to make it a true fact table.
LOAD DATA local INFILE '<path to your data files>\\data\\12_December.txt'
INTO TABLE ft_call_record
FIELDS TERMINATED BY '\t' 
OPTIONALLY ENCLOSED BY '"'   
LINES  TERMINATED BY '\n'  
IGNORE 1 LINES 
(vru, call_id, customer_id, priority, type, call_date, vru_entry, vru_exit, vru_time, q_start, q_exit, q_time, outcome, 
 ser_start, ser_exit, ser_time, server_text)
;
-- -------------------------------------------------------------
-- Now we join the fact table to the date dimension.
ALTER TABLE ft_call_record ADD COLUMN call_date_dimension_key INT AFTER call_date
;
UPDATE ft_call_record rc, d_date dd
SET rc.call_date_dimension_key = dd.d_date_key
WHERE dd.calendar_date = rc.call_date
;
-- Validate change.
SELECT dd.d_date_key, dd.calendar_date, cr.call_date_dimension_key, cr.call_date
FROM ft_call_record cr
JOIN d_date dd ON dd.calendar_date = cr.call_date
;
-- -------------------------------------------------------------------
-- Create a dimension table for Customer Service Representatives.
CREATE TABLE d_customer_service_rep (
   d_customer_service_rep_key INT(10)     UNSIGNED NOT NULL AUTO_INCREMENT,
   hire_date    DATE        DEFAULT NULL,
   user_name    VARCHAR(20) NOT NULL,
   first_name   VARCHAR(9)  DEFAULT NULL,
   last_name    VARCHAR(10) DEFAULT NULL,
   sex          CHAR(1)     DEFAULT NULL,
   rank         INT         DEFAULT NULL,
   recmod_date  DATETIME    DEFAULT NULL,
   recmod_by    VARCHAR(45) DEFAULT NULL,
   PRIMARY KEY (d_customer_service_rep_key),
   UNIQUE (user_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;
ALTER TABLE d_customer_service_rep ADD INDEX d_customer_service_rep_idx1 (user_name)
;
DELIMITER $$
CREATE TRIGGER d_customer_service_rep_INSERT 
BEFORE INSERT ON d_customer_service_rep 
FOR EACH ROW BEGIN
    SET NEW.recmod_date = NOW();
    SET NEW.recmod_by = CURRENT_USER;
END
$$
DELIMITER $$
CREATE TRIGGER d_customer_service_rep_UPDATE 
BEFORE UPDATE ON d_customer_service_rep 
FOR EACH ROW BEGIN
    SET NEW.recmod_date = NOW();
    SET NEW.recmod_by = CURRENT_USER;
END
$$
INSERT INTO d_customer_service_rep (user_name)
(SELECT DISTINCT server_text FROM ft_call_record)
;
-- Now join ft_call_records to this new dimension.
ALTER TABLE ft_call_record ADD COLUMN customer_service_rep_key INT AFTER ser_time
;
UPDATE ft_call_record cr, d_customer_service_rep csr
SET cr.customer_service_rep_key = csr.d_customer_service_rep_key
WHERE cr.server_text = csr.user_name
;
-- Validate change.
SELECT csr.d_customer_service_rep_key, csr.user_name, cr.customer_service_rep_key, cr.server_text
FROM ft_call_record cr
JOIN d_customer_service_rep csr ON csr.user_name = cr.server_text
;
ALTER TABLE ft_call_record DROP COLUMN server_text
;
-- -------------------------------------------------------------------
-- Create a dimension table for Call Types.
CREATE TABLE d_call_type (
   d_call_type_key       INT(10)     UNSIGNED NOT NULL AUTO_INCREMENT,
   call_type             CHAR(2) NOT NULL,
   call_type_description VARCHAR(45)  DEFAULT NULL,
   recmod_date           DATETIME    DEFAULT NULL,
   recmod_by             VARCHAR(45) DEFAULT NULL,
   PRIMARY KEY (d_call_type_key),
   UNIQUE (call_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;
DELIMITER $$
CREATE TRIGGER d_call_type_INSERT 
BEFORE INSERT ON d_call_type 
FOR EACH ROW BEGIN
    SET NEW.recmod_date = NOW();
    SET NEW.recmod_by = CURRENT_USER;
END
$$
DELIMITER $$
CREATE TRIGGER d_call_type_UPDATE 
BEFORE UPDATE ON d_call_type
FOR EACH ROW BEGIN
    SET NEW.recmod_date = NOW();
    SET NEW.recmod_by = CURRENT_USER;
END
$$
-- Values come from "Anonymous Bank" Call-Center Data Documentation by Ilan Guedj and Avi Mandelbaum
-- See document.pdf which accompanies the data files.
INSERT INTO d_call_type (call_type, call_type_description)
VALUES ('PS','Regular Activity'),
        ('PE','Regular Activity in English'),
        ('IN','Internet Consulting'),
        ('NE','Stock Exchange Activity'),
        ('NW','Potential customer getting information.'),
        ('TT','Customer left message. Put on hold.')
;
-- Now join ft_call_record to this new dimension.
ALTER TABLE ft_call_record ADD COLUMN d_call_type_key INT AFTER type
;
UPDATE ft_call_record cr, d_call_type ct
SET cr.d_call_type_key = ct.d_call_type_key
WHERE cr.type = ct.call_type
;
-- Validate change.
SELECT cr.d_call_type_key, cr.type, ct.d_call_type_key, ct.call_type
FROM ft_call_record cr
JOIN d_call_type ct ON cr.type = ct.call_type
;
ALTER TABLE ft_call_record DROP COLUMN type
;
-- Split the VRU + Line column into VRU and Line.
UPDATE ft_call_record SET line = CONVERT(SUBSTRING(vru,5,2),UNSIGNED INTEGER)
;
UPDATE ft_call_record SET vru = SUBSTRING(vru,1,4)
;
-- -------------------------------------------------------------------
-- Create a dimension table for Customers.
CREATE TABLE d_customer 
(
   d_customer_key INT(10)     UNSIGNED NOT NULL AUTO_INCREMENT,
   customer_id    VARCHAR(12) NOT NULL,
   customer_type  CHAR(2) DEFAULT NULL,
   account_number VARCHAR(12)  DEFAULT NULL,
   recmod_date    DATETIME    DEFAULT NULL,
   recmod_by      VARCHAR(45) DEFAULT NULL,
   PRIMARY KEY (d_customer_key),
   UNIQUE (customer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;
ALTER TABLE d_customer ADD INDEX d_customer_idx1 (customer_id)
;
DELIMITER $$
CREATE TRIGGER d_customer_INSERT 
BEFORE INSERT ON d_customer 
FOR EACH ROW BEGIN
    SET NEW.recmod_date = NOW();
    SET NEW.recmod_by = CURRENT_USER;
END
$$
DELIMITER $$
CREATE TRIGGER d_customer_UPDATE 
BEFORE UPDATE ON d_customer
FOR EACH ROW BEGIN
    SET NEW.recmod_date = NOW();
    SET NEW.recmod_by = CURRENT_USER;
END
$$
INSERT INTO d_customer (customer_id)
(SELECT DISTINCT customer_id FROM ft_call_record)
;
-- Now join ft_call_record to this new dimension.
ALTER TABLE ft_call_record ADD COLUMN d_customer_key INT AFTER customer_id
;
UPDATE ft_call_record cr, d_customer dc
SET cr.d_customer_key = dc.d_customer_key
WHERE cr.customer_id = dc.customer_id
;
-- Validate change.
SELECT cr.d_customer_key, cr.customer_id, dc.d_customer_key, dc.customer_id
FROM ft_call_record cr
JOIN d_customer dc ON cr.customer_id = dc.customer_id
;
ALTER TABLE ft_call_record DROP COLUMN customer_id
;
-- Change cryptic column names to be more data warehousecentric.
ALTER TABLE ft_call_record CHANGE COLUMN priority customer_priority SMALLINT(6)
;
ALTER TABLE ft_call_record CHANGE COLUMN vru_entry vru_entry_time TIME
;
ALTER TABLE ft_call_record CHANGE COLUMN vru_exit vru_exit_time TIME
;
ALTER TABLE ft_call_record CHANGE COLUMN vru_time vru_elapsed_time_seconds INT(11)
;
ALTER TABLE ft_call_record CHANGE COLUMN q_start queue_entry_time TIME
;
ALTER TABLE ft_call_record CHANGE COLUMN q_exit queue_exit_time TIME
;
ALTER TABLE ft_call_record CHANGE COLUMN q_time queue_elapsed_time_seconds INT(11)
;
ALTER TABLE ft_call_record CHANGE COLUMN ser_start service_entry_time TIME
;
ALTER TABLE ft_call_record CHANGE COLUMN ser_exit service_exit_time TIME
;
ALTER TABLE ft_call_record CHANGE COLUMN ser_time service_elapsed_time_seconds INT(11)
;
