package client.commands.utility;

import java.util.Arrays;

import client.commands.utility.exceptions.ArgumentEnumException;
import client.commands.utility.exceptions.ArgumentNumberException;


public class ArgHandler {

    public static boolean checkArgForInt(String arg, int min, int max) throws ArgumentNumberException{
        try {
            int in = Integer.parseInt(arg);
            if (in <= min || in > max) {
                throw new ArgumentNumberException(min, max);
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            throw new ArgumentNumberException();     
        }

    }

    public static boolean checkArgForInt(String arg) throws ArgumentNumberException{
        return checkArgForInt(arg, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static <E extends Enum<E>> boolean checkArgForEnumString(String arg, E[] enums) throws ArgumentEnumException{
        String joinedEnums = Arrays.toString(enums);
        boolean isInEnums = false;
        for (E enu : enums) {
            if (arg.equalsIgnoreCase(enu.name()) || arg.equals(Integer.toString(enu.ordinal() + 1))) {
                return true;
            }
        }
        if (isInEnums == false){
            throw new ArgumentEnumException(joinedEnums);
        }
        return true;
    }

    public static boolean checkArgForFloat(String arg, float min, float max) throws ArgumentNumberException{
        try {
            float in = Float.parseFloat(arg);

            if (in <= min || in > max) {
                throw new ArgumentNumberException(min, max);
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            throw new ArgumentNumberException();
            
        }
    }

    public static boolean checkArgForFloat(String arg) throws ArgumentNumberException{
        return checkArgForFloat(arg, -Float.MAX_VALUE, Float.MAX_VALUE);
    }


} 
