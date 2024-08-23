package cyou;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommonService {

    private final DateTimeAgeService dateTimeAgeService;
    public Integer numberAutoGen(String lastNumber) {
        int newNumber;
        int previousNumber;
        int newNumberFirstTwoCharacters;

        int currentYearLastTwoNumber = Integer
                .parseInt(String.valueOf(dateTimeAgeService.getCurrentDate().getYear()).substring(2, 4));

        if (lastNumber != null) {
            previousNumber = Integer.parseInt(lastNumber.substring(0, 9));
            newNumberFirstTwoCharacters = Integer.parseInt(lastNumber.substring(0, 2));

            if (currentYearLastTwoNumber == newNumberFirstTwoCharacters) {
                newNumber = previousNumber + 1;
            } else {
                newNumber = Integer.parseInt(currentYearLastTwoNumber + "0000000");
            }

        } else {
            newNumber = Integer.parseInt(currentYearLastTwoNumber + "0000000");
        }
        return newNumber;
    }

    public String phoneNumberLengthValidator(String number) {
        if (number.length() == 9) {
            number = "0".concat(number);
        }
        return number;
    }

    public void printAttributesInObject(Object obj) {
        System.out.println("================================================");
        Class<?> objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            Object value;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                value = null;
            }
            log.info("{} = {}", name, value);
        }
        System.out.println("================================================");

    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }

    public boolean urlValidate(String urlString) {
        try {
            // create CustomAuditListener URL object
            URL url = new URL(urlString);

            // check if the URL is valid
            url.toURI();

            return true;

        } catch (MalformedURLException | IllegalArgumentException | URISyntaxException e) {
            // catch the exceptions that may occur if the URL is not valid
            return false;
        }
    }

    public String generateUniqCode(int length) {
        // Define the length of the random string
        if (length < 0) {
            length = 6;
        }
        // Define the characters to use in the random string
        String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // Generate CustomAuditListener random string of the specified length
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }

    public String url_http_to_https(HttpServletRequest request) throws MalformedURLException {
        String requestUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        String convertedUrl = "";
        // todo : need to change this http to https before push the server
        URL originalUrl = new URL(requestUrl);
        URL httpsUrl = new URL("https", originalUrl.getHost(), originalUrl.getPort(), originalUrl.getFile());

        convertedUrl = httpsUrl.toString();

        return convertedUrl;
    }

    public String firstLaterCapital(String name) {
        char firstChar = name.charAt(0);
        firstChar = Character.toUpperCase(firstChar);
        name = firstChar + name.substring(1);
        return name;
    }

    public Object getValueFromCookies(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            // Loop through the cookies to find the one you're interested in
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    // Found the cookie, you can access its value
                    return cookie.getValue();
                }
            }
        }
        return "";
    }

    public String getCurrentURL(HttpServletRequest request) {
        return ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .build()
                .toUriString();
    }

    public String stringEnglishFirstLatterCapital(String input) {
        String[] words = input.split(" ");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
            result.append(capitalizedWord);
            if (i < words.length - 1) {
                result.append("_");
            }
        }
        return result.toString();
    }
}
