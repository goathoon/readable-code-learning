package cleancode.studycafe.tobewithlecture;

import cleancode.studycafe.tobewithlecture.io.provider.LockerPassFileReader;
import cleancode.studycafe.tobewithlecture.io.provider.SeatPassFileReader;
import cleancode.studycafe.tobewithlecture.provider.LockerPassProvider;
import cleancode.studycafe.tobewithlecture.provider.SeatPassProvider;

public class StudyCafeApplication {

    public static void main(String[] args) {
        SeatPassProvider seatPassProvider = new SeatPassFileReader();
        LockerPassProvider lockerPassProvider = new LockerPassFileReader();

        StudyCafePassMachine studyCafePassMachine = new StudyCafePassMachine(
                seatPassProvider, lockerPassProvider
        );
        studyCafePassMachine.run();
    }

}
