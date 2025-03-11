package cleancode.studycafe.mine.io.parser;

import cleancode.studycafe.mine.model.StudyCafeLockerPass;
import cleancode.studycafe.mine.model.StudyCafePass;
import cleancode.studycafe.mine.model.StudyCafePassType;

public class CsvParser {
    public static StudyCafePass parseStudyCafePass(String line) {
        String[] values = line.split(",");
        StudyCafePassType studyCafePassType = StudyCafePassType.valueOf(values[0]);
        int duration = Integer.parseInt(values[1]);
        int price = Integer.parseInt(values[2]);
        double discountRate = Double.parseDouble(values[3]);

        return StudyCafePass.of(studyCafePassType, duration, price, discountRate);
    }

    public static StudyCafeLockerPass parseLockerPass(String line) {
        String[] values = line.split(",");
        StudyCafePassType studyCafePassType = StudyCafePassType.valueOf(values[0]);
        int duration = Integer.parseInt(values[1]);
        int price = Integer.parseInt(values[2]);

        return StudyCafeLockerPass.of(studyCafePassType, duration, price);
    }
}
