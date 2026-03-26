### Finding contacts: `find`

Finds contacts whose fields match the specified search criteria.

Format: `find [KEYWORD]… [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…`
or: `find @INDEX` to find contacts associated with the contact at INDEX

* The search is case-insensitive. e.g. `hans` will match `Hans`.
* Unprefixed `KEYWORD`s search across all fields (name, phone, email, address, notes, tags) using partial matching. Each keyword must appear somewhere in the contact.
* Prefixed searches (`n/`, `p/`, `e/`, `a/`) filter by the specified field using partial matching.
* `t/TAG` filters by tag using **exact** matching (e.g. `t/friend` will not match a tag named `friends`).
* All search conditions are combined with **AND** logic — only contacts satisfying **every** condition are returned.
* At least one search condition must be provided.
* `find @INDEX` cross-references the contact at the specified index — it shows all other contacts that share at least one tag with that contact. This helps consultants quickly understand which vendors have worked with which clients by finding shared project or relationship tags.

Examples:
* `find John` returns contacts containing `john` in any field.
* `find n/Alex` returns contacts with `Alex` in their name.
* `find p/94` returns contacts with `94` in their phone number.
* `find a/street t/friends` returns contacts that have `street` in their address **and** the exact tag `friends`.
* `find @1` shows all contacts that share at least one tag with the 1st contact in the displayed list.
* `find t/vendor` followed by `find @2` cross-references the 2nd vendor in the filtered list.

![find contacts]({{ baseUrl }}/images/findContacts.png)
