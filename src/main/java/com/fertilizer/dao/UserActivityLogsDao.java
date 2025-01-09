package com.fertilizer.dao;

import java.util.List;

/**
 * @author Dhiraj
 *
 */
public interface UserActivityLogsDao {

	void updateUserActivityLog(List<Long> idsToLog, String response);

}
