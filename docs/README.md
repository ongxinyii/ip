# User Guide for Pookie
Pookie is a desktop task management chatbot that helps you track your tasks efficiently through a Command Line Interface (CLI) and a Graphical User Interface (GUI). If you can type fast, Pookie allows you to manage your tasks more quickly compared to traditional GUI-based applications.

## Quick Start
1. Ensure you have Java 17 or above installed on your computer.
2. Download the latest .jar file from the repository.
3. Copy the .jar file to a folder you want to use as Pookie's home directory.
4. Open a command terminal, navigate (cd) into the folder where you placed the .jar file, and run:
java -jar pookie.jar
5. A GUI window should appear in a few seconds, containing Pookie’s interface.

## Features
### Adding a Task: todo, deadline, event
You can add different types of tasks to Pookie.

#### 1. ToDo Task (simple tasks without deadlines):
todo TASK_DESCRIPTION

    Example:
    todo Buy groceries

#### 2. Deadline Task (tasks with a due date and time):
deadline TASK_DESCRIPTION /by YYYY-MM-DD HHmm

    Example:
    deadline Submit report /by 2025-02-28 2359

#### 3. Event Task (tasks with a start and end time):
event TASK_DESCRIPTION /from YYYY-MM-DD HHmm /to YYYY-MM-DD HHmm

    Example:
    event Project meeting /from 2025-03-01 1400 /to 2025-03-01 1600

#### 4. Fixed Duration Tasks: 
fixed_duration TASK_DESCRIPTION /duration H

    Example:
    fixed_duration do homework /duration 4

#### 5. Listing All Tasks:
list
Displays all tasks currently stored in Pookie. 

#### 6. Marking a Task as Done or Not Done: mark, unmark
Mark a task as done: mark TASK_INDEX

    Example:
    mark 2

#### 7. Unmark a task (set as not done):
unmark TASK_INDEX

    Example:
    unmark 2

#### 8. Finding a Task: find
Search for tasks by keyword.
find KEYWORD

    Example:
    find report
This command will return all tasks that contain the word "report."

#### 9. List tasks on a date: list on
Search for tasks on allocated date
list on YYYY-MM-DD

    Example: 
    list on 2025-05-23
This command will return all tasks that fall on 23 February 2025.

#### 10. Deleting a Task: delete
Deletes a task at the specified index.
delete TASK_INDEX

    Example:
    delete 3
This deletes the third task in the current list.

#### 11. Exiting Pookie: bye
Closes the application.

#### 12. Saving Tasks
Pookie automatically saves your tasks locally after every change. Your tasks are stored in a file at:
[data/pookie.txt]
You do not need to manually save your tasks.

#### 13. Editing the Data File
Pookie stores data as a plain text file at:
[data/pookie.txt]
You can manually edit this file, but be careful to maintain the correct format. Incorrect modifications may cause data loss or unexpected behavior.

⚠️ Warning: If the file format is incorrect, Pookie may not work as expected.

## FAQ
#### Q: How do I transfer my tasks to another computer?

Install Pookie on the new computer.
Copy the data/pookie.txt file from your old computer and paste it into the same directory on the new computer.
Start Pookie on the new computer.

#### Q: What happens if I accidentally delete the task file?

Pookie will generate a new, empty task list. If you need to recover tasks, restore the file from a backup.

#### Q: Can I edit the task file manually?

Yes, but be careful. Any incorrect formatting may result in unexpected issues.

## Command Summary

| Action                  | Command Format                                                   |
|-------------------------|------------------------------------------------------------------|
| Add ToDo task           | todo TASK_DESCRIPTION                                            |
| Add Deadline task       | deadline TASK_DESCRIPTION /by YYYY-MM-DD HHmm                    |
| Add Event task          | event TASK_DESCRIPTION /from YYYY-MM-DD HHmm /to YYYY-MM-DD HHmm |
| Add Fixed Duration task | fixed_duration TASK_DESCRIPTION /duration H                      |
| List all tasks          | list                                                             |
| Mark task as done       | mark INDEX                                                       |
| Unmark task             | unmark INDEX                                                     |
| Find task(s) by keyword | find KEYWORD                                                     |
| Find task(s) by date    | list on YYYY-MM-DD                                               |
| Delete tasks            | delete INDEX                                                     |
| Exiting application     | bye                                                              |
