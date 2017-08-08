package ru.ncedu.java.tasks;

import java.text.*;
import java.util.*;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class DateCollectionsImpl implements DateCollections {

    private int dateStyle = DateFormat.MEDIUM;;
    private Map<String, Element> map;

    private Comparator<String> comparator = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            try {
                Calendar c1 = toCalendar(o1);
                Calendar c2 = toCalendar(o2);
                return c1.compareTo(c2);
            } catch (ParseException e) {
                return 0;
            }
        }
    };

    public DateCollectionsImpl() {}

    @Override
    public void setDateStyle(int dateStyle) {
        this.dateStyle = dateStyle;
    }

    @Override
    public Calendar toCalendar(String dateString) throws ParseException {
        DateFormat dateFormat = DateFormat.getDateInstance(dateStyle);
        Date date = dateFormat.parse(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    @Override
    public String toString(Calendar date) {
        DateFormat dateFormat = DateFormat.getDateInstance(dateStyle);
        return dateFormat.format(date.getTime());
    }

    @Override
    public void initMainMap(int elementsNumber, Calendar firstDate) {
        map = new TreeMap<String, Element>(comparator);
        Random random = new Random();
        for (int i = 0; i < elementsNumber; i++) {
            int lifetime = random.nextInt(2000);
            Calendar birthDate = Calendar.getInstance();
            birthDate.setTime(firstDate.getTime());
            birthDate.add(Calendar.DAY_OF_MONTH, 110*i);
            map.put(toString(birthDate), new Element(birthDate, lifetime));
        }
    }

    @Override
    public void setMainMap(Map<String, Element> map) {
        this.map = map;
    }

    @Override
    public Map<String, Element> getMainMap() {
        return map;
    }

    @Override
    public SortedMap<String, Element> getSortedSubMap() {
        SortedMap<String, Element> sortedSubMap = new TreeMap<String, Element>(comparator);
        Date currentTime = Calendar.getInstance().getTime();
        for (Map.Entry<String, Element> entry : getMainMap().entrySet()) {
            String key = entry.getKey();
            Element value = entry.getValue();
            if (value.getBirthDate().getTime().compareTo(currentTime) > 0) {
                sortedSubMap.put(key, value);
            }
        }
        return sortedSubMap;
    }

    @Override
    public List<Element> getMainList() {
        List<Element> list = new ArrayList<Element>(getMainMap().values());
        Collections.sort(list, new Comparator<Element>() {
            @Override
            public int compare(Element o1, Element o2) {
                Date d1 = o1.getBirthDate().getTime();
                Date d2 = o2.getBirthDate().getTime();
                return d1.compareTo(d2);
            }
        });
        return list;
    }

    @Override
    public void sortList(List<Element> list) {
        Collections.sort(list, new Comparator<Element>() {
            @Override
            public int compare(Element o1, Element o2) {
                Date d1 = o1.getDeathDate().getTime();
                Date d2 = o2.getDeathDate().getTime();
                return d1.compareTo(d2);
            }
        });
    }

    @Override
    public void removeFromList(List<Element> list) {
        Iterator<Element> iterator = list.iterator();
        List<Element> removeList = new ArrayList<Element>();
        while (iterator.hasNext()){
            Element element = iterator.next();
            int month = element.getBirthDate().get(Calendar.MONTH);
            switch (month) {
                case Calendar.DECEMBER:
                case Calendar.JANUARY:
                case Calendar.FEBRUARY:
                    removeList.add(element);
            }
        }
        for (Element element : removeList)
            list.remove(element);
    }
}
