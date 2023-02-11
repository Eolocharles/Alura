import anotations.MinimalAge;
import entities.User;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.Period;

public class Main {
    public static void main(String[] args) {
        User user = new User("Mário", "42198284863", LocalDate.of(2005, 3, 14));
        System.out.println(userValidator(user));
    }

    private static <T> boolean userValidator(T object) {
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()){
            if (field.isAnnotationPresent(MinimalAge.class)){
                MinimalAge minimalAge = field.getAnnotation(MinimalAge.class);
                try {
                    field.setAccessible(true);
                    LocalDate birthDate = (LocalDate) field.get(object);
                    return Period.between(birthDate, LocalDate.now()).getYears() >= minimalAge.value();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}