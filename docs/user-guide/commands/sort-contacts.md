# Sorting contacts: `sort`

Sorts the currently displayed contacts by the specified field(s).

Format: `sort [n/] [p/] [e/] [a/] [lu/] [lc/] [t/TAG_NAME]…`

* Sorts by the fields indicated by each prefix, in the order the prefixes are given.
* `n/` — sort by name, `p/` — sort by phone, `e/` — sort by email, `a/` — sort by address, `lu/` — sort by last updated, `lc/` — sort by last contacted.
* `t/TAG_NAME` — contacts with the ranked tag `TAG_NAME` are displayed at the top.
* At least one sort criterion must be provided.
* Sort criterions from separate `sort` commands can stack.
* Older sort criterion given priority in sorting.

Examples:
* `sort n/` sorts all contacts alphabetically by name.
* `sort lu/` sorts contacts by when they were last updated.
* `sort n/ t/vip` sorts contacts alphabetically by name, with contacts tagged `vip` shown first.
