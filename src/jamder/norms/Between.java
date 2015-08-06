package jamder.norms;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Between extends NormConstraint
{
	private Calendar beforeDate;
	private Calendar afterDate;

	public Between(Calendar beforeDate, Calendar afterDate)
	{
		setBeforeDate( beforeDate );
		setAfterDate( afterDate );
	}

	public Calendar getBeforeDate()
	{
		return beforeDate;
	}

	public Calendar getAfterDate()
	{
		return afterDate;
	}

	public void setBeforeDate(Calendar date)
	{
		beforeDate = date;
	}

	public void setAfterDate(Calendar date)
	{
		afterDate = date;
	}

	public boolean isTrue()
	{
		Calendar now = new GregorianCalendar();

		if( beforeDate != null && afterDate != null )
			if(beforeDate.compareTo(now) <= 0 && afterDate.compareTo(now) >= 0)
				return true;

		return false;
	}
}
