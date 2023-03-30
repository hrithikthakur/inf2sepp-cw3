package command;

import controller.Context;
import view.IView;

public class LoadAppStateCommand implements ICommand<Boolean> {
    private Boolean successResult;
    private String filename;

    public LoadAppStateCommand(String filename) {
        this.filename = filename;
    }

    @Override
    public void execute(Context context, IView view) {
        try {
            context.loadAppState(filename);
            successResult = true;
        } catch (Exception e) {
            successResult = false;
        }
    }

    @Override
    public Boolean getResult() {
        return successResult;
    }
}
