### Adding notes/reminders to a contact: `note`

Manages notes and reminders for an existing contact in the contact list.

**Add a note:**

Format: `note INDEX NOTE [on/TIME]`

* Appends `NOTE` to the contact at the specified `INDEX`. The index **must be a positive integer** 1, 2, 3, …​
* New notes are stacked underneath existing ones.
* `TIME` can accept most conventional date/time formats and may omit the year. If unable to parse as a date, it will be saved as a plain string.
* Filling the `on/TIME` field turns the note into a reminder. The system will warn of reminders due within 1 week.
* Notes support **contact references** using the `@INDEX` syntax. When you include `@INDEX` in a note, it creates a link to the contact at that index. The reference is displayed as the contact's name in **bold and underlined** text.
* If a referenced contact's name changes, the displayed name updates automatically.
* If a referenced contact is deleted, the reference is replaced with the contact's name as plain text.

Examples:
* `note 1 Likes to swim.`
* `note 2 Follow up call on/15 Apr`
* `note 1 Worked with @2` — creates a reference to the 2nd contact in the note.

![add note](/images/addNote.png)

**Remove the first N notes:**

Format: `note INDEX c/LINES_TO_REMOVE`

* Removes the first `LINES_TO_REMOVE` notes from the contact at the specified `INDEX`.
* `LINES_TO_REMOVE` must be a non-negative integer.
* If `LINES_TO_REMOVE` exceeds the number of existing notes, all notes are removed.

Examples:
* `note 1 c/1` removes the first note from the 1st contact.
* `note 2 c/3` removes the first 3 notes from the 2nd contact.

![remove note](/images/removeNote.png)

**Clear all notes:**

Format: `note INDEX ca/`

* Removes all notes from the contact at the specified `INDEX`.

Example:
* `note 1 ca/`
