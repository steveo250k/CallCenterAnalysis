# CallCenterAnalysis
With the files in this repository you can create a data warehouse of call center data.
The data is from http://ie.technion.ac.il/serveng/callcenterdata/index.html. 
It represents over 400k call records for an anonymous Israeli bank.

The java files are used to create the records for a date dimension.  In this case
a little over a year's worth of days from 1999.

The file call_center_quality.sql contains the SQL to create a MySQL database.  This
SQL can then create the tables and populate them.  See Call_Center_DWH_Block.PNG for
a block diagram of the DWH.

call_center_analysis2.sql is some queries for examining the data in the data warehouse.
The other png files show some charts based off this data.

Note the call volume for the days of the week.  Since this is an Israeli data set the
number of calls drop on Friday and Saturday.  This coincides with the Jewish Sabbath as 
opposed to the expected drop in volume for Saturday/Sunday one would expect for a US
based data set.
