package cleancode.studycafe.mine.model;

/**
 * 어차피 Pass는 locker + cafePass 두개의 조합이므로 두개를 합친 VO를 만들었다.
 */
public class StudyCafeOrder {
    private final StudyCafePass studyCafePass;
    private final StudyCafeLockerPass lockerPass;

    private StudyCafeOrder(StudyCafePass studyCafePass, StudyCafeLockerPass lockerPass) {
        this.studyCafePass = studyCafePass;
        this.lockerPass = lockerPass;
    }

    public static StudyCafeOrder of(StudyCafePass studyCafePass, StudyCafeLockerPass lockerPass) {
        return new StudyCafeOrder(studyCafePass, lockerPass);
    }

    public static StudyCafeOrder ofEmptyLockerPass(StudyCafePass studyCafePass) {
        return new StudyCafeOrder(studyCafePass, null);
    }

    public StudyCafePass getStudyCafePass() {
        return studyCafePass;
    }

    public StudyCafeLockerPass getLockerPass() {
        return lockerPass;
    }
}
