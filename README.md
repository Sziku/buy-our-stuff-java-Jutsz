# Buy Our Stuff

## Story

BOO!!! Don't fret, we are friendly ghosts of past Codecoolers. We know how you feel, we've been there...
As a matter of fact, we had a task we couldn't finish back in the day, so our souls cannot rest!
Tasks were much harder back then, that's right! Anyway, so, there was this one task where we had to
create a webshop. It's called `Buy Our Stuff`, because, well, we wanted people to buy our stuff.
Makes sense, huh? Anyway, we spent so much time on the frontend (just wait until you see it for yourself) that
we couldn't create a proper persistency layer and now we are doomed to haunt students... It was fun at first,
but we've gotten so bored! Unfortunately, the punishment for not doing it in the past is that releasing our
souls requires two persistency layers. Will you please help, so that we can rest in peace?
One shall utilize files, and the other one shall utilize a database. Thank y... err... BOO!!

## What are you going to learn?

- the DAO pattern
- managing databases
- managing files

## Tasks

1. The application originally stores every data in memory, which is an issue, because every change gets lost whenever the application is stopped. Add DAO implementations which use **a database** for persistency. Make sure to utilize the DAO pattern so that it's easy to switch between DAO implementations.
    - DAO implementations can be switched by changing just one variable (or env var).
    - Changing the used DAO to _database_ results in the application creating a database and storing all the product and customer related data in it.
    - A previously registered user can log in to the webshop after restarting the application.
    - Registered users can retrieve their shopping cart contents after restarting the application.

2. The application originally stores every data in memory, which is an issue, because every change gets lost whenever the application is stopped. Add DAO implementations which use **files** for persistency. Make sure to utilize the DAO pattern so that it's easy to switch between DAO implementations.
    - The used DAO implementation can be switched by changing just one variable (or env var).
    - Changing the used DAO to _file_ results in the application creating files and storing all the product and customer related data in them.
    - A previously registered user can log in to the webshop after restarting the application.
    - Registered users can retrieve their shopping cart contents after restarting the application.

3. [OPTIONAL] There are a lot of generic (not _that_ sort of generic :)) getters, setters, and even constructors, especially in the model classes. Use Lombok to make less clutter, wherever it's possible! _Note: getters are fine, but some setters and constructors use special logic. These ones can't be replaced._
    - Generic getter methods in the model classes are replaced with Lombok annotations.
    - Generic setter methods in the model classes are replaced with Lombok annotations.
    - Generic constructors in the model classes are replaced with Lombok annotations.

4. [OPTIONAL] Modify the part of the code where the used DAO implementation is decided so that the decision is based on an environment variable. This way the code no longer needs to be touched for choosing a different DAO. Also, modify the database connection method so that it reads the required data (username, password), too, from env vars. Update the project's readme so that others can find out what sort of env var(s) they need to set up.
    - The program can decide which DAO implementation to use, based on an env var.
    - The program can read database username and password from env vars.
    - Other developers can find out how to set up env vars for the project from its `README.md` file.

5. [OPTIONAL] Currently only `ProductCategoryDao` is tested. Write similar tests for each Dao. _Note: testing `clear()` and `getAll()` methods is not required. If you still want to do it, try setting up separate test environment (database / files) for the tests!_
    - All tests are green.
    - All required methods in `CartDao` are covered with tests.
    - All required methods in `LineItemDao` are covered with tests.
    - All required methods in `ProductDao` are covered with tests.
    - All required methods in `SupplierDao` are covered with tests.
    - All required methods in `UserDao` are covered with tests.

## General requirements

None

## Hints

- Use IntelliJ's `TODO` feature to find some of the tasks ;)
- The only parts of the code you'll need to touch are in the `dao` package (and optionally in `model` and `test`)
- Note that passwords are stored in a _salt & hash_ format (check `UserDaoMem` to see how to use jBCrypt)
- Check `how-to-run.md` to see how to run the application

## Background materials

- <i class="far fa-exclamation"></i> [About the Dao pattern](http://best-practice-software-engineering.ifs.tuwien.ac.at/patterns/dao.html)
- <i class="far fa-exclamation"></i> [More Dao pattern material](https://www.baeldung.com/java-dao-pattern)
- <i class="far fa-exclamation"></i> [About jBCrypt](https://www.mindrot.org/projects/jBCrypt/)
- <i class="far fa-camera"></i> [Salting & Hashing explained](https://youtu.be/--tnZMuoK3E)
- <i class="far fa-camera"></i> [Don't encrypt passwords](https://youtu.be/FYfMZx2hy_8)
- <i class="far fa-candy-cane"></i> [Lombok introduction](https://www.baeldung.com/intro-to-project-lombok)
- <i class="far fa-candy-cane"></i> [Using env vars in Java](https://www.tutorialspoint.com/java/lang/system_getenv_string.htm)
- <i class="far fa-candy-cane"></i> [The IntelliJ TODO tool window](https://www.jetbrains.com/help/idea/todo-tool-window.html)
