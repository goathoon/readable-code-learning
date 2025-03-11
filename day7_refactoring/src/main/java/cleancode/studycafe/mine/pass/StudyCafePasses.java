package cleancode.studycafe.mine.pass;

import cleancode.studycafe.mine.model.StudyCafePass;
import cleancode.studycafe.mine.model.StudyCafePassType;

import java.util.ArrayList;
import java.util.List;

/**
 * 일급 컬렉션을 만들어낸 이유는
 * PassType 별로 가공로직을 담은 VO처럼 사용하기 위해서 적용하였다.
 *
 * ex)  studyCafePasses.stream()
 *                     .filter(studyCafePass -> studyCafePass.getPassType() == StudyCafePassType.HOURLY)
 *                     .toList();
 */
public class StudyCafePasses {
    private final List<StudyCafePass> studyCafePasses;

    private StudyCafePasses(List<StudyCafePass> studyCafePasses) {
        this.studyCafePasses = studyCafePasses;
    }

    public static StudyCafePasses of(List<StudyCafePass> studyCafePasses) {
        return new StudyCafePasses(studyCafePasses);
    }

    // 일급 컬렉션 객체를 조정할때는 항상 새로운 일급 컬렉션을 반환하게 하자
    public StudyCafePasses filterBy(StudyCafePassType studyCafePassType) {
        List<StudyCafePass> filteredList = this.studyCafePasses.stream()
                .filter(studyCafePass -> studyCafePass.getPassType() == studyCafePassType)
                .toList();

        return of(filteredList);
    }

    public List<StudyCafePass> getStudyCafePasses() {
        return new ArrayList<>(studyCafePasses);
    }

    // 컬렉션 내부에 있는 원소를 꺼내면, 밖에서 VO의 값을 변경할 수 있겠지만 setter 지양하면 괜찮음
    public StudyCafePass pickOneAt(int index) {
        return studyCafePasses.get(index);
    }

    public void add(StudyCafePass studyCafePass) {
        this.studyCafePasses.add(studyCafePass);
    }
}
