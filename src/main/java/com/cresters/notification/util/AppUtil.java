package com.cresters.notification.util;


import com.cresters.notification.domain.response.ErrorModel;
import com.cresters.notification.exception.AccessDeniedException;
import com.cresters.notification.exception.BadRequestException;
import com.cresters.notification.exception.CustomMethodArgumentNotValidException;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.StreamSupport;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author Stephen Obi <stephen@genuinesols.com>
 * @philosophy Quality must be enforced, otherwise it won't happen. We programmers must be required to write tests, otherwise we won't do it!
 * <p>
 * ------
 * Tip: Always code as if the guy who ends up maintaining your code will be a violent psychopath who knows where you live.
 * ------
 * @since 16/02/2021 14:28
 */

@SuppressWarnings("ALL")
@Component
@Slf4j
public class AppUtil {

    public static List<Field> getAllFields(Object obj) {
        List<Field> res = new ArrayList<>(Arrays.asList(obj.getClass().getDeclaredFields()));
        if (obj.getClass().getSuperclass() != null) {
            res.addAll(Arrays.asList(obj.getClass().getSuperclass().getDeclaredFields()));
        }
        return res;
    }

    public static Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> filter = new HashMap<>();

        getAllFields(obj).stream().filter(x -> !x.getName().equals("page") && !x.getName().equals("size")).forEach(o -> {
            try {
                o.setAccessible(true);
                if (o.getType().equals(String.class)) {
                    if (o.get(obj) != null) {
                        filter.put(o.getName(), String.valueOf(o.get(obj)));
                    }
                } else if (o.getType().equals(Long.class)) {
                    if (o.get(obj) != null && (Long) o.get(obj) > 0) {
                        filter.put(o.getName(), (Long) o.get(obj));
                    }
                } else if (o.get(obj) != null && o.getType().equals(Integer.class)) {
                    if ((Integer) o.get(obj) > 0) {
                        filter.put(o.getName(), (Integer) o.get(obj));
                    }
                }
            } catch (IllegalAccessException e) {
                filter.remove(o.getName());
            }
        });
        return filter;
    }

    public static Long defaultLong(Long num) {
        return (num == null ? 0 : num);
    }

    public static File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        String baseRepository = System.getProperty("java.io.tmpdir") + "/";
        File convFile = new File(baseRepository, Objects.requireNonNull(FilenameUtils.getName(multipart.getOriginalFilename())));

        if (convFile.getCanonicalPath().startsWith(baseRepository)) {
            multipart.transferTo(convFile);
            return convFile;
        } else {
            throw new BadRequestException("Invalid file");
        }
    }

    public static String convertToHumanReadableDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM, yyyy");
        Timestamp timestamp = new Timestamp(date.getTime());
        return sdf.format(timestamp);
    }

    public static String convertToHumanReadableDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd, yyyy   hh:mm a");
        Timestamp timestamp = new Timestamp(date.getTime());
        return sdf.format(timestamp);
    }

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Timestamp timestamp = new Timestamp(date.getTime());
        return sdf.format(timestamp);
    }

    public static Date convertFilterDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            throw new BadRequestException("Invalid date parsed");
        }
    }

    public static Date convertToDate(String date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.ENGLISH);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            throw new BadRequestException("Invalid date parsed");
        }
    }

    public static String randomId() {

        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder((100000 + rnd.nextInt(900000)) + "-");
        for (int i = 0; i < 5; i++)
            sb.append(chars[rnd.nextInt(chars.length)]);

        return sb.toString();
    }

    public static String businessTransRef(long businessId) {
        Long timeInSeconds = org.joda.time.DateTime.now().toDate().getTime() / 1000L;
        return ("" + businessId) + randomAlphanumeric(10 - ("" + businessId).length()) + timeInSeconds;
    }

    public static String credoTransRef(long businessId) {

        String[] Xchunks = StreamSupport.stream(Splitter.fixedLength(4).split(randomAlphanumeric(14)).spliterator(), false).toArray(String[]::new);
        String[] Vchunks = StreamSupport.stream(Splitter.fixedLength(2).split(StringUtils.leftPad(("" + businessId), 6, "0")).spliterator(), false).toArray(String[]::new);

        return Xchunks[0] + Vchunks[0] + Xchunks[1] + Vchunks[1] + Xchunks[2] + Vchunks[2] + Xchunks[3];
    }

    public static String randomInt(int n) {
        StringBuilder response = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < n; i++) {
            response.append(secureRandom.nextInt(9));
        }
        return response.toString();
    }

    public static String getCharSet(int length) {
        char[] characterSet = "cLYfKeVBxPsqWnFMrpUiIDJTtXzgjQHhGmdCNwbZoaklRuAvEySO".toCharArray();
        SecureRandom random = new SecureRandom();
        char[] result = new char[length];
        for (int i = 0; i < result.length; i++) {
            int randomCharIndex = random.nextInt(characterSet.length);
            result[i] = characterSet[randomCharIndex];
        }
        return new String(result);
    }

    public static String randomAlphanumeric(int length) {
        char[] characterSet = "Mf06BsX2Q8upcHdJP1SbvCaq63g3Djh59yGYrwoA7t4752mViZ4OW0FKnIR18zTULeN9klxE".toCharArray();

        SecureRandom random = new SecureRandom();
        char[] result = new char[length];
        result[0] = getCharSet(1).charAt(0);
        for (int i = 1; i < result.length; i++) {
            int randomCharIndex = random.nextInt(characterSet.length);
            result[i] = characterSet[randomCharIndex];
        }
        return new String(result);
    }

    public static String generateOtp() {
        return ("" + randomInt(2) + getCharSet(2) + randomInt(1) + getCharSet(1)).toUpperCase();
    }


    public static String capitalize(String str) {
        if (str == null) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase(Locale.ROOT);
    }

    public static String hashSha512(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static long getCRC32Checksum(byte[] bytes) {
        Checksum crc32 = new CRC32();
        crc32.update(bytes, 0, bytes.length);
        return crc32.getValue();
    }

    public static Pair dateTimeRangeFilter(String createdDateFrom, String createdDateTo) {

        if ((isBlank(createdDateFrom) && isBlank(createdDateTo))) {
            return null;
        }

        if (isBlank(createdDateFrom) || isBlank(createdDateTo)) {

            throw new CustomMethodArgumentNotValidException(Collections.singletonList(
                    ErrorModel.builder().messageError("Both createdDateFrom & createdDateTo are required for date range filter!")
                            .fieldName(isBlank(createdDateTo) ? "createdDateTo" : "createdDateFrom")
                            .rejectedValue("").build()));
        }


        if (!createdDateFrom.toString().matches("^(\\d{4})-([0-1]\\d)-([0-3]\\d)\\s([0-1]\\d|[2][0-3]):([0-5]\\d):([0-5]\\d)$")) {
            throw new CustomMethodArgumentNotValidException(Collections.singletonList(
                    ErrorModel.builder().messageError("Invalid date format - yyyy-MM-dd HH:mm:ss")
                            .fieldName("createdDateFrom")
                            .rejectedValue(createdDateFrom).build()));
        }

        if (!createdDateTo.toString().matches("^(\\d{4})-([0-1]\\d)-([0-3]\\d)\\s([0-1]\\d|[2][0-3]):([0-5]\\d):([0-5]\\d)$")) {
            throw new CustomMethodArgumentNotValidException(Collections.singletonList(
                    ErrorModel.builder().messageError("Invalid date format - yyyy-MM-dd HH:mm:ss")
                            .fieldName("createdDateTo")
                            .rejectedValue(createdDateTo).build()));
        }

        try {

            if (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createdDateFrom).after(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createdDateTo))) {
                throw new CustomMethodArgumentNotValidException(Collections.singletonList(
                        ErrorModel.builder().messageError("createdDateFrom must be before createdDateTo")
                                .fieldName("createdDateFrom")
                                .rejectedValue(createdDateFrom).build()));
            }

            return Pair.of(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createdDateFrom), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createdDateTo));
        } catch (ParseException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Map<String, BigDecimal> sortByValues(Map<String, BigDecimal> map) {
        List sortedByValueList = new LinkedList(map.entrySet());
        Collections.sort(sortedByValueList,
                new Comparator<Map.Entry<String, BigDecimal>>() {
                    public int compare(Map.Entry<String, BigDecimal> o1,
                                       Map.Entry<String, BigDecimal> o2) {
                        return ((Comparable) (o2).getValue())
                                .compareTo((o1).getValue());
                    }
                }.reversed());
        Map<String, BigDecimal> sortedHashMap = new LinkedHashMap<String, BigDecimal>();
        for (Iterator it = sortedByValueList.iterator(); it.hasNext(); ) {
            Map.Entry<String, BigDecimal> entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

    public static boolean verifyBusinessCode(String businessCode) {
        AppConstants.getAuthUser().orElseThrow(() -> new AccessDeniedException("Full authentication required to access this resource"));
        return AppConstants.getAuthUser().get().getBusinesses().stream().filter(b -> Objects.equals(b.getBusinessCode(), businessCode)).findFirst().isPresent();
    }
}