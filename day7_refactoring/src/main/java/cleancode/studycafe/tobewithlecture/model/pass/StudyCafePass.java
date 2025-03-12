package cleancode.studycafe.tobewithlecture.model.pass;

public interface StudyCafePass {
    StudyCafePassType getPassType();

    int getDuration();

    int getPrice();
}
