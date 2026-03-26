---
  layout: default.md
  title: "Appendix: Requirements"
  pageNav: 3
---

# **Appendix: Requirements**

## Product scope

**Target user profile**:

* consultant of a medium-sized consulting or PR agency
* has a need to manage long term relationships with big companies and clients
* has a need to manage long term relationships with various services in order to best assist their clients
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps
* tends to work by themselves
* does not share their computer with others

**Value proposition**: Allow better management of long term relationships with big companies and clients through a simple, unified interface. Provide users with quick, up-to-date access to clients’ data and relevant services necessary for quick and on-point responses.


## User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                 | I want to …​                                                                                                | So that I can…​                                                                                         |
|----------|-------------------------|-------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------|
| `* * *`  | consultant              | record clients' contact details                                                                             | retrieve them later                                                                                     |
| `* * *`  | consultant              | retrieve clients' contact details                                                                           | contact them again                                                                                      |
| `* * *`  | consultant              | delete client's contact details                                                                             | curate contacts and comply with data protection laws                                                    |
| `* * *`  | existing user           | check how to do a task / action                                                                             | use the app                                                                                             |
| `* *`    | consultant              | view an individual client's contact details                                                                 | look at their information in detail                                                                     |
| `* *`    | user with many clients  | find a person's contact by entering their name                                                              | easily contact them again even amongst an ocean of contacts                                             |
| `* *`    | user with many clients  | manually organise clients by importance / relevance                                                         | check up on them first                                                                                  |
| `* *`    | user with many clients  | sort profile data by last updated                                                                           | check for outdated profiles that require updating                                                       |
| `* *`    | security-conscious user | password-protect some sensitive information                                                                 | ensure privacy for profiles                                                                             |
| `* *`    | potential user          | review its functionalities without spending too much time                                                   | understand how the app will help me                                                                     |
| `* *`    | migrating user          | load existing client data as a file                                                                         | easily migrate clients over to the app                                                                  |
| `* *`    | migrating user          | create incomplete data profiles to be updated later                                                         | migrate existing clients over more gradually                                                            |
| `* *`    | consultant              | search for profiles by tags such as industry, occupation, company etc.                                      | quickly find contacts of services that may be useful to my client                                       |
| `* *`    | consultant              | search for profiles by fields such as location and email keywords                                           | quickly find contacts with limited information                                                          |
| `* *`    | consultant              | be reminded of upcoming appointments                                                                        | do not miss them                                                                                        |
| `* *`    | consultant              | edit client's contact details                                                                               | correct mistakes and comply with data protection laws                                                   |
| `* *`    | consultant              | sort contacts by relationship level or commission fees                                                      | find the most suitable help for my clients while balancing other factors such as budget                 |
| `* *`    | consultant              | update existing business information                                                                        | correct mistakes / obsolete data and comply with data protection laws                                   |
| `* *`    | consultant              | delete existing business information                                                                        | remove obsolete data and comply with data protection laws                                               |
| `* *`    | consultant              | store relevant business information to a client                                                             | save time looking it up in a separate app / physically                                                  |
| `* *`    | consultant              | retrieve relevant business information to a client                                                          | save time looking it up in a separate app / physically                                                  |
| `* *`    | consultant              | sort contacts by last contacted                                                                             | keep track of cold contacts                                                                             |
| `* *`    | busy consultant         | see a clear dashboard overview of recently contacted clients                                                | get straight to work without navigating through complex menus.                                          |
| `*`      | user                    | undo accidental actions                                                                                     | prevent permanent data loss from mistakes                                                               |
| `*`      | user                    | attach PDF contracts or brand guidelines to a client profile                                                | quickly retrieve them for reference                                                                     |
| `*`      | typing user             | easily input clients' data through file-editing                                                             | speed up my workflow                                                                                    |
| `*`      | proficient user         | archive old projects                                                                                        | my active workspace remains uncluttered                                                                 |
| `*`      | power user              | use keyboard shortcuts or task automation                                                                   | efficiently execute repeated tasks                                                                      |
| `*`      | potential user          | simulate my usual workflow with mock data                                                                   | get hands-on experience with the functionalities                                                        |
| `*`      | new user                | follow a simple guided setup                                                                                | customize the app to my needs and preferences                                                           |
| `*`      | forgetful user          | utilise fuzzy search even within commands                                                                   | easily use the retrieve data without clear memory of the commands or target details                     |
| `*`      | dark-mode user          | set the app to dark mode                                                                                    | reduce eye strain during late-night event planning                                                      |
| `*`      | light-mode user         | set the app to light mode                                                                                   | improve readability in well-lit environments                                                            |
| `*`      | consultant              | take down minutes during a discussion                                                                       | remember and reference them later                                                                       |
| `*`      | consultant              | cross reference tool which vendors have worked with which clients before                                    | understand relationships quickly                                                                        |
| `*`      | consultant              | automatically send emails to old clients                                                                    | follow up and keep contacts warm                                                                        |
| `*`      | consultant              | save availability information for selected contacts as a calendar and filter contacts based on availability | later on, I know when they can be contacted in person rather than needing to double-check ahead of time |
| `*`      | beginner user           | pick up advanced functionalities gradually                                                                  | utilize more of the features provided                                                                   |

