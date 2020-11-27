# Ong De Zhi - Project Portfolio Page

## Overview

**AniChan** is an all-rounded desktop application meant to improve the workflow efficiency and time management of anime translators. It has also been optimized for fast typists, as such the user will interact with it through a command-line interface.

### Summary of Contributions

*   **New Feature:** Added the ability to estimate the time needed to translate a script.
    *   What it does: Allows the user to figure out the time needed to translate a script based on the amount of words they can translate in an hour (words per hour), or by using the average translator's speed (3 different words per hour values) to generate 3 timings.
    *   Justification: Being able to estimate the time needed more accurately can help the user in better managing and planning their time.
    *   Highlights: The challenge in this implementation is in ensuring a single valid file is provided, since the file name is a user input, it could contain special characters, whitespaces, file extensions, and even invalid file name characters. Hence, it needs to consider all these factors to ensure a valid file name is provided for estimation. Moreover, it also needs to ensure the optional parameter is not accidentally validated as part of the file name.
                    
*   **New Feature:** Added the ability to create, list, select, and delete watchlist.
    *   What it does: Allows the user to create, list, select, and delete watchlist.
    *   Justification: This feature provides the user with a way to keep track of animes and group them based on their own criteria, and this helps them to be more organized.
    *   Highlights: This feature maintains the active watchlist which is used by other commands and the input prompt, so it requires an in-depth analysis of the inputs received to ensure the modifications made by the user does not turn the active watchlist invalid. This is because an invalid active watchlist can cause other commands, and the application to fail.
    
*   **New Feature:** Added the ability to save and load watchlist data automatically.
    *   What it does: Helps the user to automatically save their watchlist data into the file `watchlist.txt`, and load these data automatically when the application is launched.
    *   Justification: Being an application to help users keep track of their animes and ensure they stay organized, persistent storage would be needed otherwise, users would have to re-enter their watchlist data every time they use the application.
    *   Highlights: The challenge with this implementation was in the loading of the watchlist data. It requires several in-depth checks to ensure that the different variation of invalid watchlist entry can be identified and dealt with. Also, it requires several branching logic to ensure that an invalid watchlist entry does not prevent other valid watchlist entry from being loaded.
    
*   **New Feature:** Added the ability to save and load user data automatically.
    *   What it does: Helps the user to automatically save their profile details into the file `user.txt`, and load these data automatically when the application is launched.
    *   Justification: This allows the application to greet the user so they can feel welcomed.
    *   Highlights: It performs several checks on the user data to ensure that a valid `User` object can be created from it.
        
*   **Code Contributed:** [RepoSense Link](https://nus-cs2113-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=ongdezhi&sort=groupTitle&sortWithin=title&since=2020-09-27&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=OngDeZhi&tabRepo=AY2021S1-CS2113T-F12-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other).

*   **Documentation:**
    *   User Guide: 
        *   Add documentation for the features `watchlist`, and data saving and loading: [#138](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/138).
        *   Add documentation for the feature `estimate`: [#146](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/146).
    *   Developer Guide:
        *   Contributed to the sections, Introduction, Setting Up Guide, Architecture Design, and StorageManager component: [#151](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/151), [#284](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/284).
        *   Add implementation details of `estimate` and `watchlist` feature: [#197](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/197).
        *   Contributed to the sections, Target User Profile, User Stories, and Non-Functional Requirements: [#207](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/207).

*   **Team-based Tasks**
    *   Helped to organize the classes into packages: [#63](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/63).
    *   Increased code coverage: [#98](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/98), [#139](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/139), [#140](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/140).
    *   Contributed to User Guide formatting: [#199](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/199), [#226](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/226).

*   **Beyond Project Team Tasks**
    *   Reported bugs and offered suggestions for a team in PE-D.
    *   Reviewed other teams' User Guide and Developer Guide.
