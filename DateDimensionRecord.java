import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
/*
 * user specification: the function's comment should contain keys as follows: 
 *    1. write about the function's comment.but it must be before the "{talendTypes}" key.
 * 
 *    2. {talendTypes} 's value must be talend Type, it is required . its value should be 
 *       one of: String, char | Character, long | Long, int | Integer, boolean | Boolean, 
 *       byte | Byte, Date, double | Double, float | Float, Object, short | Short
 * 
 *    3. {Category} define a category for the Function. it is required. its value is user-defined.
 * 
 *    4. {param} 's format is: {param} <type>[(<default value or closed list values>)] 
 *        <name>[ : <comment>]
 * 
 *    <type> 's value should be one of: string, int, list, double, object, boolean, long, char, 
 *    date. <name>'s value is the Function's parameter name. the {param} is optional. so if 
 *    you the Function without the parameters. the {param} don't added. you can have many 
 *    parameters for the Function.
 * 
 *    5. {example} gives a example for the Function. it is optional.
 */
public class DateDimensionRecord 
{
	private int      Date_Dimension_Key   = 0;
	private Calendar Date                 = Calendar.getInstance();
	private String   Full_Date_Name       = "";
	private String   Day_Of_Week          = "";
	private String   Month_Name           = "";
	private int      Day_Number_In_Month  = 0;
	private int      Day_Number_In_Year   = 0;
	private Boolean  Is_Last_Day_In_Week  = false;
	private Boolean  Is_Last_Day_Of_Month = false;
	private Calendar Week_Ending_Date     = Calendar.getInstance();
	private int      Week_Number_In_Year  = 0;
	private int      Month_Number_In_Year = 0;
	private String   Month_Year           = "";
	private String   Year_Month           = "";
	private String   Quarter              = "";
	private String   Year_Quarter         = "";
	private int      Year                 = 0;
	private Boolean  Is_Holiday           = false;
	private Boolean  Is_Weekday           = false;
	private String   Major_Event          = "";
	
	public static final int DATE_DIMENSION_KEY   =  1;
	public static final int DATE                 =  2;
	public static final int FULL_DATE_NAME       =  3;
	public static final int DAY_OF_WEEK          =  4;
	public static final int MONTH_NAME           =  5;
	public static final int DAY_NUMBER_IN_MONTH  =  6;
	public static final int DAY_NUMBER_IN_YEAR   =  7;
	public static final int IS_LAST_DAY_IN_WEEK  =  8;
	public static final int IS_LAST_DAY_OF_MONTH =  9;
	public static final int WEEK_ENDING_DATE     = 10;
	public static final int WEEK_NUMBER_IN_YEAR  = 11;
	public static final int MONTH_NUMBER_IN_YEAR = 12;
	public static final int MONTH_YEAR           = 13;
	public static final int YEAR_MONTH2          = 14;
	public static final int QUARTER              = 15;
	public static final int YEAR_QUARTER         = 16;
	public static final int YEAR                 = 17;
	public static final int IS_HOLIDAY           = 18;
	public static final int IS_WEEKDAY           = 19;
	public static final int MAJOR_EVENT          = 20;
	
	public static String[] dateLabels = {"",                     "date_dimension_key", "date", 
			                             "full_date_name",       "day_of_week",        "month_name",
			                             "day_number_in_month",  "day_number_in_year", "is_last_day_im_month", 
			                             "is_last_day_of_month", "week_ending_date",   "week_number_in_year",
			                             "month_number_in_year", "month_year",         "year_month", 
			                             "quarter",              "year_quarter",       "year", 
			                             "is_holiday",           "is_weekday",         "major_event"};

	public static String[] monthName = {"January", "February", "March", 
                                        "April",   "May",      "June", 
                                        "July",    "August",   "September", 
                                        "October", "November", "December"};
	//Constructor
	public DateDimensionRecord(Calendar date)
	{
		parseThisDate(date);
	}
	
