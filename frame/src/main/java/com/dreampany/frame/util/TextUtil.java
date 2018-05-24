package com.dreampany.frame.util;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public final class TextUtil {
    private TextUtil() {
    }

    public static String toUpper(CharSequence charSequence) {
        return toLower(trim(charSequence.toString()));
    }

    public static String toUpper(String string) {
        return string.toUpperCase(Locale.getDefault());
    }

    public static String toLower(CharSequence charSequence) {
        return toLower(trim(charSequence.toString()));
    }

    public static String toLower(String string) {
        return toLower(string, Locale.getDefault());
    }

    public static String toLower(String string, Locale locale) {
        return string.toLowerCase(locale);
    }

    private static String trim(String string) {
        return string.trim();
    }

    public static Spanned toHtml(String text) {
        if (Strings.isNullOrEmpty(text)) return null;
        text = removeImg(text);
        return Html.fromHtml(text);
    }

    public static String removeImg(String text) {
        return text.replaceAll("(<(/)img>)|(<img.+?>)", "");
    }

/*    private static void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span) {
        int startUi = strBuilder.getSpanStart(span);
        int end = strBuilder.getSpanEnd(span);
        int flags = strBuilder.getSpanFlags(span);
        ClickableSpan clickable = new ClickableSpan() {
            public void onClick(View view) {
                // Do something with span.getURL() to handle the link click...
                Util.log("Hello");
            }
        };
        strBuilder.setSpan(clickable, startUi, end, flags);
        strBuilder.removeSpan(span);
    }

    public static Spanned toHtml(String text) {
        if (Util.isAbsoluteEmpty(text)) return null;
        // text = removeImg(text);

        CharSequence t = Html.fromHtml(text);
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(t);
        URLSpan[] urls = strBuilder.getSpans(0, t.length(), URLSpan.class);
        for (URLSpan span : urls) {
            makeLinkClickable(strBuilder, span);
        }

        return strBuilder;
    }

    private static String removeImg(String text) {
        return text.replaceAll("(<(/)img>)|(<img.+?>)", "");
    }*/

    public static int countVowel(String text) {
        return text.length() - text.replaceAll("a|e|i|o|u|", "").length();
    }

    public static int countConsonant(String text) {
        return text.length() - countVowel(text);
    }

    public static boolean isAlpha(String text) {
        char[] chars = text.toCharArray();

        for (char c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    public static int getWeight(String name) {

        char[] chars = name.toCharArray();

        int alphaCount = 0;

        for (char c : chars) {
            if (Character.isLetter(c)) {
                alphaCount += 1;
                if (TextUtil.isVowel(c)) {
                    alphaCount += 1;
                }
            }
        }

        return alphaCount;
    }

    public static boolean isVowel(char c) {
        switch(c) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
            case 'A':
            case 'E':
            case 'I':
            case 'O':
            case 'U':
                return true;
        }
        return false;
    }

    public static String toTitleCase(String text) {

        if (text == null) {
            return null;
        }

        boolean space = true;
        StringBuilder builder = new StringBuilder(text);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to setTitle case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }

    public static String getString(Context context, int resourceId) {
        if (context != null && resourceId > 0) {
            return context.getString(resourceId);
        }
        return null;
    }

    public static String getString(Context context, int resourceId, Object... formatArgs) {
        if (context != null && resourceId > 0) {
            return context.getString(resourceId, formatArgs);
        }
        return null;
    }

    public static String[] getStringArray(Context context, int arrayId) {
        if (context != null && arrayId > 0) {
            return context.getResources().getStringArray(arrayId);
        }
        return null;
    }

    public static List<String> getStringList(Context context, int arrayId) {
        return Arrays.asList(context.getResources().getStringArray(arrayId));
    }

    public static String toString(int value) {
        return String.valueOf(value);
    }

    public static String shuffle(String text) {
        List<Character> chars = new ArrayList<>(text.length());

        for (char ch : text.toCharArray()) {
            chars.add(ch);
        }

        Collections.shuffle(chars); //shuffle the list

        StringBuilder sb = new StringBuilder(); //now rebuild the word
        for (char ch : chars)
            sb.append(ch);

        return sb.toString();
    }

    public static String remove(String parent, String child) {
       return parent.replaceAll(child, "");
    }
}