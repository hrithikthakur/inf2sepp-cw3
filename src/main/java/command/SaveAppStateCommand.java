package command;

import controller.Context;
import view.IView;

import java.io.IOException;

/**
 * verifies that the user is logged in is a staff member
 */

public class SaveAppStateCommand implements ICommand<Boolean>{
    private Boolean successResult;
    private String filename;


    public SaveAppStateCommand(String filename) {
        this.filename = filename;
    }


    @Override
    public void execute(Context context, IView view) {
        try {
            context.saveAppState(filename);
            successResult = true;
        } catch (IOException e) {
            successResult = false;
        }
    }

    @Override
    public Boolean getResult() {
        return successResult;
    }

    private enum LogStatus {
        APP_SAVE_SUCCESS,
        APP_SAVE_FAILURE
    }


}
