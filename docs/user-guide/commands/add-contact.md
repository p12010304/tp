# Adding a contact: `add`

Adds a contact to the contact list.

Format: `add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [lc/LAST_CONTACTED] [t/TAG]…​`

<box type="tip" seamless>

**Tip:** A contact can have any number of tags (including 0). At least one of `p/PHONE_NUMBER` or `e/EMAIL` must be provided.
</box>

* Names are standardised to Title Case (e.g. `john doe` becomes `John Doe`).
* Phone numbers accept digits, spaces, and `+`. Numbers starting with `+` (country code) must be 8–15 digits; otherwise 5–14 digits.
* Tags accept alphanumeric strings in the format `TAG` or `TAG:RANK` (e.g. `friend`, `client:vip`).
* `LAST_CONTACTED` accepts most conventional date/time formats (e.g. `22/02/2026`, `15 Apr`, `today`).

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`
* `add n/Alex Tan p/91234567`
* `add n/Jane Smith e/jane@example.com lc/22/02/2026`

![add contact](../../images/addContact.png)
