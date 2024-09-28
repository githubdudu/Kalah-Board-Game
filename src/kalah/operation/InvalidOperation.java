package kalah.operation;

import kalah.GameSetting;

public class InvalidOperation implements Operation {

    @Override
    public void execute() {
        System.out.println(GameSetting.INVALID_OPERATION_MESSAGE);
    }
}
