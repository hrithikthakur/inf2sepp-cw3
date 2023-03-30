import command.ICommand;
import controller.Context;
import view.IView;

import java.io.IOException;

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
        } catch (IOException e) {
            successResult = false;
        }
    }

    @Override
    public Boolean getResult() {
        return successResult;
    }
}
