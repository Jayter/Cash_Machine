package atm.command;

import atm.exception.InterruptOperationException;

/**
 * Created by Nicki on 19.11.2015.
 */
/**
 * The base interface for all classes-commands.
 */
interface Command
{
    /**
     * Executes the main logic of this class-command.
     * <p>
     * The method is encapsulated and invoked only by {@link CommandExecutor}.
     * @throws InterruptOperationException
     */
    void execute() throws InterruptOperationException;
}
