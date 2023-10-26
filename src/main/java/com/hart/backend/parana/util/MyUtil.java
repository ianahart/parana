package com.hart.backend.parana.util;

import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

public final class MyUtil {

    private MyUtil() {

    }

    public static String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();

    }

    public static boolean validatePassword(String str) {
        boolean isUpperCase = false;
        boolean isLowerCase = false;
        boolean isDigit = false;
        boolean isSpecialChar = false;

        for (char ch : str.toCharArray()) {
            if (Character.isLetter(ch) && Character.isUpperCase(ch)) {
                isUpperCase = true;
            }
            if (Character.isLetter(ch) && Character.isLowerCase(ch)) {
                isLowerCase = true;
            }
            if (Character.isDigit(ch)) {
                isDigit = true;
            }
            if (!Character.isWhitespace(ch) && !Character.isDigit(ch) && !Character.isLetter(ch)) {
                isSpecialChar = true;
            }
        }
        List<Boolean> cases = List.of(isUpperCase, isLowerCase, isDigit, isSpecialChar);
        return cases.stream().allMatch(c -> c);
    }

    public static int paginate(int page, String direction) {
        int currentPage = page;

        if (direction.equals("next")) {
            currentPage = currentPage + 1;
        }

        if (direction.equals("prev") && page > 0) {
            currentPage = currentPage - 1;
        }

        return currentPage;
    }

    public static String slugify(String text) {
        String[] str = text.replaceAll("[^a-zA-Z]+", " ").toLowerCase().split(" ");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
            if (i > 0) {
                list.add("-" + str[i]);
            } else {
                list.add(str[i]);
            }
        }
        return String.join("", list);
    }

    public static String constructReadableDate(Timestamp timestamp) {
        long createdAtInSeconds = (timestamp.getTime() / 1000L);
        long nowInSeconds = Instant.now().getEpochSecond();
        long elapsedInSeconds = nowInSeconds - createdAtInSeconds;
        String readableDate = "";

        if (elapsedInSeconds < 60) {

            readableDate = String.format("%d seconds ago", elapsedInSeconds);
        } else if (elapsedInSeconds > 60 && elapsedInSeconds < 60 * 60) {

            int minutes = Math.round(elapsedInSeconds / 60);
            readableDate = String.format("%d minutes ago", minutes);
        } else if (elapsedInSeconds >= 60 * 60 && elapsedInSeconds < 60 * 60 * 24) {

            int hours = Math.round(elapsedInSeconds / (60 * 60));
            readableDate = String.format("%d hours ago", hours);
        } else if (elapsedInSeconds >= 60 * 60 * 24 && elapsedInSeconds < 60 * 60 * 24 * 7) {

            int days = Math.round(elapsedInSeconds / (60 * 60 * 24));
            readableDate = String.format("%d days ago", days);
        } else if (elapsedInSeconds >= 60 * 60 * 24 * 7 && elapsedInSeconds < 60 * 60 * 24 * 30) {

            int weeks = Math.round(elapsedInSeconds / (60 * 60 * 24 * 7));
            readableDate = String.format("%d weeks ago", weeks);
        }

        return readableDate;

    }

    public static String createDate(Timestamp createdAt) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = formatter.format(createdAt);

        return strDate;
    }

}
