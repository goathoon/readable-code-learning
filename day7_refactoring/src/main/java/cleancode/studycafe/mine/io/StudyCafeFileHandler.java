package cleancode.studycafe.mine.io;

import cleancode.studycafe.mine.io.parser.CsvParser;
import cleancode.studycafe.mine.model.StudyCafeLockerPass;
import cleancode.studycafe.mine.model.StudyCafePass;
import cleancode.studycafe.mine.model.StudyCafePassType;
import cleancode.studycafe.mine.pass.LockerPasses;
import cleancode.studycafe.mine.pass.StudyCafePasses;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class StudyCafeFileHandler {

    public StudyCafePasses readStudyCafePasses() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/cleancode/cleancode.studycafe/pass-list.csv"));
            StudyCafePasses studyCafePasses = StudyCafePasses.of(new ArrayList<>());

            for (String line : lines) {
                StudyCafePass studyCafePass = CsvParser.parseStudyCafePass(line);
                studyCafePasses.add(studyCafePass);
            }

            return studyCafePasses;
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
    }


    public LockerPasses readLockerPasses() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/cleancode/cleancode.studycafe/locker.csv"));
            LockerPasses lockerPasses = LockerPasses.of(new ArrayList<>());
            for (String line : lines) {
                StudyCafeLockerPass lockerPass = CsvParser.parseLockerPass(line);
                lockerPasses.add(lockerPass);
            }

            return lockerPasses;
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
    }

}
