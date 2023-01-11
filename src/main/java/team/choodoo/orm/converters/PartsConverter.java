// TODO: Converter
//package team.choodoo.orm.converters;
//
//import com.opencsv.bean.AbstractBeanField;
//import ru.sfedu.goslingdrive.model.bean.AutoPart;
//import ru.sfedu.goslingdrive.model.bean.BodyPart;
//import ru.sfedu.goslingdrive.model.bean.ElectricPart;
//import ru.sfedu.goslingdrive.model.bean.RunningPart;
//import ru.sfedu.goslingdrive.utils.Constants;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class PartsConverter extends AbstractBeanField<List<AutoPart>, String> {
//    private static final String fieldsDelimiter = Constants.FIELDS_DELIMITER;
//    private static final String beansDelimiter = Constants.BEANS_DELIMITER;
//
//    private static AutoPart stringToBean(String string) {
//        String[] parsed = string.split(fieldsDelimiter);
//        return switch (parsed.length) {
//            case (6) -> {
//                try {
//                    ElectricPart bean = new ElectricPart();
//                    bean.setId(Long.parseLong(parsed[0]));
//                    bean.setName(parsed[1]);
//                    bean.setPrice(Double.parseDouble(parsed[2]));
//                    bean.setVinPart(parsed[3]);
//                    bean.setWarranty(Integer.parseInt(parsed[4]));
//                    bean.setWattage(Double.parseDouble(parsed[5]));
//                    yield bean;
//                } catch (Exception e) {
//                    RunningPart bean = new RunningPart();
//                    bean.setId(Long.parseLong(parsed[0]));
//                    bean.setName(parsed[1]);
//                    bean.setPrice(Double.parseDouble(parsed[2]));
//                    bean.setVinPart(parsed[3]);
//                    bean.setWarranty(Integer.parseInt(parsed[4]));
//                    bean.setIsCustom(Boolean.parseBoolean(parsed[5]));
//                    yield bean;
//                }
//            }
//            case (7) -> {
//                BodyPart bean = new BodyPart();
//                bean.setId(Long.parseLong(parsed[0]));
//                bean.setName(parsed[1]);
//                bean.setPrice(Double.parseDouble(parsed[2]));
//                bean.setVinPart(parsed[3]);
//                bean.setWarranty(Integer.parseInt(parsed[4]));
//                bean.setColor(parsed[5]);
//                bean.setSide(parsed[6]);
//                yield bean;
//            }
//            default -> new AutoPart() {
//            };
//        };
//    }
//
//    public static List<AutoPart> fromString(String string) {
//        List<String> beans = List.of(string.split(beansDelimiter));
//        return beans.stream().map(PartsConverter::stringToBean).toList();
//    }
//
//    private static String beanToString(AutoPart autoPart) {
//        List<Object> params = new ArrayList<>(List.of(autoPart.getId(), autoPart.getName(), autoPart.getPrice(), autoPart.getVinPart(), autoPart.getWarranty()));
//
//        if (autoPart instanceof BodyPart bodyPart) {
//            params.add(bodyPart.getColor());
//            params.add(bodyPart.getSide());
//        } else if (autoPart instanceof ElectricPart electricPart) {
//            params.add(electricPart.getWattage());
//        } else if (autoPart instanceof RunningPart runningPart) {
//            params.add(runningPart.getIsCustom());
//        }
//
//        return params.stream().map(Object::toString).collect(Collectors.joining(fieldsDelimiter));
//    }
//
//    public static String toString(Object object) {
//        List<AutoPart> autoParts = (List<AutoPart>) object;
//        return autoParts.stream().map(PartsConverter::beanToString).collect(Collectors.joining(beansDelimiter));
//    }
//
//    @Override
//    public List<AutoPart> convert(String string) {
//        return fromString(string);
//    }
//
//    @Override
//    public String convertToWrite(Object object) {
//        return toString(object);
//    }
//}
