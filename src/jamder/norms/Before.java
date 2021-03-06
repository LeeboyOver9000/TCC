package jamder.norms;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Before extends NormConstraint {
	private Calendar date;

	public Before(Calendar date) {
		setDate( date );
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public boolean isTrue() {
		Calendar now = new GregorianCalendar();

		if( date != null )
			if( date.compareTo(now) > 0 )
				return true;

		return false;
	}
}