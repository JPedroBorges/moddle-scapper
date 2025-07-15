# moddle-scapper

**⚠️ This project is not actively maintained.**

A console-based tool to track student progress on a specific Moodle setup. It was built for a single course with a self-paced structure, where reviewing submissions by date is more helpful than by course order.

Due to the lack of a usable Moodle API in the specific moodle instance, the tool uses Selenium to scrape the site and list unread messages and assignments pending review.

This tool is tailored to a specific Moodle installation and is not configurable or tested for general use. Course structure and other specificities are currently hardcoded.


## Features
- Lists homework submissions pending review
  - Shows publish and due dates
  - Sorted by submission date, not course structure
- Lists unread student messages
  - Shows publish and due dates
- Interactive terminal interface

## output example:
```
------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Total gradables: 80
    Link                                                                                  Name                                            Exercise
------------------------------------------------------------------------------------------------------------------------------------------------------------------------
due: 2024-12-15	submitted: 2024-12-08	(2)
    https://<base_url>/mod/assign/view.php?id=893&action=grade&userid=1259                student1                                        M1 A7 E3 - Cartas II
    https://<base_url>/mod/assign/view.php?id=907&action=grade&userid=1259                student2                                        M2 A1 E1 - Carro
due: 2024-12-17	submitted: 2024-12-10	(1)
    https://<base_url>/mod/assign/view.php?id=907&action=grade&userid=1259                student3                                        M2 A2 E2 - Jogo de Xadrez
```

## Running
configuration.properties
```properties
moodle.baseurl= 
moodle.username=
moodle.password=
moodle.course= # Moodle course ID
config.interactive= # true/false – interactive mode or full listing
```

or

```bash
BASE_URL=<url> \
MUSERNAME=<moodle_username> \
MPASSWORD=<moodle_password> \
COURSE=<moodle_course_id> \
java -Dlog.level=<log_level> -jar moopper.jar
```