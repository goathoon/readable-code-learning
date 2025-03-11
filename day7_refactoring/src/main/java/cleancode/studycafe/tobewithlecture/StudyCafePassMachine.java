package cleancode.studycafe.tobewithlecture;

import cleancode.studycafe.tobewithlecture.exception.AppException;
import cleancode.studycafe.tobewithlecture.io.InputHandler;
import cleancode.studycafe.tobewithlecture.io.OutputHandler;
import cleancode.studycafe.tobewithlecture.io.StudyCafeFileHandler;
import cleancode.studycafe.tobewithlecture.model.StudyCafeLockerPass;
import cleancode.studycafe.tobewithlecture.model.StudyCafePass;
import cleancode.studycafe.tobewithlecture.model.StudyCafePassType;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            StudyCafePass selectedPass = selectPass();
            Optional<StudyCafeLockerPass> optionalLockerPass = selectLockerPass(selectedPass);

            // 이전에는 이러한 로직을 작성하기 싫어서 Optional 파라미터를 그대로 함수에 넣어줬지만.. 분기가 3개가 되는 파라미터가 되므로 지양해야함
            // 바로 optional을 해소하는 방향으로 하자 -> 분기처리가 또필요해짐 -> ifPresentOrElse로 분기
            optionalLockerPass.ifPresentOrElse(
                    lockerPass -> outputHandler.showPassOrderSummary(selectedPass, lockerPass),
                    () -> outputHandler.showPassOrderSummary(selectedPass)
            );


        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafePass selectPass() {
        outputHandler.askPassTypeSelection();
        StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();

        List<StudyCafePass> passCandidates = findPassCandidatesBy(studyCafePassType);

        outputHandler.showPassListForSelection(passCandidates);
        StudyCafePass selectedPass = inputHandler.getSelectPass(passCandidates);
        return selectedPass;
    }

    private List<StudyCafePass> findPassCandidatesBy(StudyCafePassType studyCafePassType) {
        List<StudyCafePass> allPasses = studyCafeFileHandler.readStudyCafePasses();

        return allPasses.stream()
                .filter(studyCafePass -> studyCafePass.isSamePassType(studyCafePassType))
                .toList();
    }

    /**
     * null 리턴은 안티패턴 -> optional로
     */
    private Optional<StudyCafeLockerPass> selectLockerPass(StudyCafePass selectedPass) {
        // 고정 좌석 타입이 아닌가?
        // 사물함 옵션을 사용할 수 있는 타입이 아닌가? 로 생각하면 충분히 메서드명 달리할 수 있음
        if (selectedPass.cannotUserLocker()) {
            return Optional.empty();
        }

        StudyCafeLockerPass lockerPassCandidate = findLockerPassCandidateBy(selectedPass);

        if (lockerPassCandidate != null) {
            outputHandler.askLockerPass(lockerPassCandidate);
            boolean isLockerSelected = inputHandler.getLockerSelection();

            if (isLockerSelected) {
                return Optional.of(lockerPassCandidate);
            }
        }

        return Optional.empty();
    }

    private StudyCafeLockerPass findLockerPassCandidateBy(StudyCafePass pass) {
        List<StudyCafeLockerPass> allLockerPasses = studyCafeFileHandler.readLockerPasses();

        return allLockerPasses.stream()
                .filter(pass::isSameDurationType)
                .findFirst()
                .orElse(null);
    }

}
