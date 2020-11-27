# Ong Xin Bin - Project Portfolio Page

## Overview

**AniChan** is an all-rounded desktop application meant to improve the workflow efficiency and time management of anime translators. It has also been optimized for fast typists, as such the user will interact with it through a command-line interface.

### Summary of Contributions

*   **Application setup**: Loading of anime information from JSON data file
    *   What it does: Setting up the anime information from offline data sources, the data sources are stored as JSON files embed within the program. The file data requires a parser to properly extract anime information and convert it into an anime object, which will be used by many features to query anime information.   
    *   Justification: By extracting anime data, the program can easily use anime-related functions. As a translator, it is crucial to have anime information that is required by the translator.
    *   Highlights: Using an unfamiliar JSON library made the implementation of the JSON parser challenging, the simple JSON library also limit the reading of file to the size of string which required the source file to divide it into smaller JSON files. In addition, there is the need to embed the data files into the release file to keep it hidden from the user.
    
*   **New Feature**: Added the ability to add, list, delete a bookmark. With extended features to view, edit the episode, add a note and remove a note for a bookmark entry.
    *   What it does: Allows the user to track the anime of their interest, with additional personalisation like specifying the current episode or adding notes to the bookmark entry.
    *   Justification: This bookmarking feature was designed to be a shortcut to the anime information they require, the extended features then allow the bookmark to keep additional information for particular anime that is useful to the translators.
    *   Highlights: The biggest challenge was the requirement for all bookmark features to share a single bookmark command, it has to accommodate seven bookmark actions without being too complicated to the users. This is done using multiple parameters and different combinations of variable signature types. Additionally, the bookmark parser and bookmark command logic are branched by the type of bookmark action to perform with various checks and validations.

*   **New Feature:** Added the ability to save and load bookmark data automatically.
    *   What it does: Helps the user to automatically save their bookmark data into the file `bookmark.txt`, and loads the data found in this file automatically when the application is launched.
    *   Justification: Being an application to help users keep track of their anime, the current episodes, and the anime notes, persistent storage would be essential to ensure users would not have to re-enter their bookmark information each time they use the application.
    *   Highlights: When loading bookmark data, each entry (each line) recorded in `bookmark.txt` would be split into parts that form a bookmark entry. If the objects do not properly form each bookmark objects, they are deemed corrupted. When the bookmark is corrupted, then only that specific bookmark cannot be loaded, the rest of the bookmark(s) will still be loaded if they are valid.

*   **Code contributed:** [RepoSense](https://nus-cs2113-ay2021s1.github.io/tp-dashboard/#search=n3wsoldier&sort=groupTitle&sortWithin=title&since=2020-09-27&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=false&tabOpen=true&tabType=authorship&tabAuthor=n3wsoldier&tabRepo=AY2021S1-CS2113T-F12-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other)

*   **Documentation**
    *   User Guide:
        *   Add documentation for the feature `bookmark`: [#193](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/193)

    *   Developer Guide:
        *   Add implementation details of `bookmark` feature: [#201](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/201)
        *   Contributed to the design and their write-ups of Command, AnimeData, User and Storage Manager Class diagram designs: [#174](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/174)
        *   Contributed to manual testing of `bookmark` feature: [#271](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/271)
        *   Contributed to user stories: [#215](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/215)

*   **Team-based tasks**
    *   Created issues based on team's discussions and meeting.
    *   Review and comment on PR by other team members. 
    *   Add comprehensive JUnit testing to increase code coverage: [#183](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/183)

*   **Beyond Project Team Tasks**
    *   Review the Developer Guide of another team.
    *   Review the User Guide of another team.
    *   Perform System and Acceptance testing for PE-D.