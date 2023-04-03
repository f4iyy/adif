package org.marsik.ham.adif.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.marsik.ham.adif.enums.AdifEnumCode;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Sota implements AdifEnumCode {
    String ituPrefix;
    String subdivision;
    Integer reference;

    @Override
    public String adifCode() {
        return ituPrefix + (subdivision != null ? "/" + subdivision : "") + "-" + String.format("%03d", reference);
    }

    private static Pattern SOTA_RE = Pattern.compile("([A-Z]){1,3}\/([A-Z]{2})-([0-9]{3})?", Pattern.CASE_INSENSITIVE);

    public static Sota valueOf(String s) {
        Matcher m = SOTA_RE.matcher(s);
        if (m.matches()) {
            return new Sota(m.group(1), m.group(2), Integer.parseInt(m.group(3)));
        } else {
            throw new IllegalArgumentException("This is not a SOTA reference");
        }
    }
}
