package org.example;

public class StringToConvertionTypeMapper {
    public ConvertionType stringToConvertionType(String string) {
        if(string.equals("assignment")){
            return ConvertionType.ASSIGNMENT;
        } else if (string.equals("solution")) {
            return ConvertionType.SOLUTION;
        }else{
            throw new IllegalArgumentException("Unrecognized convertion type: " + string);
        }
    }
}
