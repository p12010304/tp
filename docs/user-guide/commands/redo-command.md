# Redoing a command: `redo`

Reverses the effect of an `undo` command, effectively re-applying the previously undone action.

Format: `redo`

* Only applicable after an `undo` command has been executed.
* Commands that do not modify data (`help`, `view`, `close view`, `list`, `find`, `undo`, `redo`, `exit`) are ignored by redo.
* Displays the feedback of the redone command.

Examples:
* `delete 1` then `undo` then `redo` re-deletes the 1st contact.
* `edit 1 n/New Name` then `undo` then `redo` re-applies the name change.

![redo command]({{ baseUrl }}/images/redoCommand.png)
