import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class BuildDateDimension {

	public static void main(String[] args) 
	{
		String startDate = "";
		int numDaysToBuild = 0;
		int numArgs = 0;

        if(args.length == 2)
        {
        	startDate = args[0];
            numDaysToBuild = Integer.parseInt(args[1]);
        	
        	Calendar calDate = Calendar.getInstance();
    		
    		try 
    		{
    			String datePattern = "yyyy-MM-dd";
    			SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
    			Date dateDate = sdf.parse(startDate);
    			calDate.setTime(dateDate);
            	buildDateRecords(calDate, numDaysToBuild);
    		} 
    		catch (Exception e) 
    		{
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }
        else
        {
        	System.out.println("Wrong num args. Expecting 2.  Got " + args.length);
        }
	}

	private static void buildDateRecords(Calendar startDate, int numDaysToBuild) 
	{
		DateDimensionRecord myDateRecord ;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateDimensionRecordAsCSV = ""; // One date as a date dimension record.
		String dateDimension = "";            // n years of date dimension records.
		
		System.out.println("Stating Date: " + sdf.format(startDate.getTimeInMillis()));
		System.out.println("Creating " + numDaysToBuild + " date dimension records");
		dateDimension = "Date,Full_Date_Name,Day_Of_Week,Month_Name,Day_Number_In_Month,Day_Number_In_Year,Is_Last_Day_In_Week,Is_Last_Day_Of_Month,Week_Ending_Date,Week_Number_In_Year,Month_Number_In_Year,Month_Year,Year_Month,Quarter.Year_Quarter,Year,Is_Holiday,Is_Weekday,Major_Event\n";
		for(int day=0; day<numDaysToBuild; day++)
		{
			dateDimensionRecordAsCSV = "";
			startDate.add(Calendar.DATE, 1);
			myDateRecord = new DateDimensionRecord(startDate);
			dateDimensionRecordAsCSV = sdf.format(myDateRecord.getDate().getTimeInMillis());
			dateDimensionRecordAsCSV = dateDimensionRecordAsCSV + ",\"" + myDateRecord.getFull_Date_Name() + "\"";
			dateDimensionRecordAsCSV = dateDimensionRecordAsCSV + "," + myDateRecord.getDay_Of_Week();
			dateDimensionRecordAsCSV = dateDimensionRecordAsCSV + "," + myDateRecord.getMonth_Name();
			dateDimensionRecordAsCSV = dateDimensionRecordAsCSV + "," + myDateRecord.getDay_Number_In_Month();
			dateDimensionRecordAsCSV = dateDimensionRecordAsCSV + "," + myDateRecord.getDay_Number_In_Year();
			
			if("true".equals(myDateRecord.getIs_Last_Day_In_Week()))
			    dateDimensionRecordAsCSV = dateDimensionRecordAsCSV + ",1";
			else
			    dateDimensionRecordAsCSV = dateDimensionRecordAsCSV + ",0";
			
			if("true".equals(myDateRecord.getIs_Last_Day_Of_Month()))
			    dateDimensionRecordAsCSV = dateDimensionRecordAsCSV + ",1" ;
			else
			    dateDimensionRecordAsCSV = dateDimensionRecordAsCSV + ",0" ;
			
			dateDimensionRecordAsCSV = dateDimensionRecordAsCSV + "," + sdf.format(myDateRecord.getWeek_Ending_Date().getTimeInMillis());
			dateDimensionRecordAsCSV = dateDimensionRecordAsCSV + "," + myDateRecord.getWeek_Number_In_Year();
			dateDimensionRecordAsCSV = dateDimensionRecordAsCSV + "," + myDateRecord.getMonth_Number_In_Year();
			dateDimensionRecordAsCSV = dateDimensionRecordAsCSV + "," + myDateRecord.getMonth_Year();
			dateDimensionRecordAsCSV = dateDimensionRecordAsCSV + "," + myDateRecord.getYear_Month();
			dateDimensionRecordAsCSV = dateDimensionRecordAsCSV + "," + myDateRecord.getQuarter();
			dateDimensionRecordAsCSV = dateDimensionRecordAsCSV + "," + myDateRecord.getYear_Quarter();
			dateDimensionRecordAsCSV = dateDimensionRecordAsCSV + "," + myDateRecord.getYear();
			
			if("true".equals(myDateRecord.getIs_Holiday()))
			    dateDimensionRecordAsCSV = dateDimensionRecordAsCSV + ",1";
			else
				dateDimensionRecordAsCSV = dateDimensionRecordAsCSV + ",0";
			
			if("true".equals(myDateRecord.getIs_Weekday()))
			    dateDimensionRecordAsCSV = dateDimensionRecordAsCSV + ",1";
			else
				dateDimensionRecordAsCSV = dateDimensionRecordAsCSV + ",0";
			
			if(myDateRecord.getMajor_Event().length() == 0)
				dateDimensionRecordAsCSV = dateDimensionRecordAsCSV + ", no event";	
			else
			    dateDimensionRecordAsCSV = dateDimensionRecordAsCSV + "," + myDateRecord.getMajor_Event();
			dateDimensionRecordAsCSV = dateDimensionRecordAsCSV + "\n";
		    dateDimension = dateDimension + dateDimensionRecordAsCSV;
		}
		System.out.println(dateDimension);
	}

}
