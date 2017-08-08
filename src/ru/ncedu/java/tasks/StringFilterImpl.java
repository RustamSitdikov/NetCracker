package ru.ncedu.java.tasks;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class StringFilterImpl implements StringFilter {
    
    private Collection<String> collection = new HashSet<>();

    private interface Filter {
        boolean check(String string, String pattern);
    }
    
    public StringFilterImpl(){}

    private Iterator<String> getIterator(String pattern, Filter filter){
        if (pattern == null || pattern.isEmpty()) {
            return getCollection().iterator();
        }
        Collection<String> collection = new HashSet<>();
        for (String s : getCollection()) {
            if (filter.check(s, pattern.toLowerCase())) {
                collection.add(s);
            }
        }
        return collection.iterator();
    }

    @Override
    public void add(String s) {
        collection.add(s == null ? null : s.toLowerCase());
    }

    @Override
    public boolean remove(String s) {
        return collection.remove(s == null ? null : s.toLowerCase());
    }

    @Override
    public void removeAll() {
        collection.clear();
    }

    @Override
    public Collection<String> getCollection() {
        return collection;
    }

    @Override
    public Iterator<String> getStringsContaining(String chars) {
        Filter filter = new Filter() {
            @Override
            public boolean check(String string, String pattern) {
                return string != null && string.contains(pattern);
            }
        };
        return getIterator(chars, filter);
    }

    @Override
    public Iterator<String> getStringsStartingWith(String begin) {
        Filter filter = new Filter() {
            @Override
            public boolean check(String string, String pattern) {
                return string != null && string.startsWith(pattern);
            }
        };
        return getIterator(begin, filter);
    }

    @Override
    public Iterator<String> getStringsByNumberFormat(String format) {
        Filter filter = new Filter() {
            @Override
            public boolean check(String string, String pattern) {
                if (string == null) {
                    return false;
                }
                if (string.length() != pattern.length()) {
                    return false;
                }
                for (int i = 0; i < string.length(); i++){
                    if (pattern.charAt(i) == '#') {
                        if (!Character.isDigit(string.charAt(i))) {
                            return false;
                        }
                    } else if (pattern.charAt(i) != string.charAt(i)) {
                        return false;
                    }
                }
                return true;
            }
        };
        return getIterator(format, filter);
    }

    @Override
    public Iterator<String> getStringsByPattern(String pattern) {
        Filter filter = new Filter() {
            @Override
            public boolean check(String string, String pattern) {
                if (string == null) {
                    return false;
                }
                String tmpPattern = pattern;
                String tmpString = string;
                int index;
                boolean first = false;
                while ((index = tmpPattern.indexOf("*")) != -1) {
                    if (index == 0) {
                        tmpPattern = tmpPattern.substring(1);
                        first = true;
                        continue;
                    }
                    int tmpIndex = tmpString
                            .indexOf(tmpPattern.substring(0, index));
                    if (tmpIndex == -1
                            || (!first && tmpIndex != 0)) {
                        return false;
                    }
                    tmpString = tmpString.substring(tmpIndex + index);
                    tmpPattern = tmpPattern.substring(index);
                    first = false;
                }
                return (first && new StringBuilder(tmpString).reverse().toString()
                        .startsWith(new StringBuilder(tmpPattern).reverse().toString()))
                        || tmpString.equals(tmpPattern);
            }
        };
        return getIterator(pattern, filter);
    }
}
