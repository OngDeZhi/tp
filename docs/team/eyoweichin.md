# Eyo Wei Chin - Project Portfolio Page

## Overview

**AniChan** is an all-rounded desktop application meant to improve the workflow efficiency and time management of anime translators. It has also been optimized for fast typists, as such the user will interact with it through a command-line interface.

### Summary of Contributions

*   **New Feature**: Added `Browse` which is the ability to access and view all anime with ease.
    *   What it does: Is a useful feature that allows users to look through all available anime series. The browse feature utilises a paging system so that the user can 'flip' through the list of anime series. There is support for sorting as well, users will be able to browse through highly-rated anime or in alphabetical order.
    *   Justification: By having a `browse` feature, users can easily access and view all anime series that are available. As a translator, this will be an essential tool to find a series for reference or future job opportunities.
    *   Highlights: The implementation was challenging as many anime titles have special Unicode characters which are represented by more than 1 char. This made it difficult to print the anime in a consistent format due to the differing representation of the string length. There was also a challenge of having multiple optional parameters and different default values for each combination, which required a lot of branching logic to handle. Lastly, there were several design considerations to ensure that `browse` was able to fulfil its purpose while adhering to the constraints, these considerations are elaborated further in the developer guide.

*   **New Feature**: Added `Search` which is the ability to find any anime with a search term.
    *   What it does: Allows users to search for a specific anime by its full title, just a keyword or with a genre.
    *   Justification: `search` was built to find it difficult to remember anime titles such as `MUSHI-SHI` or long anime titles (>52 characters). This feature should be used in conjunction with the watchlist and bookmark feature to effectively find the anime that the translator wants quickly without much hassle.
    *   Highlights: The challenges of this implementation was with input validation, parsing the parameters into the command and finding the anime that fits the search term. There was some difficulty, to handle the mutually exclusive parameters and special Unicode characters. However, both was resolved with several modifications to the command parser.
    
*   **Code Contributed:** [RepoSense](https://nus-cs2113-ay2021s1.github.io/tp-dashboard/#search=EYOWEICHIN&sort=groupTitle&sortWithin=title&since=2020-09-27&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=false&tabOpen=true&tabType=authorship&tabAuthor=EyoWeiChin&tabRepo=AY2021S1-CS2113T-F12-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other).

*   **Enhancements to Existing Features**
    *   Contributed to development of the Switch Workspace feature: [#106](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/106), [#101](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/101), [#99](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/99).
    *   Contributed to writing of the CommandParser logic: [#128](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/128).
    *   Helped to refactor Main.java: [#261](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/261).
    *   Decoupled Ui from Parser, Command classes for better cohesiveness early on: [#80](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/80).
    *   Contributed to the development of the Anime Class: [#22](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/22).

*   **Documentation**
    *   User Guide:
        *   Add documentation for the features `browse` and `search`: [#190](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/190), [#111](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/111).
        *   Contributed to the "Using the Guide" section of the user guide: [#274](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/274).
    *   Developer Guide:
        *   Add Command and Starting Up sequence diagrams: [#261](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/261).
        *   Add implementation details for the `browse` feature: [#196](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/196), [#161](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/161).
        *   Contributed to the design of Architectural diagram.
        *   Contributed to the design and their write-ups of Ui, Parser, Command, User Class diagram designs: [#173](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/173), [#149](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/149).

*   **Team-based tasks**
    *   Created the labels and milestones for the team's GitHub repository.
    *   Organised online meetings and discussions.
    *   Created issues based on team's discussions and meeting. 
    *   Managed the team's collaboration document on Google Drive.
    *   Compiled and built the final jar files for releases.
    *   Added comprehensive JUnit testing to increase code coverage: [#182](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/182).
    *   Reviewed team members PR with non-trivial comments: [#269](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/269), [#135](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/135), [#185](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/185), [#46](https://github.com/AY2021S1-CS2113T-F12-2/tp/pull/46).
    *   Wrote a python script to pull and save anime data from AniList.
    *   Minor contributions to enable assertions/emoji use for the team.
    *   Gave demonstrations during product demos of **AniChan**.

*   **Beyond Project Team Tasks**
    *   Identified and reported bugs for a team in PE-D.
    *   Reviewed another team's Developer Guide.
    *   Reviewed another team's User Guide.
    