---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# TeachMate User Guide

TeachMate is a **desktop app for Teaching Assistants (TAs) to manage university students, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, TeachMate can help you manage your students faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-T12-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for TeachMate.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all students.

   * `add n/John Doe s/A0123456X e/johnd@u.nus.edu m/CS2103T` : Adds a student named `John Doe` with student ID `A0123456X` to TeachMate.

   * `delete 3` : Deletes the 3rd student shown in the current list.

   * `clear` : Deletes all students.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/struggling` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/struggling`, `t/struggling t/needsHelp` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME s/STUDENT_ID`, `s/STUDENT_ID n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a student: `add`

Adds a student to TeachMate.

Format: `add n/NAME s/STUDENT_ID e/EMAIL m/MODULE_CODE [m/MODULE_CODE]…​ [t/TAG]…​ [c/CONSULTATIONS]`

<box type="info" seamless>

**Parameter Constraints:**
* `NAME` should only contain alphanumeric characters and spaces, and should not be blank
* `STUDENT_ID` must be in the format A followed by exactly 7 digits and 1 uppercase letter (e.g., A0123456X)
* `EMAIL` should be of the format local-part@domain (see detailed constraints below)
* `MODULE_CODE` must be in NUS format: 2-3 uppercase letters, followed by 4 digits, optionally ending with 1 uppercase letter (e.g., CS2103T, CS2101)
* `TAG` should be alphanumeric (no spaces)
* At least one module code is required
* Tags are optional
* Consultations are optional, if provided it should follow one of the supported formats listed below
</box>

<box type="tip" seamless>

**Email Constraints:**
* The local-part should only contain alphanumeric characters and these special characters: `+ _ . -`
* The local-part may not start or end with special characters
* The domain name must end with a domain label at least 2 characters long
* Each domain label must start and end with alphanumeric characters
* Each domain label may contain hyphens between alphanumeric characters
</box>

<box type="tip" seamless>

**Consultations Constraints:**
* Consultations are optional — a student can be added without any consultations
* If included, each consultation must follow one of these date/time formats:
    * `dd/MM/yyyy HH:mm` → e.g., `22/10/2025 15:30`
    * `dd-MM-yyyy HH:mm` → e.g., `22-10-2025 15:30`
    * `dd MMM yyyy HH:mm` → e.g., `22 Oct 2025 15:30`
    * `dd/MM/yyyy hh:mma` → e.g., `22/10/2025 3:30PM`

</box>


Examples:
* `add n/John Doe s/A0123456X e/johnd@u.nus.edu m/CS2103T`
* `add n/Jane Smith s/A0234567Y e/janes@u.nus.edu m/CS2103T m/CS2101 t/struggling t/needsHelp c/22 Oct 2025 15:30 `

### Listing students : `list`

Shows a list of all students in TeachMate, or filters students by module code.

Format:
* `list` - Lists all students
* `list m/MODULE_CODE` - Lists students in a specific module

Examples:
* `list` - Shows all students
* `list m/CS2103T` - Shows all students taking CS2103T

### Viewing a student : `view`

Views the name of a student identified by their index or student ID.

Format:
* `view INDEX` - View student by list index
* `view s/STUDENT_ID` - View student by student ID

* The index refers to the index number shown in the displayed student list
* The index **must be a positive integer** 1, 2, 3, …​
* Student ID must match the format A followed by 7 digits and 1 uppercase letter

Examples:
* `view 1` - Views the 1st student in the current list
* `view s/A0123456X` - Views the student with student ID A0123456X

### Editing a student : `edit`

Edits an existing student in TeachMate. You can edit students whether they have phone/address (legacy data) or only have student-specific fields.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [s/STUDENT_ID] [m/MODULE_CODE]…​ [t/TAG]…​ [c/CONSULTATIONS]`

* Edits the student at the specified `INDEX`
* The index refers to the index number shown in the displayed student list
* The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided
* Existing values will be overwritten by the input values
* When editing module codes, the existing module codes will be removed and replaced (not cumulative)
* When editing tags, the existing tags will be removed and replaced (not cumulative)
* You can remove all tags by typing `t/` without specifying any tags
* You can remove all module codes by typing `m/` without specifying any module codes
* Editing consultations should still follow the same format as in the `add` command

<box type="warning" seamless>

**Note about legacy fields:**
The edit command still supports `p/PHONE` and `a/ADDRESS` prefixes for backward compatibility with older data. However, new students added via the `add` command will not have phone or address fields.
</box>

Examples:
*  `edit 1 s/A9999999Z e/newemail@u.nus.edu` - Edits the student ID and email of the 1st student
*  `edit 2 n/Jane Doe t/` - Edits the name of the 2nd student and clears all tags
*  `edit 3 m/CS2103T m/CS2101` - Replaces all module codes with CS2103T and CS2101

### Locating students by name: `find`

Finds students whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive (e.g. `john` will match `John`)
* The order of the keywords does not matter (e.g. `John Doe` will match `Doe John`)
* Only the name is searched
* Only full words will be matched (e.g. `Joh` will not match `John`)
* Students matching at least one keyword will be returned (i.e. `OR` search)

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a student : `delete`

Deletes the specified student from TeachMate.

Format: `delete INDEX`

* Deletes the student at the specified `INDEX`
* The index refers to the index number shown in the displayed student list
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd student in TeachMate
* `find Jane` followed by `delete 1` deletes the 1st student in the results of the `find` command

### Clearing all entries : `clear`

Clears all entries from TeachMate.

Format: `clear`

<box type="warning" seamless>

**Warning:** This action cannot be undone. All student data will be permanently deleted.
</box>

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

TeachMate data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

TeachMate data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, TeachMate will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause TeachMate to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous TeachMate home folder.

**Q**: What is the student ID format?<br>
**A**: Student IDs must follow the NUS format: A followed by exactly 7 digits and 1 uppercase letter (e.g., A0123456X, A1234567B).

**Q**: Can I add a student without module codes?<br>
**A**: No, every student must have at least one module code when using the `add` command.

**Q**: Why does the edit command still accept phone (p/) and address (a/) prefixes?<br>
**A**: For backward compatibility with older data that may contain phone and address information. New students added via the `add` command will only have student-specific fields (student ID, module codes).

**Q**: Can tags contain spaces?<br>
**A**: No, tags must be alphanumeric without spaces. Use camelCase or single words (e.g., `needsHelp`, `struggling`).

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action     | Format, Examples                                                                                                                                                          |
|------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**    | `add n/NAME s/STUDENT_ID e/EMAIL m/MODULE_CODE [m/MODULE_CODE]…​ [t/TAG]…​` <br> e.g., `add n/John Doe s/A0123456X e/johnd@u.nus.edu m/CS2103T m/CS2101 t/struggling`  |
| **Clear**  | `clear`                                                                                                                                                                   |
| **Delete** | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                       |
| **Edit**   | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [s/STUDENT_ID] [m/MODULE_CODE]…​ [t/TAG]…​`<br> e.g., `edit 2 n/Jane Lee s/A9999999Z`                              |
| **Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find John Jane`                                                                                                                |
| **List**   | `list` or `list m/MODULE_CODE`<br> e.g., `list m/CS2103T`                                                                                                                |
| **View**   | `view INDEX` or `view s/STUDENT_ID`<br> e.g., `view 1` or `view s/A0123456X`                                                                                             |
| **Help**   | `help`                                                                                                                                                                    |
| **Exit**   | `exit`                                                                                                                                                                    |
