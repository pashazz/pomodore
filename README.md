# architecture
## PomodoreTgBot class

- creates new timers

## Creating a new timer
A new timer is a Task. Tasks are managed by TaskService.

In essence, all we do is we create a new row in Tasks table, which has its time field updated every X seconds (configurable).

When we reach the time written in the database, we'll trigger the job which will rewrite the time.

Should we restore?


## TODO
Yes, so select all the tasks which have STARTED status and rerun them.
