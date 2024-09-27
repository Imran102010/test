package controller.commands;

public interface Command {
    /**
     * Executes the specified command
     */
    void execute();
    /**
     * Undoes the specified command
     */
    void unexecute();
}
