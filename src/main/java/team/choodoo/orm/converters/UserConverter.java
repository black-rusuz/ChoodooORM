// TODO: Converter
//package team.choodoo.orm.converters;
//
//import com.opencsv.bean.AbstractBeanField;
//import ru.sfedu.goslingdrive.model.bean.User;
//import ru.sfedu.goslingdrive.utils.Constants;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class UserConverter extends AbstractBeanField<User, String> {
//    private static final String fieldsDelimiter = Constants.FIELDS_DELIMITER;
//
//    public static User fromString(String string) {
//        String[] parsed = string.split(fieldsDelimiter);
//        return new User(Long.parseLong(parsed[0]), parsed[1], parsed[2], parsed[3]);
//    }
//
//    public static String toString(Object object) {
//        User user = (User) object;
//        List<Object> params = List.of(user.getId(),
//                user.getFullName(),
//                user.getCar(),
//                user.getAddress());
//        return params.stream().map(Object::toString).collect(Collectors.joining(fieldsDelimiter));
//    }
//
//    @Override
//    public User convert(String string) {
//        return fromString(string);
//    }
//
//    @Override
//    public String convertToWrite(Object object) {
//        return toString(object);
//    }
//}
