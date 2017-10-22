package ru.ncedu.java.tasks;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class ReflectionsImpl implements Reflections {

    /**
     * Метод возвращает текущее значение поля для данного экземпляра,
     * имеющего идентификатор private, public, protected или default.
     * @param object экземпляр класса
     * @param fieldName имя поля класса
     * @throws NoSuchFieldException если поля с указанным именем не существует
     * @throws NullPointerException если fieldName or object является null-ом
     * @return Текущее значение поля
     * */
    public Object getFieldValueByName(Object object, String fieldName) throws NoSuchFieldException {
        if (object == null || fieldName == null) throw new NullPointerException();
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);

        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new NoSuchFieldException();
        }
    }

    /**
     * Метод возвращает набор имен методов для класса, помеченных идентификатором protected
     * @param clazz класс
     * @throws NullPointerException если clazz является null-ом
     * @return Набор имен методов
     * */
    public Set<String> getProtectedMethodNames(Class clazz) {
        if (clazz == null) throw new NullPointerException();
        Set<String> protectedMethodsNames = new HashSet<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (Modifier.isProtected(method.getModifiers())) {
                protectedMethodsNames.add(method.getName());
            }
        }
        return protectedMethodsNames;
    }

    /**
     * Метод возвращает набор всех методов класса, в т.ч. методов его суперклассов.
     * Возвращаемые методы могут иметь любые модификаторы.
     * Если в иерархии есть переопределенные методы, должны возвращаться все они
     *   (а не только метод, переопределяющий остальные).<br/>
     * @param clazz класс
     * @throws NullPointerException если clazz является null-ом
     * @return Набор методов для всей иерархии классов.
     * */
    public Set<Method> getAllImplementedMethodsWithSupers(Class clazz) {
        if (clazz == null) throw new NullPointerException();
        Set<Method> implementedMethods = new HashSet<>();
        while (clazz != null) {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            implementedMethods.addAll(Arrays.asList(declaredMethods));
            clazz = clazz.getSuperclass();
        }
        return implementedMethods;
    }

    /**
     * Метод возвращает список классов, являющихся суперклассами для указанного класса.
     * @param clazz класс
     * @throws NullPointerException если clazz является null-ом
     * @return Набор классов всей иерархии.
     * */
    public List<Class> getExtendsHierarchy(Class clazz) {
        if (clazz == null) throw new NullPointerException();
        List<Class> classes = new LinkedList<>();
        while (clazz.getSuperclass() != null) {
            clazz = clazz.getSuperclass();
            classes.add(clazz);
        }
        return classes;
    }

    /**
     * Метод возвращает список интерфейсов, которые реализует класс/интерфейс clazz
     * @param clazz - класс/интерфейс
     * @throws NullPointerException - если clazz является null-ом
     * @return Набор интерфейсов
     * */
    public Set<Class> getImplementedInterfaces(Class clazz) {
        if (clazz == null) throw new NullPointerException();
        Set<Class> interfaces = new HashSet<>();
        interfaces.addAll(Arrays.asList(clazz.getInterfaces()));
        return interfaces;
    }

    /**
     * Метод возвращает список исключений, которые может порождать метод
     * @param method метод
     * @return Список порождаемых исключений
     * @throws NullPointerException если method является null-ом
     * */
    public List<Class> getThrownExceptions(Method method) {
        if (method == null) throw new NullPointerException();
        List<Class> exceptions = new ArrayList<>();
        exceptions.addAll(Arrays.asList(method.getExceptionTypes()));
        return exceptions;
    }

    /**
     * Метод создает экземпляр класса SecretClass с помощью конструктора по умолчанию,
     * после чего вызвает его метод foo()
     * @return результат, который возвращает метод foo()
     * */
    public String getFooFunctionResultForDefaultConstructedClass()  {
        try {
            Constructor<SecretClass> constructor = SecretClass.class.getDeclaredConstructor();
            constructor.setAccessible(true);

            Method foo = SecretClass.class.getDeclaredMethod("foo");
            foo.setAccessible(true);

            SecretClass secretObject = constructor.newInstance();
            return (String)foo.invoke(secretObject);
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Метод создает экземпляр класса SecretClass с помощью конструктора с параметром constructorParameter
     * после чего вызвает его метод foo(...), в который передается тот же самый набор аргументов, что получила функция
     * @param constructorParameter параметр, передаваемый конструктору
     * @param string первый аргумент для функции foo
     * @param integers последующие аргументы для функции foo
     * @return результат, который возвращает метод foo(...)
     * */
    public String getFooFunctionResultForClass(String constructorParameter, String string, Integer ... integers) {
        try {
            Constructor<SecretClass> constructor = SecretClass.class.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);

            Method foo = SecretClass.class.getDeclaredMethod("foo", String.class, Integer[].class);
            foo.setAccessible(true);

            SecretClass secretObject = constructor.newInstance(constructorParameter);
            return (String)foo.invoke(secretObject, string, integers);
        }
        catch (Exception e) {
            return null;
        }
    }

}