package com.fertilizer.enums;

/**
 * @author Dhiraj
 *
 */
public enum Activity {
	ACTIVITY_NAME("activity_name"), CREATE_BRAND("create_brand"), CREATE_CHART("create_chart"),
	UPDATE_CHART("update_chart"), CREATE_CITY("create_city"), UPDATE_CITY("update_city"),
	CREATE_COMPANY("create_company"), UPDATE_COMPANY("update_company"), CREATE_COMPOSER("create_composer"),
	UPDATE_COMPOSER("update_composer"), CREATE_ERA("create_era"), UPDATE_ERA("update_era"),
	CREATE_FESTIVE_SEASON("create_festive_season"), UPDATE_FESTIVE_SEASON("update_festive_season"),
	CREATE_GENRE("create_genre"), UPDATE_GENRE("update_genre"), CREATE_GROUP("create_group"),
	UPDATE_GROUP("update_group"), CREATE_LABEL("create_label"), UPDATE_LABEL("update_label"),
	CREATE_LANGUAGE("create_language"), UPDATE_LANGUAGE("update_language"), CREATE_LICENSE("create_license"),
	UPDATE_LICENSE("update_license"), CREATE_LYRICIST("create_lyricist"), UPDATE_LYRICIST("update_lyricist"),
	CREATE_MOOD("create_mood"), UPDATE_MOOD("update_mood"), UPDATE_REGION("update_region"),
	CREATE_REGION("create_region"), CREATE_SINGER("create_singer"), UPDATE_SINGER("update_singer"),
	CREATE_STORE("create_store"), UPDATE_STORE("update_store"), CREATE_TIME_SLOT("create_time_slot"),
	UPDATE_TIME_SLOT("update_time_slot"), CREATE_ZONE("create_zone"), UPDATE_ZONE("updated_zone"),
	DELETE_CHART("delete_chart"), ACTIVATE_DEACTIVATE_CHART("activate_deactivate_chart"),
	DELETE_COMPANY("delete_company"), ACTIVATE_DEACTIVATE_COMPANY("activate_deactivate_company"),
	DELETE_GROUP("delete_group"), ACTIVATE_DEACTIVATE_GROUP("activate_deactivate_group"),
	DELETE_REGION("delete_region"), DELETE_STORE("delete_store"),
	ACTIVATE_DEACTIVATE_STORE("activate_deactivate_store"), DELETE_ZONE("delete_zone"), CREATE_USER("create_user"),
	UPDATE_USER("update_user"), ACTIVATE_USER("activate_user"), DEACTIVATE_USER("deactivate_user"),
	DELETE_USER("delete_user"), ACTIVATE_PASSWORD("activate_password"), CHANGE_PASSWORD("change_password"),
	RESET_PASSWORD("reset_password"), ACTIVATE_CHART("activate_chart"), DEACTIVATE_CHART("deactivate_chart"),
	ACTIVATE_CHART_SONG("activate_chart_song"), DEACTIVATE_CHART_SONG("deactivate_chart_song"),
	DELETE_CHART_SONG("delete_chart_song"), ACTIVATE_COMPANY("activate_company"),
	DEACTIVATE_COMPANY("deactivate_company"), ACTIVATE_STORE("activate_store"), DEACTIVATE_STORE("deactivate_store"),
	DELETE_TIME_SLOT("delete_time_slot"), ASSIGN_USER("assign_user"), ACTIVATE_GROUP("activate_group"),
	DEACTIVATE_GROUP("deactivate_group"), CREATE_GROUP_PLAYLIST("create_group_playlist"),
	UPDATE_GROUP_PLAYLIST("update_group_playlist"), DELETE_GROUP_PLAYLIST("delete_group_playlist"),
	DELETE_GROUP_PLAYLIST_SONGS("delete_group_playlist_songs"), CREATE_MASTER_RULE("create_master_rule"),
	UPDATE_MASTER_RULE("update_master_rule"), UPLOAD_LOGO_CHART("upload_logo_chart"), ADD_CHARTSONGS("add_chartsongs"),
	DELETE_LOGO_CHART("delete_logo_chart"), SCHEDULE_NON_MUSIC("schedule_non_music"),
	UPLOAD_MULTIMEDIA("upload_multimedia"), DELETE_MULTIMEDIA("delete_multimedia");

	private final String name;

	private Activity(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
