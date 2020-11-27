# Chan Jian Hao - Project Portfolio Page

## Overview

**AniChan** is an all-rounded desktop application meant to improve the workflow efficiency and time management of anime translators. It has also been optimized for fast typists, as such the user will interact with it through a command-line interface.

### Summary of Contributions

*   **New Feature:** Added the ability to create `User`.
    *   What it does: On first execution, prompts user for his name and gender to create `User` profile.
    *   Justification: This feature allows program to know user better so that program may greet user appropriately as part of good user experience we hope to provide.
    *   Highlights: Notably, `User` is a subclass of `Human` abstract class. Other classes such as `Character` and `VoiceActor` inherits `Human` class as part of our OOP practice when coding **AniChan** which stores lots of anime data which comes with lots of voice actors and characters. `User` class contains many interesting methods meant for controlling `Workspace` feature of **AniChan**.

*   **New Feature:** Added the ability to create, list, switch, and delete `Workspace`.
    *   What it does: Allows the user to create, list, select, and delete `Workspace`.
    *   Justification: This feature enables the user to segregate his translation work. As `Watchlist` and `Bookmark` are contained in individual `Workspaces`.
    *   Highlights: `Workspace` works closely with core components of **AniChan** such as `User`, `Watchlist`, `Bookmark` and notably, `Storage`. To ensure secure and safe creation of `Workspace` when program exits, layers of string validation and sanitization ensures no unexpected behaviors when creating `Workspace` in `data` folder.

*   **New Feature:** Added customized exception class, `AniException`.
    *   What it does: Allows application to throw custom exceptions specific to **AniChan**.
    *   Justification: There needs to be a way to handle exception which are caused by **AniChan**-related operations.
    *   Highlights: Made a special exception package and class which extends Java default `Exception` class.

*   **New Feature:** Added logging class `AniLogger`.
    *   What it does: Logs information during **AniChan** runtime to both console and file system `data/AniChan.log` file.
    *   Justification: There needs to be a way to handle logging for all the classes in **AniChan** without duplicating Java `logger` everywhere.
    *   Highlights: Implemented method for all classes to easily get logger for their own logging usage. Standardized application-wide **AniChan** logging settings and level for both console and file system. **AniLogger** is fully capable of handling add and removals of console and file handlers and manage logs creation and exceptions, if any.

*   **New Feature:** Implemented Workspace delete feature for `Storage`.
    *   What it does: Deletes `Workspace` on the file system when `Workspace` is deleted in **AniChan**.
    *   Justification: When `Workspace` is deleted in **AniChan**, the leftover folder and files in `data` should be deleted as well.
    *   Highlights: Implemented a safe recursive delete approach for `Workspace` deletion, where it searches recursively for leftover `Watchlist`/`Bookmark` data to delete first before deleting the folder. Exceptions and input validation built-in for deletion to prevent file system related issues to crash the program or cause unexpected results.

*   **Code Contributed:** [RepoSense Link](https://nus-cs2113-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=chanjianhao&sort=groupTitle&sortWithin=title&since=2020-09-27&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=ChanJianHao&tabRepo=AY2021S1-CS2113T-F12-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other)

*   **Documentation:**
    *   README.md:
        * Designed **AniChan** [logo](https://github.com/AY2021S1-CS2113T-F12-2/tp/blob/master/docs/images/AniChan-Logo.png).
        * Created badges on various DevOps and statuses: [#154](https://github.com/AY2021S1-CS2113T-F12-2/tp/issues/154).
        * Add short writeup to introduce **AniChan** and provide useful links to pages like user guide and developer guide.
    *   User Guide: 
        *   Contributed to writeup on Quick Start: [#62](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/62).
        *   Documented guide on `Workspace` management: [#162](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/162), [#185](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/185), [#302](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/302).
    *   Developer Guide:
        *   Contributed to writeup on `User` architecture and diagram: [#254](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/254).
        *   Contributed to `Workspace` related user stories and manual testing guide: [#285](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/285).
        *   Documented `Workspace` implementation and its diagrams: [#206](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/206).
        *   Documented the section on '**Documentation, Logging, Testing, and DevOps**': [#195](https://github.com/AY2021S1-CS2113T-F12-2/tp/issues/195).
        *   Formatting & Grammar fixes and improvements: [#254](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/254), [#302](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/302), [#310](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/310).

*   **Team-based Tasks**
    *   Created team organization and GitHub repository.
    *   Contributed to input sanitization and validation of the overall program.
    *   Refactored common code found in `Main` and `Ui`, and assisted to delete redundant imports and variables [#152](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/152), [#217](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/217), [#256](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/256).
    *   Setting up GitHub pages and Gradle.
    *   Maintaining the issue tracker.
    *   Setup of team's Telegram & Discord channel for communication.
    *   Implemented additional CI checks for repository quality: [#179](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/179).
    *   Assisted in team's request for Java libraries: [#43](https://github.com/nus-cs2113-AY2021S1/forum/issues/43), [#78](https://github.com/nus-cs2113-AY2021S1/forum/issues/78), [#111](https://github.com/nus-cs2113-AY2021S1/forum/issues/111).
    *   Increased code coverage: [#150](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/150), [#221](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/221).

*   **Beyond Project Team Tasks**
    *   Reported a substantial amount of bugs in another team's repository: [#91](https://github.com/AY2021S1-CS2113-T13-4/tp/issues/91), [#93](https://github.com/AY2021S1-CS2113-T13-4/tp/issues/93), [#94](https://github.com/AY2021S1-CS2113-T13-4/tp/issues/94), [#96](https://github.com/AY2021S1-CS2113-T13-4/tp/issues/96), [#101](https://github.com/AY2021S1-CS2113-T13-4/tp/issues/101), [#102](https://github.com/AY2021S1-CS2113-T13-4/tp/issues/102), [#112](https://github.com/AY2021S1-CS2113-T13-4/tp/issues/112), [#118](https://github.com/AY2021S1-CS2113-T13-4/tp/issues/118), [#120](https://github.com/AY2021S1-CS2113-T13-4/tp/issues/120).
    *   Provided helpful feedback for team NavNUS: [#9](https://github.com/nus-cs2113-AY2021S1/tp/pull/9/files/cabbf353f022bfb19f0bdeeeb17ba7572bed1484).

*   **Tools:**
    *   Integrated [Codacy](https://www.codacy.com/) static code analysis to improve code and documentation quality within team.
    *   Integrated [Travis CI](https://travis-ci.org/) for DevOps purposes.
    *   Integrated [Codecov](https://codecov.io/) for coverage tracking.
    *   Added Discord bot using [webhooks](https://support.discord.com/hc/en-us/articles/228383668-Intro-to-Webhooks) for GitHub tracking.
    