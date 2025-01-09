package com.fertilizer.util;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.fertilizer.enums.EntityConstant;

import ch.qos.logback.classic.Logger;

public final class QueryUtil {

	private QueryUtil() {

	}

	public static Double rupeeRoundOff(Double amount) {

		Long result = Math.round(amount);

		return result.doubleValue();
	}

	public static String convertForLike(String param) {
		return "%" + param + "%";
	}

	public static String getProperRoleIdentifier(String param) {
		if (param.contains(EntityConstant.ROLE_PREAPPANDER_FOR_SECURITY.getName())) {
			return param.replace(EntityConstant.ROLE_PREAPPANDER_FOR_SECURITY.getName(), "");
		}

		return param;
	}

	public static String calculateEraComment(Year releaseYear) {
		Integer startingDecade = releaseYear.getValue() / 10;
		Integer endingDecade = startingDecade + 1;
		startingDecade = startingDecade * 10;
		endingDecade = endingDecade * 10;

		return Integer.toString(startingDecade) + "-" + Integer.toString(endingDecade);
	}

	public static String calculateEraName(Year releaseYear) {
		return Integer.toString((releaseYear.getValue() % 100) / 10) + "0's";
	}

	public static String sanitizedFileName(String filename) {

		String[][] latinEnglish = new String[][] { { "é", "e" }, { "è", "e" }, { "ë", "e" }, { "ê", "e" }, { "à", "a" },
				{ "ä", "ae" }, { "â", "a" }, { "Ä", "Ae" }, { "ù", "u" }, { "ü", "ue" }, { "û", "u" }, { "Ü", "Ue" },
				{ "ö", "oe" }, { "ô", "o" }, { "Ö", "Oe" }, { "ï", "i" }, { "î", "i" }, { "ß", "ss" }

		};
		Map<String, String> latinToEnglish = Stream.of(latinEnglish)
				.collect(Collectors.toMap(data -> data[0], data -> data[1]));
		String basename = FilenameUtils.getBaseName(filename);
		String extension = FilenameUtils.getExtension(filename);

		// Removing the extra white spaces
		basename = basename.trim().replaceAll(" +", " ");
		// replacing all the latin characters
		for (Map.Entry<String, String> entry : latinToEnglish.entrySet())
			basename = basename.replaceAll(entry.getKey(), entry.getValue());

		basename = basename.replaceAll("[^a-zA-Z0-9]", "-");
		basename = basename.replaceAll("-+", "-");
		return basename + "." + extension;
	}

	public static String santizedString(String inputString) {

		// For Trimming
		if (inputString == null || Boolean.TRUE.equals(inputString.trim().equals("")))
			return null;
		else
			return inputString.trim();
	}

	public static boolean isEmailValid(String string) {

		String emailRegex = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

		return string.matches(emailRegex);
	}

	public static List<Long> getLongListFromString(String data) {
		List<Long> result = new ArrayList<>();
		if (data != null && !data.isEmpty()) {
			result = Arrays.asList(data.split("\\,")).stream().map(Long::valueOf).collect(Collectors.toList());
		}
		return result;
	}

	public static Year calculateEraStartYear(Year releaseYear) {

		return Year.parse((Integer.valueOf((releaseYear.getValue() / 10) * 10)).toString());
	}

	public static String getRandomString(int targetStringLength) {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		return buffer.toString();
	}

	public static boolean checkVoucherPercentage(Double amountPledgeded, Double amountToPay,
			int voucherAmountPercentage) {
		boolean status = true;
		double percentage = amountToPay * 100 / amountPledgeded;
		if (percentage < voucherAmountPercentage) {
			status = false;
		}

		return status;
	}

	public static boolean checkVoucherAmount(Double amountPledgeded, int minimumAmount, int voucherAmountMultiple) {
		double modResult = 0;
		boolean status = true;
		if (amountPledgeded < minimumAmount) {
			status = false;
		}
		modResult = amountPledgeded % voucherAmountMultiple;
		if (modResult != 0) {
			status = false;
		}
		return status;

	}

	public static int getVoucherCat(Double amountPledged, int tier1MinValue, int tier1MaxValue, int tier2MaxValue,
			int tier3MaxValue) {
		if (amountPledged > tier1MinValue && amountPledged <= tier1MaxValue) {
			return 1;
		} else if (amountPledged > tier1MaxValue && amountPledged <= tier2MaxValue) {
			return 2;
		} else if (amountPledged > tier2MaxValue && amountPledged <= tier3MaxValue) {
			return 3;
		} else if (amountPledged > tier3MaxValue) {
			return 4;
		} else {
			return 0;
		}

	}

	public static String getVoucherCode() {
		String randomString = QueryUtil.getRandomString(4);
		String currentTime = String.valueOf(new Date().getTime());
		return String.join("", randomString, currentTime.substring(currentTime.length() - 4, currentTime.length()));
	}

	/**
	 * <p>
	 * Have not used the traditional try/catch method because of the required usage
	 * and performance related issues.
	 * </p>
	 * <p>
	 * For more info visit
	 * <a href="https://www.baeldung.com/java-check-string-number">this page</a>
	 * </p>
	 * 
	 * @param points
	 * @return {@code true} if the string is a parsable number.
	 * @author kabir
	 */
	public static boolean isPointsValid(String points) {

		return NumberUtils.isParsable(points);
	}

	public static String capitalizeWord(String str) {
		String words[] = str.split("\\s");
		String capitalizeWord = "";
		for (String w : words) {
			String first = w.substring(0, 1);
			String afterfirst = w.substring(1);
			capitalizeWord += first.toUpperCase() + afterfirst + " ";
		}
		return capitalizeWord.trim();
	}

	public static String generateRandomAlphaString(Integer targetStringLength) {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		Random random = new Random();

		return random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

	}

	public static boolean isDiffMoreThanOneHour(Date startDate,Date endDate) {
		if(startDate==null)
			return true;
		
		Long diff = endDate.getTime() - startDate.getTime();
		Long diffHours = diff / (60 * 60 * 1000);
		return diffHours >= 1l ;
	}


}