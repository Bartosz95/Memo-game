
# ![](src/main/resources/images/icon.png) Memo game 

## For installation and run command below
 [Download](https://github.com/Bartosz95/memo-game/archive/master.zip) and unzip or clone repository from github:
```shell script
git clone https://github.com/Bartosz95/memo-game/archive/master.zip
```
Change directory to memo-game/ :
```shell script
cd memo-game/
```
Build jar file and run:
```shell script
./gradlew run
```
## Use application
When the application has started at first there will be a visible menu view.
#### Menu
Main looks like below. Like it see it included 3 buttons: 
 * PLAY - run the game with is presented in section **Game**,
 * Options - run option menu with is presented in section **Option's menu**,
 * Exit Game - exit the game.
 
![](description-images/menu.PNG)

### Game
In the game view you see a button's matrix. Main rule of the memo game is to match two of the same pictures to each other. Score is displayed in the bottom-left corner. If you want to go to the main menu you must win a game or run it again.
![](description-images/game.PNG)

### Option's menu
Option's menu included 2 things. ChoiceBox provides a choice of how many picture's couples  are necessary to match. Great thing is that the algorithm lays pictures always in rectangle shape. That introduces a necessity choice if gamers prefer vertical placement of pictures. Button **Back to menu** moves to the main menu.

![](description-images/options.PNG)

