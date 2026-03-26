# Undoing a command: `undo`

Reverts the last executed command that modified data.

Format: `undo`

* Only commands that modify data can be undone (e.g. `add`, `edit`, `delete`, `note`, `clear`, `sort`).
* Commands that do not modify data (`help`, `view`, `close view`, `list`, `find`, `undo`, `redo`, `exit`) are ignored by undo.
* Displays the feedback of the undone command.

Examples:
* `delete 1` followed by `undo` restores the deleted contact.
* `edit 1 n/New Name` followed by `undo` reverts the name change.

![undo command]({{ baseUrl }}/images/undoCommand.png)
