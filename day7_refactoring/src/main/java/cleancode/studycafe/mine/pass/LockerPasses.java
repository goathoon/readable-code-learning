package cleancode.studycafe.mine.pass;

import cleancode.studycafe.mine.model.StudyCafeLockerPass;
import cleancode.studycafe.mine.model.StudyCafePass;
import cleancode.studycafe.mine.model.StudyCafePassType;

import java.util.List;
import java.util.Optional;

public class LockerPasses {
    private final List<StudyCafeLockerPass> passes;

    private LockerPasses(List<StudyCafeLockerPass> passes) {
        this.passes = passes;
    }

    public static LockerPasses of(List<StudyCafeLockerPass> passes) {
        return new LockerPasses(passes);
    }

    public Optional<StudyCafeLockerPass> filterMatchingBy(StudyCafePass selectedStudyCafePass) {
        return this.passes.stream()
                .filter(option ->
                        option.getPassType() == selectedStudyCafePass.getPassType()
                        && option.getDuration() == selectedStudyCafePass.getDuration()
                )
                .findFirst();
    }

    public void add(StudyCafeLockerPass lockerPass) {
        this.passes.add(lockerPass);
    }
}
