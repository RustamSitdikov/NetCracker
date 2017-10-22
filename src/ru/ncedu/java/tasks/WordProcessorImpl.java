package ru.ncedu.java.tasks;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * ЦЕЛИ ЗАДАЧИ:<br/>
 * - разобраться с чтением символьных данных из файлов;<br/>
 * - научиться выбирать подходящий класс-коллекцию.<br/>
 * <br/>
 * ЗАДАНИЕ<br/>
 * Программа ищет с тексте слова, начинающиеся с заданной последовательности символов
 * без учета регистра ('А' и 'а' - один и тот же символ).<br/>
 * <br/>
 * ТРЕБОВАНИЯ<br/>
 * Словом считается любая последовательность символов, выделенная произвольным
 * количеством пробелов, символов табуляции и переводов строк <br/>
 * (причем переводом строки здесь может быть как line feed, так и carriage return,
 * а в Windows переводом строки считается оба символа: CRLF).<br/>
 * Файл с данными можно прочитать лишь один раз.<br/>
 *
 * @author Andrey Shevtsov
 */
public class WordProcessorImpl implements WordProcessor {

    private String text;

    public WordProcessorImpl(){}

    /**
     * @return текущий текст для работы или <code>null</code>,
     * если ни один из set-методов не был выполнен успешно.
     */
    public String getText() {
        return text;
    }
    /**
     * Принимает текст для работы
     * @param src текст для работы
     * @throws IllegalArgumentException если <code>src == null</code>
     */
    public void setSource(String src) {
        if (src == null) throw new IllegalArgumentException();
        this.text = text;
    }
    /**
     * Считывает текст для работы из указанного файла
     * @param srcFile путь до файла с текстом
     * @throws IOException в случае ошибок при чтении из файла
     * @throws IllegalArgumentException если <code>srcFile == null</code>
     */
    public void setSourceFile(String srcFile) throws IOException {
        if (srcFile == null) throw new IllegalArgumentException();
        try (FileInputStream stream = new FileInputStream(srcFile)) {
            setSource(stream);
        }
    }
    /**
     * Считывает текст для работы из указанного потока ввода
     * @param fis поток ввода
     * @throws IOException в случае ошибок при чтении из потока
     * @throws IllegalArgumentException если <code>fis == null</code>
     */
    public void setSource(FileInputStream fis) throws IOException {
        if (fis == null) throw new IllegalArgumentException();
        StringBuilder text = new StringBuilder();
        while (fis.available() > 0) {
            text.append((char) fis.read());
        }
        setSource(text.toString());
    }
    /**
     * Ищет и возвращает все слова, начинающиеся с указанной последовательности
     * символов без учета регистра. <br/>
     * Если <code>begin</code> - пустая строка или <code>null</code>,
     * то результат содержит все слова, найденные в файле.<br/>
     * Все возвращенные слова должны быть приведены к нижнему регистру.
     * @param begin первые символы искомых слов
     * @return слова, начинающиеся с указанной последовательности символов
     * @throws IllegalStateException если нет текста для работы
     *  (ни один из set-методов не был успешно выполнен)
     */
    public Set<String> wordsStartWith(String begin) {
        if (text == null) throw new IllegalStateException();
        Set<String> textSet = new HashSet<String>();
        if (begin==null) begin = "";
        String[] words = getText().split("\\s+");
        for (String word : words){
            if (word.matches("(?i)" + begin + "\\S*?"))
                textSet.add(word.toLowerCase());
        }
        return textSet;
    }
}