	public DateDimensionRecord(String strDate)
	{
		Calendar calDate = Calendar.getInstance();
		
		try 
		{
			String datePattern = "yyyy-MM-dd";
			SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
			Date dateDate = sdf.parse(strDate);
			calDate.setTime(dateDate);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parseThisDate(calDate);
	}
	
	//Default or Null DateDimension constructor.
	public DateDimensionRecord()
	{
		this.getNullDate();
	}
	
	public static void main(String[] args) throws IOException 
	{
		String datePattern = "MM/dd/yyyy";
		Calendar calDate = Calendar.getInstance();
		Date dateDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
		
		if(args.length == 0)
			dateDate = new Date(); //Use now, today.
		else
		{
			try 
			{
				dateDate = sdf.parse(args[0]);
			} 
			catch (ParseException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		calDate.setTime(dateDate);
//		DateDimension dateDim = new DateDimension(calDate);
	}

	private void parseThisDate(Calendar calDate)
	{
//		String[] monthName = {"January", "February", "March", 
//				              "April",   "May",      "June", 
//				              "July",    "August",   "September", 
//				              "October", "November", "December"};
		
		String[] dayName = {"Sunday",   "Monday", "Tuesday", "Wednesday",
				            "Thursday", "Friday", "Saturday"};
		
		int year  = calDate.get(Calendar.YEAR);
		int month = calDate.get(Calendar.MONTH);
		int day   = calDate.get(Calendar.DATE);

		calDate.set(Calendar.HOUR_OF_DAY,   0);
		calDate.set(Calendar.MINUTE, 0);
		calDate.set(Calendar.SECOND, 0);
		calDate.set(Calendar.MILLISECOND, 0);

		this.Date.setTime(calDate.getTime());

		this.Full_Date_Name = monthName[month] + " " + day + ", " + year;
		
		this.Day_Of_Week = dayName[calDate.get(Calendar.DAY_OF_WEEK)-1];
        if(this.Day_Of_Week.equals("Saturday"))
    		this.Is_Last_Day_In_Week = true;
        else
        	this.Is_Last_Day_In_Week = false;

        this.Month_Name = monthName[month];
		
		this.Day_Number_In_Month = calDate.get(Calendar.DATE);
		
//		SimpleDateFormat sdf = new SimpleDateFormat("D", myLocale);
		this.Day_Number_In_Year = calDate.get(Calendar.DAY_OF_YEAR); //Integer.valueOf(sdf.format(dateDate));
		
		this.Month_Number_In_Year = calDate.get(Calendar.MONTH) + 1;
		
		this.Year = calDate.get(Calendar.YEAR);
		Date_Dimension_Key = this.Year * 1000 + this.Day_Number_In_Year;
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(this.Year, this.Month_Number_In_Year-1, this.Day_Number_In_Month); //Calendar months are zero based.
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		if(this.Day_Number_In_Month == maxDay)
			this.Is_Last_Day_Of_Month = true;
		else
			this.Is_Last_Day_Of_Month = false;
		
		//Find the week ending date.
		int dayOfWeek = calDate.get(Calendar.DAY_OF_WEEK);
		this.Week_Ending_Date.setTime(calDate.getTime());
		this.Week_Ending_Date.add(Calendar.DATE, 7-dayOfWeek);

		//TODO: Need better definition of week in year. e.g., look at year and use either 1 or 53.  1/1/2010 is either 53 for 2009 or 1 for 2010.
		this.Week_Number_In_Year = calDate.get(Calendar.WEEK_OF_YEAR);
		if(this.Month_Number_In_Year <10)
		{
			this.Month_Year = "0" + this.Month_Number_In_Year + "-" + this.Year;
			this.Year_Month = this.Year + "-" + "0" + this.Month_Number_In_Year;
		}
		else
		{
			this.Month_Year = this.Month_Number_In_Year + "-" + this.Year;
			this.Year_Month = this.Year + "-" + this.Month_Number_In_Year;
		}			
		
		switch(this.Month_Number_In_Year)
		{
		case(1):
		case(2):
		case(3):
			this.Quarter = "Q1";
			this.Year_Quarter = this.Year + "-" + "Q1";
			break;
		case(4):
		case(5):
		case(6):
			this.Quarter = "Q2";
			this.Year_Quarter = this.Year + "-" + "Q2";
			break;
		case(7):
		case(8):
		case(9):
			this.Quarter = "Q3";
			this.Year_Quarter = this.Year + "-" + "Q3";
			break;
		case(10):
		case(11):
		case(12):
			this.Quarter = "Q4";
			this.Year_Quarter = this.Year + "-" + "Q4";
			break;
		}
		
		if((calDate.get(Calendar.DAY_OF_WEEK) == 1) || (calDate.get(Calendar.DAY_OF_WEEK) == 7))
			this.Is_Weekday = false;
		else
			this.Is_Weekday = true;
		
		//***********************************************************************
		//** Holiday Calculations
		//***********************************************************************
		//************ January ************************
		if(this.Month_Number_In_Year == 1) //January
		{
			if(this.Day_Number_In_Month == 1) //January 1.
			{
				this.Is_Holiday = true;
				this.Major_Event = "New Years Day";
			}
		
			//If New Year's Day is on a Sunday it will be observed on Monday 1/2. Note: This logic is 
			//subject to change on a year by year basis by TCS HR.
			Calendar cal = new GregorianCalendar(this.Year, Calendar.JANUARY, 1);
			if(this.Day_Number_In_Month == 2) //Check to see if the 1st is a Sunday.
			{
				if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				{
					this.Is_Holiday = true;
					this.Major_Event = "New Years Day Observed";
				}
			}
			
			//If New Year's Day is on a Saturday it will be observed on Monday 1/3. Note: This logic is 
			// subject to change on a year by year basis by TCS HR.
			if(this.Day_Number_In_Month == 3) //Check to see if the 31st is a Sunday.
			{
				if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
				{
					this.Is_Holiday = true;
					this.Major_Event = "New Years Day Observed";
				}
			}
		}
		
		//************ FEBRUARY ************************
		//Presidents Day – Third Monday in February
		if(this.Month_Number_In_Year == 2) //February
		{
			if(calDate.get(Calendar.DAY_OF_MONTH) > 14) // President's Day is between the 15th & 21st.
			{
				//if(calDate.equals(PresidentsDay(this.Year)))
				if(this.Day_Number_In_Month == PresidentsDay(this.Year))
			    {
			    	this.Is_Holiday = true;
					this.Major_Event = "Presidents Day";
			    }
			}
		}
		
		//************ March ************************
		//Good Friday
		if((this.Month_Number_In_Year == 3) || (this.Month_Number_In_Year == 4)) //March/April
		{
			Date GFOBS = GoodFridayObserved(this.Year);
			if((calDate.get(Calendar.MONTH) == GFOBS.getMonth()) && (calDate.get(Calendar.DAY_OF_MONTH) == GFOBS.getDate()))	
		    {
		    	this.Is_Holiday = true;
				this.Major_Event = "Good Friday";
		    }
		}
		
		//************ May ************************
		//Memorial Day, always on a Monday.
		if(this.Month_Number_In_Year == 5) //May
		{
			if(calDate.getTimeInMillis() == MemorialDay(this.Year).getTime())	
		    {
		    	this.Is_Holiday = true;
				this.Major_Event = "Memorial Day";
		    }
		}
		
		//************ July ************************
		if(this.Month_Number_In_Year == 7) //July
		{
			if(this.Day_Number_In_Month == 4) //July 4.
			{
				this.Is_Holiday = true;
				this.Major_Event = "Independence Day";
			}

			//If Independence Day is on a Sunday it will be observed on Monday 7/5. Note: This logic is 
			//subject to change on a year by year basis by TCS HR.
			Calendar cal = new GregorianCalendar(this.Year, Calendar.JULY, 4);
			if(this.Day_Number_In_Month == 5) //Check to see if the 1st is a Sunday.
			{
				if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				{
					this.Is_Holiday = true;
					this.Major_Event = "Independence Day Observed";
				}
			}
			
			//If Independence Day is on a Saturday it will be observed on Friday 7/3. Note: This logic is 
			//subject to change on a year by year basis by TCS HR.
			if(this.Day_Number_In_Month == 3) //Check to see if the 1st is a Sunday.
			{
				if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
				{
					this.Is_Holiday = true;
					this.Major_Event = "Independence Day Observed";
				}
			}
			
		}
		
		//************ September ************************
		//Labor Day, always on a Monday.
		if(this.Month_Number_In_Year == 9) //September
		{
			if(calDate.getTimeInMillis() == LaborDay(this.Year).getTime())	
		    {
		    	this.Is_Holiday = true;
				this.Major_Event = "Labor Day";
		    }
		}
		
		//************ November ************************
		//Thanksgiving is the 4th Thursday in November.
		if(this.Month_Number_In_Year == 11) //November
		{
			//Thanksgiving falls between 11/22 & 11/28.  Try an limit the amount of calculations.
			if((this.Day_Number_In_Month > 21) && (this.Day_Number_In_Month < 29)) 
			{
				if(calDate.getTimeInMillis() == Thanksgiving(this.Year).getTime())	
			    {
			    	this.Is_Holiday = true;
					this.Major_Event = "Thanksgiving Day";
			    }
				
				if(calDate.get(Calendar.DAY_OF_MONTH) == (Thanksgiving(this.Year).getDate() + 1))
				{
			    	this.Is_Holiday = true;
					this.Major_Event = "Thanksgiving";
				}
			}
		}
		
		//************ December ************************
		if(this.Month_Number_In_Year == 12) //December
		{
			if(this.Day_Number_In_Month == 24) // December 24.
			{
				this.Is_Holiday = true;
				this.Major_Event = "Christmas Eve";
			}
			
			//If Christmas Eve is a Sunday, then Friday 12/22 is observed as the holiday.
			Calendar cal = new GregorianCalendar(this.Year, Calendar.DECEMBER, 24);
			if(this.Day_Number_In_Month == 22)
			{
				if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				{
					this.Is_Holiday = true;
					this.Major_Event = "Christmas Eve Observed";
				}				
			}
			
			//If Christmas Eve is a Saturday,then Friday the 23 is the observed Holiday.
			if(this.Day_Number_In_Month == 23) 
			{
				if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
				{
					this.Is_Holiday = true;
					this.Major_Event = "Christmas Eve Observed";
				}				
			}

			if(this.Day_Number_In_Month == 25) // December 25.
			{
				this.Is_Holiday = true;
				this.Major_Event = "Christmas Day";
			}
			
			//If Christmas Day is a Sunday, then Monday the 26th is the observed holiday.
			cal = new GregorianCalendar(this.Year, Calendar.DECEMBER, 25);
			if(this.Day_Number_In_Month == 26)
			{
				if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				{
					this.Is_Holiday = true;
					this.Major_Event = "Christmas Day Observed";
				}				
			}
			//If Christmas Day is a Saturday, then Monday the 27th is the observed holiday.
			if(this.Day_Number_In_Month == 27) 
			{
				if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
				{
					this.Is_Holiday = true;
					this.Major_Event = "Christmas Day Observed";
				}				
			}

			if(this.Day_Number_In_Month == 31) // December 31.
			{
				this.Is_Holiday = true;
				this.Major_Event = "New Years Eve";
			}
			
			//If New Year's Eve is on a Sunday, it will be observed on Friday 12/29.
			cal = new GregorianCalendar(this.Year, Calendar.DECEMBER, 31);
			if(this.Day_Number_In_Month == 29) 
			{
				if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				{
					this.Is_Holiday = true;
					this.Major_Event = "New Years Eve Observed";
				}
			}
			
			//If New Year's Eve is on a Saturday, it will be observed on Friday 12/30.
			if(this.Day_Number_In_Month == 30) 
			{
				if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
				{
					this.Is_Holiday = true;
					this.Major_Event = "New Years Eve Observed";
				}
			}
		}
	} //end ParseThisDate()
		
		public static int PresidentsDay (int nYear)
		{
			// Third Monday in February
			Calendar cal = new GregorianCalendar(nYear, Calendar.FEBRUARY, 1);
			//From the first day of the month of February, from the day of the week
			//figure out which date is the 3rd Tuesday. e.g. if 2/1 is a Sunday then
			// 2/16 would be the 3rd Tuesday.
			switch(cal.get(Calendar.DAY_OF_WEEK))
			{
				case Calendar.SUNDAY :
					return 16; //(new GregorianCalendar(nYear, Calendar.FEBRUARY, 16)).getTime();
				case Calendar.MONDAY :
					return 15; //(new GregorianCalendar(nYear, Calendar.FEBRUARY, 15)).getTime();
				case Calendar.TUESDAY :
					return 21; //(new GregorianCalendar(nYear, Calendar.FEBRUARY, 21)).getTime();
				case Calendar.WEDNESDAY :
					return 20; //(new GregorianCalendar(nYear, Calendar.FEBRUARY, 20)).getTime();
				case Calendar.THURSDAY :
					return 19; //(new GregorianCalendar(nYear, Calendar.FEBRUARY, 19)).getTime();
				case Calendar.FRIDAY :
					return 18; //(new GregorianCalendar(nYear, Calendar.FEBRUARY, 18)).getTime();
				default : // Saturday
					return 17; //(new GregorianCalendar(nYear, Calendar.FEBRUARY, 17)).getTime();
			}
		}
		
		public static Date EasterSunday(int year) //Based on lunar calendar and converted to Gregorian calendar.
		{
		    if (year <= 1582) 
		    {
		      throw new IllegalArgumentException("Algorithm invalid before April 1583");
		    }
		    int golden, century, x, z, d, epact, n;

		    golden = (year % 19) + 1; /* E1: metonic cycle */
		    century = (year / 100) + 1; /* E2: e.g. 1984 was in 20th C */
		    x = (3 * century / 4) - 12; /* E3: leap year correction */
		    z = ((8 * century + 5) / 25) - 5; /* E3: sync with moon's orbit */
		    d = (5 * year / 4) - x - 10;
		    epact = (11 * golden + 20 + z - x) % 30; /* E5: epact */
		    if ((epact == 25 && golden > 11) || epact == 24)
		      epact++;
		    n = 44 - epact;
		    n += 30 * (n < 21 ? 1 : 0); /* E6: */
		    n += 7 - ((d + n) % 7);
		    if (n > 31) /* E7: */
		      return new GregorianCalendar(year, 4 - 1, n - 31).getTime(); /* April */
		    else
		      return new GregorianCalendar(year, 3 - 1, n).getTime(); /* March */
		}
		
		//TODO: Convert depreciated Date methods to Calendar.
		public static Date GoodFridayObserved(int nYear)
	    {
		    // Get Easter Sunday and subtract two days
		    int nEasterMonth    = 0;
		    int nEasterDay      = 0;
		    int nGoodFridayMonth    = 0;
		    int nGoodFridayDay  = 0;
		    Date dEasterSunday;
		    dEasterSunday = EasterSunday(nYear);
		    nEasterMonth = dEasterSunday.getMonth();
		    nEasterDay = dEasterSunday.getDate();
		    if (nEasterDay <= 3 && nEasterMonth == 3) // Check if <= April 3rd
		        {
		        switch(nEasterDay)
		        {
		        case 3 : 
		            nGoodFridayMonth = nEasterMonth - 1;
		            nGoodFridayDay   = nEasterDay - 2;
		            break;
		        case 2 :
		            nGoodFridayMonth = nEasterMonth - 1;
		            nGoodFridayDay   = 31;
		            break;
		        case 1 :
		            nGoodFridayMonth = nEasterMonth - 1;
		            nGoodFridayDay   = 31;
		            break;
		        default:
		            nGoodFridayMonth = nEasterMonth;
		            nGoodFridayDay   = nEasterDay - 2;
		        }
		        }
		    else
		        {
		        nGoodFridayMonth = nEasterMonth;
		        nGoodFridayDay   = nEasterDay - 2;
		        }
		    return new Date(nYear, nGoodFridayMonth, nGoodFridayDay);
	    }

		
		public static Date MemorialDay (int nYear)
		{
			// Last Monday in May
			Calendar cal = new GregorianCalendar(nYear, Calendar.MAY, 1);
			switch(cal.get(Calendar.DAY_OF_WEEK))
			{
				case Calendar.SUNDAY :
					return (new GregorianCalendar(nYear, Calendar.MAY, 30)).getTime();
				case Calendar.MONDAY :
					return (new GregorianCalendar(nYear, Calendar.MAY, 29)).getTime();
				case Calendar.TUESDAY :
					return (new GregorianCalendar(nYear, Calendar.MAY, 28)).getTime();
				case Calendar.WEDNESDAY :
					return (new GregorianCalendar(nYear, Calendar.MAY, 27)).getTime();
				case Calendar.THURSDAY :
					return (new GregorianCalendar(nYear, Calendar.MAY, 26)).getTime();
				case Calendar.FRIDAY :
					return (new GregorianCalendar(nYear, Calendar.MAY, 25)).getTime();
				default : // Saturday
					return (new GregorianCalendar(nYear, Calendar.MAY, 31)).getTime();
			}
		}	

		public static Date LaborDay (int nYear)
		{
			// The first Monday in September
			Calendar cal = new GregorianCalendar(nYear, Calendar.SEPTEMBER, 1);
			switch(cal.get(Calendar.DAY_OF_WEEK))
			{
				case Calendar.TUESDAY :
					return (new GregorianCalendar(nYear, Calendar.SEPTEMBER, 7)).getTime();
				case Calendar.WEDNESDAY :
					return (new GregorianCalendar(nYear, Calendar.SEPTEMBER, 6)).getTime();
				case Calendar.THURSDAY :
					return (new GregorianCalendar(nYear, Calendar.SEPTEMBER, 5)).getTime();
				case Calendar.FRIDAY :
					return (new GregorianCalendar(nYear, Calendar.SEPTEMBER, 4)).getTime();
				case Calendar.SATURDAY :
					return (new GregorianCalendar(nYear, Calendar.SEPTEMBER, 3)).getTime();
				case Calendar.SUNDAY :
					return (new GregorianCalendar(nYear, Calendar.SEPTEMBER, 2)).getTime();
				case Calendar.MONDAY :
				default :
					return cal.getTime();
			}
		}

		public static Date Thanksgiving(int nYear)
		{
			Calendar cal = new GregorianCalendar(nYear, Calendar.NOVEMBER, 1);
			switch(cal.get(Calendar.DAY_OF_WEEK))
			{
				case Calendar.SUNDAY :
					return (new GregorianCalendar(nYear, Calendar.NOVEMBER, 26)).getTime();
				case Calendar.MONDAY :
					return (new GregorianCalendar(nYear, Calendar.NOVEMBER, 25)).getTime();
				case Calendar.TUESDAY :
					return (new GregorianCalendar(nYear, Calendar.NOVEMBER, 24)).getTime();
				case Calendar.WEDNESDAY :
					return (new GregorianCalendar(nYear, Calendar.NOVEMBER, 23)).getTime();
				case Calendar.THURSDAY :
					return (new GregorianCalendar(nYear, Calendar.NOVEMBER, 22)).getTime();
				case Calendar.FRIDAY :
					return (new GregorianCalendar(nYear, Calendar.NOVEMBER, 28)).getTime();
				default : // Saturday
					return (new GregorianCalendar(nYear, Calendar.NOVEMBER, 27)).getTime();
			}
		}
		


	/*********************************************************
	** Setters
	** @return
	*********************************************************/
	public void setDate(Date newDate) 
	{
		this.Date.setTime(newDate);
	}

	public void setMonth_Name(String newMonth) 
	{
		this.Month_Name = newMonth;
	}

	public void setFull_Date_Name(String newFullMonthName) 
	{
		this.Full_Date_Name = newFullMonthName;
	}

	public void setDay_Of_Week(String newDayOfWeek) 
	{
		this.Day_Of_Week = newDayOfWeek;
	}

	public void setDay_Number_In_Month(int newDayNumber) 
	{
		this.Day_Number_In_Month = newDayNumber;
	}

	public void setDay_Number_In_Year(int newDayNumber) 
	{
		this.Day_Number_In_Year = newDayNumber;
	}

	public void setIs_Last_Day_In_Week(boolean newIsLastDay) 
	{
		this.Is_Last_Day_In_Week = newIsLastDay;
	}

	public void setIs_Last_Day_Of_Month(boolean newIsLastDay) 
	{
		this.Is_Last_Day_Of_Month = newIsLastDay;
	}

	public void setWeek_Ending_Date(Date newEndDate) 
	{
		this.Week_Ending_Date.setTime(newEndDate);
	}

	public void setWeek_Number_In_Year(int newWeekNumber) 
	{
		this.Week_Number_In_Year = newWeekNumber;
	}

	public void setMonth_Number_In_Year(int newMonthNumber) 
	{
		this.Month_Number_In_Year = newMonthNumber;
	}

	public void setMonth_Year(String newMonthYear) 
	{
		this.Month_Year = newMonthYear;
	}

	public void setYear_Month(String newYearMonth)
	{
		this.Year_Month = newYearMonth;
	}

	public void setQuarter(String newQuarter) 
	{
		this.Quarter = newQuarter;
	}

	public void setYear_Quarter(String newYearQuarter) 
	{
		this.Year_Quarter = newYearQuarter;
	}

	public void setYear(int newYear) 
	{
		this.Year = newYear;
	}

	public void setIs_Holiday(boolean newIsHoliday) 
	{
		this.Is_Holiday = newIsHoliday;
	}

	public void setIs_Weekday(boolean newIsWeekday) 
	{
		this.Is_Weekday = newIsWeekday;
	}

	public void setMajor_Event(String newMajorEvent) 
	{
		this.Major_Event = newMajorEvent;
	}
	
	public void setNullDate()
	{
		this.Date.setTimeInMillis(1);
		this.Full_Date_Name = "Date Not Set";
		this.Day_Of_Week = "";
		this.Month_Name = "";
		this.Day_Number_In_Month = -1;
		this.Day_Number_In_Year = -1;
		this.Is_Last_Day_In_Week = null;
		this.Is_Last_Day_Of_Month = null;
		this.Week_Ending_Date.setTimeInMillis(0);
		this.Week_Number_In_Year = -1;
		this.Month_Number_In_Year = -1;
		this.Month_Year = "";
		this.Quarter = "";
		this.Year_Quarter = "";
		this.Year = -1;
		this.Is_Holiday = null;
		this.Is_Weekday = null;
		this.Major_Event = "";
		
	}
	
	/*********************************************************
	** Getters
	** @return
	*********************************************************/
	public int getDate_Dimension_Key()
	{
		return this.Date_Dimension_Key;
	}
	public Calendar getDate() 
	{
		return this.Date;
	}

	public String getMonth_Name() 
	{
		return this.Month_Name;
	}

	public String getFull_Date_Name() 
	{
		return this.Full_Date_Name;
	}

	public String getDay_Of_Week() 
	{
		return this.Day_Of_Week;
	}

	public int getDay_Number_In_Month() 
	{
		return this.Day_Number_In_Month;
	}

	public int getDay_Number_In_Year() 
	{
		return this.Day_Number_In_Year;
	}

	public Boolean getIs_Last_Day_In_Week() 
	{
		return this.Is_Last_Day_In_Week;
	}

	public Boolean getIs_Last_Day_Of_Month() 
	{
		return this.Is_Last_Day_Of_Month;
	}

	public Calendar getWeek_Ending_Date() 
	{
		return this.Week_Ending_Date;
	}

	public int getWeek_Number_In_Year() 
	{
		return this.Week_Number_In_Year;
	}

	public int getMonth_Number_In_Year() 
	{
		return this.Month_Number_In_Year;
	}

	public String getMonth_Year() 
	{
		return this.Month_Year;
	}

	public String getYear_Month()
	{
		return this.Year_Month;
	}

	public String getQuarter() 
	{
		return this.Quarter;
	}

	public String getYear_Quarter() 
	{
		return this.Year_Quarter;
	}

	public int getYear() 
	{
		return this.Year;
	}

	public Boolean getIs_Holiday() 
	{
		return this.Is_Holiday;
	}

	public Boolean getIs_Weekday() 
	{
		return this.Is_Weekday;
	}

	public String getMajor_Event() 
	{
		return this.Major_Event;
	}	
	
	public DateDimensionRecord getNullDate()
	{
		this.Date_Dimension_Key = 0;
		this.Date.setTimeInMillis(1);
		this.Full_Date_Name = "Date Not Set";
		this.Day_Of_Week = "";
		this.Month_Name = "";
		this.Day_Number_In_Month = -1;
		this.Day_Number_In_Year = -1;
		this.Is_Last_Day_In_Week = false;
		this.Is_Last_Day_Of_Month = false;
		this.Week_Ending_Date.setTimeInMillis(0);
		this.Week_Number_In_Year = -1;
		this.Month_Number_In_Year = -1;
		this.Month_Year = "";
		this.Quarter = "";
		this.Year_Quarter = "";
		this.Year = -1;
		this.Is_Holiday = false;
		this.Is_Weekday = false;
		this.Major_Event = "";
		return this;
	}
	
	public String getDateAsString(String format)
	{
		String strDate = "";
		
		//Convert this.Date (a Calendar) to a Date, then format as a string.
		Date dateCurrDate = new Date(this.Date.getTimeInMillis());
		strDate = new SimpleDateFormat(format).format(dateCurrDate);
		
		return strDate;
	}
	

}
