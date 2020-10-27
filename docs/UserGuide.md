# AniChan User Guide

![AniChan Logo](images/AniChan-Logo.png)

## Table of Contents
1. [Introduction](#1-introduction)
2. [Quick Start](#2-quick-start)
3. [Features](#3-features)
<br/>&nbsp;3.1 [View the help](#31-view-the-help)
<br/>&nbsp;3.2 [Estimate time need to translate the script](#32-estimate-time-needed-to-translate-the-script)
<br/>&nbsp;3.3 [Browse through all anime](#33-browse-through-anime)
<br/>&nbsp;3.4 [Search](#34-search-for-anime)
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.4.1 [Search by anime title](#341-search-by-anime-title)
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.4.2 [Search by genre](#342-search-by-genre)
<br/>&nbsp;3.5 [View the information of an anime](#35-view-the-information-of-an-anime)
<br/>&nbsp;3.6 [Workspace management](#36-workspace-management)
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.6.1 [Create new workspace](#361-create-new-workspace)
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.6.2 [Switch workspace](#362-switch-workspace)
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.6.3 [List workspaces](#363-list-workspaces)
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.6.4 [Delete workspace](#364-delete-workspace)
<br/>&nbsp;3.7 [Watchlist management](#37-watchlist-management)
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.7.1 [Create a new watchlist](#371-create-a-new-watchlist)
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.7.2 [List all created watchlist(s)](#372-list-all-created-watchlists)
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.7.3 [Select a watchlist to use](#373-select-a-watchlist-to-use)
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.7.4 [Delete a watchlist](#374-delete-a-watchlist)
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.7.5 [Add an anime to the current watchlist](#375-add-an-anime-to-the-current-watchlist)
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.7.6 [Remove an anime from the current watchlist](#376-remove-an-anime-from-the-current-watchlist)
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.7.7 [View all anime in watchlist](#377-view-all-anime-in-watchlist)
<br/>&nbsp;3.8 [Bookmark](#38-bookmark)
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.8.1 [List bookmark entries](#381-list-bookmark-entries)
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.8.2 [Add bookmark Entries](#382-add-bookmark-entry)
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.8.3 [Delete bookmark Entries](#383-delete-bookmark-entry)
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.8.4 [Edit bookmark entry episode](#384-edit-bookmark-entry-episode)
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.8.5 [Add note to bookmark entry](#385-add-note-to-bookmark-entry)
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.8.6 [View information of bookmark entry](#386-view-information-of-bookmark-entry)
<br/>&nbsp;3.9 [Exit AniChan](#39-exit-anichan)
<br/>&nbsp;3.10 [Saving and loading data](#310-saving-and-loading-data)

4. [FAQ](#4-faq)
5. [Command Summary](#5-command-summary)

## 1. Introduction

**AniChan** is an all-rounded tool to effectively create and organize anime lists with viewing statistics, efficiency-focused features, and tools to improve anime-watching experience.


## 2. Quick Start

1.  Ensure that you have Java 11 or above installed.
2.  Download the latest version of **AniChan** from [here](https://github.com/AY2021S1-CS2113T-F12-2/tp/releases/tag/V1.0).
3.  Copy the file to the folder you want to use as the home folder for **AniChan**.
4.  Open **Command Prompt** or **Powershell** and change directory into the folder. Run `java -jar anichan.jar`. 
5.  Type a command into the command prompt and press `Enter` to execute it. For example, typing `help` and pressing `Enter` will display the help message.


## 3. Features 
**Some useful notes on the Command Format**

*   Commands are case-sensitive.
E.g. Only `help` will work, and so `HELP`, `hElp`, and its other variant will not work.

*   In this guide words in UPPERCASE are values that can be supplied to the command.
E.g. in `browse -s SORT_CATEGORY -p PAGE_NO.` where SORT_CATEGORY and PAGE_NO. are parameters that can be used with `browse -s name -o asc`.

*   Square brackets indicate optional parameters.
E.g. `browse [-s SORT_CATEGORY]` can be used simply as `browse` or `browse -s name`.
  
*   The order of parameters are not important.
E.g. Both `browse -s name -p 2` and `browse -p 2 -s name` are both acceptable and will give you the same result.

> :bulb: Notice that you have a input prompt, this is what it means: WORKSPACE-NAME (WATCHLIST-NAME) #>

<br/>

### 3.1 View the help
This command will provide the details of all available commands and their usage. 
This is done by displaying the ‘Command Summary’ as listed below to the user. 

Format: `help`

<br/>

### 3.2

<br/>

### 3.3 Browse through anime

This command will provide a realistic **browsing** experience as you are able to ‘flip’ through pages of different anime series. This is a useful way to get a quick overview of all available anime series.

`browse` will also has various options to customise each browsing session by being able to sort the anime list. This can be done through the use of its optional parameters.

<br/>

Format: `browse [-s SORT_CATEGORY] [-o DISPLAY_ORDER] [-p PAGE_NUMBER`]
*   `-s` will indicate how the list is sorted and will accept the values `name` or `rating` 
*   `-o` will arrange the list in descending or ascending order by using the values `asc` or `dsc`
*   The order of the parameter does not matter
*   If no parameters or only `-o` is specified then it will display anime in no particular order.

Here are some commonly used `browse` commands to get you started. Feel free to experiment with different combinations!

*   `browse -s name -o dsc`: browse alphabetically from A - Z
*   `browse -s rating -o dsc`: browse starting from the most highly rated anime

<br/>

Example of usage: `browse -s name -o dsc`

The expected outcome:
```
1. .hack//Gift
2. .hack//Legend Of The Twilight
3. .hack//Liminality
4. .hack//Sign
5. 3x3 Eyes
6. A Chinese Ghost Story
7. ARIA The ANIMATION
8. Abashiri Ikka
9. Ace wo Nerae!
10. Ace wo Nerae! 2
11. Ace wo Nerae: Final Stage
12. After War Gundam X
13. Agatha Christie's Great Detectives Poirot and Marple
14. Agent Aika
15. Ah! My Goddess: The Movie
16. Ai Yori Aoshi
17. Ai Yori Aoshi: Enishi
18. Aim for the Ace! (1979)
19. Air
20. Air Master
Browsing Page: 1
```

<br/>

### 3.4 Search for anime

Search is a versatile tool that will allow you to search through all available anime for a specific anime, anime belonging to a certain genre, or all anime that has a keyword.

<br/>

### 3.4.1 Search by anime title
Search for all anime titles that contain or match precisely the search term.

Format: `search -n <SEARCH_TERM>` 

> :bulb: The search term is not case-sensitive.

Example of usage: `search -n bey`

The expected outcome:
```
[ID:216] Haruka: Beyond the Stream of Time – A Tale of the Eight Guardians
[ID:257] Beyblade
[ID:410] InuYasha the Movie 2: The Castle Beyond the Looking Glass
```
<br/>

### 3.4.2 Search by genre

Search for anime that has the genre matching the search term.

Format: `search -g <SEARCH_TERM>`

Example of usage: `search -g Slice of Life`

The expected outcome:
```
[ID:7] Honey and Clover
[ID:8] Hungry Heart: Wild Striker
[ID:35] Ai Yori Aoshi
[ID:39] Beck: Mongolian Chop Squad
[ID:48] Azumanga Daioh: The Animation
[ID:81] Air
...
[ID:446] Strawberry Marshmallow
[ID:447] KamiChu!
[ID:464] I My Me! Strawberry Eggs
[ID:467] Kiki's Delivery Service
[ID:475] Marmalade Boy Movie
[ID:488] Teacher's Time
```

### 3.5

<br/>

### 3.6

<br/>

### 3.7

<br/>

### 3.8

<br/>

### 3.9

<br/>

### 3.10

<br/>

## 4. FAQ

**Q:** What is a workspace?
<br/>
**A:** Workspace is a functionality in **AniChan** which provides users the flexibility to organise information. 
As application data like watchlist and bookmarks are linked to workspace, switching workspace would provide a 
clean slate environment to work in.

<br/>

**Q:** How can I save my data?
<br/>
**A:** **AniChan** automatically saves your data on every action you take. You can find them in `/data` folder in the same
directory you run **AniChan** in.

<br/>

**Q:** Can I edit the information in `data` directory?
<br/>
**A:** Yes! As **AniChan** saves and loads your information from the data directory, editing the files in `data` folder
works. However, we would strongly recommend you not to as you may cause data corruption. Use **AniChan** instead if you wish to edit your information!

<br/>

## 5. Command Summary

| Feature                                      | Command                                                  |
| ---                                          | ---                                                      |
| Help                                         | `help`                                                   |
| Estimate time needed to translate the script | `estimate <SCRIPT_FILE_NAME> [-wph WORDS_PER_HOUR]`      |
| Browse                                       | `browse -s <SORT_OPTION> -p <PAGE_NO.> -o <SORT_ORDER>`  |
| Search by title                              | `search -n <SEARCH_TERM>`                                |
| Search by genre                              | `search -g <SEARCH_TERM>`                                |
| View anime information                       | `info -a <ANIME_ID>`                                     |
| Create new workspace                         | `workspace -n <NAME>`                                    |
| Switch workspace                             | `workspace -s <NAME>`                                    |
| List workspace                               | `workspace -l`                                           |
| Delete workspace                             | `workspace -d <NAME>`                                    |
| Create watchlist                             | `watchlist -n <WATCHLIST_NAME>`                          |
| List all watchlist                           | `watchlist -l`                                           |
| Select watchlist                             | `watchlist -s <WATCHLIST_INDEX>`                         |
| Delete watchlist                             | `watchlist -d <WATCHLIST_INDEX>`                         |
| Add to watchlist                             | `add -a <ANIME_ID>`                                      |
| Remove from watchlist                        | `remove -d <ANIME_ID_IN_WATCHLIST>`                      |
| View anime in watchlist                      | `view -v <WATCHLIST_ID>`                                 |
| List bookmark                                | `bookmark -l`                                            |
| Add bookmark entry                           | `bookmark -a <ANIME_ID>`                                 |
| Delete bookmark entry                        | `bookmark -d <BOOKMARK_ID>`                              |
| Edit bookmark episode                        | `bookmark <BOOKMARK_ID> -e <EPISODE>`                    |
| Add note to bookmark                         | `bookmark <BOOKMARK_ID> -n <NOTE>`                       |
| View bookmark                                | `bookmark <BOOKMARK_ID>`                                 |
| Exit                                         | `exit`                                                   |

## TODO: Move all content below this line
<br/>
<br/>
<br/>
<br/>
<br/>

### 3.2 Workspace management: `workspace`

This command handles all workspace related operations:
*   Creates new workspace
*   Switches workspace
*   Lists existing workspaces
*   Deletes workspace

Therefore, the command parameter for workspace command is dependent on the operation you wish to use. 
For example, `-l` would signify list workspace while `-d` signifies delete.

#### 3.2.1 Creating new workspace

Format: `workspace -n <NAME>`

Example of usage: `workspace -n Crispy Donuts Studio`

The expected outcome:
```
Successfully added new workspace: Crispy Donuts Studio
```

#### 3.2.2 Switching workspace

Format: `workspace -s <NAME>`

Example of usage: `workspace -s Crispy Donuts Studio`

The expected outcome:
```
Workspace switched to Crispy Donuts Studio
```

#### 3.2.3 Listing workspaces

Format: `workspace -l`

Example of usage: `workspace -l`

The expected outcome:
```
Currently, you have 2 workspace(s):
1. Default
2. Crispy Donuts Studio
```

#### 3.2.4 Deleting workspace

Format: `workspace -d <NAME>`

Example of usage: `workspace -d Default`

The expected outcome:
```
Successfully deleted workspace: Default
```

<br/>

### 3.4 Watchlist management: `watchlist`
This command handles all watchlist management related operations: 
*   Create a new watchlist.
*   List all created watchlist(s).
*   Select another watchlist to use.
*   Delete a watchlist that is no longer needed.

Note:
*   Active watchlist refers to the watchlist that you are currently using for 
adding anime into or removing anime from.

Format: 
*   `watchlist -n <WATCHLIST_NAME>`
*   `watchlist -l`
*   `watchlist -s <WATCHLIST_INDEX>`
*   `watchlist -d <WATCHLIST_INDEX>`

Example of usage: `watchlist -n Adventure Anime`
*   Ensure the watchlist name is unique in your workspace.

The expected outcome: 
```
Watchlist "Adventure Anime" has been created successfully!
```

Example of usage: `watchlist -l`

The expected outcome: 
```
Currently, you have 2 watchlist(s):
	1. Default
	2. Adventure Anime
```

Example of usage: `watchlist -s 2`
*   Selected watchlist is also known as the **active watchlist**, which is the one
that you are using for adding anime into or removing anime from.
*   Notice how the name of the watchlist in the bracket of your prompt have changed.

The expected outcome: 
```
"Adventure Anime" is now your active watchlist!
```

Example of usage: `watchlist -d 2`
*   For deletion to succeed, you must have at least two watchlist.
*   If the currently active (selected) watchlist is deleted, then **AniChan** will automatically set
the first watchlist in the list of watchlist to be the new active watchlist.

The expected outcome: 
```
Watchlist "Adventure Anime" has been deleted successfully!
Changed active watchlist to: "Default".
```

<br/>

### 3.5 Add an Anime to the current watchlist: `add`
Add an anime to the currently selected watchlist

Format: `add -a <ANIME_ID>`

Example of usage: `add -a 3`

The expected outcome: 

```
Trigun added to watchlist!
```

<br/>

### 3.6 Remove an Anime from the current watchlist: `remove`
Remove an anime from the currently selected watchlist

Format: `remove -d <ANIME_ID_IN_WATCHLIST>`

Note:

The index used has to be the Anime ID in the watchlist, and not the general Anime ID

Example of usage: 'remove -d 1'

The expected outcome:

```
Trigun successfully removed from watchlist
```

<br/>

### 3.7 View all anime in watchlist: `view`
View all anime that is in the specified watchlist

Format: `view -v <WATCHLIST_ID>`

Example of usage: 'view -v 1'

The expected outcome:
```
Here are the anime in Default watchlist:
	1. Cowboy Bebop
	2. Witch Hunter Robin
```

<br/>

### 3.8 Bookmark an Anime: `bookmark`
This command handles all bookmark related operations: 
*   List all anime within bookmark.
*   Add an anime into bookmark.
*   Delete an anime from bookmark. 
*   Edit episode for an anime within bookmark
*   View details of an anime within bookmark [v2.0]

Format: 

`bookmark -l` will list bookmark

`bookmark -a <ANIME_ID>` will add the Anime into bookmark

`bookmark -d <BOOKMARK_ID>` will delete the Anime with bookmark id from bookmark

`bookmark <BOOKMARK_ID> -e <EPISODE>` will edit the episode for Anime with bookmark id

Example of usage: `bookmark -l`

The expected outcome: 
```
 Listing all anime in bookmark: 
	1. InuYasha the Movie 3: Swords of an Honorable Ruler Ep: 5
	2. To Heart
```

Example of usage: `bookmark -a 410`

The expected outcome: 
```
Saving 410. InuYasha the Movie 2: The Castle Beyond the Looking Glass to bookmark.
```

Example of usage: `bookmark -d 1`

The expected outcome: 
```
Removing InuYasha the Movie 3: Swords of an Honorable Ruler! :(
```

Example of usage: `bookmark 1 -e 5`

The expected outcome: 
```
Editing InuYasha the Movie 3: Swords of an Honorable Ruler to have 5 episode
```

<br/>

### 3.10 View the information of an Anime: `info`
View all the information regarding a specific anime

Format: `info -a <ANIME_ID>`

Example of usage: `info -a 1`

The expected outcome:
```
Here is the information for the anime:
Index: 1
Name: Cowboy Bebop
Episodes: 26
Release Date: 03/Apr/1998
Rating: 86
Genre: [Action, Adventure, Drama, Sci-Fi]
```

### 3.11 Estimate time needed to translate script: `estimate`
Estimates the time required to finish translating a script, users may provide
their estimated words per hour speed or use the average translator speed as an estimate.

Format: `estimate <SCRIPT_FILE_NAME> [-wph WORDS_PER_HOUR]`
*   **Only one** `.txt` file is accepted by **AniChan**.

*   You have to specify the file extension too! E.g. `script.txt`.

*   If the option `-wph` is not specified, **AniChan** will calculate the estimation timings
using the average translator's translation speed of 400, 500, and 600 words per hour. 
This will produce three estimation timings for you to consider.

Example of usage: `estimate script.txt`

The expected outcome: 
```
Average translator (400 words per hour) takes: 5 hour(s) 47 minute(s).
Average translator (500 words per hour) takes: 4 hour(s) 38 minute(s).
Average translator (600 words per hour) takes: 3 hour(s) 51 minute(s).
```

<br/>

Example of usage: `estimate script.txt -wph 777`

The expected outcome:
```
You would need 2 hour(s) 58 minute(s).
```

### 3.12 Exit AniChan: `exit`
Exit **AniChan** 

Format: `exit`

Example of usage: `exit`

The expected outcome:
```
Sayonara <NAME>!
```

<br/>

### 3.13 Saving and loading data
User, workspace(s), watchlist(s), and bookmark(s) data will be **saved automatically** when they are 
created or modified, and will be **loaded automatically** when **AniChan** is launched. 

In the folder where **AniChan** is launched, there will be a `data` folder which would contain these data:
*   User data is stored in `data/user.txt`.

*   Watchlist(s) data are stored in `data/<WORKSPACE-NAME>/watchlist.txt`, e.g. if your workspace is named "AniTranslator",
then the watchlist data can be found in `data/AniTranslator/watchlist.txt`.

*   Bookmark(s) data are also stored in the same location as watchlist data, `data/WORKSPACE-NAME/bookmark.txt`.