*{More to be added}*

## Use cases

(For all use cases below, the **System** is the `B2B4U` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC1 - Check User Guide**

**MSS**

1. User requests user guide
2. B2B4U provides user guide

    Use case ends

**Use case: UC2 - Add a contact**

**MSS**

1. User requests to create a new contact with certain parameters
2. B2B4U saves the new contact
3. B2B4U displays confirmation that the new contact is saved

    Use case ends

**Extensions**

* 1a. The new contact is invalid
  * 1a1. B2B4U shows an error message

    Use case ends.

**Use case: UC3 - View all contacts**

**MSS**

1. User requests to list contacts
2. B2B4U shows a list of all stored contacts

    Use case ends.

**Use case: UC4 - Delete a contact**

**MSS**

1. User requests to <u>view contacts (UC3)</u>
2. User requests to delete a specific contact in the list
3. B2B4U deletes the contact

    Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

* 2a. The given index is invalid.
  * 2a1. B2B4U shows an error message.

    Use case resumes at step 1.

**Use case: UC5 - Filter contacts by criterion**

**MSS**
1. User requests to list contacts which fulfill given criteria
2. B2B4U shows a list of all contacts which fulfill given criteria

    Use case ends.

**Use case: UC6 - Sort contacts by criterion**

**MSS**
1. User requests to list contacts sorted by a given criteria
2. B2B4U shows a list of all contacts in order of given criteria

    Use case ends.

*{More to be added}*

## Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Should be able to hold up to 1000 contacts in storage.
3. Should always respond to user input within 250 milliseconds.
4. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
5. Should only be accessible to a single user.
6. Data should be stored locally and in a human editable text file.
7. Should work without requiring an installer.
8. Should work without reliance on a remote server.
9. Should only contain third-party frameworks/libraries/services which are free, open-source, and have permissive license terms (if any).
10. GUI should work well for standard screen resolutions 1920x1080 and higher, and for screen scales 100% and 125%.
11. GUI should be usable for resolutions 1280x720 and higher, and for screen scales 150%.
12. Should be packaged in a single JAR file of 100MB or less in size.

*{More to be added}*

## Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **JSON (JavaScript Object Notation)**: A lightweight text format used to store and transfer structured data.
* **API (Application Programming Interface)**: A defined set of methods that allows one component of the system to interact with another component.
* **GUI (Graphical User Interface)**: The visual interface through which the user interacts with the application, built using JavaFX.
* **CLI (Command Line Interface)**: A text-based interface where users interact with the application by typing commands.
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Command**: A structured line of text entered by the user to instruct the application to perform a specific action.
* **Contact**: A single entry stored in the contact list, containing one or more pieces of contact information such as name, phone number, email, or address.
* **Displayed list**: A subset of contacts displayed after applying a search, filtering, or sorting operation.
