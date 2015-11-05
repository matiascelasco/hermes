package hermes.monitor.view.helpers;

import hermes.model.Tag;
import hermes.model.enums.Category;
import hermes.model.enums.Content;
import hermes.model.enums.Context;
import hermes.model.enums.Kid;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.RowFilter;
import javax.swing.RowFilter.ComparisonType;

public class NotificationRowFilterBuilder {

	private List<RowFilter<NotificationsTableModel, Object>> filters = new ArrayList<RowFilter<NotificationsTableModel, Object>>();
	private Date fromDate;
	private Date toDate;
	private Context context;
	private Content content;
	private Category category;
	private Kid kid;
	private Tag tag;
	
	private static RowFilter<NotificationsTableModel, Object> orDateTimeFilter(Date dateTime, ComparisonType... types){
		List<RowFilter<NotificationsTableModel, Object>> filters = new ArrayList<RowFilter<NotificationsTableModel, Object>>();
		for (ComparisonType t : types){
			RowFilter<NotificationsTableModel, Object> filter = RowFilter.dateFilter(t, dateTime, 0);
			filters.add(filter);
		}
		return RowFilter.orFilter(filters);
	}
	
	public RowFilter<NotificationsTableModel, Object> build(){
		
		if (fromDate == null || toDate == null){
			throw new IllegalStateException("fromDate and toDate are required");
		}
		RowFilter<NotificationsTableModel, Object> dateFromGE = orDateTimeFilter(fromDate, ComparisonType.AFTER, ComparisonType.EQUAL);
		RowFilter<NotificationsTableModel, Object> dateToLE = orDateTimeFilter(toDate, ComparisonType.BEFORE, ComparisonType.EQUAL);
		filters.add(dateFromGE);
		filters.add(dateToLE);
		
		Object [] objects = {content, context, category, kid, tag};
		int columnIndex = 1;
		for (Object object: objects){
			if (object != null){
				RowFilter<NotificationsTableModel, Object> filter = RowFilter.regexFilter(object.toString(), columnIndex);
				filters.add(filter);
			}
			columnIndex++;
		}
		
		return RowFilter.andFilter(filters);
	}
	
	public NotificationRowFilterBuilder fromDate(Date fromDate){
	    this.fromDate = fromDate;
	    return this;
	}

	public NotificationRowFilterBuilder toDate(Date toDate){
	    this.toDate = toDate;
	    return this;
	}

	public NotificationRowFilterBuilder context(Context context){
	    this.context = context;
	    return this;
	}

	public NotificationRowFilterBuilder content(Content content){
	    this.content = content;
	    return this;
	}

	public NotificationRowFilterBuilder category(Category category){
	    this.category = category;
	    return this;
	}

	public NotificationRowFilterBuilder kid(Kid kid){
	    this.kid = kid;
	    return this;
	}

	public NotificationRowFilterBuilder tag(Tag tag){
	    this.tag = tag;
	    return this;
	}
	
}
