package ru.ncedu.java.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckerImpl implements Checker {

    public CheckerImpl(){}

    /**
     * Имена переменных в PL/SQL должны начинаться буквой латинского алфавита.<br/>
     * Промежуточными символами могут быть буквы латинского алфавита, цифры,
     *  знак подчеркивания ("_"), или знак доллара ("$").<br/>
     * Длина имени не должна превышать 30 символов.
     * @return шаблон для имен в PL/SQL.
     */
    public Pattern getPLSQLNamesPattern() {
        return Pattern.compile("[a-zA-Z][a-zA-Z\\d_$]{0,29}");
    }

    /**
     * Содержащиеся на web-странице URLы описываются тегом &lt;a href = ...&gt; (или &lt;a href=.../&gt;).<br/>
     *  Ремарка для начинающих: в HTML &gt; - это > (больше), &lt; - это < (меньше), а комментарии пишутся в таком
     *  "странном" виде, чтобы они корректно отображались в HTML, который из них генерируется через javadoc).<br/>
     *  То есть, следует читать: URLы описываются тегом <a href = ...> (или <a href=.../>).<br/>
     * Будем условно называть URLом закрытый или незакрытый тег a с обязательным атрибутом href,
     *  значение которого не должно содержать пробельных символов (см.ниже). <br/>
     * Заключать значение атрибута href в кавычки необязательно, но если использованы двойные кавычки,
     *  то в значении МОГУТ быть пробельные символы.<br/>
     * Имена тега A и атрибута HREF (как и другие имена в HTML) не чувствительны к регистру.<br/>
     * Между символом меньше, именем тега, названием атрибута, знаком равно и символом больше
     * могут быть следующие пробельные символы:
     * табуляция, перевод строки, возврат каретки, перевод формата, пробел.<br/>
     * @return шаблон для выделения содержащихся на web-странице URL-ов.
     * */
    public Pattern getHrefURLPattern() {
        return Pattern.compile("<[\\t\\r \\n\\f]*[Aa]{1} [\\t\\r \\n\\f]*([hH][rR][eE][fF][\\t\\r \\n\\f]*=\\\\{0,2}[\\t\\r \\n\\f]*\"[a-zA-Z0-9./#@]*\"|[hH][rR][eE][fF][\\t\\r \\n\\f]*=\\\\{0,2}[a-zA-Z0-9./#@]*)[\\t\\r \\n\\f]*>");
    }

    /**
     * e-mail имеет формат: <аккаунт>@<домен>.<домен_первого_уровня><br/>
     * <Аккаунт> должен быть длиной не более 22 символов и состоять из символов:
     *  латинские буквы, цифры, знак подчеркивания ("_"), точка ("."), дефис ("-").<br/>
     * Аккаунт не может начинаться с символов дефис ("-"), точка (".") или знак подчеркивания ("_").<br/>
     * Аккаунт не может заканчиваться символом дефис ("-"), точка (".") или знак подчеркивания ("_"). <br/>
     * <Домен> может быть доменом любого уровня, каждый уровень отделяется от другого символом точка (".").
     * Название домена каждого уровня должно состоять более чем из одного символа,
     * начинаться и заканчиваться буквой латинского алфавита или цифрой. <br/>
     * Промежуточными символами могут быть буквы латинского алфавита, цифры или дефис.<br/>
     * <Домен первого уровня> - допустим один из следующих: .ru, .com, .net, .org.
     * @return шаблон для e-mail адресов.
     * */
    public Pattern getEMailPattern() {
        return Pattern.compile("^([a-zA-Z0-9][a-zA-Z0-9_.\\-]{0,20}[a-zA-Z0-9]|[a-zA-Z0-9])[@]([a-zA-Z0-9]{1}[a-zA-Z0-9\\-]*[a-zA-Z0-9]{1}\\.)+(ru|com|net|org)$");
    }

    /**
     * Метод проверяет соответствие inputString шаблону pattern.
     * @param inputString строка, подлежащая проверке.
     * @param pattern шаблон.
     * @throws IllegalArgumentException если только один из аргументов равен null.
     * @return true, если inputString соответствует шаблону pattern ИЛИ inputString и pattern являются null-ами.
     * */
    public boolean checkAccordance(String inputString, Pattern pattern) throws IllegalArgumentException {
        if (inputString == null && pattern == null) return true;
        if((inputString == null) || (pattern == null)) throw new IllegalArgumentException();

        Matcher matcher = pattern.matcher(inputString);
        return matcher.matches();
    }

    /**
     * Метод возвращает список всех соответствий в inputString шаблону pattern.
     * @param inputString строка для поиска
     * @param pattern шаблон поиска.
     * @throws IllegalArgumentException если хотя бы один из аргументов равен null.
     * @return Список всех соответствий, или пустой список, если соответствий нет.
     * */
    public List<String> fetchAllTemplates(StringBuffer inputString, Pattern pattern) throws IllegalArgumentException {
        if (inputString == null || pattern == null) throw new IllegalArgumentException();

        List<String> templates = new ArrayList<>();
        Matcher matcher = pattern.matcher(inputString);
        while (matcher.find()) {
            templates.add(matcher.group(0));
        }
        return templates;
    }
}
