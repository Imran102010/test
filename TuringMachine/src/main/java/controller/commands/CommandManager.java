package controller.commands;

import model.TurningMachineException;

import java.util.Stack;

public class CommandManager {
    private final Stack<Command> undoStack = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();

    /**
     * Executes the specified command, pushes it onto the undo stack, and clears the redo stack.
     *
     * @param command The command to be executed.
     */
    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear(); // Quand une nouvelle commande est exécutée, l'historique redo est effacé
    }

    /**
     * Undoes the last executed command by popping it from the undo stack,
     * executing its undo method, and pushing it onto the redo stack.
     *
     * @throws TurningMachineException If the undo stack is empty.
     */
    public void undo() {
        if (undoStack.isEmpty()) {
            throw new TurningMachineException("Undo failed");
        }
        Command command = undoStack.pop();
        command.unexecute();
        redoStack.push(command); // Ajouter la commande dans la pile redo
    }

    /**
     * Redoes the last undone command by popping it from the redo stack,
     * executing its execute method, and pushing it onto the undo stack.
     */
    public void redo() {
        if (redoStack.isEmpty()) {
            throw new TurningMachineException("Redo failed: No commands to redo");
        }

        Command command = redoStack.pop();
        command.execute();
        undoStack.push(command); // Remettre la commande dans la pile undo
    }
}

