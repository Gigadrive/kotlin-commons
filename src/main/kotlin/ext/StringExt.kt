package com.gigadrivegroup.kotlincommons.ext

import com.gigadrivegroup.kotlincommons.util.ALPHANUMERIC_CHARACTERS
import java.util.regex.Pattern

public val String.isValidInteger: Boolean
    get() {
        return try {
            Integer.parseInt(this)
            true
        } catch (e: Exception) {
            false
        }
    }

public val String.isAlphaNumeric: Boolean
    get() {
        for (c in toCharArray()) {
            if (!ALPHANUMERIC_CHARACTERS.contains(c.toString())) {
                return false
            }
        }

        return true
    }

public fun String.limitString(limit: Int): String {
    return if (length > limit) {
        substring(0, limit - 1)
    } else {
        this
    }
}

public fun String.containsIgnoreCase(toFilter: String): Boolean {
    return Pattern.compile(Pattern.quote(toFilter), Pattern.CASE_INSENSITIVE).matcher(this).find()
}

public val String.isURL: Boolean
    get() {
        return if (isNotEmpty()) {
            Pattern.compile(
                    StringBuilder()
                        .append("((?:(http|https|Http|Https|rtsp|Rtsp):")
                        .append("\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)")
                        .append(
                            "\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_")
                        .append(
                            "\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?")
                        .append("((?:(?:[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}\\.)+")
                        .append("(?:") // plus top level domain
                        .append("(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])")
                        .append("|(?:biz|b[abdefghijmnorstvwyz])")
                        .append("|(?:cat|com|coop|c[acdfghiklmnoruvxyz])")
                        .append("|d[ejkmoz]")
                        .append("|(?:edu|e[cegrstu])")
                        .append("|f[ijkmor]")
                        .append("|(?:gov|g[abdefghilmnpqrstuwy])")
                        .append("|h[kmnrtu]")
                        .append("|(?:info|int|i[delmnoqrst])")
                        .append("|(?:jobs|j[emop])")
                        .append("|k[eghimnrwyz]")
                        .append("|l[abcikrstuvy]")
                        .append("|(?:mil|mobi|museum|m[acdghklmnopqrstuvwxyz])")
                        .append("|(?:name|net|n[acefgilopruz])")
                        .append("|(?:org|om)")
                        .append("|(?:pro|p[aefghklmnrstwy])")
                        .append("|qa")
                        .append("|r[eouw]")
                        .append("|s[abcdeghijklmnortuvyz]")
                        .append("|(?:tel|travel|t[cdfghjklmnoprtvwz])")
                        .append("|u[agkmsyz]")
                        .append("|v[aceginu]")
                        .append("|w[fs]")
                        .append("|y[etu]")
                        .append("|z[amw]))")
                        .append("|(?:(?:25[0-5]|2[0-4]") // Ip-Adressen (IP addresses)
                        .append("[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]")
                        .append("|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1]")
                        .append(
                            "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}")
                        .append("|[1-9][0-9]|[0-9])))")
                        .append("(?:\\:\\d{1,5})?)") // Port-Nummern (Port numbers)
                        .append("(\\/(?:(?:[a-zA-Z0-9\\;\\/\\?\\:\\@\\&\\=\\#\\~") // Query-Ports
                        .append("\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?")
                        .append("(?:\\b|$)")
                        .toString())
                .matcher(this)
                .find()
        } else {
            false
        }
    }
