package cleancode.studycafe.tobewithlecture.model;

import java.util.Set;

public enum StudyCafePassType {

    HOURLY("시간 단위 이용권"),
    WEEKLY("주 단위 이용권"),
    FIXED("1인 고정석");

    private final String description;

    StudyCafePassType(String description) {
        this.description = description;
    }

    // 상수 도입 배경 -> 사물함을 사용할 수 있는 패스타입을 지정하여 cannotUseLocker의 확장성을 추구
    public static final Set<StudyCafePassType> LOCKER_TYPES = Set.of(FIXED);


    public boolean isLockerType() {
        return LOCKER_TYPES.contains(this);
    }

    public boolean isNotLockerType() {
        return !isLockerType();
    }
}
