# Command summary

| Action             | Format, Examples                                                                                                                                                                                |
| ------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Help**           | `help`                                                                                                                                                                                          |
| **Add contact**    | `add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [lc/LAST_CONTACTED] [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague` |
| **Edit contact**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [lc/LAST_CONTACTED] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                                 |
| **Delete contact** | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                                             |
| **Clear contacts** | `clear`                                                                                                                                                                                         |
| **Note (add)**     | `note INDEX NOTE [on/TIME]` <br> e.g., `note 1 To meet in February on/15 Apr`                                                                                                                   |
| **Note (remove)**  | `note INDEX c/LINES_TO_REMOVE` <br> e.g., `note 1 c/2`                                                                                                                                          |
| **Note (clear)**   | `note INDEX ca/` <br> e.g., `note 1 ca/`                                                                                                                                                        |
| **List contacts**           | `list`                                                                                                                                                                                          |
| **Find contacts**           | `find [KEYWORD]… [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…`<br> e.g., `find n/James t/friends` <br> `find @INDEX` e.g., `find @1`                                                      |
| **Sort contacts**           | `sort [n/] [p/] [e/] [a/] [lu/] [lc/] [t/TAG_NAME]…` <br> e.g., `sort n/`                                                                                                                       |
| **Undo**           | `undo`                                                                                                                                                                                          |
| **Redo**           | `redo`                                                                                                                                                                                          |
| **View contact**           | `view INDEX` <br> e.g., `view 1`                                                                                                                                                                |
| **Close view**     | `close view`                                                                                                                                                                                    |
