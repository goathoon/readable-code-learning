package cleancode.studycafe.mine;

import cleancode.studycafe.mine.exception.AppException;
import cleancode.studycafe.mine.io.InputHandler;
import cleancode.studycafe.mine.io.OutputHandler;
import cleancode.studycafe.mine.io.StudyCafeFileHandler;
import cleancode.studycafe.mine.model.StudyCafeLockerPass;
import cleancode.studycafe.mine.model.StudyCafeOrder;
import cleancode.studycafe.mine.model.StudyCafePass;
import cleancode.studycafe.mine.model.StudyCafePassType;
import cleancode.studycafe.mine.pass.LockerPasses;
import cleancode.studycafe.mine.pass.StudyCafePasses;

import java.util.Optional;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            StudyCafePass selectedPass = getUserSelectedStudyCafePass();
            Optional<StudyCafeLockerPass> optionalStudyCafeLockerPass = getUserSelectedStudyCafeLockerPassBy(selectedPass);

            StudyCafeOrder order = makeAnOrder(optionalStudyCafeLockerPass, selectedPass);
            outputHandler.showPassOrderSummary(order);

        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private Optional<StudyCafeLockerPass> getUserSelectedStudyCafeLockerPassBy(StudyCafePass selectedPass) {
        LockerPasses lockerPasses = studyCafeFileHandler.readLockerPasses();

        Optional<StudyCafeLockerPass> filteredLockerPass = lockerPasses.filterMatchingBy(selectedPass);
        if(filteredLockerPass.isEmpty()){
            return Optional.empty();
        }

        StudyCafeLockerPass lockerPass = filteredLockerPass.get();
        outputHandler.askLockerPass(lockerPass);

        if(doesUserSelectLocker(lockerPass)){
            return Optional.empty();
        }
        return Optional.of(lockerPass);
    }

    private StudyCafePass getUserSelectedStudyCafePass() {
        /**
         * 아래에 ask하고 get하는걸 메서드 추출하는게 좋을까 고민하다가 그냥 냅뒀다.. outputHandler, inputHandler가 명백하게 드러나야 좋지않을까?
         */
        outputHandler.askPassTypeSelection();
        StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();

        // fileHandler에서 parse까지 하는 역할은 과할까?
        StudyCafePasses allStudyCafePasses = studyCafeFileHandler.readStudyCafePasses();
        StudyCafePasses filteredStudyCafePasses = allStudyCafePasses.filterBy(studyCafePassType);

        outputHandler.showPassListForSelection(filteredStudyCafePasses);
        return inputHandler.getSelectPass(filteredStudyCafePasses);
    }

    private boolean doesUserSelectLocker(StudyCafeLockerPass lockerPass) {
        return inputHandler.getLockerSelection();
    }


    private StudyCafeOrder makeAnOrder(Optional<StudyCafeLockerPass> optionalStudyCafeLockerPass, StudyCafePass selectedPass) {
        return optionalStudyCafeLockerPass
                .map(studyCafeLockerPass -> StudyCafeOrder.of(selectedPass, studyCafeLockerPass))
                .orElseGet(() -> StudyCafeOrder.ofEmptyLockerPass(selectedPass));
    }

}
